package com.ule.uhj.Dcoffee.tools.sqlUtil.analysis;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhengxin on 2018/3/26.
 */
public class Section<T> {
    private SectionEntry[] entries;
    private String tag;
    private Section parent = null;
    private List<Section> children = new ArrayList<Section>();
    private boolean end = true;

    public Section(String tag) {
        this.tag = tag;
    }

    public int size(){
        return entries==null ? 0 : entries.length;
    }

    public boolean hasChild(){
        return children.size()>0;
    }

    public Section newSection(String tag){
        if(end){
            Section<T> section = new Section<T>(tag);
            section.parent = this.parent;
            this.parent.children.add(section);
            return section;
        }
        return null;
    }

    public Section startInner(String tag){
        if(!end){
            Section<T> section = new Section<T>(tag);
            section.parent = this;
            children.add(section);
            return section;
        }
        return null;
    }

    public void start(T object){
        end = false;
        entries = new SectionEntry[1];
        entries[0] = new SectionEntry(object,size()-1,-1);
    }

    public void add(T object){
        if(!end){
            entries = Arrays.copyOf(entries,size()+1);
            entries[size()-1] = new SectionEntry(object,size()-1,0);
        }
    }

    public void end(T object){
        if(!end){
            entries = Arrays.copyOf(entries,size()+1);
            entries[size()-1] = new SectionEntry(object,size()-1,1);
        }
        end = true;
    }

    public boolean isStart(){
        return !end;
    }

    public String getTag() {
        return tag;
    }

    public List<T> getSectionValue(){
        List<T> result = new ArrayList<T>(size());
        for(int i =0;i<size();i++){
            if(entries[i].getType()==0){
                result.add((T)entries[i].getValue());
            }
        }
        return result;
    }

    public Section parent() {
        return parent;
    }

    public List<Section> list() {
        return children;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    private class SectionEntry<T>{
        private T value;
        private int no;
        private int type;

        public SectionEntry(T value, int no, int type) {
            this.value = value;
            this.no = no;
            this.type = type;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public int getNo() {
            return no;
        }

        public void setNo(int no) {
            this.no = no;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
