package com.chairs.frame.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import android.util.Log;

import com.chairs.frame.exception.NoHandlerException;

public abstract  class UiTimerTask extends UiHandlerTask implements Runnable{
	public static final int TYPE_REPEAT_TASK_ONTIME = 0x000000;//按时重复执行的任务 
	public static final int TYPE_DELAY_TASK = 0x000001;//延迟执行的任务 
	public static final int TYPE_REPEAT_TASK_COMPLETE = 0x000003;//安排所提交的任务在每次执行完后，等待delay所指定的时间后重复执行。
	private ScheduledExecutorService mSes;
	public long mDelayTime;
	public long mPeriodTime;
	public int mTaskType;
	public ScheduledFuture mFuture; 
	public UiTimerTask(ITaskHandler handler,long delay,long period,int tasktype) throws NoHandlerException {
		super(handler);
		mSes = Executors.newScheduledThreadPool(1);
		mDelayTime = delay;
		mPeriodTime = period;
		mTaskType = tasktype;
	}
	
	@Override
	public void start() {
		Log.i("chairs", "timer start");
		switch (mTaskType) {
		case TYPE_REPEAT_TASK_ONTIME:
			mFuture = mSes.scheduleAtFixedRate(this, mDelayTime, mPeriodTime, TimeUnit.MICROSECONDS);
			break;
		case TYPE_REPEAT_TASK_COMPLETE:
			mFuture = mSes.scheduleWithFixedDelay(this, mDelayTime, mPeriodTime, TimeUnit.MICROSECONDS);
			break;
		case TYPE_DELAY_TASK:
			mFuture = mSes.schedule((Runnable)this, mDelayTime, TimeUnit.MICROSECONDS);
			break;

		default:
			break;
		}
	}
	
	public void cancelTimerTask(){
		mFuture.cancel(false);
	}

	
}
