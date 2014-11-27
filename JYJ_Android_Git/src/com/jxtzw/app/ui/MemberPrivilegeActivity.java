package com.jxtzw.app.ui;

import com.jxtzw.app.AppConfig;
import com.jxtzw.app.AppContext;
import com.jxtzw.app.R;
import com.jxtzw.app.R.layout;
import com.jxtzw.app.bean.Article;
import com.jxtzw.app.common.CyptoUtils;
import com.jxtzw.app.common.EncryptionAES;
import com.jxtzw.app.common.UIHelper;
import com.jxtzw.app.view.WebMemberView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MemberPrivilegeActivity extends BaseActivity {
	protected AppContext mAppContext;
	protected Activity mActivity;
	/**
	 * 数据
	 */
	private String[] mSubTitles;
	private int mMemberIndex;
	private String mMainTitle;
	private String mCatName;
	private String[] mMemberAPIs;
	private String mMemberAPI;
	/**
	 * UI标题栏
	 */
	protected Button mHeadLeft;
	protected Button mHeadRight;
	protected TextView mCatNameTextView;
	protected OnClickListener mHLClickListener;
	protected OnClickListener mHRClickListener;
	/**
	 * WebView
	 */
	protected WebMemberView mWebMemberView;
	/**
	 * SharedPreferences
	 */
	private SharedPreferences mSharedPreferences;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_member_privilege);
		init();
	}
	
	/**
	 * 调用父类构造函数供子类使用
	 * @param savedInstanceState
	 */
	protected void onCreateEx(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	/**
	 * 初始化
	 */
	protected void init() {
		initCommonMemberVar();
		initMemberVar();
		initSelfMemVar();
		initTitle();
		initSession();
		initWebView();
	}
	
	/**
	 * 初始化通用成员变量
	 */
	protected void initCommonMemberVar() {
		//上下文
		mContext=this;
		mActivity=(Activity)mContext;
		mAppContext=(AppContext) mActivity.getApplication();
		//资源
		mResources=getResources();
		//布局
		mLayoutInflater=LayoutInflater.from(this);
	}
	
	/**
	 * 初始化成员变量
	 */
	protected void initMemberVar() {
		//获取Intent
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		mMemberIndex=bundle.getInt("MemberIndex");
		mMainTitle=bundle.getString("MainTitle");
	}
	
	/**
	 * 初始化本类成员变量
	 */
	protected void initSelfMemVar() {
		mSubTitles=mResources.getStringArray(R.array.member_title);
		mCatName=mSubTitles[mMemberIndex];
		mMemberAPIs=mResources.getStringArray(R.array.member_api);
		mMemberAPI=mMemberAPIs[mMemberIndex];
	}
	
	/**
	 * 标题栏初始化
	 */
	protected void initTitle() {
		mHeadLeft=(Button) findViewById(R.id.head_left);
		mHeadRight=(Button) findViewById(R.id.head_right);
		mCatNameTextView=(TextView) findViewById(R.id.head_middle);
		String titleString=mMainTitle+" • "+mCatName;
		mCatNameTextView.setText(titleString);
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
	 * WebView初始化
	 */
	protected void initWebView() {
		mWebMemberView=new WebMemberView(mContext);
		View view=((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);
		mWebMemberView.init(view, mMemberAPI, mMemberIndex);
		mWebMemberView.update();
	}
	
	
	/**
	 * 登录并设置session
	 */
	private void initSession(){
		mSharedPreferences=AppConfig.getSharedPreferences(mContext);
		String username=mSharedPreferences.getString("username", "");
		String encryPassword=mSharedPreferences.getString("password", "");
		if(username.length()==0||encryPassword.length()==0){
			UIHelper.ToastMessage(mContext, "登录信息有误，请重新登录！");
		}else{
			String password=CyptoUtils.decode("maru09743233aoze", encryPassword);
			if (password==null) {
				UIHelper.ToastMessage(mContext, "登录信息有误，请重新登录！[密码]");
			}else{
				
			}
		}
	}
	
}
