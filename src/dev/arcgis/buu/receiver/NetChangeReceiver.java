/**
 * dev.arcgis.buu.receiver / NetChangeReceiver.java - (Simple description)
 * 
 * Version 1.0.0.
 * 
 * First created: 2014-12-23
 * 
 * Copyright (c) 2014 dev.arcgis.buu. All rights reserved.
 */

package dev.arcgis.buu.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import dev.arcgis.buu.BaseApplication;
import dev.arcgis.buu.utils.L;
import dev.arcgis.buu.utils.NetUtils;

/**
 * 网络变化监听器
 */
public class NetChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        final String action = intent.getAction();
        L.i(NetChangeReceiver.class, "Alarm received. action: " + action);
        final int networkState = NetUtils.getNetworkState(context);
        BaseApplication.updateNetworkState(networkState);
    }

}
