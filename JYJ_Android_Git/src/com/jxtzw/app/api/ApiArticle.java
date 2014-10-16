package com.jxtzw.app.api;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.jxtzw.app.bean.Article;
import com.jxtzw.app.callback.ArticleAjaxCallBack;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class ApiArticle extends ApiBase {
	/**
	 * 用于获取数据的Api地址和请求信息
	 */
	protected String mAPIURL;
	protected String mQuery;
	/**
	 * 数据
	 */
	protected String mArticlesString;
	
	/**
	 * 构造函数
	 * @param context
	 */
	public ApiArticle(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 初始化
	 * @param apiurl
	 * @param query
	 */
	public void init(String apiurl,String query) {
		this.mAPIURL=apiurl;
		this.mQuery=query;
	}
	
	/**
	 * 返回文章列表数据
	 * @param method
	 * @return
	 */
	public ArrayList<Article> getArticles(boolean method) {
		ArrayList<Article> articles=new ArrayList<Article>();
		if (method) {
			getArticleString();
		}else{
			postArticleString();
		}
		//articles=parseArticles();
		return articles;
	}
	
	
	
	/**
	 * 使用get方法获取数据
	 * @return
	 */
	protected void getArticleString(){
		final String APIURL=mAPIURL+"?"+mQuery;
		final FinalHttp finalHttp=new FinalHttp();
		// TODO Auto-generated method stub
		finalHttp.get(APIURL, new AjaxCallBack<String>(){
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
			}

			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	/**
	 * 使用post方法获取数据
	 * @return
	 */
	protected void postArticleString(){}

	/**
	 * 将JSON字符串转换为文章结构列表
	 * @return
	 */
	protected ArrayList<Article> parseArticles() {
		ArrayList<Article> articles=new ArrayList<Article>();
		try {
			JSONObject jsonObject=new JSONObject(mArticlesString);
			Log.v("OK", "OK");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return articles;
	}
	
}
