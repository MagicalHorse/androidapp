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
import com.shenma.yueba.baijia.modle.BrandDetailInfoBean;
import com.shenma.yueba.baijia.modle.BuyerIndexInfoBean;
import com.shenma.yueba.baijia.modle.CityListRequestBean;
import com.shenma.yueba.baijia.modle.ProvinceCityListBeanRequest;
import com.shenma.yueba.baijia.modle.RequestProductListInfoBean;
import com.shenma.yueba.baijia.modle.RequestUploadProductInfoBean;
import com.shenma.yueba.baijia.modle.ResponseUploadProductInfoBean;
import com.shenma.yueba.baijia.modle.UserInfo;
import com.shenma.yueba.baijia.modle.UserRequestBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.constants.HttpConstants;
import com.shenma.yueba.yangjia.modle.BuyerProductManagerListBack;
import com.shenma.yueba.yangjia.modle.BuyerProductManagerListBean;
import com.shenma.yueba.yangjia.modle.ContactsAddressRequestBean;
import com.shenma.yueba.yangjia.modle.ContactsAddressRequestListBean;
import com.shenma.yueba.yangjia.modle.ContactsAddressResponseBean;
import com.shenma.yueba.yangjia.modle.OrderDetailBackBean;
import com.shenma.yueba.yangjia.modle.OrderListBackBean;


/**
 * @author gyj
 * @version 创建时间：2015-5-10 下午4:29:03 程序的简单说明:本类定义 Http控制类 用于各种Http访问的的方法处理
 *          {@link HttpCallBackInterface}
 * @see #getCityList(HttpCallBackInterface, Context)
 * @see #getAllCityList(HttpCallBackInterface, Context)
 * @see #sendPhoeCode(String, HttpCallBackInterface, Context)
 * @see #setLoginInfo(Context, UserRequestBean)
 * @see #createContactAddress(HttpCallBackInterface, Context, ContactsAddressResponseBean)
 * @see #registerUserInfo(String, String, String, int, HttpCallBackInterface, Context)
 * @see #validVerifyCode(String, String, HttpCallBackInterface, Context)
 * @see #updateLoginPwd(String, String, String, HttpCallBackInterface, Context)
 * @see #getContactAddressDetails(HttpCallBackInterface, Context, int)
 * @see #getDefaultContactAddress(HttpCallBackInterface, Context, int)
 * @see #getDeleteContactAddress(HttpCallBackInterface, Context, int)
 * @see #getMyContactAddressList(HttpCallBackInterface, Context, int)
 * @see #getUpdateContactAddress(HttpCallBackInterface, Context, ContactsAddressResponseBean)
 * @see #setDefaultContactAddress(HttpCallBackInterface, Context, int)
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
	 * @param str String 手机号码
	 * @param httpCallBack HttpCallBackInterface  回调接口
	 * @param context
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
	 * @param phone String 手机号码
	 * @param code String验证码
	 * @param httpCallBack HttpCallBackInterface 回调接口
	 * @param context Context
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
	 * @param httpCallBack HttpCallBackInterface 回调接口
	 * @param context Context
	 * @return void
	 * **/
	public void getCityList(final HttpCallBackInterface httpCallBack,Context context) {
		BasehttpSend(null, context, HttpConstants.METHOD_GETCITYLIST, httpCallBack, CityListRequestBean.class, true, true);
	}
	
	
	/**
	 * 获取省市地区所有数据
	 * @param httpCallBack HttpCallBackInterface 回调接口
	 * @param context Context
	 * @return void
	 * **/
	public void getAllCityList(final HttpCallBackInterface httpCallBack,Context context) {
		BasehttpSend(null, context, HttpConstants.METHOD_ALLGETCITYLIST, httpCallBack, ProvinceCityListBeanRequest.class, true, true);
	}

	/**
	 * 注册用户信息
	 * 
	 * @param phone   String手机号码
	 * @param name    String用户名
	 * @param pwd     String密码
	 * @param cityId  String 城市ID
	 * @param httpCallBack HttpCallBackInterface 回调接口
	 * @param context Context
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
	 * @param phone      String手机号码
	 * @param password   String密码
	 * @param httpCallBack HttpCallBackInterface 回调接口
	 * @param context  Context
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
	 * @param phone            String手机号码
	 * @param oldpassword      String旧密码
	 * @param password        String新密码
	 * @param httpCallBack HttpCallBackInterface回调接口
	 * @param context Context
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
	 * @param phone           String手机号码
	 * @param password        String 密码
	 * @param httpCallBack HttpCallBackInterface回调接口
	 * @param context Context
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
	 * @param httpCallBack  HttpCallBackInterface 回调接口
	 * @param context  Context
	 * @param responsebrean  ContactsAddressResponseBean联系人地址信息
	 * @return void
	 * **/
	public void createContactAddress(final HttpCallBackInterface httpCallBack,Context context,ContactsAddressResponseBean responsebrean) {
        String responsejsonstr=BaseGsonUtils.getObjectToJson(responsebrean);
		if(responsejsonstr==null)
		{
			httpCallBack.http_Fails(500, "发送数据错误");
		}
		basehttpSendToJson(responsejsonstr, null, context, HttpConstants.METHOD_ADDRESSCREATE, httpCallBack, ContactsAddressRequestBean.class, true, true);
	}
	
	
	/**
	 * 获取联系人地址详细信息
	 * @param httpCallBack   HttpCallBackInterface 回调接口
	 * @param context  Context
	 * @param Id  int  指定id
	 * @return void
	 * **/
	public void getContactAddressDetails(final HttpCallBackInterface httpCallBack,Context context,int Id ) {
        String json="{'Id':"+Id+"}";
		basehttpSendToJson(json, null, context, HttpConstants.METHOD_ADDRESSCREATE_DETAILS, httpCallBack, ContactsAddressRequestBean.class, true, true);
	}
	
	/**
	 * 获取联系人地址列表
	 * @param httpCallBack  HttpCallBackInterface 回调接口
	 * @param context  Context
	 * @param Id  int
	 * @return void
	 * **/
	public void getMyContactAddressList(final HttpCallBackInterface httpCallBack,Context context,int Id ) {
        String json="{'Id':"+Id+"}";
		basehttpSendToJson(json, null, context, HttpConstants.METHOD_MYADDRESSCREATE_LIST, httpCallBack, ContactsAddressRequestListBean.class, true, true);
	}
	
	
	/**
	 * 删除联系人地址详细信息
	 * @param httpCallBack  HttpCallBackInterface 回调接口
	 * @param context  Context
	 * @param Id  int 删除的ID
	 * @return void
	 * **/
	public void getDeleteContactAddress(final HttpCallBackInterface httpCallBack,Context context,int Id ) {
        String json="{'id':"+Id+"}";
		basehttpSendToJson(json, null, context, HttpConstants.METHOD_ADDRESSCREATE_DELETE, httpCallBack, BaseRequest.class, true, true);
	}
	
	
	/**
	 * 修改联系人地址详细信息
	 * @param httpCallBack  HttpCallBackInterface 回调接口
	 * @param context  Context
	 * @param bean  ContactsAddressResponseBean  修改的数据
	 * @return void
	 * **/
	public void getUpdateContactAddress(final HttpCallBackInterface httpCallBack,Context context,ContactsAddressResponseBean bean ) {
		String responsejsonstr=BaseGsonUtils.getObjectToJson(bean);
		if(responsejsonstr==null)
		{
			httpCallBack.http_Fails(500, "发送数据错误");
		}
		basehttpSendToJson(responsejsonstr, null, context, HttpConstants.METHOD_ADDRESSCREATE_UPDATE, httpCallBack, ContactsAddressRequestListBean.class, true, true);
	}
	
	
	/**
	 * 设置默认联系人地址详细信息
	 * @param httpCallBack  HttpCallBackInterface 回调接口
	 * @param context  Context
	 * @param Id int 
	 * @return void
	 * **/
	public void setDefaultContactAddress(final HttpCallBackInterface httpCallBack,Context context,int Id ) {
		String json="{'id':"+Id+"}";
		basehttpSendToJson(json, null, context, HttpConstants.METHOD_ADDRESSCREATE_DEFAULT, httpCallBack, BaseRequest.class, true, true);
	}
	
	
	/**
	 * 获取默认联系人地址详细信息
	 * @param httpCallBack HttpCallBackInterface 回调接口
	 * @param context  Context
	 * @param Id  int
	 * @return void
	 * **/
	public void getDefaultContactAddress(final HttpCallBackInterface httpCallBack,Context context,int Id ) {
		basehttpSendToJson(null, null, context, HttpConstants.METHOD_ADDRESSCREATE_GETDEFAULT, httpCallBack, ContactsAddressRequestBean.class, true, true);
	}
	
	
	/**
	 * 获取品牌信息详细
	 * @param httpCallBack HttpCallBackInterface 回调接口
	 * @param context  Context
	 * @param Id  int
	 * @return void
	 * **/
	public void getBrandDetailInfo(final HttpCallBackInterface httpCallBack,Context context,int BrandId ) {
		Map<String, String> map=new HashMap<String, String>();
		map.put(Constants.BRANDID, BrandId+"");
		BasehttpSend(map, context, HttpConstants.METHOD_BRANDMANAGEER_DETAIL, httpCallBack, BrandDetailInfoBean.class, true, true);
	}
	
	
	/**
	 * 获取品牌列表
	 * @param httpCallBack HttpCallBackInterface 回调接口
	 * @param context  Context
	 * @param Id  int
	 * @return void
	 * **/
	public void getBrandList(final HttpCallBackInterface httpCallBack,Context context,String type,String refreshts) {
		Map<String, String> map=new HashMap<String, String>();
		map.put(Constants.TYPE, type);
		map.put(Constants.REFRESHTS, refreshts);
		BasehttpSend(map, context, HttpConstants.METHOD_BRANDMANAGEER_ALL, httpCallBack, BrandDetailInfoBean.class, true, true);
	}
	
	
	
	
	/**
	 * 按照时间获取传入时间后的品牌列表
	 * @param httpCallBack HttpCallBackInterface 回调接口
	 * @param context  Context
	 * @param Id  int
	 * @return void
	 * **/
	public void getRecentBrandList(final HttpCallBackInterface httpCallBack,Context context,String type,String refreshts) {
		Map<String, String> map=new HashMap<String, String>();
		map.put(Constants.REFRESHTS, refreshts);
		BasehttpSend(map, context, HttpConstants.METHOD_BRANDMANAGEER_REFRESH, httpCallBack, BrandDetailInfoBean.class, true, true);
	}
	
	
	
	/**
	 * 按照时间获取传入时间后的品牌列表
	 * @param httpCallBack HttpCallBackInterface 回调接口
	 * @param context  Context
	 * @param Id  int
	 * @return void
	 * **/
//	public void getRecentBrandList(final HttpCallBackInterface httpCallBack,Context context,String type,String refreshts) {
//		Map<String, String> map=new HashMap<String, String>();
//		map.put(Constants.REFRESHTS, refreshts);
//		BasehttpSend(map, context, HttpConstants.METHOD_BRANDMANAGEER_GROUPALL, httpCallBack, BrandDetailInfoBean.class, true, true);
//	}
	
	
	
	
	/**
	 *  买手首页统计信息
	 * @param httpCallBack HttpCallBackInterface 回调接口
	 * @param context  Context
	 * @param Id  int
	 * @return void
	 * **/
	public void getBuyerIndexInfo(final HttpCallBackInterface httpCallBack,Context context,boolean refresh,boolean canCancle) {
		Map<String, String> map=new HashMap<String, String>();
		BasehttpSend(map, context, HttpConstants.METHOD_BUYER_INDEX, httpCallBack, BuyerIndexInfoBean.class, refresh,canCancle);
	}
	
	/**
	 *  获取买手在线商品列表（买手）
	 * @param httpCallBack HttpCallBackInterface 回调接口
	 * @param context  Context
	 * @param Id  int
	 * @return void
	 * **/
	public void getBuyerProductListForOnLine(String page,String pageSize,int status,final HttpCallBackInterface httpCallBack,Context context,boolean refresh,boolean canCancle) {
		Map<String, String> map=new HashMap<String, String>();
         map.put(Constants.PAGE, page);
		 map.put(Constants.PAGESIZE, pageSize);
		 map.put(Constants.STATUS, status+"");
		 BasehttpSend(map, context, HttpConstants.METHOD_PRODUCTLIST, httpCallBack, BuyerProductManagerListBack.class, refresh,canCancle);
	}
	
	
	
	
	/**
	 *   上线商品（买手）
	 * @param httpCallBack HttpCallBackInterface 回调接口
	 * @param context  Context
	 * @param Id  int
	 * @return void
	 * **/
	public void setProductOnLineOrOffLine(String id,int status,final HttpCallBackInterface httpCallBack,Context context,boolean refresh,boolean canCancle) {
		Map<String, String> map=new HashMap<String, String>();
		map.put(Constants.ID, id);
		map.put(Constants.STATUS, status+"");
		BasehttpSend(map, context, HttpConstants.METHOD_PRODUCTMANAGER_ONLINE, httpCallBack, BaseRequest.class, refresh,canCancle);
	}
	 
	
	/**
	 *   删除商品（买手）
	 * @param httpCallBack HttpCallBackInterface 回调接口
	 * @param context  Context
	 * @param Id  int
	 * @return void
	 * **/
	public void deleteProduct(String id,final HttpCallBackInterface httpCallBack,Context context,boolean refresh,boolean canCancle) {
		Map<String, String> map=new HashMap<String, String>();
		map.put(Constants.ID, id);
		BasehttpSend(map, context, HttpConstants.METHOD_PRODUCTMANAGER_DELETE, httpCallBack, BaseRequest.class, refresh,canCancle);
	}
	
	
	
	
	
	
	
	/**************************商品信息***********************************/
	/**
	 * 设置收藏商品
	 * @param httpCallBack  HttpCallBackInterface 回调接口
	 * @param context  Context
	 * @param productId int 商品编号
	 * @return void
	 * **/
	public void setFavor(int productId,final HttpCallBackInterface httpCallBack,Context context) {
		Map<String, String> map=new HashMap<String, String>();
		map.put(Constants.PRODUCTID, Integer.toString(productId));
		BasehttpSend(map, context, HttpConstants.METHOD_PRODUCTMANAGER_FAVOR, httpCallBack, BaseRequest.class, true, true);
	}
	
	
	/**
	 * 设置取消收藏商品
	 * @param httpCallBack  HttpCallBackInterface 回调接口
	 * @param context  Context
	 * @param productId int 商品编号
	 * @return void
	 * **/
	public void setUnFavor(int productId,final HttpCallBackInterface httpCallBack,Context context) {
		Map<String, String> map=new HashMap<String, String>();
		map.put(Constants.PRODUCTID, Integer.toString(productId));
		BasehttpSend(map, context, HttpConstants.METHOD_PRODUCTMANAGER_UNFAVOR, httpCallBack, BaseRequest.class, true, true);
	}
	
	
	/**
	 * 设置喜欢的商品
	 * @param httpCallBack  HttpCallBackInterface 回调接口
	 * @param context  Context
	 * @param productId int 商品编号
	 * @return void
	 * **/
	public void setLike(int productId,final HttpCallBackInterface httpCallBack,Context context) {
		Map<String, String> map=new HashMap<String, String>();
		map.put(Constants.PRODUCTID, Integer.toString(productId));
		BasehttpSend(map, context, HttpConstants.METHOD_PRODUCTMANAGER_LIKE, httpCallBack, BaseRequest.class, true, true);
	}
	
	
	/**
	 * 设置取消喜欢的商品
	 * @param httpCallBack  HttpCallBackInterface 回调接口
	 * @param context  Context
	 * @param productId int 商品编号
	 * @return void
	 * **/
	public void setUnLike(int productId,final HttpCallBackInterface httpCallBack,Context context) {
		Map<String, String> map=new HashMap<String, String>();
		map.put(Constants.PRODUCTID, Integer.toString(productId));
		BasehttpSend(map, context, HttpConstants.METHOD_PRODUCTMANAGER_UNLIKE, httpCallBack, BaseRequest.class, true, true);
	}
	
	
	/**
	 * 获取主页商品列表
	 * @param httpCallBack  HttpCallBackInterface 回调接口
	 * @param context  Context
	 * @param page int 请求页
	 * @param pagesize int 每页显示的条数
	 * @param isshowDialog boolean 是否显示对话框
	 * @param isEnableCancell boolean  返回键是否可用
	 * @return void
	 * **/
	public void getProduceHomeListData(int page,int pagesize,final HttpCallBackInterface httpCallBack,Context context,boolean isshowDialog,boolean isEnableCancell) {
		Map<String, String> map=new HashMap<String, String>();
		map.put(Constants.PAGE, Integer.toString(page));
		map.put(Constants.PAGESIZE, Integer.toString(pagesize));
		BasehttpSend(map, context, HttpConstants.METHOD_PRODUCTMANAGER_HOMELIST, httpCallBack, RequestProductListInfoBean.class, isshowDialog, isEnableCancell);
	}
	
	
	
	
	/**
	 * 获取商品被喜欢的用户列表
	 * @param httpCallBack  HttpCallBackInterface 回调接口
	 * @param context  Context
	 * @param productId int 商品编号
     * @param page int第几页
     * @param pagesize int每页显示的条数
	 * @return void
	 * **/
	public void setLikedUsers(int productId,int page,int pagesize,final HttpCallBackInterface httpCallBack,Context context) {
		Map<String, String> map=new HashMap<String, String>();
		map.put(Constants.PRODUCTID, Integer.toString(productId));
		map.put(Constants.PAGE, Integer.toString(page));
		map.put(Constants.PAGESIZE, Integer.toString(pagesize));
		BasehttpSend(map, context, HttpConstants.METHOD_PRODUCTMANAGER_LIKEDUSERS, httpCallBack, BaseRequest.class, true, true);
	}
	
	
	/**
	 * 获取我收藏的买手的商品列表
	 * @param httpCallBack  HttpCallBackInterface 回调接口
	 * @param context  Context
	 * @param page int第几页
     * @param pagesize int每页显示的条数
	 * @return void
	 * **/
	public void getMyBuyerProductList(int page,int pagesize,final HttpCallBackInterface httpCallBack,Context context) {
		Map<String, String> map=new HashMap<String, String>();
		map.put(Constants.PAGE, Integer.toString(page));
		map.put(Constants.PAGESIZE, Integer.toString(pagesize));
		BasehttpSend(map, context, HttpConstants.METHOD_PRODUCTMANAGER_MYBUYERPRODUCTLIST, httpCallBack, BaseRequest.class, true, true);
	}
	
	
	
	/**
	 * 获取商品信息详情
	 * @param httpCallBack  HttpCallBackInterface 回调接口
	 * @param context  Context
	 * @param ProductId  int商品编号
     * @param  ReadType  int商品读取操作类型 可为空 1：记录本商品点击数
	 * @return void
	 * **/
	public void getMyBuyerProductDetails(int ProductId,int ReadType,final HttpCallBackInterface httpCallBack,Context context) {
		Map<String, String> map=new HashMap<String, String>();
		map.put("ProductId", Integer.toString(ProductId));
		map.put("ReadType", Integer.toString(ReadType));
		BasehttpSend(map, context, HttpConstants.METHOD_PRODUCTMANAGER_DETAIL, httpCallBack, BaseRequest.class, true, true);
	}
	
	
	/**
	 * 上传商品信息(买手)
	 * @param httpCallBack  HttpCallBackInterface 回调接口
	 * @param context  Context
	 * @param ProductId  int商品编号
     * @param  ReadType  int商品读取操作类型 可为空 1：记录本商品点击数
	 * @return void
	 * **/
	public void createBuyerProductInfo(ResponseUploadProductInfoBean bean,final HttpCallBackInterface httpCallBack,Context context) {
		String responsejsonstr=BaseGsonUtils.getObjectToJson(bean);
		if(responsejsonstr==null)
		{
			httpCallBack.http_Fails(500, "发送数据错误");
		}
		basehttpSendToJson(responsejsonstr, null, context, HttpConstants.METHOD_PRODUCTMANAGER_CREATE, httpCallBack, RequestUploadProductInfoBean.class, true, true);
	}
	
	
	/**
	 * 修改商品信息(买手)
	 * @param httpCallBack  HttpCallBackInterface 回调接口
	 * @param context  Context
	 * @param ProductId  int商品编号
     * @param  ReadType  int商品读取操作类型 可为空 1：记录本商品点击数
	 * @return void
	 * **/
	public void updateBuyerProductInfo(ResponseUploadProductInfoBean bean,final HttpCallBackInterface httpCallBack,Context context) {
		String responsejsonstr=BaseGsonUtils.getObjectToJson(bean);
		if(responsejsonstr==null)
		{
			httpCallBack.http_Fails(500, "发送数据错误");
		}
		basehttpSendToJson(responsejsonstr, null, context, HttpConstants.METHOD_PRODUCTMANAGER_UPDATE, httpCallBack, RequestUploadProductInfoBean.class, true, true);
	}
	
	
	
	
	
	
	/**
	 * 获取订单列表
	 * @return void
	 * **/
	public void getOrderList(int Page,String Pagesize,String OrderProductType,String Status,final HttpCallBackInterface httpCallBack,Context context) {
		Map<String, String> map=new HashMap<String, String>();
		map.put(Constants.PAGE, Integer.toString(Page));
		map.put(Constants.PAGESIZE, Pagesize);
		map.put(Constants.OrderProductType, OrderProductType);
		map.put(Constants.STATUS, Status);
		BasehttpSend(map, context, HttpConstants.METHOD_ORDER_GETALLORDERFORBUYER, httpCallBack, OrderListBackBean.class, true, true);
	}
	
	/**
	 * 获取订单详情
	 * @return void
	 * **/
	public void getOrderDetail(String orderNo,final HttpCallBackInterface httpCallBack,Context context) {
		Map<String, String> map=new HashMap<String, String>();
		map.put(Constants.ORDER_NO, orderNo);
		BasehttpSend(map, context, HttpConstants.METHOD_ORDER_GETORDERDETAIL, httpCallBack, OrderDetailBackBean.class, true, true);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/********
	 * 基础Http传递json数据通用类
	 * @param responsejsonstr       String  JSON类型数据
	 * @param map  Map<String, String> map 传递的参数
	 * @param method  String  访问的方法
	 * @param httpCallBack   HttpCallBackInterface Ui回调
	 * @param classzz  Class<T>  泛型即相应成功后 返回的对象类型<T extends BaseRequest> 且 只能是BaseRequest 或 BaseRequest的 子类
	 * @param isshwoDialog  boolean  是否显示等待对话框 true 显示 false不显示  默认不显示
	 * @param isDialogCancell  boolean  是否可取消对话框 true 不可取消 false 可取消 默认 可取消
	 * @return void
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
	 * @param  map  Map<String, String> 传递的参数
	 * @param  context Context
	 * @param  json  String传递的json数据  没有值传递NULL
	 * @return String
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
				map.put(Constants.TOKEN, SharedUtil.getStringPerfernece(context, SharedUtil.user_token));
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
	 * @param map  Map<String, String>  需要传递的参数 K=v K-传递的参数   V-对应的值
	 * @param context  Context 
	 * @return RequestParams
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
				map.put(Constants.TOKEN, SharedUtil.getStringPerfernece(context, SharedUtil.user_token));
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
	 * @param map  Map<String, String> 上传的参数 k-V
	 * @param method   String   方法名
	 * @param requestCallBack  RequestCallBack<T>  回调
	 * @param classzz  Class<T>  泛型即相应成功后 返回的对象类型<T extends BaseRequest> 且 只能是BaseRequest 或 BaseRequest的 子类
	 * @param isshwoDialog   boolean  是否显示等待对话框 true 显示 false不显示  默认不显示
	 * @param isDialogCancell  boolean  是否可取消对话框 true 不可取消 false 可取消 默认 可取消
	 * @param httpCallBack HttpCallBackInterface回调接口
	 * @param context Context
	 * @return void 
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
					BaseRequest bean = BaseGsonUtils.getJsonToObject(classzz,responseInfo.result);
					if (bean == null) {
						httpCallBack.http_Fails(0,"数据解析异常");

					}else if(bean.getStatusCode() != 200)
					{
						httpCallBack.http_Fails(bean.getStatusCode(), bean.getMessage());
					}
					else {
						httpCallBack.http_Success(bean);
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
	 * @param json String json类型的字符串
	 * @param map  Map<String, String> 传递的参数
	 * @param RequestParams  params传输的 数据
	 * @param context Context 
	 * @param method  String   方法名
	 * @param requestCallBack RequestCallBack<T>  回调
	 * @param httpRequestDataInterface HttpRequestDataInterface http接口
	 * @return void 
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
	 * @param url  String  访问的url 
	 * @param json  String  需要传递的json 格式的字符串
	 * @param httpRequestDataInterface  HttpRequestDataInterface  回调接口
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
	 * @param bean UserRequestBean  用户登录信息类
	 * @param context Context
	 * @return void
	 * ***/
	public void setLoginInfo(Context context,UserRequestBean bean)
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
	 * @param context Context 
	 * @return void 
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
		 * @param obj  Object
		 * @return void
		 */
		void http_Success(Object obj);

		/**
		 * 返回失败
		 * 
		 * @param error int
		 * @param msg  String
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
		/**
		 * json上传成功
		 * @param obj Object
		 * @return void
		 * */
		void httpRequestDataInterface_Sucss(Object obj);
		
		/**
		 * json上传失败
		 * @param msg String
		 * @return void
		 * */
		void httpRequestDataInterface_Fails(String msg);
	}
}
