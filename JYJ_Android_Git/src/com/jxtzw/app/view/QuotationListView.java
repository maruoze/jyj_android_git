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

import android.content.Context;
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
	 * 时间计时器
	 */
	protected Timer mTimeTimer;
	protected TimerTask mTimeTimerTask;
	
	
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
		mQuoListView.setVisibility(View.VISIBLE);
		mQuoListView.setAdapter(mQuoLVAdapter);
		updateData();
	}
	
	/**
	 * 更新数据
	 */
	protected void updateData() {
		boolean flag=getLocalCache();
		if (flag) {
			copyLocalToShow();
			mQuoLVAdapter.notifyDataSetChanged();
		}
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
			refreshTime();
			flag=true;
		}
		return flag;
	}
	
	/**
	 * 获取网络数据
	 */
	protected void getDataOnline() {
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
				mApiQuotation.parseQuotation(t);
				getLocalCache();
				copyLocalToShow();
				mQuoLVAdapter.notifyDataSetChanged();
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
	 * 更新时间
	 */
	protected void refreshTime(){
		mTimeTimer=new Timer();
		mTimeTimerTask=new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String nowString=StringUtils.getNowString();
				mQuotationsShow.get(0).setQuo_time(nowString);
				mQuoLVAdapter.notifyDataSetChanged();
			}
		};
		mTimeTimer.schedule(mTimeTimerTask, 0, 1000);
	}
}
