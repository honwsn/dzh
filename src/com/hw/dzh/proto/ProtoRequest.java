package com.hw.dzh.proto;

import com.hw.dzh.network.BinaryRequest;
import com.google.protobuf.Message;
import com.hw.dzh.DataCommand.Command;

import com.hw.dzh.data.proto.ProtoDzh;

public class ProtoRequest {

	private String mServerName = "";
	private String mFuncName = "";
	private Message mPayLoad;
	private Command mCommandID;
	
	private String mReqUrl;
	private BinaryRequest 	mNetWorkRequest = null;

	public ProtoRequest() {
		// TODO Auto-generated constructor stub
	}

	public ProtoRequest(String serverName, String funcName, Message payLoad) {
		mServerName = serverName;
		mFuncName = funcName;
		mPayLoad = payLoad;
	}

	public void setCommandID(Command id) {
		this.mCommandID = id;
	}

	public Command getCommandID() {
		return mCommandID;
	}

	public String getReqUrl() {
		return mReqUrl;
	}

	public void setReqUrl(String reqUrl) {
		mReqUrl = reqUrl;
	}
	
	
	public void setRequest(BinaryRequest networkRequest){
		mNetWorkRequest = networkRequest;
	}
	
	public void cancel(){
		if(mNetWorkRequest != null){
			mNetWorkRequest.cancel();
		}
	}
	
	public byte[] toByteArray(){
		ProtoDzh.ProtoPacket.Builder builder = ProtoDzh.ProtoPacket.newBuilder();
		builder.setServerName(mServerName);
		builder.setFuncName(mFuncName);
		builder.setPayload(mPayLoad.toByteString());
		ProtoDzh.ProtoPacket packet= builder.build();
		return packet.toByteArray();
	}
}
