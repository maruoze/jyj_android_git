package com.jxtzw.app.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class ArticleInfoViewPagerAdapter extends PagerAdapter {
	protected ArrayList<View> mArticleInfoViews;
	
	public ArticleInfoViewPagerAdapter(ArrayList<View> mArticleInfoViews) {
		super();
		this.mArticleInfoViews = mArticleInfoViews;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mArticleInfoViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager) container).removeView(mArticleInfoViews.get(position));
	}

	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		((ViewPager) container).addView(mArticleInfoViews.get(position), 0);
        return mArticleInfoViews.get(position);
	}
}
