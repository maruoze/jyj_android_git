package com.jxtzw.app.view;

import com.jxtzw.app.AppContext;
import com.jxtzw.app.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.EditText;

public class FeedbackDialog extends BaseView {
	private AppContext mAppContext;
	/**
	 * 当前类实例家句柄
	 */
	private FeedbackDialog mThis;
	/**
	 * 回执对话框
	 */
	private AlertDialog mFeedbackDialog;
	private View mFeedbackView;
	private EditText mFeedbackEditText;
	
	
	public FeedbackDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mAppContext=(AppContext) mActivity.getApplication();
		mThis=this;
	}

	/**
	 * 意见与建议
	 */
	@SuppressLint("InflateParams")
	public void show() {
		mFeedbackView=mLayoutInflater.inflate(R.layout.dialog_feedback, null);
		mFeedbackDialog=new AlertDialog.Builder(mContext)
			.setTitle(R.string.feedback_title)
			.setView(mFeedbackView)
			.setPositiveButton(R.string.sure, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			})
			.setNegativeButton(R.string.cancle, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			})
			.create();
			mFeedbackDialog.show();
	}
	
	/**
	 * 
	 */
}
