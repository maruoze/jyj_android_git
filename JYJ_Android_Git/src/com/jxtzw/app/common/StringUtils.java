package com.jxtzw.app.common;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串操作工具包
 * 
 * @author liux (http://my.oschina.net/liux)
 * @version 1.0
 * @created 2012-3-21
 */
public class StringUtils {
	private final static Pattern emailer = Pattern
			.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	// private final static SimpleDateFormat dateFormater = new
	// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// private final static SimpleDateFormat dateFormater2 = new
	// SimpleDateFormat("yyyy-MM-dd");

	private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};

	private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};

	/**
	 * 将字符串转为日期类型
	 * 
	 * @param sdate
	 * @return
	 */
	public static Date toDate(String sdate) {
		try {
			return dateFormater.get().parse(sdate);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 以友好的方式显示时间
	 * 
	 * @param sdate
	 * @return
	 */
	public static String friendly_time(String sdate) {
		Date time = null;
		if (TimeZoneUtil.isInEasternEightZones()) {
			time = toDate(sdate);
		} else {
			time = TimeZoneUtil.transformTime(toDate(sdate),
					TimeZone.getTimeZone("GMT+08"), TimeZone.getDefault());
		}
		if (time == null) {
			return "Unknown";
		}
		String ftime = "";
		Calendar cal = Calendar.getInstance();

		// 判断是否是同一天
		String curDate = dateFormater2.get().format(cal.getTime());
		String paramDate = dateFormater2.get().format(time);
		if (curDate.equals(paramDate)) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
			return ftime;
		}

		long lt = time.getTime() / 86400000;
		long ct = cal.getTimeInMillis() / 86400000;
		int days = (int) (ct - lt);
		if (days == 0) {
			int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
			if (hour == 0)
				ftime = Math.max(
						(cal.getTimeInMillis() - time.getTime()) / 60000, 1)
						+ "分钟前";
			else
				ftime = hour + "小时前";
		} else if (days == 1) {
			ftime = "昨天";
		} else if (days == 2) {
			ftime = "前天";
		} else if (days > 2 && days <= 10) {
			ftime = days + "天前";
		} else if (days > 10) {
			ftime = dateFormater2.get().format(time);
		}
		return ftime;
	}

	/**
	 * 判断给定字符串时间是否为今日
	 * 
	 * @param sdate
	 * @return boolean
	 */
	public static boolean isToday(String sdate) {
		boolean b = false;
		Date time = toDate(sdate);
		Date today = new Date();
		if (time != null) {
			String nowDate = dateFormater2.get().format(today);
			String timeDate = dateFormater2.get().format(time);
			if (nowDate.equals(timeDate)) {
				b = true;
			}
		}
		return b;
	}

	/**
	 * 返回long类型的今天的日期
	 * 
	 * @return
	 */
	public static long getToday() {
		Calendar cal = Calendar.getInstance();
		String curDate = dateFormater2.get().format(cal.getTime());
		curDate = curDate.replace("-", "");
		return Long.parseLong(curDate);
	}

	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断是不是一个合法的电子邮件地址
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		if (email == null || email.trim().length() == 0)
			return false;
		return emailer.matcher(email).matches();
	}

	/**
	 * 字符串转整数
	 * 
	 * @param str
	 * @param defValue
	 * @return
	 */
	public static int toInt(String str, int defValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
		}
		return defValue;
	}

	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static int toInt(Object obj) {
		if (obj == null)
			return 0;
		return toInt(obj.toString(), 0);
	}

	/**
	 * 对象转整数
	 * 
	 * @param obj
	 * @return 转换异常返回 0
	 */
	public static long toLong(String obj) {
		try {
			return Long.parseLong(obj);
		} catch (Exception e) {
		}
		return 0;
	}

	/**
	 * 字符串转布尔值
	 * 
	 * @param b
	 * @return 转换异常返回 false
	 */
	public static boolean toBool(String b) {
		try {
			return Boolean.parseBoolean(b);
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 将一个InputStream流转换成字符串
	 * 
	 * @param is
	 * @return
	 */
	public static String toConvertString(InputStream is) {
		StringBuffer res = new StringBuffer();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader read = new BufferedReader(isr);
		try {
			String line;
			line = read.readLine();
			while (line != null) {
				res.append(line);
				line = read.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != isr) {
					isr.close();
					isr.close();
				}
				if (null != read) {
					read.close();
					read = null;
				}
				if (null != is) {
					is.close();
					is = null;
				}
			} catch (IOException e) {
			}
		}
		return res.toString();
	}

	/**
	 * @author MaRuoze
	 * @deprecated 自定义相关工具函数
	 */
	/**
	 * 字符串数组转为带分隔符的字符串
	 */
	public static String stringArrayToString(String[] strings, String seperator) {
		String returnString = "";
		for (int i = 0; i < strings.length; i++) {
			if (i != strings.length - 1) {
				returnString += strings[i] + seperator;
			} else {
				returnString += strings[i];
			}
		}
		return returnString;
	}

	/**
	 * 将秒数时间戳转换为指定格式的日期
	 */
	@SuppressLint("SimpleDateFormat")
	public static String timeStamp2Date(String time, String format) {
		Long timestamp = Long.parseLong(time) * 1000;
		String date = new SimpleDateFormat(format).format(new Date(timestamp));
		return date;
	}

	/**
	 * 判断是否是当日
	 */
	public static boolean isTodayEx(String time) {
		boolean b = false;
		// Date time = toDate(sdate);
		Long timestamp = Long.parseLong(time) * 1000;
		Date today = new Date();
		Date isTodayDate = new Date(timestamp);
		if (time != null) {
			String nowDate = dateFormater2.get().format(today);
			String timeDate = dateFormater2.get().format(isTodayDate);
			if (nowDate.equals(timeDate)) {
				b = true;
			}
		}
		return b;
	}

	/**
	 * 浮点数保留两位小数
	 */
	public static String float2Format(String str) {
		String returnString = "";
		float price = Float.parseFloat(str);
		DecimalFormat decimalFormat = new DecimalFormat(".00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
		returnString = decimalFormat.format(price);// format 返回的是字符串
		return returnString;
	}

	/**
	 * 获得当前系统时间并格式化输出
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getNowString() {
		String nowString = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		nowString = df.format(new Date());// new Date()为获取当前系统时间
		return nowString;
	}

	/**
	 * 获取的网络数据的处理
	 */
	public static String dataFiltering(String origin, String imageBaseURL) {
		if (origin != null) {
			// 超链接去除
			origin = HtmlRegexpUtils.fiterHtmlTag(origin, "a");
			origin = origin.replaceAll("</a>", "");
			// HTML特殊符号替换
			origin = StringUtils.replaceHTML(origin);
			// 图片地址完整化
			origin = StringUtils.completeImageURL(origin, imageBaseURL);
		}
		return origin;
	}

	/**
	 * HTML符号替换
	 */
	public static String replaceHTML(String origin) {
		origin = origin.replace("&amp;", "&");
		origin = origin.replace("&quot;", "\"");
		origin = origin.replace("&gt;", ">");
		origin = origin.replace("&lt;", "<");
		return origin;
	}

	/**
	 * 图片地址完整化, 图片宽度设置为100%
	 */
	public static String completeImageURL(String origin, String imageBaseURL) {
		String img = "";
		Pattern pImageURL;
		Matcher matcherImageURL;
		String regExImage = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
		pImageURL = Pattern.compile(regExImage, Pattern.CASE_INSENSITIVE);
		matcherImageURL = pImageURL.matcher(origin);
		while (matcherImageURL.find()) {
			img = matcherImageURL.group();
			// Matcher m =
			// Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
			Matcher m = Pattern.compile("src\\s*=\"").matcher(img);
			String imageSrc = null;
			while (m.find()) {
				String imageURL = m.group();
				String imageURLWhole = " width='100%' " + imageURL
						+ imageBaseURL;
				imageSrc = m.replaceAll(imageURLWhole);
			}
			origin = matcherImageURL.replaceAll(imageSrc);
		}
		return origin;
	}

	
	/**
	 * 字符编码转换
	 */
	public static String gbk2utf8(String gbk) {
		String l_temp = GBK2Unicode(gbk);
		l_temp = unicodeToUtf8(l_temp);
		return l_temp;
	}

	public static String utf82gbk(String utf) {
		String l_temp = utf8ToUnicode(utf);
		l_temp = Unicode2GBK(l_temp);
		return l_temp;
	}

	/**
	 * 
	 * @param str
	 * @return String
	 */

	public static String GBK2Unicode(String str) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char chr1 = (char) str.charAt(i);

			if (!isNeedConvert(chr1)) {
				result.append(chr1);
				continue;
			}

			result.append("\\u" + Integer.toHexString((int) chr1));
		}

		return result.toString();
	}

	/**
	 * 
	 * @param dataStr
	 * @return String
	 */

	public static String Unicode2GBK(String dataStr) {
		int index = 0;
		StringBuffer buffer = new StringBuffer();

		int li_len = dataStr.length();
		while (index < li_len) {
			if (index >= li_len - 1
					|| !"\\u".equals(dataStr.substring(index, index + 2))) {
				buffer.append(dataStr.charAt(index));

				index++;
				continue;
			}

			String charStr = "";
			charStr = dataStr.substring(index + 2, index + 6);

			char letter = (char) Integer.parseInt(charStr, 16);

			buffer.append(letter);
			index += 6;
		}

		return buffer.toString();
	}

	public static boolean isNeedConvert(char para) {
		return ((para & (0x00FF)) != para);
	}

	/**
	 * utf-8 转unicode
	 * 
	 * @param inStr
	 * @return String
	 */
	@SuppressLint("DefaultLocale")
	public static String utf8ToUnicode(String inStr) {
		char[] myBuffer = inStr.toCharArray();

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < inStr.length(); i++) {
			UnicodeBlock ub = UnicodeBlock.of(myBuffer[i]);
			if (ub == UnicodeBlock.BASIC_LATIN) {
				sb.append(myBuffer[i]);
			} else if (ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
				int j = (int) myBuffer[i] - 65248;
				sb.append((char) j);
			} else {
				short s = (short) myBuffer[i];
				String hexS = Integer.toHexString(s);
				String unicode = "\\u" + hexS;
				sb.append(unicode.toLowerCase());
			}
		}
		return sb.toString();
	}

	/**
	 * 
	 * @param theString
	 * @return String
	 */
	public static String unicodeToUtf8(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}

}
