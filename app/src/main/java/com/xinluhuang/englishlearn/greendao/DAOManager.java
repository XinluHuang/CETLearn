package com.xinluhuang.englishlearn.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.xinluhuang.englishlearn.greendao.dao.DaoMaster;
import com.xinluhuang.englishlearn.greendao.dao.DaoSession;
import com.xinluhuang.englishlearn.util.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class DAOManager {
    public static final String COLLINS = "collins.db";
    public static final String DICT = "dict.db";
    public static final String MARK_WORD = "markword.db";
    public static final String CET4 = "cet4.db";
    public static final String CET6 = "cet6.db";

    private Map<String, DaoSession> map;

    private Context context;

    private DAOManager() {

    }

    public static DAOManager getInstance() {
        return Singleton.daoManager;
    }

    static class Singleton {
        private static DAOManager daoManager = new DAOManager();
    }

    public void init(Context cont) {
        context = cont;
        map = new HashMap<String, DaoSession>();
        copyDict(DICT);
        copyDict(COLLINS);
        copyDict(CET4);
        copyDict(CET6);
    }


    private DaoSession getMarkWordDaoSession() {
        SQLiteDatabase sqLiteDatabase;
        if (!context.getDatabasePath(MARK_WORD).exists()) {
            sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(context.getDatabasePath(MARK_WORD), null);
            sqLiteDatabase.execSQL("create table markword(id integer primary key not null,word VARCHAR (100));");
            sqLiteDatabase.execSQL("create table searchword(id integer primary key not null,word VARCHAR (100));");
        } else {
            sqLiteDatabase = SQLiteDatabase.openDatabase(context.getDatabasePath(MARK_WORD).getPath(), null, SQLiteDatabase.OPEN_READWRITE);
        }
        return new DaoMaster(sqLiteDatabase).newSession();
    }

    private DaoSession getReadOnlyDaoSession(String name) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openDatabase(context.getDatabasePath(name).getPath(), null, SQLiteDatabase.OPEN_READONLY);
        return new DaoMaster(sqLiteDatabase).newSession();
    }
    private DaoSession getReadWriteDaoSession(String name) {
        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(context.getDatabasePath(name), null);
        return new DaoMaster(sqLiteDatabase).newSession();
    }

    public DaoSession getDaoSession(String name) {
        DaoSession daoSession = map.get(name);
        if (daoSession == null) {
            switch (name) {
                case CET6:
                    daoSession = getReadOnlyDaoSession(CET6);
                    map.put(name,daoSession);
                    break;
                case CET4:
                    daoSession = getReadOnlyDaoSession(CET4);
                    map.put(name,daoSession);
                    break;
                case COLLINS:
                    daoSession = getReadOnlyDaoSession(COLLINS);
                    map.put(name,daoSession);
                    break;
                case DICT:
                    daoSession = getReadOnlyDaoSession(DICT);
                    map.put(name,daoSession);
                    break;
                case MARK_WORD:
                    daoSession = getMarkWordDaoSession();
                    map.put(name,daoSession);
                    break;
                default:
                    break;
            }
        }
        return daoSession;
    }


    private void copyDict(String dictName) {
        File destination = context.getDatabasePath(dictName);
        if (destination.exists()) {
            return;
        }

        File dbDir = context.getDatabasePath(" ");//加个空格，不能为""
        LogUtil.d(dbDir.getPath());
        if (!dbDir.exists()) {
            dbDir.mkdirs();
        }

        FileOutputStream fos = null;
        InputStream resinput = null;
        if (!destination.exists()) {
            try {
                fos = new FileOutputStream(destination);
                resinput = context.getAssets().open(dictName);
                byte[] bytes = new byte[2048];
                int len = 0;
                while ((len = resinput.read(bytes)) != -1) {
                    fos.write(bytes, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if(fos!=null) {
                        fos.close();
                    }
                    if(resinput!=null) {
                        resinput.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
