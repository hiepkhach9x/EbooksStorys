package com.CTCStudio.ebookstorys.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Variable {
	public static final boolean isLog = true;
	public static String EBOOKPATH = "EBOOKPATH";

	public static void updateConnectedFlags(Context ctx) {
		ConnectivityManager connMgr = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
		if (activeInfo != null && activeInfo.isConnected()) {
			NetworkReceiver.wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
			NetworkReceiver.mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
		} else {
			NetworkReceiver.wifiConnected = false;
			NetworkReceiver.mobileConnected = false;
		}
	}
}
