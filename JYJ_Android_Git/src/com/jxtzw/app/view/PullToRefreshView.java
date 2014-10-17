package com.jxtzw.app.view;

import java.util.ArrayList;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.jxtzw.app.R;
import com.jxtzw.app.adapter.ListViewArticleAdapter;
import com.jxtzw.app.api.ApiArticle;
import com.jxtzw.app.bean.Article;
import com.jxtzw.app.widget.PullToRefreshListView;
import com.jxtzw.app.widget.PullToRefreshListView.OnRefreshListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class PullToRefreshView extends BaseView {
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
	protected PullToRefreshListView mArticleListPTRLV;		//下拉刷新列表对象
	protected ListViewArticleAdapter mArticleListAdapter;	//下拉刷新列表对象数据适配器
	/**
	 * 下拉刷新列表的底部
	 */
	protected View mArticleListFooter;									
	protected ProgressBar mALFooterProgressBar;
	protected TextView mALFooterMore;
	/**
	 * 下拉刷新列表的事件
	 */
	protected OnItemClickListener mPTRItemClickListener;	//列表条目单击事件
	protected OnScrollListener mPTRScrollListener;				//列表滚动事件
	protected OnRefreshListener mPTRRefreshListener;			//列表下拉刷新事件
	/**
	 * 数据
	 */
	protected ApiArticle mApiArticle;						//数据接口
	protected ArrayList<Article> mArticlesLocal;		//本地数据
	protected ArrayList<Article> mArticlesNew;		//更新数据
	protected ArrayList<Article> mArticlesShow;		//显示数据
	/**
	 * 记录当前滚动位置
	 */
	protected int mCurItemPosition=0;
	
	
	/**
	 * 构造
	 * @param context
	 * @param listView
	 * @param catID
	 * @param index
	 */
	public PullToRefreshView(Context context, View listView,String catID,int index) {
		super(context);
		this.mListView = listView;
		this.mCatID=catID;
		this.mIndex=index;
		initMemberVar();
		initArticleListView();
	}
	
	/**
	 * 初始化数据成员变量
	 */
	protected void initMemberVar(){
		mArticlesLocal=new ArrayList<Article>();
		mArticlesNew=new ArrayList<Article>();
		mArticlesShow=new ArrayList<Article>();
		mApiArticle=new ApiArticle(mContext);
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
	 * 刷新UI
	 */
	public void refreshPTR(){
		getListData();
	}
	
	/**
	 * 数据获取
	 */
	public void getListData(){
		//mApiArticle.init(apiurl, query);
		//获取本地缓存数据
		boolean flag=getLocalCache();
		//如果本地有数据
		if (flag) {
			mArticlesShow=(ArrayList<Article>) mArticlesLocal.clone();
			mArticleListAdapter.notifyDataSetChanged();
		}else{
			getArticlesOnline();
		}
	}
	
	/**
	 * 本地缓存数据获取
	 */
	protected boolean getLocalCache(){
		boolean flag=false;
		mArticlesLocal=mApiArticle.getArticlesLocal(mCatID);
		if(mArticlesLocal.size()!=0){
			flag=true;
		}
		return flag;
	}
	
	/**
	 * 从网上获取数据
	 */
	protected void getArticlesOnline(){
		//请求API地址
		String apiurl=mResources.getString(R.string.api_articles);
		String query="vcode=jxtzw&cid="+mCatID;
		final String APIURL=apiurl+"?"+query;
		final FinalHttp finalHttp=new FinalHttp();
		// TODO Auto-generated method stub
		finalHttp.get(APIURL, new AjaxCallBack<String>(){
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
			}

			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				mArticlesNew=mApiArticle.parseArticles(t);
				getLocalCache();
				mArticlesShow=(ArrayList<Article>) mArticlesLocal.clone();
				mArticleListAdapter.notifyDataSetChanged();
			}
		});
	}
	
	
	/**
	 * 复制mArticlesLocal到mArticlesShow
	 * 
	 */
	protected void deepCopy(){}
}
