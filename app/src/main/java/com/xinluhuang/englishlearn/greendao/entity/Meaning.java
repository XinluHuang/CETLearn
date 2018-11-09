package com.xinluhuang.englishlearn.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "dict_collins_meaning")
public class Meaning {
    @Property(nameInDb = "id")
    private int id;
    @Property(nameInDb = "wid")
    private int wid;
    @Property(nameInDb = "type")
    private String type;
    @Property(nameInDb = "en")
    private String en;
    @Property(nameInDb = "cn")
    private String cn;
    @Generated(hash = 359368361)
    public Meaning(int id, int wid, String type, String en, String cn) {
        this.id = id;
        this.wid = wid;
        this.type = type;
        this.en = en;
        this.cn = cn;
    }
    @Generated(hash = 688675587)
    public Meaning() {
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getWid() {
        return this.wid;
    }
    public void setWid(int wid) {
        this.wid = wid;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getEn() {
        return this.en;
    }
    public void setEn(String en) {
        this.en = en;
    }
    public String getCn() {
        return this.cn;
    }
    public void setCn(String cn) {
        this.cn = cn;
    }
}