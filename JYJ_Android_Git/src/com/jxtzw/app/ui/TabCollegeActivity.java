package com.jxtzw.app.ui;

import com.jxtzw.app.AppConfig;
import com.jxtzw.app.R;
import com.jxtzw.app.common.DataHelper;

import android.os.Bundle;

public class TabCollegeActivity extends TabHomeActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void initSelfMemberVar() {
		// TODO Auto-generated method stub
		//子导航标题
		mCatNames=DataHelper.getSubCat(mContext, AppConfig.JYJ_CAT_COLL_NAME_USERSET,
				AppConfig.JYJ_CAT_COLL_NAME_DEFAULT, R.array.cat_coll_name);
		//子导航ID
		mCatIDs=DataHelper.getSubCat(mContext, AppConfig.JYJ_CAT_COLL_ID_USERSET, 
						AppConfig.JYJ_CAT_COLL_ID_DEFAULT, R.array.cat_coll_id);
		//是否显示行情
		mCatShowQuotation=DataHelper.getSubCat(mContext, AppConfig.JYJ_CAT_COLL_SHOW_QUOT_USERSET, 
				AppConfig.JYJ_CAT_COLL_SHOW_QUOT_DEFAULT, R.array.cat_coll_show_quotaion);
		//是否显示文章图片
		mCatShowImages=DataHelper.getSubCat(mContext, AppConfig.JYJ_CAT_COLL_SHOW_IMAGE_USERSET, 
				AppConfig.JYJ_CAT_COLL_SHOW_IMAGE_DEFAULT, R.array.cat_coll_show_image);
		
		mVPCount=mCatIDs.length;
	}
	
}
