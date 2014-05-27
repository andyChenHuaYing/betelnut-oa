package org.betelnut.application.hdfs.bean;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 上锁信息
 *
 * @author James
 */
@Entity
@Table(name = "bo_lock")
public class Lock extends IdEntity implements Serializable {

    /** 所属者 */
    private String owner;
    /** 文件的路径 */
    private String notePath;

    private Document document;

    @OneToOne
    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

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
