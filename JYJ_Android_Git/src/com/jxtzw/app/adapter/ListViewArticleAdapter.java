package com.jxtzw.app.adapter;

import java.util.ArrayList;
import com.jxtzw.app.R;
import com.jxtzw.app.bean.Article;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 新闻资讯Adapter类
 * @author MaRuoze
 * @version 1.0
 * @created 2014-10-16
 */
public class ListViewArticleAdapter extends BaseAdapter {
	private Context 					mContext;//运行上下文
	private ArrayList<Article> 	mArticles;//数据集合
	private LayoutInflater 		mLayoutInflater;//视图容器
	private Resources 				mResources;//自定义项视图源 
	static class ListItemView{				//自定义控件集合  
	        public TextView title;  
		    public TextView author;
		    public TextView date;  
		    public TextView count;
		    public ImageView flag;
	 }  

	/**
	 * 实例化Adapter
	 * @param context
	 * @param data
	 * @param resource
	 */
	public ListViewArticleAdapter(Context context, ArrayList<Article> data,Resources resource) {
		this.mContext = context;			
		this.mLayoutInflater = LayoutInflater.from(context);	//创建视图容器并设置上下文
		this.mResources = resource;
		this.mArticles = data;
	}
	
	@Override
	public int getCount() {
		return mArticles.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}
	
	/**
	 * ListView Item设置
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//Log.d("method", "getView");
		
		//自定义视图
		ListItemView  listItemView = null;
		
		if (convertView == null) {
			//获取list_item布局文件的视图
			convertView = mLayoutInflater.inflate(mResources.getLayout(R.layout.item_article), null);
			
			listItemView = new ListItemView();
			//获取控件对象
			/*listItemView.title = (TextView)convertView.findViewById(R.id.news_listitem_title);
			listItemView.author = (TextView)convertView.findViewById(R.id.news_listitem_author);
			listItemView.count= (TextView)convertView.findViewById(R.id.news_listitem_commentCount);
			listItemView.date= (TextView)convertView.findViewById(R.id.news_listitem_date);
			listItemView.flag= (ImageView)convertView.findViewById(R.id.news_listitem_flag);*/
			
			//设置控件集到convertView
			convertView.setTag(listItemView);
		}else {
			listItemView = (ListItemView)convertView.getTag();
		}	
		
		//设置文字和图片
		Article article = mArticles.get(position);
		
		/*listItemView.title.setText(news.getTitle());
		listItemView.title.setTag(news);//设置隐藏参数(实体类)
		listItemView.author.setText(news.getAuthor());
		listItemView.date.setText(StringUtils.friendly_time(news.getPubDate()));
		listItemView.count.setText(news.getCommentCount()+"");
		if(StringUtils.isToday(news.getPubDate()))
			listItemView.flag.setVisibility(View.VISIBLE);
		else
			listItemView.flag.setVisibility(View.GONE);
		*/
		
		return convertView;
	}
}