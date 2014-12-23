/**
 * dev.arcgis.buu.utils / SenserUtils.java - (Simple description)
 * 
 * Version 1.0.0.
 * 
 * First created: 2014-12-23
 * 
 * Copyright (c) 2014 dev.arcgis.buu. All rights reserved.
 */

package dev.arcgis.buu.utils;

import android.hardware.Sensor;

/**
 * 传感器工具类
 */
public class SenserUtils {
    public static String getSenserNameByType(final int type) {
        switch (type) {
        case Sensor.TYPE_LINEAR_ACCELERATION:
            return "Linear Acceleration Senser";
        case Sensor.TYPE_GYROSCOPE:
            return "Gyroscope Senser";
        case Sensor.TYPE_ACCELEROMETER:
            return "Accelerometer Senser";
        case Sensor.TYPE_MAGNETIC_FIELD:
            return "Magnetic Field Senser";
        default:
            return "Unknown Senser";
        }
    }
}
