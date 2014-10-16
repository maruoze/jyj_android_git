package com.jxtzw.app.api;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;

public class ApiBase {
	protected Context mContext;
	protected Activity mActivity;
	protected Resources mResources;
	protected LayoutInflater mLayoutInflater;
	public ApiBase(Context context) {
		super();
		this.mContext = context;
		this.mActivity=(Activity) mContext;
		this.mResources=mContext.getResources();
		this.mLayoutInflater=LayoutInflater.from(mContext);
	}
}
