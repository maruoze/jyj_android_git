package com.jxtzw.app.ui;

import com.jxtzw.app.common.StringUtils;
import com.jxtzw.app.AppConfig;
import com.jxtzw.app.R;
import com.jxtzw.app.adapter.ImageViewPagerAdapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;


public class GuideActivity extends Activity {
	/**
	 * 资源及相关
	 */
	protected Resources mResources;
	
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
	//进入主Activity的按钮
	private Button mEnterToMain;
	//要切换的图片数量
	private int mViewCount;
	//状态存储
	private SharedPreferences mSharedPreferences;
	private SharedPreferences.Editor mEditor;
	/**
	 * 主Activity启动时需要的相关数据
	 */
	private String mMainTabsText;
	private String mCatGoldName;
	private String mCatGoldID;
	private String mCatGoldShowQuotation;
	private String mCatGoldShowImage;
	
	
	
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
		//初始化成员变量
		initMemberVar();
		//判断是否直接跳转主界面
		initBaseSettings();
		//初始化ViewPager
		initImageViewPager();
		//初始化进入按钮
		initEnterToMain();
	}
	
	/**
	 * 初始化成员变量
	 */
	protected void initMemberVar() {
		//初始化资源
		mResources=getResources();
		mViewCount=mImagesID.length;
		mEnterToMain=(Button) findViewById(R.id.EnterToMain);
		//初始化图片数组
		mImages=new ImageView[mViewCount];
	}
	
	/**
	 * 初始化图片切换的ViewPager
	 */
	protected void initImageViewPager() {
		//初始化图片对象数组
		for (int i = 0; i < mViewCount; i++) {
			ImageView imageView=new ImageView(this);
			imageView.setBackgroundResource(mImagesID[i]);
			mImages[i]=imageView;
		}
		//初始化图片ViewPager对象
		mImageViewPager=(ViewPager) findViewById(R.id.ImageViewPager);
		mImageViewPagerAdapter=new ImageViewPagerAdapter(mImages);
		mImageViewPager.setAdapter(mImageViewPagerAdapter);
		//初始化Point
		mPoints=new ImageView[mViewCount];
		for (int i = 0; i < mViewCount; i++) {
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
				//点切换
				Drawable point_c = getResources().getDrawable(R.drawable.point_c); 
				Drawable point_s = getResources().getDrawable(R.drawable.point_s); 
				mPoints[mCurPoint].setImageDrawable(point_c);
				mPoints[arg0].setImageDrawable(point_s);
				mCurPoint=arg0;
				//进入主界面按钮显示
				if (mCurPoint==mViewCount-1) {
					mEnterToMain.setVisibility(View.VISIBLE);
				}
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
	 * 按钮初始化
	 */
	protected void initEnterToMain() {
		mEnterToMain.setVisibility(View.GONE);
		mEnterToMain.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startMain();
			}
		});
	}
	
	/**
	 * 跳转到MainActivity
	 */
	protected void startMain(){
		Intent intent=new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
		finish();
	}
	
	/**
	 * 初始化基本设置
	 */
	protected void initBaseSettings() {
		mSharedPreferences=AppConfig.getSharedPreferences(this);
		mEditor=mSharedPreferences.edit();
		boolean is_first_open=mSharedPreferences.getBoolean(AppConfig.JYJ_CONF_IS_FIRST_OPEN, true);
		if (!is_first_open) {
			startMain();
		}else{
			saveSharedPreferences();
		}
	}
	
	/**
	 * 设置保存
	 */
	protected void saveSharedPreferences() {
		//初始化需要保存的变量
		initConfig();
		//保存状态变量
		mEditor.putBoolean(AppConfig.JYJ_CONF_IS_FIRST_OPEN, false);
		//保存切换标签名称
		mEditor.putString(AppConfig.JYJ_MAIN_TABS_TEXT_DEFAULT, mMainTabsText);
		mEditor.putString(AppConfig.JYJ_MAIN_TABS_TEXT_USERSET, mMainTabsText);
		//保存子分类名称和ID
		mEditor.putString(AppConfig.JYJ_CAT_GOLD_NAME_DEFAULT, mCatGoldName);
		mEditor.putString(AppConfig.JYJ_CAT_GOLD_NAME_USERSET, mCatGoldName);
		mEditor.putString(AppConfig.JYJ_CAT_GOLD_ID_DEFAULT, mCatGoldID);
		mEditor.putString(AppConfig.JYJ_CAT_GOLD_ID_USERSET, mCatGoldID);
		mEditor.putString(AppConfig.JYJ_CAT_GOLD_SHOW_QUOT_DEFAULT, mCatGoldShowQuotation);
		mEditor.putString(AppConfig.JYJ_CAT_GOLD_SHOW_QUOT_USERSET, mCatGoldShowQuotation);
		mEditor.putString(AppConfig.JYJ_CAT_GOLD_SHOW_IMAGE_DEFAULT, mCatGoldShowImage);
		mEditor.putString(AppConfig.JYJ_CAT_GOLD_SHOW_IMAGE_USERSET, mCatGoldShowImage);
		//数据提交
		mEditor.commit();
	}
	
	/**
	 * 初始化主程序启动需要的变量
	 */
	protected void initConfig() {
		//主分类名称字符串
		String[] mainTabsText=mResources.getStringArray(R.array.main_tabs_text);
		mMainTabsText=StringUtils.stringArrayToString(mainTabsText, ";");
		//子分类名称ID字符串
		String[] catGoldName=mResources.getStringArray(R.array.cat_gold_name);
		mCatGoldName=StringUtils.stringArrayToString(catGoldName, ";");
		
		String[] catGoldID=mResources.getStringArray(R.array.cat_gold_id);
		mCatGoldID=StringUtils.stringArrayToString(catGoldID, ";");
		
		String[] catGoldShowQuotation=mResources.getStringArray(R.array.cat_gold_show_quotaion);
		mCatGoldShowQuotation=StringUtils.stringArrayToString(catGoldShowQuotation, ";");
		
		String[] catGoldShowImage=mResources.getStringArray(R.array.cat_gold_show_image);
		mCatGoldShowImage=StringUtils.stringArrayToString(catGoldShowImage, ";");
	}
}
