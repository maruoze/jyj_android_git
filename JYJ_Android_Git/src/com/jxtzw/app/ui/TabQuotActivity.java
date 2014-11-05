package com.jxtzw.app.ui;

import java.util.ArrayList;
import java.util.Hashtable;

import com.jxtzw.app.AppConfig;
import com.jxtzw.app.R;
import com.jxtzw.app.common.DataHelper;
import com.jxtzw.app.view.NewsListView;
import com.jxtzw.app.view.WebQuoView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class TabQuotActivity extends TabHomeActivity {
	protected ArrayList<WebQuoView> mWebQuoViews;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreateBK(savedInstanceState);
		setContentView(R.layout.activity_tab_quot);
		init();
	}
	
	/**
	 * 初始化
	 */
	protected void init() {
		//获取传递进来的参数
		initMemberVar();
		initTitle(mCurView);
		initSubCat();
		initViewPage();
	}

	@Override
	protected void initSelfMemberVar() {
		// TODO Auto-generated method stub
		//子导航标题
		mCatNames=DataHelper.getSubCat(mContext, AppConfig.JYJ_CAT_QUO_NAME_USERSET,
				AppConfig.JYJ_CAT_QUO_NAME_DEFAULT, R.array.cat_quotation_name);
		//子导航ID
		mCatIDs=DataHelper.getSubCat(mContext, AppConfig.JYJ_CAT_QUO_ID_USERSET, 
						AppConfig.JYJ_CAT_QUO_ID_DEFAULT, R.array.cat_quotation_url);
		
		mVPCount=mCatIDs.length;
	}

	@Override
	protected void initExMemberVar() {
		// TODO Auto-generated method stub
		//ViewPager对应的View
		mNewsListViewPages=new ArrayList<View>();
		//包含子导航的横向滚动条
		mHSVSubCat=(HorizontalScrollView) findViewById(R.id.hsv_subnav);
		//滚动条内的布局
		mLLSubCat=(LinearLayout) findViewById(R.id.ll_subnav);
		//存储子导航Button的数组
		mSubCats=new ArrayList<Button>();
		//切换页列表对象
		mWebQuoViews=new ArrayList<WebQuoView>();
		Intent intent=getIntent();
		mMainTitle=intent.getStringExtra("MainTitle");
	}
	
	
	@Override
	@SuppressLint("InflateParams")
	protected View initView(int index) {
		// TODO Auto-generated method stub
		View listView=null;
		listView=mLayoutInflater.inflate(R.layout.viewpager_webquo, null);
		Hashtable<String, String> config=new Hashtable<String, String>();
		config.put("mCatID", mCatIDs[index]);
		config.put("mCatName", mCatNames[index]);
		WebQuoView webQuoView=new WebQuoView(mContext);
		webQuoView.init(listView,config,index);
		mWebQuoViews.add(webQuoView);
		return listView;
	}

	@Override
	protected void setCurItem(int last, int current) {
		// TODO Auto-generated method stub
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
		//更新标题
		initTitle(current);
	}

	@Override
	protected void updateView(int index) {
		// TODO Auto-generated method stub
		WebQuoView webQuoView=mWebQuoViews.get(index);
	}
	
	
}
