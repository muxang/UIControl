package com.chairs.frame.core;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

public class FutureContainer {
	private static final int HARD_CACHE_CAPACITY = 30;
	private static FutureContainer instance;

	private final ConcurrentHashMap<Long, SoftReference<Future>> mSoftCache = new ConcurrentHashMap<Long, SoftReference<Future>>();

	private final HashMap<Long, Future> mFutureCache = new LinkedHashMap<Long, Future>(
			HARD_CACHE_CAPACITY, 0.75f, true) {
		protected boolean removeEldestEntry(
				java.util.Map.Entry<Long, Future> eldest) {
			if (size() > HARD_CACHE_CAPACITY) {
				mSoftCache.put(eldest.getKey(), new SoftReference<Future>(
						eldest.getValue()));
				return true;
			} else {
				return false;
			}
		};
	};

	
	private FutureContainer(){
		
	}
	
	public static FutureContainer getInstance() {
		if (instance == null) {
			instance = new FutureContainer();
		}
		return instance;
	}

	public Future getFuture(Long taskId) {
		if (mFutureCache.containsKey(taskId)){
			return mFutureCache.get(taskId);
		} else if(mSoftCache.containsKey(taskId)){
			return mFutureCache.get(taskId);
		}
		return null;
	}

	public boolean setFuture(long taskId, Future future) {
		if (hasFuture(taskId)) {
			return false;
		} else {
			mFutureCache.put(taskId, future);
			return true;
		}

	}

	public boolean hasFuture(long taskId) {
		return mFutureCache.containsKey(taskId)
				|| mSoftCache.containsKey(taskId);
	}

	public Future removeFuture(long taskId) {
		if (hasFuture(taskId)) {
			Future ft;
			if (mFutureCache.containsKey(taskId)) {
				ft = mFutureCache.remove(taskId);
			} else {
				ft = mSoftCache.remove(taskId).get();
			}

			return ft;
		}
		return null;
	}
}
