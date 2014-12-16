package com.jxtzw.app.handler;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.jxtzw.app.AppConfig;
import com.jxtzw.app.AppContext;
import com.jxtzw.app.R;
import com.jxtzw.app.api.ApiArticle;
import com.jxtzw.app.bean.Article;
import com.jxtzw.app.common.DataHelper;
import com.jxtzw.app.common.StringUtils;
import com.jxtzw.app.common.UIHelper;
import com.jxtzw.app.ui.MainActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

public class CheckNewsHandler extends Handler {
	/**
	 * 上下文与自身实例
	 */
	private Context mContext;
	private Service mService;
	private Resources mResources;
	private LayoutInflater mLayoutInflater;
	private CheckNewsHandler mThis;
	private AppContext mAppContext;			//APP全局上下文
	/**
	 * 计时器
	 */
	private Timer mTimer;
	private TimerTask mTimerTask;
	/**
	 * 测试用计数
	 */
	private int mCount=0;
	/**
	 * 数据
	 */
	private String[] mCatIDs;
	private String mCatID;
	private ApiArticle mApiArticle;						//数据接口
	private ArrayList<Article> mArticlesNew;
	private Article mArticleForNoti=null;
	/**
	 * 获取网络文章的起始位置
	 */
	private int mStart=0;
	private int mPageSize;
	/**
	 * 通知栏样式
	 */
	private RemoteViews mNotifyView;
	/**
	 * 是否第一次运行
	 */
	private boolean mIsFirst=true;
	
	
	public CheckNewsHandler(Context mContext) {
		super();
		this.mContext = mContext;
		this.mService=(Service) mContext;
		this.mResources=mService.getResources();
		this.mLayoutInflater=LayoutInflater.from(mContext);
		this.mThis=this;
		initMemberVar();
	}
	
	/**
	 * 初始化相关变量
	 */
	private void initMemberVar() {
		mCatIDs=DataHelper.getSubCat(mContext, AppConfig.JYJ_CAT_GOLD_ID_USERSET, 
				AppConfig.JYJ_CAT_GOLD_ID_DEFAULT, R.array.cat_gold_id);
		mCatID=mCatIDs[0];
		mArticlesNew=new ArrayList<Article>();
		mApiArticle=new ApiArticle(mContext);
		mAppContext=(AppContext) mService.getApplication();
		mPageSize=AppContext.PAGE_SIZE;
	}
	
	
	
	
	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub
		if (msg.what==0) {
			getLastNewsOnline();
		}
	}

	/**
	 * 获取线上新闻的最后更新时间
	 */
	private void getLastNewsOnline(){
		//判断是否有可用网络
		if (!mAppContext.isNetworkConnected()) {
			//如果无可用网络则直接返回
			UIHelper.ToastMessage(mContext, R.string.network_not_connected);
			return;
		}
		//请求API地址
		String apiurl=mResources.getString(R.string.api_articles);
		String query="vcode=jxtzw&cid="+mCatID+"&start="+mStart+"&limit="+mPageSize;
		final String APIURL=apiurl+"?"+query;
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
				//获取更新的数据
				mArticlesNew.clear();
				mArticlesNew=mApiArticle.parseArticles(t);
				//判断有数据更新后刷新UI
				String string="没有数据更新！";
				if (mArticlesNew.size()>0) {
					mArticleForNoti=mArticlesNew.get(0);
					sendNotification();
					string="数据更新！";
				}
				Log.v("Blur","调用次数："+mCount+" "+string);
			}
		});
	}
	
	
	/**
	 * 发送通知
	 */
	@SuppressWarnings("deprecation")
	private void sendNotification() {
		//获得通知管理器
        NotificationManager manager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        //构建一个通知对象(需要传递的参数有三个,分别是图标,标题和 时间)
        Notification notification = new Notification(R.drawable.ic_launcher,"通知",System.currentTimeMillis());
        Intent intent = new Intent(mContext,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext,0,intent,0);                                                                          
        //notification.setLatestEventInfo(mContext.getApplicationContext(), 
        		//mArticleForNoti.getTitle(), mArticleForNoti.getSummary(), pendingIntent);
        mNotifyView=new RemoteViews(mContext.getPackageName(), R.layout.item_notify);
        if(mArticleForNoti!=null){
	        mNotifyView.setImageViewResource(R.id.notify_image, R.drawable.ic_launcher);
	        mNotifyView.setTextViewText(R.id.notify_title, mArticleForNoti.getTitle());
	        mNotifyView.setTextViewText(R.id.notify_summary, mArticleForNoti.getSummary());
	        String timeString=StringUtils.timeStamp2Date(mArticleForNoti.getDateLine(),"HH:mm");
	        mNotifyView.setTextViewText(R.id.notify_dateline, timeString);
        }
        notification.contentView=mNotifyView;
        notification.flags = Notification.FLAG_AUTO_CANCEL;//点击后自动消失
        notification.defaults = Notification.DEFAULT_SOUND;//声音默认
        notification.contentIntent=pendingIntent;
        manager.notify(1, notification);			//发动通知,id由自己指定，每一个Notification对应的唯一标志
        //其实这里的id没有必要设置,只是为了下面要用到它才进行了设置
	}
	
	/**
	 * 新文章检查
	 */
	public void checkNews() {
		clearAll();
		mTimer=new Timer();
		mTimerTask=new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//getLastNewsLocal();
				//第一次运行时延时30秒，防止出现文章显示不了的bug
				if(mIsFirst){
					try {
						Thread.sleep(30000);
						mIsFirst=!mIsFirst;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				mCount++;
				mThis.sendEmptyMessage(0);
			}
		};
		int delay=1000*1800;									//查询新闻更新的事件间隔
		mTimer.schedule(mTimerTask, 0, delay);
	}
	
	/**
	 * 获取本地缓存新闻的最后更新时间
	 */
	/*private void getLastNewsLocal() {
		Article article=mApiArticle.getLastArticleLocal(mCatID);
		if(article!=null){
			mLastLocalTime=Integer.parseInt(article.getDateLine());
		}
	}*/
	
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
    	cleanTimerTask(mTimerTask, mTimer);
	}
	
}
