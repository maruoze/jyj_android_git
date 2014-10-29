package com.jxtzw.app.adapter;

import java.util.ArrayList;
import java.util.Hashtable;

import net.tsz.afinal.FinalBitmap;

import com.jxtzw.app.R;
import com.jxtzw.app.bean.Article;
import com.jxtzw.app.common.StringUtils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
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
	private Hashtable<String, String> mRelatedData; //相关数据hash表
	private ArrayList<Article> 	mArticles;//数据集合
	private LayoutInflater 		mLayoutInflater;//视图容器
	private Resources 				mResources;//自定义项视图源 
	static class ListItemView{				//自定义控件集合  
	        public TextView title;  
		    public TextView summary;
		    public TextView dateline;  
		    public TextView catname;
		    public ImageView pic;
	 }  

	/**
	 * 实例化Adapter
	 * @param context
	 * @param data
	 * @param resource
	 */
	public ListViewArticleAdapter(Context context, ArrayList<Article> data,
			Resources resource, Hashtable<String, String> hashtable) {
		this.mContext = context;			
		this.mLayoutInflater = LayoutInflater.from(context);	//创建视图容器并设置上下文
		this.mResources = resource;
		this.mArticles = data;
		this.mRelatedData=hashtable;
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
			listItemView.title = (TextView)convertView.findViewById(R.id.article_title);
			listItemView.summary= (TextView)convertView.findViewById(R.id.article_summary);
			listItemView.dateline= (TextView)convertView.findViewById(R.id.article_dateline);
			listItemView.pic= (ImageView)convertView.findViewById(R.id.article_pic_iv);
			listItemView.catname=(TextView) convertView.findViewById(R.id.article_catname);
			
			//设置控件集到convertView
			convertView.setTag(listItemView);
		}else {
			listItemView = (ListItemView)convertView.getTag();
		}	
		
		//设置文字和图片
		Article article = mArticles.get(position);
		
		listItemView.title.setText(StringUtils.replaceHTML(article.getTitle()));
		listItemView.title.setTag(article);//设置隐藏参数(实体类)
		listItemView.summary.setText(StringUtils.replaceHTML(article.getSummary()));
		listItemView.dateline.setText(" "+StringUtils.timeStamp2Date(article.getDateLine(), "yyyy-MM-dd HH:mm"));
		listItemView.catname.setText(" "+mRelatedData.get("mCatName"));
		if(StringUtils.isTodayEx(article.getDateLine())){
			Drawable drawable=mResources.getDrawable(R.drawable.f026);
			drawable.setBounds(0, 0, 20, 20);
			listItemView.title.setCompoundDrawables(drawable, null, null, null);
		}else{
			listItemView.title.setCompoundDrawables(null, null, null, null);
		}
		//设置图片
		String imageBaseURL=mResources.getString(R.string.image_base_url);
		String picString=article.getPic();
		boolean isShowImage=mRelatedData.get("mCatShowImages").equals("1");
		if(!picString.equals("")&&isShowImage){
			FinalBitmap fb=FinalBitmap.create(mContext);
			String imageURL=imageBaseURL+article.getPic();
			fb.display(listItemView.pic, imageURL);
			listItemView.pic.setVisibility(View.VISIBLE);
		}
		return convertView;
	}
}