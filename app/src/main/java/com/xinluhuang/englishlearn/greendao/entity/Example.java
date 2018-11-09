package com.xinluhuang.englishlearn.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "dict_collins_example")
public class Example {
    @Property(nameInDb = "mid")
    private int mid;
    @Property(nameInDb = "en")
    private String en;
    @Property(nameInDb = "cn")
    private String cn;
    @Generated(hash = 1096946606)
    public Example(int mid, String en, String cn) {
        this.mid = mid;
        this.en = en;
        this.cn = cn;
    }
    @Generated(hash = 646563864)
    public Example() {
    }
    public int getMid() {
        return this.mid;
    }
    public void setMid(int mid) {
        this.mid = mid;
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
