package com.jxtzw.app.adapter;

import com.jxtzw.app.R;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridViewShareAdapter extends BaseAdapter {
	private Resources mResources;
	private LayoutInflater mLayoutInflater;
	private String[] mTitleStrings;
	private int[] mImages;
	
	public GridViewShareAdapter(Resources mResources,
			LayoutInflater mLayoutInflater, String[] mTitleStrings,
			int[] mImages) {
		super();
		this.mResources = mResources;
		this.mLayoutInflater = mLayoutInflater;
		this.mTitleStrings = mTitleStrings;
		this.mImages = mImages;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mTitleStrings.length;
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
			convertView=mLayoutInflater.inflate(mResources.getLayout(R.layout.item_share), null);
			listItemView = new ListItemView();
			listItemView.shareTV=(TextView) convertView.findViewById(R.id.textview_share);
			convertView.setTag(listItemView);
		}else{
			listItemView = (ListItemView) convertView.getTag();
		}
		String shareTitle=mTitleStrings[position];
		int shareImage=mImages[position];
		listItemView.shareTV.setText(shareTitle);
		Drawable	drawable=mResources.getDrawable(shareImage);
		drawable.setBounds(0, 0, 30, 30);
		listItemView.shareTV.setCompoundDrawables(null, drawable, null , null);
		return convertView;
	}

	/**
	 * 自定义控件集合
	 */
	static class ListItemView{				  
        public TextView shareTV; 
	} 
}
