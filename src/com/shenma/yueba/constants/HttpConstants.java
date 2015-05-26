package com.shenma.yueba.constants;

public class HttpConstants {

	private static String baseUrl = "http://123.57.52.187:8080/app/";
	private static String USERURL = baseUrl + "User/";// USER接口
	private static String COMMONURL = baseUrl + "Common/";
	private static String ADDRESS = baseUrl + "Address/";// 地址管理
	private static String BRAND = baseUrl + "Brand/";// 品牌管理
	private static String PRODUCT = baseUrl + "Product/";// 商品管理
	private static String ASSISTANT = baseUrl + "Assistant/";// 买手相关的接口
	private static String BUYER = baseUrl + "Buyer/";// 买手相关的接口
	private static String Order = baseUrl + "Order/";// 订单相关的接口
	
	
	
	
	/**
	 * 注册----获取手机短信验证码
	 */
	public static String sendPhoneCode = USERURL + "SendMobileCode";
	// 注册
	public static String RegisterCompleate = USERURL + "RegisterCompleate";
	/**
	 * 校验手机验证
	 */
	public static String METHOD_VERIFYCODE = USERURL + "VerifyCode";
	/**
	 * 获取城市列表
	 */
	public static String METHOD_GETCITYLIST = COMMONURL + "GetCityList";
	/**
	 * 所有城市列表包含省
	 */
	public static String METHOD_ALLGETCITYLIST = COMMONURL + "GetAllCity";
	/**
	 * 注册用户信息
	 */
	public static String METHOD_REGISTER = USERURL + "Register";
	/**
	 * 登录
	 */
	public static String METHOD_LOGIN = USERURL + "Login";
	/**
	 * 修改登录密码
	 */
	public static String METHOD_UPDATEPWD = USERURL + "ChangePassword";
	/**
	 * 重置手机密码
	 */
	public static String METHOD_RESETPASSWORD = USERURL + "ResetPassword";
	/**
	 * 常见联系人地址
	 */
	public static String METHOD_ADDRESSCREATE = ADDRESS + "Create";
	/**
	 * 获取我的联系人地址列表
	 */
	public static String METHOD_MYADDRESSCREATE_LIST = ADDRESS + "My";

	/**
	 * 获取联系人地址详细
	 */
	public static String METHOD_ADDRESSCREATE_DETAILS = ADDRESS + "Details";

	/**
	 * 删除联系人地址
	 */
	public static String METHOD_ADDRESSCREATE_DELETE = ADDRESS + "Delete";

	/**
	 * 修改联系人地址信息
	 */
	public static String METHOD_ADDRESSCREATE_UPDATE = ADDRESS + "Edit";

	/**
	 * 设置默认联系人地址信息
	 */
	public static String METHOD_ADDRESSCREATE_DEFAULT = ADDRESS + "SetDefault";

	/**
	 * 获取默认联系人地址
	 */
	public static String METHOD_ADDRESSCREATE_GETDEFAULT = ADDRESS + "Default";

	/**
	 * ---------------------------------品牌管理接口----------------------------------
	 * ---------
	 */

	/**
	 * 获取品牌信息详细
	 */
	public static String METHOD_BRANDMANAGEER_DETAIL = BRAND + "Detail";

	/**
	 * 获取品牌列表
	 */
	public static String METHOD_BRANDMANAGEER_ALL = BRAND + "All";

	/**
	 * 按照时间获取传入时间后的品牌列表
	 */
	public static String METHOD_BRANDMANAGEER_REFRESH = BRAND + "Refresh";

	/**
	 * 按照品牌首字母获取品牌列表
	 */
	public static String METHOD_BRANDMANAGEER_GROUPALL = BRAND + "GroupAll";

	/**
	 * 按照品牌首字母获取品牌列表
	 */
	public static String METHOD_BRANDMANAGEER_GROUPREFRESH = BRAND
			+ "GroupRefresh";

	/**
	 * ---------------------------------商品相关接口----------------------------------
	 * ---------
	 */

	/**
	 * 收藏商品
	 */
	public static String METHOD_PRODUCTMANAGER_FAVOR = PRODUCT + "Favor";
	/**
	 * 取消收藏商品
	 */
	public static String METHOD_PRODUCTMANAGER_UNFAVOR = PRODUCT + "Unfavor";
	/**
	 * 喜欢商品
	 */
	public static String METHOD_PRODUCTMANAGER_LIKE = PRODUCT + "Like";
	/**
	 * 取消喜欢商品
	 */
	public static String METHOD_PRODUCTMANAGER_UNLIKE = PRODUCT + "UnLike";
	/**
	 * 获取主页商品列表
	 */
	public static String METHOD_PRODUCTMANAGER_HOMELIST = PRODUCT + "HomeList";
	/**
	 * 获取商品被喜欢的用户列表
	 */
	public static String METHOD_PRODUCTMANAGER_LIKEDUSERS = PRODUCT
			+ "LikedUsers";
	/**
	 * 获取我收藏的买手的商品列表
	 */
	public static String METHOD_PRODUCTMANAGER_MYBUYERPRODUCTLIST = PRODUCT
			+ "MyBuyerProductList";
	/**
	 * 获取商品被喜欢的用户列表
	 */
	public static String METHOD_PRODUCTMANAGER_DETAIL = PRODUCT + "Detail";
	
	/**
	 * 上传商品信息(买手)
	 */
	public static String METHOD_PRODUCTMANAGER_CREATE = PRODUCT + "Create";
	
	/**
	 * 上传商品信息(买手)
	 */
	public static String METHOD_PRODUCTMANAGER_UPDATE = PRODUCT + "Update";
	
	
	/**
	 * 获取买手在线商品列表（买手）
	 */
	public static String METHOD_PRODUCTMANAGER_ONLINEPRODUCTS = PRODUCT + "OnLineProducts";
	/**
	 * 获取买手即将下线商品列表（买手）
	 */
	public static String METHOD_PRODUCTMANAGER_WILLOFFLINE = PRODUCT + "SoonDownProducts";
	/**
	 * 获取买手已经下线商品列表（买手）
	 */
	public static String METHOD_PRODUCTMANAGER_HASOFFLINE = PRODUCT + "DownLineProducts";

	
	/**
	 * 商品上线（买手）
	 */
	public static String METHOD_PRODUCTMANAGER_ONLINE = PRODUCT + "OnLine";
	
	
	/**
	 * 商品下线（买手）
	 */
	public static String METHOD_PRODUCTMANAGER_OFFLINE = PRODUCT + "DownLine";
	/**
	 * ---------------------------------买手相关接口----------------------------------
	 * ---------
	 */
	/**
	 * 获取自己的品牌列表
	 */
	public static String METHOD_ASSISTANT_BRANDS = ASSISTANT + "Brands";
	/**
	 * 获取销售码
	 */
	public static String METHOD_ASSISTANT_SALESCODES = ASSISTANT + "SalesCodes";
	/**
	 * 添加销售码
	 */
	public static String METHOD_ASSISTANT_SALESCODE_ADD = ASSISTANT
			+ "SalesCode_Add";
	/**
	 * 获取支持的银行列表
	 */
	public static String METHOD_ASSISTANT_AVAIL_BANKS = ASSISTANT
			+ "Avail_Banks";
	/**
	 * 获取分类
	 */
	public static String METHOD_ASSISTANT_AVAIL_TAG = ASSISTANT + "Avail_Tag";
	/**
	 * 根据品牌获取分类
	 */
	public static String METHOD_ASSISTANT_AVAIL_TAGBYBRAND = ASSISTANT
			+ "Avail_TagByBrand";
	/**
	 * 导购获取收入信息
	 */
	public static String METHOD_ASSISTANT_INCOME = ASSISTANT + "Income";
	/**
	 * 导购提现信息
	 */
	public static String METHOD_ASSISTANT_INCOME_RECEIVE = ASSISTANT
			+ "Income_Received";
	/**
	 * 导购正在申请的提现信息
	 */
	public static String METHOD_ASSISTANT_INCOME_REQUESTING = ASSISTANT
			+ "Income_Requesting";
	/**
	 * 导购申请的提现成功的信息
	 */
	public static String METHOD_ASSISTANT_INCOME_REQUEST_COMPLETED = ASSISTANT
			+ "Income_RequestCompleted";
	/**
	 * 导购申请的提现失败的信息
	 */
	public static String METHOD_ASSISTANT_INCOME_REQUESTFAILD = ASSISTANT
			+ "Income_RequestFaild";
	/**
	 * 导购历史提现信息
	 */
	public static String METHOD_ASSISTANT_INCOME_HAISTORY = ASSISTANT
			+ "Income_History";
	/**
	 * 导购冻结状态的提成信息
	 */
	public static String METHOD_ASSISTANT_INCOME_FROZEN = ASSISTANT
			+ "Income_Frozen";
	/**
	 * 导购可提现状态的提成信息
	 */
	public static String METHOD_ASSISTANT_INCOME_AVAIL = ASSISTANT
			+ "Income_Avail";
	/**
	 * 导购无效现状态的提成信息
	 */
	public static String METHOD_ASSISTANT_INCOME_VOID = ASSISTANT
			+ "Income_Void";
	/**
	 * 导购提现
	 */
	public static String METHOD_ASSISTANT_INCOME_REQUEST = ASSISTANT
			+ "Income_Request";
	/**
	 * 导购提现 红包发放
	 */
	public static String METHOD_ASSISTANT_INCOME_REQUEST_REDPACK = ASSISTANT
			+ "Income_Request_RedPack";
	/**
	 * 导购提现信息
	 */
	public static String METHOD_ASSISTANT_INCOME_RECEIVED_REDPACK = ASSISTANT
			+ "Income_Received_RedPack";
	/**
	 * 导购正在申请的提现信息
	 */
	public static String METHOD_ASSISTANT_INCOME_REQUESTING_REDPACK = ASSISTANT
			+ "Income_Requesting_RedPack";
	/**
	 * 导购申请的提现成功的信息
	 */
	public static String METHOD_ASSISTANT_INCOME_REQUESTCOMPLETED_REDPACK = ASSISTANT
			+ "Income_RequestCompleted_RedPack";

	/**
	 * 导购申请的提现失败的信息
	 */
	public static String METHOD_ASSISTANT_INCOME_REQUESTFAILD_REDPACK = ASSISTANT
			+ "Income_RequestFaild_RedPack";
	/**
	 * 导购更新店铺名称 对应user的nickname
	 */
	public static String METHOD_ASSISTANT_UPDETE_NAME = ASSISTANT
			+ "Update_Name";
	/**
	 * 导购更新手机
	 */
	public static String METHOD_ASSISTANT_UPDATE_MOBILE = ASSISTANT
			+ "Update_Mobile";
	/**
	 * 导购更新模板
	 */
	public static String METHOD_ASSISTANT_UPDATE_TEMPLATE = ASSISTANT
			+ "Update_Template";
	/**
	 * 导购获取最后一次使用的银行信息
	 */
	public static String METHOD_ASSISTANT_LATEST_BANKINFO = ASSISTANT
			+ "Latest_BankInfo";

	
	
	
	/**
	 * 获取订单列表
	 */
	
	public static String METHOD_ORDER_GETALLORDERFORBUYER = Order
			+ "GetOrderList";
	
	/**
	 * 获取订单详情
	 */
	
	public static String METHOD_ORDER_GETORDERDETAIL = Order
			+ "GetOrderDetail";
	
	
	
	
	
	/**
	 * buyer相关的接口
	 */
	
	/**
	 * 买手首页统计信息
	 */
	public static String METHOD_BUYER_INDEX = BUYER
			+ "Index";
}
