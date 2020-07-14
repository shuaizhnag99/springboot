package com.ule.uhj.dto.zgd;

import com.ule.uhj.Annotation.ParameterDescripor;
import com.ule.uhj.Annotation.Transfer;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public interface TransferModel extends Descriptable{
    /***
     * 返回model类型，便于映射业务
     * @return
     */
    public default String getModelType(){
        Transfer transferModel = this.getClass().getAnnotation(Transfer.class);
        return transferModel.name();
    }

    /***
     * 设置指定索引的校验结果
     * @param index
     * @param result
     */
    void setModelCheckResult(String index, String result);

    /***
     * 获取指定索引的校验结果
     * @param index
     * @return
     */
    String getModelcheckResult(String index);

    /***
     * 手动索引方式设置model，方便使用native sql的情景可以直接生成model
     * 不推荐该方法，尽量使用另一个使用注解的方式设置model
     * @param data
     * @param indexs
     * @throws Exception
     */
    public default void setModel(Object data, String[] indexs) throws Exception{
        List dataList;
        if(data == null){
            throw new Exception("数据为空，不能赋值给model！");
        }
        if(data instanceof Array){
            dataList = Arrays.asList(data);
        }else if (data instanceof List){
            dataList = (List)data;
        }else{
            throw new Exception("数据类型非法，数据必须为线性类型(list 或 数组)！");
        }

        Field[] myFields = this.getClass().getDeclaredFields();

        for(int i = 0;i!=indexs.length;++i){
            Field targetField = null;
            String needIndex = null;
            for(Field myfield : myFields){
                myfield.setAccessible(true);
                ParameterDescripor parameterDescripor = myfield.getAnnotation(ParameterDescripor.class);
                if(parameterDescripor.Index().equals(indexs[i])){
                    targetField = myfield;
                    needIndex = parameterDescripor.Index();
                    break;
                }
            }
            if(targetField == null){
                continue;
            }
            if(i > dataList.size()){
                throw new Exception("指定索引的长度不能超过实际长度！");
            }

            Object value = dataList.get(i);
            if(!(targetField.getType().equals(value.getClass())) && !(value.getClass().isAssignableFrom(targetField.getType()))){
                throw new Exception(this.getClass().getSimpleName()+"-"+needIndex+"所需的类型为"+targetField.getType().getName()+",目标实参的类型"+value.getClass().getName()+"不能匹配！");
            }

            targetField.set(this, value);
        }

    }

    /***
     * 注解方式设置model，推荐使用该方式来设置model
     * @param descriptable
     * @throws Exception
     */
    public default <T extends TransferModel> void setModel(T descriptable) throws Exception{
        Map<String, String> PDT = this.getParameterDescriptorTable();
        Field[] myFields = this.getClass().getDeclaredFields();
        Field[] dataFields = descriptable.getClass().getDeclaredFields();
        for(String index : PDT.keySet()){
            boolean hasMy = false, hasData = false;
            Field myfield = null, datafield = null;
            int size_my = myFields.length, size_d = dataFields.length;
            int l_t = 0, l_d = 0;
            while( (!hasMy || !hasData) && l_t<size_my && l_d < size_d ){
                if(!hasMy && l_t<myFields.length){
                    myFields[l_t].setAccessible(true);
                    ParameterDescripor parameterDescripor = myFields[l_t].getAnnotation(ParameterDescripor.class);
                    if(parameterDescripor==null){
                        ++l_t;
                        continue;
                    }
                    if(parameterDescripor.Index().equals(index)){
                        myfield = myFields[l_t];
                        hasMy = true;
                    }
                    ++l_t;
                }
                if(!hasData && l_d<dataFields.length){
                    dataFields[l_d].setAccessible(true);
                    ParameterDescripor parameterDescripor = dataFields[l_d].getAnnotation(ParameterDescripor.class);
                    if(parameterDescripor==null){
                        ++l_d;
                        continue;
                    }
                    if(parameterDescripor.Index().equals(index)){
                        datafield = dataFields[l_d];
                        hasData = true;
                    }
                    ++l_d;
                }
            }
            if(myfield == null || datafield ==null){
                continue;
            }
            if( !(myfield.getType().equals(datafield.getType())) && !(datafield.getType().isAssignableFrom(myfield.getType()))){
                throw new Exception(this.getClass().getSimpleName()+"-"+index+"所需的类型为"+myfield.getType().getName()+",目标实参的类型"+datafield.getType().getName()+"不能匹配！");
            }
            myfield.setAccessible(true);
            datafield.setAccessible(true);
            myfield.set(this, datafield.get(descriptable));
        }
    }
}
