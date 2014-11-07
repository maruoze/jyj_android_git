package com.jxtzw.app.ui;

import com.jxtzw.app.AppContext;
import com.jxtzw.app.R;
import com.jxtzw.app.R.layout;
import com.jxtzw.app.bean.Article;
import com.jxtzw.app.view.WebMemberView;

import android.app.Activity;
import android.content.Intent;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_member_privilege);
		init();
	}
	
	/**
	 * 初始化
	 */
	protected void init() {
		initCommonMemberVar();
		initMemberVar();
		initSelfMemVar();
		initTitle();
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
	
	
}
