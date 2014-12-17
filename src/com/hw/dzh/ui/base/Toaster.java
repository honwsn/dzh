package com.hw.dzh.ui.base;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;



public class Toaster {
	public static final int SHORT = Toast.LENGTH_SHORT;
	public static final int LONG = Toast.LENGTH_LONG;

	public static void show(Context context, String text, int time) {
		Toast toast = Toast.makeText(context, text, time);
		toast.setGravity(Gravity.BOTTOM, 0, 40);
		toast.show();
	}
}
