package com.CTCStudio.ebookstorys.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.CTCStudio.ebookstorys.R;

public class NetworkReceiver extends BroadcastReceiver {
	public static final String WIFI = "Wi-Fi";
	public static final String ANY = "Any";
	// Whether there is a Wi-Fi connection.
	public static boolean wifiConnected = false;
	// Whether there is a mobile connection.
	public static boolean mobileConnected = false;
	// Whether the display should be refreshed.
	public static boolean refreshDisplay = true;

	@Override
	public void onReceive(Context context, Intent intent) {
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

		// Checks the user prefs and the network connection. Based on the
		// result, decides
		// whether
		// to refresh the display or keep the current display.
		// If the userpref is Wi-Fi only, checks to see if the device has a
		// Wi-Fi connection.
		if (networkInfo != null
				&& networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			// If device has its Wi-Fi connection, sets refreshDisplay
			// to true. This causes the display to be refreshed when the user
			// returns to the app.
			refreshDisplay = true;
			Toast.makeText(context, R.string.wifi_connected, Toast.LENGTH_SHORT)
					.show();

			// If the setting is ANY network and there is a network connection
			// (which by process of elimination would be mobile), sets
			// refreshDisplay to true.
		} else if (networkInfo != null) {
			refreshDisplay = true;

			// Otherwise, the app can't download content--either because there
			// is no network
			// connection (mobile or Wi-Fi), or because the pref setting is
			// WIFI, and there
			// is no Wi-Fi connection.
			// Sets refreshDisplay to false.
		} else {
			refreshDisplay = false;
			Toast.makeText(context, R.string.lost_connection,
					Toast.LENGTH_SHORT).show();
		}
	}

	public boolean checkNetwork() {
		if ((wifiConnected || mobileConnected) || (wifiConnected)) {
			// AsyncTask subclass
			return true;
		} else
			return false;
	}
}