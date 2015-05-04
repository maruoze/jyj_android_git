package com.jxtzw.app.ui;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import com.jxtzw.app.R;
import com.jxtzw.app.common.StringUtils;
import com.jxtzw.app.common.UIHelper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

public class MemberRegisterActivity extends MemberPrivilegeActivity {
	/**
	 * 注册相关
	 */
	private TextView mUserNameTV;
	private TextView mHandphoneTV;
	private TextView mQQTextView;
	private Button mRegisterButton;
	private AlertDialog mRegistering=null;
	private AlertDialog mResult;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreateEx(savedInstanceState);
		setContentView(R.layout.activity_member_register);
		init();
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		initCommonMemberVar();
		initTitle();
		initRegister();
	}

	@Override
	protected void initSelfMemVar() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void initTitle() {
		// TODO Auto-generated method stub
		mHeadLeft=(Button) findViewById(R.id.head_left);
		mHeadRight=(Button) findViewById(R.id.head_right);
		mCatNameTextView=(TextView) findViewById(R.id.head_middle);
		String titleString="注册";
		mCatNameTextView.setText(titleString);
		//初始化监听
		initTilteListner();
		mHeadLeft.setOnClickListener(mHLClickListener);
		mHeadRight.setOnClickListener(mHRClickListener);
	}

	@Override
	protected void initTilteListner() {
		// TODO Auto-generated method stub
		super.initTilteListner();
	}
	
	/**
	 * 创建注册
	 */
	private void initRegister() {
		/**
		 * 获取
		 */
		mUserNameTV=(TextView) findViewById(R.id.et_username);
		mHandphoneTV=(TextView) findViewById(R.id.et_handphone);
		mQQTextView=(TextView) findViewById(R.id.et_qq);
		mRegisterButton=(Button) findViewById(R.id.bt_register);
		mRegisterButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0); 
				registerSubmit();
			}
		});
	}
	
	/**
	 * 注册提交
	 */
	private void registerSubmit() {
		//判断是否有可用网络
		if (!mAppContext.isNetworkConnected()) {
			//如果无可用网络则直接返回
			UIHelper.ToastMessage(mContext, R.string.network_not_connected);
			return;
		}
		//获取数据
		String apiRegister=mResources.getString(R.string.api_register);
		//String apiRegister="http://m1.jxtzw.com/Ajax/qq_group/";
		String username=mUserNameTV.getText().toString();
		String handphone=mHandphoneTV.getText().toString();
		String qq=mQQTextView.getText().toString();
		//数据验证
		Drawable drawable=mResources.getDrawable(R.drawable.error);
		drawable.setBounds(0, 0, 30, 30);  
		if(username.equals("")){
			mUserNameTV.setError("请填写您的姓名",drawable);
			//mUserNameTV.setFocusable(true);   
			//mUserNameTV.setFocusableInTouchMode(true);   
			mUserNameTV.requestFocus();  
			return;
		}else if (username.matches("[\\s!@#$0-9]+.*")) {
			mUserNameTV.setError("您姓名的格式含有非法字符",drawable);
			mUserNameTV.requestFocus();  
			return;
		}
		if(handphone.equals("")){
			mHandphoneTV.setError("请填写您的手机号", drawable);
			mHandphoneTV.requestFocus();  
			return;
		}else if (!handphone.matches("1[0-9]{10}")) {
			mHandphoneTV.setError("请填写正确的手机号",drawable);
			mHandphoneTV.requestFocus();  
			return;
		}
		
		//注册中....对话框
		mRegistering=new AlertDialog.Builder(mContext).create();
		mRegistering.setMessage("注册中......");
		mRegistering.show();
		
		AjaxParams ajaxParams=new AjaxParams();
		ajaxParams.put("customer", username);
		ajaxParams.put("handphone", handphone);
		ajaxParams.put("qq_number", qq);
		
		final FinalHttp finalHttp=new FinalHttp();
		finalHttp.post(apiRegister, ajaxParams, new AjaxCallBack<String>() {

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
			}

			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				if (mRegistering!=null) {
					mRegistering.dismiss();
				} 
				String rt=StringUtils.unescapeUnicode(t);
				mResult=new AlertDialog.Builder(mContext).
				setTitle("温馨提示").
				setMessage(rt).
				setNegativeButton("关闭",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int which) {
							dialog.dismiss();
						}
				}).create();
				mResult.show();
			}
		});
	}
}
