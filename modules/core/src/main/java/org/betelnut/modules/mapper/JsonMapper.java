package org.betelnut.modules.mapper;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * 封装简单的Jackson, 实现JSON String <-> Java Object的Mapper
 *
 * 封装不同的输出风格，使用不同的builder函数创建实例
 *
 * @author James
 * @version 1.0-SNAPSHOT
 * @since 2014-04-15
 */
public class JsonMapper {

    private static Logger logger = LoggerFactory.getLogger(JsonMapper.class);

    private ObjectMapper mapper;


    public JsonMapper() {
        this(null);
    }

    public JsonMapper(JsonInclude.Include include) {
        mapper = new ObjectMapper();
        // 设置输出时包含属性的风格
        if (include != null) {
            mapper.setSerializationInclusion(include);
        }
        // 设置输入时忽略在JSON字符串里存在但Java实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    /**
     * 创建只输出非NULL且非Empty(如List.isEmpty)的属性到Json字符串的Mapper,建议在外部接口使用
     */
    public static JsonMapper nonEmptyMapper() {
        return new JsonMapper(JsonInclude.Include.NON_EMPTY);
    }

    /**
     * 创建只输出初始值被改变的属性到Json字符串的Mapper，最节约的存储方式，建议在内部接口使用
     * @return
     */
    public static JsonMapper nonDefaultMapper() {
        return new JsonMapper(JsonInclude.Include.NON_DEFAULT);
    }

    /**
     * Object 可以是POJO，也可以是Collection或数组
     * 如果对象为Null, 则返回"null".
     * 如果集合为空集合,则返回"[]"
     *
     * @param object 需要转换成String Json的Object对象
     * @return Json字符串
     */
    public String toJson(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.warn("Write to json string error:" + object, e);
            return null;
        }
    }

    /**
     *
     * 反序列化POJO或者简单的Collection如List<String>
     *
     * 如果Json字符串为Null或者"null"字符串则返回Null.
     * 如果Json字符串为"[]"则返回空集合.
     *
     * 如果需要反序列化复杂的Collection如List<MyBean>,使用fromJson(String, JavaType)
     *
     *
     * @param jsonString json String 对象
     * @param clazz 需要序列化的Java Type Class对象
     * @param <T> 返回泛型定义
     * @return 反序列化过后的Java Object对象
     *
     * @see #fromJson(String, JavaType)
     */
    public <T> T fromJson(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.warn("Parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 反序列化复杂的Collection<Bean>，先使用createCollectionType()或constructMapType()，构造类型，然后调用本函数
     */
    public <T> T fromJson(String jsonString, JavaType javaType) {
        if (StringUtils.isEmpty(jsonString)) {
            return null;
        }

        try {
            return (T) mapper.readValue(jsonString, javaType);
        } catch (IOException e) {
            logger.warn("Parse json string error:" + jsonString, e);
            return null;
        }
    }

    /**
     * 构造Collection类型
     *
     * @param collectionClass Collection的Class对象
     * @param elementClass Collection所承载的Bean对象Class
     * @return 构造成的JavaType Collection类型
     */
    public JavaType constructCollectionType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
        return mapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
    }

    /**
     * 构造Map转换的JavaType对象
     *
     * @param mapClass Map类型的Class
     * @param keyClass Key对应的Class
     * @param valueClass Value对应的Class
     * @return 构造出的JavaType对象
     */
    public JavaType constructMapType(Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
        return mapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
    }

    /**
     * 当JSON里只含有Bean的部分属性时,更新一个已存在Bean,只覆盖部分的属性
     */
    public void update(String jsonString, Object object) {
        try {
            mapper.readerForUpdating(object).readValue(jsonString);
        } catch (JsonProcessingException e) {
            logger.warn("Update json string:" + jsonString + " to object:" + object + " error.", e);
        } catch (IOException e) {
            logger.warn("Update json string:" + jsonString + " to object:" + object + " error.", e);
        }
    }

    /**
     * 转换成JSONP的数据格式
     */
    public String toJsonP(String functionName, Object object) {
        return toJson(new JSONPObject(functionName, object));
    }

    /**
     * 设定是否使用Enum的toString函数来读写Enum,
     * 为False的时候使用Enum的name()函数来读写Enum,默认为False
     * 注意本函数一定要在Mapper创建后，所有的读写动作之前调用
     */
    public void enableEnumUseToString() {
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
    }

    /**
     * 支持使用Jaxb的Annotation，使得POJO上的annotation不用与Jackson耦合
     * 默认会先查找Jaxb的annotation,如果没有查找到再查找jackson的annotation.
     */
    public void enableJaxbAnnotation() {
        JaxbAnnotationModule module = new JaxbAnnotationModule();
        mapper.registerModule(module);
    }

    public ObjectMapper getMapper() {
        return mapper;
    }




}
