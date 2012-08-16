package com.chairs.frame.core;

import com.chairs.frame.interfc.Idata;
import com.chairs.frame.util.ITaskHandler;

/**
 * @author chairsmu    
 * @version 1.0  
 * @created 2012-6-20 下午2:47:23
 */
public class UiControlManager {
	private UiControlTaskManager mTaskManager;
	private static UiControlManager instance;
	
	private UiControlManager() {
		mTaskManager = UiControlTaskManager.getInstance();
	}
	
	public static UiControlManager getInstance(){
		if(instance == null){
			instance = new UiControlManager();
		}
		return instance;
	}
	
	public boolean exec(UiTask task, Idata data){
		return mTaskManager.exec(task, data) != null;
		
	}
	
	public boolean cancelTask(UiTask task){
		return mTaskManager.cancleTask(task);
	}
	
	public void cancelTaskGroup(UiTask []tasks){
		mTaskManager.cancleTaskGroup(tasks);
	}
	
	public void cancelAllTask(){
		mTaskManager.cancleAllTask();
	}
	
	
}
