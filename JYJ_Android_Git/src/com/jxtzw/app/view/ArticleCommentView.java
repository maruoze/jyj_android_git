package com.jxtzw.app.view;

import java.util.ArrayList;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.jxtzw.app.AppContext;
import com.jxtzw.app.R;
import com.jxtzw.app.adapter.ListViewCommentAdapter;
import com.jxtzw.app.api.ApiArticle;
import com.jxtzw.app.api.ApiComment;
import com.jxtzw.app.bean.Article;
import com.jxtzw.app.bean.Comment;
import com.jxtzw.app.common.StringUtils;
import com.jxtzw.app.common.UIHelper;
import com.jxtzw.app.ui.ArticleInfoActivity;
import com.jxtzw.app.widget.PullToRefreshListView;
import com.jxtzw.app.widget.PullToRefreshListView.OnRefreshListener;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class ArticleCommentView extends BaseView {
	/**
	 * 布局对象
	 */
	protected View mArticleCommentView;
	/**
	 * 数据
	 */
	protected Article mArticle;
	protected String mCatName;
	protected ApiArticle mApiArticle;									//数据接口
	protected ArrayList<Comment> mCommentsLocal;
	protected ArrayList<Comment> mCommentsShow;
	protected ArrayList<Comment> mCommentsNew;
	protected int mPageSize;
	protected int mStart=0;
	protected ListViewCommentAdapter mCommentListAdapter;
	/**
	 * 控件
	 */
	protected TextView mArticleCatNameTV;
	protected TextView mArticleTitleTV;
	protected PullToRefreshListView mPTRComment;
	protected View mCommentFooter;
	protected TextView mCommentFooterMore;
	protected ProgressBar mCommentFooterPB;
	/**
	 * 监听的事件
	 */
	protected OnScrollListener mPTRScrollListener;
	protected OnRefreshListener mPTRRefreshListener;			//列表下拉刷新事件
	
	/**
	 * APP上下文
	 */
	protected AppContext mAppContext;
	protected ApiComment mApiComment;
	/**
	 * 常量数据
	 */
	private static int NOT_GET_ARTICLE_ONLINE=-1;
	private static int UPDATE_PTR_FIRST=0;
	private static int NOT_UPDATE_PTR=1;
	/**
	 * 是否下拉刷新标志
	 */
	protected boolean mIsRefresh=false;
	
	/**
	 * 构造
	 * @param context
	 * @param mArticleCommentView
	 * @param mArticle
	 * @param mCatName
	 */
	public ArticleCommentView(Context context, View mArticleCommentView,
			Article mArticle, String mCatName) {
		super(context);
		this.mArticleCommentView = mArticleCommentView;
		this.mArticle = mArticle;
		this.mCatName = mCatName;
		init();
	}

	/**
	 * UI控件与环境初始化
	 */
	protected void init() {
		//上下文
		mAppContext=(AppContext) mActivity.getApplication();
		mApiComment=new ApiComment(mContext);
		//UI控件对象
		mArticleCatNameTV=(TextView) mArticleCommentView.findViewById(R.id.article_info_catname_time);
		mArticleTitleTV=(TextView) mArticleCommentView.findViewById(R.id.article_info_title);
		//数据变量初始化
		mPageSize=AppContext.PAGE_SIZE;
		mCommentsLocal=new ArrayList<Comment>();
		mCommentsNew=new ArrayList<Comment>();
		mCommentsShow=new ArrayList<Comment>();
		//初始化评论下拉列表
		initCommentPTR();
	}
	
	/**
	 * 初始化评论ListView
	 */
	@SuppressLint("InflateParams")
	protected void initCommentPTR() {
		//初始化评论下拉列表
		mPTRComment=(PullToRefreshListView) mArticleCommentView.findViewById(R.id.ptr_article_comment);
		//初始化评论下拉列表的底部
		mCommentFooter=mLayoutInflater.inflate(R.layout.listview_footer, null);
		mCommentFooterMore=(TextView) mCommentFooter.findViewById(R.id.listview_foot_more);
		mCommentFooterPB=(ProgressBar) mCommentFooter.findViewById(R.id.listview_foot_progress);
		//初始化评论列表数据适配器
		mCommentListAdapter=new ListViewCommentAdapter(mContext, 
																	mCommentsShow, mLayoutInflater, mResources);
		//给评论列表添加底部
		mPTRComment.addFooterView(mCommentFooter);
		//给列表设置适配器
		mPTRComment.setAdapter(mCommentListAdapter);
		//初始化监听
		initListener();
		mPTRComment.setOnScrollListener(mPTRScrollListener);
		mPTRComment.setOnRefreshListener(mPTRRefreshListener);
	}
	
	
	/**
	 * 初始化事件监听
	 */
	protected void initListener(){		
		//滚动条滚动
		mPTRScrollListener=new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				mPTRComment.onScrollStateChanged(view, scrollState);
				// 数据为空--不用继续下面代码了
				if (mCommentsShow.isEmpty())
					return;

				// 判断是否滚动到底部
				boolean scrollEnd = false;
				try {
					if (view.getPositionForView(mCommentFooter) == view
							.getLastVisiblePosition())
						scrollEnd = true;
				} catch (Exception e) {
					scrollEnd = false;
				}
				
				//如果滚动到了底部
				if (scrollEnd) {
					//锁定滚动列表，防止加载数据时的多次提交
					mPTRComment.setEnabled(false);
					mCommentFooterPB.setVisibility(View.VISIBLE);
					mCommentFooterMore.setText("加载中...");
					//确定新的数据起点
					mStart+=mPageSize;
					int CommentLocalCount=mCommentsLocal.size();
					if(mStart+mPageSize>CommentLocalCount){
						getArticlesOnline();
					}else{
						copyLocalToShow();
						mPTRComment.setEnabled(true);
						mCommentListAdapter.notifyDataSetChanged();
					}
				}
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				mPTRComment.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
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
	 * 更新数据和UI
	 */
	public void update() {
		//标题分类
		mArticleCatNameTV.setText(mResources.getString(R.string.comment_text));
		mArticleTitleTV.setText(StringUtils.replaceHTML(mArticle.getTitle()));
		
	}
	
	/**
	 * 更新评论数据
	 */
	@SuppressLint("HandlerLeak")
	public void getCommentHandle(final boolean isGetCommentOnline) {
		final Handler commentHandler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				switch (msg.what) {
					case -1:
						copyLocalToShow();
						mCommentListAdapter.notifyDataSetChanged();
						break;
					case 0:
						copyLocalToShow();
						mCommentListAdapter.notifyDataSetChanged();
						//获取网络数据
						getArticlesOnline();
						break;
					case 1:
						//获取网络数据
						getArticlesOnline();
						break;
				}
				//解锁滚动列表
				mPTRComment.setEnabled(true);
			}
		};
		
		Thread threadGetComment=new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//本地数据库数据获取
				mCommentsLocal.clear();
				mCommentsLocal=mApiComment.getCommentsLocal(mArticle.getAid());
				if (isGetCommentOnline) {
					if(mCommentsLocal.size()!=0){
						commentHandler.sendEmptyMessage(UPDATE_PTR_FIRST);
					}else{
						commentHandler.sendEmptyMessage(NOT_UPDATE_PTR);
					}
				} else {
					if(mCommentsLocal.size()!=0){
						commentHandler.sendEmptyMessage(NOT_GET_ARTICLE_ONLINE);
					}
				}
			}
		};
		threadGetComment.start();
	}
	
	/**
	 * 从网上获取数据
	 */
	public void getArticlesOnline(){
		//判断是否有可用网络
		if (!mAppContext.isNetworkConnected()) {
			//如果无可用网络则直接返回
			UIHelper.ToastMessage(mContext, R.string.network_not_connected);
			return;
		}
		//请求API地址
		String apiurl=mResources.getString(R.string.api_comment);
		String query="vcode=jxtzw&aid="+mArticle.getAid()+"&start="+mStart+"&limit="+mPageSize;
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
				mCommentsNew.clear();
				mCommentsNew=mApiComment.parseComments(t);
				//判断有数据更新后刷新UI
				String msgString=null;
				if(mCommentsNew.size()>0){
					/**
					//刷新本地数据数据
					getLocalCache();
					//更新与PullToRefresh绑定的数据
					copyLocalToShow();
					mArticleListAdapter.notifyDataSetChanged();
					//解锁滚动列表
					mArticleListPTRLV.setEnabled(true);
					*/
					getCommentHandle(false);
					//显示更新了多少条数据
					msgString="更新了"+mCommentsNew.size()+"条数据";
					UIHelper.ToastMessage(mContext, msgString);
				}
				//判断是否是下拉刷新
				if(mIsRefresh){
					mPTRComment.onRefreshComplete();
					mIsRefresh=false;
					if(mCommentsNew.size()<=0){
						msgString="没有新数据";
						UIHelper.ToastMessage(mContext, msgString);
					}
				}
				//判断网络上是否已经没有新数据
				if(mIsRefresh==false&&mCommentsNew.size()==0){
					mCommentFooterPB.setVisibility(View.GONE);
					mCommentFooterMore.setText("没有更多的评论可以加载了！");
				}
			}
		});
	}
	
	
	/**
	 * 更新与ListView绑定的数据
	 * 
	 */
	public void copyLocalToShow(){
		mCommentsShow.clear();
		//确定显示的条目数量
		int AScount=mStart+mPageSize;
		if(AScount<mCommentsLocal.size()){
			for (int i = 0; i < AScount; i++) {
				mCommentsShow.add(mCommentsLocal.get(i));
			}
		}else{
			mCommentsShow.addAll(mCommentsLocal);
		}
	}
}
