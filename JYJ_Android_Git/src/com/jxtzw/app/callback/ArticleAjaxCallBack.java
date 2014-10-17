package com.jxtzw.app.callback;

import net.tsz.afinal.http.AjaxCallBack;

public class ArticleAjaxCallBack extends AjaxCallBack<String> {
	
	@Override
	public void onFailure(Throwable t, int errorNo, String strMsg) {
		// TODO Auto-generated method stub
		super.onFailure(t, errorNo, strMsg);
	}

	@Override
	public void onSuccess(String t) {
		// TODO Auto-generated metho
	}
}
