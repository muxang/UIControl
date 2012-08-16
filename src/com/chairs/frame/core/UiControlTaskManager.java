package com.chairs.frame.core;

import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;

import android.util.Log;

import com.chairs.frame.interfc.Idata;

public class UiControlTaskManager {

	// @define Other
	// ！--------------constant

	// ----------------constant end
	// !----------------variable
	private static UiControlTaskManager instance;
	private FutureContainer mFutureContainer;
	private ThreadPoolManager mThreadPoolManager;
	private TaskQueueManager mTaskQueue;

	// ----------------variable end

	// #endDf Other

	private UiControlTaskManager() {
		mThreadPoolManager = ThreadPoolManager.getInstance();
		mFutureContainer = FutureContainer.getInstance();
		mTaskQueue = TaskQueueManager.getInstance();
	}

	public static UiControlTaskManager getInstance() {
		if (instance == null) {
			instance = new UiControlTaskManager();
		}
		return instance;
	}
	

	@SuppressWarnings("unchecked")
	protected Future exec(UiTask task, Idata data) {
		if (task == null)
			return null;
		Future future = null;
		task.setRequestData(data);// 设置请求数据
		if (hasTask(task)) {// 如果任务已存在,-->待处理
			
		} else {
			mTaskQueue.add(task);//将此任务加入优先级队列
		}
		return future;
	}

	protected boolean hasTask(UiTask task) {
		return mTaskQueue.contains(task) || mFutureContainer.hasFuture(task.getTaskId());
	}

	/*---------------------------------------------------------------
	 * 取消任务有待研究跟完善
	 *---------------------------------------------------------------
	 */
	protected boolean cancleTask(UiTask task) {
		if (hasTask(task)) {
			if (mTaskQueue.contains(task)) {//如果任务还在队列中，移除任务即可
				return mTaskQueue.remove(task);
			}
			return mFutureContainer.getFuture(task.getTaskId()).cancel(true);
		}
		return false;
	}

	protected void cancleTaskGroup(UiTask[] tasks) {
		for (UiTask ut : tasks) {
			cancleTask(ut);
		}
	}

	protected void cancleAllTask() {
		mTaskQueue.clear();
		mThreadPoolManager.shutdownNow();
	}
	/*---------------------------------------------------------------
	 * 取消任务有待研究跟完善 -----------------------------------------------
	 *---------------------------------------------------------------
	 */
}
