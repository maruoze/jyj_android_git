package com.jxtzw.app.view;

import java.util.ArrayList;

import net.tsz.afinal.FinalDb;

import com.jxtzw.app.R;
import com.jxtzw.app.adapter.GridViewCollectionAdapter;
import com.jxtzw.app.bean.CollectionClassify;
import com.jxtzw.app.common.UIHelper;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DialogColloctionOp extends BaseView {
	/**
	 * UI
	 */
	private AlertDialog mDCOPDialog;
	private View mDCOPView;
	private Button mBroswer;
	private Button mUpdate;
	private Button mDelete;
	private CollectionClassify mCCFY;
	private int mCCFYID;
	private String mCCFYName;
	/**
	 * 数据库
	 */
	private FinalDb mFinalDb;
	/**
	 * 相关UI
	 */
	private ArrayList<CollectionClassify> mCCFYs;
	private GridViewCollectionAdapter mGVCAdapter;
	private int mPosition;
	
	
	public DialogColloctionOp(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressLint("InflateParams")
	public void show(View view,int position,ArrayList<CollectionClassify> al,GridViewCollectionAdapter gvca) {
		//数据库准备
		String dbName="collection";
		mFinalDb=FinalDb.create(mContext,dbName);
		//更新的UI
		mCCFYs=al;
		mGVCAdapter=gvca;
		mPosition=position;
		//UI
		mCCFY=(CollectionClassify) ((TextView)view.findViewById(R.id.textview_collection)).getTag();
		mCCFYID=mCCFY.getCcf_classify_id();
		
		mCCFYName=((TextView)view.findViewById(R.id.textview_collection)).getText().toString();
		mDCOPView=mLayoutInflater.inflate(R.layout.dialog_collection_op, null);
		mBroswer=(Button) mDCOPView.findViewById(R.id.bt_collection_op_broswer);
		mUpdate=(Button) mDCOPView.findViewById(R.id.bt_collection_op_update);
		mDelete=(Button) mDCOPView.findViewById(R.id.bt_collection_op_delete);
		String titleString="对收藏分类【"+mCCFYName+"】的操作";
		mDCOPDialog=new AlertDialog.Builder(mContext)
		.setTitle(titleString)
		.setView(mDCOPView)
		.setNegativeButton(R.string.collection_op_shut, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		})
		.create();
		mDCOPDialog.show();
		
		//操作的事件
		operater();
	}
	
	/**
	 * 操作的事件
	 */
	private void operater() {
		//浏览分类下收藏的文章
		mBroswer.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UIHelper.ToastMessage(mContext, mCCFYName);
				mDCOPDialog.dismiss();
			}
		});
		
		//修改收藏分类
		mUpdate.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//UIHelper.ToastMessage(mContext, mCCFYName);
				mDCOPDialog.dismiss();
				DialogCollection dialogCollection=new DialogCollection(mContext);
				dialogCollection.show(mCCFYs, mGVCAdapter, mCCFY,mPosition);
			}
		});
		
		//删除收藏分类
		mDelete.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mFinalDb.delete(mCCFY);
				mCCFYs.remove(mPosition);
				mGVCAdapter.notifyDataSetChanged();
				mDCOPDialog.dismiss();
			}
		});
	}
}
