package com.jxtzw.app.ui;

import java.util.ArrayList;

import com.jxtzw.app.AppConfig;
import com.jxtzw.app.R;
import com.jxtzw.app.adapter.NewsListViewPagerAdapter;
import com.jxtzw.app.common.DataHelper;
import com.jxtzw.app.common.UIHelper;
import com.jxtzw.app.view.NewsListView;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TabHomeActivity extends BaseActivity {
	/**
	 * 文章分类
	 */
	protected String[] mCatNames;
	protected String[] mCatIDs;
	protected int mVPCount;
	/**
	 * 子导航相关变量
	 */
	protected HorizontalScrollView mHSVSubCat;
	protected LinearLayout mLLSubCat;
	protected ArrayList<Button> mSubCats;
	protected int mScreenWidth;
	protected int mSubButtonWidth;
	protected int mSubNavShowCount=4;
	protected LinearLayout.LayoutParams mLayoutParams;
	
	/**
	 * ViewPage相关变量
	 */
	protected ViewPager mNewsListVP;
	protected NewsListViewPagerAdapter mNewsListVPAdapter;
	protected ArrayList<View> mNewsListViews;
	protected OnPageChangeListener mNewsListPCListener;
	
	/**
	 * 与切换相关的数据
	 */
	protected int mCurView=0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_home);
		initMemberVar();
		initSubCat();
		initViewPage();
	}
	
	
	/**
	 * 初始化成员变量
	 */
	protected void initMemberVar() {
		initCommonMemberVar();
		initSelfMemberVar();
	}
	
	
	/**
	 * 初始化通用成员变量
	 */
	protected void initCommonMemberVar() {
		//上下文
		mContext=this;
		//资源
		mResources=getResources();
		//布局
		mLayoutInflater=LayoutInflater.from(this);
	}
	
	/**
	 * 初始化本类专用成员变量
	 */
	protected void initSelfMemberVar() {
		//子导航标题
		mCatNames=DataHelper.getSubCat(mContext, AppConfig.JYJ_CAT_GOLD_NAME_USERSET,
				AppConfig.JYJ_CAT_GOLD_NAME_DEFAULT, R.array.cat_gold_name);
		//子导航ID
		mCatIDs=DataHelper.getSubCat(mContext, AppConfig.JYJ_CAT_GOLD_ID_USERSET, 
						AppConfig.JYJ_CAT_GOLD_ID_DEFAULT, R.array.cat_gold_id);
		mVPCount=mCatIDs.length;
		//ViewPager对应的View
		mNewsListViews=new ArrayList<View>();
		//包含子导航的横向滚动条
		mHSVSubCat=(HorizontalScrollView) findViewById(R.id.hsv_subnav);
		//滚动条内的布局
		mLLSubCat=(LinearLayout) findViewById(R.id.ll_subnav);
		//存储子导航Button的数组
		mSubCats=new ArrayList<Button>();
	}
	
	/**
	 * 初始化标题栏信息
	 */
	
	
	/**
	 * 初始化子分类导航
	 */
	@SuppressLint({ "InflateParams", "InlinedApi" })
	protected void initSubCat() {
		DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth=metric.widthPixels;
        mLayoutParams=new LinearLayout.LayoutParams(
        		LayoutParams.WRAP_CONTENT,30);
        mSubButtonWidth=(mScreenWidth-3)/mSubNavShowCount;
        mLayoutParams.width=mSubButtonWidth;
		
		for (int i = 0; i < mVPCount; i++) {
			Button button=(Button) mLayoutInflater.inflate(R.layout.button_subcat, null);
			button.setText(mCatNames[i]);
			button.setTag(i);
			button.setLayoutParams(mLayoutParams);
			mSubCats.add(button);
			mLLSubCat.addView(button);
		}
	}
	
	/**
	 * 分类导航当前激活项设置
	 */
	protected void setCurItem(int last, int current) {
		//更新二级导航
		int scrollX=0;
		if (current>=mSubNavShowCount-1) {
			scrollX=mSubButtonWidth*(current-1);
		}
		mHSVSubCat.scrollTo(scrollX, 0);
		mSubCats.get(last).setTextColor(mResources.getColor(R.color.gray));
		mSubCats.get(last).setBackgroundDrawable(mResources.getDrawable(R.drawable.subnav_bg_def));
		mSubCats.get(current).setTextColor(mResources.getColor(R.color.red));
		mSubCats.get(current).setBackgroundDrawable(mResources.getDrawable(R.drawable.subnav_bg_sel));
		//更新内容显示
		updateView(current);
	}
	
	
	/**
	 * 初始化分类下文章列表ViewPager
	 */
	protected void initViewPage() {
		//初始化ViewPager中包含的View
		for (int i = 0; i <mVPCount; i++) {
			View newsListView=initView(i);
			mNewsListViews.add(newsListView);
		}
		
		//绑定ViewPager和View
		mNewsListVP=(ViewPager) findViewById(R.id.newslist_viewpager);
		mNewsListVPAdapter=new NewsListViewPagerAdapter(mNewsListViews);
		mNewsListVP.setAdapter(mNewsListVPAdapter);
		
		//事件监听
		initEventListener();
		mNewsListVP.setOnPageChangeListener(mNewsListPCListener);
		//设置当前选中项
		setCurItem(0, 0);
	}
	
	/**
	 * 初始化事件监听
	 */
	protected void initEventListener() {
		//ViewPager切换事件
		mNewsListPCListener=new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				setCurItem(mCurView, arg0);
				mCurView=arg0;
				UIHelper.ToastMessage(mContext,mCatNames[arg0]);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		};
		
		//子导航button单击事件
		for (int i = 0; i < mVPCount; i++) {
			mSubCats.get(i).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int newCur=Integer.parseInt(  v.getTag().toString());
					setCurItem(mCurView, newCur);
					mNewsListVP.setCurrentItem(newCur);
					mCurView=newCur;
				}
			});
		}
	}
	
	/**
	 * 初始化ViewPager中的View
	 * @param index
	 * @return
	 */
	@SuppressLint("InflateParams")
	protected View initView(int index) {
		View listView=null;
		listView=mLayoutInflater.inflate(R.layout.viewpager_newslist, null);
		TextView tvTextView=(TextView) listView.findViewById(R.id.textView1);
		tvTextView.setText(mCatNames[index]);
		return listView;
	}
	
	/**
	 * 刷新ViewPager中的View
	 */
	protected void updateView(int index){
		View listView=mNewsListViews.get(index);
		String catID=mCatIDs[index];
		NewsListView newsListView=new NewsListView(mContext);
		newsListView.init(listView,catID);
	}
}
