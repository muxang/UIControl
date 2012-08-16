package com.chairs.frame.util;

import java.lang.ref.SoftReference;

import com.chairs.frame.core.UiTask;
import com.chairs.frame.exception.NoHandlerException;

/**
 * @author chairsmu    
 * @version 1.0  
 * @created 2012-6-28 下午1:39:23
 * TODO:通过handler回传的task
 */
public abstract class UiHandlerTask extends UiTask {
	public SoftReference<ITaskHandler> mHandler;
	public UiHandlerTask(ITaskHandler handler) throws NoHandlerException{
		super();
		if(handler != null){
			mHandler = new SoftReference<ITaskHandler>(handler);
		} else {
			throw(new NoHandlerException());
		}
	}
}
