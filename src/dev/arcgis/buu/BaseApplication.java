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
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import dev.arcgis.buu.entity.SenserRecord;
import dev.arcgis.buu.utils.Constants;
import dev.arcgis.buu.utils.L;
import dev.arcgis.buu.utils.SenserUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Application类：程序入口
 */
public class BaseApplication extends Application {
    public static final String kTag = BaseApplication.class.getSimpleName();
    private static BaseApplication inst;

    private static LocationClient locationClient;
    private static BDLocation currentLocation = null;

    private static HashMap<String, SenserRecord> senserRecordMap;

    private static int networkState;

    @Override
    public void onCreate() {
        super.onCreate();
        inst = this;
        if (!isPrimaryProcess()) {
            return;
        }
        initBaiduGeo(inst);
        regSenserListener();
    }

    public static BaseApplication getInstance() {
        return inst;
    }

    private static void initBaiduGeo(final Context context) {
        locationClient = new LocationClient(context.getApplicationContext());
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(final BDLocation location) {
                final int locationType = location.getLocType();
                L.d(kTag, "onReceiveLocation, retCode: " + locationType);
                if (locationType != BDLocation.TypeCriteriaException //
                    && locationType != BDLocation.TypeNetWorkException
                    && locationType != BDLocation.TypeOffLineLocationFail
                    && locationType != BDLocation.TypeOffLineLocationNetworkFail
                    && locationType != BDLocation.TypeServerError) {
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

    /**
     * 注册传感器监听
     */
    private static void regSenserListener() {
        senserRecordMap = new HashMap<String, SenserRecord>();
        final SensorManager manager = (SensorManager) inst.getSystemService(SENSOR_SERVICE);
        final SenserListener listener = new SenserListener();
        final Sensor linerSenser = manager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION); //线性加速传感器
        manager.registerListener(listener, linerSenser, SensorManager.SENSOR_DELAY_NORMAL);
        final Sensor gyroscopeSenser = manager.getDefaultSensor(Sensor.TYPE_GYROSCOPE); //陀螺仪
        manager.registerListener(listener, gyroscopeSenser, SensorManager.SENSOR_DELAY_NORMAL);
        final Sensor accelerometerSensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); //加速度传感器
        manager.registerListener(listener, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        final Sensor magneticSensor = manager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD); //磁场传感器
        manager.registerListener(listener, magneticSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    static class SenserListener implements SensorEventListener {

        @Override
        public void onSensorChanged(final SensorEvent event) {
            final int senserType = event.sensor.getType();
            final String senserName = SenserUtils.getSenserNameByType(senserType);
            L.d(SenserListener.class, "onSensorChanged: " + senserName);

            SenserRecord record = senserRecordMap.get(senserName);
            if (record == null) {
                record = new SenserRecord(senserName);
                senserRecordMap.put(senserName, record);
            }
            record.addRecord(event.values);
        }

        @Override
        public void onAccuracyChanged(final Sensor sensor, final int accuracy) {}

    }

    public static int getNetworkState() {
        return networkState;
    }

    public static void updateNetworkState(final int newNetworkState) {
        networkState = newNetworkState;
    }
}
