package com.ule.uhj.Dcoffee.tools.sqlUtil.analysis;

import com.ule.uhj.Dcoffee.tools.StringUtils;
import com.ule.uhj.Dcoffee.tools.sqlUtil.SqlUtilConstant;

/**
 * Created by zhengxin on 2018/3/26.
 */
public class WordHelper {
    private static String[] ssWords = new String[]{
            SqlUtilConstant.SELECT.trim()
    };

    private static String[] seWords = new String[]{
            SqlUtilConstant.FROM.trim()
    };

    private static String[] fsWords = new String[]{
            SqlUtilConstant.FROM.trim()
    };

    private static String[] feWords = new String[]{
            SqlUtilConstant.WHERE.trim(),
            SqlUtilConstant.SQL_END.trim()
    };

    private static String[] wsWords = new String[]{
            SqlUtilConstant.WHERE.trim()
    };
    private static String[] weWords = new String[]{
            SqlUtilConstant.SQL_END.trim()
    };

    private static String[] functionWords = new String[]{
            "count","sum"
    };
    private static Class[] functionType = new Class[]{
            Integer.class,Integer.class
    };

    private static String[] emWords = new String[]{
            ")","(",""," ","in","left","right","inner","join","case","when","in","group","as","and","between","by","desc","order","translate","trim"
    };

    private static String[] mergeRemoveWords = new String[]{
            "as"
    };

    public static boolean isSS(Word word){
        return isHas(word,ssWords);
    }

    public static boolean isSE(Word word){
        return isHas(word,seWords);
    }

    public static boolean isFS(Word word){
        return isHas(word,fsWords);
    }

    public static boolean isFE(Word word){
        return isHas(word,feWords);
    }

    public static boolean isWS(Word word){
        return isHas(word,wsWords);
    }

    public static boolean isWE(Word word){
        return isHas(word,weWords);
    }

    public static boolean isEM(Word word){
        if(isHas(word,emWords) || word.getValue().contains(SqlUtilConstant.QUICK_MARK)){
            return true;
        }
        return StringUtils.isNumeric(word.getValue());
    }

    public static String hasFunction(Word word){
        return has(word,functionWords);
    }

    public static String getFunctionParameter(Word word){
        if(hasFunction(word)!=null){
            String wordString = word.getValue();
            while(wordString.contains(SqlUtilConstant.LBRACKETS) && wordString.contains(SqlUtilConstant.RBRACKETS)){
                int start = wordString.indexOf(SqlUtilConstant.LBRACKETS);
                int end = wordString.indexOf(SqlUtilConstant.RBRACKETS);
                wordString = wordString.substring(start+1,end);
            }
            return wordString;
        }
        return null;
    }

    public static Class getFunctionType(String function){
        for(int i = 0;i<functionWords.length;i++){
            if(functionWords[i].equals(function)){
                return functionType[i];
            }
        }
        return null;
    }

    public static boolean isME(Word word){
        return isHas(word,mergeRemoveWords);
    }

    public static boolean isTable(Word word){
        return word.getValue().toUpperCase().contains(SqlUtilConstant.SCHEMAL.toUpperCase());
    }

    public static boolean isColumn(Word word){
        boolean result = ( word.getValue().contains(SqlUtilConstant.PERIOD));
        if(result){
            Word testWord = word.getPrev();
            while(testWord!=null){
                String testValue = testWord.getValue().trim();
                if(testValue.equalsIgnoreCase(SqlUtilConstant.FROM.trim())){
                    return false;
                }
                if(testValue.equalsIgnoreCase(SqlUtilConstant.WHERE.trim()) || testValue.equalsIgnoreCase(SqlUtilConstant.SELECT.trim())){
                    return true;
                }
                testWord = testWord.getPrev();
            }
        }
        return result;
    }

    public static boolean isNM(Word word){
        return isTable(word) || isColumn(word);
    }

    private static boolean isHas(Word word,String[] words){
        for(String currentStr : words){
            if(word.getValue().trim().equalsIgnoreCase(currentStr.trim())){
                return true;
            }
        }
        return false;
    }

    private static String has(Word word,String[] words){
        String testStr = word.getValue();
        if(!testStr.contains(SqlUtilConstant.LBRACKETS) || !testStr.contains(SqlUtilConstant.RBRACKETS)){
            return null;
        }

        int testStrStart = word.getValue().trim().indexOf(SqlUtilConstant.LBRACKETS);

        if(testStrStart<0){
            return null;
        }

        testStr = word.getValue().substring(0,testStrStart);

        for(String currentStr : words){
            if(testStr.toUpperCase().contains(currentStr.trim().toUpperCase())){
                return currentStr;
            }
        }
        return null;
    }
}
