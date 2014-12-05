package com.jxtzw.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.apache.http.cookie.Cookie;

import com.jxtzw.app.bean.AccessInfo;
import com.jxtzw.app.common.StringUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;

/**
 * 应用程序配置类：用于保存用户相关信息及设置
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
@SuppressLint("NewApi")
public class AppConfig {
	/**
	 * OSChina定义
	 */
	private final static String APP_CONFIG = "config";
	public final static String CONF_APP_UNIQUEID = "APP_UNIQUEID";
	public final static String CONF_COOKIE = "cookie";
	public final static String CONF_ACCESSTOKEN = "accessToken";
	public final static String CONF_ACCESSSECRET = "accessSecret";
	public final static String CONF_EXPIRESIN = "expiresIn";
	public final static String CONF_LOAD_IMAGE = "perf_loadimage";
	public final static String CONF_SCROLL = "perf_scroll";
	public final static String CONF_HTTPS_LOGIN = "perf_httpslogin";
	public final static String CONF_VOICE = "perf_voice";
	public final static String CONF_CHECKUP = "perf_checkup";

	public final static String SAVE_IMAGE_PATH = "save_image_path";
	@SuppressLint("NewApi")
	public final static String DEFAULT_SAVE_IMAGE_PATH = Environment.getExternalStorageDirectory()+ File.separator+ "jxtzw"+ File.separator;
			
	private Context mContext;
	private AccessInfo accessInfo = null;
	private static AppConfig appConfig;
	
	/**
	 * 自定义
	 */
	//是否首次运行本程序的名称字符串常量
	public final static String JYJ_CONF_IS_FIRST_OPEN="is_first_open";
	//主程序标签名称的字符串常量
	public final static String JYJ_MAIN_TABS_TEXT_DEFAULT="main_tabs_text_default";
	public final static String JYJ_MAIN_TABS_TEXT_USERSET="main_tabs_text_userset";
	/**
	 * 子分类名称字符串常量[黄金]
	 */
	//储存黄金分类的名称的SP变量名
	public final static String JYJ_CAT_GOLD_NAME_DEFAULT="cat_gold_name_default";
	public final static String JYJ_CAT_GOLD_NAME_USERSET="cat_gold_name_userset";
	//储存黄金分类的ID的SP变量名
	public final static String JYJ_CAT_GOLD_ID_DEFAULT="cat_gold_id_default";
	public final static String JYJ_CAT_GOLD_ID_USERSET="cat_gold_id_userset";
	//储存黄金分类里是否显示行情的SP变量名
	public final static String JYJ_CAT_GOLD_SHOW_QUOT_DEFAULT="cat_gold_show_quot_default";
	public final static String JYJ_CAT_GOLD_SHOW_QUOT_USERSET="cat_gold_show_quot_userset";
	//储存黄金分类中的文章列表是显示图片的SP变量名
	public final static String JYJ_CAT_GOLD_SHOW_IMAGE_DEFAULT="cat_gold_show_image_default";
	public final static String JYJ_CAT_GOLD_SHOW_IMAGE_USERSET="cat_gold_show_image_userset";
	/**
	 * 子分类名称字符串常量[白银]
	 */
	public final static String JYJ_CAT_SILVER_NAME_DEFAULT="cat_silver_name_default";
	public final static String JYJ_CAT_SILVER_NAME_USERSET="cat_silver_name_userset";
	//储存黄金分类的ID的SP变量名
	public final static String JYJ_CAT_SILVER_ID_DEFAULT="cat_silver_id_default";
	public final static String JYJ_CAT_SILVER_ID_USERSET="cat_silver_id_userset";
	//储存黄金分类里是否显示行情的SP变量名
	public final static String JYJ_CAT_SILVER_SHOW_QUOT_DEFAULT="cat_silver_show_quot_default";
	public final static String JYJ_CAT_SILVER_SHOW_QUOT_USERSET="cat_silver_show_quot_userset";
	//储存黄金分类中的文章列表是显示图片的SP变量名
	public final static String JYJ_CAT_SILVER_SHOW_IMAGE_DEFAULT="cat_silver_show_image_default";
	public final static String JYJ_CAT_SILVER_SHOW_IMAGE_USERSET="cat_silver_show_image_userset";	
	/**
	 * 子分类名称字符串常量[原油]
	 */
	public final static String JYJ_CAT_OIL_NAME_DEFAULT="cat_oil_name_default";
	public final static String JYJ_CAT_OIL_NAME_USERSET="cat_oil_name_userset";
	//储存黄金分类的ID的SP变量名
	public final static String JYJ_CAT_OIL_ID_DEFAULT="cat_oil_id_default";
	public final static String JYJ_CAT_OIL_ID_USERSET="cat_oil_id_userset";
	//储存黄金分类里是否显示行情的SP变量名
	public final static String JYJ_CAT_OIL_SHOW_QUOT_DEFAULT="cat_oil_show_quot_default";
	public final static String JYJ_CAT_OIL_SHOW_QUOT_USERSET="cat_oil_show_quot_userset";
	//储存黄金分类中的文章列表是显示图片的SP变量名
	public final static String JYJ_CAT_OIL_SHOW_IMAGE_DEFAULT="cat_oil_show_image_default";
	public final static String JYJ_CAT_OIL_SHOW_IMAGE_USERSET="cat_oil_show_image_userset";		
	
	/**
	 * 子分类名称字符串常量[行情]
	 */
	public final static String JYJ_CAT_QUO_NAME_DEFAULT="cat_quo_name_default";
	public final static String JYJ_CAT_QUO_NAME_USERSET="cat_quo_name_userset";
	//储存黄金分类的ID的SP变量名
	public final static String JYJ_CAT_QUO_ID_DEFAULT="cat_quo_id_default";
	public final static String JYJ_CAT_QUO_ID_USERSET="cat_quo_id_userset";	
	
	/**
	 * 是否登录判断
	 */
	public static boolean isLogin=false;
	public static boolean isMember=false;
	public final static String IS_LOGIN="is_login";
	public final static String IS_MEMBER="is_member";
	public final static String UID="uid";
	public final static String USERNAME="username";
	public final static String PASSWORD="password";
	/**
	 * 临时登录信息包保存
	 */
	public static String uid="";
	public static String username="";
	public static String password="";
	/**
	 * 收藏相关
	 */
	public final static int COLLECT_CLASSIFY_MAX=12;
	public final static String CCFY_LAST_COUNT="ccfy_last_count";
	/**
	 * 全局cookie保存
	 */
	public static Cookie COOKIE;
	/**
	 * 检查更新
	 */
	public final static String CHECK_UPDATE="checkupdate"; 
	

	public static AppConfig getAppConfig(Context context) {
		if (appConfig == null) {
			appConfig = new AppConfig();
			appConfig.mContext = context;
		}
		return appConfig;
	}

	/**
	 * 获取Preference设置
	 */
	public static SharedPreferences getSharedPreferences(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context);
	}

	/**
	 * 是否加载显示文章图片
	 */
	public static boolean isLoadImage(Context context) {
		return getSharedPreferences(context).getBoolean(CONF_LOAD_IMAGE, true);
	}

	public String getCookie() {
		return get(CONF_COOKIE);
	}

	public void setAccessToken(String accessToken) {
		set(CONF_ACCESSTOKEN, accessToken);
	}

	public String getAccessToken() {
		return get(CONF_ACCESSTOKEN);
	}

	public void setAccessSecret(String accessSecret) {
		set(CONF_ACCESSSECRET, accessSecret);
	}

	public String getAccessSecret() {
		return get(CONF_ACCESSSECRET);
	}

	public void setExpiresIn(long expiresIn) {
		set(CONF_EXPIRESIN, String.valueOf(expiresIn));
	}

	public long getExpiresIn() {
		return StringUtils.toLong(get(CONF_EXPIRESIN));
	}

	public void setAccessInfo(String accessToken, String accessSecret,
			long expiresIn) {
		if (accessInfo == null)
			accessInfo = new AccessInfo();
		accessInfo.setAccessToken(accessToken);
		accessInfo.setAccessSecret(accessSecret);
		accessInfo.setExpiresIn(expiresIn);
		// 保存到配置
		this.setAccessToken(accessToken);
		this.setAccessSecret(accessSecret);
		this.setExpiresIn(expiresIn);
	}

	public AccessInfo getAccessInfo() {
		if (accessInfo == null && !StringUtils.isEmpty(getAccessToken())
				&& !StringUtils.isEmpty(getAccessSecret())) {
			accessInfo = new AccessInfo();
			accessInfo.setAccessToken(getAccessToken());
			accessInfo.setAccessSecret(getAccessSecret());
			accessInfo.setExpiresIn(getExpiresIn());
		}
		return accessInfo;
	}

	public String get(String key) {
		Properties props = get();
		return (props != null) ? props.getProperty(key) : null;
	}

	public Properties get() {
		FileInputStream fis = null;
		Properties props = new Properties();
		try {
			// 读取files目录下的config
			// fis = activity.openFileInput(APP_CONFIG);

			// 读取app_config目录下的config
			File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
			fis = new FileInputStream(dirConf.getPath() + File.separator
					+ APP_CONFIG);

			props.load(fis);
		} catch (Exception e) {
		} finally {
			try {
				fis.close();
			} catch (Exception e) {
			}
		}
		return props;
	}

	private void setProps(Properties p) {
		FileOutputStream fos = null;
		try {
			// 把config建在files目录下
			// fos = activity.openFileOutput(APP_CONFIG, Context.MODE_PRIVATE);

			// 把config建在(自定义)app_config的目录下
			File dirConf = mContext.getDir(APP_CONFIG, Context.MODE_PRIVATE);
			File conf = new File(dirConf, APP_CONFIG);
			fos = new FileOutputStream(conf);

			p.store(fos, null);
			fos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (Exception e) {
			}
		}
	}

	public void set(Properties ps) {
		Properties props = get();
		props.putAll(ps);
		setProps(props);
	}

	public void set(String key, String value) {
		Properties props = get();
		props.setProperty(key, value);
		setProps(props);
	}

	public void remove(String... key) {
		Properties props = get();
		for (String k : key)
			props.remove(k);
		setProps(props);
	}
}
