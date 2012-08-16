package com.chairs.example;

import android.util.Log;

import com.chairs.frame.exception.NoCallbackException;
import com.chairs.frame.interfc.IControlTaskCallback;
import com.chairs.frame.util.UiCallbackTask;

public class MyCallBackTask extends UiCallbackTask {

	public MyCallBackTask(IControlTaskCallback callback) throws NoCallbackException {
		super(callback);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start() {
		Log.i("MyTask", "call is call pre");
		int i = 0 ;
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				break;
			}
			i+=2;
			mRequestdata.setObj(i);
			callback.get().respose(mRequestdata);
		}

	}

}
