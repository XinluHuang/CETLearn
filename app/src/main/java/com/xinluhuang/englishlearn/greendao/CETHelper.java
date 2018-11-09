package com.xinluhuang.englishlearn.greendao;

import com.xinluhuang.englishlearn.greendao.dao.CET4WordDao;
import com.xinluhuang.englishlearn.greendao.dao.CET6WordDao;
import com.xinluhuang.englishlearn.greendao.dao.DaoSession;
import com.xinluhuang.englishlearn.greendao.entity.CET4Word;
import com.xinluhuang.englishlearn.greendao.entity.CET6Word;
import com.xinluhuang.englishlearn.greendao.entity.CETWord;

import java.util.ArrayList;
import java.util.List;

public class CETHelper {
    private static DaoSession cet6DaoSession = null;
    private static DaoSession cet4DaoSession = null;
    private static CETHelper cetHelper = new CETHelper();
    public static final int CET4 = 0;
    public static final int CET6 = 1;

    public int getWordSum(int type) {
        Class clazz= TypeToClass(type);
        if(clazz==null){
            return -1;
        }
        List list=null;
        if(type==CET6){
             list= cet6DaoSession.queryBuilder(clazz).build().list();
        }else if(type==CET4){
            list= cet4DaoSession.queryBuilder(clazz).build().list();
        }else {
            return 0;
        }

        return list.size();
    }
    private Class TypeToClass(int type){
        Class clazz=null;
        if (type == CET4) {
            clazz = CET4Word.class;
        } else if (type == CET6) {
            clazz = CET6Word.class;
        }
        return clazz;
    }

    private CETHelper() {
        cet6DaoSession=DAOManager.getInstance().getDaoSession(DAOManager.CET6);
        cet4DaoSession=DAOManager.getInstance().getDaoSession(DAOManager.CET4);
    }

    public static CETHelper getInstance() {
        return cetHelper;
    }

    public List<CET6Word> getCET6CustomList(int custom) {
        DaoSession daoSession = DAOManager.getInstance().getDaoSession(DAOManager.CET6);
        return daoSession.queryBuilder(CET6Word.class).where(CET6WordDao.Properties.Id.between(new Integer(15 * custom - 14), new Integer(15 * custom))).build().list();
    }
    public List<CET4Word> getCET4CustomList(int custom) {
        DaoSession daoSession = DAOManager.getInstance().getDaoSession(DAOManager.CET4);
        return daoSession.queryBuilder(CET4Word.class).where(CET4WordDao.Properties.Id.between(new Integer(15 * custom - 14), new Integer(15 * custom))).build().list();
    }
}
