/**
 * dev.arcgis.buu.entity / AgentInfo.java - (Simple description)
 * 
 * Version 1.0.0.
 * 
 * First created: 2014-12-6
 * 
 * Copyright (c) 2014 dev.arcgis.buu. All rights reserved.
 */

package dev.arcgis.buu.entity;

/**
 * 客户端信息类
 */
public class AgentInfo {
    private long uid;
    private String name;
    private String deviceInfo;
    private String appInfo;
    private String extraInfo;

    public long getUid() {
        return uid;
    }

    public void setUid(final long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(final String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(final String appInfo) {
        this.appInfo = appInfo;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(final String extraInfo) {
        this.extraInfo = extraInfo;
    }
}
