package com.jxtzw.app.api;

import java.util.ArrayList;

import net.tsz.afinal.FinalDb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.jxtzw.app.bean.Article;
import com.jxtzw.app.bean.Quotation;

public class ApiQuotation extends ApiBase {
	/**
	 * 数据库相关操作
	 */
	 protected FinalDb mFinalDb;
	 protected String mDBName;
	
	/**
	 * 构造函数
	 * @param context
	 */
	public ApiQuotation(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mDBName="jyj_quotations";
		this.mFinalDb=FinalDb.create(mContext, mDBName);
	}

	/**
	 * 将JSON字符串转换为文章结构列表
	 * @return
	 */
	public ArrayList<Quotation> parseQuotation(String QuotaionString) {
		ArrayList<Quotation> quotations=new ArrayList<Quotation>();
		try {
			//JSONArray jsonArray=new JSONArray(QuotaionString);
			//for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject=new JSONObject(QuotaionString);
				Quotation quotation=new Quotation();
				quotation.setQuo_chs_code(jsonObject.getString("chs_code"));
				quotation.setQuo_price(jsonObject.getString("price"));
				quotation.setQuo_time(jsonObject.getString("time"));
				quotation.setQuo_open_price(jsonObject.getString("open_price"));
				quotation.setQuo_close_price(jsonObject.getString("close_price"));
				quotation.setQuo_high_price(jsonObject.getString("high_price"));
				quotation.setQuo_low_price(jsonObject.getString("low_price"));
				quotation.setQuo_price_change(jsonObject.getString("price_change"));
				quotation.setQuo_type_id(jsonObject.getString("type_id"));
				
				//保存数据到本地数据库
				if(saveQuotation(quotation)){
				//保存数据到返回的数组列表
					quotations.add(quotation);
				}
			//}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return quotations;
	}
	
	/**
	 * 存数据如数据库
	 */
	protected boolean saveQuotation(Quotation quotation){
		boolean flag=false;
		//删除同类数据
		String strWhere="quo_type_id='"+quotation.getQuo_type_id()+"'";
		mFinalDb.deleteByWhere(Quotation.class, strWhere);
		mFinalDb.save(quotation);
		flag=true;
		return flag;
	}
	
	
	/**
	 * 从本地数据库缓存取数据
	 */
	public  ArrayList<Quotation> getQuotationLocal(String type_id){
		String strWhere="quo_type_id='"+type_id+"'";
		String strOrder="quo_time DESC";
		ArrayList<Quotation> quotations=(ArrayList<Quotation>) mFinalDb.findAllByWhere(Quotation.class, strWhere,strOrder);
		return quotations;
	}
}
