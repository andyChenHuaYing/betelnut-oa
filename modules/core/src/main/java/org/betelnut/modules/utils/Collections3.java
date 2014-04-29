package org.betelnut.modules.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Collections tool
 * <p/>
 * JDK collection tool named Collections
 * Guava collection tool named Collections2
 * so we named this class as Collections3
 *
 * @author James
 * @version 1.0-SNAPSHOT
 * @since 2014-04-12
 */
public class Collections3 {

    /**
     * Extract collection object to Map object.
     *
     * @param collection collection
     * @param keyPropertyName Key
     * @param valuePropertyName Value
     * @return Map Object
     */
    public static Map extractToMap(final Collection collection, final String keyPropertyName,
            final String valuePropertyName) {
        Map map = new HashMap(collection.size());

        try {
            for (Object obj : collection) {
                map.put(PropertyUtils.getProperty(obj, keyPropertyName), PropertyUtils.getProperty(obj, valuePropertyName));
            }
        } catch (Exception e) {
            throw Reflections.convertReflectionExceptionToUnchecked(e);
        }

        return map;
    }

    /**
     * Get collection object field value into new ArrayList.
     *
     * @param collection collection
     * @param propertyName field name
     * @return new List Object
     */
    public static List extractToList(final Collection collection, final String propertyName) {
        List list = new ArrayList(collection.size());

        try {
            for (Object obj : collection) {
                list.add(PropertyUtils.getProperty(obj, propertyName));
            }
        } catch (Exception e) {
            throw Reflections.convertReflectionExceptionToUnchecked(e);
        }

        return list;
    }

    /**
     * Get collection object field value combine to new String object.
     *
     * @param collection collection
     * @param propertyName field name
     * @param separator separator
     * @return String value
     */
    public static String extractToString(final Collection collection, final String propertyName, final String separator) {
        List list = extractToList(collection, propertyName);
        return StringUtils.join(list, separator);
    }

    public static String convertToString(final Collection collection, final String separator) {
        return StringUtils.join(collection, separator);
    }

    /**
     * eg.
     * James<->Allen<->LeBron
     *
     * prefix: _
     * postfix: $
     *
     * after invoke this method:
     * _James$_Allen$_LeBron$
     *
     * @param collection collection
     * @param prefix prefix
     * @param postfix post fix
     * @return new String value
     */
    public static String convertToString(final Collection collection, final String prefix, final String postfix) {
        StringBuilder builder = new StringBuilder();
        for (Object obj : collection) {
            builder.append(prefix).append(obj).append(postfix);
        }

        return builder.toString();
    }

    /**
     * Judge the collection is empty or null
     */
    public static boolean isEmpty(Collection collection) {
        return (collection == null) || collection.isEmpty();
    }

    /**
     * Judge the map is empty or null
     */
    public static boolean isEmpty(Map map) {
        return (map == null) || map.isEmpty();
    }

    public static boolean isNotEmpty(Collection collection) {
        return (collection != null) && !(collection.isEmpty());
    }

    public static <T> T getFirst(Collection<T> collection) {
        if (isEmpty(collection)) {
            return null;
        }

        return collection.iterator().next();
    }

    /**
     * Get the collection last element.
     */
    public static <T> T getLast(Collection<T> collection) {
        if (isEmpty(collection)) {
            return null;
        }

        // if the collection is List, the List type provide the get method.
        if (collection instanceof List) {
            List<T> list = (List<T>) collection;
            return list.get(list.size() - 1);
        }

        // not the List type, use Iterator interface iterator all element.
        Iterator<T> iterator = collection.iterator();
        while (true) {
            T current = iterator.next();
            if (!iterator.hasNext()) {
                return current;
            }
        }
    }

    /**
     * a + b = new List
     *
     * @param a first list
     * @param b second list
     * @return a+b
     */
    public static <T> List<T> union(final Collection<T> a, final Collection<T> b) {
        List<T> result = new ArrayList<T>(a);
        result.addAll(b);
        return result;
    }

    /**
     * a - b = new List
     * @param a first list
     * @param b second list
     * @return a-b
     */
    public static <T> List<T> subtract(final Collection<T> a, final Collection<T> b) {
        List<T> list = new ArrayList<T>();
        for (T element : b) {
            list.remove(element);
        }
        return list;
    }

    /**
     * 取两个集合的并集
     */
    public static <T> List<T> intersection(Collection<T> a, Collection<T> b) {
        List<T> list = new ArrayList<T>();

        for (T element : a) {
            if (b.contains(element)) {
                list.add(element);
            }
        }

        return list;
    }


}
