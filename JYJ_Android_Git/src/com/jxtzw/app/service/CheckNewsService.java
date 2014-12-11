package com.jxtzw.app.service;

import com.jxtzw.app.handler.CheckNewsHandler;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class CheckNewsService extends Service {
	/**
	 * 上下文
	 */
	private Context mContext;

	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		this.mContext=this;
		Log.v("Blur","IN SERVICE");
		startCheckNewsTimer();
	}
	
	/**
	 * 开启新文章检测计时器
	 */
	private void startCheckNewsTimer() {
		CheckNewsHandler checkNewsHandler=new CheckNewsHandler(mContext);
		checkNewsHandler.checkNews();
	}
}
