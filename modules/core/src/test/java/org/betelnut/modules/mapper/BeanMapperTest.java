package org.betelnut.modules.mapper;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test for BeanMapper tool class function.
 *
 * @author james
 * @version 1.0-SNAPSHOT
 * @since 2014-04-12
 */
public class BeanMapperTest {

    /**
     * test method BeanMapper.map()
     */
    @Test
    public void testMap() {
        User user = new User();
        user.setUsername("James Chow");
        user.setAge(24);
        user.setBirthDay(new Date());
        user.setPassword("admin");

        UserDTO userDTO = BeanMapper.map(user, UserDTO.class);
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.getAge(), userDTO.getAge());
        assertEquals(user.getBirthDay(), userDTO.getBirthDay());
        assertEquals(user.getPassword(), userDTO.getPassword());
        assertNull(userDTO.getPlainPassword());
    }

    /**
     * test method BeanMapper.mapList()
     */
    @Test
    public void testMapList() {
        List<User> userList = Lists.newArrayList();
        for (int i=1; i<=10; i++) {
            User user = new User();
            user.setUsername("user :: username :: " + i);
            user.setPassword("user :: password :: " + i);
            user.setBirthDay(new Date());
            user.setAge(i);
            userList.add(user);
        }

        List<UserDTO> userDTOList = BeanMapper.mapList(userList, UserDTO.class);
        assertEquals(userDTOList.size(), 10);
        assertEquals(userDTOList.get(4).getUsername(), "user :: username :: 5");
    }

    /**
     * test method BeanMapper.copy()
     */
    @Test
    public void testCopy() {
        User user = new User();
        user.setUsername("James Chow");
        user.setAge(24);
        user.setBirthDay(new Date());
        user.setPassword("admin");

        UserDTO userDTO = new UserDTO();
        BeanMapper.copy(user, userDTO);
        assertEquals(user.getUsername(), userDTO.getUsername());
        assertEquals(user.getAge(), userDTO.getAge());
        assertEquals(user.getBirthDay(), userDTO.getBirthDay());
        assertEquals(user.getPassword(), userDTO.getPassword());
        assertNull(userDTO.getPlainPassword());

    }

    /**
     * Just for test constructor, coverage for 100%
     */
    @Test
    public void testConstructorForCoverage() {
        BeanMapper mapper = new BeanMapper();
    }


    public static class User {
        private String username;

        private String password;

        private Integer age;

        private Date birthDay;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Date getBirthDay() {
            return birthDay;
        }

        public void setBirthDay(Date birthDay) {
            this.birthDay = birthDay;
        }
    }

    public static class UserDTO {
        private String username;

        private String password;

        private Integer age;

        private String plainPassword;

        private Date birthDay;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getPlainPassword() {
            return plainPassword;
        }

        public void setPlainPassword(String plainPassword) {
            this.plainPassword = plainPassword;
        }

        public Date getBirthDay() {
            return birthDay;
        }

        public void setBirthDay(Date birthDay) {
            this.birthDay = birthDay;
        }
    }

}
