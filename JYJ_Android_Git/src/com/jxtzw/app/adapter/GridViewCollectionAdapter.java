package com.jxtzw.app.adapter;

import java.util.ArrayList;

import com.jxtzw.app.R;
import com.jxtzw.app.adapter.GridViewShareAdapter.ListItemView;
import com.jxtzw.app.bean.CollectionClassify;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridViewCollectionAdapter extends BaseAdapter {
	private Resources mResources;
	private LayoutInflater mLayoutInflater;
	private ArrayList<CollectionClassify> mTitleStrings;
	
	public GridViewCollectionAdapter(Resources mResources,
			LayoutInflater mLayoutInflater, ArrayList<CollectionClassify> mTitleStrings) {
		super();
		this.mResources = mResources;
		this.mLayoutInflater = mLayoutInflater;
		this.mTitleStrings = mTitleStrings;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTitleStrings.size();
	}
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//自定义视图
		ListItemView  listItemView = null;
		if (convertView == null){
			convertView=mLayoutInflater.inflate(mResources.getLayout(R.layout.item_collection), null);
			listItemView = new ListItemView();
			listItemView.collectionTV=(TextView) convertView.findViewById(R.id.textview_collection);
			convertView.setTag(listItemView);
		}else{
			listItemView = (ListItemView) convertView.getTag();
		}
		String shareTitle=mTitleStrings.get(position).getCcf_classify_name();
		listItemView.collectionTV.setText(shareTitle);
		listItemView.collectionTV.setTag(mTitleStrings.get(position).getCcf_classify_id());
		return convertView;
	}
	
	/**
	 * 自定义控件集合
	 */
	static class ListItemView{				  
        public TextView collectionTV; 
	} 

}
