package com.jxtzw.app.ui;

import com.jxtzw.app.R;
import android.annotation.SuppressLint;
import android.app.ActivityGroup;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
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
	protected Resources mResources;
	protected LayoutInflater mLayoutInflater;
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
	private String mPreTabString="";
	//弹出菜单
	private PopupWindow mPWTab;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		initTabHost();
	}
	
	/**
	 * 初始化上下文几及资源
	 */
	protected void init(){
		mContext=this;
		mResources=getResources();
		mLayoutInflater=LayoutInflater.from(this);
	}
	
	/**
	 * 初始化TabHost
	 */
	protected void initTabHost() {
		mTabTitleStrings=mResources.getStringArray(R.array.main_tabs_text);
		mTabCount=mTabClasses.length;
		mTabHost=getTabHost();
		for (int i = 0; i < mTabCount; i++) {
			String tag=mTabTagsStrings[i];
			Intent intent=new Intent(this, mTabClasses[i]);
			TabHost.TabSpec tabSpec=initTabSpec(tag,mTabTitleStrings[i],mTabImages[i],intent);
			mTabHost.addTab(tabSpec);
		}
		initOnTabChange();
		mTabHost.setOnTabChangedListener(mOnTabChangeListener);
	}
	
	/**
     * 自定义创建标签项的方法
	 */
	protected TabHost.TabSpec initTabSpec(String tag,String title,int image_id,Intent intent) {
		TabHost.TabSpec tabSpec = null;
		tabSpec=mTabHost.newTabSpec(tag);
		TextView textView=initTabSpecTextView(tag,title,image_id);
		tabSpec.setIndicator(textView);
		tabSpec.setContent(intent);
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
	protected void initOnTabChange() {
		View popView=mLayoutInflater.inflate(R.layout.pop_tab, null);
		mPWTab=new PopupWindow(popView,200,30);
		mOnTabChangeListener=new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				if(tabId.equals("Classify")){
					//mTabHost.setCurrentTabByTag(mPreTabString);
					mPreTabString=tabId;
					View v=mTabHost.getTabWidget().findViewWithTag(tabId);
					mPWTab.showAtLocation(v, Gravity.CENTER, 0, 0);
				}else{
					mPreTabString=tabId;
					mPWTab.dismiss();
				}
			}
		};
	}
	
}
