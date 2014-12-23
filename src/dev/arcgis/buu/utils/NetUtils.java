/**
 * dev.arcgis.buu.utils / NetUtils.java - (Simple description)
 * 
 * Version 1.0.0.
 * 
 * First created: 2014-12-23
 * 
 * Copyright (c) 2014 dev.arcgis.buu. All rights reserved.
 */

package dev.arcgis.buu.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

/**
 * 检查当前网络状况的工具类
 */
public class NetUtils {

    public static final int NETWORK_NONE = -1;
    public static final int NETWORK_WIFI = 1;
    public static final int NETWORK_MOBILE = 2;

    public static int getNetworkState(final Context context) {
        try {
            final ConnectivityManager connManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            // Wifi
            State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
            if (state == State.CONNECTED || state == State.CONNECTING) {
                return NETWORK_WIFI;
            }

            // 3G
            state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
            if (state == State.CONNECTED || state == State.CONNECTING) {
                return NETWORK_MOBILE;
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return NETWORK_NONE;
    }
}
