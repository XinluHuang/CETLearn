package com.xinluhuang.englishlearn.dict;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xinluhuang.englishlearn.application.MyApplication;
import com.xinluhuang.englishlearn.greendao.DAOManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NormalDict {
    private SQLiteDatabase database;
    private static volatile NormalDict instance;

    private NormalDict() {
        database = SQLiteDatabase.openOrCreateDatabase(MyApplication.getContext().getDatabasePath(DAOManager.DICT), null);
    }

    public static NormalDict getInstance() {
        if (instance == null) {
            synchronized (NormalDict.class) {
                if (instance == null) {
                    instance = new NormalDict();
                }
            }
        }
        return instance;
    }
    public boolean isExist(String word){
        Cursor cur = database.rawQuery("select * from " + getTable(word) + " where word=?", new String[]{word});
        return cur.moveToFirst();
    }

    public DictBean getDictBean(String word) {
        if("".equals(word) || word==null){
            return null;
        }
        Cursor cur = database.rawQuery("select * from " + getTable(word) + " where word=?", new String[]{word});
        DictBean dictBean = null;
        if (cur.moveToFirst()) {
            dictBean = new DictBean(cur.getInt(0), cur.getString(1), cur.getString(2), cur.getString(3), cur.getString(4));
        }
        return dictBean;
    }

    public List<DictBean> getWordForItem(List<String> wordlist) {
        if(wordlist==null){
            return null;
        }
        List<DictBean> dictBeanList=new ArrayList<>();
        for(Iterator iterator=wordlist.iterator();iterator.hasNext();){
            dictBeanList.add(getDictBean((String) iterator.next()));
        }
        return dictBeanList;
    }

    private String getTable(String word) {
        char first = Character.toLowerCase(word.charAt(0));
        return new String("dict_word_" + first);
    }

}
