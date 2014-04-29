package org.betelnut.modules.mapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.betelnut.modules.utils.Exceptions;
import org.betelnut.modules.utils.Reflections;

import javax.xml.bind.*;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.namespace.QName;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * JavaBean<->Xml
 *
 * Support for root object is collection type.
 * 在创建时需要设定所有需要序列化的ROOT对象的Class
 * 特别支持Root对象是Collection的情形
 *
 * @author James
 * @version 1.0-SNAPSHOT
 * @since 2014-04-12
 */
public class JaxbMapper {
    private static ConcurrentMap<Class, JAXBContext> jaxbContexts = new ConcurrentHashMap<Class, JAXBContext>();

    /**
     * Java Object -> xml without encoding.
     *
     * @param root Java object
     * @return xml string object
     */
    public static String toXml(Object root) {
        Class clazz = Reflections.getUserClass(root);
        return toXml(root, clazz, null);
    }

    /**
     * 转换Object->Xml，根据相应的编码格式Encoding
     *
     * @param root 待转换的Object对象
     * @param encoding 编码格式
     * @return String 类型的Xml对象
     */
    public static String toXml(Object root, String encoding) {
        Class clazz = Reflections.getUserClass(root);
        return toXml(root, clazz, encoding);
    }

    /**
     * 转换Object->Xml，根据相应的编码格式Encoding
     *
     * @param root 待转换的Object对象
     * @param clazz 待转换的Class对象
     * @param encoding 编码格式
     * @return String 类型的Xml对象
     */
    public static String toXml(Object root, Class clazz, String encoding) {
        try {
            StringWriter writer = new StringWriter();
            createMarShaller(clazz, encoding).marshal(root, writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw Exceptions.unchecked(e);
        }
    }

    /**
     * 转换Collection->Xml对象
     *
     * @param root 待转换的Object对象
     * @param rootName 待转换的Class对象
     * @param clazz Class对象
     * @return Xml
     */
    public static String toXml(Collection<?> root, String rootName, Class clazz) {
        return toXml(root, rootName, clazz, null);
    }

    /**
     * 转换Collection->Xml对象方法
     *
     * @param root Collection对象
     * @param rootName Xml的根节点名称
     * @param clazz Collection中的节点对象Class
     * @param encoding Xml的编码格式
     * @return Xml对象
     */
    public static String toXml(Collection<?> root, String rootName, Class clazz, String encoding) {
        try {
            CollectionWrapper wrapper = new CollectionWrapper();
            wrapper.collection = root;

            JAXBElement<CollectionWrapper> wrapperElement = new JAXBElement<CollectionWrapper>(new QName(rootName),
                    CollectionWrapper.class, wrapper);
            StringWriter writer = new StringWriter();
            createMarShaller(clazz, encoding).marshal(wrapperElement, writer);

            return writer.toString();
        } catch (JAXBException e) {
            throw Exceptions.unchecked(e);
        }
    }

    public static <T> T fromXml(String xml, Class<T> clazz) {
        try {
            StringReader reader = new StringReader(xml);
            return (T) createUnmarshaller(clazz).unmarshal(reader);
        } catch (JAXBException e) {
            throw Exceptions.unchecked(e);
        }
    }


    public static Unmarshaller createUnmarshaller(Class clazz) {
        try {
            JAXBContext jaxbContext = getJaxbContext(clazz);
            return jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            throw Exceptions.unchecked(e);
        }

    }

    /**
     * 创建Marshaller对象
     *
     * @param clazz
     * @param encoding
     * @return
     */
    public static Marshaller createMarShaller(Class clazz, String encoding) {
        try {
            JAXBContext jaxbContext = getJaxbContext(clazz);

            Marshaller marshaller = jaxbContext.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            if (StringUtils.isNotBlank(encoding)) {
                marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);
            }

            return marshaller;
        } catch (JAXBException e) {
            throw Exceptions.unchecked(e);
        }

    }

    protected static JAXBContext getJaxbContext(Class clazz) {
        Validate.notNull(clazz, "'clazz' must not be null");
        JAXBContext jaxbContext = jaxbContexts.get(clazz);

        if (jaxbContext == null) {
            try {
                jaxbContext = jaxbContext.newInstance(clazz, CollectionWrapper.class);
                jaxbContexts.putIfAbsent(clazz, jaxbContext);
            } catch (JAXBException e) {
                throw new RuntimeException("Could not instantiate JAXBContext for class[" + clazz + "]" +
                        e.getMessage(), e);
            }
        }

        return jaxbContext;
    }

    public static class CollectionWrapper {
        @XmlAnyElement
        protected Collection<?> collection;

    }
}
