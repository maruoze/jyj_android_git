package com.jxtzw.app.view;

import java.util.Hashtable;

import com.jxtzw.app.AppContext;

import android.content.Context;
import android.view.View;

public class WebMemberView extends WebQuoView {

	public WebMemberView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 初始化
	 */
	public void init(View view, String url,int index) {
		this.mListView=view;
		this.mCatID=url;
		this.mIndex=index;
		this.mAppContext=(AppContext) mActivity.getApplication();
		initMemberVar();
	}
}
