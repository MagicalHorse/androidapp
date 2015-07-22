package com.shenma.yueba.wxapi;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sourceforge.simcpux.Constants;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.constants.HttpConstants;
import com.shenma.yueba.wxapi.bean.WenXinErrorBean;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-23 上午9:37:45  
 * 程序的简单说明   创建微信统一订单
 */

public class CreateWeiXinOrderManager extends WeiXinBasePayManager{
	public static final String WEIXINACTION_FILTER="com.shenma.yueba.weixinpay_intentfilter";
	WeiXinPayManagerListener listener;
	Map map;
	public CreateWeiXinOrderManager(Context context,WeiXinPayManagerListener listener, Map map) {
		super(context, listener, map);
		this.listener=listener;
		this.map=map;
	}

	@Override
	Object abs_doInBackground() {
		
		{
			String url = String.format(HttpConstants.METHOD_WXCREATEORDER);
			
		    String xml=genProductArgs();
			String requestxml=sendHttp(url, xml);
			return requestxml;
		}
	}

	@Override
	void abs_onPostExecute(Object obj) {
		if(obj==null)
		{
			sendFails("请求数据失败，请重试");
		}else
		{
			String str=(String)obj;
			Map<String,String> xml=decodeXml(str);
			resultunifiedorder=xml;
			if(xml.containsKey("err_code") && xml.containsKey("err_code_des"))
			{
				MyApplication.getInstance().showMessage(context, xml.get("err_code_des"));
				return;
			}
			
			if(!(xml.containsKey("return_code")))
			{
				sendFails("请求数据失败，请重试");
			}else if(xml.get("return_code").equals("FAIL"))
			{
				sendFails(xml.get("return_msg"));
			}else if(xml.get("return_code").equals("SUCCESS"))
			{
				if(xml.containsKey("result_code"))
				{
					if(xml.get("result_code").equals("SUCCESS"))
					{
						//微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时
						String prepay_id=xml.get("prepay_id");
						//执行第二部操作
						genPayReq();
						sendPayReq();
					}else
					{
						if(xml.containsKey("err_code"))
						{
							sendFails(WenXinErrorBean.getValue(xml.get("err_code")));
						}
					}
				}else
				{
					sendFails("请求数据失败，请重试");
				}
			}
		}
	}

	
	
	
	void sendFails(String msg)
	{
		if(listener!=null)
		{
			listener.fails(msg);
		}
	}
	
	
	/******
	 * 生成签名
	 * ***/
	private void genPayReq() {

		req.appId = Constants.APP_ID;
		req.partnerId = Constants.MCH_ID;
		req.prepayId = resultunifiedorder.get("prepay_id");
		req.packageValue = "Sign=WXPay";
		req.nonceStr = genNonceStr();
		req.timeStamp = String.valueOf(genTimeStamp());
        /*req.extData=(String)map.get("OrderNo");*/

		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

		req.sign = genAppSign(signParams);

		sb.append("sign\n"+req.sign+"\n\n");

	}
	
	/****
	 * 调起微信支付
	 * ***/
       private void sendPayReq() {
		msgApi.registerApp(Constants.APP_ID);
		msgApi.sendReq(req);
	}
       
       private String genProductArgs() {
   		try {
   			String	nonceStr = genNonceStr();
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
   			packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID));
   			packageParams.add(new BasicNameValuePair("body", (String)map.get("messageTitle")));
   			//packageParams.add(new BasicNameValuePair("body","商品名称")); 
   			//packageParams.add(new BasicNameValuePair("detail", (String)map.get("messageDetail")));//详细
   			packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
   			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
   			packageParams.add(new BasicNameValuePair("notify_url", com.shenma.yueba.constants.Constants.WX_NOTIFY_URL));
   			packageParams.add(new BasicNameValuePair("out_trade_no",(String)map.get("OrderNo")));//商户订单号
   			packageParams.add(new BasicNameValuePair("spbill_create_ip","127.0.0.1"));
   			double d=(Double)map.get("TotalAmount");
   			packageParams.add(new BasicNameValuePair("total_fee", Integer.toString((int)(d*100))));
   			packageParams.add(new BasicNameValuePair("trade_type", "APP"));


   			String sign = genPackageSign(packageParams);
   			packageParams.add(new BasicNameValuePair("sign", sign));


   		   String xmlstring =toXml(packageParams);
            xmlstring=new String(xmlstring.getBytes(), "ISO8859-1");
   			return xmlstring;

   		} catch (Exception e) {
   			return null;
   		}
   	

   	}
}
