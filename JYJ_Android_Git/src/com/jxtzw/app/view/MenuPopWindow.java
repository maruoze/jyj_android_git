package com.jxtzw.app.view;

import java.util.ArrayList;
import java.util.HashMap;

import net.tsz.afinal.FinalDb;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.jxtzw.app.AppConfig;
import com.jxtzw.app.AppContext;
import com.jxtzw.app.R;
import com.jxtzw.app.adapter.GridViewCollectionAdapter;
import com.jxtzw.app.adapter.GridViewShareAdapter;
import com.jxtzw.app.api.ApiCollectionEntry;
import com.jxtzw.app.bean.Article;
import com.jxtzw.app.bean.CollectionClassify;
import com.jxtzw.app.bean.CollectionEntry;
import com.jxtzw.app.common.ShareHelper;
import com.jxtzw.app.common.UIHelper;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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
	private EditText mCommentEditText;
	
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
	private String mCatName;
	private Article mArticle;
	private String mComment;
	private CollectionEntry mCollectionEntry;
	/**
	 * 数据接口
	 */
	private ApiCollectionEntry mApiCE;
	/**
	 * 登录状态
	 */
	protected SharedPreferences mSharedPreferences;
	protected SharedPreferences.Editor mEditor;

	/**
	 * 构造
	 * @param mLayoutInflater
	 */
	public MenuPopWindow(Context context, AppContext appContext,Resources mResources,
			LayoutInflater mLayoutInflater, Article article, String catName) {
		this.mContext=context;
		this.mAppContext=appContext;
		this.mResources=mResources;
		this.mLayoutInflater = mLayoutInflater;
		this.mArticle=article;
		this.mCatName=catName;
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
				"新浪微博","腾讯微博","更多"
		};
		int[] images={
				R.drawable.f001,R.drawable.f002,R.drawable.f003
		};
		GridView shareGridView=(GridView) mPopView.findViewById(R.id.gridview_share);
		GridViewShareAdapter shareAdapter=new GridViewShareAdapter(mResources, 
				mLayoutInflater, titleStrings, images);
		shareGridView.setAdapter(shareAdapter);
		shareGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				ShareHelper shareHelper=new ShareHelper(mContext);
				shareHelper.shareCase(parent, view, position, id,mArticle);
			}
		});
	}
	
	
	/**
	 * 收藏菜单
	 */
	private void initCellection() {
		//String[] titleStrings={"新浪微博","腾讯微博","QQ空间","微信","朋友圈","QQ","人人网","豆瓣","邮件"};
		//初始化数据
		final ArrayList<CollectionClassify> titleStrings=new ArrayList<CollectionClassify>();
		String dbName="jyj_collection";
		FinalDb finalDb=FinalDb.create(mContext,dbName);
		ArrayList<CollectionClassify> ccfy=new ArrayList<CollectionClassify>();
		//ccfy=(ArrayList<CollectionClassify>) finalDb.findAll(CollectionClassify.class);
		mSharedPreferences=AppConfig.getSharedPreferences(mContext);
		String uid=mSharedPreferences.getString(AppConfig.UID, "0");
		String strWhere="ccf_uid ='"+uid+"'";
		ccfy=(ArrayList<CollectionClassify>) finalDb.findAllByWhere(CollectionClassify.class, strWhere);
		if(ccfy.size()>0){
			for (int i = 0; i < ccfy.size(); i++) {
				titleStrings.add(ccfy.get(i));
			}
		}
		//设置数据适配器
		GridView collectionGridView=(GridView) mPopView.findViewById(R.id.gridview_collection);
		final GridViewCollectionAdapter collectionAdapter=new GridViewCollectionAdapter(mResources, 
				mLayoutInflater, titleStrings);
		collectionGridView.setAdapter(collectionAdapter);
		/**
		 * 项目单击
		 */
		collectionGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//UIHelper.ToastMessage(mContext, String.valueOf(position));
				mApiCE=new ApiCollectionEntry(mContext);
				mCollectionEntry=mApiCE.parse(mArticle,mCatName);
				CollectionClassify cClassify= (CollectionClassify) ((TextView)view.findViewById(R.id.textview_collection)).getTag();
				mCollectionEntry.setCcf_classify_id(cClassify.getCcf_classify_id());
				mCollectionEntry.setCcf_uid(cClassify.getCcf_uid());
				if(mApiCE.save(mCollectionEntry)){
					UIHelper.ToastMessage(mContext, "当前文章收藏到【"+cClassify.getCcf_classify_name()+"】成功！");
				}else{
					UIHelper.ToastMessage(mContext, "当前文章已经被收藏");
				}
				mPop.dismiss();
			}
		});
		
		/**
		 * 项目长按
		 */
		collectionGridView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				DialogColloctionOp dcOp=new DialogColloctionOp(mContext);
				dcOp.show(view,position,titleStrings,collectionAdapter);
				return true;
			}
		});
		
		
		
		
		//收藏菜单的新增按钮
		Button addCollectionClassify=(Button) mPopView.findViewById(R.id.collection_add);
		addCollectionClassify.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (titleStrings.size()>=AppConfig.COLLECT_CLASSIFY_MAX){
					String msg="最多只能创建"+AppConfig.COLLECT_CLASSIFY_MAX+"个收藏分类";
					UIHelper.ToastMessage(mContext, msg);
				}else{
					DialogCollection dialogCollection=new DialogCollection(mContext);
					dialogCollection.show(titleStrings,collectionAdapter,null,0);
				}
			}
		});
	}
	
	
	/**
	 * 评论
	 */
	private void initComment() {
		ImageButton commentSubmit=(ImageButton) mPopView.findViewById(R.id.comment_submit);
		mCommentEditText=(EditText) mPopView.findViewById(R.id.comment_edit);
		commentSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mComment=mCommentEditText.getText().toString();
				//UIHelper.ToastMessage(mContext, mComment);
				if (mComment.length()<3) {
					UIHelper.ToastMessage(mContext, "至少要输入3个字符！");
					//mPop.dismiss();
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
					mCommentEditText.setText("");
					if(mArticleCommentView!=null){
						mArticleCommentView.update();
					}
				} else {
					UIHelper.ToastMessage(mContext, t);
				}
				mPop.dismiss();
			}
		});
	}
	
	/**
	 * 模式
	 */
	private void initModel() {
		String[] titleStrings={"夜间模式"};
		//数据放入ArrayList
		ArrayList<HashMap<String, Object>> listItems=new ArrayList<HashMap<String,Object>>();
		for (int i = 0; i < titleStrings.length; i++) {
			HashMap<String, Object> itemMap=new HashMap<String, Object>();
			itemMap.put("title", titleStrings[i]);
			listItems.add(itemMap);
		}
		//初始化Adapter和ListView
		String[] dataIndex={"title"};
		int[] viewID={R.id.model_title};
		SimpleAdapter listViewAdapter=new SimpleAdapter(mContext, listItems, R.layout.item_model, dataIndex, viewID);
		ListView listView=(ListView) mPopView.findViewById(R.id.listview_model);
		listView.setAdapter(listViewAdapter);
	}
}
