/**
 * dev.arcgis.buu.utils / CalculateUtils.java - (Simple description)
 * 
 * Version 1.0.0.
 * 
 * First created: 2014-11-29
 * 
 * Copyright (c) 2014 dev.arcgis.buu. All rights reserved.
 */

package dev.arcgis.buu.utils;

import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;

/**
 * 各种转换公式函数
 */
public class CalculateUtils {

    private final static double EARTH_RADIUS = 6378137.0; //地球直径

    /**
     * 从GPS坐标转换至地图坐标
     */
    public static Point getMapPointFromGPSPoint(final Point gpsPoint, final SpatialReference sr) {
        return (Point) GeometryEngine.project(gpsPoint, SpatialReference.create(4326), sr);
    }

    /**
     * 确认GPS数据是合法的
     */
    public static boolean isGpsValid(final double longitude, final double latitude) {
        return Math.abs(latitude) > 0.001 && Math.abs(longitude) > 0.001;
    }

    /**
     * 计算两个GPS坐标之间的距离
     */
    public static double gps2km(final double lat_a, final double lng_a, final double lat_b, final double lng_b) {
        if (!isGpsValid(lng_a, lat_a) || !isGpsValid(lng_b, lat_b)) {
            return -1;
        }
        final double radLat1 = (lat_a * Math.PI / 180.0);
        final double radLat2 = (lat_b * Math.PI / 180.0);
        final double a = radLat1 - radLat2;
        final double b = (lng_a - lng_b) * Math.PI / 180.0;
        final double s =
                2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2)
                                        * Math.pow(Math.sin(b / 2), 2)));
        return s * EARTH_RADIUS;
    }
}
