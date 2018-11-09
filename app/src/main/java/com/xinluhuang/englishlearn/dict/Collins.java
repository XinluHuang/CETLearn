package com.xinluhuang.englishlearn.dict;

import com.xinluhuang.englishlearn.greendao.DAOManager;
import com.xinluhuang.englishlearn.greendao.entity.Example;
import com.xinluhuang.englishlearn.greendao.entity.Meaning;
import com.xinluhuang.englishlearn.greendao.entity.Word;
import com.xinluhuang.englishlearn.greendao.dao.DaoSession;
import com.xinluhuang.englishlearn.greendao.dao.ExampleDao;
import com.xinluhuang.englishlearn.greendao.dao.MeaningDao;
import com.xinluhuang.englishlearn.greendao.dao.WordDao;

import java.util.List;

public class Collins {
    private static DaoSession daoSession= DAOManager.getInstance().getDaoSession(DAOManager.COLLINS);

    public static String findWordInHtml(String wordstr){
        StringBuilder stringBuilder=new StringBuilder();
        Word word=daoSession.queryBuilder(Word.class).where(WordDao.Properties.Word.eq(wordstr)).unique();
        List<Meaning> meaningList=daoSession.queryBuilder(Meaning.class).where(MeaningDao.Properties.Wid.eq(word.getId())).build().list();
        for (int j=0;j<meaningList.size();j++){
            Meaning meaning=meaningList.get(j);
            //序号从一开始，故用j+1
            stringBuilder.append(HtmlFont.Meaning(j+1,meaning.getType(),meaning.getEn(),meaning.getCn()));
            List<Example> exampleList=daoSession.queryBuilder(Example.class).where(ExampleDao.Properties.Mid.eq(meaning.getId())).build().list();
            if(exampleList.size()>0) {
                Example example = exampleList.get(0);
                stringBuilder.append(HtmlFont.Example(example.getEn(), example.getCn()));
            }
        }
        return stringBuilder.toString();
    }
    public static boolean isExist(String word){
        Word w=daoSession.queryBuilder(Word.class).where(WordDao.Properties.Word.eq(word)).unique();
        return w==null?false:true;
    }

}
