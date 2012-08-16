package com.chairs.frame.core;

import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;

import android.os.Handler;
import android.os.Message;

public class TaskQueueManager {
	private PriorityBlockingQueue<UiTask> mTaskQueue;
	private static TaskQueueManager instance;
	private FutureContainer mFutureContainer;
	private ThreadPoolManager mThreadPoolManager;
	private Thread outQueue;// 出队线程（消费者）:有阻塞情况
	private boolean end;// 是否结束线程
	private Handler mHandler;

	private TaskQueueManager() {
		mFutureContainer = FutureContainer.getInstance();
		mThreadPoolManager = ThreadPoolManager.getInstance();
		initHandler();
		initThreadQueue();
	}

	public static final TaskQueueManager getInstance() {
		if (instance == null) {
			instance = new TaskQueueManager();
		}
		return instance;
	}
	
	public void add(UiTask task) {
		mTaskQueue.add(task);
	}
	
	public boolean contains(UiTask task){
		return mTaskQueue.contains(task);
	}
	
	public boolean remove(UiTask task){
		return mTaskQueue.remove(task);
	}
	
	public void clear(){
		mTaskQueue.clear();
	}

	private void initHandler() {
		mHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				UiTask task = (UiTask) msg.obj;
				Future future = mThreadPoolManager.submit(task);
				if (future != null) {
					mFutureContainer.setFuture(task.getTaskId(), future);
				}
			}
		};
	}

	private void initThreadQueue() {
		mTaskQueue = new PriorityBlockingQueue<UiTask>();
		outQueue = new Thread() {
			public void run() {
				while (true) {
					try {
						UiTask task = (UiTask) mTaskQueue.take();
						if (task != null) {
							Message msg = mHandler.obtainMessage();
							msg.obj = task;
							msg.sendToTarget();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		outQueue.start();
	}

}
