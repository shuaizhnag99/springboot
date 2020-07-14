package com.ule.uhj.Dcoffee.tools.sqlUtil.analysis;

import com.ule.uhj.Dcoffee.tools.StringUtils;
import com.ule.uhj.Dcoffee.tools.sqlUtil.SqlUtilConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengxin on 2018/3/26.
 */
public class Word {
    private static int size = 0;
    private static Word[] table;
    private String value;
    private Word prev;
    private Word next;
    private boolean isAccess = false;

    private String[] sensitiveWords = new String[]{
      ",","\n","\"","'","\'",">","<","=","-",":","|","&"
    };

    public static Word createWords(String value,String spiltor){
        String[] Words = value.split(spiltor);
        List<Word> WordList = new ArrayList<Word>();

        int i = 0,j = 0;
        Word lastWord = null;
        while(i< Words.length){
            if(StringUtils.isBlank(Words[i])){
                i++;
                continue;
            }
            Word newWord = new Word(Words[i]);
            WordList.add(newWord);
            if(i>0){
                newWord.setPrev(lastWord);
                if(null!=lastWord) {                	
                	lastWord.setNext(newWord);
                }
            }
            lastWord = newWord;
            i++;
        }
        size = WordList.size();
        if(size>=1){
            table = WordList.toArray(new Word[WordList.size()]);
            return table[0];
        }
        return null;
    }

    public void access(){
        this.isAccess = true;
    }

    public boolean isAccess(){
        return this.isAccess;
    }

    public void mergeNext(){
        Word next = this.next;
        this.next = next.next;
        this.next.prev = this;
        next.prev = null;
        next.next = null;
        this.value+=" "+next.value;
    }

    @Override
    public boolean equals(Object obj) {
    	if(obj instanceof Word) {
    		
    		return this.value.trim().equalsIgnoreCase(obj.toString().trim());
    	}
    	return false;
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    public boolean isStart(){
        if(size==1){
            return true;
        }
        if(prev==null && next!=null){
            return true;
        }
        return false;
    }

    public boolean isEnd(){
        if(size==1){
            return true;
        }
        if(prev!=null && next==null){
            return true;
        }
        return false;
    }

    public Word(String value) {
        this.value = new String(value);

        if(this.value.contains(SqlUtilConstant.SQL_END) && this.value.length()>1){
            String[] strs = this.value.split(SqlUtilConstant.SQL_END);
            this.value = strs[0];
            Word next = new Word(SqlUtilConstant.SQL_END);
            this.next = next;
            next.prev = this;
        }

        while(this.value.contains(SqlUtilConstant.LBRACKETS.trim()) || this.value.contains(SqlUtilConstant.RBRACKETS.trim())){
            if(this.value.contains(SqlUtilConstant.LBRACKETS.trim()) && this.value.contains(SqlUtilConstant.RBRACKETS.trim())){
                int start = this.value.indexOf(SqlUtilConstant.LBRACKETS.trim())+1;
                int end = this.value.indexOf(SqlUtilConstant.RBRACKETS.trim());
                this.value = this.value.substring(start,end);
            }else if(this.value.contains(SqlUtilConstant.LBRACKETS.trim())){
                int start = this.value.indexOf(SqlUtilConstant.LBRACKETS.trim())+1;
                this.value = this.value.substring(start,this.value.length());
            }else{
                int end = this.value.indexOf(SqlUtilConstant.RBRACKETS.trim());
                this.value = this.value.substring(0, end);
            }
        }

        for(String sensitiveWord : sensitiveWords){
            this.value = this.value.replace(sensitiveWord,"");
        }
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Word getPrev() {
        return prev;
    }

    public void setPrev(Word prev) {
        this.prev = prev;
    }

    public Word getNext() {
        return next;
    }

    public void setNext(Word next) {
        this.next = next;
    }
}
