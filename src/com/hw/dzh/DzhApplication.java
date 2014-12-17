package com.hw.dzh;

import android.app.Application;

public class DzhApplication extends Application {

	public DzhApplication() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// 初始化AppEngine, 这里面会初始化很多需要在启动阶段初始化的类
		AppEngine.getInstance().onInitialize(getApplicationContext());

	}

}
