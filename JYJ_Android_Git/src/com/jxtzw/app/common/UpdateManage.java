package com.jxtzw.app.common;

import java.io.File;

import com.jxtzw.app.R;
import com.jxtzw.app.bean.AppUpdate;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.HttpHandler;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class UpdateManage {
	/**
	 * 当前类实例句柄
	 */
	private Context mContext;
	private Resources mResources;
	/**
	 * 唯一实例
	 */
	private static UpdateManage mUpdateManage;
	//终止标记
    private boolean mInterceptFlag;
    //版本信息
    private String mCurVersionName;
    private int mCurVersionCode;
    /**
     * UI
     */
    private ProgressDialog mProDialog;	//检查新版本时的进度条
    private Dialog mLatestOrFailDialog;	//已经是最新版本或者无法获取最新版本信息的对话框
	//通知对话框
	private Dialog mNoticeDialog;
	//下载对话框
	private Dialog downloadDialog;
    //进度条
    private ProgressBar mProgress;
    //显示下载数值
    private TextView mProgressText;
    private HttpHandler<File> mDownLoadHandler;
    
    /**
     * 数据
     */
    private AppUpdate mAppUpdate;
    /**
     * 常量定义
     */
    private static final int DIALOG_TYPE_LATEST = 0;
    private static final int DIALOG_TYPE_FAIL   = 1;
    /**
     * 
     */
    private String mUpdateLog="";
	
    /**
     * 单例模式，返回当前类唯一实例
     */
	public static UpdateManage getUpdateManage() {
		if (mUpdateManage==null) {
			mUpdateManage=new UpdateManage();
		} 
		mUpdateManage.mInterceptFlag=false;
		return mUpdateManage;
	}
	
	public void checkAppUpdate(Context context, final boolean isShow) {
		this.mContext=context;
		this.mResources=mContext.getResources();
		
		getCurrentVersion();
		if (isShow) {
			if(mProDialog==null){	
				mProDialog=ProgressDialog.show(mContext, null, "检测中，请稍后......", true, true);
			} else if(mProDialog.isShowing() || (mLatestOrFailDialog!=null && mLatestOrFailDialog.isShowing())){
				return;
			}
		}
		
		String url=mResources.getString(R.string.api_apk_version_check);
		FinalHttp finalHttp=new FinalHttp();
		finalHttp.get(url, new AjaxCallBack<String>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
			}

			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				//关闭并释放释放进度条对话框
				if(isShow && mProDialog != null){
					mProDialog.dismiss();
					mProDialog = null;
				}
				mAppUpdate=AppUpdate.parse(t);
				if (mCurVersionCode<mAppUpdate.getVersionCode()) {
					mUpdateLog=mAppUpdate.getUpdateLog();
					showNoticeDialog();
				} else {
					showLatestOrFailDialog(DIALOG_TYPE_LATEST);
				}
			}
		});
	}
	
	/**
	 * 获取当前客户端版本信息
	 */
	private void getCurrentVersion(){
        try { 
        	PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        	mCurVersionName = info.versionName;
        	mCurVersionCode = info.versionCode;
        } catch (NameNotFoundException e) {    
			e.printStackTrace(System.err);
		} 
	}
	
	/**
	 * 显示'已经是最新'或者'无法获取版本信息'对话框
	 */
	private void showLatestOrFailDialog(int dialogType) {
		if (mLatestOrFailDialog != null) {
			//关闭并释放之前的对话框
			mLatestOrFailDialog.dismiss();
			mLatestOrFailDialog = null;
		}
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("系统提示");
		if (dialogType == DIALOG_TYPE_LATEST) {
			builder.setMessage("您当前已经是最新版本");
		} else if (dialogType == DIALOG_TYPE_FAIL) {
			builder.setMessage("无法获取版本更新信息");
		}
		builder.setPositiveButton("确定", null);
		mLatestOrFailDialog = builder.create();
		mLatestOrFailDialog.show();
	}
	
	/**
	 * 显示版本更新通知对话框
	 */
	private void showNoticeDialog(){
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("软件版本更新");
		builder.setMessage(mUpdateLog);
		builder.setPositiveButton("立即更新", new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				showDownloadDialog();			
			}
		});
		builder.setNegativeButton("以后再说", new OnClickListener() {			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();				
			}
		});
		mNoticeDialog = builder.create();
		mNoticeDialog.show();
	}
	
	/**
	 * 显示下载对话框
	 */
	private void showDownloadDialog(){
		AlertDialog.Builder builder = new Builder(mContext);
		builder.setTitle("正在下载新版本");
		
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.update_progress, null);
		mProgress = (ProgressBar)v.findViewById(R.id.update_progress);
		mProgressText = (TextView) v.findViewById(R.id.update_progress_text);
		
		builder.setView(v);
		builder.setNegativeButton("取消", new OnClickListener() {	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				mInterceptFlag = true;
			}
		});
		builder.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				dialog.dismiss();
				mInterceptFlag = true;
			}
		});
		downloadDialog = builder.create();
		downloadDialog.setCanceledOnTouchOutside(false);
		downloadDialog.show();
		downloadApk();
	}
	
	/**
	 * 下载APP
	 */
	private void downloadApk(){
		FinalHttp finalHttp=new FinalHttp();
		String url="";
		String target="";
		mDownLoadHandler=finalHttp.download(url, target, new AjaxCallBack<File>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
			}

			@Override
			public void onSuccess(File t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
			}

			@Override
			public void onLoading(long count, long current) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
}
