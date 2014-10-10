package com.jxtzw.app.ui;

import java.util.ArrayList;

import com.jxtzw.app.AppConfig;
import com.jxtzw.app.R;
import com.jxtzw.app.adapter.NewsListViewPagerAdapter;
import com.jxtzw.app.common.DataHelper;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class TabHomeActivity extends BaseActivity {
	/**
	 * 文章分类
	 */
	protected String[] mCatName;
	protected String[] mCatID;
	protected int mVPCount;
	/**
	 * ViewPage相关变量
	 */
	protected ViewPager mNewsListVP;
	protected NewsListViewPagerAdapter mNewsListVPAdapter;
	protected ArrayList<View> mNewsListViews;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_home);
		init();
		initViewPage();
	}
	
	/**
	 * 初始化相关成员变量
	 */
	protected void init() {
		mContext=this;
		mResources=getResources();
		mLayoutInflater=LayoutInflater.from(this);
		mCatName=DataHelper.getSubCat(mContext, AppConfig.JYJ_CAT_GOLD_NAME_USERSET,
																AppConfig.JYJ_CAT_GOLD_NAME_DEFAULT, R.array.cat_gold_name);
		mCatID=DataHelper.getSubCat(mContext, AppConfig.JYJ_CAT_GOLD_ID_USERSET, 
																AppConfig.JYJ_CAT_GOLD_ID_DEFAULT, R.array.cat_gold_id);
		mVPCount=mCatID.length;
		mNewsListViews=new ArrayList<View>();
	}
	
	/**
	 * 初始化标题栏信息
	 */
	
	
	/**
	 * 初始化子分类导航
	 */
	
	
	/**
	 * 初始化分类下文章列表ViewPager
	 */
	@SuppressLint("InflateParams")
	protected void initViewPage() {
		//初始化ViewPager中包含的View
		for (int i = 0; i <mVPCount; i++) {
			View newsListView=mLayoutInflater.inflate(R.layout.viewpager_newslist, null);
			TextView textView=(TextView) newsListView.findViewById(R.id.textView1);
			textView.setText(mCatName[i]);
			mNewsListViews.add(newsListView);
		}
		
		//绑定ViewPager和View
		mNewsListVP=(ViewPager) findViewById(R.id.newslist_viewpager);
		mNewsListVPAdapter=new NewsListViewPagerAdapter(mNewsListViews);
		mNewsListVP.setAdapter(mNewsListVPAdapter);
		
	}
	
	
}
