package com.jxtzw.app.view;

import java.lang.reflect.Field;

import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.jxtzw.app.AppConfig;
import com.jxtzw.app.AppContext;
import com.jxtzw.app.R;
import com.jxtzw.app.common.UIHelper;

import android.R.string;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

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
	private TextView mFBHintTextView;
	private DialogInterface mDialogInterface;
	/**
	 * 数据
	 */
	private String mFeedbackString;
	
	
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
		mFeedbackEditText=(EditText) mFeedbackView.findViewById(R.id.et_feedback);
		mFBHintTextView=(TextView) mFeedbackView.findViewById(R.id.tv_fbhint);
		mFeedbackEditText.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				mFeedbackString=mFeedbackEditText.getText().toString();
				int len=mFeedbackString.length();
				mFBHintTextView.setText(mResources.getString(R.string.feedback_txtlen)+len);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		mFeedbackDialog=new AlertDialog.Builder(mContext)
			.setTitle(R.string.feedback_title)
			.setView(mFeedbackView)
			.setPositiveButton(R.string.sure, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					mDialogInterface=dialog;
					setAlertDialogShow(dialog);
					feedbackSubmit();
				}
			})
			.setNegativeButton(R.string.cancle, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					setAlertDialogHide(dialog);
					dialog.dismiss();
				}
			})
			.create();
			mFeedbackDialog.show();
	}
	
	/**
	 * 意见和建议提交
	 */
	private void feedbackSubmit() {	
		mFeedbackString=mFeedbackEditText.getText().toString();
		Drawable drawable=mResources.getDrawable(R.drawable.error);
		drawable.setBounds(0, 0, 30, 30);  
		if (mFeedbackString.equals("")) {
			mFeedbackEditText.setError(mResources.getString(R.string.feedback_error0),drawable);
			mFeedbackEditText.requestFocus();
			return;
		}
		if(mFeedbackString.length()>240){
			mFeedbackEditText.setError(mResources.getString(R.string.feedback_error1),drawable);
			mFeedbackEditText.requestFocus();
			return;
		}
		submitProcess();
	}
	
	/**
	 * 意见回复发送过程
	 */
	private void submitProcess(){
		String url=mResources.getString(R.string.api_feedback);
		AjaxParams params=new AjaxParams();
		params.put("action", "feedback");
		params.put("uid", AppConfig.uid);
		String usernameString="";
		if (AppConfig.username.equals("")) {
			usernameString="游客";
		}else{
			usernameString=AppConfig.username;
		}
		params.put("username", usernameString);
		params.put("feedback", mFeedbackString);
		String verify_code=mResources.getString(R.string.verify_code);
		params.put("verify_code", verify_code);
		
		
		FinalHttp finalHttp=new FinalHttp();
		finalHttp.post(url, params, new AjaxCallBack<String>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
			}

			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				try {
					JSONObject jsonObject=new JSONObject(t);
					String resultString=jsonObject.getString("msg");
					if (resultString.equals("1")) {
						UIHelper.ToastMessage(mContext, mResources.getString(R.string.feedback_seccued));
						mThis.setAlertDialogHide(mDialogInterface);
						mFeedbackDialog.dismiss();
					}else{
						UIHelper.ToastMessage(mContext, mResources.getString(R.string.feedback_failure));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * 阻止AlertDialog关闭
	 */
	public void setAlertDialogShow(DialogInterface dialog) {
		try { 
			Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing"); 
			field.setAccessible(true); 
			field.set(dialog, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭AlertDialog
	 */
	public void setAlertDialogHide(DialogInterface dialog) {
		try {
			Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
			field.setAccessible(true);
			field.set(dialog, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
