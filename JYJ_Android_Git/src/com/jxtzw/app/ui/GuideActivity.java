package com.jxtzw.app.ui;

import com.jxtzw.app.R;
import com.jxtzw.app.adapter.ImageViewPagerAdapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class GuideActivity extends Activity {
	//图片切换对象实例
	private ViewPager mImageViewPager;
	//图片切换对象数据适配器实例
	private ImageViewPagerAdapter mImageViewPagerAdapter;
	//图片切换事件监听器
	private OnPageChangeListener mIVPOnPageChangeListener;
	//图片对象数组
	private ImageView[] mImages;
	//图片对象资源ID数组
	private int[] mImagesID={
			R.drawable.guide_01,R.drawable.guide_02,
			R.drawable.guide_03,R.drawable.guide_04};
	//切换点图片对象数组
	private ImageView[] mPoints;
	private int[] mPointsID={
			R.id.ImagePoint_1,R.id.ImagePoint_2,
			R.id.ImagePoint_3,R.id.ImagePoint_4};
	//当前为选中的点的索引
	private int mCurPoint=0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去掉标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉信息栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
										  WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//绑定布局XML
		setContentView(R.layout.activity_guide);
		//初始化ViewPager
		initImageViewPager();
	}
	
	
	/**
	 * 初始化图片切换的ViewPager
	 */
	protected void initImageViewPager() {
		int viewCount=mImagesID.length;
		//初始化图片数组
		mImages=new ImageView[viewCount];
		//初始化图片对象数组
		for (int i = 0; i < viewCount; i++) {
			ImageView imageView=new ImageView(this);
			imageView.setBackgroundResource(mImagesID[i]);
			mImages[i]=imageView;
		}
		//初始化图片ViewPager对象
		mImageViewPager=(ViewPager) findViewById(R.id.ImageViewPager);
		mImageViewPagerAdapter=new ImageViewPagerAdapter(mImages);
		mImageViewPager.setAdapter(mImageViewPagerAdapter);
		//初始化Point
		mPoints=new ImageView[viewCount];
		for (int i = 0; i < viewCount; i++) {
			ImageView pointImageView=new ImageView(this);
			pointImageView=(ImageView) findViewById(mPointsID[i]);
			mPoints[i]=pointImageView;
		}
		//切换事件监听
		initListener();
		mImageViewPager.setOnPageChangeListener(mIVPOnPageChangeListener);
	}
	
	/**
	 * 事件监听器初始化
	 */
	protected void initListener() {
		mIVPOnPageChangeListener=new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				Drawable point_c = getResources().getDrawable(R.drawable.point_c); 
				Drawable point_s = getResources().getDrawable(R.drawable.point_s); 
				mPoints[mCurPoint].setImageDrawable(point_c);
				mPoints[arg0].setImageDrawable(point_s);
				mCurPoint=arg0;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	
	
	/**
	 * 跳转到MainActivity
	 */
	protected void startMain(){
		Intent intent=new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
	}
}
