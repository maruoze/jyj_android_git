package com.jxtzw.app.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.jxtzw.app.AppContext;
import com.jxtzw.app.R;
import com.jxtzw.app.api.ApiArticle;
import com.jxtzw.app.bean.Article;
import com.jxtzw.app.common.StringUtils;
import com.jxtzw.app.common.UIHelper;
import com.jxtzw.app.ui.ArticleInfoActivity;
import com.jxtzw.app.widget.ListViewInScrollView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
	protected String mImageBaseURL;
	/**
	 * 控件
	 */
	protected TextView mArticleCatNameTV;
	protected TextView mArticleTitleTV;
	protected WebView mArticleContentWV;
	protected ListViewInScrollView mRelatedArticlesLV;
	protected ArrayAdapter<String> mRelatedArticlesLVAdapter;
	protected String[] mRelatedArticlesTitle;
	protected ArrayList<Article> mRelatedArticles;
	protected OnItemClickListener mRelatedArticleICListener;
	protected Button mWviewRefresh;
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
	
	/**
	 * 变量和对象初始化
	 */
	protected void init() {
		//上下文
		mAppContext=(AppContext) mActivity.getApplication();
		//UI控件对象
		mArticleCatNameTV=(TextView) mArticleInfoView.findViewById(R.id.article_info_catname_time);
		mArticleTitleTV=(TextView) mArticleInfoView.findViewById(R.id.article_info_title);
		mArticleContentWV=(WebView) mArticleInfoView.findViewById(R.id.article_info_content);
		mArticleContentWV.getSettings().setDefaultTextEncodingName("UTF-8") ;
		mRelatedArticlesLV=(ListViewInScrollView) mArticleInfoView.findViewById(R.id.related_article);
		mApiArticle=new ApiArticle(mContext);
		mWviewRefresh=(Button) mArticleInfoView.findViewById(R.id.bt_wview_refresh);
		//数据
		mRelatedArticles=new ArrayList<Article>();
		mImageBaseURL=mResources.getString(R.string.host);
		mWviewRefresh.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				updateContent();
			}
		});
	}
	
	/**
	 * 数据和UI更新
	 */
	public void update() {
		String dateTimeString=StringUtils.timeStamp2Date(mArticle.getDateLine(), "yyyy-MM-dd");
		String catTitleString=mCatName+" • "+mArticle.getUsername()+" • "+dateTimeString;
		mArticleCatNameTV.setText(catTitleString);
		mArticleTitleTV.setText(StringUtils.replaceHTML(mArticle.getTitle()));
		String htmlString="文章内容加载中...";
		//mArticleContentWV.loadData(htmlString, "text/html", "UTF-8");
		mArticleContentWV.loadDataWithBaseURL(null, htmlString, "text/html", "UTF-8", null);
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
			content=StringUtils.dataFiltering(content,mImageBaseURL);
			mArticleContentWV.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);
			mWviewRefresh.setVisibility(View.GONE);
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
			String htmlString=mResources.getString(R.string.network_not_connected)+", 设置网络后点击下面的按钮刷新！";
			mArticleContentWV.loadDataWithBaseURL(null, htmlString, "text/html", "UTF-8", null);
			mWviewRefresh.setText("有可用网络后请点击本按钮刷新");
			mWviewRefresh.setVisibility(View.VISIBLE);
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
				String htmlString="获取文章内容失败, 请稍后点击下面按钮再试！";
				mArticleContentWV.loadDataWithBaseURL(null, htmlString, "text/html", "UTF-8", null);
				mWviewRefresh.setText("刷新");
				mWviewRefresh.setVisibility(View.VISIBLE);
			}

			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				//刷新
				ArrayList<Article> articles=mApiArticle.getArticleContent(t,mArticle);
				if(articles.size()>0){
					mArticle=articles.get(0);
					String content=StringUtils.dataFiltering(mArticle.getContents(),mImageBaseURL);
					mArticleContentWV.loadDataWithBaseURL(null, content,
							"text/html", "UTF-8", null);
					mWviewRefresh.setVisibility(View.GONE);
				}else{
					String htmlString="文章内容解析失败, 请稍后点击下面按钮再试！";
					mArticleContentWV.loadDataWithBaseURL(null, htmlString, "text/html", "UTF-8", null);
					mWviewRefresh.setText("刷新");
					mWviewRefresh.setVisibility(View.VISIBLE);
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
				//数据绑定
				mRelatedArticlesLVAdapter=new ArrayAdapter<String>(mContext, R.layout.item_related_article, mRelatedArticlesTitle);
				mRelatedArticlesLV.setAdapter(mRelatedArticlesLVAdapter);
				//事件监听
				initRAListener();
				mRelatedArticlesLV.setOnItemClickListener(mRelatedArticleICListener);
				mRelatedArticlesLVAdapter.notifyDataSetChanged();
			}
		};
		
		Thread relatedAricleThread=new Thread(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				//获取相关文章
				ArrayList<Article> articles = mApiArticle.getArticlesLocalEx(String.valueOf(mArticle.getCatid()),mArticle.getAid());
				//相关文章个数
				int articleCount=articles.size();
				//如果相关文章个数小于设定的默认个数，则默认个数改为文章个数
				if(articleCount<mRelatedArticleCount){
					mRelatedArticleCount=articleCount;
				}
				//分配相关文章标题数组
				mRelatedArticlesTitle=new String[mRelatedArticleCount];
				//获取随机文章索引
				HashSet<Integer> randomHashSet=new HashSet<Integer>();
				Random r = new Random();
		        while(randomHashSet.size()<5){
		        	randomHashSet.add(r.nextInt(mRelatedArticleCount));
		        }
		        //数据赋值
				Iterator<Integer> iterator=randomHashSet.iterator();
				int index=0;
				while (iterator.hasNext()) {
					int hs_index = iterator.next();
					mRelatedArticlesTitle[index]=articles.get(hs_index).getTitle();
					mRelatedArticles.add(articles.get(hs_index));
					index++;
				}
				relatedArticleHandler.sendEmptyMessage(0);
			}
		};
		relatedAricleThread.start();
	}

	
	/**
	 * 相关新闻的Item单击事件监听
	 */
	protected void initRAListener() {
		mRelatedArticleICListener=new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				// TODO Auto-generated method stub
				//Bundle数据
				Article article=mRelatedArticles.get(position);
				Bundle bundle=new Bundle();
				bundle.putSerializable("article", article);
				bundle.putString("cat_name", mCatName);
				//新的Intent
				Intent articleInfoIntent=new Intent();
				articleInfoIntent.putExtras(bundle);
				articleInfoIntent.setClass(mContext, ArticleInfoActivity.class);
				mContext.startActivity(articleInfoIntent);
			}
		};
	}
}
