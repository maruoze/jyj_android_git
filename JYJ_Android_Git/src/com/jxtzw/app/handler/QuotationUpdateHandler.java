package com.jxtzw.app.handler;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.jxtzw.app.adapter.ListViewQuotationAdapter;
import com.jxtzw.app.bean.Quotation;
import com.jxtzw.app.common.StringUtils;
import com.jxtzw.app.view.QuotationListView;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class QuotationUpdateHandler extends Handler {
	/**
	 * 上下文
	 */
	protected Context mContext;
	/**
	 * 数据相关
	 */
	protected ArrayList<Quotation> mQuotationsShow;
	/**
	 * UI相关
	 */
	protected ListViewQuotationAdapter mQuoLVAdapter;
	
	/**
	 * 时间更新相关
	 */
	protected Timer mTimeTimer;
	protected TimerTask mTimeTimerTask;
	/**
	 * 数据更新相关
	 */
	protected Timer mDataTimer;
	protected TimerTask mDataTimerTask;
	/**
	 * 当前类实例
	 */
	protected QuotationUpdateHandler mThis;
	/**
	 * 
	 */
	protected QuotationListView mQuotationListView;
	
	
	/**
	 * 构造
	 * @param mContext
	 * @param mQuotationsShow
	 * @param mQuoLVAdapter
	 */
	public QuotationUpdateHandler(Context mContext,
			ArrayList<Quotation> mQuotationsShow,
			ListViewQuotationAdapter mQuoLVAdapter,QuotationListView mQuotationListView) {
		super();
		this.mContext = mContext;
		this.mQuotationsShow = mQuotationsShow;
		this.mQuoLVAdapter = mQuoLVAdapter;
		this.mQuotationListView=mQuotationListView;
		this.mThis=this;
		//refreshTime();
	}
	

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		switch (msg.what) {
			case 0:
				mQuoLVAdapter.notifyDataSetChanged();
				break;
			case 1:
				mQuotationListView.getDataOnline();
				break;
			default:
				break;
				
		}
	}
	
	/**
	 * Time刷新
	 */
	public synchronized void refreshTime() {
		//清除旧的时间计时器
		cleanTimerTask(mTimeTimerTask, mTimeTimer);
		//开始新的时间计时器
		mTimeTimer=new Timer();
		mTimeTimerTask=new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String nowString=StringUtils.getNowString();
				if (mQuotationsShow.size()!=0) {
					mQuotationsShow.get(0).setQuo_time(nowString);
					mThis.sendEmptyMessage(0);
				} 
			}
		};
		mTimeTimer.schedule(mTimeTimerTask, 0, 1000);
	}
	
	/**
	 * 数据刷新
	 */
	public synchronized void refreshData() {
		//清除旧的数据计时器
		cleanTimerTask(mDataTimerTask, mDataTimer);
		//开启新的时间计时器
		mDataTimer=new Timer();
		mDataTimerTask=new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mThis.sendEmptyMessage(1);
			}
		};
		mDataTimer.schedule(mDataTimerTask, 0, 30000);
	}
	
	/**
     * 关闭掉Timer 与 TimerTask
     */
    private void cleanTimerTask(TimerTask tt,Timer t) {
        if (tt != null) {
            tt.cancel();
            tt = null;
        }
        if (t != null) {
            t.cancel();
            t.purge();
            t = null;
        }
    }
    
    /**
     * 关闭Timer和TimerTask
     */
    public void clearAll() {
    	cleanTimerTask(mTimeTimerTask, mTimeTimer);
    	cleanTimerTask(mDataTimerTask, mDataTimer);
	}
	
}
