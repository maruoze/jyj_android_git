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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.provider.Contacts.Intents.UI;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class LoginDialog extends BaseView {
	private AppContext mAppContext;
	/**
	 * 当前类实例家句柄
	 */
	private LoginDialog mThis;
	/**
	 * 登录对话框
	 */
	private AlertDialog mLoginDialog;
	private View mLoginView;
	private EditText mUsername;
	private EditText mPassword;
	private String mStrUserName;
	private String mStrPassword;
	private DialogInterface mDialogInterface;
	/**
	 * 登录状态
	 */
	private SharedPreferences mSharedPreferences;
	private SharedPreferences.Editor mEditor;
	
	public LoginDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mAppContext=(AppContext) mActivity.getApplication();
		mThis=this;
		mSharedPreferences=AppConfig.getSharedPreferences(mContext);
		mEditor=mSharedPreferences.edit();
	}

	/**
	 * 登录对话框
	 */
	@SuppressLint("InflateParams")
	public void show() {
		mLoginView=mLayoutInflater.inflate(R.layout.dialog_login, null);
		mLoginDialog=new AlertDialog.Builder(mContext)
			.setTitle(R.string.login)
			.setView(mLoginView)
			.setPositiveButton(R.string.sure, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					mDialogInterface=dialog;
					setAlertDialogShow(dialog);
					loginPrepare();
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
		mLoginDialog.show();
	}
	
	/**
	 * 登录过程
	 */
	private void loginPrepare() {
		//判断是否有可用网络
		if (!mAppContext.isNetworkConnected()) {
			//如果无可用网络则直接返回
			UIHelper.ToastMessage(mContext, R.string.network_not_connected);
			return;
		}
		mUsername=(EditText) mLoginView.findViewById(R.id.et_username);
		mPassword=(EditText) mLoginView.findViewById(R.id.et_password);
		mStrUserName=mUsername.getText().toString();
		mStrPassword=mPassword.getText().toString();
		Drawable drawable=mResources.getDrawable(R.drawable.f063);
		drawable.setBounds(0, 0, 30, 30);  
		//判断用户名是否为空
		if (mStrUserName.equals("")) {
			mUsername.setError(mResources.getString(R.string.sinalogin_check_account), drawable);
			mUsername.requestFocus();
			return;
		}
		//判断密码是否为空
		if (mStrPassword.equals("")) {
			mPassword.setError(mResources.getString(R.string.msg_login_pwd_null), drawable);
			mPassword.requestFocus();
			return;
		}
		loginProcess();
	}
	
	/**
	 * 登录过程
	 */
	private void loginProcess(){
		String apiLogin=mResources.getString(R.string.api_login);
		AjaxParams ajaxParams=new AjaxParams();
		ajaxParams.put("verify_code", mResources.getString(R.string.verify_code));
		ajaxParams.put("username", mStrUserName);
		ajaxParams.put("password", mStrPassword);
		final FinalHttp finalHttp=new FinalHttp();
		finalHttp.post(apiLogin, ajaxParams, new AjaxCallBack<String>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
			}

			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				dealLogin(t);
			}
			
		});
	}
	
	/**
	 * 登录返回值处理
	 */
	private void dealLogin(String t){
		try {
			JSONObject jsonObject=new JSONObject(t);
			//UIHelper.ToastMessage(mContext, jsonObject.getString("status"));
			String loginStatus=jsonObject.getString("status");
			if (loginStatus.equals("1")) {
				saveLoginStatus(jsonObject);
				UIHelper.ToastMessage(mContext, "登录成功");
				mThis.setAlertDialogHide(mDialogInterface);
				mLoginDialog.dismiss();
			} else {
				UIHelper.ToastMessage(mContext, "登录失败，用户名或密码错误");
				mPassword.setText(null);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 登录状态保存
	 */
	private void saveLoginStatus(JSONObject jsonObject){
		try {
			String username=jsonObject.getJSONObject("ucresult").getString("username");
			String password=jsonObject.getJSONObject("ucresult").getString("password");
			AppConfig.isLogin=true;
			//保存登录状态
			mEditor.putBoolean(AppConfig.IS_LOGIN, true);
			mEditor.putString(AppConfig.USERNAME, username);
			mEditor.putString(AppConfig.PASSWORD, password);
			//数据提交
			mEditor.commit();
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	
	/**
	 * 退出登录
	 */
	public void logout() {
		AlertDialog logoutDialog=new AlertDialog.Builder(mContext)
			.setTitle("退出登录提示")
			.setMessage("确定退出登录吗？")
			.setPositiveButton(R.string.sure, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					AppConfig.isLogin=false;
					//保存登录状态
					mEditor.putBoolean(AppConfig.IS_LOGIN, false);
					mEditor.putString(AppConfig.USERNAME, "");
					mEditor.putString(AppConfig.PASSWORD, "");
					//数据提交
					mEditor.commit();
					UIHelper.ToastMessage(mContext, "退出登录成功！");
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
		logoutDialog.show();
	}
}
