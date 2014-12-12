package com.jxtzw.app.view;

import com.jxtzw.app.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AboutusDialog extends BaseView {
	
	/**
	 * 关于我们对话框
	 */
	private AlertDialog mAboutUSDialog;
	private View mAboutUSView;
	private TextView mVersion;
    //版本信息
    private String mCurVersionName="";
	
	public AboutusDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 意见与建议
	 */
	@SuppressLint("InflateParams")
	public void show() {
		mAboutUSView=mLayoutInflater.inflate(R.layout.dialog_aboutus, null);
		mVersion=(TextView) mAboutUSView.findViewById(R.id.aboutus_version);
		getCurrentVersion();
		String versionString=mResources.getString(R.string.version)+mCurVersionName;
		mVersion.setText(versionString);
		mAboutUSDialog=new AlertDialog.Builder(mContext)
			.setTitle(R.string.aboutus_title)
			.setView(mAboutUSView)
			.setPositiveButton(R.string.sure, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			})
			.create();
		mAboutUSDialog.show();
	}
	
	/**
	 * 获取当前客户端版本信息
	 */
	private void getCurrentVersion(){
        try { 
        	PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        	mCurVersionName = info.versionName;
        } catch (NameNotFoundException e) {    
			e.printStackTrace(System.err);
		} 
	}
}
