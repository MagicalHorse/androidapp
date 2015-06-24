package com.shenma.yueba.wxapi.utils;

import java.util.Random;

import net.sourceforge.simcpux.MD5;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-19 下午6:12:06  
 * 程序的简单说明  
 */

public class UtilsTool {
	
	/****
	 * 微信 推荐生成随机数
	 * ***/
	public static String genOutTradNo() {
		Random random = new Random();
		return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
	}
}
