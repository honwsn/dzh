package com.hw.dzh.DataCommand;

import com.android.volley.VolleyError;
import com.google.protobuf.Message;
import com.hw.dzh.proto.ProtoCommandExecutor;
import com.hw.dzh.proto.ProtoRequest;

public class DataCommand implements com.hw.dzh.proto.ProtoRequestCallBack {
	
	public interface onProtoRequestFinishedListener {
		public void onSuccess(Command cmd, long optionalInfo1, int optionalInfo2);
		public void onFail(Command cmd, long optionalInfo, int errorType);
	}
	
	protected onProtoRequestFinishedListener mListener;
	protected ProtoRequest mRequest;
	public long   mStartTime = System.currentTimeMillis();

//	public DataCommand(String serverName,String funcName,Command id,Message payLoad,onProtoRequestFinishedListener listener){
//		mRequest = new ProtoRequest(serverName,funcName,payLoad);
//		mRequest.setCommandID(id);
//		mListener = listener;
//	}
//	
	
	public void setListener(onProtoRequestFinishedListener listener) {
		this.mListener = listener;
	}

	public void cancel(){
		this.mListener = null;
		mRequest.cancel();
	}

	public boolean execute() {
		if(mRequest == null)
			return false;
		
		boolean res = true;
		try{
			res = ProtoCommandExecutor.getInstance().doExecute(mRequest, this);
		}
		catch(Exception e){
			return false;
		}
		return res;
	}

	@Override
	public void onResponse(byte[] arg0) {
		//parse data and notify
	}

	@Override
	public void onErrorResponse(VolleyError arg0) {
		//parse data and notify

	}

}
