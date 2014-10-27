package com.jxtzw.app.view;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.jxtzw.app.AppContext;
import com.jxtzw.app.R;
import com.jxtzw.app.api.ApiArticle;
import com.jxtzw.app.bean.Article;
import com.jxtzw.app.common.StringUtils;
import com.jxtzw.app.common.UIHelper;
import com.jxtzw.app.widget.ListViewInScrollView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ArticleInfoView extends BaseView {
	/**
	 * 布局对象
	 */
	protected View mArticleInfoView;
	/**
	 * 数据
	 */
	protected Article mArticle;
	protected String mCatName;
	protected ApiArticle mApiArticle;						//数据接口
	protected int mRelatedArticleCount=5;
	
	/**
	 * 控件
	 */
	protected TextView mArticleCatNameTV;
	protected TextView mArticleTitleTV;
	protected WebView mArticleContentWV;
	protected ListViewInScrollView mRelatedArticlesLV;
	protected ArrayAdapter<String> mRelatedArticlesLVAdapter;
	protected String[] mRelatedArticles;
	/**
	 * APP上下文
	 */
	protected AppContext mAppContext;
	
	
	public ArticleInfoView(Context context, View articleInfoView,
			Article article,String catName) {
		super(context);
		this.mArticleInfoView = articleInfoView;
		this.mArticle = article;
		this.mCatName=catName;
		init();
	}
	
	protected void init() {
		mAppContext=(AppContext) mActivity.getApplication();
		mArticleCatNameTV=(TextView) mArticleInfoView.findViewById(R.id.article_info_catname_time);
		mArticleTitleTV=(TextView) mArticleInfoView.findViewById(R.id.article_info_title);
		mArticleContentWV=(WebView) mArticleInfoView.findViewById(R.id.article_info_content);
		mArticleContentWV.getSettings().setDefaultTextEncodingName("UTF-8") ;
		mRelatedArticlesLV=(ListViewInScrollView) mArticleInfoView.findViewById(R.id.related_article);
		mApiArticle=new ApiArticle(mContext);
		mRelatedArticles=new String[mRelatedArticleCount];
		for (int i = 0; i < mRelatedArticleCount; i++) {
			mRelatedArticles[i]="";
		}
		mRelatedArticlesLVAdapter=new ArrayAdapter<String>(mContext, R.layout.item_related_article, mRelatedArticles);
		mRelatedArticlesLV.setAdapter(mRelatedArticlesLVAdapter);
	}
	
	/**
	 * 数据和UI更新
	 */
	public void update() {
		String dateTimeString=StringUtils.timeStamp2Date(mArticle.getDateLine(), "yyyy-MM-dd");
		String catTitleString=mCatName+" • "+mArticle.getUsername()+" • "+dateTimeString;
		mArticleCatNameTV.setText(catTitleString);
		mArticleTitleTV.setText(mArticle.getTitle());
		String htmlString="文章内容加载中...";
		mArticleContentWV.loadData(htmlString, "text/html", "UTF-8");
		updateContent();
		updateRelatedArticle();
	}
	
	/**
	 * 文章内容更新
	 */
	protected void updateContent(){
		String content=mArticle.getContents();
		if (content==null) {
			//首次获取文章内容
			getContentOnline();
		}else{
			//mArticleContentWV.loadData(content, "text/html", "utf-8");
			mArticleContentWV.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);
		}
	}
	
	/**
	 * 获取文章内容更新回调
	 */
	protected void getContentOnline() {
		//判断是否有可用网络
		if (!mAppContext.isNetworkConnected()) {
			//如果无可用网络则直接返回
			UIHelper.ToastMessage(mContext, R.string.network_not_connected);
			return;
		}
		//请求API地址
		String apiurl=mResources.getString(R.string.api_articles);
		String query="vcode=jxtzw&aid="+mArticle.getAid();
		final String APIURL=apiurl+"?"+query;
		final FinalHttp finalHttp=new FinalHttp();
		finalHttp.get(APIURL, new AjaxCallBack<String>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
			}

			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				//刷新
				ArrayList<Article> articles=mApiArticle.getArticleContent(t,mArticle);
				if(articles.size()>0){
					mArticle=articles.get(0);
					mArticleContentWV.loadDataWithBaseURL(null, mArticle.getContents(),
							"text/html", "UTF-8", null);
				}
			}
		});
	}
	
	
	/**
	 * 相关文章显示
	 */
	@SuppressLint("HandlerLeak")
	protected void updateRelatedArticle() {
		final Handler relatedArticleHandler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				mRelatedArticlesLVAdapter.notifyDataSetChanged();
			}
			
		};
		
		Thread relatedAricleThread=new Thread(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ArrayList<Article> articles = mApiArticle.getArticlesLocal(String.valueOf(mArticle.getCatid()));
				int articleCount=articles.size();
				if(articleCount<mRelatedArticleCount){
					mRelatedArticleCount=articleCount;
				}
				for (int i = 0; i < mRelatedArticleCount; i++) {
					mRelatedArticles[i]=articles.get(i).getTitle();
				}
				relatedArticleHandler.sendEmptyMessage(0);
			}
		};
		relatedAricleThread.start();
	}
	
}
