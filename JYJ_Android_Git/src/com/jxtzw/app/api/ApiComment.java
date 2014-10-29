package com.jxtzw.app.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jxtzw.app.bean.Comment;
import net.tsz.afinal.FinalDb;
import android.content.Context;

public class ApiComment extends ApiBase {
	/**
	 * 数据库相关操作
	 */
	protected FinalDb mFinalDb;
	protected String mDBName;
	
	public ApiComment(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.mDBName="jyj_comment";
		this.mFinalDb=FinalDb.create(mContext, mDBName);
	}

	/**
	 * 将JSON字符串转换为文章结构列表
	 * @return
	 */
	public ArrayList<Comment> parseComments(String mCommentsString) {
		ArrayList<Comment> comments=new ArrayList<Comment>();
		try {
			JSONArray jsonArray=new JSONArray(mCommentsString);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject=jsonArray.getJSONObject(i);
				Comment comment=new Comment();
				comment.setAid(jsonObject.getString("id"));
				comment.setUid(jsonObject.getString("uid"));
				comment.setUsername(jsonObject.getString("username"));
				comment.setDateline(jsonObject.getString("dateline"));
				comment.setPostip(jsonObject.getString("postip"));
				comment.setStatus(jsonObject.getString("status"));
				comment.setMessage(jsonObject.getString("message"));
				comment.setIdtype(jsonObject.getString("idtype"));
				//保存数据到本地数据库
				if(saveComment(comment)){
				//保存数据到返回的数组列表
					comments.add(comment);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comments;
	}
	
	/**
	 * 存数据入数据库
	 */
	protected boolean saveComment(Comment comment){
		boolean flag=false;
		String strWhere="Cid in("+comment.getCid()+")";
		ArrayList<Comment> tpComment=(ArrayList<Comment>) mFinalDb.findAllByWhere(Comment.class, strWhere);
		if( tpComment.size()==0 ){
			mFinalDb.save(comment);
			flag=true;
		}
		return flag;
	}
	
	
	/**
	 * 从本地数据库缓存取数据
	 */
	public  ArrayList<Comment> getCommentsLocal(String aid){
		String strWhere="Aid in("+aid+")";
		String strOrder="Dateline DESC";
		ArrayList<Comment> comments=(ArrayList<Comment>) mFinalDb.findAllByWhere(Comment.class, strWhere,strOrder);
		return comments;
	}
	
	/**
	 * 从本地数据库缓存取数据【去除当前显示数据】
	 */
	public  ArrayList<Comment> getCommentsLocalEx(String catID,String aid){
		String strWhere="Catid IN("+catID+") AND Aid NOT IN("+aid+")";
		String strOrder="Dateline DESC";
		ArrayList<Comment> comments=(ArrayList<Comment>) mFinalDb.findAllByWhere(Comment.class, strWhere,strOrder);
		return comments;
	}
	
}
