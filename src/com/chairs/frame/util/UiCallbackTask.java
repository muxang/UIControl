package com.chairs.frame.util;

import java.lang.ref.SoftReference;

import com.chairs.frame.core.UiTask;
import com.chairs.frame.exception.NoCallbackException;
import com.chairs.frame.interfc.IControlTaskCallback;

/**
 * @author chairsmu    
 * @version 1.0  
 * @created 2012-6-28 下午1:37:12
 * TODO:通过callback回调的task
 */
public abstract class UiCallbackTask extends UiTask{
	public SoftReference<IControlTaskCallback> callback;
	
	public UiCallbackTask(IControlTaskCallback callback) throws NoCallbackException{
		super();
		if(callback != null){
			this.callback = new SoftReference<IControlTaskCallback>(callback);
		} else {
			throw(new NoCallbackException());
		}
	}

}
