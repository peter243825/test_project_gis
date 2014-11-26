/**
 * dev.arcgis.buu.utils / T.java - (Simple description)
 * 
 * Version 1.0.0.
 * 
 * First created: 2014年11月26日
 * 
 * Copyright (c) 2014 dev.arcgis.buu. All rights reserved.
 */

package dev.arcgis.buu.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast统一管理类
 * 
 * @author way
 * 
 */
public class T {

    /**
     * 短时间显示Toast
     * 
     * @param context
     * @param message
     */
    public static void showShort(final Context context, final CharSequence message) {
        final Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        // toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 短时间显示Toast
     * 
     * @param context
     * @param message
     */
    public static void showShort(final Context context, final int message) {
        final Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        // toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 长时间显示Toast
     * 
     * @param context
     * @param message
     */
    public static void showLong(final Context context, final CharSequence message) {
        final Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        // toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 长时间显示Toast
     * 
     * @param context
     * @param message
     */
    public static void showLong(final Context context, final int message) {
        final Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        // toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 自定义显示Toast时间
     * 
     * @param context
     * @param message
     * @param duration
     */
    public static void show(final Context context, final CharSequence message, final int duration) {
        final Toast toast = Toast.makeText(context, message, duration);
        // toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 自定义显示Toast时间
     * 
     * @param context
     * @param message
     * @param duration
     */
    public static void show(final Context context, final int message, final int duration) {
        final Toast toast = Toast.makeText(context, message, duration);
        // toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
