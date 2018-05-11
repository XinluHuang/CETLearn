package com.example.nietzche.test3;

import android.app.Application;
import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        context=getApplicationContext();
        super.onCreate();
//        copyDict();
//        copyCollins();
    }

    public static Context getContext() {
        return context;
    }
    private void copyDict() {
        File mkdir = new File(getFilesDir().getParent(), "databases");
        if (!mkdir.exists()) {
            mkdir.mkdirs();
        }
        File destination = new File(mkdir, "dict.db");
        FileOutputStream fos = null;
        InputStream resinput = null;
        if (!destination.exists()) {
            try {
                fos = new FileOutputStream(destination);
                resinput = getAssets().open("dict.db");
                byte[] bytes = new byte[2048];
                int len = 0;
                while ((len = resinput.read(bytes)) != -1) {
                    fos.write(bytes, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fos.close();
                    resinput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void copyCollins() {
        File mkdir = new File(getFilesDir().getParent(), "databases");
        if (!mkdir.exists()) {
            mkdir.mkdirs();
        }
        File destination = new File(mkdir, "collins.db");
        FileOutputStream fos = null;
        InputStream resinput = null;
        if (!destination.exists()) {
            try {
                fos = new FileOutputStream(destination);
                resinput = getAssets().open("collins.db");
                byte[] bytes = new byte[2048];
                int len = 0;
                while ((len = resinput.read(bytes)) != -1) {
                    fos.write(bytes, 0, len);
                }
            } catch (IOException e) {

            } finally {
                try {
                    fos.close();
                    resinput.close();
                } catch (IOException e) {

                }
            }
        }
    }
}
