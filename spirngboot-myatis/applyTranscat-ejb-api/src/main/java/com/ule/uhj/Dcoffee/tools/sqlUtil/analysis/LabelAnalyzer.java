package com.ule.uhj.Dcoffee.tools.sqlUtil.analysis;

import com.ule.uhj.Dcoffee.object.CoffeeObject;
import com.ule.uhj.Dcoffee.object.coupling.conf.constant.CoffeeRecipe;
import com.ule.uhj.Dcoffee.object.model.inner.util.Parameter;
import com.ule.uhj.Dcoffee.tools.sqlUtil.Label;
import com.ule.uhj.Dcoffee.tools.sqlUtil.analysis.Labels.SimpleLable;
import com.ule.uhj.util.Convert;

import java.util.*;

public class LabelAnalyzer implements CoffeeObject{
    public static final String FLAG_START = "<%";
    public static final String FLAG_END = "%>";
    static final List<String> definetionLabelList = Collections.synchronizedList(new ArrayList<String>());
    static {
        definetionLabelList.add("IF");
    }

    public static boolean hasLabel(String str){
        return str.indexOf(FLAG_START)!=-1;
    }

    public static String analysis(String str, List<Parameter> parameterList) throws Exception{
        String executeStr = new String(str);
        Map<String,Object> resultMap = getAllLabel(executeStr);
        if(resultMap==null || resultMap.size()<=0){
            return str;
        }
        List<Label> existsLable = (List<Label>)resultMap.get("row");
        if(existsLable==null || existsLable.size()<=0){
            return str;
        }
        executeStr = Convert.toStr(resultMap.get("str"));
        for(Label label : existsLable){
            executeStr = executeStr.replace(label.getTag(),label.execute(parameterList));
        }
        return executeStr;
    }

    private static Map<String,Object> getAllLabel(String str) throws Exception{
        List<Label> resultList = new ArrayList<Label>();
        int startIndex = str.indexOf(FLAG_START);
        while(startIndex!=-1 && startIndex + FLAG_START.length() <str.length()){
            int endIndex = 0;
            for(int i = startIndex + FLAG_START.length();i<str.length();i++){
                if(i==str.length()-1){
                    break;
                }
                if(str.charAt(i)=='%' && str.charAt(i+1)=='>'){
                    endIndex = i+1;
                    break;
                }
            }

            if(endIndex==0){
                throw new Exception("Str="+str+"的标签不完整！startIndex="+startIndex);
            }
            if(endIndex+1>str.length()){
                throw  new Exception("不允许设置空标签！str="+str);
            }
            String lableDefined = str.substring(startIndex+FLAG_START.length(),endIndex-FLAG_END.length()+1);
            while(lableDefined.indexOf("  ")!=-1){
                lableDefined = lableDefined.replace("  "," ");
            }
            String lableDefinedArr[] = lableDefined.split(" ");

            SimpleLable lable = new SimpleLable();
            lable.setName(lableDefinedArr[0]);
            for(int i=1;i<lableDefinedArr.length;i++){
                String key = lableDefinedArr[i].substring(0,lableDefinedArr[i].indexOf("=")).trim();
                String value = lableDefinedArr[i].substring(lableDefinedArr[i].indexOf("=")+1,lableDefinedArr[i].length()).trim();
                if(value.startsWith(",")){
                    value = value.substring(1,value.length()-1);
                }
                lable.addDefined(key,value);
            }

            String labelEnd = FLAG_START+"/"+lable.getName()+FLAG_END;
            if(str.indexOf(labelEnd)!=-1){
                lable.setInnerContent(str.substring(endIndex+1,str.indexOf(labelEnd)));
            }else{
                throw new Exception("不允许设置空标签！str="+str);
            }
            if(definetionLabelList.contains(lable.getName().toUpperCase())){
                String className = lable.getName().toUpperCase()+"Label";
                Class targetClass = Class.forName(CoffeeRecipe.packageName+".tools.sqlUtil.analysis.Labels.impl."+className);
                SimpleLable targetLabel = (SimpleLable) targetClass.newInstance();
                lable.copy(targetLabel);

                StringBuffer nowStr  = new StringBuffer();
                nowStr.append(str.substring(0,startIndex))
                        .append(" ")
                        .append(targetLabel.getTag())
                        .append(" ")
                        .append(str.substring(str.indexOf(labelEnd)+labelEnd.length(),str.length()));
                str = nowStr.toString();
                resultList.add(targetLabel);
            }else{
                throw new Exception("找不到关键字:"+lable.getName());
            }

            startIndex = str.indexOf(FLAG_START);
        }

        Map<String,Object> responseMap = new HashMap<String,Object>();
        responseMap.put("str",str);
        responseMap.put("row",resultList);
        return responseMap;
    }

}