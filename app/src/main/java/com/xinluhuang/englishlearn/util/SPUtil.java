package com.xinluhuang.englishlearn.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtil {
    private static SharedPreferences sharedPreferences;
    private static final String FILE_NAME = "share_data";
    public static final String isDay = "daynight";
    public static final String DICT_TYPE = "dict_type";
    public static final String FIRST_START = "isFirst";
    public static final String FIRST_FIND_WORD = "isFirstWord";

    private SPUtil() {
    }

    public static void init(Context context) {
        sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public static Object get(String key, Object defaultObject) {
        if (defaultObject instanceof Boolean) {
            return sharedPreferences.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof String) {
            return sharedPreferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sharedPreferences.getInt(key, (Integer) defaultObject);
        }
        return null;
    }

    public static void put(String key, Object object) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof String) {
            editor.putString(key, (String) object);
        }else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        }
        editor.commit();
    }

    public static void remove(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }
}
