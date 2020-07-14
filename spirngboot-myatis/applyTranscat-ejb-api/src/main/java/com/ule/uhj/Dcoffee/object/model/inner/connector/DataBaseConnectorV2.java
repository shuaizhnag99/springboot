package com.ule.uhj.Dcoffee.object.model.inner.connector;

import com.ule.uhj.Dcoffee.core.annotation.Dcoffee;
import com.ule.uhj.Dcoffee.object.coupling.conf.constant.CoffeeRecipe;
import com.ule.uhj.Dcoffee.object.coupling.exception.MapDisconnectedException;
import com.ule.uhj.Dcoffee.object.model.inner.source.DefaultSource;
import com.ule.uhj.Dcoffee.object.model.inner.source.Source;
import com.ule.uhj.Dcoffee.object.model.inner.util.Log;
import com.ule.uhj.Dcoffee.object.model.inner.util.Parameter;
import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.Column;
import com.ule.uhj.Dcoffee.object.model.inner.virtualTable.VirtualTable;
import com.ule.uhj.Dcoffee.object.model.interaction.coffee.BadCoffee;
import com.ule.uhj.Dcoffee.object.model.interaction.coffee.Coffee;
import com.ule.uhj.Dcoffee.tools.StringUtils;
import com.ule.uhj.Dcoffee.tools.inner.DateUtil;
import com.ule.uhj.Dcoffee.tools.sqlUtil.SqlUtil;
import com.ule.uhj.Dcoffee.tools.sqlUtil.SqlUtilConstant;
import com.ule.uhj.Dcoffee.tools.sqlUtil.analysis.LabelAnalyzer;

import java.util.*;

/**
 * Created by zhengxin on 2018/3/28.
 */
@Dcoffee(name="DataBaseConnectorV2")
public class DataBaseConnectorV2 implements Connector {

    private StringBuffer resultBuffer = new StringBuffer(1024);
    private List<Source> sourceList = new ArrayList<Source>();
    private List<Parameter> mSortParameterList = new ArrayList<Parameter>();
    private SourceLinkList sourceLinkList;

    private BadCoffee coffee = new BadCoffee();

    @Override
    public Coffee connect(Collection<? extends Source> iterable) throws MapDisconnectedException {
        if(!(iterable instanceof List)){
            return null;
        }

        sourceList = (List)iterable;
        Collections.sort(sourceList, new Comparator<Source>() {
            @Override
            public int compare(Source o1, Source o2) {
                DefaultSource defaultSource1 = (DefaultSource)o1;
                DefaultSource defaultSource2 = (DefaultSource)o2;
                return defaultSource1.getSourceLevel() - defaultSource2.getSourceLevel();
            }
        });

        createLink();
        start();

        coffee.setSqlExe(resultBuffer.toString());
        coffee.setCreateTime(DateUtil.currentTime());
        coffee.setExeParameterList(mSortParameterList);
        return coffee;
    }

    private void start(){
        StringBuffer columnBuffer = new StringBuffer(256);
        StringBuffer tableBuffer = new StringBuffer(768);
        while(sourceLinkList!=null){
            if(sourceLinkList.isAccess()){
                sourceLinkList=sourceLinkList.getNext();
                continue;
            }

            sourceLinkList.setAccess(true);

            setupColumn((DefaultSource)sourceLinkList.getValue(),sourceLinkList.getNo(),columnBuffer);

            if(tableBuffer.length() >0 && sourceLinkList.getPrev()!=null){
                SourceLinkList prev = sourceLinkList.getPrev();
                tableBuffer.append(setupSlot(prev, sourceLinkList));
            }else{
                tableBuffer.append(setupTable((DefaultSource)sourceLinkList.getValue(),sourceLinkList.getNo()));
                sourceLinkList.setAccess(true);
            }

            sourceLinkList = sourceLinkList.getNext();
            continue;
        }
        resultBuffer.append(columnBuffer.toString()).append(SqlUtilConstant.FROM).append(tableBuffer.toString());
    }

    private String setupTable(DefaultSource source,int no){
        StringBuffer setupBuffer = new StringBuffer(128);
        String prexecuteSql = source.getPrepareSql();
        if(prexecuteSql.charAt(prexecuteSql.length()-1)==';'){
            prexecuteSql = prexecuteSql.substring(0,prexecuteSql.length()-1);
        }
        setupBuffer.append(SqlUtilConstant.LBRACKETS).
                append(checkParamer(prexecuteSql,source.getParameterList(),mSortParameterList,null)).
                append(SqlUtilConstant.RBRACKETS).
                append(CoffeeRecipe.Source.DataBaseSource.alias).
                append(Integer.toString(no)).
                append(SqlUtilConstant.TRIM);
        return setupBuffer.toString();
    }

    private void setupColumn(DefaultSource source,int no,StringBuffer buffer){
        if(buffer.length()<=0){
            buffer.append(SqlUtilConstant.SELECT);
        }
        VirtualTable table = source.getVirtualTable();
        List<Column> columns  = (List<Column>)table.getColumns();
        for(int i = 0;i<columns.size();i++){
            Column column = columns.get(i);
            if(!(buffer.charAt(buffer.length()-1)==SqlUtilConstant.COMMA.charAt(0)) && buffer.length()>SqlUtilConstant.SELECT.length()){
                buffer.append(SqlUtilConstant.COMMA);
            }
            buffer.append(CoffeeRecipe.Source.DataBaseSource.alias).
                    append(Integer.toString(no)).
                    append(SqlUtilConstant.PERIOD).
                    append(column.getColumnName()).
                    append(SqlUtilConstant.TRIM);
        }
    }

    private String setupSlot(SourceLinkList prev,SourceLinkList next){
        StringBuffer buffer = new StringBuffer(128);
        DefaultSource prevSource = (DefaultSource)prev.getValue();
        DefaultSource nextSource = (DefaultSource)next.getValue();
        List<Slot> prevSourceSlotList = prevSource.getSlots();
        List<Slot> nextSourceSlotList = nextSource.getSlots();

        for(Slot currentPrevSlot : prevSourceSlotList){
            for(Slot currentNextSlot : nextSourceSlotList){
                if(currentNextSlot.equals(currentPrevSlot)){
                    DefaultSlot currentPrevDefaultSlot = (DefaultSlot)currentPrevSlot;
                    DefaultSlot currentNextDefaultSlot = (DefaultSlot)currentNextSlot;
                    if(buffer.length()<=0){
                        buffer.append(currentPrevDefaultSlot.getSlotType()).
                                append(SqlUtilConstant.TRIM).
                                append(setupTable(nextSource,next.getNo())).
                                append(SqlUtilConstant.TRIM).
                                append(SqlUtilConstant.ON).
                                append(CoffeeRecipe.Source.DataBaseSource.alias).append(Integer.toString(prev.getNo())).
                                append(SqlUtilConstant.PERIOD).
                                append(currentPrevDefaultSlot.getMapName()).
                                append(SqlUtilConstant.EQUAL).
                                append(CoffeeRecipe.Source.DataBaseSource.alias).append(Integer.toString(next.getNo())).
                                append(SqlUtilConstant.PERIOD).
                                append(currentNextDefaultSlot.getMapName()).
                                append(SqlUtilConstant.TRIM);
                    }else{
                        buffer.append(SqlUtilConstant.AND).
                                append(CoffeeRecipe.Source.DataBaseSource.alias).append(Integer.toString(prev.getNo())).
                                append(SqlUtilConstant.PERIOD).
                                append(currentPrevDefaultSlot.getMapName()).
                                append(SqlUtilConstant.EQUAL).
                                append(CoffeeRecipe.Source.DataBaseSource.alias).append(Integer.toString(next.getNo())).
                                append(SqlUtilConstant.PERIOD).
                                append(currentNextDefaultSlot.getMapName()).
                                append(SqlUtilConstant.TRIM);
                    }
                    break;
                }
            }
        }
        return buffer.toString();
    }

    public static String checkParamer(String preExecuteSql ,List<Parameter> parameterList){
        if(LabelAnalyzer.hasLabel(preExecuteSql)){
            try{
                preExecuteSql = LabelAnalyzer.analysis(preExecuteSql,parameterList);
            }catch (Exception e){
//                e.printStackTrace();
                return null;
            }
        }

        String startStr = SqlUtilConstant.LSQUARE + SqlUtilConstant.AT;
        String endStr = SqlUtilConstant.AT + SqlUtilConstant.RSQUARE;
        String orginSrcCopy = new String(preExecuteSql.getBytes());

        int start = preExecuteSql.indexOf(startStr);
        while(start>0){
            int end = preExecuteSql.indexOf(endStr);
            String section = preExecuteSql.substring(start+startStr.length(),end);
            for(Parameter parameter : parameterList){
                if(parameter.getParameterName().equals(section)){
                    if(parameter.getParameterValue() == null || StringUtils.isBlank(parameter.getParameterValue().toString())){
                        int originSrcCopyEnd = orginSrcCopy.indexOf(endStr);
                        orginSrcCopy = deleteSqlParamer(orginSrcCopy, originSrcCopyEnd + endStr.length() - 1);
                        preExecuteSql = preExecuteSql.substring(end + endStr.length());
                        continue;
                    }else{
                        orginSrcCopy = orginSrcCopy.replace(startStr+section+endStr,parameter.getParameterValue().toString());
                        preExecuteSql = preExecuteSql.substring(end + endStr.length());
                        break;
                    }
                }
            }
            start = preExecuteSql.indexOf(startStr);
        }

        return orginSrcCopy;
    }

    public static String checkParamer(String preExecuteSql ,List<Parameter> parameterList,List<Parameter> sortParameterList,String special){
        if(LabelAnalyzer.hasLabel(preExecuteSql)){
            try{
                preExecuteSql = LabelAnalyzer.analysis(preExecuteSql,parameterList);
            }catch (Exception e){
//                e.printStackTrace();
                return null;
            }
        }

        String startStr = SqlUtilConstant.LSQUARE + SqlUtilConstant.AT;
        String endStr = SqlUtilConstant.AT + SqlUtilConstant.RSQUARE;
        String orginSrcCopy = new String(preExecuteSql.getBytes());

        int start = preExecuteSql.indexOf(startStr);
        while(start>0){
            int end = preExecuteSql.indexOf(endStr);
            String section = preExecuteSql.substring(start+startStr.length(),end);
            for(Parameter parameter : parameterList){
                if(parameter.getParameterName().equals(section)){
                    if(parameter.getParameterValue() == null || StringUtils.isBlank(parameter.getParameterValue().toString())){
                        int originSrcCopyEnd = orginSrcCopy.indexOf(endStr);
                        orginSrcCopy = deleteSqlParamer(orginSrcCopy, originSrcCopyEnd + endStr.length() - 1);
                        preExecuteSql = preExecuteSql.substring(end + endStr.length());
                        continue;
                    }else{
                        sortParameterList.add(parameter);
                        if(StringUtils.isBlank(special)){
                            orginSrcCopy = orginSrcCopy.replace("\'"+startStr+section+endStr+"\'",":"+parameter.getParameterName().toString());
                            orginSrcCopy = orginSrcCopy.replace(startStr+section+endStr,":"+parameter.getParameterName().toString());
                        }else{
                            orginSrcCopy = orginSrcCopy.replace("\'"+startStr+section+endStr+"\'",special);
                            orginSrcCopy = orginSrcCopy.replace(startStr+section+endStr,special);
                        }
                        preExecuteSql = preExecuteSql.substring(end + endStr.length());
                        break;
                    }
                }
            }
            start = preExecuteSql.indexOf(startStr);
        }

        return orginSrcCopy;
    }

    private static int getRealStart(int startStr,String context){
        int i = startStr;
        char tc;
        while(i>0){
            tc = context.charAt(i);
            if(tc==' '){
                i--;
                continue;
            }else{
                return tc=='\''?i:startStr;
            }
        }
        return startStr;
    }

    private static int getRealEnd(int endStr,String context){
        int i = endStr;
        char tc;
        while(i<context.length()){
            tc = context.charAt(i);
            if(tc==' '){
                i++;
                continue;
            }else{
                return tc=='\''?i:endStr;
            }
        }
        return endStr;
    }

    public List<Parameter> getSortParameterList() {
        return mSortParameterList;
    }

    private static String deleteSqlParamer(String originSql,int endIndex){
        StringBuffer sql = new StringBuffer();
        sql.append(originSql);
        StringBuffer result = new StringBuffer(sql.length());
        String[] words = new String[]{
          SqlUtilConstant.WHERE.trim(), SqlUtilConstant.AND.trim(), SqlUtilConstant.OR.trim()
        };
        String[] del = new String[]{
                SqlUtilConstant.COMMA.trim(),
                SqlUtilConstant.PERIOD.trim(),
                SqlUtilConstant.QUICK_MARK.trim(),
                SqlUtilConstant.TRIM,
                SqlUtilConstant.HUAND
        };
        int originEnd = endIndex+1;
        boolean originEndisRealend = false;

        outflag :
        while(!originEndisRealend){
            for(int i = 0;i<del.length;i++){
                if(originEnd<sql.length() && sql.charAt(originEnd)==del[i].charAt(0)){
                    originEnd++;
                    continue outflag;
                }
            }
            originEndisRealend = true;
        }

        while(endIndex > SqlUtilConstant.SELECT.length()){
            for(int i=0;i<words.length;i++){
                String currentWord = words[i];
                int wordHead = endIndex - currentWord.length() - 1,wordTail = endIndex;
                boolean isRealHit =
                        (sql.charAt(wordHead) == ' ' || sql.charAt(wordHead) == '\n') &&
                        (sql.charAt(wordTail) == ' ' || sql.charAt(wordTail) == '\n');
                if(sql.substring(endIndex-currentWord.length(),endIndex).equalsIgnoreCase(currentWord) && isRealHit){
                    int startIndex = endIndex - currentWord.length();
                    result.append(sql.substring(0,startIndex)).append(SqlUtilConstant.TRIM);
                    if(originEnd<sql.length()){
                        result.append(sql.substring(originEnd,sql.length()));
                    }
                    return result.toString();
                }
            }
            endIndex--;
        }
        return sql.toString();
    }


    private void createLink(){
        sourceLinkList = new SourceLinkList(sourceList.get(0));
        SourceLinkList topList = sourceLinkList;
        if(sourceList.size()>1){
            for(int i = 1;i<sourceList.size();i++){
                SourceLinkList sourceLinkList = new SourceLinkList(sourceList.get(i));
                sourceLinkList.setNo(i);
                sourceLinkList.setPrev(topList);
                topList.setNext(sourceLinkList);
                topList = sourceLinkList;
            }
        }
    }

    private class SourceLinkList {
        private boolean isAccess = false;
        private Source value;
        private SourceLinkList next;
        private SourceLinkList prev;
        private int no;

        public SourceLinkList(Source value) {
            this.value = value;
        }

        public boolean isAccess() {
            return isAccess;
        }

        public void setAccess(boolean isAccess) {
            this.isAccess = isAccess;
        }

        public Source getValue() {
            return value;
        }

        public void setValue(Source value) {
            this.value = value;
        }

        public SourceLinkList getNext() {
            return next;
        }

        public void setNext(SourceLinkList next) {
            this.next = next;
        }

        public SourceLinkList getPrev() {
            return prev;
        }

        public void setPrev(SourceLinkList prev) {
            this.prev = prev;
        }

        public int getNo() {
            return no;
        }

        public void setNo(int no) {
            this.no = no;
        }
    }
}
