package com.shenma.yueba.wxapi.bean;
/**  
 * @author gyj  
 * @version 创建时间：2015-6-23 上午10:15:29  
 * 程序的简单说明  
 */

public class StaticWenXinErrorBean {
	private String NOAUTH="商户无此接口权限";
	private String NOTENOUGH="余额不足";
	private String ORDERPAID="商户订单已支付";
	private String ORDERCLOSED="订单已关闭";
	private  String SYSTEMERROR="系统错误";
	private  String APPID_NOT_EXIST="APPID不存在";
	private  String MCHID_NOT_EXIST="MCHID不存在";
	private  String APPID_MCHID_NOT_MATCH="appid和mch_id不匹配";
	private  String LACK_PARAMS="缺少参数";
	private  String OUT_TRADE_NO_USED="商户订单号重复";
	private  String SIGNERROR="签名错误";
	private  String XML_FORMAT_ERROR="XML格式错误";
	private  String REQUIRE_POST_METHOD="请使用post方法";
	private  String POST_DATA_EMPTY="post数据为空";
	private  String NOT_UTF8="编码格式错误";
	private String getNOAUTH() {
		return NOAUTH;
	}
	private void setNOAUTH(String nOAUTH) {
		NOAUTH = nOAUTH;
	}
	private String getNOTENOUGH() {
		return NOTENOUGH;
	}
	private void setNOTENOUGH(String nOTENOUGH) {
		NOTENOUGH = nOTENOUGH;
	}
	private String getORDERPAID() {
		return ORDERPAID;
	}
	private void setORDERPAID(String oRDERPAID) {
		ORDERPAID = oRDERPAID;
	}
	private String getORDERCLOSED() {
		return ORDERCLOSED;
	}
	private void setORDERCLOSED(String oRDERCLOSED) {
		ORDERCLOSED = oRDERCLOSED;
	}
	private String getSYSTEMERROR() {
		return SYSTEMERROR;
	}
	private void setSYSTEMERROR(String sYSTEMERROR) {
		SYSTEMERROR = sYSTEMERROR;
	}
	private String getAPPID_NOT_EXIST() {
		return APPID_NOT_EXIST;
	}
	private void setAPPID_NOT_EXIST(String aPPID_NOT_EXIST) {
		APPID_NOT_EXIST = aPPID_NOT_EXIST;
	}
	private String getMCHID_NOT_EXIST() {
		return MCHID_NOT_EXIST;
	}
	private void setMCHID_NOT_EXIST(String mCHID_NOT_EXIST) {
		MCHID_NOT_EXIST = mCHID_NOT_EXIST;
	}
	private String getAPPID_MCHID_NOT_MATCH() {
		return APPID_MCHID_NOT_MATCH;
	}
	private void setAPPID_MCHID_NOT_MATCH(String aPPID_MCHID_NOT_MATCH) {
		APPID_MCHID_NOT_MATCH = aPPID_MCHID_NOT_MATCH;
	}
	private String getLACK_PARAMS() {
		return LACK_PARAMS;
	}
	private void setLACK_PARAMS(String lACK_PARAMS) {
		LACK_PARAMS = lACK_PARAMS;
	}
	private String getOUT_TRADE_NO_USED() {
		return OUT_TRADE_NO_USED;
	}
	private void setOUT_TRADE_NO_USED(String oUT_TRADE_NO_USED) {
		OUT_TRADE_NO_USED = oUT_TRADE_NO_USED;
	}
	private String getSIGNERROR() {
		return SIGNERROR;
	}
	private void setSIGNERROR(String sIGNERROR) {
		SIGNERROR = sIGNERROR;
	}
	private String getXML_FORMAT_ERROR() {
		return XML_FORMAT_ERROR;
	}
	private void setXML_FORMAT_ERROR(String xML_FORMAT_ERROR) {
		XML_FORMAT_ERROR = xML_FORMAT_ERROR;
	}
	private String getREQUIRE_POST_METHOD() {
		return REQUIRE_POST_METHOD;
	}
	private void setREQUIRE_POST_METHOD(String rEQUIRE_POST_METHOD) {
		REQUIRE_POST_METHOD = rEQUIRE_POST_METHOD;
	}
	private String getPOST_DATA_EMPTY() {
		return POST_DATA_EMPTY;
	}
	private void setPOST_DATA_EMPTY(String pOST_DATA_EMPTY) {
		POST_DATA_EMPTY = pOST_DATA_EMPTY;
	}
	private String getNOT_UTF8() {
		return NOT_UTF8;
	}
	private void setNOT_UTF8(String nOT_UTF8) {
		NOT_UTF8 = nOT_UTF8;
	}
}
