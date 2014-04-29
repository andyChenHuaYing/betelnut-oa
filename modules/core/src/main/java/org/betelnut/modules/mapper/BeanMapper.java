package org.betelnut.modules.mapper;

import com.google.common.collect.Lists;
import org.dozer.DozerBeanMapper;

import java.util.Collection;
import java.util.List;

/**
 * Simple Dozer util class. Bean<->Bean mapper:
 *
 * 1. Take the singleton Mapper class.
 * 2. Convert the return type.
 * 3. Convert the collection's all object.
 * 4. Copy A object to B object and create new method.
 *
 * Chinese Specification:
 * 1. 持有Mapper的单例.
 * 2. 返回值类型转换.
 * 3. 批量转换Collection中的所有对象.
 * 4. 区分创建新的B对象与将对象A值复制到已存在的B对象两种函数.
 *
 * @author James
 * @version 1.0-SNAPSHOT
 * @since 2014-04-12
 */
public class BeanMapper {

    /**
     * Dozer singleton object, avoid create serial objects.
     * 持有Dozer单例, 避免重复创建DozerMapper消耗资源.
     */
    private static DozerBeanMapper dozer = new DozerBeanMapper();

    /**
     * Basic map method
     * 基于Dozer转换对象的类型.
     *
     * @param source source object
     * @param destinationClass destination class
     * @param <T> return type...
     * @return new object which copped object value
     */
    public static <T> T map(Object source, Class<T> destinationClass) {
        return dozer.map(source, destinationClass);
    }

    /**
     *
     * copy collection object
     *
     * @param sourceList source collection object
     * @param destinationClass destination collection class
     * @param <T> return type
     * @return new collection object
     */
    public static <T> List<T> mapList(Collection sourceList, Class<T> destinationClass) {
        List<T> destinationList = Lists.newArrayList();
        for (Object sourceObject : sourceList) {
            T destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }

    /**
     * copy method, copy the source object field value to destination object.
     *
     * @param source source object
     * @param destinationObject destination object
     */
    public static void copy(Object source, Object destinationObject) {
        dozer.map(source, destinationObject);
    }


}
