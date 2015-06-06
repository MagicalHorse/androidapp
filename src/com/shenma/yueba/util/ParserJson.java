package com.shenma.yueba.util;

import java.lang.reflect.Type;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shenma.yueba.baijia.modle.BaseRequest;
import com.shenma.yueba.baijia.modle.LoginBackBean;
import com.shenma.yueba.baijia.modle.PhoneCodeBean;


/**
 * json解析类
 * 
 * @author zhou
 * 
 */
public class ParserJson {
	/**
	 * 解析便利宝所需要的数据
	 */
	public static BaseRequest parserBase(String jsonData) {
		BaseRequest bean = null;
		try {
			Type type = new TypeToken<BaseRequest>() {
			}.getType();
			Gson gson = new Gson();
			bean = gson.fromJson(jsonData, type);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return bean;
	}
	
	
	/**
	 * 登录
	 * @return
	 */
	public static LoginBackBean parserLogin(String jsonData){
		
		return null;
		
	}
	
	/**
	 * 获取验证码
	 * @param <T>
	 * @return
	 */
	public static PhoneCodeBean getvalidateCode(String jsonData){
		PhoneCodeBean bean = null;
		if(TextUtils.isEmpty(jsonData)){
			return null;
		}else {
			try {
				Type type = new TypeToken<PhoneCodeBean>() {
				}.getType();
				Gson gson = new Gson();
				bean = gson.fromJson(jsonData, type);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return bean;
		
	}
	
	
	

	
	
}
