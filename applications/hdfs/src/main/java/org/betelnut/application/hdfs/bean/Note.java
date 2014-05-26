package org.betelnut.application.hdfs.bean;

import java.io.Serializable;
import java.util.Calendar;

/**
 * 文件note
 *
 * @author James
 */
public class Note implements Serializable {
    /** 创建时间 */
    private Calendar date;
    /** 创建者 */
    private String user;
    /** 注释的信息 */
    private String text;
    /** 所在路径 */
    private String path;

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
