package org.betelnut.application.hdfs.bean;

import java.io.Serializable;
import java.util.*;

/**
 * 文件实体bean
 *
 * @author James
 */
public class Document implements Serializable, Comparable<Document> {

    private static final String TYPE = "betelnut:document";

    /** 文件所在的路径 */
    private String path;
    /** 文件的作者 */
    private String author;
    /** 文件的创建时间 */
    private Calendar created;
    /** 最后的更改时间 */
    private Calendar lastModified;
    /** 文件mimeType */
    private String mimeType;
    /** 文件是否上锁 */
    private boolean locked;
    /** 文件的上锁信息 */
    private Lock lockInfo;
    /** 文件的uuid */
    private String uuid;
    /** 是否可以转换成pdf */
    private boolean convertiableToPdf;
    /** 是否可以转换成swf */
    private boolean convertiableToSwf;
    /** 文件的关键词 */
    private Set<String> keywords = new HashSet<String>();
    /** 文件note信息 */
    private List<Note> notes = new ArrayList<Note>();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Calendar getCreated() {
        return created;
    }

    public void setCreated(Calendar created) {
        this.created = created;
    }

    public Calendar getLastModified() {
        return lastModified;
    }

    public void setLastModified(Calendar lastModified) {
        this.lastModified = lastModified;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public Lock getLockInfo() {
        return lockInfo;
    }

    public void setLockInfo(Lock lockInfo) {
        this.lockInfo = lockInfo;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isConvertiableToPdf() {
        return convertiableToPdf;
    }

    public void setConvertiableToPdf(boolean convertiableToPdf) {
        this.convertiableToPdf = convertiableToPdf;
    }

    public boolean isConvertiableToSwf() {
        return convertiableToSwf;
    }

    public void setConvertiableToSwf(boolean convertiableToSwf) {
        this.convertiableToSwf = convertiableToSwf;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    @Override
    public int compareTo(Document o) {
        return 0;
    }
}
