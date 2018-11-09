package com.xinluhuang.englishlearn.greendao.entity;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity(nameInDb = "markword")
public class MarkWord {
    @Id
    @Property(nameInDb = "id")
    private Long id;
    @Property(nameInDb = "word")
    private String word;
    @Generated(hash = 1549844084)
    public MarkWord(Long id, String word) {
        this.id = id;
        this.word = word;
    }
    @Generated(hash = 1276201899)
    public MarkWord() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getWord() {
        return this.word;
    }
    public void setWord(String word) {
        this.word = word;
    }

}
