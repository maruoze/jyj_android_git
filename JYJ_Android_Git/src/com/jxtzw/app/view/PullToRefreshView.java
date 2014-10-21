package com.jxtzw.app.view;

import java.util.ArrayList;
import java.util.Hashtable;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.jxtzw.app.AppContext;
import com.jxtzw.app.R;
import com.jxtzw.app.adapter.ListViewArticleAdapter;
import com.jxtzw.app.api.ApiArticle;
import com.jxtzw.app.bean.Article;
import com.jxtzw.app.common.UIHelper;
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
	 * 分类ID,数据
	 */
	protected Hashtable<String, String> mHashtable;
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
	 * 是否下拉刷新标志
	 */
	protected boolean mIsRefresh=false;
	/**
	 * 获取网络文章的起始位置
	 */
	protected int mStart=0;
	protected int mPageSize;
	/**
	 * APP上下文
	 */
	protected AppContext mAppContext;
	
	
	/**
	 * 构造
	 * @param context
	 * @param listView
	 * @param catID
	 * @param index
	 */
	public PullToRefreshView(Context context, View listView,
			Hashtable<String, String> hashtable,int index) {
		super(context);
		this.mListView = listView;
		this.mHashtable=hashtable;
		this.mCatID=mHashtable.get("mCatID");
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
		mPageSize=AppContext.PAGE_SIZE;
		mAppContext=(AppContext) mActivity.getApplication();
	}
	
	
	/**
	 * 初始化下拉刷新列表
	 */
	@SuppressLint("InflateParams")
	protected void initArticleListView() {
		//初始化文章列表数据适配器
		mArticleListAdapter=new ListViewArticleAdapter(mContext, mArticlesShow, mResources, mHashtable);
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
				UIHelper.ToastMessage(mContext, String.valueOf(position));
			}
		};
		
		//滚动条滚动
		mPTRScrollListener=new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				mArticleListPTRLV.onScrollStateChanged(view, scrollState);
				// 数据为空--不用继续下面代码了
				if (mArticlesShow.isEmpty())
					return;

				// 判断是否滚动到底部
				boolean scrollEnd = false;
				try {
					if (view.getPositionForView(mArticleListFooter) == view
							.getLastVisiblePosition())
						scrollEnd = true;
				} catch (Exception e) {
					scrollEnd = false;
				}
				
				//如果滚动到了底部
				if (scrollEnd) {
					//锁定滚动列表，防止加载数据时的多次提交
					mArticleListPTRLV.setEnabled(false);
					mALFooterProgressBar.setVisibility(View.VISIBLE);
					mALFooterMore.setText("加载中...");
					//确定新的数据起点
					mStart+=mPageSize;
					int ArticleLocalCount=mArticlesLocal.size();
					if(mStart+mPageSize>ArticleLocalCount){
						getArticlesOnline();
					}else{
						copyLocalToShow();
						mArticleListPTRLV.setEnabled(true);
						mArticleListAdapter.notifyDataSetChanged();
					}
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				mArticleListPTRLV.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
			}
		};
		
		//刷新
		mPTRRefreshListener=new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				mIsRefresh=true;
				getArticlesOnline();
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
		//如果本地有数据,首先加载本地缓存数据
		if (flag) {
			copyLocalToShow();
			mArticleListAdapter.notifyDataSetChanged();
		}
		//检查是否有新数据更新
		getArticlesOnline();
	}
	
	/**
	 * 本地缓存数据获取
	 */
	protected boolean getLocalCache(){
		boolean flag=false;
		mArticlesLocal.clear();
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
		//判断是否有可用网络
		if (!mAppContext.isNetworkConnected()) {
			//如果无可用网络则直接返回
			UIHelper.ToastMessage(mContext, R.string.network_not_connected);
			return;
		}
		//请求API地址
		String apiurl=mResources.getString(R.string.api_articles);
		String query="vcode=jxtzw&cid="+mCatID+"&start="+mStart+"&limit="+mPageSize;
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
				//获取更新的数据
				mArticlesNew.clear();
				mArticlesNew=mApiArticle.parseArticles(t);
				//判断有数据更新后刷新UI
				String msgString=null;
				if(mArticlesNew.size()>0){
					//刷新本地数据数据
					getLocalCache();
					//更新与PullToRefresh绑定的数据
					copyLocalToShow();
					//解锁滚动列表
					mArticleListPTRLV.setEnabled(true);
					mArticleListAdapter.notifyDataSetChanged();
					//显示更新了多少条数据
					msgString="更新了"+mArticlesNew.size()+"条数据";
				}else{
					//显示更新了多少条数据
					msgString="没有数据更新";
				}
				UIHelper.ToastMessage(mContext, msgString);
				//判断是否是下拉刷新
				if(mIsRefresh){
					mArticleListPTRLV.onRefreshComplete();
					mIsRefresh=false;
				}
				//判断网络上是否已经没有新数据
				if(mIsRefresh==false&&mArticlesNew.size()==0){
					mALFooterProgressBar.setVisibility(View.GONE);
					mALFooterMore.setText("没有更多的数据可以加载了！");
				}
			}
		});
	}
	
	
	/**
	 * 复制mArticlesLocal到mArticlesShow，更新与ListView绑定的数据
	 * 
	 */
	protected void copyLocalToShow(){
		mArticlesShow.clear();
		//确定显示的条目数量
		int AScount=mStart+mPageSize;
		if(AScount<mArticlesLocal.size()){
			for (int i = 0; i < AScount; i++) {
				mArticlesShow.add(mArticlesLocal.get(i));
			}
		}else{
			mArticlesShow.addAll(mArticlesLocal);
		}
	}
	
}
