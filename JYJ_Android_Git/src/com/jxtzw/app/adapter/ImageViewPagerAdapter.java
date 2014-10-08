package com.jxtzw.app.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

public class ImageViewPagerAdapter extends PagerAdapter {
	protected ImageView[] mImageViews;
	
	public ImageViewPagerAdapter(ImageView[] ImageViews) {
		super();
		this.mImageViews = ImageViews;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mImageViews.length;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		//return false;
		return arg0==arg1;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager) container).removeView(mImageViews[position]);
	}

	@Override
	public Object instantiateItem(View container, int position) {
		// TODO Auto-generated method stub
		((ViewPager) container).addView(mImageViews[position], 0);
        return mImageViews[position];
	}
	
	

}
