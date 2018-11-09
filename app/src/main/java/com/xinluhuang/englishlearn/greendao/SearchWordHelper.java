package com.xinluhuang.englishlearn.greendao;

import com.xinluhuang.englishlearn.greendao.dao.DaoSession;
import com.xinluhuang.englishlearn.greendao.dao.MarkWordDao;
import com.xinluhuang.englishlearn.greendao.dao.SearchWordDao;
import com.xinluhuang.englishlearn.greendao.entity.MarkWord;
import com.xinluhuang.englishlearn.greendao.entity.SearchWord;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

public class SearchWordHelper {
    private static volatile SearchWordHelper instance = null;
    private static DaoSession daoSession = null;

    public static SearchWordHelper getInstance() {
        if (instance == null) {
            synchronized (SearchWordHelper.class) {
                if (instance == null) {
                    instance = new SearchWordHelper();
                }
            }
        }
        return instance;
    }

    private SearchWordHelper() {
        daoSession = DAOManager.getInstance().getDaoSession(DAOManager.MARK_WORD);
    }

    public List<String> getSearchWordList() {
        List<SearchWord> searchWordList = daoSession.queryBuilder(SearchWord.class).build().list();
        List<String> stringList = new ArrayList<String>();
        for (int i = 0; i < searchWordList.size(); i++) {
            stringList.add(searchWordList.get(i).getWord());
        }
        return stringList;
    }

    public boolean insertSearchWord(String word) {
        if ("".equals(word) || word == null) {
            return false;
        }
        SearchWord searchWord = new SearchWord();
        searchWord.setWord(word);
        if (daoSession.queryBuilder(SearchWord.class).where(SearchWordDao.Properties.Word.eq(word)).build().list().size() == 0) {
            daoSession.insert(searchWord);
            return true;
        }
        return false;
    }

    public boolean deleteSearchWord(String word) {
        if ("".equals(word) || word == null) {
            return false;
        }
        SearchWord searchWord = daoSession.queryBuilder(SearchWord.class).where(SearchWordDao.Properties.Word.eq(word)).unique();
        if (searchWord != null) {
            daoSession.delete(searchWord);
            return true;
        } else {
            return false;
        }
    }

    public boolean isMarkedWordExist(String word) {
        if ("".equals(word) || word == null) {
            return false;
        }
        MarkWord markWord = new MarkWord();
        markWord.setWord(word);
        if (daoSession.queryBuilder(MarkWord.class).where(MarkWordDao.Properties.Word.eq(word)).build().list().size() != 0) {
            return true;
        }
        return false;
    }

    public boolean insertMarkedWord(String word) {
        if ("".equals(word) || word == null) {
            return false;
        }
        MarkWord markWord = new MarkWord();
        markWord.setWord(word);
        if (daoSession.queryBuilder(MarkWord.class).where(MarkWordDao.Properties.Word.eq(word)).build().list().size() == 0) {
            daoSession.insert(markWord);
            return true;
        }
        return false;
    }


    public List<String> getMarkedWordList() {
        List<MarkWord> markWordList = daoSession.queryBuilder(MarkWord.class).build().list();
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < markWordList.size(); i++) {
            stringList.add(markWordList.get(i).getWord());
        }
        return stringList;
    }

    public boolean deleteMarkedWord(String word) {
        if ("".equals(word) || word == null) {
            return false;
        }
        MarkWord markWord = daoSession.queryBuilder(MarkWord.class).where(MarkWordDao.Properties.Word.eq(word)).unique();
        if (markWord != null) {
            daoSession.delete(markWord);
            return true;
        } else {
            return false;
        }
    }
}
