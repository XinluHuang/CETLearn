package com.xinluhuang.englishlearn.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity(nameInDb = "tbWord")
public class CET6Word extends CETWord{
    @Id
    @Property(nameInDb = "id")
    private int id;
    @Property(nameInDb = "word")
    private String word;
    @Property(nameInDb = "meaning")
    private String meaning;
    @Property(nameInDb = "spell")
    private String spell;
    @Generated(hash = 985162481)
    public CET6Word(int id, String word, String meaning, String spell) {
        this.id = id;
        this.word = word;
        this.meaning = meaning;
        this.spell = spell;
    }
    @Generated(hash = 1145881498)
    public CET6Word() {
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
