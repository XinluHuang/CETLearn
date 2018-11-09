package com.xinluhuang.englishlearn.util;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;

public class CacheDataManager {
    public static long getFolderSize(File file) {
        long size = 0;
        File[] files = file.listFiles();
        if(files==null){
            return 0;
        }
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                size += getFolderSize(files[i]);
            } else {
                size += files[i].length();
            }
        }
        return size;
    }
    public static Long getTotalCacheSize(Context context){
        long size=0;
        size+=getFolderSize(context.getCacheDir());

        //判断sd卡是否挂载
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            size += getFolderSize(context.getExternalCacheDir());
        }
        size+=getFolderSize(new File(context.getCacheDir().getParentFile(),"shared_prefs"));
        return size;
    }
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte / 1024;

        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;

        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;

        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }

        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";

    }

    public static boolean deleteDir(File file){
        if(file!=null && file.isDirectory()){
            File[] files=file.listFiles();
            for(int i=0;i<files.length;i++){
                if(files[i].isDirectory()){
                    deleteDir(files[i]);
                }else {
                    files[i].delete();
                }
            }
        }
        //如果file为空,也会返回false
        return file.delete();
    }

    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
        deleteDir(new File(context.getCacheDir().getParentFile(),"shared_prefs"));
    }
}
