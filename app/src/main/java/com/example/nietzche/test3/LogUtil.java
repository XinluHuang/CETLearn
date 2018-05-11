package com.example.nietzche.test3;

import android.util.Log;

public class LogUtil {
    private static final int DEBUG=1;
    private static int level=1;
    public static void d(String tag,String content){
        if(level<=DEBUG) {
            Log.d(tag, content);
        }
    }
}
