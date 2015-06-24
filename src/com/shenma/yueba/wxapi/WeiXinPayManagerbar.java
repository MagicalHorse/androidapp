package com.shenma.yueba.wxapi;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sourceforge.simcpux.Constants;
import net.sourceforge.simcpux.MD5;
import net.sourceforge.simcpux.Util;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.xmlpull.v1.XmlPullParser;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.CreatOrderInfoBean;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * @author gyj
 * @version 创建时间：2015-6-19 上午10:48:54 程序的简单说明
 */

public class WeiXinPayManagerbar {
	Context context;
	Map<String,String> resultunifiedorder;
	StringBuffer sb;
	IWXAPI msgApi;
	private ProgressDialog dialog;
	PayReq req;
	CreatOrderInfoBean creatOrderInfoBean;
	String messageTitle, messageDesc;
	public WeiXinPayManagerbar(Context context) {
		this.context = context;
		msgApi = WXAPIFactory.createWXAPI(context, null);
		msgApi.registerApp(Constants.APP_ID);
		req = new PayReq();
		sb=new StringBuffer();
	}
	
	/****
	 * 创建微信支付订单
	 * ***/
	public void createWenXinPay(CreatOrderInfoBean creatOrderInfoBean,String messageTitle,String messageDesc)
	{
		this.creatOrderInfoBean=creatOrderInfoBean;
		this.messageTitle=messageTitle;
		this.messageDesc=messageDesc;
		dialog = ProgressDialog.show(context,context.getString(R.string.app_tip), context.getString(R.string.getting_prepayid));
		GetPrepayIdTask getPrepayIdTask=new GetPrepayIdTask();
		getPrepayIdTask.execute();
	}
	
	
	/*****
	 * 获取预支付订单
	 * **/
	private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String,String>> {
		
		@Override
		protected void onPreExecute() {
			
		}

		@Override
		protected void onPostExecute(Map<String,String> result) {
			/*if (dialog != null) {
				dialog.dismiss();
			}*/
			if(!result.containsKey("return_code"))
			{
				showFail("启动失败，请重试");
				return;
			}else if(result.get("return_code").equals("SUCCESS"))
			{
				sb.append("prepay_id\n"+result.get("prepay_id")+"\n\n");
				Log.i("TAG", "weixin:prepay_id="+result.get("prepay_id"));
				//show.setText(sb.toString());

				resultunifiedorder=result;
	            //获取预支付订单号成功
				genPayReq();
				//sendPayReq();
				if(dialog!=null)
				{
					dialog.cancel();
				}
			}else 
			{
				String errormsg=result.get("return_msg");
				showFail(errormsg);
			}
			
		}		
		
		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected Map<String,String>  doInBackground(Void... params) {

			String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
			String entity = genProductArgs();

			Log.e("orion",entity);

			byte[] buf = Util.httpPost(url, entity);

			String content = new String(buf);
			Log.e("orion", content);
			Map<String,String> xml=decodeXml(content);

			return xml;
		}
	}
	
	private String genProductArgs() {
		StringBuffer xml = new StringBuffer();

		try {
			String	nonceStr = genNonceStr();


			xml.append("</xml>");
            List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
			packageParams.add(new BasicNameValuePair("appid", Constants.APP_ID));
			packageParams.add(new BasicNameValuePair("body", messageTitle));
			packageParams.add(new BasicNameValuePair("mch_id", Constants.MCH_ID));
			packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
			packageParams.add(new BasicNameValuePair("notify_url", com.shenma.yueba.constants.Constants.WX_NOTIFY_URL));
			packageParams.add(new BasicNameValuePair("out_trade_no",genOutTradNo()));
			packageParams.add(new BasicNameValuePair("spbill_create_ip","127.0.0.1"));
			packageParams.add(new BasicNameValuePair("total_fee", Integer.toString((int)(creatOrderInfoBean.getTotalAmount()*100))));
			packageParams.add(new BasicNameValuePair("trade_type", "APP"));


			String sign = genPackageSign(packageParams);
			packageParams.add(new BasicNameValuePair("sign", sign));


		   String xmlstring =toXml(packageParams);

			return xmlstring;

		} catch (Exception e) {
			return null;
		}
	

	}
	
	
	public Map<String,String> decodeXml(String content) {

		try {
			Map<String, String> xml = new HashMap<String, String>();
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {

				String nodeName=parser.getName();
				switch (event) {
					case XmlPullParser.START_DOCUMENT:

						break;
					case XmlPullParser.START_TAG:

						if("xml".equals(nodeName)==false){
							//实例化student对象
							xml.put(nodeName,parser.nextText());
						}
						break;
					case XmlPullParser.END_TAG:
						break;
				}
				event = parser.next();
			}

			return xml;
		} catch (Exception e) {
			Log.e("orion",e.toString());
		}
		return null;

	}
	
	private String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}
	
	private String genOutTradNo() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}
	
	/**
	 生成签名111
	 */

	private String genPackageSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constants.API_KEY);
		

		String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		Log.e("orion",packageSign);
		return packageSign;
	}
	
	
	private String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<"+params.get(i).getName()+">");


			sb.append(params.get(i).getValue());
			sb.append("</"+params.get(i).getName()+">");
		}
		sb.append("</xml>");

		Log.e("orion",sb.toString());
		return sb.toString();
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


		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

		req.sign = genAppSign(signParams);

		sb.append("sign\n"+req.sign+"\n\n");

		//show.setText(sb.toString());

		Log.e("orion", signParams.toString());

	}
	
	protected long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}
	
	private String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constants.API_KEY);

        this.sb.append("sign str\n"+sb.toString()+"\n\n");
		String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		Log.e("orion",appSign);
		return appSign;
	}
	
	
	
       
       void showFail(String errormsg)
       {
    	   MyApplication.getInstance().showMessage(context, errormsg);
    	   if(dialog!=null)
    	   {
    		   dialog.cancel();
    	   }
       }
       
       
       /*******
        * 查询微信订单
        * ***/
       public void QueryOrder()
       {
    	   
       }
       
       
       private class WenXinTask extends AsyncTask<Void, Void, Map<String,String>> {

		@Override
		protected Map<String, String> doInBackground(Void... params) {
			return null;
		}
		
		@Override
		protected void onPostExecute(Map<String, String> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
		
       }
}
