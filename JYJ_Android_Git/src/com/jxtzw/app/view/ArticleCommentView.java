package com.jxtzw.app.view;

import com.jxtzw.app.common.UIHelper;

import android.content.Context;

public class ArticleCommentView extends BaseView {

	public ArticleCommentView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 更新数据和UI
	 */
	public void update() {
		UIHelper.ToastMessage(mContext, "评论");
	}
}
