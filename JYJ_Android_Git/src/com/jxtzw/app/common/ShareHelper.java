package com.jxtzw.app.common;


import com.jxtzw.app.R;
import com.jxtzw.app.bean.Article;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

public class ShareHelper {
	private Context mContext;
	public ShareHelper(Context mContext) {
		super();
		this.mContext = mContext;
	}
	
	/**
	 * 流程
	 */
	public void shareCase(AdapterView<?> parent, View view,
			int position, long id,Article article) {
			TextView textView=(TextView) view.findViewById(R.id.textview_share);
			String title=textView.getText().toString();
			String shareMessage=article.getTitle();
			switch (position) {
				case 0:
					SinaWeiboHelper.authorize((Activity) mContext, shareMessage);
					break;
				case 1:
					UIHelper.ToastMessage(mContext, title+position+"bbb");
					break;
				case 2:
					UIHelper.ToastMessage(mContext, title+position+"ccc");
					break;
				case 3:
					UIHelper.ToastMessage(mContext, title+position+"ddd");
					break;
				case 4:
					UIHelper.ToastMessage(mContext, title+position+"eee");
					break;
				case 5:
					UIHelper.ToastMessage(mContext, title+position+"fff");
					break;
				case 6:
					UIHelper.ToastMessage(mContext, title+position+"ggg");
					break;
				case 7:
					UIHelper.ToastMessage(mContext, title+position+"hhh");
					break;
				case 8:
					UIHelper.ToastMessage(mContext, title+position+"iii");
					break;
				default:
					UIHelper.ToastMessage(mContext, title+position);
					break;
			}
	}
}
