package org.betelnut.application.hdfs.bean;

import java.io.Serializable;

/**
 * 上锁信息
 *
 * @author James
 */
public class Lock implements Serializable {

    /** 所属者 */
    private String owner;
    /** 文件的路径 */
    private String notePath;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getNotePath() {
        return notePath;
    }

    public void setNotePath(String notePath) {
        this.notePath = notePath;
    }
}
