package com.hw.dzh.DataCommand;

import android.util.Log;

import com.android.volley.VolleyError;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.hw.dzh.data.proto.ProtoDzh;
import com.hw.dzh.proto.ProtoCommandExecutor;
import com.hw.dzh.proto.ProtoRequest;

public class DataCommand implements com.hw.dzh.proto.ProtoRequestCallBack {
	
	public interface onProtoRequestFinishedListener {
		public void onSuccess(Command cmd, Object optionalInfo1, int optionalInfo2);
		public void onFail(Command cmd, long optionalInfo, int errorType);
	}
	
	protected onProtoRequestFinishedListener mListener;
	protected ProtoRequest mRequest;
	public long   mStartTime = System.currentTimeMillis();

	public DataCommand(String serverName,String funcName,Command id,Message payLoad,onProtoRequestFinishedListener listener){
		mRequest = new ProtoRequest(serverName,funcName,payLoad);
		mRequest.setCommandID(id);
		mListener = listener;
	}
	
	
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
	public void onResponse(byte[] data) {
		
		//parse data and notify
		ProtoDzh.ResponsePacket rspPacket;
		try{
			rspPacket = ProtoDzh.ResponsePacket.parseFrom(data);
		}
		catch(Exception e){
			e.printStackTrace();
			if(mListener != null){
				mListener.onFail(mRequest.getCommandID(), 0, ErrorType.ERROR_PARSE_PACKET);
			}
			
			return;
		}
		
		if(rspPacket == null || rspPacket.getRspCode() != 0){
			
			if(mListener != null){
				mListener.onFail(mRequest.getCommandID(), 0, ErrorType.ERROR_PARSE_PACKET);
			}
			return;
		}
		
		ProtoDzh.ForumReply forumReply = null;
		try {
			forumReply = ProtoDzh.ForumReply.parseFrom(rspPacket.getPayLoad());
		} catch (InvalidProtocolBufferException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		if(mListener != null){
			mListener.onSuccess(mRequest.getCommandID(),forumReply,0);
		}
	}

	@Override
	public void onErrorResponse(VolleyError arg0) {
		//parse data and notify
		

		if(mListener != null){
			Log.d("volley", "error:"+arg0);
			mListener.onFail(mRequest.getCommandID(),0,0);
		}
	}

}
