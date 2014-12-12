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
				article.setCatid(jsonObject.getInt("catid"));
				article.setAuthor(jsonObject.getString("author"));
				article.setDateLine(jsonObject.getString("dateline"));
				article.setTitle(jsonObject.getString("title"));
				article.setSummary(jsonObject.getString("summary"));
				article.setPic(jsonObject.getString("pic"));
				article.setUsername(jsonObject.getString("username"));
				//保存数据到本地数据库
				if(saveArticle(article)){
				//保存数据到返回的数组列表
					articles.add(article);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return articles;
	}
	
	/**
	 * 存数据入数据库
	 */
	protected synchronized boolean  saveArticle(Article article){
		boolean flag=false;
		String strWhere="Aid in("+article.getAid()+")";
		ArrayList<Article> tpArticle=(ArrayList<Article>) mFinalDb.findAllByWhere(Article.class, strWhere);
		if( tpArticle.size()==0 ){
			mFinalDb.save(article);
			flag=true;
		}
		return flag;
	}
	
	
	/**
	 * 从本地数据库缓存取数据
	 */
	public  ArrayList<Article> getArticlesLocal(String catID){
		String strWhere="Catid in("+catID+")";
		String strOrder="Dateline DESC";
		ArrayList<Article> articles=(ArrayList<Article>) mFinalDb.findAllByWhere(Article.class, strWhere,strOrder);
		return articles;
	}
	
	/**
	 * 从本地数据库缓存取数据【去除当前显示数据】
	 */
	public  ArrayList<Article> getArticlesLocalEx(String catID,String aid){
		String strWhere="Catid IN("+catID+") AND Aid NOT IN("+aid+")";
		String strOrder="Dateline DESC";
		ArrayList<Article> articles=(ArrayList<Article>) mFinalDb.findAllByWhere(Article.class, strWhere,strOrder);
		return articles;
	}
	
	
	/**
	 * 文章内容部分
	 */
	public ArrayList<Article> getArticleContent(String mArticlesString,Article article) {
		ArrayList<Article> articles=new ArrayList<Article>();
		try {
			JSONArray jsonArray=new JSONArray(mArticlesString);
			if (jsonArray.length()==1) {
				JSONObject jsonObject=jsonArray.getJSONObject(0);
				article.setContents(jsonObject.getString("content"));
				//保存数据到本地数据库
				if(updateArticle(article)){
				//保存数据到返回的数组列表
					articles.add(article);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return articles;	
	}
	
	/**
	 * 更新文章
	 */
	protected boolean updateArticle(Article article){
		boolean flag=false;
		String strWhere="Aid in("+article.getAid()+")";
		ArrayList<Article> tpArticle=(ArrayList<Article>) mFinalDb.findAllByWhere(Article.class, strWhere);
		if (tpArticle.size()==1) {
			mFinalDb.update(article, strWhere);
			flag=true;
		}
		return flag;
	}
	
	/**
	 * 根据文章ID获取文章
	 */
	public Article getArticleByAid(String aid) {
		Article article=null;
		String strWhere="Aid ='"+aid+"'";
		ArrayList<Article> articles=(ArrayList<Article>) mFinalDb.findAllByWhere(Article.class, strWhere);
		if (articles.size()==1) {
			article=articles.get(0);
		}
		return article;
	}
	
	/**
	 * 从本地数据库获取当类最新的一条信息
	 */
	public  Article getLastArticleLocal(String catID){
		Article article=null;
		String strWhere="Catid in("+catID+")";
		String strOrder="Dateline DESC LIMIT 0,1";
		ArrayList<Article> articles=(ArrayList<Article>) mFinalDb.findAllByWhere(Article.class, strWhere,strOrder);
		if (articles.size()>0) {
			article=articles.get(0);
		}
		return article;
	}
}
