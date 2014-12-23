package com.jxtzw.app.view;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.jxtzw.app.AppContext;
import com.jxtzw.app.R;
import com.jxtzw.app.adapter.ListViewQuotationAdapter;
import com.jxtzw.app.api.ApiQuotation;
import com.jxtzw.app.bean.Quotation;
import com.jxtzw.app.common.StringUtils;
import com.jxtzw.app.common.UIHelper;
import com.jxtzw.app.handler.QuotationUpdateHandler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

public class QuotationListView extends BaseView {
	/**
	 * 布局
	 */
	protected View mListView;
	/**
	 * 分类ID,数据
	 */
	protected Hashtable<String, String> mHashtable;
	protected String mTypeID;
	protected int mIndex;						//切换页索引
	/**
	 * ListView
	 */
	protected ListView mQuoListView;
	protected ListViewQuotationAdapter mQuoLVAdapter;
	/**
	 * 列表数据
	 */
	protected ApiQuotation mApiQuotation;
	protected ArrayList<Quotation> mQuotationsLocal;
	protected ArrayList<Quotation> mQuotationsShow;
	/**
	 * APP上下文
	 */
	protected AppContext mAppContext;
	/**
	 * 更新Handler
	 */
	protected QuotationUpdateHandler mQuotationUpdateHandler;
	protected QuotationListView mThis;
	protected ArrayList<QuotationUpdateHandler> mQuotationUpdateHandlers;
	
	
	
	/**
	 * 构造函数 
	 * @param context
	 * @param mListView
	 * @param mHashtable
	 * @param mIndex
	 */
	public QuotationListView(Context context, View listView,
			Hashtable<String, String> hashtable, int index) {
		super(context);
		this.mListView = listView;
		this.mHashtable = hashtable;
		this.mIndex = index;
		this.mThis=this;
		initMemberVar();
		initQuoListView();
	}
	
	/**
	 * 初始化成员变量
	 */
	protected void initMemberVar() {
		mQuotationsLocal=new ArrayList<Quotation>();
		mQuotationsShow=new ArrayList<Quotation>();
		mApiQuotation=new ApiQuotation(mContext);
		mTypeID=mHashtable.get("mCatShowQuo");
		mAppContext=(AppContext) mActivity.getApplication();
	}
	
	/**
	 * 初始化listView
	 */
	protected void initQuoListView() {
		mQuoLVAdapter=new ListViewQuotationAdapter(mContext, mQuotationsShow,
																						mResources, mHashtable);
		mQuoListView=(ListView) mListView.findViewById(R.id.lv_quotation);
		//mQuoListView.setVisibility(View.VISIBLE);
		mQuoListView.setAdapter(mQuoLVAdapter);
	}
	
	/**
	 * 对外的数据刷新接口
	 */
	public void refreshQuo(ArrayList<QuotationUpdateHandler> quh) {
		mQuotationUpdateHandlers=quh;
		updateData();
		refreshQuotation();
	}
	
	
	/**
	 * 更新数据
	 */
	protected void updateData() {
		//获取本地缓存
		boolean flag=getLocalCache();
		if (flag) {
			copyLocalToShow();
			mQuoLVAdapter.notifyDataSetChanged();
			if (mQuoListView.getVisibility()==View.GONE) {
				mQuoListView.setVisibility(View.VISIBLE);
			}
		}
		//更新数据
		getDataOnline();
	}
	
	/**
	 * 获取本地数据
	 */
	protected boolean getLocalCache(){
		boolean flag=false;
		mQuotationsLocal.clear();
		mQuotationsLocal=mApiQuotation.getQuotationLocal(mTypeID);
		if (mQuotationsLocal.size()!=0) {
			flag=true;
		}
		return flag;
	}
	
	/**
	 * 获取网络数据
	 */
	public void getDataOnline() {
		//判断是否有可用网络
		if (!mAppContext.isNetworkConnected()) {
			//如果无可用网络则直接返回
			return;
		}
		//请求API地址
		String apiurl=mResources.getString(R.string.api_quotation_goldsilver);
		String query="type="+mTypeID;
		final String APIURL=apiurl+"?"+query;
		final FinalHttp finalHttp=new FinalHttp();
		finalHttp.get(APIURL, new AjaxCallBack<String>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
			}

			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				if(!t.isEmpty()){
					mApiQuotation.parseQuotation(t);
					if(getLocalCache()){
						copyLocalToShow();
						mQuoLVAdapter.notifyDataSetChanged();
						if (mQuoListView.getVisibility()==View.GONE) {
							mQuoListView.setVisibility(View.VISIBLE);
						}
					}
				}
			}
			
		});
	}
	
	/**
	 * 更新与ListView绑定的数据
	 * 
	 */
	protected void copyLocalToShow(){
		mQuotationsShow.clear();
		mQuotationsShow.addAll(mQuotationsLocal);
	}
	
	/**
	 * 更新行情显示
	 */
	protected void refreshQuotation(){
		//开启新计时器
		mQuotationUpdateHandler=new QuotationUpdateHandler(mContext, mQuotationsShow, mQuoLVAdapter,mThis);
		//开启行情更新
		mQuotationUpdateHandler.refreshTime();
		mQuotationUpdateHandler.refreshData();
		//保存计时器Handler
		mQuotationUpdateHandlers.set(mIndex, mQuotationUpdateHandler);
	}
}
