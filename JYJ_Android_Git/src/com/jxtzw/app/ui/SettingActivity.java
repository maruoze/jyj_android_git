package com.jxtzw.app.ui;

import com.jxtzw.app.AppConfig;
import com.jxtzw.app.AppContext;
import com.jxtzw.app.AppManager;
import com.jxtzw.app.R;
import com.jxtzw.app.bean.Article;
import com.jxtzw.app.common.UIHelper;
import com.jxtzw.app.common.UpdateManage;
import com.jxtzw.app.view.LoginDialog;
import com.jxtzw.app.view.MenuCollectionPW;
import com.jxtzw.app.view.MenuPopWindow;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.PopupWindow;
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
	private Preference mRegister;
	private Preference mFeedback;
	private Preference mCheckUpdate;
	private Preference mAboutus;
	private Preference mCollection;
	
	/**
	 * 收藏显示
	 */
	private MenuCollectionPW mMenuCollectionPW;
	protected Article mArticle;
	protected AppContext mAppContext;
	protected Activity mActivity;
	protected Resources mResources;
	protected LayoutInflater mLayoutInflater;
	private static int POP_COLLECTION=1;
	
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
		mActivity=(Activity)mContext;
		mAppContext=(AppContext) mActivity.getApplication();
		//资源
		mResources=getResources();
		//布局
		mLayoutInflater=LayoutInflater.from(this);
		mArticle=null;
		
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
		//boolean is_login=mSharedPreferences.getBoolean(AppConfig.IS_LOGIN, false);
		boolean is_login=AppConfig.isLogin;
		if (is_login) {
			mAccount.setTitle("用户登出");
		}else{
			mAccount.setTitle("用户登录");
		}
		mAccount.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				// TODO Auto-generated method stub
				UIHelper.showLogin(mContext,mAccount);
				return false;
			}
		});
		
		//用户注册
		mRegister=findPreference("register");
		mRegister.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(mContext, MemberRegisterActivity.class);
				mContext.startActivity(intent);
				return false;
			}
		});
		
		//我的收藏
		mCollection=findPreference("collection");
		mCollection.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			
			@Override
			public boolean onPreferenceClick(Preference preference) {
				// TODO Auto-generated method stub
				if (AppConfig.isLogin) {
					mMenuCollectionPW=new MenuCollectionPW(mContext,mAppContext,mResources,
							mLayoutInflater,mArticle,mCatName);
					PopupWindow collection=mMenuCollectionPW.initPopWindow(R.layout.pop_collection, POP_COLLECTION,null);
					collection.showAtLocation(mCatNameTextView, Gravity.BOTTOM, 0, 0);
					mMenuCollectionPW.updatePopWindow(POP_COLLECTION);
				}else{
					UIHelper.showLogin(mContext, null);
				}
				return false;
			}
		});
		
		//意见反馈
		mFeedback=findPreference("feedback");
		mFeedback.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				// TODO Auto-generated method stub
				UIHelper.showFeedback(mContext);
				return false;
			}
		});
		
		//更新检测
		mCheckUpdate=findPreference("update");
		mCheckUpdate.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				// TODO Auto-generated method stub
				UpdateManage.getUpdateManage().checkAppUpdate(mContext,true,true);
				return false;
			}
		});
		
		//关于我们
		mAboutus=findPreference("aboutus");
		mAboutus.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			@Override
			public boolean onPreferenceClick(Preference preference) {
				// TODO Auto-generated method stub
				//UIHelper.ToastMessage(mContext, "关于我们");
				UIHelper.showAboutus(mContext);
				return false;
			}
		});
	}
}
