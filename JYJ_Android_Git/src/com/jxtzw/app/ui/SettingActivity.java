package com.jxtzw.app.ui;

import com.jxtzw.app.AppConfig;
import com.jxtzw.app.AppManager;
import com.jxtzw.app.R;
import com.jxtzw.app.common.UIHelper;
import com.jxtzw.app.view.LoginDialog;

import android.accounts.Account;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SettingActivity extends PreferenceActivity {
	/**
	 * 
	 */
	private Context mContext;
	private SharedPreferences mSharedPreferences;
	/**
	 * UI标题栏
	 */
	protected Button mHeadLeft;
	protected Button mHeadRight;
	protected TextView mCatNameTextView;
	protected OnClickListener mHLClickListener;
	protected OnClickListener mHRClickListener;
	/**
	 * 数据标题栏
	 */
	protected String mCatName;
	/**
	 * 设置选项
	 */
	private Preference mAccount;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);
		setContentView(R.layout.activity_setting);
		// 设置显示Preferences
		addPreferencesFromResource(R.xml.setttings);
		mContext=this;
		mSharedPreferences=AppConfig.getSharedPreferences(mContext);
		
		initTilte();
		initSettings();
	}
	
	/**
	 * 初始化标题栏
	 */
	private void initTilte() {
		mHeadLeft=(Button) findViewById(R.id.head_left);
		mHeadRight=(Button) findViewById(R.id.head_right);
		mCatNameTextView=(TextView) findViewById(R.id.head_middle);
		mCatName=getResources().getString(R.string.setting_string);
		mCatNameTextView.setText(mCatName);
		//初始化监听
		initTilteListner();
		mHeadLeft.setOnClickListener(mHLClickListener);
		mHeadRight.setOnClickListener(mHRClickListener);
	}
	/**
	 * 标题栏事件监听初始化
	 */
	protected void initTilteListner(){
		mHLClickListener=new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		};
		mHRClickListener=new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UIHelper.showMainPopMenu(mContext, R.layout.pop_users, mHeadRight);
			}
		};
	}
	/**
	 * 设置项目的初始化
	 */
	private void initSettings(){
		//用户登录部分
		mAccount=findPreference("account");
		boolean is_login=mSharedPreferences.getBoolean(AppConfig.IS_LOGIN, false);
		if (is_login) {
			mAccount.setTitle("用户登出");
		}else{
			mAccount.setTitle("用户登录");
		}
		mAccount.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				// TODO Auto-generated method stub
				UIHelper.showLogin(mContext);
				return false;
			}
		});
		
	}
}
