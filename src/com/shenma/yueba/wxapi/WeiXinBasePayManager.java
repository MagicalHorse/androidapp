package com.shenma.yueba.wxapi;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sourceforge.simcpux.Constants;
import net.sourceforge.simcpux.MD5;
import net.sourceforge.simcpux.Util;

import org.apache.http.NameValuePair;
import org.xmlpull.v1.XmlPullParser;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * @author gyj
 * @version 创建时间：2015-6-19 上午10:48:54
 * 
 *          统一执行 操作
 */

public abstract class WeiXinBasePayManager {
	protected static String PREFIX_CDATA = "<![CDATA[";
	protected static String SUFFIX_CDATA = "]]>";

	Context context;
	Map<String, String> resultunifiedorder;
	StringBuffer sb;
	IWXAPI msgApi;
	private ProgressDialog dialog;
	PayReq req;
	WeiXinPayManagerListener listener;
	Map map;

	/*****
	 * @param context Context
	 * @param listener WeiXinPayManagerListener 成功失败接口
	 * @param str_array String[] 传递的对象参数
	 * ***/
	public WeiXinBasePayManager(Context context,WeiXinPayManagerListener listener,Map map) {
		this.context = context;
		msgApi = WXAPIFactory.createWXAPI(context, null);
		msgApi.registerApp(Constants.APP_ID);
		req = new PayReq();
		sb = new StringBuffer();
		this.listener = listener;
		this.map = map;
	}

	/*****
	 * 执行操作
	 * ***/
	public void execute() {
		dialog = ProgressDialog.show(context,
				context.getString(R.string.app_tip),
				context.getString(R.string.getting_prepayid));
		ExecuteTask executeTask = new ExecuteTask();
		executeTask.execute();
	}

	public interface WeiXinPayManagerListener {
		//成功
		void success(Object obj);
		//失败
		void fails(String msg);
	}

	private class ExecuteTask extends AsyncTask<Void, Void, Object> {

		@Override
		protected Object doInBackground(Void... params) {

			return abs_doInBackground();
		}

		@Override
		protected void onPostExecute(Object result) {
			abs_onPostExecute(result);
			if (dialog != null) {
				dialog.cancel();
			}
		}
	}

	/***
	 * 访问网络
	 * **/
	protected String sendHttp(String url, String content) {
		byte[] buf = Util.httpPost(url, content);
		if (buf == null) {
			return null;
		}
		String requestcontent = new String(buf);
		return requestcontent;
	}

	/******
	 * 后台执行耗时操作
	 * 
	 * @param _obj
	 *            参数对象
	 * ****/
	abstract Object abs_doInBackground();

	/****
	 * 操作的返回结果
	 * 
	 * @param obj
	 *            返回的结果对象
	 * ***/
	abstract void abs_onPostExecute(Object obj);

	/****
	 * 执行操作的返回签名
	 *  @param packageParams List<NameValuePair>字段
	 * @return String 返回签名信息
	 * ***/
	protected String genProductArgs(List<NameValuePair> packageParams) {
		StringBuffer xml = new StringBuffer();

		try {
			if(packageParams!=null && packageParams.size()>0)
			{
				String sign = genPackageSign(packageParams);
				return sign;
			}
			else {
				return "";
			}

		} catch (Exception e) {
			return null;
		}

	}
	
	
	public Map<String, String> decodeXml(String content) {

		try {
			Map<String, String> xml = new HashMap<String, String>();
			XmlPullParser parser = Xml.newPullParser();
			parser.setInput(new StringReader(content));
			int event = parser.getEventType();
			while (event != XmlPullParser.END_DOCUMENT) {

				String nodeName = parser.getName();
				switch (event) {
				case XmlPullParser.START_DOCUMENT:

					break;
				case XmlPullParser.START_TAG:

					if ("xml".equals(nodeName) == false) {
						// 实例化student对象
						xml.put(nodeName, parser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					break;
				}
				event = parser.next();
			}

			return xml;
		} catch (Exception e) {
			Log.e("orion", e.toString());
		}
		return null;

	}	

	/*******
	 * 获取随机数
	 * ****/
	protected String genNonceStr() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	protected String genOutTradNo() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000))
				.getBytes());
	}

	/**
	 * 生成签名
	 */

	protected String genPackageSign(List<NameValuePair> params) {
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
		Log.e("orion", packageSign);
		return packageSign;
	}

	/*****
	 * 生成xml 
	 * @param params List<NameValuePair> 字段对象
	 * ***/
	protected String toXml(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (int i = 0; i < params.size(); i++) {
			sb.append("<"+ params.get(i).getName() + ">");

			sb.append(PREFIX_CDATA);
			sb.append(params.get(i).getValue());
			sb.append(SUFFIX_CDATA);
			sb.append("</" + params.get(i).getName() + ">");
		}
		sb.append("</xml>");

		Log.e("orion", sb.toString());
		return sb.toString();
	}


	protected long genTimeStamp() {
		return System.currentTimeMillis() / 1000;
	}

	/*****
	 * 获取带 sign签名的  签名
	 * ***/
	protected String genAppSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(Constants.API_KEY);

		this.sb.append("sign str\n" + sb.toString() + "\n\n");
		String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
		Log.e("orion", appSign);
		return appSign;
	}

	
	protected void showFail(String errormsg) {
		MyApplication.getInstance().showMessage(context, errormsg);
		if (dialog != null) {
			dialog.cancel();
		}
	}

	
	private class WenXinTask extends AsyncTask<Void, Void, Map<String, String>> {

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
