package com.xinluhuang.englishlearn.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "dict_words")
public class Word {
    @Property(nameInDb = "id")
    private int id;
    @Property(nameInDb = "word")
    private String word;
    @Generated(hash = 849489701)
    public Word(int id, String word) {
        this.id = id;
        this.word = word;
    }
    @Generated(hash = 3342184)
    public Word() {
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getWord() {
        return this.word;
    }
    public void setWord(String word) {
        this.word = word;
    }
}