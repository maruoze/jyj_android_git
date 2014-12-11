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
		try {
			this.mActivity=(Activity) mContext;
		} catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
		}
		this.mResources=mContext.getResources();
		this.mLayoutInflater=LayoutInflater.from(mContext);
	}
}
