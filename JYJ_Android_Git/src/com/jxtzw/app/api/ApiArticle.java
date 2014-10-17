package com.jxtzw.app.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalDb;
import com.jxtzw.app.bean.Article;
import android.content.Context;

public class ApiArticle extends ApiBase {
	/**
	 * 数据库相关操作
	 */
	 protected FinalDb mFinalDb;
	 protected String mDBName;
	
	/**
	 * 构造函数
	 * @param context
	 */
	public ApiArticle(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mDBName="jyj_articles";
		this.mFinalDb=FinalDb.create(mContext, mDBName);
	}

	/**
	 * 将JSON字符串转换为文章结构列表
	 * @return
	 */
	public ArrayList<Article> parseArticles(String mArticlesString) {
		ArrayList<Article> articles=new ArrayList<Article>();
		try {
			JSONArray jsonArray=new JSONArray(mArticlesString);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject=jsonArray.getJSONObject(i);
				Article article=new Article();
				article.setAid(jsonObject.getString("aid"));
				article.setCatid(jsonObject.getString("catid"));
				article.setAuthor(jsonObject.getString("author"));
				article.setDateLine(jsonObject.getString("dateline"));
				article.setTitle(jsonObject.getString("title"));
				article.setSummary(jsonObject.getString("summary"));
				article.setPic(jsonObject.getString("pic"));
				article.setUsername(jsonObject.getString("username"));
				//保存数据到本地数据库
				saveArticle(article);
				//保存数据到返回的数组列表
				articles.add(article);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return articles;
	}
	
	/**
	 * 存数据如数据库
	 */
	protected void saveArticle(Article article){
		String strWhere="aid in("+article.getAid()+")";
		ArrayList<Article> tpArticle=(ArrayList<Article>) mFinalDb.findAllByWhere(Article.class, strWhere);
		if(tpArticle.size()==0){
			mFinalDb.save(article);
		}
	}
	
	
	/**
	 * 从本地数据库缓存取数据
	 */
	public  ArrayList<Article> getArticlesLocal(String catID){
		String strWhere="Catid='"+catID+"'";
		ArrayList<Article> articles=(ArrayList<Article>) mFinalDb.findAllByWhere(Article.class, strWhere);
		return articles;
	}
}
