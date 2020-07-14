package com.ule.uhj.zgd.service;

import com.ule.uhj.Dcoffee.core.adapter.AdapterBuilder;
import com.ule.uhj.Dcoffee.core.adapter.FilterBean;
import com.ule.uhj.Dcoffee.core.builder.dataset.DataSetBuilder;
import com.ule.uhj.Dcoffee.core.builder.source.SourceBuilder;
import com.ule.uhj.Dcoffee.core.builder.virtualTable.TableBuilder;
import com.ule.uhj.Dcoffee.core.power.PowerSupply;
import com.ule.uhj.Dcoffee.object.coupling.conf.constant.CoffeeRecipe;
import com.ule.uhj.Dcoffee.object.model.inner.connector.Connector;
import com.ule.uhj.Dcoffee.object.model.inner.connector.DataBaseConnectorV2;
import com.ule.uhj.Dcoffee.object.model.inner.connector.Slot;
import com.ule.uhj.Dcoffee.object.model.inner.dataSet.DataSet;
import com.ule.uhj.Dcoffee.object.model.inner.source.Source;
import com.ule.uhj.Dcoffee.object.model.inner.util.Log;
import com.ule.uhj.Dcoffee.object.model.inner.util.Parameter;
import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.DataBaseVirtualTable;
import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.VirtualTable;
import com.ule.uhj.Dcoffee.object.model.interaction.coffee.Coffee;
import com.ule.uhj.Dcoffee.object.model.interaction.coffeeBeans.CoffeeBean;
import com.ule.uhj.app.zgd.dao.PowerMapper;
import com.ule.uhj.app.zgd.model.FieldObj;
import com.ule.uhj.util.Convert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.*;

/**
 * Created by zhengxin on 2018/3/22.
 */
@Service
public class PowerSupplyService implements PowerSupply {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PowerMapper powerMapper;

    private static final ThreadLocal<List<Source>> sourcesLocal = new ThreadLocal<List<Source>>();

    @Override
    public DataSet getDataSetById(String Id,List<Parameter> parameterList) {
        Map<String,Object> queryParameterMap = new HashMap<String, Object>();
        queryParameterMap.put("id",Id);
        List<DataSet> dataSetList = powerMapper.getDataSetById(queryParameterMap);
        DataSet dataSet = null;
        if(dataSetList!=null && dataSetList.size()>0){
            dataSet = dataSetList.get(0);
        }
        if(dataSet!=null){
            String id = dataSet.getId();
            /***
             * 2018-03-28
             * @TODO 注意，这里的连接器写死了！留待日后优化
             * By.ZhengXin
             */
            return DataSetBuilder.buildDataSet(dataSet, getSources(id, parameterList), getConnector(id), getFilter(id));
        }
        return null;
    }

    public String getDataSetDescriptor(String dataSetId){
        Map map = new HashMap();
        map.put("dataSetId",Convert.toStr(dataSetId,"UNKNOW"));
        return powerMapper.getDataSetDescriptorById(map);
    }

    public List<DataSet> getDataSetList(){
        Map parameterMap = new HashMap();
        parameterMap.put("support", CoffeeRecipe.DataSet.SUPPORT_REPORT);
        List<DataSet> dataSetList = powerMapper.getDataSetBySupport(parameterMap);
        return dataSetList;
    }

    @Override
    public Coffee boil(Coffee greenCoffee) {
        List<Map<String, Object>> result = null;

        CoffeeBean coffeeBean = greenCoffee.getCoffeeBean();
        if(coffeeBean.getPageSize() != null && coffeeBean.getPageCurr() != null){
            Long pageSize = coffeeBean.getPageSize();
            Long pageCurr = coffeeBean.getPageCurr();

            long minRow = (pageCurr - 1) * pageSize + 1;
            long maxRow = pageCurr * pageSize;

            String preExeSql = new String(greenCoffee.getSqlExe());
            if(preExeSql.endsWith(";")){
                preExeSql = preExeSql.substring(0,preExeSql.length()-1);
            }

            StringBuffer bufferOut = new StringBuffer(preExeSql.length());
            StringBuffer bufferInner = new StringBuffer(preExeSql.length());
            StringBuffer bufferTotal = new StringBuffer(preExeSql.length());

            bufferTotal.
                    append("SELECT count(*) FROM ( ").
                    append(preExeSql).
                    append(" ) ");

            bufferInner.
                    append("SELECT tp1.* , rownum as rowN FROM ( ").
                    append(preExeSql).
                    append(" ) tp1 WHERE rownum <= ").
                    append(maxRow);
            bufferOut.
                    append("SELECT * FROM ( ").
                    append(bufferInner.toString()).
                    append(" ) WHERE rowN >= ").
                    append(minRow);

            greenCoffee.setSqlExe(bufferOut.toString());
            greenCoffee.setSqlExeCount(bufferTotal.toString());
        }

        result = jdbcTemplate.queryForList(greenCoffee.getSqlExe());
        if(greenCoffee.getSqlExeCount()!=null){
            Long size = jdbcTemplate.queryForObject(greenCoffee.getSqlExeCount(),Long.class);
            greenCoffee.setResultSize(size);
        }else{
            greenCoffee.setResultSize(result!=null?(long)result.size():0);
        }

        greenCoffee.setExtension(result);
        powerMapper.keepWarm(greenCoffee.toMap());
        return greenCoffee;
    }

    @Override
    public Connector getConnector(String Id) {
        return new DataBaseConnectorV2();
    }

    @Override
    public List<Source> getSources(String Id,List<Parameter> parameterList) {
        List<Source> sources = powerMapper.getSourcesByDatasetId(Id);
        List<Source> resultSources = new ArrayList<Source>(sources.size());
        if(sources==null || sources.size()<=0){
            return null;
        }
        for(Source currentSource : sources){
            String sourceId = currentSource.getID();
            List<Parameter> parameterMaps = powerMapper.getParameterMapsBySourceId(sourceId);
            for(Parameter parameter: parameterMaps){
                if(parameter.getParameterTypeString().equalsIgnoreCase(String.class.getSimpleName())){
                    parameter.setParameterType(String.class);
                }
            }
            List<Slot> sourceSlotsMap = powerMapper.getSlotsBySourceId(sourceId);
            Source source = SourceBuilder.buildSource(currentSource, parameterMaps, parameterList, sourceSlotsMap);
            source.setVirtualTable(getVirtualTable(source,parameterList));
            resultSources.add(source);
        }
        return resultSources;
    }

    @Override
    public List<FilterBean> getFilter(String dataSetId) {
        List<FilterBean> filterBeanList = powerMapper.getFilterByDataSetId(dataSetId);
        filterBeanList = AdapterBuilder.build(filterBeanList);
        return filterBeanList;
    }

    @Override
    public PowerSupply clone() {
        return this;
    }

    @Override
    public VirtualTable getVirtualTable(Source source,List<Parameter> parameterList) {
        VirtualTable result = null;
        if(source.getSourceType().equals(CoffeeRecipe.Source.DataBaseSource.type)){
            try{
                result = getDataBaseVirtualTable(source);
            }catch (Exception e){
                Log.error(String.format("指定的Source %s 无法获取虚表！", source.getID()), e);
                return null;
            }
        }
        return result;
    }

    public List<FieldObj> getFields(String dataSetID,String bind){
        Map parameter = new HashMap();
        parameter.put("dataSetId", Convert.toStr(dataSetID, ""));
        parameter.put("bind",Convert.toStr(bind,""));
        List<FieldObj> result = powerMapper.getFieldObjByDataSetId(parameter);
        Collections.sort(result, new Comparator<FieldObj>() {
            @Override
            public int compare(FieldObj o1, FieldObj o2) {
                return o1.getSort().intValue() - o2.getSort().intValue();
            }
        });
        return result;
    }

    public List<FieldObj> getFields(Map parameter){
        return powerMapper.getFieldObjByDataSetId(parameter);
    }

    private DataBaseVirtualTable getDataBaseVirtualTable(Source source) throws Exception{
        Connection connection = null;
        DataBaseVirtualTable resultTable = null;
        DataSource dataSource = jdbcTemplate.getDataSource();
        try{
            connection = DataSourceUtils.getConnection(dataSource);
            resultTable = (DataBaseVirtualTable) TableBuilder.buildTable(CoffeeRecipe.VirtualTable.TYPE_DATABASE_DYNAMIC, connection, source);
        }catch (Exception e){
        }finally {
            if(connection!=null){
                DataSourceUtils.releaseConnection(connection,dataSource);
            }
        }
        return resultTable;
    }
}