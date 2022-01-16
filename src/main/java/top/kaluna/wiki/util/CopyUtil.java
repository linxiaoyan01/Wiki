package top.kaluna.wiki.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yuery
 * @date 2022/1/15/0015 - 23:37
 */
public class CopyUtil {
    /**
     * 单个对象复制
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T copy(Object source, Class<T> clazz){
        if(source == null){
            return null;
        }
        T obj = null;
        try{
            obj = clazz.newInstance();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        BeanUtils.copyProperties(source,obj);
        return obj;
    }

    public static <T> List<T> copyList(List source, Class<T> clazz){
        List<T> target = new ArrayList<>();
        if(!CollectionUtils.isEmpty(source)){
            for(Object c : source){
                T obj = copy(c, clazz);
                target.add(obj);
            }
        }
        return target;
    }
}
