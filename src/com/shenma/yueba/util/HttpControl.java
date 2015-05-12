package com.shenma.yueba.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Handler;
import android.os.Message;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shenma.yueba.baijia.modle.BaseRequest;
import com.shenma.yueba.baijia.modle.CityListRequestBean;
import com.shenma.yueba.baijia.modle.UserInfo;
import com.shenma.yueba.baijia.modle.UserRequestBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.constants.HttpConstants;
import com.shenma.yueba.yangjia.modle.ContactsAddressRequestBean;
import com.shenma.yueba.yangjia.modle.ContactsAddressResponseBean;

/**
 * @author gyj
 * @version 创建时间：2015-5-10 下午4:29:03 程序的简单说明:本类定义 Http控制类 用于各种Http访问的的方法处理
 *          {@link HttpCallBackInterface}
 * @see #getCityList(HttpCallBackInterface, Context)
 * @see #sendPhoeCode(String, HttpCallBackInterface, Context)
 * @see #setLoginInfo(Context, UserRequestBean)
 * @see #createContactAddress(HttpCallBackInterface, Context, ContactsAddressResponseBean)
 * @see #registerUserInfo(String, String, String, int, HttpCallBackInterface, Context)
 * @see #validVerifyCode(String, String, HttpCallBackInterface, Context)
 * @see #updateLoginPwd(String, String, String, HttpCallBackInterface, Context)
 * 
 */
public class HttpControl {
	private  HttpUtils httpUtils;
	private  HttpControl httpControl;
	private final String setContentType = "application/json;charset=UTF-8";

	/****
	 * 创建对象
	 * **//*
	public static HttpControl the() {
		if (httpControl == null) {
			httpControl = new HttpControl();
		}
		httpControl = new HttpControl();
		return httpControl;
	}*/

	public HttpControl() {
		httpUtils = new HttpUtils();
	}

	/**
	 * 发送手机验证码
	 * 
	 * @param String str手机号码
	 * @param HttpCallBackInterface  回调接口
	 * @param Context
	 * @return void
	 * **/
	public void sendPhoeCode(String str,final HttpCallBackInterface httpCallBack, Context context) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constants.MOBILE, str.trim());
		BasehttpSend(map, context, HttpConstants.sendPhoneCode, httpCallBack, BaseRequest.class, true, true);
	}

	/**
	 * 校验手机验证码
	 * 
	 * @param String  phone手机号码
	 * @param String code验证码
	 * @param HttpCallBackInterface 回调接口
	 * @param Context
	 * @return void
	 * **/
	public void validVerifyCode(String phone, String code,final HttpCallBackInterface httpCallBack, Context context) {
		Map<String, String> map=new HashMap<String, String>();
		map.put(Constants.MOBILE, phone.trim());
		map.put(Constants.CODE, code.trim());
		BasehttpSend(map, context, HttpConstants.METHOD_VERIFYCODE, httpCallBack, BaseRequest.class, true, true);
	}

	/**
	 * 获取城市列表
	 * 
	 * @param HttpCallBackInterface 回调接口
	 * @param Context
	 * @return void
	 * **/
	public void getCityList(final HttpCallBackInterface httpCallBack,Context context) {
		BasehttpSend(null, context, HttpConstants.METHOD_GETCITYLIST, httpCallBack, CityListRequestBean.class, true, true);
	}

	/**
	 * 注册用户信息
	 * 
	 * @param String  phone手机号码
	 * @param String  name用户名
	 * @param String pwd密码
	 * @param String cityId城市ID
	 * @param HttpCallBackInterface 回调接口
	 * @param Context
	 * @return void
	 * **/
	public void registerUserInfo(String phone, String name, String pwd,int cityId, final HttpCallBackInterface httpCallBack,final Context context) {
		Map<String, String> map=new HashMap<String, String>();
		map.put(Constants.NAME, name.trim());
		map.put(Constants.MOBILE, phone.trim());
		map.put(Constants._PASSWORD, pwd.trim());
		map.put(Constants.CITYID, Integer.toString(cityId));
		BasehttpSend(map, context, HttpConstants.METHOD_REGISTER, httpCallBack, UserRequestBean.class, true, true);
	}

	/**
	 * 登录用户
	 * 
	 * @param String  phone手机号码
	 * @param String password密码
	 * @param HttpCallBackInterface 回调接口
	 * @param Context
	 * @return void
	 * **/
	public void userLogin(String phone, String password,final HttpCallBackInterface httpCallBack,final Context context) {
		Map<String, String> map=new HashMap<String, String>();
		map.put(Constants.MOBILE, phone.trim());
		map.put(Constants._PASSWORD, password.trim());
		BasehttpSend(map, context, HttpConstants.METHOD_LOGIN, httpCallBack, UserRequestBean.class, true, true);
	}

	/**
	 * 修改登录密码
	 * 
	 * @param String  phone手机号码
	 * @param String oldpassword旧密码
	 * @param String password新密码
	 * @param HttpCallBackInterface回调接口
	 * @param Context
	 * @return void
	 * **/
	public void updateLoginPwd(String phone, String password,String oldpassword,final HttpCallBackInterface httpCallBack, Context context) {
		Map<String, String> map=new HashMap<String, String>();
		map.put(Constants.MOBILE, phone.trim());
		map.put(Constants._PASSWORD, password.trim());
		map.put(Constants._OLDPASSWORD, oldpassword.trim());
		BasehttpSend(map, context, HttpConstants.METHOD_UPDATEPWD, httpCallBack, BaseRequest.class, true, true);
	}
	
	
	/**
	 * 重置密码
	 * 
	 * @param String  phone手机号码
	 * @param String password密码
	 * @param HttpCallBackInterface回调接口
	 * @param Context
	 * @return void
	 * **/
	public void resetPassword (String phone, String password,final HttpCallBackInterface httpCallBack, Context context) {
		Map<String, String> map=new HashMap<String, String>();
		map.put(Constants.MOBILE, phone.trim());
		map.put(Constants._PASSWORD, password.trim());
		BasehttpSend(map, context, HttpConstants.METHOD_RESETPASSWORD, httpCallBack, BaseRequest.class, true, true);
	}

	
	
	/**
	 * 创建联系人地址
	 * @param HttpCallBackInterface 回调接口
	 * @param Context
	 * @return void
	 * **/
	public void createContactAddress(final HttpCallBackInterface httpCallBack,Context context,ContactsAddressResponseBean responsebrean) {
        String responsejsonstr=BaseGsonUtils.getObjectToJson(responsebrean);
		if(responsejsonstr==null)
		{
			httpCallBack.http_Fails(500, "数据错误");
		}
		basehttpSendToJson(responsejsonstr, null, context, HttpConstants.METHOD_ADDRESSCREATE, httpCallBack, ContactsAddressRequestBean.class, true, true);
	}
	
	
	/********
	 * 基础Http传递json数据通用类
	 * @param String responsejsonstr JSON类型数据
	 * @param Map<String, String> map 传递的参数
	 * @param String method 访问的方法
	 * @param HttpCallBackInterface httpCallBackUi回调
	 * @param Class<T> classzz 泛型即相应成功后 返回的对象类型<T extends BaseRequest> 且 只能是BaseRequest 或 BaseRequest的 子类
	 * @param boolean isshwoDialog 是否显示等待对话框 true 显示 false不显示  默认不显示
	 * @param boolean isDialogCancell 是否可取消对话框 true 不可取消 false 可取消 默认 可取消
	 * ****/
	<T extends BaseRequest> void basehttpSendToJson(String responsejsonstr,Map<String, String> map,Context context,String method,final HttpCallBackInterface httpCallBack,final Class<T> classzz,boolean isshwoDialog,boolean isDialogCancell)
	{
		final CustomProgressDialog progressDialog = CustomProgressDialog.createDialog(context);
		if(isDialogCancell)
		{
			progressDialog.setCancelable(false);
		}
		if(isshwoDialog)
		{
			progressDialog.show();
		}
		httpSendToJson(responsejsonstr, map, context,method , new HttpRequestDataInterface() {
			
			@Override
			public void httpRequestDataInterface_Sucss(Object obj) {
				
				if(obj!=null && obj instanceof String)
				{
					String str=(String)obj;
					BaseRequest bean = BaseGsonUtils.getJsonToObject(classzz,str);
					if(bean==null)
					{
						httpRequestDataInterface_Fails("数据不存在");
					}else if(bean.getStatusCode()!=200)
					{
						httpRequestDataInterface_Fails(bean.getMessage());
					}
					else
					{
						httpCallBack.http_Success(bean);
					}
				}else
				{
					httpRequestDataInterface_Fails("数据不存在");
				}
				progressDialog.cancel();
			}
			
			@Override
			public void httpRequestDataInterface_Fails(String msg) {
				
				httpCallBack.http_Fails(400, msg);
				progressDialog.cancel();
			}
		});
	}
	
	
	/****
	 * 通用传输数据
	 * 用于上传JSON类型数据 返回url拼接后的字符串 
	 * 如 a=1&b=2&sign=md5
	 * **/
	String setBaseRequestJsonParams(Map<String, String> map, Context context,final String json) {
		StringBuilder stringBuilder=new StringBuilder();
		if (stringBuilder != null) {
			String versionName = "0";
			try {
				// 获取版本号
				PackageInfo pi = context.getPackageManager().getPackageInfo(
						context.getPackageName(), 0);
				versionName = pi.versionName;
				versionName="2.3";
			} catch (Exception e) {

			}
			if(map==null)
			{
				map=new HashMap<String, String>();
			}
			if (map != null) {
				map.put(Constants.HTTPCHANNEL, Constants.ANDROID);
				map.put(Constants.CLIENTVERSION, versionName);
				map.put(Constants.UUID, UUID.randomUUID().toString());
				String md5str = Md5Utils.md5ToString(map,json);
				map.put(Constants.SIGN, md5str);
				Set<String> set=map.keySet();
				if(set!=null && set.size()>0)
				{
					Iterator<String> iterator=set.iterator();
					while(iterator.hasNext())
					{
						String k=iterator.next();
						if(map.containsKey(k))
						{
							String v=map.get(k);
							stringBuilder.append(k);
							stringBuilder.append("=");
							stringBuilder.append(v);
							stringBuilder.append("&");
						}
						
					}
				}
			}
		}
		return stringBuilder.toString();
	}
	
	/****
	 * 通用传输数据
	 * 生成http传输对象 RequestParams
	 * @param Map<String, String> map 需要传递的参数 K=v K-传递的参数   V-对应的值
	 * **/
	RequestParams setBaseRequestParams(Map<String, String> map, Context context) {
		RequestParams params = new RequestParams();
		if (params != null) {
			String versionName = "0";
			try {
				// 获取版本号
				PackageInfo pi = context.getPackageManager().getPackageInfo(
						context.getPackageName(), 0);
				versionName = pi.versionName;
				versionName="2.3";
			} catch (Exception e) {

			}
			if(map==null)
			{
				map=new HashMap<String, String>();
			}
			if (map != null) {
				map.put(Constants.HTTPCHANNEL, Constants.ANDROID);
				map.put(Constants.CLIENTVERSION, versionName);
				map.put(Constants.UUID, UUID.randomUUID().toString());
				String md5str = Md5Utils.md5ToString(map,null);
				map.put(Constants.SIGN, md5str);
				Set<String> set=map.keySet();
				if(set!=null && set.size()>0)
				{
					Iterator<String> iterator=set.iterator();
					while(iterator.hasNext())
					{
						String k=iterator.next();
						if(map.containsKey(k))
						{
							String v=map.get(k);
							params.addBodyParameter(k, v);
						}
						
					}
				}
			}
		}
		return params;
	}

	

	/*****
	 * 访问网络基础Http 用于post上传K-V
	 * @param  Map<String, String> map上传的参数 k-V
	 * @param String  method 方法名
	 * @param RequestCallBack  <T> requestCallBack 回调
	 * @param Class<T> classzz 泛型即相应成功后 返回的对象类型<T extends BaseRequest> 且 只能是BaseRequest 或 BaseRequest的 子类
	 * @param boolean isshwoDialog 是否显示等待对话框 true 显示 false不显示  默认不显示
	 * @param boolean isDialogCancell 是否可取消对话框 true 不可取消 false 可取消 默认 可取消
	 * ***/
	<T extends BaseRequest> void BasehttpSend(final Map<String, String> map, final Context context,final String method,final HttpCallBackInterface httpCallBack,final Class<T> classzz,boolean isshwoDialog,boolean isDialogCancell) {
		final CustomProgressDialog progressDialog = CustomProgressDialog.createDialog(context);
		if(isDialogCancell)
		{
			progressDialog.setCancelable(false);
		}
		if(isshwoDialog)
		{
			progressDialog.show();
		}
		httpUtils.send(HttpMethod.POST, method.trim(),setBaseRequestParams(map, context),new RequestCallBack<String>() {
            
			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {

				if (httpCallBack != null) {
					try {
						BaseRequest bean = BaseGsonUtils.getJsonToObject(classzz,responseInfo.result);
						if (bean == null) {
							httpCallBack.http_Fails(0,"数据不存在");

						}else if(bean.getStatusCode() != 200)
						{
							httpCallBack.http_Fails(bean.getStatusCode(), bean.getMessage());
						}
						else {
							httpCallBack.http_Success(bean);
						}
					} catch (Exception e) {
						httpCallBack.http_Fails(0, "数据不存在");
					}

				}
				progressDialog.cancel();
			}

			@Override
			public void onFailure(HttpException error, String msg) {

				if (httpCallBack != null) {
					httpCallBack.http_Fails(0, msg);
				}
				progressDialog.cancel();
			}
		});
	}
	
	
	/*****
	 * 访问网络 上传JSON类型数据
	 * 
	 * @param RequestParams  params传输的 数据
	 * @param String  method 方法名
	 * @param RequestCallBack  <T> requestCallBack 回调
	 * ***/
	<T> void httpSendToJson(final String json,final Map<String, String> map, final Context context, String method,final HttpRequestDataInterface httpRequestDataInterface) {
		String parameter=setBaseRequestJsonParams(map, context, json);
		/*if(parameter!=null && !parameter.trim().equals(""))
		{
			try {
				method=method+"?"+URLEncoder.encode(parameter, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				
				e.printStackTrace();
			}
		}*/
		method=method+"?"+setBaseRequestJsonParams(map, context, json);
		sendHttpJson(method, json, httpRequestDataInterface);
	}

	
	/*******
	 * 本类定义 异步 上传JSON 数据
	 * @param String url 访问的url 
	 * @param String json 需要传递的json 格式的字符串
	 * @param HttpRequestDataInterface httpRequestDataInterface 回调接口
	 * @return void 
	 * ***/
	void sendHttpJson(final String url,final String json,final HttpRequestDataInterface httpRequestDataInterface)
	{
		final CustonHandler handler=new CustonHandler(){
			@Override
			public void handleMessage(Message msg) {
				
				super.handleMessage(msg);
				if(httpRequestDataInterface==null)
				{
					return;
				}
				switch(msg.what)
				{
				case 200:
					httpRequestDataInterface.httpRequestDataInterface_Sucss(msg.obj);
					break;
				case 400:
					httpRequestDataInterface.httpRequestDataInterface_Fails((String)msg.obj);
					break;
				}
			}
		};
		//启动线程进行数据请求
		new Thread()
		{
		public void run() {
			try {
				URL htturl = new URL(url);
				HttpURLConnection http=(HttpURLConnection) htturl.openConnection();
				http.setDoInput(true);
				http.setDoOutput(true);
				http.setReadTimeout(10000);
				http.setConnectTimeout(3000);
				http.setRequestMethod("POST");
				http.setRequestProperty("Content-type", "application/json;charset=UTF-8");
				 OutputStream  out=http.getOutputStream();
				 if(out!=null)
				 {
					 out.write(json.getBytes());
				 }else
				 {
					 handler.sendError("连接失败");
				 }
				InputStream io= http.getInputStream();
				StringBuilder sb=new StringBuilder();
				if(io!=null)
				{
					byte[] buffer=new byte[1024];
					int count=0;
					while((count=io.read(buffer, 0, 1024))!=-1)
					{
						sb.append(new String(buffer, 0, count, "UTF-8"));
					}
					String returnValue=URLDecoder.decode(sb.toString(), sb.toString().trim());
					handler.sendSucess(returnValue);
				}else
				{
					handler.sendError("连接失败");
				}
			} catch (Exception e) {
				
				e.printStackTrace();
				handler.sendError(e.getMessage());
			}
		};
		}.start();
	}
	
	/******
	 * Handler
	 * ****/
	class CustonHandler extends Handler
	{
		public void sendError(String errormsg)
		{
			Message msg=obtainMessage(400);
			msg.obj=errormsg;
			sendMessage(msg);
		}
		
		public void sendSucess(String sucessMsg)
		{
			Message msg=obtainMessage(200);
			msg.obj=sucessMsg;
			sendMessage(msg);
		}
	}
	
	/****
	 * 设置登录信息 存储到属性文件
	 * @param UserRequestBean bean 用户登录信息类
	 * ***/
	void setLoginInfo(Context context,UserRequestBean bean)
	{
		if(bean!=null)
		{
		  UserInfo userInfo=bean.getData();
		   SharedUtil.setStringPerfernece(context, SharedUtil.user_id, Integer.toString(userInfo.getId()));
		   SharedUtil.setStringPerfernece(context, SharedUtil.user_name, userInfo.getName());
		   SharedUtil.setStringPerfernece(context, SharedUtil.user_names, userInfo.getNickname());
		   SharedUtil.setStringPerfernece(context, SharedUtil.user_logo, userInfo.getLogo());
		   SharedUtil.setStringPerfernece(context, SharedUtil.user_logo_full, userInfo.getLogo_full());
		   SharedUtil.setStringPerfernece(context, SharedUtil.user_logobg, userInfo.getLogobg());
		   SharedUtil.setStringPerfernece(context, SharedUtil.user_logobg_s, userInfo.getLogobg_s());
		   SharedUtil.setStringPerfernece(context, SharedUtil.user_mobile, userInfo.getMobile());
		   SharedUtil.setStringPerfernece(context, SharedUtil.user_token, userInfo.getToken());
		   SharedUtil.setBooleanPerfernece(context, SharedUtil.user_loginstatus, true);
		}
	}
	
	/****
	 * 设置登录信息 存储到属性文件
	 * ***/
	void setUnLoginInfo(Context context)
	{
		   SharedUtil.setStringPerfernece(context, SharedUtil.user_id, null);
		   SharedUtil.setStringPerfernece(context, SharedUtil.user_name, null);
		   SharedUtil.setStringPerfernece(context, SharedUtil.user_names, null);
		   SharedUtil.setStringPerfernece(context, SharedUtil.user_logo, null);
		   SharedUtil.setStringPerfernece(context, SharedUtil.user_logo_full, null);
		   SharedUtil.setStringPerfernece(context, SharedUtil.user_logobg, null);
		   SharedUtil.setStringPerfernece(context, SharedUtil.user_logobg_s,null);
		   SharedUtil.setStringPerfernece(context, SharedUtil.user_mobile, null);
		   SharedUtil.setStringPerfernece(context, SharedUtil.user_token, null);
		   SharedUtil.setBooleanPerfernece(context, SharedUtil.user_loginstatus, false);
	}
	
	
	/****
	 * 定义Http接口回调类 用于接收返回的 成功与失败
	 * 
	 * @see #http_Success(Object)
	 * @see #http_Fails(int, String)
	 * ***/
	public interface HttpCallBackInterface {

		/**
		 * 返回成功
		 * 
		 * @param obj
		 * @return void
		 */
		void http_Success(Object obj);

		/**
		 * 返回失败
		 * 
		 * @param error
		 * @param msg
		 * @return void
		 */
		void http_Fails(int error, String msg);

	};
	
	/****
	 * 定义Http接口回调类 用于接收返回的 成功与失败
	 * 用于 异步上传Json类型数据时 的回调接口
	 * @see #httpRequestDataInterface_Sucss(Object)
	 * @see #httpRequestDataInterface_Fails(String)
	 * ***/
	private interface HttpRequestDataInterface
	{
		void httpRequestDataInterface_Sucss(Object obj);
		void httpRequestDataInterface_Fails(String msg);
	}
}
