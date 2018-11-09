package com.xinluhuang.englishlearn.dict;

import com.xinluhuang.englishlearn.util.SPUtil;

public class HtmlFont {
    public static final String BLANK = " ";
    public static String titleWord(String word) {
        return new String("<h1><font color='blue'>" + word + "</font></h1>");
    }

    public static String Meaning(int position, String type, String meaningEN, String meaningCH) {
        String color="black";
        if(!(Boolean) SPUtil.get(SPUtil.isDay,true)){
            color="white";
        }
        return new String("<p><font color='"+color+"'>" + position + ".<b>" + type + "</b>" + BLANK + meaningEN + BLANK + meaningCH + "</font></p>");
    }

    public static String Example(String English, String Chinese) {
        return new String("<p><font color='gray'>例:" + English + "<br/>" + Chinese + "</font></p>");
    }
}
