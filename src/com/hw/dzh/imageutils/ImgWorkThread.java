/**
 * maxxiang
 * 2014-7-17
 * TODO
 */
package com.hw.dzh.imageutils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * @author maxxiang
 * 专用于图片截�?解码的线�?
 * Create On: 2014-7-17 
 */
public class ImgWorkThread {
	private HandlerThread mThread;
	private Handler mHandler;
	private Handler mUiHandler;
	private static ImgWorkThread mInstance;
	
	public static ImgWorkThread getInstance(){
		if (mInstance == null){
			mInstance = new ImgWorkThread();
			mInstance.init();
		}
		return mInstance;
	}
	
	private void init(){
		mThread = new HandlerThread("ImageCropThread", Thread.MIN_PRIORITY);//默认�?��优先�?..
		mThread.start();
		mHandler= null;
	}
	
	public void setPriority(int priority) {
		if (mInstance == null){
			return;
		}
		mThread.setPriority(priority);
	}
	
	public Handler getWorkThreadHandler(){
		if (mHandler == null){
			mHandler = new Handler(mThread.getLooper());
		}
		return mHandler;
	}
	
	public void postToWorkThread(Runnable r){
		if(r==null){
			return;
		}
		
		getWorkThreadHandler().post(r);
		
	}
	
	public Handler getUiHandler(){
		if (mUiHandler == null){
			mUiHandler = new Handler(Looper.getMainLooper());
		}
		return mUiHandler;
	}
	
	public void postToMainThread(Runnable r){
		if(r==null){
			return;
		}
		
		getUiHandler().post(r);
		
	}
}
