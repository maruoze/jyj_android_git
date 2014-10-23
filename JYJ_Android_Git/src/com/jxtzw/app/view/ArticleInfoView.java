package com.jxtzw.app.view;

import com.jxtzw.app.R;
import com.jxtzw.app.bean.Article;
import com.jxtzw.app.common.UIHelper;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;
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
	/**
	 * 控件
	 */
	protected TextView mArticleCatNameTV;
	protected TextView mArticleTitleTV;
	protected WebView mArticleContentWV;
	protected ListView mRelatedArticlesLV;
	
	public ArticleInfoView(Context context, View articleInfoView,
			Article article) {
		super(context);
		this.mArticleInfoView = articleInfoView;
		this.mArticle = article;
	}
	
	protected void init() {
		mArticleCatNameTV=(TextView) mArticleInfoView.findViewById(R.id.article_info_catname_time);
		mArticleTitleTV=(TextView) mArticleInfoView.findViewById(R.id.article_info_title);
		mArticleContentWV=(WebView) mArticleInfoView.findViewById(R.id.article_info_content);
		mRelatedArticlesLV=(ListView) mArticleInfoView.findViewById(R.id.related_article);
	}
	
	/**
	 * 数据和UI更新
	 */
	public void update() {
		
	}
	
	
}
