package com.xinluhuang.englishlearn.greendao.entity;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity(nameInDb = "searchword")
public class SearchWord {
    @Id
    @Property(nameInDb = "id")
    private Long id;
    @Property(nameInDb = "word")
    private String word;
    @Generated(hash = 1390576659)
    public SearchWord(Long id, String word) {
        this.id = id;
        this.word = word;
    }
    @Generated(hash = 407254878)
    public SearchWord() {
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
