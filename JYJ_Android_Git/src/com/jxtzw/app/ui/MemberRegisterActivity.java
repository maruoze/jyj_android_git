package com.jxtzw.app.ui;

import java.io.UnsupportedEncodingException;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import com.jxtzw.app.R;
import com.jxtzw.app.common.StringUtils;
import com.jxtzw.app.common.UIHelper;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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
		String username=mUserNameTV.getText().toString();
		String handphone=mHandphoneTV.getText().toString();
		String qq=mQQTextView.getText().toString();
		//数据验证
		Drawable drawable=mResources.getDrawable(R.drawable.f063);
		drawable.setBounds(0, 0, 30, 30);  
		if(username.equals("")){
			mUserNameTV.setError("请填写您的姓名",drawable);
			//mUserNameTV.setFocusable(true);   
			//mUserNameTV.setFocusableInTouchMode(true);   
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
		String postString=username+"|,|0|,|"+handphone+"|,|"+qq+"|,|0|,|0|,|0|,|0|,|0|,|1|,|5|,|";
		AjaxParams ajaxParams=new AjaxParams();
		ajaxParams.put("post_str", postString);
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
				UIHelper.ToastMessage(mContext,t);
				String iosString="";
				String utfString = "";
				try {
					iosString=new String(t.getBytes("UTF-8"),"ISO-8859-1");
					utfString=new String(iosString.getBytes("ISO-8859-1"),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				UIHelper.ToastMessage(mContext,utfString);
			}
			
		});
	}
}
