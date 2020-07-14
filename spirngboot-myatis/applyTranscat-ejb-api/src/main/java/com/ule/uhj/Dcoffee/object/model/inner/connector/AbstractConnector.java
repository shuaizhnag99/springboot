package com.ule.uhj.Dcoffee.object.model.inner.connector;

import com.ule.uhj.Dcoffee.core.annotation.Dcoffee;
import com.ule.uhj.Dcoffee.object.coupling.exception.MapDisconnectedException;
import com.ule.uhj.Dcoffee.object.model.inner.source.Source;
import com.ule.uhj.Dcoffee.object.model.inner.util.SourceWeight;
import com.ule.uhj.Dcoffee.object.model.interaction.coffee.Coffee;

import java.util.*;

/**
 * Created by zhengxin on 2018/3/14.
 */
public abstract class AbstractConnector implements Connector {
    @Override
    public Coffee connect(Collection<? extends Source> iterable) throws MapDisconnectedException{
        if(iterable instanceof Stack){
            return connect((Stack)iterable);
        }
        if(iterable instanceof ArrayList){
            return connect(buildSourceStack(buildSourceMap((List)iterable)));
        }else{
//            List<Source> sources = new ArrayList<Source>(Arrays.asList((Source[])iterable.toArray()));
            
            return connect(iterable);
        }
    }

    public abstract Coffee connect(Stack<? extends Source> stack);

    public Map<Source,Source> buildSourceMap(List<Source> sources) throws MapDisconnectedException {
        Map<Source,Source> sourceMap = new HashMap<Source, Source>();
        if(sources.size()<=0){
            return null;
        }

        Source currentSource = sources.get(0);
        sources.remove(currentSource);
        int size = sources.size();

        while(size>0){
            boolean isConnected = false;
            for(int i = 0;i<sources.size();i++){
                Source indexSource = sources.get(i);
                if(currentSource.canBeConnect(indexSource)){
                    sourceMap.put(currentSource,indexSource);
                    currentSource = indexSource;
                    sources.remove(indexSource);
                    size = sources.size();
                    isConnected = true;
                    break;
                }
            }
            if(isConnected){
                continue;
            }else{
                throw new MapDisconnectedException("原图不具备连通性！");
            }
        }

        return sourceMap;
    }

    public Stack<Source> buildSourceStack(Map<Source,Source> sourceMap){
        Stack<Source> result = new Stack<Source>();
        List<Source> vNew = new ArrayList<Source>();
        List<Source> v = new ArrayList<Source>(sourceMap.keySet());
        Source initSource = v.get(0);
        result.push(initSource);
        vNew.add(initSource);
        v.remove(initSource);

        int size = v.size();
        while(size>0){
            SourceWeight minWeight = new SourceWeight(null,Integer.MAX_VALUE);
            for(int i = 0;i<size;i++){
                Source currentSource = v.get(i);
                for(int j = 0;j<vNew.size();j++){
                    Source targetSource = vNew.get(j);
                    if(currentSource.canBeConnect(targetSource)){
                        int currentWeight = currentSource.getSlotWeight(targetSource);
                        minWeight = currentWeight<=minWeight.getWeight() ? new SourceWeight(currentSource,currentWeight) : minWeight;
                    }
                }
            }
            Source needSource = minWeight.getSource();
            result.push(needSource);
            vNew.add(needSource);
            v.remove(needSource);
            size = v.size();
        }
        return result;
    }

}