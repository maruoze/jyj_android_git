package com.jxtzw.app.ui;

import java.util.ArrayList;

import com.jxtzw.app.R;
import com.jxtzw.app.adapter.CollectionListViewAdapter;
import com.jxtzw.app.api.ApiArticle;
import com.jxtzw.app.api.ApiCollectionEntry;
import com.jxtzw.app.bean.Article;
import com.jxtzw.app.bean.CollectionClassify;
import com.jxtzw.app.bean.CollectionEntry;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CollectionActivity extends ArticleInfoActivity {
	/**
	 * 数据
	 */
	private CollectionClassify mCCFY;
	private ArrayList<CollectionEntry> mCCEntries;
	/**
	 * UI
	 */
	private ListView mListView;
	private CollectionListViewAdapter mCCListViewAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreatePt(savedInstanceState);
		setContentView(R.layout.activity_collection);
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		initCommonMemberVar();
		initMemberVar();
		initTitle();
		initCollectionLV();
	}

	@Override
	protected void initMemberVar() {
		// TODO Auto-generated method stub
		//获取Intent
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		mCCFY=(CollectionClassify) bundle.getSerializable("ccfy");
		mCatName="收藏分类 • "+mCCFY.getCcf_classify_name();
	}
	
	/**
	 * 初始化
	 */
	private void initCollectionLV() {
		mCCEntries=new ArrayList<CollectionEntry>();
		mListView=(ListView) findViewById(R.id.lv_collection);
		mCCListViewAdapter=new CollectionListViewAdapter(mCCEntries, mContext);
		mListView.setAdapter(mCCListViewAdapter);
		//项目单击事件监听
		initListener();
		//更新ListView
		initData();
	}
	
	/**
	 * ListView每项的监听事件
	 */
	private void initListener() {
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//获取文章
				CollectionEntry cEntry=(CollectionEntry) ((TextView)view.findViewById(R.id.tv_article_title)).getTag();
				String aid=cEntry.getAid();
				ApiArticle apiArticle=new ApiArticle(mContext);
				Article article=apiArticle.getArticleByAid(aid);
				Bundle bundle=new Bundle();
				bundle.putSerializable("article", article);
				bundle.putString("cat_name", mCCEntries.get(position).getCatName());
				//新的Intent
				Intent articleInfoIntent=new Intent();
				articleInfoIntent.putExtras(bundle);
				articleInfoIntent.setClass(mContext, ArticleInfoActivity.class);
				mContext.startActivity(articleInfoIntent);
			}
		});
	}
	
	/**
	 * 获取文章
	 */
	
	
	/**
	 * 获取收藏条目
	 */
	@SuppressLint("HandlerLeak")
	private void initData() {
		final Handler handler=new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				mCCListViewAdapter.notifyDataSetChanged();
			}
			
		};
		
		Thread thread=new Thread(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				ApiCollectionEntry apiCE=new ApiCollectionEntry(mContext);
				ArrayList<CollectionEntry> ce=apiCE.get(mCCFY);
				if (ce.size()!=0) {
					mCCEntries.clear();
					mCCEntries.addAll(ce);
					handler.sendEmptyMessage(0);
				}
			}
		};
		thread.start();
	}
	
}
