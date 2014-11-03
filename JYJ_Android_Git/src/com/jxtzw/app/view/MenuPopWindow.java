package com.jxtzw.app.view;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.jxtzw.app.AppContext;
import com.jxtzw.app.R;
import com.jxtzw.app.adapter.GridViewCollectionAdapter;
import com.jxtzw.app.adapter.GridViewShareAdapter;
import com.jxtzw.app.bean.Article;
import com.jxtzw.app.common.UIHelper;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract.Constants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.PopupWindow;

public class MenuPopWindow {
	/**
	 * UI
	 */
	private Context mContext;
	private AppContext mAppContext;
	private Resources mResources;
	private int mLayout;
	private LayoutInflater mLayoutInflater;
	private View mPopView;
	private PopupWindow mPop;
	private ArticleCommentView mArticleCommentView;
	
	/**
	 * 常量定义
	 */
	private static final int POP_SHARE=0;
	private static final int POP_COLLECTION=1;
	private static final int POP_COMMENT=2;
	private static final int POP_MODEL=3;
	
	/**
	 * 数据
	 */
	private Article mArticle;
	private String mComment;

	/**
	 * 构造
	 * @param mLayoutInflater
	 */
	public MenuPopWindow(Context context, AppContext appContext,Resources mResources,
			LayoutInflater mLayoutInflater, Article article) {
		this.mContext=context;
		this.mAppContext=appContext;
		this.mResources=mResources;
		this.mLayoutInflater = mLayoutInflater;
		this.mArticle=article;
	}


	/**
	 * 弹出菜单样式初始化
	 * @param layout
	 * @return
	 */
	@SuppressLint("InflateParams")
	public PopupWindow initPopWindow(int layout,int index,ArticleCommentView mArticleCommentView) {
		this.mLayout = layout;
		mPopView=mLayoutInflater.inflate(mLayout, null);
		mPop=new PopupWindow(mPopView);
		mPop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);  
		mPop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
		mPop.setOutsideTouchable(true);
		mPop.setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
		mPop.setFocusable(true);
		//防止虚拟软键盘被弹出菜单遮住
		mPop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		//根据index显示不同的PopWindow
		switch (index) {
			case POP_SHARE:
				initShare();
				break;
			case POP_COLLECTION:
				initCellection();
				break;
			case POP_COMMENT:
				this.mArticleCommentView=mArticleCommentView;
				initComment();
				break;
			case POP_MODEL:
				initModel();
				break;
		}
		return mPop;
	}
	
	
	/**
	 * 分享菜单
	 */
	private void initShare() {
		String[] titleStrings={
				"新浪微博","腾讯微博","QQ空间","微信","朋友圈","QQ","人人网","豆瓣","邮件"
		};
		int[] images={
				R.drawable.f001,R.drawable.f002,R.drawable.f003,
				R.drawable.f004,R.drawable.f005,R.drawable.f006,
				R.drawable.f007,R.drawable.f008,R.drawable.f009,
		};
		GridView shareGridView=(GridView) mPopView.findViewById(R.id.gridview_share);
		GridViewShareAdapter shareAdapter=new GridViewShareAdapter(mResources, 
				mLayoutInflater, titleStrings, images);
		shareGridView.setAdapter(shareAdapter);
	}
	
	
	/**
	 * 收藏菜单
	 */
	private void initCellection() {
		String[] titleStrings={
				"新浪微博","腾讯微博","QQ空间","微信","朋友圈","QQ","人人网","豆瓣","邮件"
		};
		GridView collectionGridView=(GridView) mPopView.findViewById(R.id.gridview_collection);
		GridViewCollectionAdapter collectionAdapter=new GridViewCollectionAdapter(mResources, 
				mLayoutInflater, titleStrings);
		collectionGridView.setAdapter(collectionAdapter);
	}
	
	
	/**
	 * 评论
	 */
	private void initComment() {
		ImageButton commentSubmit=(ImageButton) mPopView.findViewById(R.id.comment_submit);
		final EditText commentEditText=(EditText) mPopView.findViewById(R.id.comment_edit);
		commentSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mComment=commentEditText.getText().toString();
				//UIHelper.ToastMessage(mContext, mComment);
				if (mComment.length()<3) {
					UIHelper.ToastMessage(mContext, "至少要输入3个字符！");
					return;
				}else{
					commentSubmit();
				}
			}
		});
	}
	
	/**
	 * 评论提交
	 */
	private void commentSubmit() {
		//判断是否有可用网络
		if (!mAppContext.isNetworkConnected()) {
			//如果无可用网络则直接返回
			UIHelper.ToastMessage(mContext, R.string.network_not_connected);
			return;
		}
		String apiurl=mResources.getString(R.string.api_comment);
		AjaxParams ajaxParams=new AjaxParams();
		String actionString="0";
		String commentString="action|=|"+actionString+"|&|aid|=|"+
													mArticle.getAid()+"|&|message|=|"+mComment;
		ajaxParams.put("comment", commentString);
		final FinalHttp finalHttp=new FinalHttp();
		finalHttp.post(apiurl, ajaxParams, new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				if (t.equals("1")) {
					UIHelper.ToastMessage(mContext, "评论提交成功！");
					if(mArticleCommentView!=null){
						mArticleCommentView.update();
					}
				} else {
					UIHelper.ToastMessage(mContext, t);
				}
			}
		});
	}
	
	/**
	 * 模式
	 */
	private void initModel() {
		
	}
}
