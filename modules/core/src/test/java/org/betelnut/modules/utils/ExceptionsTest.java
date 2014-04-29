package org.betelnut.modules.utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

import java.io.IOException;

/**
 *
 * 异常工具类的测试类
 *
 * @author James
 * @version 1.0-SNAPSHOT
 * @since 2014-04-19
 */
public class ExceptionsTest {

    @Test
    public void unchecked() {

        // 转换Exception to RuntimeException with cause
        Exception exception = new Exception("my exception");
        RuntimeException runtimeException = Exceptions.unchecked(exception);
        assertThat(runtimeException.getCause()).isEqualTo(exception);

        // 当异常为RuntimeException时，什么工作也不会做，这时候两个对象是相同的
        RuntimeException runtimeException2 = Exceptions.unchecked(runtimeException);
        assertThat(runtimeException2).isSameAs(runtimeException);

    }

    @Test
    public void getStackTraceAsString() {
        Exception exception = new Exception("my exception");
        RuntimeException runtimeException = new RuntimeException(exception);

        String stack = Exceptions.getStackTraceAsString(runtimeException);
        System.out.println(stack);
    }

    @Test
    public void isCausedBy() {
        IOException ioException = new IOException("my exception");
        IllegalStateException illegalStateException = new IllegalStateException(ioException);
        RuntimeException runtimeException = new RuntimeException(illegalStateException);

        assertThat(Exceptions.isCausedBy(runtimeException, IOException.class)).isTrue();
        assertThat(Exceptions.isCausedBy(runtimeException, IllegalStateException.class)).isTrue();
        assertThat(Exceptions.isCausedBy(runtimeException, Exception.class)).isTrue();
        assertThat(Exceptions.isCausedBy(runtimeException, IllegalAccessException.class)).isFalse();
    }
}
