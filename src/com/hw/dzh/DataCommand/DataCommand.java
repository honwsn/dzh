package com.hw.dzh.DataCommand;

import com.android.volley.VolleyError;
import com.hw.dzh.proto.ProtoCommandExecutor;
import com.hw.dzh.proto.ProtoRequest;

public class DataCommand implements com.hw.dzh.proto.ProtoRequestCallBack {
	
	public interface onDataCommandFinishedListener {
		
		public void onSuccess(Command cmd, long optionalInfo1, int optionalInfo2);

		public void onFail(Command cmd, long optionalInfo, int errorType);
	}
	
	protected onDataCommandFinishedListener mListener;
	protected ProtoRequest mRequest;
	protected Command mCommandID;
	protected String mServerName = "circle";
	protected String mReqName = "stReq";
	protected String mRspName = "stRsp";
	protected String mFuncName = "";
	public long   mStartTime = System.currentTimeMillis();

	protected void setCommandID(Command id) {
		this.mCommandID = id;
	}

	public Command getCommandID() {
		return mCommandID;
	}
	
	public void setListener(onDataCommandFinishedListener mListener) {
		this.mListener = mListener;
	}

	public void cancel(){
		this.mListener = null;
	//	mRequest.cancel();
	}

	public boolean execute() {
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
		// TODO Auto-generated method stub

	}

	@Override
	public void onErrorResponse(VolleyError arg0) {
		// TODO Auto-generated method stub

	}

}
