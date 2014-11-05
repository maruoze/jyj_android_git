package com.jxtzw.app.view;

import java.util.Hashtable;

import com.jxtzw.app.R;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;

public class WebQuoView extends BaseView {
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
	/**
	 * 控件UI
	 */
	protected WebView mWebView;
	
	public WebQuoView(Context context) {
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
		this.mIndex=index;
		initQuoWebView();
	}
	
	/**
	 * 初始化WebView
	 */
	protected void initQuoWebView() {
		mWebView=(WebView) mListView.findViewById(R.id.webview_quotation);
		mWebView.loadUrl(mCatID);
	}
	
}
