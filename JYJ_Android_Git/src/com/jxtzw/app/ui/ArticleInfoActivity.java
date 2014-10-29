package com.jxtzw.app.ui;

import java.util.ArrayList;

import com.jxtzw.app.R;
import com.jxtzw.app.R.id;
import com.jxtzw.app.R.layout;
import com.jxtzw.app.adapter.ArticleInfoViewPagerAdapter;
import com.jxtzw.app.bean.Article;
import com.jxtzw.app.common.UIHelper;
import com.jxtzw.app.view.ArticleCommentView;
import com.jxtzw.app.view.ArticleInfoView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ArticleInfoActivity extends BaseActivity {
	/**
	 * 数据
	 */
	protected Article mArticle;
	protected String mCatName;
	/**
	 * UI标题栏
	 */
	protected Button mHeadLeft;
	protected Button mHeadRight;
	protected TextView mCatNameTextView;
	/**
	 * ViewPager
	 */
	protected ArrayList<View> mArticleInfoViews;
	protected ViewPager mArticleInfoViewPager;
	protected ArticleInfoViewPagerAdapter mArticleInfoVPAdapter;
	protected OnPageChangeListener mArticleInfoPCListener;
	
	protected ArticleInfoView mArticleInfoView;
	protected ArticleCommentView mArticleCommentView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_info);
		init();
	}
	
	/**
	 * 初始化
	 */
	protected void init(){
		initCommonMemberVar();
		initMemberVar();
		initTitle();
		initArticleInfoViewPager();
	}
	
	/**
	 * 初始化通用成员变量
	 */
	protected void initCommonMemberVar() {
		//上下文
		mContext=this;
		//资源
		mResources=getResources();
		//布局
		mLayoutInflater=LayoutInflater.from(this);
	}
	
	/**
	 * 成员变量初始化
	 */
	protected void initMemberVar(){
		//获取Intent
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		mArticle=(Article) bundle.getSerializable("article");
		mCatName=bundle.getString("cat_name");
		//ViewPager
		mArticleInfoViews=new ArrayList<View>();
	}
	
	/**
	 * 标题栏初始化
	 */
	protected void initTitle() {
		mHeadLeft=(Button) findViewById(R.id.head_left);
		mHeadRight=(Button) findViewById(R.id.head_right);
		mCatNameTextView=(TextView) findViewById(R.id.head_middle);
		mCatNameTextView.setText(mCatName);
		
		initTilteListner();
	}
	
	/**
	 * 标题栏事件监听初始化
	 */
	protected void initTilteListner(){}
	
	/**
	 * 初始化ArticleInfo的ViewPager
	 */
	protected void initArticleInfoViewPager(){
		//设置ViewPager的View数据
		View articleInfoView=initArticleInfoView();
		View articleCommentView = initArticleCommentView();
		mArticleInfoViews.add(articleInfoView);
		mArticleInfoViews.add(articleCommentView);
		//初始化ViewPager和ViewPagerAdapter
		mArticleInfoViewPager=(ViewPager) findViewById(R.id.article_info_viewpager);
		mArticleInfoVPAdapter=new ArticleInfoViewPagerAdapter(mArticleInfoViews);
		mArticleInfoViewPager.setAdapter(mArticleInfoVPAdapter);
		//初始化并设置监听
		initViewPagerListener();
		mArticleInfoViewPager.setOnPageChangeListener(mArticleInfoPCListener);
		//首次刷新
		update(0);
	}
	
	/**
	 * 初始化ViewPager的事件监听
	 */
	protected void initViewPagerListener() {
		mArticleInfoPCListener=new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				update(arg0);
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
	 * 初始化ViewPager中的Article Information
	 */
	@SuppressLint("InflateParams")
	protected View initArticleInfoView() {
		View view=mLayoutInflater.inflate(R.layout.viewpager_article_info, null);
		mArticleInfoView=new ArticleInfoView(mContext, view, mArticle,mCatName);
		return view;
	}
	
	/**
	 * 初始化ViewPager中的Article Comment
	 */
	@SuppressLint("InflateParams")
	protected View initArticleCommentView() {
		View view=mLayoutInflater.inflate(R.layout.viewpager_article_comment, null);
		mArticleCommentView=new ArticleCommentView(mContext, view,mArticle,mCatName);
		return view;
	}
	
	/**
	 * 更新数据和UI
	 */
	protected void update(int index) {
		switch (index) {
			case 0:
				mArticleInfoView.update();
				break;
			case 1:
				mArticleCommentView.update();
				break;
		}
	}
}
