package com.jxtzw.app.adapter;

import com.jxtzw.app.R;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class GridViewMenuAdapter extends BaseAdapter {
	private Resources mResources;
	private LayoutInflater mLayoutInflater;
	//private String[] mMenuTitles={"评论","分享","收藏","模式"};
	private String[] mMenuTitles={"评论","分享","收藏"};
	
	
	public GridViewMenuAdapter(Resources mResources, LayoutInflater mLayoutInflater) {
		super();
		this.mResources=mResources;
		this.mLayoutInflater = mLayoutInflater;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mMenuTitles.length;
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
			convertView=mLayoutInflater.inflate(mResources.getLayout(R.layout.item_menu), null);
			listItemView = new ListItemView();
			listItemView.button=(TextView) convertView.findViewById(R.id.menu_button);
			convertView.setTag(listItemView);
		}else{
			listItemView = (ListItemView) convertView.getTag();
		}
		String buttonTitle=mMenuTitles[position];
		listItemView.button.setText(buttonTitle);
		return convertView;
	}
	
	/**
	 * 自定义控件集合
	 */
	static class ListItemView{				  
        public TextView button; 
	} 
}
