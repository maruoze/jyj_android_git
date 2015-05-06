package com.jxtzw.app.view;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

import com.jxtzw.app.AppConfig;
import com.jxtzw.app.AppContext;
import com.jxtzw.app.R;
import com.jxtzw.app.adapter.GridViewCollectionAdapter;
import com.jxtzw.app.api.ApiCollectionEntry;
import com.jxtzw.app.bean.Article;
import com.jxtzw.app.bean.CollectionClassify;
import com.jxtzw.app.common.UIHelper;

public class MenuCollectionPW extends MenuPopWindow {

	public MenuCollectionPW(Context context, AppContext appContext,
			Resources mResources, LayoutInflater mLayoutInflater,
			Article article, String catName) {
		super(context, appContext, mResources, mLayoutInflater, article, catName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initCellection() {
		// TODO Auto-generated method stub
		//初始化数据
		mCollTitleStrings=new ArrayList<CollectionClassify>();
		setCollectionClassify();
		//设置数据适配器
		mCollectionGridView=(GridView) mPopView.findViewById(R.id.gridview_collection);
		mCollectionAdapter=new GridViewCollectionAdapter(mResources, 
				mLayoutInflater, mCollTitleStrings);
		mCollectionGridView.setAdapter(mCollectionAdapter);
		/**
		 * 项目单击
		 */
		mCollectionGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				DialogColloctionOp dcOp=new DialogColloctionOp(mContext);
				dcOp.show(view,position,mCollTitleStrings,mCollectionAdapter);
			}
		});
				
		
		//收藏菜单的新增按钮
		Button addCollectionClassify=(Button) mPopView.findViewById(R.id.collection_add);
		addCollectionClassify.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mCollTitleStrings.size()>=AppConfig.COLLECT_CLASSIFY_MAX){
					String msg="最多只能创建"+AppConfig.COLLECT_CLASSIFY_MAX+"个收藏分类";
					UIHelper.ToastMessage(mContext, msg);
				}else{
					DialogCollection dialogCollection=new DialogCollection(mContext);
					dialogCollection.show(mCollTitleStrings,mCollectionAdapter,null,0);
				}
			}
		});
		
	}
}
