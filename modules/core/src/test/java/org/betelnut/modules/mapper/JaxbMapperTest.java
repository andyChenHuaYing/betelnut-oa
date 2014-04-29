package org.betelnut.modules.mapper;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 *
 * 演示JAXB2.0的对象转换为Xml及DOM4j的使用.
 *
 * 演示使用的Xml如下:
 * <pre>
 * <?xml version="1.0" encoding="UTF-8">
 * <user id="1">
 *     <name>james</name>
 *     <interests>
 *         <interest>movie</interest>
 *         <interest>coding</interest>
 *     </interests>
 * </user>
 * </pre>
 *
 * @author James
 * @version 1.0-SNAPSHOT
 * @since 2014-04-19
 */
public class JaxbMapperTest {

    @Test
    public void objectToXml() {
        User user = new User();
        user.setId(1L);
        user.setName("james");

        user.getInterests().add("movie");
        user.getInterests().add("coding");

        String xml = JaxbMapper.toXml(user, "UTF-8");
        System.out.println("Jaxb Object to Xml result:\n" + xml);
        assertXmlByDom4j(xml);
    }

    @Test
    public void xmlToObject() {
        String xml = generateXmlByDom4j();
        User user = JaxbMapper.fromXml(xml, User.class);

        System.out.println("Jaxb Xml to Object result:\n" + user);

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getInterests()).containsOnly("movie", "coding");
    }

    @Test
    public void toXmlWithListAsRoot() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("james");
        user1.setPassword("Hello");

        User user2 = new User();
        user2.setId(2L);
        user2.setName("kate");
        user2.setPassword("World");

        List<User> userList = Lists.newArrayList(user1, user2);

        String xml = JaxbMapper.toXml(userList, "userList", User.class, "UTF-8");
        System.out.println("Jaxb Object List to Xml result:\n" + xml);
    }

    /**
     * 使用Dom4j生成要测试用的Xml字符串
     *
     * @return xml字符串
     */
    private static String generateXmlByDom4j() {
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement("user").addAttribute("id", "1");

        root.addElement("name").setText("james");

        Element interests = root.addElement("interests");
        interests.addElement("interest").addText("movie");
        interests.addElement("interest").addText("coding");

        return document.asXML();

    }

    /**
     * 使用Dom4j去验证生成的Xml是否正确
     *
     * @param xml
     */
    private static void assertXmlByDom4j(String xml) {
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            fail(e.getMessage());
        }

        Element user = doc.getRootElement();
        assertThat(user.attribute("id").getValue()).isEqualTo("1");

        Element interests = (Element) doc.selectSingleNode("//interests");
        assertThat(interests.elements()).hasSize(2);
        assertThat(((Element) interests.elements().get(0)).getText()).isEqualTo("movie");
    }

    @XmlRootElement
    @XmlType(propOrder = { "name", "interests" })
    public static class User {

        private Long id;
        private String name;
        private String password;

        private List<String> interests = Lists.newArrayList();

        // 设置为转换为xml节点的属性
        @XmlAttribute
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @XmlTransient
        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        @XmlElementWrapper(name = "interests")
        @XmlElement(name = "interest")
        public List<String> getInterests() {
            return interests;
        }

        public void setInterests(List<String> interests) {
            this.interests = interests;
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }

}
