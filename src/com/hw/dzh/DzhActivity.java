package com.hw.dzh;

import com.hw.dzh.DataCommand.Command;
import com.hw.dzh.DataCommand.DataCommand;
import com.hw.dzh.data.proto.ProtoDzh;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class DzhActivity extends Activity implements DataCommand.onProtoRequestFinishedListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(EnvironmentManager.IS_DEBUG)
			ViewServer.get(this).addWindow(this);
		
		setContentView(R.layout.activity_dzh);
	}
	
	@Override
	public void onDestroy() {
		if(EnvironmentManager.IS_DEBUG)
			ViewServer.get(this).removeWindow(this);
	}
	
 
	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		if (EnvironmentManager.IS_DEBUG)
			ViewServer.get(this).setFocusedWindow(this);

	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dzh, menu);
		
		BroadcastReceiver netWorkReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
					ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
					NetworkInfo info = connectivityManager.getActiveNetworkInfo();

					if (info != null && info.isConnected()) {
						Toast.makeText(DzhActivity.this, "网络已经恢复！ .....", Toast.LENGTH_LONG).show();   
					} else {
						Toast.makeText(DzhActivity.this, "当前网络不可用.....", Toast.LENGTH_LONG).show();
					}
				}
			}
		};	
		
		//注册网络变化通知
		IntentFilter filter = new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(netWorkReceiver, filter);
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			
			ProtoDzh.UserInfo.Builder bd = ProtoDzh.UserInfo.newBuilder();
			bd.setSNickname("hello world");
			bd.setSUserId("100000");
			bd.setEAccountType(ProtoDzh.AccountType.ACCOUNT_WECHAT);
			bd.setSFaceIcon("http://www.baidu.com");
			ProtoDzh.UserInfo userInfo = bd.build();
			DataCommand cm = new DataCommand("","",Command.CMD_LOGIN,userInfo,this);
			cm.execute();
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSuccess(Command cmd, Object optionalInfo1, int optionalInfo2) {
		// TODO Auto-generated method stub
		if(cmd == Command.CMD_LOGIN){
			Toast.makeText(this, "hello world .....", Toast.LENGTH_LONG).show();
			if(optionalInfo1 != null){
				ProtoDzh.ForumReply  reply  = (ProtoDzh.ForumReply )optionalInfo1;
				TextView tv = (TextView)findViewById(R.id.title);
				tv.setText(reply.getTitle());
				TextView content = (TextView)findViewById(R.id.content);
				content.setText(reply.getUrl());
			}
		}
	}

	@Override
	public void onFail(Command cmd, long optionalInfo, int errorType) {
		// TODO Auto-generated method stub
		if(cmd == Command.CMD_LOGIN){
			Toast.makeText(this, "failed to hello world", Toast.LENGTH_LONG).show();
		}
	}
}
