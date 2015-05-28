package com.shenma.yueba.baijia.modle;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ImageStringBean {
BannersInfoBean bean;
ImageView iv;

public BannersInfoBean getBean() {
	return bean;
}

public void setBean(BannersInfoBean bean) {
	this.bean = bean;
}

public ImageView getIv() {
	return iv;
}

public void setIv(ImageView iv) {
	this.iv = iv;
}

public ImageStringBean(Activity activity,BannersInfoBean bean)
{
	this.bean=bean;
	this.iv=new ImageView(activity);
	iv.setScaleType(ScaleType.FIT_XY);
	this.iv.setTag(bean);
}
}
