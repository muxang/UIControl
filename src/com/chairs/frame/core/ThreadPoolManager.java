package com.chairs.frame.core;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager  {
	public static ThreadPoolManager instance;
	private static final int THREAD_POOL_CORE_NUM = Runtime.getRuntime()
			.availableProcessors();// cpu个数-》运行线程数量
	private static final int THREAD_POOL_RUNING_MAX = 10;// 最大线程数量
	private static final int THREAD_POOL_QUEUE_NUM = 50;// 缓存队列数量

	private ThreadPoolExecutor singleThreadPool;// 固定线程数的线程池
	private ThreadPoolExecutor limitLessThreadPool;// 无界线程池
	private ThreadPoolExecutor limitThreadPool;// 有界线程池

	// ---------------------public api------------------------

	public static ThreadPoolManager getInstance() {
		if (instance == null) {
			instance = new ThreadPoolManager();
		}
		return instance;
	}

	/**
	 * @Title: submit
	 * @Description: 提交任务到相应的线程池
	 * @param task
	 * @return Future 返回future
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public Future submit(UiTask task) {
		switch (task.getTaskType()) {
		case UiTask.SINGLE_TASK:// 单线程任务
			return singleThreadPool.submit(task);
		case UiTask.LIMIT_LESS_TASK:// 无界任务
			return limitLessThreadPool.submit(task);
		case UiTask.LIMIT_TASK:// 有界任务
			return limitThreadPool.submit(task);
		default:
			return null;
		}

	}

	/**
	* @Title: shutdownNow 
	* @Description: TODO关闭所有任务 
	 */
	public void shutdownNow() {
		if (singleThreadPool != null) {
			singleThreadPool.shutdownNow();
		}

		if (limitLessThreadPool != null) {
			limitLessThreadPool.shutdownNow();
		}

		if (limitThreadPool != null) {
			limitThreadPool.shutdownNow();
		}
	}

	// ----------------------end api----------------------------

	// ----------------------private Method---------------------

	private ThreadPoolManager() {
		getSinglePool();
		getLimitPool();
		getLimitLessPool();
	}

	private ThreadPoolExecutor getSinglePool() {// 取单线程线程池
		if (singleThreadPool == null) {
			singleThreadPool = new ThreadPoolExecutor(1, 1, 0L,
					TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

		}
		return singleThreadPool;
	}

	private ThreadPoolExecutor getLimitPool() {// 取有界线程池
		if (limitThreadPool == null) {
			limitThreadPool = new ThreadPoolExecutor(THREAD_POOL_CORE_NUM,
					THREAD_POOL_RUNING_MAX, 60L, TimeUnit.SECONDS,
					new ArrayBlockingQueue<Runnable>(THREAD_POOL_QUEUE_NUM));
		}
		return limitThreadPool;
	}

	private ThreadPoolExecutor getLimitLessPool() {// 取无界线程池
		if (limitLessThreadPool == null) {
			limitLessThreadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
					60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

		}
		return limitLessThreadPool;
	}
}
