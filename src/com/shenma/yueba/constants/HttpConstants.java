package com.shenma.yueba.constants;

public class HttpConstants {

	private static String baseUrl = "http://123.57.77.86:8080/app/";
	private static String USERURL=baseUrl+"User/";//USER接口
	private static String COMMONURL=baseUrl+"Common/";
	private static String ADDRESS=baseUrl+"Address/";
	/**
	 * 注册----获取手机短信验证码
	 */
	public static String sendPhoneCode = USERURL+"SendMobileCode";
	//注册
	public static String RegisterCompleate = USERURL+"RegisterCompleate";
	/**
	 * 校验手机验证
	 */
	public static String METHOD_VERIFYCODE = USERURL+"VerifyCode";
	/**
	 * 获取城市列表
	 */
	public static String METHOD_GETCITYLIST = COMMONURL+"GetCityList";
	/**
	 * 所有城市列表包含省
	 */
	public static String METHOD_ALLGETCITYLIST = COMMONURL+"GetAllCity";
	/**
	 * 注册用户信息
	 */
	public static String METHOD_REGISTER = USERURL+"Register";
	/**
	 * 登录
	 */
	public static String METHOD_LOGIN = USERURL+"Login";
	/**
	 * 修改登录密码
	 */
	public static String METHOD_UPDATEPWD = USERURL+"ChangePassword";
	/**
	 * 重置手机密码
	 */
	public static String METHOD_RESETPASSWORD = USERURL+"ResetPassword";
	/**
	 * 常见联系人地址
	 */
	public static String METHOD_ADDRESSCREATE = ADDRESS+"Create";
	/**
	 * 获取我的联系人地址列表
	 */
	public static String METHOD_MYADDRESSCREATE_LIST = ADDRESS+"My";
	
	/**
	 * 获取联系人地址详细
	 */
	public static String METHOD_ADDRESSCREATE_DETAILS = ADDRESS+"Details";
	
	/**
	 * 删除联系人地址
	 */
	public static String METHOD_ADDRESSCREATE_DELETE = ADDRESS+"Delete";
	
	/**
	 * 修改联系人地址信息
	 */
	public static String METHOD_ADDRESSCREATE_UPDATE = ADDRESS+"Edit";
	
	/**
	 * 设置默认联系人地址信息
	 */
	public static String METHOD_ADDRESSCREATE_DEFAULT = ADDRESS+"SetDefault";
	
	/**
	 * 获取默认联系人地址
	 */
	public static String METHOD_ADDRESSCREATE_GETDEFAULT = ADDRESS+"Default";
}

