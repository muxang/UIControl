package com.chairs.frame;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;

public class TaskCancelManager extends HandlerThread implements Callback {
	private static final int CANCEL_TASK_CMD = 0x000001;
	ScheduledExecutorService canCelScheduledService;//
	Handler mHandler;

	public TaskCancelManager(String name) {
		super(name);
		mHandler = new Handler(this);
	}

	public void canCelCmd(Future future) {
		Message msg = mHandler.obtainMessage();
		msg.what = CANCEL_TASK_CMD;
		msg.obj = future;
		msg.sendToTarget();
	}

	@Override
	public boolean handleMessage(Message msg) {
		switch (msg.what) {
		case CANCEL_TASK_CMD:
			Future taskFuture = (Future)msg.obj;
			taskFuture.cancel(true);
			break;

		default:
			break;
		}
		return false;
	}

}
