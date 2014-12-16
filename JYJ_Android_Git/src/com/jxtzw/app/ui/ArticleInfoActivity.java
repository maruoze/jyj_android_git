package com.jxtzw.app.ui;

import java.util.ArrayList;

import com.jxtzw.app.AppConfig;
import com.jxtzw.app.AppContext;
import com.jxtzw.app.R;
import com.jxtzw.app.adapter.ArticleInfoViewPagerAdapter;
import com.jxtzw.app.adapter.GridViewMenuAdapter;
import com.jxtzw.app.bean.Article;
import com.jxtzw.app.common.UIHelper;
import com.jxtzw.app.view.ArticleCommentView;
import com.jxtzw.app.view.ArticleInfoView;
import com.jxtzw.app.view.LoginDialog;
import com.jxtzw.app.view.MainMenuPop;
import com.jxtzw.app.view.MenuPopWindow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ArticleInfoActivity extends BaseActivity {
	/**
	 * 数据
	 */
	protected Article mArticle;
	protected String mCatName;
	protected AppContext mAppContext;
	protected Activity mActivity;
	/**
	 * UI标题栏
	 */
	protected Button mHeadLeft;
	protected Button mHeadRight;
	protected TextView mCatNameTextView;
	protected OnClickListener mHLClickListener;
	protected OnClickListener mHRClickListener;
	/**
	 * ViewPager
	 */
	protected ArrayList<View> mArticleInfoViews;
	protected ViewPager mArticleInfoViewPager;
	protected ArticleInfoViewPagerAdapter mArticleInfoVPAdapter;
	protected OnPageChangeListener mArticleInfoPCListener;
	/**
	 * ViewPager内包含的页面
	 */
	protected ArticleInfoView mArticleInfoView;
	protected ArticleCommentView mArticleCommentView;
	/**
	 * 底部的菜单项
	 */
	private ArrayList<PopupWindow> mPopupWindows;
	private GridView mGridView;
	private GridViewMenuAdapter mGridViewAdapter;
	private OnItemClickListener mItemClickListener;
	
	private TextView mLastTextView;
	private MenuPopWindow mMenuPopWindow;
	
	/**
	 * 常量定义
	 */
	private static int POP_SHARE=0;
	private static int POP_COLLECTION=1;
	private static int POP_COMMENT=2;
	private static int POP_MODEL=3;
	/**
	 * 程序全局设置保存
	 */
	protected SharedPreferences mSharedPreferences;
	protected SharedPreferences.Editor mEditor;
	
	
	@Override
 	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_info);
		init();
	}
	
	/**
	 * 提供父类构造函数供子类调用
	 */
	protected void onCreatePt(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	/**
	 * 初始化
	 */
	protected void init(){
		initCommonMemberVar();
		initMemberVar();
		initTitle();
		initArticleInfoViewPager();
		initMenu();
	}
	
	/**
	 * 初始化通用成员变量
	 */
	protected void initCommonMemberVar() {
		//上下文
		mContext=this;
		mActivity=(Activity)mContext;
		mAppContext=(AppContext) mActivity.getApplication();
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
		mLastTextView=null;
	}
	
	/**
	 * 标题栏初始化
	 */
	protected void initTitle() {
		mHeadLeft=(Button) findViewById(R.id.head_left);
		mHeadRight=(Button) findViewById(R.id.head_right);
		mCatNameTextView=(TextView) findViewById(R.id.head_middle);
		mCatNameTextView.setText(mCatName);
		//初始化监听
		initTilteListner();
		mHeadLeft.setOnClickListener(mHLClickListener);
		mHeadRight.setOnClickListener(mHRClickListener);
	}
	
	/**
	 * 标题栏事件监听初始化
	 */
	protected void initTilteListner(){
		mHLClickListener=new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		};
		mHRClickListener=new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UIHelper.showMainPopMenu(mContext, R.layout.pop_users, mHeadRight);
			}
		};
	}
	
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
	
	/**
	 * 底部的菜单项
	 */
	protected void initMenu() {
		//初始化变量
		mPopupWindows=new ArrayList<PopupWindow>();
		initMenuPop();
		initMenuButton();
	}
	
	/**
	 * 初始化菜单按钮
	 */
	@SuppressLint("InflateParams")
	protected void initMenuButton() {
		mGridView=(GridView) findViewById(R.id.gridview_menu);
		mGridViewAdapter=new GridViewMenuAdapter(mResources, mLayoutInflater);
		mGridView.setAdapter(mGridViewAdapter);
		initMenuButtonListener();
		mGridView.setOnItemClickListener(mItemClickListener);
	}
	
	/**
	 * 初始化弹出窗口
	 */
	@SuppressLint("InflateParams")
	protected void initMenuPop() {
		mMenuPopWindow=new MenuPopWindow(mContext,mAppContext,mResources,
				mLayoutInflater,mArticle,mCatName);
		PopupWindow share=mMenuPopWindow.initPopWindow(R.layout.pop_share,POP_SHARE,null);
		PopupWindow collection=mMenuPopWindow.initPopWindow(R.layout.pop_collection, POP_COLLECTION,null);
		PopupWindow comment=mMenuPopWindow.initPopWindow(R.layout.pop_comment,POP_COMMENT,mArticleCommentView);
		//PopupWindow model=mMenuPopWindow.initPopWindow(R.layout.pop_model,POP_MODEL,null);
		mPopupWindows.add(comment);
		mPopupWindows.add(share);
		mPopupWindows.add(collection);
		//mPopupWindows.add(model);
	}
	
	/**
	 * 初始化Button的监听
	 */
	protected void initMenuButtonListener() {
		mItemClickListener=new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (mLastTextView!=null) {
					//还原上一个导航字体颜色
					mLastTextView.setTextColor(mResources.getColor(R.color.white));
				}
				//设置当前导航字体颜色
				TextView curTextView=(TextView) view.findViewById(R.id.menu_button);
				curTextView.setTextColor(mResources.getColor(R.color.red));
				//设置当前导航为上一个
				mLastTextView=curTextView;
				//弹出对应的菜单
				mSharedPreferences=AppConfig.getSharedPreferences(mContext);
				boolean is_login=mSharedPreferences.getBoolean(AppConfig.IS_LOGIN, false);
				if(position==2){
					if (is_login) {
						mPopupWindows.get(position).showAtLocation(curTextView, Gravity.BOTTOM, 0, 0);
					}else{
						LoginDialog loginDialog=new LoginDialog(mContext);
						loginDialog.show(null);
					}
				}else{
					mPopupWindows.get(position).showAtLocation(curTextView, Gravity.BOTTOM, 0, 0);
				}
			}
		};
	}
}
