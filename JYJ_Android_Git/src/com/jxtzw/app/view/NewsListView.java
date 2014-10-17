package com.jxtzw.app.view;

import android.content.Context;
import android.view.View;

public class NewsListView extends BaseView {
	/**
	 * 布局
	 */
	protected View mListView;
	/**
	 * 分类ID
	 */
	protected String mCatID;
	protected int mIndex;						//切换页索引
	/**
	 * 下拉刷新列表
	 */
	protected PullToRefreshView mPullToRefreshView;
		
	
	public NewsListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 初始化
	 */
	public void init(View view, String catID,int index){
		this.mListView=view;
		this.mCatID=catID;
		this.mIndex=index;
		initPTRView();
	}
		

	
	/**
	 * 行情显示部分
	 */
	
	
	
	
	/**
	 * 下拉刷新列表显示部分
	 */
	protected void initPTRView() {
		mPullToRefreshView=new PullToRefreshView(mContext,mListView,mCatID,mIndex);
	}
	
	
	/**
	 * 刷新数据
	 */
	public void update() {
		mPullToRefreshView.refreshPTR();
	}
	
}
