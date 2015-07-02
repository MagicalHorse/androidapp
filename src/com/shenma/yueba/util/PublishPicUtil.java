package com.shenma.yueba.util;

import android.net.Uri;

import com.shenma.yueba.baijia.modle.RequestUploadProductDataBean;

public class PublishPicUtil {

	private RequestUploadProductDataBean bean;
	private String index = "0";
	private String from;
	private Uri uri;
	
	private static PublishPicUtil instance;
	public static PublishPicUtil getInstance() {
		if (instance == null) {
			instance = new PublishPicUtil();
		}
		return instance;
	}
	public RequestUploadProductDataBean getBean() {
		if(bean == null){
			bean = new RequestUploadProductDataBean();
		}
		return bean;
	}
	public void setBean(RequestUploadProductDataBean bean) {
		this.bean = bean;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public Uri getUri() {
		return uri;
	}
	public void setUri(Uri uri) {
		this.uri = uri;
	}

	
	
}
