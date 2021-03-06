package com.jxtzw.app.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalBitmap;

import com.jxtzw.app.AppConfig;
import com.jxtzw.app.R;
import com.jxtzw.app.common.UIHelper;
import com.jxtzw.app.handler.QuotationUpdateHandler;
import com.jxtzw.app.view.MainMenuPop;
import com.jxtzw.app.view.NewsListView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class TabMemberActivity extends BaseActivity {
	/**
	 * 
	 */
	protected String mMainTitle;
	/**
	 * UI
	 */
	protected ImageView mImageView;
	protected TextView mTitleTextView;
	protected ListView mListView;
	protected Button mTitleMenu;
	/**
	 * 数据
	 */
	protected String[] mItemTitle;
	protected String[] mItemDescription;
	protected int[] mItemImagesID;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.gc();
		setContentView(R.layout.activity_tab_member);
		init();
		initImageView();
		initTitle();
		initListView();
	}
	
	/**
	 * 初始化
	 */
	protected void init() {
		initCommonMemberVar();
		initMemberVar();
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
	 * 初始化成员变量
	 */
	protected void initMemberVar() {
		Intent intent=getIntent();
		mMainTitle=intent.getStringExtra("MainTitle");
	}
	
	/**
	 * 初始化Image
	 */
	protected void initImageView() {
		/*
		mImageView=(ImageView) findViewById(R.id.head_image);
		Drawable bm=mResources.getDrawable(R.drawable.ban);
		mImageView.setImageDrawable(bm);
		//mImageView.setBackgroundResource(R.drawable.ban);
		 */
	}
	
	
	/**
	 * 初始化标题栏信息
	 */
	protected void initTitle() {
		mTitleTextView=(TextView) findViewById(R.id.head_middle);
		String titleString=mMainTitle+" • "+"会员专享六大特权";
		mTitleTextView.setText(titleString);
		initTitleMenu();
	}
	
	/**
	 * 初始化会员特权列表
	 */
	@SuppressLint("Recycle")
	protected void initListView() {
		//初始化Adapter需要提供的数据
		mItemTitle=mResources.getStringArray(R.array.member_title);
		mItemDescription=mResources.getStringArray(R.array.member_description);
		TypedArray typedArray=mResources.obtainTypedArray(R.array.member_images);
		mItemImagesID=new int[mItemTitle.length];
		if (null!=mItemTitle) {
			for (int i = 0; i < mItemTitle.length; i++) {
				mItemImagesID[i]=typedArray.getResourceId(i, 0);
			}
		}
		//数据放入ArrayList
		ArrayList<HashMap<String, Object>> listItems=new ArrayList<HashMap<String,Object>>();
		for (int i = 0; i < mItemTitle.length; i++) {
			HashMap<String, Object> itemMap=new HashMap<String, Object>();
			itemMap.put("title", mItemTitle[i]);
			itemMap.put("description", mItemDescription[i]);
			itemMap.put("images", mItemImagesID[i]);
			listItems.add(itemMap);
		}
		//初始化Adapter和ListView
		String[] dataIndex={"title","description","images"};
		int[] viewID={R.id.member_title,R.id.member_description,R.id.member_image};
		SimpleAdapter listViewAdapter=new SimpleAdapter(mContext, listItems, R.layout.item_member, dataIndex, viewID);
		mListView=(ListView) findViewById(R.id.menber_right);
		mListView.setAdapter(listViewAdapter);
		initListener();
	}
	
	/**
	 * 初始化Listener
	 */
	protected void initListener() {
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// UIHelper.ToastMessage(mContext, String.valueOf(position));
				if (AppConfig.isLogin) {
					if (AppConfig.isMember) {
						Bundle bundle=new Bundle();
						bundle.putInt("MemberIndex", position);
						bundle.putString("MainTitle", mMainTitle);
						
						Intent memberIntent=new Intent();
						memberIntent.putExtras(bundle);
						if(position==4){
							memberIntent.setClass(mContext, MemberRegisterActivity.class);
						}else{
							memberIntent.setClass(mContext, MemberPrivilegeActivity.class);
						}
						mContext.startActivity(memberIntent);
					}else{
						UIHelper.ToastMessage(mContext, "您还不是金牌会员，请联系客服升级金牌会员！");
					}
				}else{
					UIHelper.showLogin(mContext, null);
				}
			}
		});
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
				UIHelper.showMainPopMenu(mContext, R.layout.pop_users, mTitleMenu);
			}
		});
	}
}
