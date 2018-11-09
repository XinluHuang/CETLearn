package com.xinluhuang.englishlearn.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "tbWord")
public class CET4Word extends CETWord{
    @Id
    @Property(nameInDb = "id")
    private int id;
    @Property(nameInDb = "word")
    private String word;
    @Property(nameInDb = "meaning")
    private String meaning;
    @Property(nameInDb = "spell")
    private String spell;
    @Generated(hash = 1405846305)
    public CET4Word(int id, String word, String meaning, String spell) {
        this.id = id;
        this.word = word;
        this.meaning = meaning;
        this.spell = spell;
    }
    @Generated(hash = 1836386553)
    public CET4Word() {
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
    public String getMeaning() {
        return this.meaning;
    }
    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
    public String getSpell() {
        return this.spell;
    }
    public void setSpell(String spell) {
        this.spell = spell;
    }

}
