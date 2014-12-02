/**
 * dev.arcgis.buu.entity / SenserRecord.java - (Simple description)
 * 
 * Version 1.0.0.
 * 
 * First created: 2014-12-2
 * 
 * Copyright (c) 2014 dev.arcgis.buu. All rights reserved.
 */

package dev.arcgis.buu.entity;

import dev.arcgis.buu.BaseApplication;
import dev.arcgis.buu.utils.Constants;
import dev.arcgis.buu.utils.FileUtils;
import dev.arcgis.buu.utils.L;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * 传感器数据记录类
 */
public class SenserRecord implements Serializable {
    private static final long serialVersionUID = 2055765895260925417L;
    private static final String kTimePattern = "HH:mm:ss.ms";

    private String senserName;
    private TreeMap<DateTime, float[]> record;

    public SenserRecord(final String senserName) {
        this.senserName = senserName;
        record = new TreeMap<DateTime, float[]>();
    }

    public String getSenserName() {
        return senserName;
    }

    public void setSenserName(final String senserName) {
        this.senserName = senserName;
    }

    public TreeMap<DateTime, float[]> getRecord() {
        return record;
    }

    public void setRecord(final TreeMap<DateTime, float[]> record) {
        this.record = record;
    }

    public void addRecord(final float[] recordItem) {
        final DateTime currentTime = new DateTime(DateTimeZone.getDefault());
        addRecord(currentTime, recordItem);
    }

    public void addRecord(final DateTime time, final float[] recordItem) {
        record.put(time, recordItem);
        if (record.size() >= Constants.kMaxRecordCount) {
            dump();
        }
    }

    public void dump() {
        final String fileName = FileUtils.generateDataFileName(BaseApplication.getInstance(), senserName);
        final DateTimeFormatter formatter = DateTimeFormat.forPattern(kTimePattern);
        final StringBuffer buffer = new StringBuffer();
        final Iterator<Entry<DateTime, float[]>> it = record.entrySet().iterator();
        int count = 0;
        while (it.hasNext()) {
            ++count;
            final Entry<DateTime, float[]> entry = it.next();
            buffer.append(formatter.print(entry.getKey()));
            for (final float floatItem : entry.getValue()) {
                buffer.append("\t").append(floatItem);
            }
            buffer.append("\n");
        }
        L.i(SenserRecord.class,
            "dump: {senserName: " + senserName + ", count: " + count + ", buffer.length: " + buffer.length());
        FileUtils.saveToFile(fileName, buffer.toString(), true);
        record.clear();
    }

    @Override
    public String toString() {
        return "SenserRecord: { senserName: " + senserName + ", record: " + record + " }";
    }
}
