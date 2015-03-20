package com.CTCStudio.ebookstorys.Util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Logs {
	String TAG = "EBOOK_STORY";

	public static void d(String msg) {
		if (Variable.isLog)
			Log.d("EBOOK_STORY", msg);

	}

	public static void e(String msg) {
		if (Variable.isLog)
			Log.e("EBOOK_STORY", msg);

	}

	public static void i(String msg) {
		if (Variable.isLog)
			Log.i("EBOOK_STORY", msg);

	}

	public static void showMes(Context ctx, String msg) {
		if (Variable.isLog)
			Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
	}
}
