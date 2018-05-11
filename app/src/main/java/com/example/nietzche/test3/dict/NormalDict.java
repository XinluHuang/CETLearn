package com.example.nietzche.test3.dict;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Html;
import android.util.Log;

import com.example.nietzche.test3.MyApplication;

public class NormalDict {
    private SQLiteDatabase database;
    public NormalDict(){
        database=SQLiteDatabase.openOrCreateDatabase(MyApplication.getContext().getFilesDir().getParent()+"/databases/dict.db",null);
    }

    public String getMeaning(String word){
        String mean="";
        Cursor cur=database.rawQuery("select meaning from "+getTable(word)+" where word=?",new String[]{word});
        if(cur.moveToFirst()){
            mean=cur.getString(0);
        }
        return mean;
    }

    private String getTable(String word){
        char first=Character.toLowerCase(word.charAt(0));
        return new String("dict_word_"+first);
    }

}
