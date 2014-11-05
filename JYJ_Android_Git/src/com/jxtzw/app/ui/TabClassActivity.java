package com.jxtzw.app.ui;

import com.jxtzw.app.AppConfig;
import com.jxtzw.app.R;
import com.jxtzw.app.common.DataHelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class TabClassActivity extends TabHomeActivity {
	/**
	 * 弹出窗口
	 */
	protected View mPopWindowBox;
	protected PopupWindow mPWTab;
	
	/**
	 * 弹出窗口中的按钮
	 */
	protected TextView mGold;
	protected TextView mSilver;
	protected TextView mOil;
	/**
	 * 下层UI
	 */
	protected View mMainContainer;
	
	/**
	 * 数据
	 */
	protected String[][] mAppConfigDefault={
			{ AppConfig.JYJ_CAT_GOLD_NAME_DEFAULT, AppConfig.JYJ_CAT_GOLD_ID_DEFAULT,
			  AppConfig.JYJ_CAT_GOLD_SHOW_QUOT_DEFAULT,AppConfig.JYJ_CAT_GOLD_SHOW_IMAGE_DEFAULT},
			{ AppConfig.JYJ_CAT_SILVER_NAME_DEFAULT, AppConfig.JYJ_CAT_SILVER_ID_DEFAULT,
			  AppConfig.JYJ_CAT_SILVER_SHOW_QUOT_DEFAULT,AppConfig.JYJ_CAT_SILVER_SHOW_IMAGE_DEFAULT},
		    { AppConfig.JYJ_CAT_OIL_NAME_DEFAULT, AppConfig.JYJ_CAT_OIL_ID_DEFAULT,
			  AppConfig.JYJ_CAT_OIL_SHOW_QUOT_DEFAULT,AppConfig.JYJ_CAT_OIL_SHOW_IMAGE_DEFAULT}
	};
	protected String[][] mAppConfigUserSet={
			{ AppConfig.JYJ_CAT_GOLD_NAME_USERSET, AppConfig.JYJ_CAT_GOLD_ID_USERSET,
			  AppConfig.JYJ_CAT_GOLD_SHOW_QUOT_USERSET,AppConfig.JYJ_CAT_GOLD_SHOW_IMAGE_USERSET},
			{ AppConfig.JYJ_CAT_SILVER_NAME_USERSET, AppConfig.JYJ_CAT_SILVER_ID_USERSET,
		      AppConfig.JYJ_CAT_SILVER_SHOW_QUOT_USERSET,AppConfig.JYJ_CAT_SILVER_SHOW_IMAGE_USERSET},
		    { AppConfig.JYJ_CAT_OIL_NAME_USERSET, AppConfig.JYJ_CAT_OIL_ID_USERSET,
			  AppConfig.JYJ_CAT_OIL_SHOW_QUOT_USERSET,AppConfig.JYJ_CAT_OIL_SHOW_IMAGE_USERSET}
	};
	protected int[][] mResourseID={
			{R.array.cat_gold_name,R.array.cat_gold_id,R.array.cat_gold_show_quotaion,R.array.cat_gold_show_image},
			{R.array.cat_silver_name,R.array.cat_silver_id,R.array.cat_silver_show_quotaion,R.array.cat_silver_show_image},
			{R.array.cat_oil_name,R.array.cat_oil_id,R.array.cat_oil_show_quotaion,R.array.cat_oil_show_image}
	};
	protected int mCurIndex=0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreateBK(savedInstanceState);
		setContentView(R.layout.activity_tab_class);
		initPopWindow();
		init(0);
	}
	

	/**
	 * 初始化
	 */
	protected void init(int index) {
		//获取传递进来的参数
		initMemberVar(index);
		mCurView=0;
		initTitle(mCurView);
		initSubCat();
		initViewPage();
		mCurIndex=index;
	}
	
	/**
	 * 
	 * @param index
	 */
	protected void initMemberVar(int index) {
		initCommonMemberVar();
		initSelfMemberVar(index);
		initExMemberVar();
	}
	
	/**
	 * 
	 * @param index
	 */
	protected void initSelfMemberVar(int index) {
		//子导航标题
		mCatNames=DataHelper.getSubCat(mContext, mAppConfigUserSet[index][0],
				mAppConfigDefault[index][0], mResourseID[index][0]);
		//子导航ID
		mCatIDs=DataHelper.getSubCat(mContext, mAppConfigUserSet[index][1], 
				mAppConfigDefault[index][1], mResourseID[index][1]);
		//是否显示行情
		mCatShowQuotation=DataHelper.getSubCat(mContext, mAppConfigUserSet[index][2], 
				mAppConfigDefault[index][2], mResourseID[index][2]);
		//是否显示文章图片
		mCatShowImages=DataHelper.getSubCat(mContext, mAppConfigUserSet[index][3], 
				mAppConfigDefault[index][3], mResourseID[index][3]);
		
		mVPCount=mCatIDs.length;
	}
	
	
	/**
	 * 初始化子分类导航
	 */
	@SuppressLint({ "InflateParams", "InlinedApi" })
	protected void initSubCat() {
		mSubCats.clear();
		mLLSubCat.removeAllViews();
		
		DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        mScreenWidth=metric.widthPixels;
        mLayoutParams=new LinearLayout.LayoutParams(
        		android.view.ViewGroup.LayoutParams.WRAP_CONTENT,30);
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
	 * 单击事件
	 */
	protected void initClick() {
		//黄金
		mGold.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mCurIndex!=0){
					init(0);
				}
				mPopWindowBox.setVisibility(View.GONE);
			}
		});
		
		//白银
		mSilver.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mCurIndex!=1){
					init(1);
				}
				mPopWindowBox.setVisibility(View.GONE);
			}
		});
		
		//原油
		mOil.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mCurIndex!=2){
					init(2);
				}
				mPopWindowBox.setVisibility(View.GONE);
			}
		});
		
		
		//上层PopWindow,消化掉单击事件
		mPopWindowBox.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	/**
	 * PopWindow初始化
	 */
	private void initPopWindow() {
		
		DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int tabTVWidth=metric.widthPixels;
        View contanner=findViewById(R.id.popup_window);
        ViewGroup.LayoutParams layoutParams=contanner.getLayoutParams(); 
        layoutParams.width = tabTVWidth/6*3; 
        contanner.setLayoutParams(layoutParams); 
		
        mPopWindowBox=findViewById(R.id.popwindow_box);
        
        mGold=(TextView) findViewById(R.id.gold);
        mSilver=(TextView) findViewById(R.id.silver);
        mOil=(TextView) findViewById(R.id.oil);
        initClick();
	}
	
}
