package com.jxtzw.app.adapter;

import java.util.ArrayList;
import java.util.Hashtable;

import net.tsz.afinal.FinalBitmap;

import com.jxtzw.app.R;
import com.jxtzw.app.adapter.ListViewArticleAdapter.ListItemView;
import com.jxtzw.app.bean.Article;
import com.jxtzw.app.bean.Quotation;
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

public class ListViewQuotationAdapter extends BaseAdapter {
	private Context 					mContext;//运行上下文
	private Hashtable<String, String> mRelatedData; //相关数据hash表
	private ArrayList<Quotation> 	mQuotations;//数据集合
	private LayoutInflater 		mLayoutInflater;//视图容器
	private Resources 				mResources;//自定义项视图源 
	static class ListItemView{				//自定义控件集合  
	        public TextView tv_chs_code;  
		    public TextView tv_quo_price;
		    public TextView tv_quo_open_price;  
		    public TextView tv_quo_close_price;
		    public TextView tv_quo_high_price;  
		    public TextView tv_quo_low_price;
		    public TextView tv_quo_time; 
	 }  
	
	
	
	public ListViewQuotationAdapter(Context mContext,
			ArrayList<Quotation> quotations,
			Resources mResources,Hashtable<String, String> mRelatedData) {
		super();
		this.mContext = mContext;
		this.mRelatedData = mRelatedData;
		this.mQuotations = quotations;
		this.mLayoutInflater = LayoutInflater.from(mContext);
		this.mResources = mResources;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mQuotations.size();
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
			convertView = mLayoutInflater.inflate(mResources.getLayout(R.layout.item_quotation), null);
			
			listItemView = new ListItemView();
			//获取控件对象
			listItemView.tv_chs_code = (TextView)convertView.findViewById(R.id.quot_title);
			listItemView.tv_quo_time= (TextView)convertView.findViewById(R.id.quot_time_time);
			listItemView.tv_quo_price= (TextView)convertView.findViewById(R.id.quot_qoutaiton);
			listItemView.tv_quo_open_price= (TextView)convertView.findViewById(R.id.quot_open_price);
			listItemView.tv_quo_close_price=(TextView) convertView.findViewById(R.id.quot_close_price);
			listItemView.tv_quo_high_price= (TextView)convertView.findViewById(R.id.quot_high_price);
			listItemView.tv_quo_low_price=(TextView) convertView.findViewById(R.id.quot_low_price);
			//设置控件集到convertView
			convertView.setTag(listItemView);
		}else {
			listItemView = (ListItemView)convertView.getTag();
		}	
		
		//设置文字和图片
		Quotation quotation = mQuotations.get(position);
		
		listItemView.tv_chs_code.setText(quotation.getQuo_chs_code());
		listItemView.tv_chs_code.setTag(quotation);//设置隐藏参数(实体类)
		listItemView.tv_quo_price.setText(StringUtils.float2Format(quotation.getQuo_price())+"  ");
		listItemView.tv_quo_open_price.setText("开盘:"+StringUtils.float2Format(quotation.getQuo_open_price()));
		listItemView.tv_quo_close_price.setText("昨收:"+StringUtils.float2Format(quotation.getQuo_close_price()));
		listItemView.tv_quo_high_price.setText("最高:"+StringUtils.float2Format(quotation.getQuo_high_price()));
		listItemView.tv_quo_low_price.setText("最低:"+StringUtils.float2Format(quotation.getQuo_low_price()));
		listItemView.tv_quo_time.setText(StringUtils.getNowString());
		
		float price_change=Float.parseFloat(quotation.getQuo_price_change());
		Drawable drawable=null;
		if(price_change>=0){
			drawable=mResources.getDrawable(R.drawable.upu);
		}else{
			drawable=mResources.getDrawable(R.drawable.upd);
		}
		drawable.setBounds(0, 0, 20, 20);
		listItemView.tv_quo_price.setCompoundDrawables(null, null, drawable, null);
		return convertView;
	}

}
