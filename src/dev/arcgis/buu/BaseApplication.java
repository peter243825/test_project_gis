/**
 * dev.arcgis.buu / BaseApplication.java - (Simple description)
 * 
 * Version 1.0.0.
 * 
 * First created: 2014-11-26
 * 
 * Copyright (c) 2014 dev.arcgis.buu. All rights reserved.
 */

package dev.arcgis.buu;

import android.app.Application;
import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import dev.arcgis.buu.utils.L;

/**
 * Application类：程序入口
 */
public class BaseApplication extends Application {
    public static final String kTag = BaseApplication.class.getSimpleName();
    private static BaseApplication inst;

    private static LocationClient locationClient;
    private static BDLocation currentLocation = null;

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        inst = this;
        initBaiduGeo(inst);
    }

    private static void initBaiduGeo(final Context context) {
        locationClient = new LocationClient(context.getApplicationContext());
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(final BDLocation location) {
                L.d(kTag, "onReceiveLocation, retCode: " + location.getLocType());
                if (location.getLocType() != BDLocation.TypeCriteriaException && location.getAddrStr() != null
                    && !"".equals(location.getAddrStr())) {
                    final double latitude = location.getLatitude();
                    final double lontitude = location.getLongitude();
                    L.i(kTag, "get new location\n" + "latitude = " + latitude + "\nlontitude = " + lontitude
                              + "\nradius = " + location.getRadius() + "\naddress: " + location.getAddrStr());
                    currentLocation = location;
                }
            }
        });
        final LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationMode.Hight_Accuracy);
        option.setCoorType("gcj02");
        option.setScanSpan(10000); //每10秒更新一次位置信息
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);
        locationClient.start();
    }

    public static BDLocation getCurrentLocation() {
        return currentLocation;
    }
}
