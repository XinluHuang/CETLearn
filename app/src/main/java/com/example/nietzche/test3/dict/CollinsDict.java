package com.example.nietzche.test3.dict;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nietzche.test3.MyApplication;

public class CollinsDict {
    private String word;
    private int wid;//一个word仅一个wid
    private int[] mids;//一个word多个meaning，多个mid
    // 一个meaning只取一个例句
    private String[] type;
    private String[] MeaningsEN;
    private String[] MeaningsCN;
    private String[] ExamplesEN;
    private String[] ExamplesCN;
    private SQLiteDatabase database;

    public CollinsDict() {
        database = SQLiteDatabase.openOrCreateDatabase(MyApplication.getContext().getFilesDir().getParent()+"/databases/collins.db", null);
    }

    private static class HtmlFont {
        static final String BLANK = " ";

        private HtmlFont() {
        }

        static String titleWord(String word) {
            return new String("<h1><font color='blue'>" + word + "</font></h1>");
        }

        static String Meaning(int position, String type, String meaningEN, String meaningCH) {
            return new String("<p><font color='black'>" + position + ".<b>" + type + "</b>" + BLANK + meaningEN + BLANK + meaningCH + "</font></p>");
        }

        static String Example(String English, String Chinese) {
            return new String("<p><font color='gray'>例:" + English + "<br/>" + Chinese + "</font></p>");
        }
    }

    private void initMeanCount(int count) {
        mids = new int[count];
        type = new String[count];
        MeaningsEN = new String[count];
        MeaningsCN = new String[count];
        ExamplesEN = new String[count];
        ExamplesCN = new String[count];
    }

    public boolean find(String word) {
        this.word = word;
        Cursor widcur = database.rawQuery("select id from dict_words w where w.word=?", new String[]{this.word});

        if (!widcur.moveToFirst()) return false;//找不到就return
        this.wid = widcur.getInt(0);
//获得word id
        Cursor midcur = database.rawQuery("select id,type,en,cn from dict_collins_meaning m where m.wid=?", new String[]{this.wid + ""});

        this.initMeanCount(midcur.getCount());

        if (!midcur.moveToFirst()) return false;
        this.mids[0] = midcur.getInt(0);
        this.type[0] = midcur.getString(1);
        this.MeaningsEN[0] = midcur.getString(2);
        this.MeaningsCN[0] = midcur.getString(3);

        for (int i = 1; midcur.moveToNext(); i++) {//获取全部meaning的字符串
            this.mids[i] = midcur.getInt(0);
            this.type[i] = midcur.getString(1);
            this.MeaningsEN[i] = midcur.getString(2);
            this.MeaningsCN[i] = midcur.getString(3);
        }
//获取单个meaning其中一个example
        for (int i = 0; i < this.mids.length; i++) {
            Cursor eidcur = database.rawQuery("select en,cn from dict_collins_example e where e.mid=?", new String[]{this.mids[i] + ""});
            if (eidcur.getCount() != 0 && eidcur != null) {
                eidcur.moveToFirst();
                this.ExamplesEN[i] = eidcur.getString(0);
                this.ExamplesCN[i] = eidcur.getString(1);
            }
        }
        widcur.close();
        midcur.close();
        return true;
    }

    public String Output() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(word + "\n");
        int count = 1;
        for (int i = 0; i < mids.length; i++) {
            stringBuilder.append(count++ + "." + type[i] + " " + MeaningsEN[i] + MeaningsCN[i] + "\n");
            stringBuilder.append("例:" + ExamplesEN[i] + "\n" + ExamplesCN[i] + "\n\n");
        }
        return stringBuilder.toString();
    }

    public String outInHtml() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HtmlFont.titleWord(word));
        for (int i = 0; i < mids.length; i++) {
            stringBuilder.append(HtmlFont.Meaning(i + 1, type[i], MeaningsEN[i], MeaningsCN[i]));
            stringBuilder.append(HtmlFont.Example(ExamplesEN[i], ExamplesCN[i]));
        }
        return stringBuilder.toString();
    }
}
