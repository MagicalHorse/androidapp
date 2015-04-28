package com.shenma.yueba.util;



import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MyDate {

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(String millon) {

		try {
            int index = millon.indexOf(".");
	        if (index != -1) {
	        	millon = StringUtils.getSendtoMinllon(millon,13);
	        } 
	        if  ("".equals(millon)) return dateToStrLong(new Date());
			Date date = new Date(Long.valueOf(millon));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = format.format(date);
			return time;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateToStrLong(new Date());
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrNoSS(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	public static long getDatetimeIntervalUsingDay(Date date1, Date date2) {
		long interval = date1.getTime() - date2.getTime();
		return interval / (24 * 60 * 60 * 1000);
	}

	/***
	 * 得到时间 ：上午 11:34
	 * 
	 * @param date
	 * @return
	 */
	public static String GetHourExtraInfo(Date date) {
		String longDate = dateToStrNoSS(date);
		int hour = Integer.parseInt(longDate.substring(11, 13));
		String hourAndMin = longDate.substring(11);
		if (hour >= 6 && hour < 11) {
			return "早上" + hourAndMin;
		} else if (hour >= 11 && hour < 13) {
			return "中午" + hourAndMin;
		} else if (hour >= 13 && hour < 18) {
			return "下午" + hourAndMin;
		} else if ((hour >= 18 && hour <= 23) || (hour >= 0 && hour < 6)) {
			return "晚上" + hourAndMin;
		} else {
			return null;
		}
	}

	/**
	 * 根据一个日期，返回是星期几的字符串
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getIndexOfWeek(Date date) {
		String[] weeks = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (week_index < 0) {
			week_index = 0;
		}
		return weeks[week_index];
	}

	/**
	 * 日期上加上年月日
	 * 
	 * @param date
	 * @return
	 */
	public static String AddYMDChina(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = formatter.format(date);
		String[] arrStr = strDate.split("-");
		return arrStr[0] + "年" + arrStr[1] + "月" + arrStr[2] + "日";
	}

	/**
	 * 日期上加上月日
	 * 
	 * @param date
	 * @return
	 */
	public static String AddMDChina(Date date) {
		String YMDEN = dateToStrLong(date);
		String[] arrStr = YMDEN.split("-");
		String[] arrStrDay = arrStr[2].split(" ");
		return arrStr[1] + "月" + arrStrDay[0] + "日" + arrStrDay[1];
	}

	public static String getStrDateWithoutHour(Date date) {
		if (date == null) {
			return "时间为空";
		}
		/** 当前时间 **/
		Date currentDate = new Date(System.currentTimeMillis());
		/** 两个时间差的天数 **/
		long ApartDays = getDatetimeIntervalUsingDay(currentDate, date);
		/** 得到消息的小时 包含上下午 **/
		String hourExtraInfo = GetHourExtraInfo(date);
		/** 当前时间 年 **/
		int yearCurrentDate = currentDate.getYear();
		/** 消息时间 年 **/
		int yearMsgDate = date.getYear();
		/** 周几 **/
		String indexOfWeek = getIndexOfWeek(date);
		if (ApartDays == 0) {// 今天
			return hourExtraInfo;
		} else if (ApartDays == 1) {// 昨天
			return ("昨天");
		} else if (ApartDays == 2) {// 前天
			return (indexOfWeek);
		} else if (yearCurrentDate == (yearMsgDate)) {// 今年
			return (AddMDChina(date));
		} else {// 今年之前
			return (AddYMDChina(date));
		}
	}
	public static String SetDate(Date date) {
		if (date == null) {
			return "时间为空";
		}
		/** 当前时间 年月日 **/
		Date currentDate = new Date(System.currentTimeMillis());
		/** 两个时间差的天数 **/
		long ApartDays = MyDate.getDatetimeIntervalUsingDay(currentDate, date);
		/** 得到消息的小时 包含上下午 **/
		String hourExtraInfo = MyDate.GetHourExtraInfo(date);
		/** 当前时间 年 **/
		int yearCurrentDate = currentDate.getYear();
		/** 消息时间 年 **/
		int yearMsgDate = date.getYear();
		/** 周几 **/
		String indexOfWeek = MyDate.getIndexOfWeek(date);
		if (ApartDays == 0) {// 今天
			return hourExtraInfo;
		} else if (ApartDays == 1) {// 昨天
			return ("昨天" + " " + hourExtraInfo);
		} else if (ApartDays == 2) {// 前天
			return (indexOfWeek + " " + hourExtraInfo);
		} else if (yearCurrentDate == (yearMsgDate)) {// 今年
			return (MyDate.AddMDChina(date) + " " + hourExtraInfo);
		} else {// 今年之前
			return (MyDate.AddYMDChina(date) + " " + hourExtraInfo);
		}
	}

}
