package com.shenma.yueba.util;

import java.util.ArrayList;
import java.util.List;

import android.net.Uri;

import com.shenma.yueba.baijia.modle.RequestUploadProductDataBean;
import com.shenma.yueba.yangjia.modle.TagCacheBean;

public class PublishPicUtil {

	private RequestUploadProductDataBean bean;
	private String index = "0";
	private String from;
	private Uri uri;
	private boolean otherPic;
	private ArrayList<List<TagCacheBean>> tagCacheList = new ArrayList<List<TagCacheBean>>();//临时存放标签的集合

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
	public ArrayList<List<TagCacheBean>> getTagCacheList() {
		if(tagCacheList.size() == 0){
			tagCacheList.add(new ArrayList<TagCacheBean>());
			tagCacheList.add(new ArrayList<TagCacheBean>());
			tagCacheList.add(new ArrayList<TagCacheBean>());
		}
		return tagCacheList;
	}
	public void setTagCacheList(ArrayList<List<TagCacheBean>> tagCacheList) {
		this.tagCacheList = tagCacheList;
	}
	public boolean isOtherPic() {
		return otherPic;
	}
	public void setOtherPic(boolean otherPic) {
		this.otherPic = otherPic;
	}

	
	
}
