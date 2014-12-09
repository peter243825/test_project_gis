/**
 * dev.arcgis.buu.utils / FileUtils.java - (Simple description)
 * 
 * Version 1.0.0.
 * 
 * First created: 2014-11-30
 * 
 * Copyright (c) 2014 dev.arcgis.buu. All rights reserved.
 */

package dev.arcgis.buu.utils;

import android.content.Context;
import android.os.AsyncTask;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件操作函数
 */
public class FileUtils {

    private static final String kFileNamePattern = "yyyy-MM-dd";
    private static final String kDataFolderName = "data";

    /**
     * 根据当前时间生成数据文件夹中的文件名
     */
    public static String generateDataFileName(final Context context, final String fileNameSuffix) {
        final DateTime dateTime = new DateTime(DateTimeZone.getDefault());
        final DateTimeFormatter formatter = DateTimeFormat.forPattern(kFileNamePattern);
        return context.getDir(kDataFolderName, Context.MODE_PRIVATE) + formatter.print(dateTime) + "_" + fileNameSuffix
               + ".dat";
    }

    /**
     * 获得数据文件夹中的所有文件名
     */
    public static String[] getFilesInDataFolder(final Context context) {
        final File dataFolder = context.getDir(kDataFolderName, Context.MODE_PRIVATE);
        return dataFolder.list();
    }

    /**
     * 将数据写入指定文件
     */
    public static void saveToFile(final String fileName, final String data, final boolean append) {
        new SaveToFile(fileName, data, append).execute();
    }

    //async task of saving data to file
    private static class SaveToFile extends AsyncTask<Void, Void, Integer> {

        private final static int FAILED = -1;
        private final static int SUCCESS = 0;

        private final String fileName;
        private final String data;
        private final boolean append;

        SaveToFile(final String fileName, final String data, final boolean append) {
            super();
            this.fileName = fileName;
            this.data = data;
            this.append = append;
        }

        @Override
        protected Integer doInBackground(final Void... arg0) {
            try {
                final File dir = new File(fileName);
                dir.mkdirs();
                L.d(FileUtils.class, "about to write: " + fileName);
                final FileOutputStream fos = new FileOutputStream(fileName, append);
                fos.write(data.getBytes());
                fos.close();
                return SUCCESS;
            } catch (final IOException e) {
                e.printStackTrace();
                return FAILED;
            }
        }

        @Override
        protected void onPostExecute(final Integer result) {
            switch (result) {
            case SUCCESS:
                L.i(SaveToFile.class, "save to cache file success: " + fileName);
                break;
            case FAILED:
                L.e(SaveToFile.class, "save to cache file failed: " + fileName);
                break;
            }
        }
    }

    /**
     * 从指定文件读取数据，如果文件读取失败返回null
     */
    public static String loadFromFile(final String fileName) {
        final StringBuffer data = new StringBuffer();
        byte[] buffer = null;
        final File file = new File(fileName);
        if (!file.exists()) {
            L.e(FileUtils.class, "file not exists: " + fileName);
            return null;
        }
        L.d(FileUtils.class, "about to read: " + fileName);
        final int fileLen = (int) file.length();
        if (fileLen > 0) {
            buffer = new byte[fileLen];
        } else {
            buffer = new byte[100 * 1024]; //100KB
        }
        L.d(FileUtils.class, "buffer size: " + buffer.length);
        try {
            final FileInputStream fis = new FileInputStream(file);
            while (true) {
                final int ret = fis.read(buffer, 0, buffer.length);
                if (ret == -1) {
                    break;
                }
                data.append(new String(buffer));
            }
            fis.close();
        } catch (final Exception e) {
            e.printStackTrace();
            L.e(FileUtils.class, "read failed: " + fileName);
            return null;
        }

        buffer = null;
        L.i(FileUtils.class, "read successfully: " + fileName);
        return data.toString();
    }

    /**
     * 获得文件的长度
     */
    public static long getFileLength(final String fileName) {
        final File file = new File(fileName);
        if (!file.exists()) {
            L.e(FileUtils.class, "file not exists: " + fileName);
            return -1;
        }
        return file.length();
    }

    public static void deleteFile(final String fileName) {
        final File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
