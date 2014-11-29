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
    /**
     * 从GPS坐标转换至地图坐标
     */
    public static Point getMapPointFromGPSPoint(final Point gpsPoint, final SpatialReference sr) {
        return (Point) GeometryEngine.project(gpsPoint, SpatialReference.create(4326), sr);
    }
}
