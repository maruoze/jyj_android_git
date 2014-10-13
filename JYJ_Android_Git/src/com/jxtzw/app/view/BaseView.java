package com.jxtzw.app.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;

public class BaseView {
	protected Context mContext;
	protected Activity mActivity;
	protected Resources mResources;
	protected LayoutInflater mLayoutInflater;
	public BaseView(Context context) {
		super();
		this.mContext = context;
		this.mActivity=(Activity) mContext;
		this.mResources=mActivity.getResources();
		this.mLayoutInflater=LayoutInflater.from(mContext);
	}
}
