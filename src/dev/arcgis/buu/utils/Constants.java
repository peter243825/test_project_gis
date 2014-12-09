/**
 * dev.arcgis.buu.utils / Constants.java - (Simple description)
 * 
 * Version 1.0.0.
 * 
 * First created: 2014-12-2
 * 
 * Copyright (c) 2014 dev.arcgis.buu. All rights reserved.
 */

package dev.arcgis.buu.utils;

/**
 * 常量定义类: 约定时间以秒为单位，距离以米为单位
 */
public class Constants {
    public static final int kGetGPSLoopTime = 10; //gps轮询间隔
    public static final int kRefreshMapTime = 10; //地图刷新间隔

    public static final int kMaxRecordCount = 1000; //在内存中的最多记录条数

    public static final String kServerAddress = "127.0.0.1"; //服务器地址
    public static final int kServerPort = 8008; //服务器端口
}
