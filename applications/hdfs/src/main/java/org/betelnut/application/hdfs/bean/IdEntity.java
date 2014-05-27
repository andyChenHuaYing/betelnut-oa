package org.betelnut.application.hdfs.bean;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author James
 *
 * Id generator:
 * http://stackoverflow.com/questions/6356834/using-hibernate-uuidgenerator-via-annotations
 */
@MappedSuperclass
public abstract class IdEntity {
    protected String uuid;

    // 这里直接使用了hibernate的uuid2的生成方式，当然也可以使用assigned
    // assigned的模式就需要实体在与数据库进行交互的时候手动的去将id设值
    // 使用hibernate的uuid生成方式并没有自己去编写的好。
    // 不过使用hibernate自己自带的生成方式更加的方便
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
