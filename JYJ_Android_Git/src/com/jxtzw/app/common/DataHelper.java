package com.jxtzw.app.common;

import com.jxtzw.app.AppConfig;
import com.jxtzw.app.R;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

public class DataHelper {
	/**
	 * 获取主要标签的标题文字
	 */
	public static String[] getMainTabsText(Resources r,SharedPreferences sp) {
		String[] mainTabsTextStrings = null;
		String mainTabsText=sp.getString(AppConfig.JYJ_MAIN_TABS_TEXT_USERSET, null);
		if (mainTabsText.equals(null)) {
			mainTabsText=sp.getString(AppConfig.JYJ_MAIN_TABS_TEXT_DEFAULT, null);
			if (mainTabsText.equals(null)) {
				mainTabsTextStrings=r.getStringArray(R.array.main_tabs_text);
			}else{
				mainTabsTextStrings=mainTabsText.split(";");
			}
		}else{
			mainTabsTextStrings=mainTabsText.split(";");
		}
		return mainTabsTextStrings;
	}
	
	/**
	 * 获取子分类名称和ID
	 */
	public static String[] getSubCat(Context context, String sp_userset,
														String sp_default, int rc_array) {
		//定义返回字符串
		String[] returnStrings=null;
		//获取资源和sp
		Resources r=context.getResources();
		SharedPreferences sp=AppConfig.getSharedPreferences(context);
		//获取返回字符串
		String returnText=sp.getString(sp_userset, null);
		if (returnText.equals(null)) {
			returnText=sp.getString(sp_default, null);
			if (returnText.equals(null)) {
				returnStrings=r.getStringArray(rc_array);
			}else{
				returnStrings=returnText.split(";");
			}
		}else{
			returnStrings=returnText.split(";");
		}
		return returnStrings;
	}
}
