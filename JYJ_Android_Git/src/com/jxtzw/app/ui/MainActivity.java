package com.jxtzw.app.ui;

import java.util.ArrayList;


import com.jxtzw.app.AppConfig;
import com.jxtzw.app.AppManager;
import com.jxtzw.app.R;
import com.jxtzw.app.R.color;
import com.jxtzw.app.common.DataHelper;
import com.jxtzw.app.common.DoubleClickExitHelper;
import com.jxtzw.app.common.UIHelper;
import com.jxtzw.app.common.UpdateManage;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings.System;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
	/**
	 * 上下文及资源相关
	 */
	protected Context mContext;
	protected Activity mActivity;
	protected Resources mResources;
	protected LayoutInflater mLayoutInflater;
	protected SharedPreferences mSharedPreferences;
	/**
	 * TabHost相关
	 */
	private TabHost mTabHost;
	private Class<?>[] mTabClasses={TabHomeActivity.class,TabQuotActivity.class,TabClassActivity.class,
													TabCollegeActivity.class,TabMemberActivity.class};
	private String[] mTabTitleStrings;
	private String[] mTabTagsStrings={"Home","Quotation","Classify","College","Member"};
	private int[] mTabImages={	R.drawable.f001,R.drawable.f002,R.drawable.f003,
												R.drawable.f004,R.drawable.f005};
	private int mTabCount;
	private OnTabChangeListener mOnTabChangeListener;
	private android.view.View.OnClickListener mOnTabClickListener;
	/**
	 * TabHost的tab的TextView
	 */
	private ArrayList<TextView> mTabTextViews;
	/**
	 * 双击退出
	 */
	private DoubleClickExitHelper mDoubleClickExitHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		AppManager.getAppManager().addActivity(this);
		init();
		initTabHost();
		checkUpdate();
		initServices();
	}
	
	/**
	 * 初始化上下文几及资源
	 */
	protected void init(){
		mContext=this;
		mActivity=(Activity)mContext;
		mResources=getResources();
		mLayoutInflater=LayoutInflater.from(this);
		mSharedPreferences=AppConfig.getSharedPreferences(this);
		getConfig();
		mTabTextViews=new ArrayList<TextView>();
		mDoubleClickExitHelper=new DoubleClickExitHelper(this);
	}
	
	/**
	 * 初始化TabHost
	 */
	protected void initTabHost() {
		//mTabTitleStrings=mResources.getStringArray(R.array.main_tabs_text);
		mTabCount=mTabClasses.length;
		mTabHost=getTabHost();
		for (int i = 0; i < mTabCount; i++) {
			String tag=mTabTagsStrings[i];
			Intent intent=new Intent(this, mTabClasses[i]);
			intent.putExtra("MainTitle", mTabTitleStrings[i]);
			TabHost.TabSpec tabSpec=initTabSpec(tag,mTabTitleStrings[i],mTabImages[i],intent,i);
			mTabHost.addTab(tabSpec);
		}
		initOnTabChange();
		mTabHost.setOnTabChangedListener(mOnTabChangeListener);
		mTabHost.getTabWidget().getChildAt(2).setOnClickListener(mOnTabClickListener);
	}
	
	/**
     * 自定义创建标签项的方法
	 */
	protected TabHost.TabSpec initTabSpec(String tag,String title,int image_id,Intent intent,int index) {
		TabHost.TabSpec tabSpec = null;
		tabSpec=mTabHost.newTabSpec(tag);
		TextView textView=initTabSpecTextView(tag,title,image_id);
		if(index==0){
			textView.setBackgroundColor(mResources.getColor(R.color.black));
		}else{
			textView.setBackgroundColor(mResources.getColor(R.color.light_black));
		}
		tabSpec.setIndicator(textView);
		tabSpec.setContent(intent);
		mTabTextViews.add(textView);
		return tabSpec;
	}
	
	/**
	 * 初始化Indicator
	 */
	@SuppressLint("InflateParams")
	protected TextView initTabSpecTextView(String tag,String title,int image_id){
		TextView textView=(TextView) mLayoutInflater.inflate(R.layout.tabhost_tab, null);
		textView.setTag(tag);
		textView.setText(title);
		Drawable topDrawable=mResources.getDrawable(image_id);
		topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
		textView.setCompoundDrawables(null, topDrawable, null, null);
		return textView;
	} 
	
	/**
	 * 重写OnTabChange
	 */
	@SuppressLint("InflateParams")
	protected void initOnTabChange() {
		/*DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int tabTVWidth=metric.widthPixels;
		View popView=mLayoutInflater.inflate(R.layout.pop_tab, null);
		//mPWTab=new PopupWindow(popView,tabTVWidth/6*3,90);
		mPWTab=new PopupWindow(popView);
		mPWTab.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);  
		mPWTab.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);*/
		
		
		mOnTabChangeListener=new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				/*if(tabId.equals("Classify")){
					View v=mTabHost.getTabWidget().findViewWithTag(tabId);
					mPWTab.showAtLocation(v, Gravity.BOTTOM, 0, 90);
					mPWTab.setOutsideTouchable(true);
					
				}else{
					mPWTab.dismiss();
				}*/
				//清除颜色
				for (int i = 0; i < mTabTextViews.size(); i++) {
					mTabTextViews.get(i).setBackgroundColor(mResources.getColor(R.color.light_black));
				}
				//设置颜色
				TextView tView=(TextView) mTabHost.getTabWidget().findViewWithTag(tabId);
				tView.setBackgroundColor(mResources.getColor(R.color.black));
			}
		};
		
		//显示弹出的导航菜单
		mOnTabClickListener=new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//选择对应的TabWidget
				mTabHost.setCurrentTab(2);
				//选择对应的TabContent，并显示弹出菜单
				mTabHost.getCurrentView().findViewById(R.id.popwindow_box).setVisibility(View.VISIBLE);
			}
		};
	}
	
	/**
	 * 获取程序配置数据
	 */
	protected void getConfig() {
		mTabTitleStrings=DataHelper.getMainTabsText(mResources,mSharedPreferences);
		AppConfig.isLogin=mSharedPreferences.getBoolean(AppConfig.IS_LOGIN, false);
		AppConfig.isMember=mSharedPreferences.getBoolean(AppConfig.IS_MEMBER, false);
		AppConfig.uid=mSharedPreferences.getString(AppConfig.UID, "");
		AppConfig.username=mSharedPreferences.getString(AppConfig.USERNAME, "");
		AppConfig.password=mSharedPreferences.getString(AppConfig.PASSWORD, "");
	}
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		// 结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
		super.onDestroy();
	}
	
	/**
	 * 截获返回按钮，重写返回事件
	 */
	public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.KEYCODE_BACK){  
             if (event.getAction() == KeyEvent.ACTION_DOWN && event.getRepeatCount() == 0) { 
            	 mDoubleClickExitHelper.onKeyDown(event.getKeyCode(), event);
                 /*AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
                 builder.setTitle("温馨提示：");
                 builder.setMessage("确定要退出金银家吗？");
                 builder.setPositiveButton("确定", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						AppManager.getAppManager().AppExit(mContext);
						//彻底退出APP的方法2,需要android.permission.KILL_BACKGROUND_PROCESSES权限
						//ActivityManager am= (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
						//am.killBackgroundProcesses(mContext.getPackageName());
					}
                 });
                 builder.setNegativeButton("取消", null);
                 builder.create();
                 builder.show();*/
             }  
             return true;  
        } 
        return super.dispatchKeyEvent(event);  
    }
	
	/**
	 * 检查更新
	 */
	private void checkUpdate() {
		boolean checkUpdate=true;
		checkUpdate=mSharedPreferences.getBoolean(AppConfig.CHECK_UPDATE, true);
		if (checkUpdate) {
			UpdateManage.getUpdateManage().checkAppUpdate(mContext,true,false);
		}
	}
	
	/**
	 * 初始化服务
	 */
	private void initServices() {
		Intent serviceIntent=new Intent();
		String serviceName="com.jxtzw.app.service.CheckNewsService";
		serviceIntent.setAction(serviceName);
		startService(serviceIntent);
	}
}
