package com.ule.uhj.dto.zgd;

import com.ule.uhj.util.Convert;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PostMemberManageDtoV2 extends PostMemberManageDto {

    private String realNameStart;

    private String realNameEnd;

    private String isRealName;

    private String status;

    //是否过滤邮乐金融地推
    private boolean filterULE = true;

    public boolean isFilterULE() {
        return filterULE;
    }

    public void setFilterULE(boolean filterULE) {
        this.filterULE = filterULE;
    }

    public String getIsRealName() {
        return isRealName;
    }

    public void setIsRealName(String isRealName) {
        this.isRealName = isRealName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRealNameStart() {
        return realNameStart;
    }

    public void setRealNameStart(String realNameStart) {
        this.realNameStart = realNameStart;
    }

    public String getRealNameEnd() {
        return realNameEnd;
    }

    public void setRealNameEnd(String realNameEnd) {
        this.realNameEnd = realNameEnd;
    }

    public static PostMemberManageDtoV2 newinstance(Map dataMap){
        PostMemberManageDtoV2 cloneDto = new PostMemberManageDtoV2();
        Field[] fields = cloneDto.getClass().getDeclaredFields();
        Field[] fileds_parents = cloneDto.getClass().getSuperclass().getDeclaredFields();
        List<Field> fieldList = new ArrayList<>();
        fieldList.addAll(Arrays.asList(fields));
        fieldList.addAll(Arrays.asList(fileds_parents));
        for(Field field : fieldList){
            if(!Modifier.isStatic(field.getModifiers())){
                field.setAccessible(true);
                try{
                    Object fieldValue = dataMap.get(field.getName());
                    if(fieldValue!=null){
                        if(Number.class.isAssignableFrom(field.getType())){
                            field.set(cloneDto, Convert.toLong(fieldValue));
                        }else{
                            String FieldName = field.getName();
                            String MethodName = "set" + FieldName.substring(0,1).toUpperCase() + FieldName.substring(1,FieldName.length());
                            System.out.println(field.getType().getSimpleName());
                            if ( (fieldValue instanceof List) && (!List.class.isAssignableFrom(field.getType())) ) {
                                Method Setter = null;
                                try{
                                    Setter = cloneDto.getClass().getDeclaredMethod(MethodName,List.class);
                                }catch (NoSuchMethodException e){
                                    Setter = cloneDto.getClass().getSuperclass().getDeclaredMethod(MethodName,List.class);
                                }

                                Setter.setAccessible(true);
                                Setter.invoke(cloneDto,((List)(fieldValue)));
                            }else{
                                Method Setter = null;
                                try{
                                    Setter = cloneDto.getClass().getDeclaredMethod(MethodName,field.getType());
                                }catch (NoSuchMethodException e){
                                    Setter = cloneDto.getClass().getSuperclass().getDeclaredMethod(MethodName,field.getType());
                                }

                                Setter.setAccessible(true);
                                Setter.invoke(cloneDto,fieldValue);
                            }
                        }
                    }
                }catch (Exception e){
                    continue;
                }
            }
        }
        return cloneDto;
    }

    public PostMemberManageDtoV2 DtoClone(){
        PostMemberManageDtoV2 cloneDto = new PostMemberManageDtoV2();
        Field[] fields = cloneDto.getClass().getDeclaredFields();

        for(Field field : fields){
            if(!Modifier.isStatic(field.getModifiers())){
                field.setAccessible(true);
                try{
                    Object fieldValue = field.get(this);
                    if(fieldValue!=null){
                        if(Number.class.isAssignableFrom(field.getType())){
                            field.set(cloneDto, Convert.toLong(fieldValue));
                        }else{
                            field.set(cloneDto,fieldValue);
                        }
                    }
                }catch (Exception e){
                    continue;
                }
            }
        }
        return cloneDto;
    }
}
