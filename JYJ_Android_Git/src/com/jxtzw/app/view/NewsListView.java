package com.jxtzw.app.view;

import java.util.ArrayList;

import com.jxtzw.app.bean.Article;
import com.jxtzw.app.widget.PullToRefreshListView;

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
	/**
	 * 下拉刷新列表
	 */
	protected PullToRefreshListView mNewsListPTRLV;
	/**
	 * 数据
	 */
	protected ArrayList<Article> mArticlesLocal;
	protected ArrayList<Article> mArticlesShow;

	public NewsListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 初始化
	 */
	public void init(View view, String catID){
		
	}
	
	/**
	 * 初始化数据
	 */
	
	
}
