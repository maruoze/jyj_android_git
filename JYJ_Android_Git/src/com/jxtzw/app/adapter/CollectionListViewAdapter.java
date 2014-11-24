package com.jxtzw.app.adapter;

import java.util.ArrayList;
import java.util.Hashtable;

import com.jxtzw.app.R;
import com.jxtzw.app.api.ApiCollectionEntry;
import com.jxtzw.app.bean.Article;
import com.jxtzw.app.bean.CollectionEntry;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class CollectionListViewAdapter extends BaseAdapter {
	
	private ArrayList<CollectionEntry> mCTEntries;
	private Context 					mContext;//运行上下文
	private LayoutInflater 		mLayoutInflater;//视图容器
	private Resources 				mResources;//自定义项视图源 
	
	/**
	 * 滑动手指
	 */
	private float mDownX;  //点下时候获取的x坐标
	private float mUpX;   //手指离开时候的x坐标
	private Button mButton; //用于执行删除的button
	/**
	 * 
	 */
	private ApiCollectionEntry mACEntry;
	
	
	
	/**
	 * 构造函数
	 * @param mCTEntries
	 * @param mContext
	 */
	public CollectionListViewAdapter(ArrayList<CollectionEntry> cTEntries,
			Context context) {
		super();
		this.mCTEntries = cTEntries;
		this.mContext = context;
		this.mLayoutInflater = LayoutInflater.from(mContext);
		this.mResources=mContext.getResources();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mCTEntries.size();
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mCTEntries.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(mResources.getLayout(R.layout.item_collection_list),null); 
			holder = new ViewHolder();
			holder.textView = (TextView) convertView.findViewById(R.id.tv_article_title);
			holder.button=(Button) convertView.findViewById(R.id.bt_delete);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		convertView.setOnTouchListener(new OnTouchListener() {  //为每个item设置setOnTouchListener事件
			@SuppressLint("ClickableViewAccessibility")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final ViewHolder holder = (ViewHolder) v.getTag();  //获取滑动时候相应的ViewHolder，以便获取button按钮
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:  //手指按下
						mDownX = event.getX(); //获取手指x坐标
						if ( mButton!= null) {   
							mButton.setVisibility(View.GONE);  //影藏显示出来的button
						}
						break;
					case MotionEvent.ACTION_UP:  //手指离开
						mUpX = event.getX(); //获取x坐标值
						break;
					}
				
				if (holder.button != null) { 
					if (Math.abs(mUpX - mDownX) > 35) {  //2次坐标的绝对值如果大于35，就认为是左右滑动
						holder.button.setVisibility(View.VISIBLE);  //显示删除button
						mButton = holder.button;  //赋值给全局button，一会儿用
						//view=v; //得到itemview，在上面加动画
						return true; //终止事件
					}
					return false;  //释放事件，使onitemClick可以执行
				}
				return false;	
			}
			
		});
		
		holder.button.setOnClickListener(new OnClickListener() {  //为button绑定事件

			@Override
			public void onClick(View v) {
				
				if (mButton != null) {  
					mButton.setVisibility(View.GONE);  //点击删除按钮后，影藏按钮 
					//数据库中也删除
					mACEntry=new ApiCollectionEntry(mContext);
					mACEntry.delete(mCTEntries.get(position));
					//把数据源里面相应数据删除
					mCTEntries.remove(position);  
					notifyDataSetChanged();
				}

			}
		});
		
		//获取数据
		holder.textView.setText(mCTEntries.get(position).getTitle());  //显示数据
		holder.textView.setTag(mCTEntries.get(position));
		return convertView;		
	}

	static class ViewHolder {
		TextView textView;  //显示数据的view
		Button button;  //删除按钮
	}
	
}
