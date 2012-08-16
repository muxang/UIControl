package com.chairs.example;

import android.util.Log;

import com.chairs.frame.core.UiTask;
import com.chairs.frame.exception.NoHandlerException;
import com.chairs.frame.util.ITaskHandler;
import com.chairs.frame.util.UiHandlerTask;

public class MyHandlerTask extends UiHandlerTask {

	public MyHandlerTask(ITaskHandler handler) throws NoHandlerException {
		super(handler);
		setTaskType(UiTask.LIMIT_LESS_TASK);
	}

	@Override
	public void start() {
		Log.i("chairs", "MyHandlerTask is start");
		int i = 0;
		while (true) {
			i+=2;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				break;
			}
			mRequestdata.setObj(i);
			mHandler.get().sendResposeData(mRequestdata);
		}
		
	}

}
