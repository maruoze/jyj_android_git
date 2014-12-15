package com.jxtzw.app.ui;

import java.util.ArrayList;
import java.util.Hashtable;

import com.jxtzw.app.AppConfig;
import com.jxtzw.app.R;
import com.jxtzw.app.adapter.NewsListViewPagerAdapter;
import com.jxtzw.app.common.DataHelper;
import com.jxtzw.app.common.UIHelper;
import com.jxtzw.app.handler.QuotationUpdateHandler;
import com.jxtzw.app.view.MainMenuPop;
import com.jxtzw.app.view.NewsListView;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class TabHomeActivity extends BaseActivity {
	/**
	 * 文章分类
	 */
	protected String mMainTitle;
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
	protected ArrayList<View> mNewsListViewPages;
	protected OnPageChangeListener mNewsListPCListener;
	
	/**
	 * 与切换相关的数据
	 */
	protected int mCurView=0;
	/**
	 * 切换页列表对象
	 */
	protected ArrayList<NewsListView> mNewsListViews;
	protected ArrayList<QuotationUpdateHandler> mQuotationUpdateHandlers;
	/**
	 * 显示控制
	 */
	protected String[] mCatShowQuotation;
	protected String[] mCatShowImages;
	/**
	 * 标题栏相关变量
	 */
	protected TextView mTitleTextView;
	protected Button mTitleMenu;
	/**
	 * 弹出窗口
	 */
	protected MainMenuPop mMainMenuPop;
	protected PopupWindow mPopWindow;
	
	
	@Override
 	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_home);
		//获取传递进来的参数
		initMemberVar();
		initTitle(mCurView);
		initSubCat();
		initViewPage();
	}
	
	/**
	 * 备份父类的OnCreate供子类使用
	 * @param savedInstanceState
	 */
	protected void onCreateBK(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
	}
	
	
	/**
	 * 初始化成员变量
	 */
	protected void initMemberVar() {
		initCommonMemberVar();
		initSelfMemberVar();
		initExMemberVar();
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
		//是否显示行情
		mCatShowQuotation=DataHelper.getSubCat(mContext, AppConfig.JYJ_CAT_GOLD_SHOW_QUOT_USERSET, 
				AppConfig.JYJ_CAT_GOLD_SHOW_QUOT_DEFAULT, R.array.cat_gold_show_quotaion);
		//是否显示文章图片
		mCatShowImages=DataHelper.getSubCat(mContext, AppConfig.JYJ_CAT_GOLD_SHOW_IMAGE_USERSET, 
				AppConfig.JYJ_CAT_GOLD_SHOW_IMAGE_DEFAULT, R.array.cat_gold_show_image);
		
		mVPCount=mCatIDs.length;
	}
	
	/**
	 * 初始化
	 */
	protected void initExMemberVar() {
		//ViewPager对应的View
		mNewsListViewPages=new ArrayList<View>();
		//包含子导航的横向滚动条
		mHSVSubCat=(HorizontalScrollView) findViewById(R.id.hsv_subnav);
		//滚动条内的布局
		mLLSubCat=(LinearLayout) findViewById(R.id.ll_subnav);
		//存储子导航Button的数组
		mSubCats=new ArrayList<Button>();
		//切换页列表对象
		mNewsListViews=new ArrayList<NewsListView>();
		//初始化行情时间更新计时器handler
		mQuotationUpdateHandlers=new ArrayList<QuotationUpdateHandler>();
		for (int i = 0; i < mCatShowQuotation.length; i++) {
			if(!mCatShowQuotation[i].equals("-1")){
				mQuotationUpdateHandlers.add(null);
			}
		}
		Intent intent=getIntent();
		mMainTitle=intent.getStringExtra("MainTitle");
	}
	
	/**
	 * 初始化标题栏信息
	 */
	protected void initTitle(int index) {
		mTitleTextView=(TextView) findViewById(R.id.head_middle);
		String titleString=mMainTitle+" • "+mCatNames[index];
		mTitleTextView.setText(titleString);
		initTitleMenu();
	}
	
	/**
	 * 初始化子分类导航
	 */
	@SuppressLint({ "InflateParams", "InlinedApi" })
	protected void initSubCat() {
		DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth=metric.widthPixels;
        mLayoutParams=new LinearLayout.LayoutParams(
        		android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
        		android.view.ViewGroup.LayoutParams.MATCH_PARENT);
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
		//清除上一个计时器
		if (mQuotationUpdateHandlers.size()>last&&mQuotationUpdateHandlers.get(last)!=null) {
			mQuotationUpdateHandlers.get(last).clearAll();
		}
		//更新内容显示
		updateView(current);
		//更新标题
		initTitle(current);
	}
	
	
	/**
	 * 初始化分类下文章列表ViewPager
	 */
	protected void initViewPage() {
		//初始化ViewPager中包含的View
		for (int i = 0; i <mVPCount; i++) {
			View newsListView=initView(i);
			mNewsListViewPages.add(newsListView);
		}
		
		//绑定ViewPager和View
		mNewsListVP=(ViewPager) findViewById(R.id.newslist_viewpager);
		mNewsListVPAdapter=new NewsListViewPagerAdapter(mNewsListViewPages);
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
		Hashtable<String, String> config=new Hashtable<String, String>();
		config.put("mCatID", mCatIDs[index]);
		config.put("mCatName", mCatNames[index]);
		config.put("mCatShowQuo", mCatShowQuotation[index]);
		config.put("mCatShowImages", mCatShowImages[index]);
		NewsListView newsListView=new NewsListView(mContext);
		newsListView.init(listView,config,index);
		mNewsListViews.add(newsListView);
		return listView;
	}
	
	/**
	 * 刷新ViewPager中的View
	 */
	protected void updateView(int index){
		NewsListView newsListView=mNewsListViews.get(index);
		newsListView.update(mQuotationUpdateHandlers);
	}
	
	/**
	 * 初始化标题栏右部的按钮
	 */
	protected void initTitleMenu() {
		mTitleMenu=(Button) findViewById(R.id.head_right);
		mTitleMenu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//UIHelper.ToastMessage(mContext, " 标题菜单单击");
				mMainMenuPop=new MainMenuPop(mContext, mResources, mLayoutInflater);
				mPopWindow=mMainMenuPop.initPop(R.layout.pop_users);
				mPopWindow.showAtLocation(mTitleMenu, Gravity.RIGHT|Gravity.TOP, 0, 80);
			}
		});
	}
}
