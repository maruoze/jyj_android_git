package com.jxtzw.app.adapter;

import java.util.ArrayList;
import com.jxtzw.app.R;
import com.jxtzw.app.bean.Comment;
import com.jxtzw.app.common.StringUtils;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 评论列表的Adapter
 * @author MaRuoze
 *
 */
public class ListViewCommentAdapter extends BaseAdapter {
	private ArrayList<Comment> 	mComments;//数据集合
	private LayoutInflater 		mLayoutInflater;//视图容器
	private Resources 				mResources;//自定义项视图源 
	static class ListItemView{				//自定义控件集合  
	        public TextView username; 
		    public TextView dateline;  
		    public TextView message;
		    public ImageView headimage;
	 }  
	
	
	/**
	 * 构造
	 * @param mContext
	 * @param mComments
	 * @param mLayoutInflater
	 * @param mResources
	 */
	public ListViewCommentAdapter(Context mContext,
			ArrayList<Comment> mComments, LayoutInflater mLayoutInflater,
			Resources mResources) {
		super();
		this.mComments = mComments;
		this.mLayoutInflater = mLayoutInflater;
		this.mResources = mResources;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mComments.size();
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
		
		if (convertView == null) {
			//获取list_item布局文件的视图
			convertView = mLayoutInflater.inflate(mResources.getLayout(R.layout.item_comment), null);
			
			listItemView = new ListItemView();
			//获取控件对象
			listItemView.username = (TextView)convertView.findViewById(R.id.comment_user);
			listItemView.message= (TextView)convertView.findViewById(R.id.comment_message);
			listItemView.dateline= (TextView)convertView.findViewById(R.id.comment_dateline);
			listItemView.headimage= (ImageView)convertView.findViewById(R.id.comment_headimage);
			
			//设置控件集到convertView
			convertView.setTag(listItemView);
		}else {
			listItemView = (ListItemView)convertView.getTag();
		}	
		
		//设置文字和图片
		Comment comment = mComments.get(position);
		
		if(comment.getUsername().equals("")){
			comment.setUsername("游客");
		}
		listItemView.username.setText(comment.getUsername());
		listItemView.username.setTag(comment);//设置隐藏参数(实体类)
		listItemView.message.setText(comment.getMessage());
		listItemView.dateline.setText(StringUtils.timeStamp2Date(comment.getDateline(), "yyyy-MM-dd HH:mm"));
		//加载头像
		
		return convertView;
	}

}
