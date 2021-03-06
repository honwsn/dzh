package com.hw.dzh.proto;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.hw.dzh.AppEngine;
import com.hw.dzh.EnvironmentManager;
import com.hw.dzh.network.BinaryRequest;

public class ProtoCommandExecutor {

	public ProtoCommandExecutor() {
		// TODO Auto-generated constructor stub
	}
	private static ProtoCommandExecutor mInstance;
	private static Context mContext;
	private static RequestQueue mQueue;

	public static ProtoCommandExecutor getInstance() {
		if (mInstance == null) {
			mInstance = new ProtoCommandExecutor();

			mContext = AppEngine.getInstance().getApplicationContext();
			mQueue = Volley.newRequestQueue(mContext);
		}
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		return mQueue;
	}

	public void addTask(Request r) {

		mQueue.add(r);
	}
	
	public boolean doExecute(ProtoRequest r, ProtoRequestCallBack callback) {
		if (r == null)
			return false;

		byte[] postData = r.toByteArray();

		if (postData == null)
			return false;

		com.hw.dzh.network.BinaryRequest request;

		if (r.getReqUrl() != null && r.getReqUrl().length()>1){
			//get Server List
			request = new BinaryRequest(
					r.getReqUrl(), postData, callback, callback);
		}else{
			request = new BinaryRequest(
					EnvironmentManager.WUP_SERVER_ADDR, postData, callback, callback);
			
		}
		request.setTag(r.getCommandID());
		request.setShouldCache(false);
		r.setRequest(request);
		mQueue.add(request);

		return true;
	}

	public void cancelRequest(Object id) {
		// TODO:to be test
		mQueue.cancelAll(id);
	}

	public void cancelAllRequest() {
		mQueue.cancelAll(mContext);
	}

}
