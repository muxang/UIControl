package com.chairs.frame.exception;

public class NoHandlerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 10L;

	public NoHandlerException() {
		super("Task's handler is null");
	}


}
