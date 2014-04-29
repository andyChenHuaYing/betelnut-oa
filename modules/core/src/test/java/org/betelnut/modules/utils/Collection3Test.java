package org.betelnut.modules.utils;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Map;

/**
 * Collections3 Test
 *
 * @author James
 * @version 1.0-SNAPSHOT
 * @since 2014-04-13
 */
public class Collection3Test {

    private List<TestBean> list = Lists.newArrayList();

    @Before
    public void initList() {
        for (int i=1; i<=10; i++) {
            TestBean testBean = new TestBean(i, "name: " + i);
            list.add(testBean);
        }
    }

    @Test
    public void extractToMap() {
        Map<Integer, String> map = Collections3.extractToMap(list, "id", "name");
        assertThat(map.size()).isEqualTo(list.size());
        assertThat(map.get(list.get(4).getId())).isEqualTo(list.get(4).getName());
    }

    @Test
    public void extractToList() {
        List<String> nameList = Collections3.extractToList(list, "name");
        assertThat(list.get(5).getName()).isEqualTo(nameList.get(5));
    }

    @Test
    public void extractToString() {
        assertThat(Collections3.extractToString(list, "id", ",")).isEqualTo("1,2,3,4,5,6,7,8,9,10");
    }

    @Test
    public void convertCollectionToString() {
        List<String> list = Lists.newArrayList("aa", "bb");
        String result = Collections3.convertToString(list, ",");
        assertThat(result).isEqualTo("aa,bb");

        result = Collections3.convertToString(list, "<li>", "</li>");
        assertThat(result).isEqualTo("<li>aa</li><li>bb</li>");
    }

    public static class TestBean {
        private int id;

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public TestBean() {
        }

        public TestBean(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

}
