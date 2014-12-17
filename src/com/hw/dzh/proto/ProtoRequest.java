package com.hw.dzh.proto;

import com.hw.dzh.DataCommand.Command;

public class ProtoRequest {

	private String mServerName = "";
	private String mFuncName = "";
	private Object mPayLoad;
	protected Command mCommandID;

	public ProtoRequest() {
		// TODO Auto-generated constructor stub
	}

	public ProtoRequest(String serverName, String funcName, Object payLoad) {
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

}
