package com.chairs.frame.util;

import android.os.Handler;
import android.os.Message;

import com.chairs.frame.interfc.Idata;

public abstract class ITaskHandler extends Handler {

	public void sendResposeData(Idata resposeData){
		Message msg = this.obtainMessage();
		msg.obj = resposeData;
		msg.sendToTarget();
	}
	
	public void handleMessage(Message msg){
		work((Idata) msg.obj);
	}
	
	public abstract void work(Idata resposeData);
}
