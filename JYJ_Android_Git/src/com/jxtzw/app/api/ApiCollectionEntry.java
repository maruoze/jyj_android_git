package com.jxtzw.app.api;

import java.util.ArrayList;

import com.jxtzw.app.bean.Article;
import com.jxtzw.app.bean.CollectionEntry;

import android.content.Context;
import net.tsz.afinal.FinalDb;

public class ApiCollectionEntry extends ApiBase{
	/**
	 * 数据库相关操作
	 */
	protected String mDBName;
	protected FinalDb mFinalDb;
	
	/**
	 * 构造函数
	 * @param context
	 */
	public ApiCollectionEntry(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mDBName="jyj_collection";
		this.mFinalDb=FinalDb.create(mContext, mDBName);
	}
	
	/**
	 * 数据类型转换
	 */
	public CollectionEntry parse(Article article) {
		CollectionEntry ce=null;
		if (article!=null) {
			ce=new CollectionEntry();
			ce.setAid(article.getAid());
			ce.setAllowcomment(article.getAllowcomment());
			ce.setAuthor(article.getAuthor());
			ce.setBid(article.getBid());
			ce.setCatid(article.getCatid());
			ce.setClick1(article.getClick1());
			ce.setClick2(article.getClick2());
			ce.setClick3(article.getClick3());
			ce.setClick4(article.getClick4());
			ce.setClick5(article.getClick5());
			ce.setClick6(article.getClick6());
			ce.setClick7(article.getClick7());
			ce.setClick8(article.getClick8());
			ce.setContents(article.getContents());
			ce.setDateLine(article.getDateLine());
			ce.setHighlight(article.getHighlight());
			ce.setIdtype(article.getIdtype());
			ce.setOwncomment(article.getOwncomment());
			ce.setPic(article.getPic());
			ce.setPrename(article.getPrename());
			ce.setPreurl(article.getPreurl());
			ce.setRemote(article.getRemote());
			ce.setShorttitle(article.getShorttitle());
			ce.setStatus(article.getStatus());
			ce.setSummary(article.getSummary());
			ce.setTag(article.getTag());
			ce.setShowinnernav(article.getShowinnernav());
			ce.setThumb(article.getThumb());
			ce.setTitle(article.getTitle());
			ce.setUid(article.getUid());
			ce.setUrl(article.getUrl());
			ce.setUsername(article.getUsername());
		}
		return ce;
	}
	
	/**
	 * 数据新增
	 */
	public boolean save(CollectionEntry ce) {
		boolean flag=false;
		//String strWhere="Aid in("+ce.getAid()+")";
		//ArrayList<Article> tpArticle=(ArrayList<Article>) mFinalDb.findAllByWhere(Article.class, strWhere);
		String idString=ce.getAid();
		try {
			String strWhere="Aid in("+idString+") AND ccf_uid in("+ce.getCcf_uid()+")";
			ArrayList<CollectionEntry> cEntries=(ArrayList<CollectionEntry>) mFinalDb.findAllByWhere(CollectionEntry.class, strWhere);
			//CollectionEntry cEntry=mFinalDb.findById(idString, CollectionEntry.class);
			if(cEntries.size()==0){
			//if(cEntry==null ){
				mFinalDb.save(ce);
				flag=true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
		}
		return flag;
	}
	
	/**
	 * 数据修改
	 */
	public boolean update(CollectionEntry ce) {
		return false;
		
	}
	
	/**
	 * 数据删除
	 */
	public boolean delete(CollectionEntry ce) {
		return false;
	}
}
