package com.jxtzw.app.view;

import java.util.ArrayList;

import com.jxtzw.app.R;
import com.jxtzw.app.adapter.ListViewArticleAdapter;
import com.jxtzw.app.api.ApiArticle;
import com.jxtzw.app.bean.Article;
import com.jxtzw.app.widget.PullToRefreshListView;
import com.jxtzw.app.widget.PullToRefreshListView.OnRefreshListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class NewsListView extends BaseView {
	/**
	 * 布局
	 */
	protected View mListView;
	/**
	 * 分类ID
	 */
	protected String mCatID;
	protected int mIndex;
	/**
	 * 下拉刷新列表
	 */
	protected PullToRefreshListView mArticleListPTRLV;
	protected ListViewArticleAdapter mArticleListAdapter;
	/**
	 * 下拉刷新列表的底部
	 */
	protected View mArticleListFooter;
	protected ProgressBar mALFooterProgressBar;
	protected TextView mALFooterMore;
	/**
	 * 下拉刷新列表的事件
	 */
	protected OnItemClickListener mPTRItemClickListener;
	protected OnScrollListener mPTRScrollListener;
	protected OnRefreshListener mPTRRefreshListener;
	
	
	/**
	 * 数据
	 */
	protected ApiArticle mApiArticle;						//数据接口
	protected ArrayList<Article> mArticlesLocal;		//本地数据
	protected ArrayList<Article> mArticleNew;			//更新数据
	protected ArrayList<Article> mArticlesShow;		//显示数据

	public NewsListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 初始化
	 */
	public void init(View view, String catID){
		this.mListView=view;
		this.mCatID=catID;
		initMemberVar();
		initArticleListView();
		initData();
	}
	
	/**
	 * 初始化数据成员变量
	 */
	protected void initMemberVar(){
		mArticlesLocal=new ArrayList<Article>();
		mArticleNew=new ArrayList<Article>();
		mArticlesShow=new ArrayList<Article>();
	}
	
	/**
	 * 初始化下拉刷新列表
	 */
	@SuppressLint("InflateParams")
	protected void initArticleListView() {
		//初始化文章列表数据适配器
		mArticleListAdapter=new ListViewArticleAdapter(mContext, mArticlesShow, mResources);
		//初始化底部
		mArticleListFooter=mLayoutInflater.inflate(R.layout.listview_footer, null);
		mALFooterMore=(TextView) mArticleListFooter.findViewById(R.id.listview_foot_more);
		mALFooterProgressBar=(ProgressBar) mArticleListFooter.findViewById(R.id.listview_foot_progress);
		//初始化下拉刷新列表对象
		mArticleListPTRLV=(PullToRefreshListView) mListView.findViewById(R.id.ptr_newslist);
		//给下拉列表添加底部，要在setAdapter之前添加
		mArticleListPTRLV.addFooterView(mArticleListFooter);
		//关联数据列表对象和数据
		mArticleListPTRLV.setAdapter(mArticleListAdapter);
		//事件监听
		initListener();
		mArticleListPTRLV.setOnItemClickListener(mPTRItemClickListener);
		mArticleListPTRLV.setOnScrollListener(mPTRScrollListener);
		mArticleListPTRLV.setOnRefreshListener(mPTRRefreshListener);
	}
	
	
	/**
	 * 初始化事件监听
	 */
	protected void initListener(){
		//条目单击事件
		mPTRItemClickListener=new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}
		};
		
		//滚动条滚动
		mPTRScrollListener=new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				
			}
		};
		
		//刷新
		mPTRRefreshListener=new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				
			}
		};
		
		
	}
	
	
	/**
	 * 初始化数据
	 */
	public void initData(){
		mApiArticle=new ApiArticle(mContext);
		String apiurl=mResources.getString(R.string.api_articles);
		String query="vcode=jxtzw&cid=25";
		mApiArticle.init(apiurl, query);
		mArticlesShow=mApiArticle.getArticles(true);
	}
	
}
