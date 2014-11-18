package com.jxtzw.app.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.AdapterView.OnItemClickListener;

import com.jxtzw.app.R;
import com.jxtzw.app.common.UIHelper;

public class MainMenuPop {
	/**
	 * UI
	 */
	private Context mContext;
	private Resources mResources;
	private int mLayout;
	private LayoutInflater mLayoutInflater;
	private View mPopView;
	private PopupWindow mPop;
	private ListView mListView;
	private ArrayAdapter<String> mLVAdapter;
	private String[] arrayStrings={"登录","注册"};
	/**
	 * 事件监听
	 */
	private OnItemClickListener mItemClickListener;
	
	
	/**
	 * 构造
	 * @param mLayoutInflater
	 */
	public MainMenuPop(Context context,Resources mResources,
			LayoutInflater mLayoutInflater) {
		this.mContext=context;
		this.mResources=mResources;
		this.mLayoutInflater = mLayoutInflater;
	}
	
	/**
	 * 弹出菜单样式及功能创建
	 */
	@SuppressLint("InflateParams")
	public PopupWindow initPop(int layout) {
		mLayout=layout;
		mPopView=mLayoutInflater.inflate(R.layout.pop_users, null);
		mPop=new PopupWindow(mPopView);
		mPop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);  
		mPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		//要使setOutsideTouchable(true)生效，首先要给PopWindow设置背景图片为透明
		mPop.setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
		mPop.setOutsideTouchable(true);
		mPop.setFocusable(true);
		//PopWindow内部设置
		mListView=(ListView) mPopView.findViewById(R.id.lv_main_menu);
		mLVAdapter=new ArrayAdapter<String>(mContext, R.layout.item_main_menu, arrayStrings);
		mListView.setAdapter(mLVAdapter);
		//监听事件设置
		initListner();
		mListView.setOnItemClickListener(mItemClickListener);
		//显示位置
		return mPop;
	}
	
	/**
	 * 事件监听
	 */
	private void initListner() {
		mItemClickListener=new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//UIHelper.ToastMessage(mContext, String.valueOf(position));
			}
		};
	}
}
