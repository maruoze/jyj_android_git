package com.jxtzw.app.bean;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.jxtzw.app.common.StringUtils;
import com.lidroid.xutils.db.annotation.Transient;

public class AppUpdate implements Serializable {

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = -6286181826881926597L;

	public final static String UTF8 = "UTF-8";
	private int versionCode;
	private String versionName;
	private String downloadUrl;
	private String updateLog;

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getUpdateLog() {
		return updateLog;
	}

	public void setUpdateLog(String updateLog) {
		this.updateLog = updateLog;
	}

	public static AppUpdate parse(String xml) {
		AppUpdate appUpdate = null;
		// 获得XmlPullParser解析器
		XmlPullParser xmlParser = Xml.newPullParser();
		try {
			InputStream inputStream = new ByteArrayInputStream(
					xml.getBytes(AppUpdate.UTF8));
			xmlParser.setInput(inputStream, AppUpdate.UTF8);
			// 获得解析到的事件类别，这里有开始文档，结束文档，开始标签，结束标签，文本等等事件。
			int evtType = xmlParser.getEventType();
			// 一直循环，直到文档结束
			while (evtType != XmlPullParser.END_DOCUMENT) {
				String tag = xmlParser.getName();
				switch (evtType) {
				case XmlPullParser.START_TAG:
					// 通知信息
					if (tag.equalsIgnoreCase("AppUpdate")) {
						appUpdate = new AppUpdate();
					} else if (appUpdate != null) {
						if (tag.equalsIgnoreCase("versionCode")) {
							appUpdate.setVersionCode(StringUtils.toInt(
									xmlParser.nextText(), 0));
						} else if (tag.equalsIgnoreCase("versionName")) {
							appUpdate.setVersionName(xmlParser.nextText());
						} else if (tag.equalsIgnoreCase("downloadUrl")) {
							appUpdate.setDownloadUrl(xmlParser.nextText());
						} else if (tag.equalsIgnoreCase("updateLog")) {
							appUpdate.setUpdateLog(xmlParser.nextText());
						}
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				}
				// 如果xml没有结束，则导航到下一个节点
				evtType = xmlParser.next();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return appUpdate;
	}
}
