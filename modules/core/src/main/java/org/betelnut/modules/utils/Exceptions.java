package org.betelnut.modules.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Exceptions Util
 *
 * @author James
 * @version 1.0-SNAPSHOT
 * @since 2014-04-15
 */
public class Exceptions {

    /**
     * switch CheckedException to UncheckedException
     */
    public static RuntimeException unchecked(Throwable ex) {
        if (ex instanceof RuntimeException) {
            return (RuntimeException) ex;
        }

        return new RuntimeException(ex);
    }

    /**
     * Get exception stack trace os String object.
     */
    public static String getStackTraceAsString(Throwable ex) {
        StringWriter stringWriter = new StringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /**
     * Get error message with nestedException
     */
    public static String getErrorMessageWithNestedException(Throwable ex) {
        Throwable nestedException = ex.getCause();
        return new StringBuilder().append(ex.getMessage()).append(" nested exception is ")
                .append(nestedException.getClass().getName()).append(":").append(nestedException.getMessage())
                .toString();
    }

    /**
     * 获得异常的Root Cause.
     *
     * @param ex
     * @return
     */
    public static Throwable getRootCause(Throwable ex) {
        Throwable cause;
        while ((cause = ex.getCause()) != null) {
            return cause;
        }

        return ex;
    }

    /**
     * 判断一些异常是由什么引起的
     *
     * @param ex
     * @param causeExceptionClasses
     * @return
     */
    public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
        Throwable cause = ex;
        while (cause != null) {
            for (Class<? extends Exception> causeClass : causeExceptionClasses) {
                if (causeClass.isInstance(cause)) {
                    return true;
                }
            }
            cause = cause.getCause();
        }
        return false;
    }
}
