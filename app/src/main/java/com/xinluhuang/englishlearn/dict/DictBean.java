package com.xinluhuang.englishlearn.dict;

public class DictBean {
    private int id;
    private String word;
    private String meaning;
    private String uk;

    public DictBean(int id, String word, String meaning, String uk, String us) {
        this.id = id;
        this.word = word;
        this.meaning = meaning;
        this.uk = uk;
        this.us = us;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    //字符串处理
    public String getMeaningForHtml() {
        String str=meaning.substring(0,meaning.lastIndexOf("\\r\\n"));
        return str.replace("\\r\\n","<br />");
    }
    public String getHeadForHtml(){
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(HtmlFont.titleWord(word));
        stringBuilder.append("<h3><font color='grey'>" + "英"+uk +"  美"+us+ "</font></h3>");
        stringBuilder.append(getMeaningForHtml());
        return stringBuilder.toString();
    }
    public String getSingleLineMeaning(){
        return meaning.substring(0,meaning.indexOf("\\r\\n"));
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getUk() {
        return uk;
    }

    public void setUk(String uk) {
        this.uk = uk;
    }

    public String getUs() {
        return us;
    }

    public void setUs(String us) {
        this.us = us;
    }

    private String us;
}
