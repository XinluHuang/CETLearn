package com.xinluhuang.englishlearn.greendao.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.xinluhuang.englishlearn.greendao.entity.CET4Word;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "tbWord".
*/
public class CET4WordDao extends AbstractDao<CET4Word, Integer> {

    public static final String TABLENAME = "tbWord";

    /**
     * Properties of entity CET4Word.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, int.class, "id", true, "id");
        public final static Property Word = new Property(1, String.class, "word", false, "word");
        public final static Property Meaning = new Property(2, String.class, "meaning", false, "meaning");
        public final static Property Spell = new Property(3, String.class, "spell", false, "spell");
    }


    public CET4WordDao(DaoConfig config) {
        super(config);
    }
    
    public CET4WordDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"tbWord\" (" + //
                "\"id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: id
                "\"word\" TEXT," + // 1: word
                "\"meaning\" TEXT," + // 2: meaning
                "\"spell\" TEXT);"); // 3: spell
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"tbWord\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CET4Word entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String word = entity.getWord();
        if (word != null) {
            stmt.bindString(2, word);
        }
 
        String meaning = entity.getMeaning();
        if (meaning != null) {
            stmt.bindString(3, meaning);
        }
 
        String spell = entity.getSpell();
        if (spell != null) {
            stmt.bindString(4, spell);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CET4Word entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String word = entity.getWord();
        if (word != null) {
            stmt.bindString(2, word);
        }
 
        String meaning = entity.getMeaning();
        if (meaning != null) {
            stmt.bindString(3, meaning);
        }
 
        String spell = entity.getSpell();
        if (spell != null) {
            stmt.bindString(4, spell);
        }
    }

    @Override
    public Integer readKey(Cursor cursor, int offset) {
        return cursor.getInt(offset + 0);
    }    

    @Override
    public CET4Word readEntity(Cursor cursor, int offset) {
        CET4Word entity = new CET4Word( //
            cursor.getInt(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // word
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // meaning
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // spell
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CET4Word entity, int offset) {
        entity.setId(cursor.getInt(offset + 0));
        entity.setWord(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setMeaning(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setSpell(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Integer updateKeyAfterInsert(CET4Word entity, long rowId) {
        return entity.getId();
    }
    
    @Override
    public Integer getKey(CET4Word entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CET4Word entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}