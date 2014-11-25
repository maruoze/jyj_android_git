package com.jxtzw.app;

import com.jxtzw.app.bean.CollectionClassify;
import com.jxtzw.app.ui.ArticleInfoActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class SettingActivity extends ArticleInfoActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreatePt(savedInstanceState);
		setContentView(R.layout.activity_setting);
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		initCommonMemberVar();
		initMemberVar();
		initTitle();
	}

	@Override
	protected void initMemberVar() {
		// TODO Auto-generated method stub
		//获取Intent
		mCatName="设置";
	}
	
	
	
}
