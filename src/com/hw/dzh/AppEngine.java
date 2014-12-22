package com.hw.dzh;

import java.io.File;

import com.hw.dzh.imageutils.ImageCacheManager;
import com.hw.dzh.ui.base.Toaster;
import com.hw.dzh.utils.MttLog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;

public class AppEngine {
	private static final AppEngine mInstance = new AppEngine();

	private Handler mUiHandler = null;
	private Context mApplicationContext;
	private BroadcastReceiver mNetWorkReceiver = null;
	private boolean mNetWorkConnected = true;

	public static AppEngine getInstance() {
		return mInstance;
	}

	public Handler getUiHandler() {
		if (mUiHandler == null) {
			mUiHandler = new Handler(Looper.getMainLooper());
		}
		return mUiHandler;
	}

	public BroadcastReceiver getNetWorkReceiver() {
		return mNetWorkReceiver;
	}

	public void setApplicationContext(Context context) {
		mApplicationContext = context;
	}

	public Context getApplicationContext() {
		return mApplicationContext;
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivity = (ConnectivityManager) mApplicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null)
			return false;

		NetworkInfo[] info = connectivity.getAllNetworkInfo();
		if (info != null) {
			for (int i = 0; i < info.length; i++) {
				if (info[i].getState() == NetworkInfo.State.CONNECTED)
					return true;
			}
		}

		return false;
	}

//	private void onInitializeNeworkStateMachine() {
//		mNetWorkConnected = isNetworkAvailable();
//		mNetWorkReceiver = new BroadcastReceiver() {
//			@Override
//			public void onReceive(Context context, Intent intent) {
//				String action = intent.getAction();
//				if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
//					ConnectivityManager connectivityManager = (ConnectivityManager) mApplicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
//					NetworkInfo info = connectivityManager.getActiveNetworkInfo();
//
//					if (info != null && info.isConnected()) {
//						MttLog.d("onReceive(): isConnected=" + info.isConnected() + ", isAvailable=" + info.isAvailable());
//
//						if (!mNetWorkConnected) {
//							mNetWorkConnected = true;
//							Toaster.show(getApplicationContext(), "网络已经恢复", Toaster.SHORT);
//						}
//					} else if (mNetWorkConnected) {
//						mNetWorkConnected = false;
//						Toaster.show(getApplicationContext(), "当前网络不可用！", Toaster.SHORT);
//					}
//				}
//			}
//		};
//	}

	public void onInitialize(Context context) {
		setApplicationContext(context);

		// 初始化图片cache的类
		ImageCacheManager.getInstance().init(context);

//		onInitializeNeworkStateMachine();

	}

	public boolean isNetWorkAvailable() {
		return mNetWorkConnected;
	}

}