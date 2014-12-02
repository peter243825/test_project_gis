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

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import dev.arcgis.buu.utils.Constants;
import dev.arcgis.buu.utils.L;

import java.util.List;

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
        super.onCreate();
        inst = this;
        if (!isPrimaryProcess()) {
            return;
        }
        initBaiduGeo(inst);
    }

    public static BaseApplication getInstance() {
        return inst;
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
        option.setScanSpan(Constants.kGetGPSLoopTime * 1000); //更新位置信息的时间间隔
        option.setIsNeedAddress(true);
        locationClient.setLocOption(option);
        locationClient.start();
    }

    public static BDLocation getCurrentLocation() {
        return currentLocation;
    }

    /**
     * 检查当前进程是否为主进程（因baidu location service是remote进程，同样会初始化Application）
     */
    private boolean isPrimaryProcess() {
        final ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        final List<RunningAppProcessInfo> processInfoList = am.getRunningAppProcesses();
        final String primaryProcessName = getPackageName();
        final int myPid = android.os.Process.myPid();
        for (final RunningAppProcessInfo info : processInfoList) {
            if (info.pid == myPid && primaryProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
