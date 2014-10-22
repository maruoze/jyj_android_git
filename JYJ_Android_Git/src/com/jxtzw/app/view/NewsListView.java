package com.jxtzw.app.view;

import java.util.Hashtable;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

public class NewsListView extends BaseView {
	/**
	 * 布局
	 */
	protected View mListView;
	/**
	 * 分类ID, 数据
	 */
	protected Hashtable<String, String> mHashtable;
	protected String mCatID;
	protected int mIndex;							//切换页索引
	protected String mShowQuotation; 	//行情显示
	/**
	 * 行情数据
	 */
	protected QuotationListView mQuotationListView;
	
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
	public void init(View view, Hashtable<String, String> hashtable,int index){
		this.mListView=view;
		this.mHashtable=hashtable;
		this.mCatID=mHashtable.get("mCatID");
		this.mShowQuotation=mHashtable.get("mCatShowQuo");
		this.mIndex=index;
		initQuotaionView();
		initPTRView();
	}
		

	
	/**
	 * 行情显示部分
	 */
	public void initQuotaionView() {
		if (!mShowQuotation.equals("-1")) {
			mQuotationListView=new QuotationListView(mContext, mListView, mHashtable, mIndex);
		}
	}
	
	
	
	/**
	 * 下拉刷新列表显示部分
	 */
	protected void initPTRView() {
		mPullToRefreshView=new PullToRefreshView(mContext,mListView,mHashtable,mIndex);
	}
	
	
	/**
	 * 刷新数据
	 */
	public void update() {
		mPullToRefreshView.refreshPTR();
	}
	
}
