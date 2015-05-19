# dzh
demo to show protobuf packet encode & decode at android.
use volley as network lib ,base on http to send &rev protobuf packet.
follwing code use ProtoDzh.proto as protocol.
ProtoDzh.proto is google proto file.



encode：DzhActivity.java
	ProtoDzh.UserInfo.Builder bd = ProtoDzh.UserInfo.newBuilder();
	bd.setSNickname("hello world");
	bd.setSUserId("100000");
	bd.setEAccountType(ProtoDzh.AccountType.ACCOUNT_WECHAT);
	bd.setSFaceIcon("http://www.baidu.com");
	ProtoDzh.UserInfo userInfo = bd.build();
	DataCommand cm = new DataCommand("","",Command.CMD_LOGIN,userInfo,this);
	cm.execute();


decode：DataCommand.java
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
