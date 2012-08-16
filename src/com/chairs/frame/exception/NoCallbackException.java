package com.chairs.frame.exception;

public class NoCallbackException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public NoCallbackException(){
		super("Task's callback is null");
	}

}
