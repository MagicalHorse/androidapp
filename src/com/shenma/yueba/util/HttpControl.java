package com.shenma.yueba.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.SystemClock;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shenma.yueba.baijia.modle.CityListRequestBean;
import com.shenma.yueba.baijia.modle.CommonBackBean;
import com.shenma.yueba.baijia.modle.UserRequestBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.constants.HttpConstants;

/**
 * @author gyj
 * @version 创建时间：2015-5-10 下午4:29:03 程序的简单说明:本类定义 Http控制类 用于各种Http访问的的方法处理
 *          {@link HttpCallBackInterface}
 * @see #getCityList(HttpCallBackInterface, Context)
 * @see #sendPhoeCode(String, HttpCallBackInterface, Context)
 * @see #sendPhoeCode(String, HttpCallBackInterface, Context)
 * 
 */
public class HttpControl {
	private static HttpUtils httpUtils;
	private static HttpControl httpControl;
	private final String setContentType = "application/json;charset=UTF-8";

	public static HttpControl the() {
		if (httpControl == null) {
			httpControl = new HttpControl();
		}
		return httpControl;
	}

	private HttpControl() {
		httpUtils = new HttpUtils();
	}

	/**
	 * 发送手机验证码
	 * 
	 * @param String
	 *            str手机号码
	 * @param HttpCallBackInterface
	 *            回调接口
	 * @param Context
	 * @return void
	 * **/
	public void sendPhoeCode(String str,
			final HttpCallBackInterface httpCallBack, Context context) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(Constants.MOBILE, str.trim());
		final CustomProgressDialog progressDialog = CustomProgressDialog.createDialog(context);
		progressDialog.setCancelable(false);
		progressDialog.show();
		httpSend(map, context, HttpConstants.sendPhoneCode,new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

						try {
							CommonBackBean bean = BaseGsonUtils
									.getJsonToObject(CommonBackBean.class,
											responseInfo.result);

							if (bean == null || bean.getStatusCode() != 200) {
								httpCallBack.http_Fails(bean.getStatusCode(), bean.getMessage());

							} else {
								httpCallBack.http_Success(bean);
							}
						} catch (Exception e) {
							httpCallBack.http_Fails(0, "数据不存在");
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

	/**
	 * 校验手机验证码
	 * 
	 * @param String
	 *            phone手机号码
	 * @param String
	 *            code验证码
	 * @param HttpCallBackInterface
	 *            回调接口
	 * @param Context
	 * @return void
	 * **/
	public void validVerifyCode(String phone, String code,
			final HttpCallBackInterface httpCallBack, Context context) {
		Map<String, String> map=new HashMap<String, String>();
		map.put(Constants.MOBILE, phone.trim());
		map.put(Constants.CODE, code.trim());
		final CustomProgressDialog progressDialog = CustomProgressDialog.createDialog(context);
		progressDialog.setCancelable(false);
		progressDialog.show();
		httpSend(map,context, HttpConstants.METHOD_VERIFYCODE,new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

						if (httpCallBack != null) {
							try {
								CommonBackBean bean = BaseGsonUtils
										.getJsonToObject(CommonBackBean.class,
												responseInfo.result);
								if (bean == null || bean.getStatusCode() != 200) {
									httpCallBack.http_Fails(bean.getStatusCode(), bean.getMessage());

								} else {
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

	/**
	 * 获取城市列表
	 * 
	 * @param HttpCallBackInterface
	 *            回调接口
	 * @param Context
	 * @return void
	 * **/
	public void getCityList(final HttpCallBackInterface httpCallBack,
			Context context) {

		final CustomProgressDialog progressDialog = CustomProgressDialog.createDialog(context);
		progressDialog.setCancelable(false);
		progressDialog.show();
		Map<String, String> map=new HashMap<String, String>();
		httpSend(map,context,HttpConstants.METHOD_GETCITYLIST,new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

						if (httpCallBack != null) {
							try {
								/*CityListRequestBean request = new CityListRequestBean();
								CityBeanList lista = new CityBeanList();
								lista.setKey("A");
								List<CityBean> arraya = new ArrayList<CityBean>();
								arraya.add(new CityBean("A", "阿尔卑斯", 1001));
								arraya.add(new CityBean("A", "阿拉斯加", 1002));
								arraya.add(new CityBean("A", "阿尔胡", 1003));
								lista.setList(arraya);

								List<CityBeanList> requestlist = new ArrayList<CityBeanList>();
								requestlist.add(lista);

								CityBeanList listb = new CityBeanList();
								List<CityBean> arrayb = new ArrayList<CityBean>();
								arrayb.add(new CityBean("B", "北京", 2001));
								arrayb.add(new CityBean("B", "北海", 2002));
								arrayb.add(new CityBean("B", "别克", 2003));
								listb.setList(arrayb);
								requestlist.add(listb);
								request.setData(requestlist);

								String json_str = BaseGsonUtils
										.getObjectToJson(request);

								CityListRequestBean bean = BaseGsonUtils
										.getJsonToObject(
												CityListRequestBean.class,
												json_str);*/
								CityListRequestBean bean=BaseGsonUtils.getJsonToObject(CityListRequestBean.class,responseInfo.result);
								if (bean == null || bean.getStatusCode() != 200) {
									httpCallBack.http_Fails(bean.getStatusCode(), bean.getMessage());
								} else {
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

	/**
	 * 注册用户信息
	 * 
	 * @param String
	 *            phone手机号码
	 * @param String
	 *            name用户名
	 * @param String
	 *            pwd密码
	 * @param String
	 *            cityId城市ID
	 * @param HttpCallBackInterface
	 *            回调接口
	 * @param Context
	 * @return void
	 * **/
	public void registerUserInfo(String phone, String name, String pwd,
			int cityId, final HttpCallBackInterface httpCallBack,
			Context context) {
		Map<String, String> map=new HashMap<String, String>();
		map.put(Constants.NAME, name.trim());
		map.put(Constants.MOBILE, phone.trim());
		map.put(Constants._PASSWORD, pwd.trim());
		map.put(Constants.CITYID, Integer.toString(cityId));
		final CustomProgressDialog progressDialog = CustomProgressDialog.createDialog(context);
		progressDialog.setCancelable(false);
		progressDialog.show();

		httpSend(map,context, HttpConstants.METHOD_REGISTER,new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

						if (httpCallBack != null) {
							try {
								UserRequestBean bean = BaseGsonUtils
										.getJsonToObject(UserRequestBean.class,
												responseInfo.result);
								if (bean == null || bean.getStatusCode() != 200) {
									httpCallBack.http_Fails(bean.getStatusCode(), bean.getMessage());

								} else {
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

	/**
	 * 登录
	 * 
	 * @param String
	 *            phone手机号码
	 * @param String
	 *            password密码
	 * @param HttpCallBackInterface
	 *            回调接口
	 * @param Context
	 * @return void
	 * **/
	public void registerUserInfo(String phone, String password,
			final HttpCallBackInterface httpCallBack, Context context) {
		Map<String, String> map=new HashMap<String, String>();
		map.put(Constants.MOBILE, phone.trim());
		map.put(Constants._PASSWORD, password.trim());
		final CustomProgressDialog progressDialog = CustomProgressDialog
				.createDialog(context);
		progressDialog.show();

		httpSend(map,context, HttpConstants.METHOD_LOGIN,new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

						if (httpCallBack != null) {
							try {
								UserRequestBean bean = BaseGsonUtils
										.getJsonToObject(UserRequestBean.class,
												responseInfo.result);
								if (bean == null || bean.getStatusCode() != 200) {
									httpCallBack.http_Fails(bean.getStatusCode(), bean.getMessage());

								} else {
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

	/**
	 * 修改登录密码
	 * 
	 * @param String
	 *            phone手机号码
	 * @param String
	 *            password密码
	 * @param HttpCallBackInterface
	 *            回调接口
	 * @param Context
	 * @return void
	 * **/
	public void registerUserInfos(String phone, String password,
			final HttpCallBackInterface httpCallBack, Context context) {
		Map<String, String> map=new HashMap<String, String>();
		map.put(Constants.MOBILE, phone.trim());
		map.put(Constants._PASSWORD, password.trim());
		final CustomProgressDialog progressDialog = CustomProgressDialog
				.createDialog(context);
		progressDialog.show();
        httpSend(map,context, HttpConstants.METHOD_UPDATEPWD,new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

						if (httpCallBack != null) {
							try {
								UserRequestBean bean = BaseGsonUtils
										.getJsonToObject(UserRequestBean.class,
												responseInfo.result);
								if (bean == null || bean.getStatusCode() != 200) {
									httpCallBack.http_Fails(bean.getStatusCode(), bean.getMessage());

								} else {
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

	/****
	 * 通用传输数据
	 * **/
	RequestParams setBaseRequestParams(final Map<String, String> map, Context context) {
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
			if (map != null) {
				map.put(Constants.HTTPCHANNEL, Constants.ANDROID);
				map.put(Constants.CLIENTVERSION, versionName);
				map.put(Constants.UUID, Long.toString(SystemClock.currentThreadTimeMillis()));
				String md5str = Md5Utils.md5ToString(map);
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
	 * 访问网络
	 * 
	 * @param RequestParams
	 *            params传输的 数据
	 * @param String
	 *            method 方法名
	 * @param RequestCallBack
	 *            <T> requestCallBack 回调
	 * ***/
	<T> void httpSend(final Map<String, String> map, final Context context,final String method,final RequestCallBack<T> requestCallBack) {
		httpUtils.send(HttpMethod.POST, method,setBaseRequestParams(map, context), requestCallBack);

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
}
