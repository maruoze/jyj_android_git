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
	private Activity mActivity;
	public ShareHelper(Context mContext) {
		super();
		this.mContext = mContext;
		this.mActivity=(Activity) mContext;
	}
	
	/**
	 * 流程
	 */
	public void shareCase(AdapterView<?> parent, View view,
			int position, long id,Article article) {
			TextView textView=(TextView) view.findViewById(R.id.textview_share);
			String title=textView.getText().toString();
			String urlString="http://www.jxtzw.com/"+"article-"+article.getAid()+"-1.html";
			String shareMessage=article.getTitle()+" "+urlString;
			String articlTitle=shareMessage;
			
			switch (position) {
				case 0://新浪微博
					SinaWeiboHelper.authorize(mActivity, shareMessage);
					break;
				case 1://腾讯微博
					QQWeiboHelper.shareToQQ(mActivity, articlTitle, urlString);
					break;
				case 2://微信朋友圈
					WXFriendsHelper.shareToWXFriends((Activity) mContext, articlTitle, urlString);
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
				case 8://更多
					UIHelper.showShareMore(mActivity, articlTitle, urlString);
					break;
				default:
					UIHelper.ToastMessage(mContext, title+position);
					break;
			}
	}
}
