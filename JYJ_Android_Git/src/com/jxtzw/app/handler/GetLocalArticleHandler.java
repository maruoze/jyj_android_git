package com.jxtzw.app.handler;

import com.jxtzw.app.adapter.ListViewArticleAdapter;
import com.jxtzw.app.view.PullToRefreshView;
import com.jxtzw.app.widget.PullToRefreshListView;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GetLocalArticleHandler extends Handler {
	/**
	 * 上下文
	 */
	protected Context mContext;
	protected PullToRefreshView mPTRView;
	/**
	 * 要更新的数据适配器
	 */
	protected PullToRefreshListView mArticleListPTRLV;		//下拉刷新列表对象
	protected ListViewArticleAdapter mArticleListAdapter;	//下拉刷新列表对象数据适配器
	private ProgressBar mALFooterProgressBar;
	private TextView mALFooterMore;
	
	public GetLocalArticleHandler(Context mContext, PullToRefreshView mPTRView,
			ListViewArticleAdapter mArticleListAdapter,PullToRefreshListView articleListPTRLV,
			ProgressBar mALFooterProgressBar, TextView mALFooterMore) {
		super();
		this.mContext = mContext;
		this.mPTRView = mPTRView;
		this.mArticleListAdapter = mArticleListAdapter;
		this.mArticleListPTRLV=articleListPTRLV;
		this.mALFooterMore=mALFooterMore;
		this.mALFooterProgressBar=mALFooterProgressBar;
	}

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
			case -1:
				mPTRView.copyLocalToShow();
				mArticleListAdapter.notifyDataSetChanged();
				mALFooterProgressBar.setVisibility(View.GONE);
				mALFooterMore.setText("文章加载完成");
				break;
			case 0:
				mPTRView.copyLocalToShow();
				mArticleListAdapter.notifyDataSetChanged();
				//获取网络数据
				mPTRView.getArticlesOnline();
				break;
			case 1:
				//获取网络数据
				mPTRView.getArticlesOnline();
				break;
		}
		//解锁滚动列表
		mArticleListPTRLV.setEnabled(true);
	}
	
}
