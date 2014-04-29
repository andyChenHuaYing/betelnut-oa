package org.betelnut.modules.mapper;

import static org.assertj.core.api.Assertions.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * JsonMapper工具类测试
 *
 * @author James
 * @version 1.0-SNAPSHOT
 * @since 2014-04-13
 */
public class JsonMapperTest {

    private static JsonMapper binder = JsonMapper.nonDefaultMapper();

    /**
     * 序列化Object/Collection到Json字符串
     */
    @Test
    public void toJson() {
        // Bean
        TestBean bean = new TestBean("A");
        String beanString = binder.toJson(bean);
        assertThat(beanString).isEqualTo("{\"name\":\"A\"}");

        // Map
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("name", "A");
        map.put("age", 2);
        String mapString = binder.toJson(map);
        assertThat(mapString).isEqualTo("{\"name\":\"A\",\"age\":2}");

        // List<String>
        List<String> stringList = Lists.newArrayList("A", "B", "C");
        String listString = binder.toJson(stringList);
        assertThat(listString).isEqualTo("[\"A\",\"B\",\"C\"]");

        // List<Bean>
        List<TestBean> beanList = Lists.newArrayList(new TestBean("A"), new TestBean("B"));
        String beanListString = binder.toJson(beanList);
        assertThat(beanListString).isEqualTo("[{\"name\":\"A\"},{\"name\":\"B\"}]");

        // Bean[]
        TestBean[] beanArray = new TestBean[] { new TestBean("A"), new TestBean("B") };
        String beanArrayString = binder.toJson(beanArray);
        assertThat(beanArrayString).isEqualTo("[{\"name\":\"A\"},{\"name\":\"B\"}]");

    }

    /**
     * 测试反序列化情况
     */
    @Test
    public void fromJson() throws IOException {
        // Bean
        String beanString = "{\"name\":\"A\"}";
        TestBean bean = binder.fromJson(beanString, TestBean.class);
        assertThat(bean.getName()).isEqualTo("A");

        // Map
        String mapString = "{\"name\":\"A\",\"age\":2}";
        Map<String, Object> map = binder.fromJson(mapString, HashMap.class);
        assertThat(map.size()).isEqualTo(2);
        assertThat(map.get("name")).isEqualTo("A");
        assertThat(map.get("age")).isEqualTo(2);

        // List<String>
        String listString = "[\"A\",\"B\",\"C\"]";
        List<String> stringList = binder.getMapper().readValue(listString, List.class);
        assertThat(stringList.size()).isEqualTo(3);
        assertThat(stringList.get(1)).isEqualTo("B");

        // List<Bean>
        String beanListString = "[{\"name\":\"A\"},{\"name\":\"B\"}]";
        List<TestBean> beanList = binder.getMapper().readValue(beanListString, new TypeReference<List<TestBean>>() {
        });
        assertThat(beanList.size()).isEqualTo(2);
        assertThat(beanList.get(1).getName()).isEqualTo("B");

    }

    @Test
    public void nullAndEmpty() {
        // toJson测试

        // Null Bean
        TestBean nullBean = null;
        String nullBeanString = binder.toJson(nullBean);
        assertThat(nullBeanString).isEqualTo("null");

        // Empty List
        List<String> emptyList = Lists.newArrayList();
        String emptyListString = binder.toJson(emptyList);
        assertThat(emptyListString).isEqualTo("[]");

        // fromJson 测试

        // Null String for Bean
        TestBean nullBeanResult = binder.fromJson(null, TestBean.class);
        assertThat(nullBeanResult).isNull();

        nullBeanResult = binder.fromJson("null", TestBean.class);
        assertThat(nullBeanResult).isNull();

        // Null/Empty String for List
        List nullListResult = binder.fromJson(null, List.class);
        assertThat(nullListResult).isNull();

        nullListResult = binder.fromJson("null", List.class);
        assertThat(nullListResult).isNull();

        nullListResult = binder.fromJson("[]", List.class);
        assertThat(nullListResult).isEmpty();


    }

    public static class TestBean {
        private String name;
        private String defaultValue = "hello";
        private String nullValue = null;

        public TestBean() {
        }

        public TestBean(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public void setDefaultValue(String defaultValue) {
            this.defaultValue = defaultValue;
        }

        public String getNullValue() {
            return nullValue;
        }

        public void setNullValue(String nullValue) {
            this.nullValue = nullValue;
        }

        @Override
        public String toString() {
            return "TestBean{" +
                    "name='" + name + '\'' +
                    ", defaultValue='" + defaultValue + '\'' +
                    ", nullValue='" + nullValue + '\'' +
                    '}';
        }
    }
}
