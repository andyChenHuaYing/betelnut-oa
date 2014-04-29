package org.betelnut.modules.utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

/**
 * Reflections Test
 *
 * @author James
 * @version 1.0-SNAPSHOT
 * @since 2014-04-13
 */
public class ReflectionsTest {

    @Test
    public void getAndSetFieldValue() {
        TestBean bean = new TestBean();

        // Get private field value.
        assertThat(Reflections.getFieldValue(bean, "privateField")).isEqualTo(1);
        // Get public field value not use getter method.
        assertThat(Reflections.getFieldValue(bean, "publicField")).isEqualTo(1);

        bean = new TestBean();
        // Set private field value.
        Reflections.setFieldValue(bean, "privateField", 2);
        assertThat(bean.inspectPrivateField()).isEqualTo(2);

        Reflections.setFieldValue(bean, "publicField", 2);
        assertThat(bean.inspectPublicField()).isEqualTo(2);

        try {
            Reflections.getFieldValue(bean, "notExist");
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {

        }

        try {
            Reflections.setFieldValue(bean, "notExist", 2);
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void invokeGetterAndSetter() {
        TestBean bean = new TestBean();
        assertThat(Reflections.invokeGetter(bean, "publicField")).isEqualTo(bean.inspectPublicField() + 1);

        bean = new TestBean();
        Reflections.invokeSetter(bean, "publicField", 10);
        assertThat(bean.inspectPublicField()).isEqualTo(10 + 1);
    }

    @Test
    public void invokeMethod() {
        TestBean bean = new TestBean();

        assertThat(Reflections
                              .invokeMethod(bean, "privateMethod", new Class<?>[] { String.class }, new Object[] { "James"}))
                .isEqualTo("hello James");

        assertThat(Reflections
                              .invokeMethodByName(bean, "privateMethod", new Object[] { "James" }))
                .isEqualTo("hello James");

        // method name does not exist, invokeMethod
        try {
            Reflections.invokeMethod(bean, "notExistMethodName", new Class[] { String.class }, new Object[] { "James" });
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {

        }

        // method paremeter class error.
        try {
            Reflections.invokeMethod(bean, "privateMethod", new Class[] { Integer.class }, new Object[] { 3 });
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {

        }

        // method name does not exist, invokeMethodByName
        try {
            Reflections.invokeMethodByName(bean, "notExistMethodName", new Object[] { "James" });
            failBecauseExceptionWasNotThrown(IllegalArgumentException.class);
        } catch (IllegalArgumentException e) {

        }
    }

    @Test
    public void getSuperClassGenericType() {
        assertThat(Reflections.getClassGenericType(TestBean.class)).isEqualTo(String.class);
        assertThat(Reflections.getClassGenericType(TestBean.class, 1)).isEqualTo(Long.class);

        assertThat(Reflections.getClassGenericType(TestBean2.class)).isEqualTo(Object.class);

        assertThat(Reflections.getClassGenericType(TestBean3.class)).isEqualTo(Object.class);

    }

    @Test
    public void convertReflectionExceptionToUnchecked() {
        IllegalArgumentException iae = new IllegalArgumentException();
        RuntimeException e = Reflections.convertReflectionExceptionToUnchecked(iae);
        assertThat(e.getCause()).isEqualTo(iae);

        Exception ex = new Exception();
        e = Reflections.convertReflectionExceptionToUnchecked(new InvocationTargetException(ex));
        assertThat(e.getCause()).isEqualTo(ex);

        RuntimeException re = new RuntimeException("abc");
        e = Reflections.convertReflectionExceptionToUnchecked(re);
        assertThat(e).hasMessage("abc");

        e = Reflections.convertReflectionExceptionToUnchecked(ex);
        assertThat(e).hasMessage("Unexpected Checked Exception.");
    }

    public static class ParentBean<T, ID> {}

    public static class TestBean extends ParentBean<String, Long> {
        /** hasn't getter/setter method */
        private int privateField = 1;
        /** has getter/setter method */
        private int publicField = 1;

        public int getPublicField() {
            return publicField + 1;
        }

        public void setPublicField(int publicField) {
            this.publicField = publicField + 1;
        }

        public int inspectPrivateField() {
            return privateField;
        }

        public int inspectPublicField() {
            return publicField;
        }

        /**
         * Private method that test for invoke private method.
         */
        private String privateMethod(String text) {
            return "hello " + text;
        }
    }

    public static class TestBean2 extends ParentBean {}

    public static class TestBean3 {
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
