package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-7 下午7:36:46  
 * 程序的简单说明  
 */

public class RequestUploadChatImageInfo implements Serializable{

	String imageurl="";

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
}
