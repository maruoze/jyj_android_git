package com.jxtzw.service;

import com.jxtzw.app.ui.MainActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class CheckNewsService extends Service {
	private NotificationManager mNotificationManager;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.v("Blur", "Test ServiceStart");
		/*Notification n = new Notification();  
        //n.icon = R.drawable.icon;  
        n.tickerText = "Notification";  
        n.when = System.currentTimeMillis();  
        //n.flags=Notification.FLAG_ONGOING_EVENT;  
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);  
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);  
        n.setLatestEventInfo(getApplicationContext(), "title","content", pi);  
        mNotificationManager.notify(1, n);  */
	}
	
	
	

}
