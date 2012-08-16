package com.chairs.frame;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class CancelTask implements Callable<Boolean> {
	Future futrue;

	public CancelTask(Future future) {
		this.futrue = future;
	}

	@Override
	public Boolean call() throws Exception {
		return futrue.cancel(true);
	}

}
