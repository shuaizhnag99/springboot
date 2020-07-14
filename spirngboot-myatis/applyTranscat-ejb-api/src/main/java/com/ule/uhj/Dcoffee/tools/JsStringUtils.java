package com.ule.uhj.Dcoffee.tools;

import org.apache.commons.lang.*;

import java.util.*;

/**
 * Created by zhengxin on 2018/7/26.
 */
public class JsStringUtils {
    public static final String EMPTY = "";
    public static final int INDEX_NOT_FOUND = -1;
    private static final int PAD_LIMIT = 8192;

    public JsStringUtils() {
    }

    public  String deleteSqlParamer(String executeSql,int endIndex){
        int delWords = 0,index = endIndex;
        StringBuffer buffer = new StringBuffer(executeSql.length());
        while(index>=0){
            if(delWords>0 && executeSql.charAt(index)==' ' && !(executeSql.charAt(index+1)=='=')){
                break;
            }
            if(executeSql.charAt(index)=='='){
                delWords++;
            }
            index--;
        }
        buffer.append(executeSql.substring(0,index+1)).append(executeSql.substring(endIndex+1,executeSql.length()));
        return buffer.toString();
    }

    public  boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public  boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public  boolean isBlank(String str) {
        int strLen;
        if(str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if(!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public  boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /** @deprecated */
    public  String clean(String str) {
        return str == null?"":str.trim();
    }

    public  String trim(String str) {
        return str == null?null:str.trim();
    }

    public  String trimToNull(String str) {
        String ts = trim(str);
        return isEmpty(ts)?null:ts;
    }

    public  String trimToEmpty(String str) {
        return str == null?"":str.trim();
    }

    public  String strip(String str) {
        return strip(str, (String)null);
    }

    public  String stripToNull(String str) {
        if(str == null) {
            return null;
        } else {
            str = strip(str, (String)null);
            return str.length() == 0?null:str;
        }
    }

    public  String stripToEmpty(String str) {
        return str == null?"":strip(str, (String)null);
    }

    public  String strip(String str, String stripChars) {
        if(isEmpty(str)) {
            return str;
        } else {
            str = stripStart(str, stripChars);
            return stripEnd(str, stripChars);
        }
    }

    public  String stripStart(String str, String stripChars) {
        int strLen;
        if(str != null && (strLen = str.length()) != 0) {
            int start = 0;
            if(stripChars == null) {
                while(start != strLen && Character.isWhitespace(str.charAt(start))) {
                    ++start;
                }
            } else {
                if(stripChars.length() == 0) {
                    return str;
                }

                while(start != strLen && stripChars.indexOf(str.charAt(start)) != -1) {
                    ++start;
                }
            }

            return str.substring(start);
        } else {
            return str;
        }
    }

    public  String stripEnd(String str, String stripChars) {
        int end;
        if(str != null && (end = str.length()) != 0) {
            if(stripChars == null) {
                while(end != 0 && Character.isWhitespace(str.charAt(end - 1))) {
                    --end;
                }
            } else {
                if(stripChars.length() == 0) {
                    return str;
                }

                while(end != 0 && stripChars.indexOf(str.charAt(end - 1)) != -1) {
                    --end;
                }
            }

            return str.substring(0, end);
        } else {
            return str;
        }
    }

    public  String[] stripAll(String[] strs) {
        return stripAll(strs, (String)null);
    }

    public  String[] stripAll(String[] strs, String stripChars) {
        int strsLen;
        if(strs != null && (strsLen = strs.length) != 0) {
            String[] newArr = new String[strsLen];

            for(int i = 0; i < strsLen; ++i) {
                newArr[i] = strip(strs[i], stripChars);
            }

            return newArr;
        } else {
            return strs;
        }
    }

    public  boolean equals(String str1, String str2) {
        return str1 == null?str2 == null:str1.equals(str2);
    }

    public  boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null?str2 == null:str1.equalsIgnoreCase(str2);
    }

    public  int indexOf(String str, char searchChar) {
        return isEmpty(str)?-1:str.indexOf(searchChar);
    }

    public  int indexOf(String str, char searchChar, int startPos) {
        return isEmpty(str)?-1:str.indexOf(searchChar, startPos);
    }

    public  int indexOf(String str, String searchStr) {
        return str != null && searchStr != null?str.indexOf(searchStr):-1;
    }

    public  int ordinalIndexOf(String str, String searchStr, int ordinal) {
        return ordinalIndexOf(str, searchStr, ordinal, false);
    }

    private  int ordinalIndexOf(String str, String searchStr, int ordinal, boolean lastIndex) {
        if(str != null && searchStr != null && ordinal > 0) {
            if(searchStr.length() == 0) {
                return lastIndex?str.length():0;
            } else {
                int found = 0;
                int index = lastIndex?str.length():-1;

                do {
                    if(lastIndex) {
                        index = str.lastIndexOf(searchStr, index - 1);
                    } else {
                        index = str.indexOf(searchStr, index + 1);
                    }

                    if(index < 0) {
                        return index;
                    }

                    ++found;
                } while(found < ordinal);

                return index;
            }
        } else {
            return -1;
        }
    }

    public  int indexOf(String str, String searchStr, int startPos) {
        return str != null && searchStr != null?(searchStr.length() == 0 && startPos >= str.length()?str.length():str.indexOf(searchStr, startPos)):-1;
    }

    public  int indexOfIgnoreCase(String str, String searchStr) {
        return indexOfIgnoreCase(str, searchStr, 0);
    }

    public  int indexOfIgnoreCase(String str, String searchStr, int startPos) {
        if(str != null && searchStr != null) {
            if(startPos < 0) {
                startPos = 0;
            }

            int endLimit = str.length() - searchStr.length() + 1;
            if(startPos > endLimit) {
                return -1;
            } else if(searchStr.length() == 0) {
                return startPos;
            } else {
                for(int i = startPos; i < endLimit; ++i) {
                    if(str.regionMatches(true, i, searchStr, 0, searchStr.length())) {
                        return i;
                    }
                }

                return -1;
            }
        } else {
            return -1;
        }
    }

    public  int lastIndexOf(String str, char searchChar) {
        return isEmpty(str)?-1:str.lastIndexOf(searchChar);
    }

    public  int lastIndexOf(String str, char searchChar, int startPos) {
        return isEmpty(str)?-1:str.lastIndexOf(searchChar, startPos);
    }

    public  int lastIndexOf(String str, String searchStr) {
        return str != null && searchStr != null?str.lastIndexOf(searchStr):-1;
    }

    public  int lastOrdinalIndexOf(String str, String searchStr, int ordinal) {
        return ordinalIndexOf(str, searchStr, ordinal, true);
    }

    public  int lastIndexOf(String str, String searchStr, int startPos) {
        return str != null && searchStr != null?str.lastIndexOf(searchStr, startPos):-1;
    }

    public  int lastIndexOfIgnoreCase(String str, String searchStr) {
        return str != null && searchStr != null?lastIndexOfIgnoreCase(str, searchStr, str.length()):-1;
    }

    public  int lastIndexOfIgnoreCase(String str, String searchStr, int startPos) {
        if(str != null && searchStr != null) {
            if(startPos > str.length() - searchStr.length()) {
                startPos = str.length() - searchStr.length();
            }

            if(startPos < 0) {
                return -1;
            } else if(searchStr.length() == 0) {
                return startPos;
            } else {
                for(int i = startPos; i >= 0; --i) {
                    if(str.regionMatches(true, i, searchStr, 0, searchStr.length())) {
                        return i;
                    }
                }

                return -1;
            }
        } else {
            return -1;
        }
    }

    public  boolean contains(String str, char searchChar) {
        return isEmpty(str)?false:str.indexOf(searchChar) >= 0;
    }

    public  boolean contains(String str, String searchStr) {
        return str != null && searchStr != null?str.indexOf(searchStr) >= 0:false;
    }

    public  boolean containsIgnoreCase(String str, String searchStr) {
        if(str != null && searchStr != null) {
            int len = searchStr.length();
            int max = str.length() - len;

            for(int i = 0; i <= max; ++i) {
                if(str.regionMatches(true, i, searchStr, 0, len)) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public  int indexOfAny(String str, char[] searchChars) {
        if(!isEmpty(str) && !ArrayUtils.isEmpty(searchChars)) {
            for(int i = 0; i < str.length(); ++i) {
                char ch = str.charAt(i);

                for(int j = 0; j < searchChars.length; ++j) {
                    if(searchChars[j] == ch) {
                        return i;
                    }
                }
            }

            return -1;
        } else {
            return -1;
        }
    }

    public  int indexOfAny(String str, String searchChars) {
        return !isEmpty(str) && !isEmpty(searchChars)?indexOfAny(str, (char[])searchChars.toCharArray()):-1;
    }

    public  boolean containsAny(String str, char[] searchChars) {
        if(str != null && str.length() != 0 && searchChars != null && searchChars.length != 0) {
            for(int i = 0; i < str.length(); ++i) {
                char ch = str.charAt(i);

                for(int j = 0; j < searchChars.length; ++j) {
                    if(searchChars[j] == ch) {
                        return true;
                    }
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public  boolean containsAny(String str, String searchChars) {
        return searchChars == null?false:containsAny(str, (char[])searchChars.toCharArray());
    }

    public  int indexOfAnyBut(String str, char[] searchChars) {
        if(!isEmpty(str) && !ArrayUtils.isEmpty(searchChars)) {
            int i = 0;

            label26:
            while(i < str.length()) {
                char ch = str.charAt(i);

                for(int j = 0; j < searchChars.length; ++j) {
                    if(searchChars[j] == ch) {
                        ++i;
                        continue label26;
                    }
                }

                return i;
            }

            return -1;
        } else {
            return -1;
        }
    }

    public  int indexOfAnyBut(String str, String searchChars) {
        if(!isEmpty(str) && !isEmpty(searchChars)) {
            for(int i = 0; i < str.length(); ++i) {
                if(searchChars.indexOf(str.charAt(i)) < 0) {
                    return i;
                }
            }

            return -1;
        } else {
            return -1;
        }
    }

    public  boolean containsOnly(String str, char[] valid) {
        return valid != null && str != null?(str.length() == 0?true:(valid.length == 0?false:indexOfAnyBut(str, (char[])valid) == -1)):false;
    }

    public  boolean containsOnly(String str, String validChars) {
        return str != null && validChars != null?containsOnly(str, (char[])validChars.toCharArray()):false;
    }

    public  boolean containsNone(String str, char[] invalidChars) {
        if(str != null && invalidChars != null) {
            int strSize = str.length();
            int validSize = invalidChars.length;

            for(int i = 0; i < strSize; ++i) {
                char ch = str.charAt(i);

                for(int j = 0; j < validSize; ++j) {
                    if(invalidChars[j] == ch) {
                        return false;
                    }
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public  boolean containsNone(String str, String invalidChars) {
        return str != null && invalidChars != null?containsNone(str, (char[])invalidChars.toCharArray()):true;
    }

    public  int indexOfAny(String str, String[] searchStrs) {
        if(str != null && searchStrs != null) {
            int sz = searchStrs.length;
            int ret = 2147483647;
            boolean tmp = false;

            for(int i = 0; i < sz; ++i) {
                String search = searchStrs[i];
                if(search != null) {
                    int var7 = str.indexOf(search);
                    if(var7 != -1 && var7 < ret) {
                        ret = var7;
                    }
                }
            }

            return ret == 2147483647?-1:ret;
        } else {
            return -1;
        }
    }

    public  int lastIndexOfAny(String str, String[] searchStrs) {
        if(str != null && searchStrs != null) {
            int sz = searchStrs.length;
            int ret = -1;
            boolean tmp = false;

            for(int i = 0; i < sz; ++i) {
                String search = searchStrs[i];
                if(search != null) {
                    int var7 = str.lastIndexOf(search);
                    if(var7 > ret) {
                        ret = var7;
                    }
                }
            }

            return ret;
        } else {
            return -1;
        }
    }

    public  String substring(String str, int start) {
        if(str == null) {
            return null;
        } else {
            if(start < 0) {
                start += str.length();
            }

            if(start < 0) {
                start = 0;
            }

            return start > str.length()?"":str.substring(start);
        }
    }

    public  String substring(String str, int start, int end) {
        if(str == null) {
            return null;
        } else {
            if(end < 0) {
                end += str.length();
            }

            if(start < 0) {
                start += str.length();
            }

            if(end > str.length()) {
                end = str.length();
            }

            if(start > end) {
                return "";
            } else {
                if(start < 0) {
                    start = 0;
                }

                if(end < 0) {
                    end = 0;
                }

                return str.substring(start, end);
            }
        }
    }

    public  String left(String str, int len) {
        return str == null?null:(len < 0?"":(str.length() <= len?str:str.substring(0, len)));
    }

    public  String right(String str, int len) {
        return str == null?null:(len < 0?"":(str.length() <= len?str:str.substring(str.length() - len)));
    }

    public  String mid(String str, int pos, int len) {
        if(str == null) {
            return null;
        } else if(len >= 0 && pos <= str.length()) {
            if(pos < 0) {
                pos = 0;
            }

            return str.length() <= pos + len?str.substring(pos):str.substring(pos, pos + len);
        } else {
            return "";
        }
    }

    public  String substringBefore(String str, String separator) {
        if(!isEmpty(str) && separator != null) {
            if(separator.length() == 0) {
                return "";
            } else {
                int pos = str.indexOf(separator);
                return pos == -1?str:str.substring(0, pos);
            }
        } else {
            return str;
        }
    }

    public  String substringAfter(String str, String separator) {
        if(isEmpty(str)) {
            return str;
        } else if(separator == null) {
            return "";
        } else {
            int pos = str.indexOf(separator);
            return pos == -1?"":str.substring(pos + separator.length());
        }
    }

    public  String substringBeforeLast(String str, String separator) {
        if(!isEmpty(str) && !isEmpty(separator)) {
            int pos = str.lastIndexOf(separator);
            return pos == -1?str:str.substring(0, pos);
        } else {
            return str;
        }
    }

    public  String substringAfterLast(String str, String separator) {
        if(isEmpty(str)) {
            return str;
        } else if(isEmpty(separator)) {
            return "";
        } else {
            int pos = str.lastIndexOf(separator);
            return pos != -1 && pos != str.length() - separator.length()?str.substring(pos + separator.length()):"";
        }
    }

    public  String substringBetween(String str, String tag) {
        return substringBetween(str, tag, tag);
    }

    public  String substringBetween(String str, String open, String close) {
        if(str != null && open != null && close != null) {
            int start = str.indexOf(open);
            if(start != -1) {
                int end = str.indexOf(close, start + open.length());
                if(end != -1) {
                    return str.substring(start + open.length(), end);
                }
            }

            return null;
        } else {
            return null;
        }
    }

    public  String[] substringsBetween(String str, String open, String close) {
        if(str != null && !isEmpty(open) && !isEmpty(close)) {
            int strLen = str.length();
            if(strLen == 0) {
                return ArrayUtils.EMPTY_STRING_ARRAY;
            } else {
                int closeLen = close.length();
                int openLen = open.length();
                ArrayList list = new ArrayList();

                int end;
                for(int pos = 0; pos < strLen - closeLen; pos = end + closeLen) {
                    int start = str.indexOf(open, pos);
                    if(start < 0) {
                        break;
                    }

                    start += openLen;
                    end = str.indexOf(close, start);
                    if(end < 0) {
                        break;
                    }

                    list.add(str.substring(start, end));
                }

                return list.isEmpty()?null:(String[])((String[])list.toArray(new String[list.size()]));
            }
        } else {
            return null;
        }
    }

    /** @deprecated */
    public  String getNestedString(String str, String tag) {
        return substringBetween(str, tag, tag);
    }

    /** @deprecated */
    public  String getNestedString(String str, String open, String close) {
        return substringBetween(str, open, close);
    }

    public  String[] split(String str) {
        return split(str, (String)null, -1);
    }

    public  String[] split(String str, char separatorChar) {
        return splitWorker(str, separatorChar, false);
    }

    public  String[] split(String str, String separatorChars) {
        return splitWorker(str, separatorChars, -1, false);
    }

    public  String[] split(String str, String separatorChars, int max) {
        return splitWorker(str, separatorChars, max, false);
    }

    public  String[] splitByWholeSeparator(String str, String separator) {
        return splitByWholeSeparatorWorker(str, separator, -1, false);
    }

    public  String[] splitByWholeSeparator(String str, String separator, int max) {
        return splitByWholeSeparatorWorker(str, separator, max, false);
    }

    public  String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator) {
        return splitByWholeSeparatorWorker(str, separator, -1, true);
    }

    public  String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator, int max) {
        return splitByWholeSeparatorWorker(str, separator, max, true);
    }

    private  String[] splitByWholeSeparatorWorker(String str, String separator, int max, boolean preserveAllTokens) {
        if(str == null) {
            return null;
        } else {
            int len = str.length();
            if(len == 0) {
                return ArrayUtils.EMPTY_STRING_ARRAY;
            } else if(separator != null && !"".equals(separator)) {
                int separatorLength = separator.length();
                ArrayList substrings = new ArrayList();
                int numberOfSubstrings = 0;
                int beg = 0;
                int end = 0;

                while(end < len) {
                    end = str.indexOf(separator, beg);
                    if(end > -1) {
                        if(end > beg) {
                            ++numberOfSubstrings;
                            if(numberOfSubstrings == max) {
                                end = len;
                                substrings.add(str.substring(beg));
                            } else {
                                substrings.add(str.substring(beg, end));
                                beg = end + separatorLength;
                            }
                        } else {
                            if(preserveAllTokens) {
                                ++numberOfSubstrings;
                                if(numberOfSubstrings == max) {
                                    end = len;
                                    substrings.add(str.substring(beg));
                                } else {
                                    substrings.add("");
                                }
                            }

                            beg = end + separatorLength;
                        }
                    } else {
                        substrings.add(str.substring(beg));
                        end = len;
                    }
                }

                return (String[])((String[])substrings.toArray(new String[substrings.size()]));
            } else {
                return splitWorker(str, (String)null, max, preserveAllTokens);
            }
        }
    }

    public  String[] splitPreserveAllTokens(String str) {
        return splitWorker(str, (String)null, -1, true);
    }

    public  String[] splitPreserveAllTokens(String str, char separatorChar) {
        return splitWorker(str, separatorChar, true);
    }

    private  String[] splitWorker(String str, char separatorChar, boolean preserveAllTokens) {
        if(str == null) {
            return null;
        } else {
            int len = str.length();
            if(len == 0) {
                return ArrayUtils.EMPTY_STRING_ARRAY;
            } else {
                ArrayList list = new ArrayList();
                int i = 0;
                int start = 0;
                boolean match = false;
                boolean lastMatch = false;

                while(i < len) {
                    if(str.charAt(i) == separatorChar) {
                        if(match || preserveAllTokens) {
                            list.add(str.substring(start, i));
                            match = false;
                            lastMatch = true;
                        }

                        ++i;
                        start = i;
                    } else {
                        lastMatch = false;
                        match = true;
                        ++i;
                    }
                }

                if(match || preserveAllTokens && lastMatch) {
                    list.add(str.substring(start, i));
                }

                return (String[])((String[])list.toArray(new String[list.size()]));
            }
        }
    }

    public  String[] splitPreserveAllTokens(String str, String separatorChars) {
        return splitWorker(str, separatorChars, -1, true);
    }

    public  String[] splitPreserveAllTokens(String str, String separatorChars, int max) {
        return splitWorker(str, separatorChars, max, true);
    }

    private  String[] splitWorker(String str, String separatorChars, int max, boolean preserveAllTokens) {
        if(str == null) {
            return null;
        } else {
            int len = str.length();
            if(len == 0) {
                return ArrayUtils.EMPTY_STRING_ARRAY;
            } else {
                ArrayList list = new ArrayList();
                int sizePlus1 = 1;
                int i = 0;
                int start = 0;
                boolean match = false;
                boolean lastMatch = false;
                if(separatorChars == null) {
                    while(i < len) {
                        if(!Character.isWhitespace(str.charAt(i))) {
                            lastMatch = false;
                            match = true;
                            ++i;
                        } else {
                            if(match || preserveAllTokens) {
                                lastMatch = true;
                                if(sizePlus1++ == max) {
                                    i = len;
                                    lastMatch = false;
                                }

                                list.add(str.substring(start, i));
                                match = false;
                            }

                            ++i;
                            start = i;
                        }
                    }
                } else if(separatorChars.length() == 1) {
                    char sep = separatorChars.charAt(0);

                    while(i < len) {
                        if(str.charAt(i) != sep) {
                            lastMatch = false;
                            match = true;
                            ++i;
                        } else {
                            if(match || preserveAllTokens) {
                                lastMatch = true;
                                if(sizePlus1++ == max) {
                                    i = len;
                                    lastMatch = false;
                                }

                                list.add(str.substring(start, i));
                                match = false;
                            }

                            ++i;
                            start = i;
                        }
                    }
                } else {
                    while(i < len) {
                        if(separatorChars.indexOf(str.charAt(i)) < 0) {
                            lastMatch = false;
                            match = true;
                            ++i;
                        } else {
                            if(match || preserveAllTokens) {
                                lastMatch = true;
                                if(sizePlus1++ == max) {
                                    i = len;
                                    lastMatch = false;
                                }

                                list.add(str.substring(start, i));
                                match = false;
                            }

                            ++i;
                            start = i;
                        }
                    }
                }

                if(match || preserveAllTokens && lastMatch) {
                    list.add(str.substring(start, i));
                }

                return (String[])((String[])list.toArray(new String[list.size()]));
            }
        }
    }

    public  String[] splitByCharacterType(String str) {
        return splitByCharacterType(str, false);
    }

    public  String[] splitByCharacterTypeCamelCase(String str) {
        return splitByCharacterType(str, true);
    }

    private  String[] splitByCharacterType(String str, boolean camelCase) {
        if(str == null) {
            return null;
        } else if(str.length() == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        } else {
            char[] c = str.toCharArray();
            ArrayList list = new ArrayList();
            int tokenStart = 0;
            int currentType = Character.getType(c[tokenStart]);

            for(int pos = tokenStart + 1; pos < c.length; ++pos) {
                int type = Character.getType(c[pos]);
                if(type != currentType) {
                    if(camelCase && type == 2 && currentType == 1) {
                        int newTokenStart = pos - 1;
                        if(newTokenStart != tokenStart) {
                            list.add(new String(c, tokenStart, newTokenStart - tokenStart));
                            tokenStart = newTokenStart;
                        }
                    } else {
                        list.add(new String(c, tokenStart, pos - tokenStart));
                        tokenStart = pos;
                    }

                    currentType = type;
                }
            }

            list.add(new String(c, tokenStart, c.length - tokenStart));
            return (String[])((String[])list.toArray(new String[list.size()]));
        }
    }

    /** @deprecated */
    public  String concatenate(Object[] array) {
        return join((Object[])array, (String)null);
    }

    public  String join(Object[] array) {
        return join((Object[])array, (String)null);
    }

    public  String join(Object[] array, char separator) {
        return array == null?null:join(array, separator, 0, array.length);
    }

    public  String join(Object[] array, char separator, int startIndex, int endIndex) {
        if(array == null) {
            return null;
        } else {
            int bufSize = endIndex - startIndex;
            if(bufSize <= 0) {
                return "";
            } else {
                bufSize *= (array[startIndex] == null?16:array[startIndex].toString().length()) + 1;
                StringBuffer buf = new StringBuffer(bufSize);

                for(int i = startIndex; i < endIndex; ++i) {
                    if(i > startIndex) {
                        buf.append(separator);
                    }

                    if(array[i] != null) {
                        buf.append(array[i]);
                    }
                }

                return buf.toString();
            }
        }
    }

    public  String join(Object[] array, String separator) {
        return array == null?null:join(array, separator, 0, array.length);
    }

    public  String join(Object[] array, String separator, int startIndex, int endIndex) {
        if(array == null) {
            return null;
        } else {
            if(separator == null) {
                separator = "";
            }

            int bufSize = endIndex - startIndex;
            if(bufSize <= 0) {
                return "";
            } else {
                bufSize *= (array[startIndex] == null?16:array[startIndex].toString().length()) + separator.length();
                StringBuffer buf = new StringBuffer(bufSize);

                for(int i = startIndex; i < endIndex; ++i) {
                    if(i > startIndex) {
                        buf.append(separator);
                    }

                    if(array[i] != null) {
                        buf.append(array[i]);
                    }
                }

                return buf.toString();
            }
        }
    }

    public  String join(Iterator iterator, char separator) {
        if(iterator == null) {
            return null;
        } else if(!iterator.hasNext()) {
            return "";
        } else {
            Object first = iterator.next();
            if(!iterator.hasNext()) {
                return ObjectUtils.toString(first);
            } else {
                StringBuffer buf = new StringBuffer(256);
                if(first != null) {
                    buf.append(first);
                }

                while(iterator.hasNext()) {
                    buf.append(separator);
                    Object obj = iterator.next();
                    if(obj != null) {
                        buf.append(obj);
                    }
                }

                return buf.toString();
            }
        }
    }

    public  String join(Iterator iterator, String separator) {
        if(iterator == null) {
            return null;
        } else if(!iterator.hasNext()) {
            return "";
        } else {
            Object first = iterator.next();
            if(!iterator.hasNext()) {
                return ObjectUtils.toString(first);
            } else {
                StringBuffer buf = new StringBuffer(256);
                if(first != null) {
                    buf.append(first);
                }

                while(iterator.hasNext()) {
                    if(separator != null) {
                        buf.append(separator);
                    }

                    Object obj = iterator.next();
                    if(obj != null) {
                        buf.append(obj);
                    }
                }

                return buf.toString();
            }
        }
    }

    public  String join(Collection collection, char separator) {
        return collection == null?null:join((Iterator)collection.iterator(), separator);
    }

    public  String join(Collection collection, String separator) {
        return collection == null?null:join((Iterator)collection.iterator(), separator);
    }

    /** @deprecated */
    public  String deleteSpaces(String str) {
        return str == null?null: CharSetUtils.delete(str, " \t\r\n\b");
    }

    public  String deleteWhitespace(String str) {
        if(isEmpty(str)) {
            return str;
        } else {
            int sz = str.length();
            char[] chs = new char[sz];
            int count = 0;

            for(int i = 0; i < sz; ++i) {
                if(!Character.isWhitespace(str.charAt(i))) {
                    chs[count++] = str.charAt(i);
                }
            }

            if(count == sz) {
                return str;
            } else {
                return new String(chs, 0, count);
            }
        }
    }

    public  String removeStart(String str, String remove) {
        return !isEmpty(str) && !isEmpty(remove)?(str.startsWith(remove)?str.substring(remove.length()):str):str;
    }

    public  String removeStartIgnoreCase(String str, String remove) {
        return !isEmpty(str) && !isEmpty(remove)?(startsWithIgnoreCase(str, remove)?str.substring(remove.length()):str):str;
    }

    public  String removeEnd(String str, String remove) {
        return !isEmpty(str) && !isEmpty(remove)?(str.endsWith(remove)?str.substring(0, str.length() - remove.length()):str):str;
    }

    public  String removeEndIgnoreCase(String str, String remove) {
        return !isEmpty(str) && !isEmpty(remove)?(endsWithIgnoreCase(str, remove)?str.substring(0, str.length() - remove.length()):str):str;
    }

    public  String remove(String str, String remove) {
        return !isEmpty(str) && !isEmpty(remove)?replace(str, remove, "", -1):str;
    }

    public  String remove(String str, char remove) {
        if(!isEmpty(str) && str.indexOf(remove) != -1) {
            char[] chars = str.toCharArray();
            int pos = 0;

            for(int i = 0; i < chars.length; ++i) {
                if(chars[i] != remove) {
                    chars[pos++] = chars[i];
                }
            }

            return new String(chars, 0, pos);
        } else {
            return str;
        }
    }

    public  String replaceOnce(String text, String searchString, String replacement) {
        return replace(text, searchString, replacement, 1);
    }

    public  String replace(String text, String searchString, String replacement) {
        return replace(text, searchString, replacement, -1);
    }

    public  String replace(String text, String searchString, String replacement, int max) {
        if(!isEmpty(text) && !isEmpty(searchString) && replacement != null && max != 0) {
            int start = 0;
            int end = text.indexOf(searchString, start);
            if(end == -1) {
                return text;
            } else {
                int replLength = searchString.length();
                int increase = replacement.length() - replLength;
                increase = increase < 0?0:increase;
                increase *= max < 0?16:(max > 64?64:max);

                StringBuffer buf;
                for(buf = new StringBuffer(text.length() + increase); end != -1; end = text.indexOf(searchString, start)) {
                    buf.append(text.substring(start, end)).append(replacement);
                    start = end + replLength;
                    --max;
                    if(max == 0) {
                        break;
                    }
                }

                buf.append(text.substring(start));
                return buf.toString();
            }
        } else {
            return text;
        }
    }

    public  String replaceEach(String text, String[] searchList, String[] replacementList) {
        return replaceEach(text, searchList, replacementList, false, 0);
    }

    public  String replaceEachRepeatedly(String text, String[] searchList, String[] replacementList) {
        int timeToLive = searchList == null?0:searchList.length;
        return replaceEach(text, searchList, replacementList, true, timeToLive);
    }

    private  String replaceEach(String text, String[] searchList, String[] replacementList, boolean repeat, int timeToLive) {
        if(text != null && text.length() != 0 && searchList != null && searchList.length != 0 && replacementList != null && replacementList.length != 0) {
            if(timeToLive < 0) {
                throw new IllegalStateException("TimeToLive of " + timeToLive + " is less than 0: " + text);
            } else {
                int searchLength = searchList.length;
                int replacementLength = replacementList.length;
                if(searchLength != replacementLength) {
                    throw new IllegalArgumentException("Search and Replace array lengths don\'t match: " + searchLength + " vs " + replacementLength);
                } else {
                    boolean[] noMoreMatchesForReplIndex = new boolean[searchLength];
                    int textIndex = -1;
                    int replaceIndex = -1;
                    boolean tempIndex = true;

                    int start;
                    int var16;
                    for(start = 0; start < searchLength; ++start) {
                        if(!noMoreMatchesForReplIndex[start] && searchList[start] != null && searchList[start].length() != 0 && replacementList[start] != null) {
                            var16 = text.indexOf(searchList[start]);
                            if(var16 == -1) {
                                noMoreMatchesForReplIndex[start] = true;
                            } else if(textIndex == -1 || var16 < textIndex) {
                                textIndex = var16;
                                replaceIndex = start;
                            }
                        }
                    }

                    if(textIndex == -1) {
                        return text;
                    } else {
                        start = 0;
                        int increase = 0;

                        int textLength;
                        for(int buf = 0; buf < searchList.length; ++buf) {
                            if(searchList[buf] != null && replacementList[buf] != null) {
                                textLength = replacementList[buf].length() - searchList[buf].length();
                                if(textLength > 0) {
                                    increase += 3 * textLength;
                                }
                            }
                        }

                        increase = Math.min(increase, text.length() / 5);
                        StringBuffer var17 = new StringBuffer(text.length() + increase);

                        while(textIndex != -1) {
                            for(textLength = start; textLength < textIndex; ++textLength) {
                                var17.append(text.charAt(textLength));
                            }

                            var17.append(replacementList[replaceIndex]);
                            start = textIndex + searchList[replaceIndex].length();
                            textIndex = -1;
                            replaceIndex = -1;
                            tempIndex = true;

                            for(textLength = 0; textLength < searchLength; ++textLength) {
                                if(!noMoreMatchesForReplIndex[textLength] && searchList[textLength] != null && searchList[textLength].length() != 0 && replacementList[textLength] != null) {
                                    var16 = text.indexOf(searchList[textLength], start);
                                    if(var16 == -1) {
                                        noMoreMatchesForReplIndex[textLength] = true;
                                    } else if(textIndex == -1 || var16 < textIndex) {
                                        textIndex = var16;
                                        replaceIndex = textLength;
                                    }
                                }
                            }
                        }

                        textLength = text.length();

                        for(int result = start; result < textLength; ++result) {
                            var17.append(text.charAt(result));
                        }

                        String var18 = var17.toString();
                        if(!repeat) {
                            return var18;
                        } else {
                            return replaceEach(var18, searchList, replacementList, repeat, timeToLive - 1);
                        }
                    }
                }
            }
        } else {
            return text;
        }
    }

    public  String replaceChars(String str, char searchChar, char replaceChar) {
        return str == null?null:str.replace(searchChar, replaceChar);
    }

    public  String replaceChars(String str, String searchChars, String replaceChars) {
        if(!isEmpty(str) && !isEmpty(searchChars)) {
            if(replaceChars == null) {
                replaceChars = "";
            }

            boolean modified = false;
            int replaceCharsLength = replaceChars.length();
            int strLength = str.length();
            StringBuffer buf = new StringBuffer(strLength);

            for(int i = 0; i < strLength; ++i) {
                char ch = str.charAt(i);
                int index = searchChars.indexOf(ch);
                if(index >= 0) {
                    modified = true;
                    if(index < replaceCharsLength) {
                        buf.append(replaceChars.charAt(index));
                    }
                } else {
                    buf.append(ch);
                }
            }

            if(modified) {
                return buf.toString();
            } else {
                return str;
            }
        } else {
            return str;
        }
    }

    /** @deprecated */
    public  String overlayString(String text, String overlay, int start, int end) {
        return (new StringBuffer(start + overlay.length() + text.length() - end + 1)).append(text.substring(0, start)).append(overlay).append(text.substring(end)).toString();
    }

    public  String overlay(String str, String overlay, int start, int end) {
        if(str == null) {
            return null;
        } else {
            if(overlay == null) {
                overlay = "";
            }

            int len = str.length();
            if(start < 0) {
                start = 0;
            }

            if(start > len) {
                start = len;
            }

            if(end < 0) {
                end = 0;
            }

            if(end > len) {
                end = len;
            }

            if(start > end) {
                int temp = start;
                start = end;
                end = temp;
            }

            return (new StringBuffer(len + start - end + overlay.length() + 1)).append(str.substring(0, start)).append(overlay).append(str.substring(end)).toString();
        }
    }

    public  String chomp(String str) {
        if(isEmpty(str)) {
            return str;
        } else if(str.length() == 1) {
            char var3 = str.charAt(0);
            return var3 != 13 && var3 != 10?str:"";
        } else {
            int lastIdx = str.length() - 1;
            char last = str.charAt(lastIdx);
            if(last == 10) {
                if(str.charAt(lastIdx - 1) == 13) {
                    --lastIdx;
                }
            } else if(last != 13) {
                ++lastIdx;
            }

            return str.substring(0, lastIdx);
        }
    }

    public  String chomp(String str, String separator) {
        return !isEmpty(str) && separator != null?(str.endsWith(separator)?str.substring(0, str.length() - separator.length()):str):str;
    }

    /** @deprecated */
    public  String chompLast(String str) {
        return chompLast(str, "\n");
    }

    /** @deprecated */
    public  String chompLast(String str, String sep) {
        if(str.length() == 0) {
            return str;
        } else {
            String sub = str.substring(str.length() - sep.length());
            return sep.equals(sub)?str.substring(0, str.length() - sep.length()):str;
        }
    }

    /** @deprecated */
    public  String getChomp(String str, String sep) {
        int idx = str.lastIndexOf(sep);
        return idx == str.length() - sep.length()?sep:(idx != -1?str.substring(idx):"");
    }

    /** @deprecated */
    public  String prechomp(String str, String sep) {
        int idx = str.indexOf(sep);
        return idx == -1?str:str.substring(idx + sep.length());
    }

    /** @deprecated */
    public  String getPrechomp(String str, String sep) {
        int idx = str.indexOf(sep);
        return idx == -1?"":str.substring(0, idx + sep.length());
    }

    public  String chop(String str) {
        if(str == null) {
            return null;
        } else {
            int strLen = str.length();
            if(strLen < 2) {
                return "";
            } else {
                int lastIdx = strLen - 1;
                String ret = str.substring(0, lastIdx);
                char last = str.charAt(lastIdx);
                return last == 10 && ret.charAt(lastIdx - 1) == 13?ret.substring(0, lastIdx - 1):ret;
            }
        }
    }

    /** @deprecated */
    public  String chopNewline(String str) {
        int lastIdx = str.length() - 1;
        if(lastIdx <= 0) {
            return "";
        } else {
            char last = str.charAt(lastIdx);
            if(last == 10) {
                if(str.charAt(lastIdx - 1) == 13) {
                    --lastIdx;
                }
            } else {
                ++lastIdx;
            }

            return str.substring(0, lastIdx);
        }
    }

    /** @deprecated */
    public  String escape(String str) {
        return StringEscapeUtils.escapeJava(str);
    }

    public  String repeat(String str, int repeat) {
        if(str == null) {
            return null;
        } else if(repeat <= 0) {
            return "";
        } else {
            int inputLength = str.length();
            if(repeat != 1 && inputLength != 0) {
                if(inputLength == 1 && repeat <= 8192) {
                    return padding(repeat, str.charAt(0));
                } else {
                    int outputLength = inputLength * repeat;
                    switch(inputLength) {
                        case 1:
                            char ch = str.charAt(0);
                            char[] output1 = new char[outputLength];

                            for(int var11 = repeat - 1; var11 >= 0; --var11) {
                                output1[var11] = ch;
                            }

                            return new String(output1);
                        case 2:
                            char ch0 = str.charAt(0);
                            char ch1 = str.charAt(1);
                            char[] output2 = new char[outputLength];

                            for(int buf = repeat * 2 - 2; buf >= 0; --buf) {
                                output2[buf] = ch0;
                                output2[buf + 1] = ch1;
                                --buf;
                            }

                            return new String(output2);
                        default:
                            StringBuffer var12 = new StringBuffer(outputLength);

                            for(int i = 0; i < repeat; ++i) {
                                var12.append(str);
                            }

                            return var12.toString();
                    }
                }
            } else {
                return str;
            }
        }
    }

    public  String repeat(String str, String separator, int repeat) {
        if(str != null && separator != null) {
            String result = repeat(str + separator, repeat);
            return removeEnd(result, separator);
        } else {
            return repeat(str, repeat);
        }
    }

    private  String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
        if(repeat < 0) {
            throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
        } else {
            char[] buf = new char[repeat];

            for(int i = 0; i < buf.length; ++i) {
                buf[i] = padChar;
            }

            return new String(buf);
        }
    }

    public  String rightPad(String str, int size) {
        return rightPad(str, size, ' ');
    }

    public  String rightPad(String str, int size, char padChar) {
        if(str == null) {
            return null;
        } else {
            int pads = size - str.length();
            return pads <= 0?str:(pads > 8192?rightPad(str, size, String.valueOf(padChar)):str.concat(padding(pads, padChar)));
        }
    }

    public  String rightPad(String str, int size, String padStr) {
        if(str == null) {
            return null;
        } else {
            if(isEmpty(padStr)) {
                padStr = " ";
            }

            int padLen = padStr.length();
            int strLen = str.length();
            int pads = size - strLen;
            if(pads <= 0) {
                return str;
            } else if(padLen == 1 && pads <= 8192) {
                return rightPad(str, size, padStr.charAt(0));
            } else if(pads == padLen) {
                return str.concat(padStr);
            } else if(pads < padLen) {
                return str.concat(padStr.substring(0, pads));
            } else {
                char[] padding = new char[pads];
                char[] padChars = padStr.toCharArray();

                for(int i = 0; i < pads; ++i) {
                    padding[i] = padChars[i % padLen];
                }

                return str.concat(new String(padding));
            }
        }
    }

    public  String leftPad(String str, int size) {
        return leftPad(str, size, ' ');
    }

    public  String leftPad(String str, int size, char padChar) {
        if(str == null) {
            return null;
        } else {
            int pads = size - str.length();
            return pads <= 0?str:(pads > 8192?leftPad(str, size, String.valueOf(padChar)):padding(pads, padChar).concat(str));
        }
    }

    public  String leftPad(String str, int size, String padStr) {
        if(str == null) {
            return null;
        } else {
            if(isEmpty(padStr)) {
                padStr = " ";
            }

            int padLen = padStr.length();
            int strLen = str.length();
            int pads = size - strLen;
            if(pads <= 0) {
                return str;
            } else if(padLen == 1 && pads <= 8192) {
                return leftPad(str, size, padStr.charAt(0));
            } else if(pads == padLen) {
                return padStr.concat(str);
            } else if(pads < padLen) {
                return padStr.substring(0, pads).concat(str);
            } else {
                char[] padding = new char[pads];
                char[] padChars = padStr.toCharArray();

                for(int i = 0; i < pads; ++i) {
                    padding[i] = padChars[i % padLen];
                }

                return (new String(padding)).concat(str);
            }
        }
    }

    public  int length(String str) {
        return str == null?0:str.length();
    }

    public  String center(String str, int size) {
        return center(str, size, ' ');
    }

    public  String center(String str, int size, char padChar) {
        if(str != null && size > 0) {
            int strLen = str.length();
            int pads = size - strLen;
            if(pads <= 0) {
                return str;
            } else {
                str = leftPad(str, strLen + pads / 2, padChar);
                str = rightPad(str, size, padChar);
                return str;
            }
        } else {
            return str;
        }
    }

    public  String center(String str, int size, String padStr) {
        if(str != null && size > 0) {
            if(isEmpty(padStr)) {
                padStr = " ";
            }

            int strLen = str.length();
            int pads = size - strLen;
            if(pads <= 0) {
                return str;
            } else {
                str = leftPad(str, strLen + pads / 2, padStr);
                str = rightPad(str, size, padStr);
                return str;
            }
        } else {
            return str;
        }
    }

    public  String upperCase(String str) {
        return str == null?null:str.toUpperCase();
    }

    public  String upperCase(String str, Locale locale) {
        return str == null?null:str.toUpperCase(locale);
    }

    public  String lowerCase(String str) {
        return str == null?null:str.toLowerCase();
    }

    public  String lowerCase(String str, Locale locale) {
        return str == null?null:str.toLowerCase(locale);
    }

    public  String capitalize(String str) {
        int strLen;
        return str != null && (strLen = str.length()) != 0?(new StringBuffer(strLen)).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString():str;
    }

    /** @deprecated */
    public  String capitalise(String str) {
        return capitalize(str);
    }

    public  String uncapitalize(String str) {
        int strLen;
        return str != null && (strLen = str.length()) != 0?(new StringBuffer(strLen)).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString():str;
    }

    /** @deprecated */
    public  String uncapitalise(String str) {
        return uncapitalize(str);
    }

    public  String swapCase(String str) {
        int strLen;
        if(str != null && (strLen = str.length()) != 0) {
            StringBuffer buffer = new StringBuffer(strLen);
            boolean ch = false;

            for(int i = 0; i < strLen; ++i) {
                char var5 = str.charAt(i);
                if(Character.isUpperCase(var5)) {
                    var5 = Character.toLowerCase(var5);
                } else if(Character.isTitleCase(var5)) {
                    var5 = Character.toLowerCase(var5);
                } else if(Character.isLowerCase(var5)) {
                    var5 = Character.toUpperCase(var5);
                }

                buffer.append(var5);
            }

            return buffer.toString();
        } else {
            return str;
        }
    }

    /** @deprecated */
    public  String capitaliseAllWords(String str) {
        return WordUtils.capitalize(str);
    }

    public  int countMatches(String str, String sub) {
        if(!isEmpty(str) && !isEmpty(sub)) {
            int count = 0;

            for(int idx = 0; (idx = str.indexOf(sub, idx)) != -1; idx += sub.length()) {
                ++count;
            }

            return count;
        } else {
            return 0;
        }
    }

    public  boolean isAlpha(String str) {
        if(str == null) {
            return false;
        } else {
            int sz = str.length();

            for(int i = 0; i < sz; ++i) {
                if(!Character.isLetter(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    public  boolean isAlphaSpace(String str) {
        if(str == null) {
            return false;
        } else {
            int sz = str.length();

            for(int i = 0; i < sz; ++i) {
                if(!Character.isLetter(str.charAt(i)) && str.charAt(i) != 32) {
                    return false;
                }
            }

            return true;
        }
    }

    public  boolean isAlphanumeric(String str) {
        if(str == null) {
            return false;
        } else {
            int sz = str.length();

            for(int i = 0; i < sz; ++i) {
                if(!Character.isLetterOrDigit(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    public  boolean isAlphanumericSpace(String str) {
        if(str == null) {
            return false;
        } else {
            int sz = str.length();

            for(int i = 0; i < sz; ++i) {
                if(!Character.isLetterOrDigit(str.charAt(i)) && str.charAt(i) != 32) {
                    return false;
                }
            }

            return true;
        }
    }

    public  boolean isAsciiPrintable(String str) {
        if(str == null) {
            return false;
        } else {
            int sz = str.length();

            for(int i = 0; i < sz; ++i) {
                if(!CharUtils.isAsciiPrintable(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    public  boolean isNumeric(String str) {
        if(str == null) {
            return false;
        } else {
            int sz = str.length();

            for(int i = 0; i < sz; ++i) {
                if(!Character.isDigit(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    public  boolean isNumericSpace(String str) {
        if(str == null) {
            return false;
        } else {
            int sz = str.length();

            for(int i = 0; i < sz; ++i) {
                if(!Character.isDigit(str.charAt(i)) && str.charAt(i) != 32) {
                    return false;
                }
            }

            return true;
        }
    }

    public  boolean isWhitespace(String str) {
        if(str == null) {
            return false;
        } else {
            int sz = str.length();

            for(int i = 0; i < sz; ++i) {
                if(!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }

    public  boolean isAllLowerCase(String str) {
        if(str != null && !isEmpty(str)) {
            int sz = str.length();

            for(int i = 0; i < sz; ++i) {
                if(!Character.isLowerCase(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public  boolean isAllUpperCase(String str) {
        if(str != null && !isEmpty(str)) {
            int sz = str.length();

            for(int i = 0; i < sz; ++i) {
                if(!Character.isUpperCase(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public  String defaultString(String str) {
        return str == null?"":str;
    }

    public  String defaultString(String str, String defaultStr) {
        return str == null?defaultStr:str;
    }

    public  String defaultIfEmpty(String str, String defaultStr) {
        return isEmpty(str)?defaultStr:str;
    }

    public  String reverse(String str) {
        return str == null?null:(new StringBuffer(str)).reverse().toString();
    }

    public  String reverseDelimited(String str, char separatorChar) {
        if(str == null) {
            return null;
        } else {
            String[] strs = split(str, separatorChar);
            ArrayUtils.reverse(strs);
            return join((Object[])strs, separatorChar);
        }
    }

    /** @deprecated */
    public  String reverseDelimitedString(String str, String separatorChars) {
        if(str == null) {
            return null;
        } else {
            String[] strs = split(str, separatorChars);
            ArrayUtils.reverse(strs);
            return separatorChars == null?join((Object[])strs, ' '):join((Object[])strs, separatorChars);
        }
    }

    public  String abbreviate(String str, int maxWidth) {
        return abbreviate(str, 0, maxWidth);
    }

    public  String abbreviate(String str, int offset, int maxWidth) {
        if(str == null) {
            return null;
        } else if(maxWidth < 4) {
            throw new IllegalArgumentException("Minimum abbreviation width is 4");
        } else if(str.length() <= maxWidth) {
            return str;
        } else {
            if(offset > str.length()) {
                offset = str.length();
            }

            if(str.length() - offset < maxWidth - 3) {
                offset = str.length() - (maxWidth - 3);
            }

            if(offset <= 4) {
                return str.substring(0, maxWidth - 3) + "...";
            } else if(maxWidth < 7) {
                throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
            } else {
                return offset + (maxWidth - 3) < str.length()?"..." + abbreviate(str.substring(offset), maxWidth - 3):"..." + str.substring(str.length() - (maxWidth - 3));
            }
        }
    }

    public  String abbreviateMiddle(String str, String middle, int length) {
        if(!isEmpty(str) && !isEmpty(middle)) {
            if(length < str.length() && length >= middle.length() + 2) {
                int targetSting = length - middle.length();
                int startOffset = targetSting / 2 + targetSting % 2;
                int endOffset = str.length() - targetSting / 2;
                StringBuffer builder = new StringBuffer(length);
                builder.append(str.substring(0, startOffset));
                builder.append(middle);
                builder.append(str.substring(endOffset));
                return builder.toString();
            } else {
                return str;
            }
        } else {
            return str;
        }
    }

    public  String difference(String str1, String str2) {
        if(str1 == null) {
            return str2;
        } else if(str2 == null) {
            return str1;
        } else {
            int at = indexOfDifference(str1, str2);
            return at == -1?"":str2.substring(at);
        }
    }

    public  int indexOfDifference(String str1, String str2) {
        if(str1 == str2) {
            return -1;
        } else if(str1 != null && str2 != null) {
            int i;
            for(i = 0; i < str1.length() && i < str2.length() && str1.charAt(i) == str2.charAt(i); ++i) {
                ;
            }

            return i >= str2.length() && i >= str1.length()?-1:i;
        } else {
            return 0;
        }
    }

    public  int indexOfDifference(String[] strs) {
        if(strs != null && strs.length > 1) {
            boolean anyStringNull = false;
            boolean allStringsNull = true;
            int arrayLen = strs.length;
            int shortestStrLen = 2147483647;
            int longestStrLen = 0;

            int firstDiff;
            for(firstDiff = 0; firstDiff < arrayLen; ++firstDiff) {
                if(strs[firstDiff] == null) {
                    anyStringNull = true;
                    shortestStrLen = 0;
                } else {
                    allStringsNull = false;
                    shortestStrLen = Math.min(strs[firstDiff].length(), shortestStrLen);
                    longestStrLen = Math.max(strs[firstDiff].length(), longestStrLen);
                }
            }

            if(!allStringsNull && (longestStrLen != 0 || anyStringNull)) {
                if(shortestStrLen == 0) {
                    return 0;
                } else {
                    firstDiff = -1;

                    for(int stringPos = 0; stringPos < shortestStrLen; ++stringPos) {
                        char comparisonChar = strs[0].charAt(stringPos);

                        for(int arrayPos = 1; arrayPos < arrayLen; ++arrayPos) {
                            if(strs[arrayPos].charAt(stringPos) != comparisonChar) {
                                firstDiff = stringPos;
                                break;
                            }
                        }

                        if(firstDiff != -1) {
                            break;
                        }
                    }

                    return firstDiff == -1 && shortestStrLen != longestStrLen?shortestStrLen:firstDiff;
                }
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    public  String getCommonPrefix(String[] strs) {
        if(strs != null && strs.length != 0) {
            int smallestIndexOfDiff = indexOfDifference(strs);
            return smallestIndexOfDiff == -1?(strs[0] == null?"":strs[0]):(smallestIndexOfDiff == 0?"":strs[0].substring(0, smallestIndexOfDiff));
        } else {
            return "";
        }
    }

    public  int getLevenshteinDistance(String s, String t) {
        if(s != null && t != null) {
            int n = s.length();
            int m = t.length();
            if(n == 0) {
                return m;
            } else if(m == 0) {
                return n;
            } else {
                if(n > m) {
                    String p = s;
                    s = t;
                    t = p;
                    n = m;
                    m = p.length();
                }

                int[] var11 = new int[n + 1];
                int[] d = new int[n + 1];

                int i;
                for(i = 0; i <= n; var11[i] = i++) {
                    ;
                }

                for(int j = 1; j <= m; ++j) {
                    char t_j = t.charAt(j - 1);
                    d[0] = j;

                    for(i = 1; i <= n; ++i) {
                        int cost = s.charAt(i - 1) == t_j?0:1;
                        d[i] = Math.min(Math.min(d[i - 1] + 1, var11[i] + 1), var11[i - 1] + cost);
                    }

                    int[] _d = var11;
                    var11 = d;
                    d = _d;
                }

                return var11[n];
            }
        } else {
            throw new IllegalArgumentException("Strings must not be null");
        }
    }

    public  boolean startsWith(String str, String prefix) {
        return startsWith(str, prefix, false);
    }

    public  boolean startsWithIgnoreCase(String str, String prefix) {
        return startsWith(str, prefix, true);
    }

    private  boolean startsWith(String str, String prefix, boolean ignoreCase) {
        return str != null && prefix != null?(prefix.length() > str.length()?false:str.regionMatches(ignoreCase, 0, prefix, 0, prefix.length())):str == null && prefix == null;
    }

    public  boolean startsWithAny(String string, String[] searchStrings) {
        if(!isEmpty(string) && !ArrayUtils.isEmpty(searchStrings)) {
            for(int i = 0; i < searchStrings.length; ++i) {
                String searchString = searchStrings[i];
                if(startsWith(string, searchString)) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    public  boolean endsWith(String str, String suffix) {
        return endsWith(str, suffix, false);
    }

    public  boolean endsWithIgnoreCase(String str, String suffix) {
        return endsWith(str, suffix, true);
    }

    private  boolean endsWith(String str, String suffix, boolean ignoreCase) {
        if(str != null && suffix != null) {
            if(suffix.length() > str.length()) {
                return false;
            } else {
                int strOffset = str.length() - suffix.length();
                return str.regionMatches(ignoreCase, strOffset, suffix, 0, suffix.length());
            }
        } else {
            return str == null && suffix == null;
        }
    }


    /**
     * 字符串拆分为map
     * @param operateStr = "default:3,YD:0,LT:0,DX:0"
     * @param split1     = ","
     * @param split2     = ":"
     * @return  Map
     * 		key :	value
     * 		default		3
     * 		YD			0
     * 		LT			0
     */
    public  Map<String, String> getMapByString(String operateStr,String split1,String split2){
        Map<String,String> result = new HashMap<String, String>();
        if(isNotBlank(operateStr)){
            String[] operateArr = operateStr.split(split1);
            for (String operate : operateArr) {
                String [] arr=operate.split(split2);
                result.put(arr[0], arr[1]);
            }
        }
        return result;
    }

    /**
     * 字符串拆分为map
     * @param operateStr = "default,YD,LT,DX"
     * @param split     = ","
     * @return  List
     * 		default		
     * 		YD			
     * 		LT
     * 		DX			
     */
    public  List<String> getListByString(String operateStr,String split){
        List<String> rs =null;
        if(!isEmpty(operateStr)){
            String[] operateArr = operateStr.split(split);
            rs = Arrays.asList(operateArr);
        }
        return rs;
    }
}
