package com.chairs.frame.core;

import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

import com.chairs.frame.interfc.Idata;

import android.util.Log;

public abstract class UiTask implements Callable, Comparable<UiTask> {

	public static final int SINGLE_TASK = 0x000001;// ---》ThreadPool 中为单线程
	public static final int LIMIT_TASK = 0x000002;// ----》ThreadPool 为有界池
	public static final int LIMIT_LESS_TASK = 0x000003;// ----》ThreadPool 为无界池
	public int taskType;// 任务类型-》根据不同的任务类型投放到不同的线程池当中去
	public Idata mRequestdata;
	public int priority = 5;// 任务优先级 1-10,数值越大优先级越高，默认为5.
	private long taskId;

	public UiTask() {
		taskId = System.currentTimeMillis();
		taskType = LIMIT_TASK;
	}

	@Override
	public Object call() {
		Log.i("chairs", "task call");
		start();
		return null;
	}

	public abstract void start();

	public void setRequestData(Idata data) {
		mRequestdata = data;
	}

	public long getTaskId() {
		return taskId;
	}

	public int getTaskType() {
		return taskType;
	}

	public void setTaskType(int taskType) {
		this.taskType = taskType;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	/*
	 * 优先级处理，数值起大优先级越高
	 */
	public int compareTo(UiTask another) {

		return this.priority < another.priority ? 1
				: this.priority > another.priority ? -1 : 0;

	}
}
