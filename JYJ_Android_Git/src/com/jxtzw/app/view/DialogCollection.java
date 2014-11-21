package com.jxtzw.app.view;

import java.util.ArrayList;

import net.tsz.afinal.FinalDb;

import com.jxtzw.app.AppConfig;
import com.jxtzw.app.R;
import com.jxtzw.app.adapter.GridViewCollectionAdapter;
import com.jxtzw.app.bean.CollectionClassify;
import com.jxtzw.app.common.UIHelper;

import android.R.interpolator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.EditText;

public class DialogCollection extends LoginDialog {
	/**
	 * 新增收藏分类对话框
	 */
	private AlertDialog mCCFDialog;
	private View mCCFView;
	private EditText mCollectionName;
	private String mStrCollectionName;
	
	/**
	 * EditText出错信息图片
	 */
	private Drawable mDrawable;
	
	/**
	 * 数据库
	 */
	private FinalDb mFinalDb;
	
	public DialogCollection(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressLint("InflateParams")
	public void show(final ArrayList<CollectionClassify> al,
			final GridViewCollectionAdapter gvca, 
			final CollectionClassify ccfy,
			final int position) {
		String dbName="collection";
		mFinalDb=FinalDb.create(mContext,dbName);
		
		mDrawable=mResources.getDrawable(R.drawable.f063);
		mDrawable.setBounds(0, 0, 30, 30); 
		mCCFView=mLayoutInflater.inflate(R.layout.dialog_collection, null);
		mCollectionName=(EditText) mCCFView.findViewById(R.id.et_collection_name);
		if (ccfy!=null) {
			mCollectionName.setText(ccfy.getCcf_classify_name());
		}
		mCCFDialog=new AlertDialog.Builder(mContext)
			.setTitle(R.string.collection_name_add)
			.setView(mCCFView)
			.setPositiveButton(R.string.sure, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					mStrCollectionName=mCollectionName.getText().toString();
					if (mStrCollectionName.length()==0) {
						setAlertDialogShow(dialog);
						mCollectionName.setError(mResources.getString(R.string.collection_name_error), mDrawable);
					} else {
						boolean flag=false;
						if (ccfy!=null){
							flag=updateCollectionClassify(al,gvca,ccfy,position);
						}else{
							flag=addCollectionClassify(al,gvca);
						}
						if (flag) {
							setAlertDialogHide(dialog);
						}else{
							setAlertDialogShow(dialog);
						}
					}
				}
			}).setNegativeButton(R.string.cancle, new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					setAlertDialogHide(dialog);
				}
			})
			.create();
			mCCFDialog.show();
	}
	
	/**
	 * 收藏分类名称添加
	 */
	private boolean addCollectionClassify(ArrayList<CollectionClassify> al,GridViewCollectionAdapter gvca) {
		//获取要保存的信息
		CollectionClassify ccf=new CollectionClassify();
		int ccf_classify_id=mSharedPreferences.getInt(AppConfig.CCFY_LAST_COUNT, 0);
		String uid=mSharedPreferences.getString(AppConfig.UID, "");
		String username=mSharedPreferences.getString(AppConfig.USERNAME, "");
		ccf.setCcf_classify_id(ccf_classify_id);
		ccf.setCcf_classify_name(mStrCollectionName);
		ccf.setCcf_uid(uid);
		ccf.setCcf_username(username);
		//保存收藏信息前的判断
		//判断收藏的个数
		ArrayList<CollectionClassify> ccfs=new ArrayList<CollectionClassify>();
		ccfs=(ArrayList<CollectionClassify>) mFinalDb.findAll(CollectionClassify.class);
		int ccfs_max=AppConfig.COLLECT_CLASSIFY_MAX;
		int ccfs_size=ccfs.size();
		if (ccfs_size>=ccfs_max) {
			String msg="最多只能创建"+ccfs_max+"个收藏分类";
			UIHelper.ToastMessage(mContext, msg);
			return true;
		}
		//判断当前收藏名称是否已经存在
		String whereString="ccf_classify_name='"+mStrCollectionName+"'";
		ArrayList<CollectionClassify> ccf_rt=new ArrayList<CollectionClassify>();
		ccf_rt=(ArrayList<CollectionClassify>) mFinalDb.findAllByWhere(CollectionClassify.class, whereString);
		if (ccf_rt.size()==0) {
			mFinalDb.save(ccf);
			ccf_classify_id++;
			mEditor.putInt(AppConfig.CCFY_LAST_COUNT, ccf_classify_id);
			mEditor.commit();
			al.add(ccf);
			gvca.notifyDataSetChanged();
			return true;
		}else{
			String msg="收藏分类名称【"+mStrCollectionName+"】已存在";
			mCollectionName.setError(msg, mDrawable);
			return false;
		}
	}
	
	/**
	 * 收藏分类名称修改
	 */
	private boolean updateCollectionClassify(ArrayList<CollectionClassify> al,
			GridViewCollectionAdapter gvca, 
			CollectionClassify ccfy,
			int position){
		
		if (!mStrCollectionName.equals(ccfy.getCcf_classify_name())) {
			ccfy.setCcf_classify_name(mStrCollectionName);
			mFinalDb.update(ccfy);
			al.remove(position);
			al.add(position, ccfy);
			gvca.notifyDataSetChanged();
		}
		return true;
	}
}
