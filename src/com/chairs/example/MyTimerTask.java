package com.chairs.example;

import android.util.Log;

import com.chairs.frame.exception.NoHandlerException;
import com.chairs.frame.util.ITaskHandler;
import com.chairs.frame.util.UiTimerTask;

public class MyTimerTask extends UiTimerTask{
	int i = 0;
	public MyTimerTask(ITaskHandler handler, long delay, long period,
			int tasktype) throws NoHandlerException {
		super(handler, delay, period, tasktype);		
	}

	@Override
	public void run() {
		Log.i("MyTimerTask time ", System.currentTimeMillis()+"");
		i+=2;
		mRequestdata.setObj(i);
		mHandler.get().sendResposeData(mRequestdata);
	}



}
