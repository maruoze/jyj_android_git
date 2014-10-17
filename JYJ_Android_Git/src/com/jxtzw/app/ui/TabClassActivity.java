package com.jxtzw.app.ui;

import com.jxtzw.app.R;
import android.os.Bundle;
import android.widget.TextView;

public class TabClassActivity extends BaseActivity {
	protected TextView mGold;
	protected TextView mSilver;
	protected TextView mOil;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab_class);
	}
	
	/**
	 * 初始化
	 */
/*	protected void init() {
		DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int tabTVWidth=metric.widthPixels;
        View contanner=findViewById(R.id.popup_window);
        ViewGroup.LayoutParams layoutParams=contanner.getLayoutParams(); 
        layoutParams.width = tabTVWidth/5*3; 
        contanner.setLayoutParams(layoutParams); 
        
        mGold=(TextView) findViewById(R.id.gold);
        mSilver=(TextView) findViewById(R.id.silver);
        mOil=(TextView) findViewById(R.id.oil);
	}*/
	
	/**
	 * 单击事件
	 */
/*	protected void initClick() {
		mGold.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(TabClassActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}*/
	
	
}
