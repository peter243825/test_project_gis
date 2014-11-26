/**
 * dev.arcgis.buu.utils / L.java - (Simple description)
 * 
 * Version 1.0.0.
 * 
 * First created: 2014年11月26日
 * 
 * Copyright (c) 2014 dev.arcgis.buu. All rights reserved.
 */

package dev.arcgis.buu.utils;

import android.util.Log;

/**
 * Log统一管理类
 */
public class L {
    private static final String TAG = "huihui";

    // 下面四个是默认tag的函数
    public static void i(final String msg) {
        if (isDebug()) {
            Log.i(TAG, msg);
        }
    }

    public static void d(final String msg) {
        if (isDebug()) {
            Log.d(TAG, msg);
        }
    }

    public static void e(final String msg) {
        if (isDebug()) {
            Log.e(TAG, msg);
        }
    }

    public static void e(final String msg, final Throwable e) {
        if (isDebug()) {
            Log.e(TAG, msg, e);
        }
    }

    public static void v(final String msg) {
        if (isDebug()) {
            Log.v(TAG, msg);
        }
    }

    //下面是传入类名打印log
    public static void i(final Class<?> _class, final String msg) {
        if (isDebug()) {
            Log.i(_class.getName(), msg);
        }
    }

    public static void d(final Class<?> _class, final String msg) {
        if (isDebug()) {
            Log.d(_class.getName(), msg);
        }
    }

    public static void e(final Class<?> _class, final String msg) {
        if (isDebug()) {
            Log.e(_class.getName(), msg);
        }
    }

    public static void e(final Class<?> _class, final String msg, final Throwable e) {
        if (isDebug()) {
            Log.e(_class.getName(), msg, e);
        }
    }

    public static void v(final Class<?> _class, final String msg) {
        if (isDebug()) {
            Log.v(_class.getName(), msg);
        }
    }

    // 下面是传入自定义tag的函数
    public static void i(final String tag, final String msg) {
        if (isDebug()) {
            Log.i(tag, msg);
        }
    }

    public static void d(final String tag, final String msg) {
        if (isDebug()) {
            Log.d(tag, msg);
        }
    }

    public static void e(final String tag, final String msg) {
        if (isDebug()) {
            Log.e(tag, msg);
        }
    }

    public static void e(final String tag, final String msg, final Throwable e) {
        if (isDebug()) {
            Log.e(tag, msg, e);
        }
    }

    public static void v(final String tag, final String msg) {
        if (isDebug()) {
            Log.v(tag, msg);
        }
    }

    private static boolean isDebug() {
        return true;
    }
}
