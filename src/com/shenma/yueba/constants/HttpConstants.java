package com.shenma.yueba.constants;

public class HttpConstants {

	private static String baseUrl = "http://123.57.77.86:8080/app/";
	private static String USERURL=baseUrl+"User/";//USER接口
	private static String COMMONURL=baseUrl+"Common/";
	//注册----获取手机短信验证码
	public static String sendPhoneCode = USERURL+"SendMobileCode";
	public static String RegisterCompleate = USERURL+"RegisterCompleate";
	//校验手机验证
	public static String METHOD_VERIFYCODE = USERURL+"VerifyCode";
	//获取城市列表
	public static String METHOD_GETCITYLIST = COMMONURL+"GetCityList";
	//注册用户信息
	public static String METHOD_REGISTER = USERURL+"Register";
	//登录
	public static String METHOD_LOGIN = USERURL+"Login";
	//修改登录密码
	public static String METHOD_UPDATEPWD = USERURL+"ChangePassword";
}
