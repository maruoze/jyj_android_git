package com.jxtzw.app.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class NewsListViewPagerAdapter extends PagerAdapter {
	protected ArrayList<View> mNewsListViews;
	
	public NewsListViewPagerAdapter(ArrayList<View> mNewsListViews) {
		super();
		this.mNewsListViews = mNewsListViews;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mNewsListViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager) container).removeView(mNewsListViews.get(position));
	}

	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		((ViewPager) container).addView(mNewsListViews.get(position), 0);
        return mNewsListViews.get(position);
	}
	
	

}
