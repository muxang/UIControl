package com.chairs.example;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chairs.frame.R;
import com.chairs.frame.core.UiControlManager;
import com.chairs.frame.exception.NoCallbackException;
import com.chairs.frame.exception.NoHandlerException;
import com.chairs.frame.interfc.IControlTaskCallback;
import com.chairs.frame.interfc.Idata;
import com.chairs.frame.util.ITaskHandler;

public class UiControlActivity extends Activity {
	TextView mTv;
	UiControlManager mUIControl;
	IControlTaskCallback mCallback;

	MyCallBackTask mytask;
	MyHandlerTask myhtask;
	MyTimerTask myttask;
	ITaskHandler imHandler;
	Handler mHandler = new Handler();
	StringBuilder mStr = new StringBuilder();
	Button mBt;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mTv = (TextView) findViewById(R.id.hhe);
		mBt = (Button) findViewById(R.id.nnh);
		mUIControl = UiControlManager.getInstance();

		handlerTest();

	}

	public void timerTest() {
		imHandler = new ITaskHandler() {

			@Override
			public void work(Idata reposeData) {
				Log.i("chairs", "timerTest handler");
				mStr.append(reposeData.getObj().toString());
				mTv.setText(reposeData.getObj().toString());

			}
		};
		try {
			myttask = new MyTimerTask(imHandler, 1000, 1000, MyTimerTask.TYPE_REPEAT_TASK_COMPLETE);
		} catch (NoHandlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mUIControl.exec(myttask, new MyData(111));
	}

	public void handlerTest() {
		imHandler = new ITaskHandler() {

			@Override
			public void work(Idata reposeData) {
				mStr.append(reposeData.getObj().toString());
				mTv.setText(mStr);

			}
		};

		try {
			myhtask = new MyHandlerTask(imHandler);
		} catch (NoHandlerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mUIControl.exec(myhtask, new MyData(111));
	}

	public void callbackTest() {
		mCallback = new IControlTaskCallback() {

			@Override
			public void respose(final Idata resposeData) {
				Log.i("chairs", "callback" + resposeData.getObj().toString());
				mHandler.post(new Runnable() {

					@Override
					public void run() {
						mStr.append(resposeData.getObj().toString());
						mTv.setText(mStr);
					}
				});

			}

			@Override
			public void error(Idata errorData) {

			}
		};

		try {
			mytask = new MyCallBackTask(mCallback);
		} catch (NoCallbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mUIControl.exec(mytask, new MyData(111));

		mBt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.e("chairs", "cancle" + mUIControl.cancelTask(mytask));
				// mUIControl.cancelAllTask();
			}
		});
	}
}