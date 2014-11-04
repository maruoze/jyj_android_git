package com.jxtzw.app.ui;

import com.jxtzw.app.R;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

public class TabClassActivity extends BaseActivity {
	protected TextView mGold;
	protected TextView mSilver;
	protected TextView mOil;
	
	protected View mPopWindowBox;
	protected PopupWindow mPWTab;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_class);
		init();
	}
	

	/**
	 * 初始化
	 */
	protected void init() {
		initPopWindow();
	}
	
	/**
	 * 单击事件
	 */
	protected void initClick() {
		mGold.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*Intent intent=new Intent();
				intent.setClass(TabClassActivity.this, TabHomeActivity.class);
				startActivity(intent);
				finish();*/
				mPopWindowBox.setVisibility(View.GONE);
			}
		});
	}
	
	/**
	 * PopWindow初始化
	 */
	private void initPopWindow() {
		
		DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int tabTVWidth=metric.widthPixels;
        View contanner=findViewById(R.id.popup_window);
        ViewGroup.LayoutParams layoutParams=contanner.getLayoutParams(); 
        layoutParams.width = tabTVWidth/6*3; 
        contanner.setLayoutParams(layoutParams); 
		
        mPopWindowBox=findViewById(R.id.popwindow_box);
        mGold=(TextView) findViewById(R.id.gold);
        mSilver=(TextView) findViewById(R.id.silver);
        mOil=(TextView) findViewById(R.id.oil);
        initClick();
	}
	
}
