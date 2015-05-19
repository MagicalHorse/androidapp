package com.shenma.yueba.baijia.modle;

import android.app.Activity;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ImageStringBean {
String iconurl="";
ImageView iv;

public ImageStringBean(Activity activity,String iconurl)
{
	this.iconurl=iconurl;
	this.iv=new ImageView(activity);
	iv.setScaleType(ScaleType.FIT_XY);
	this.iv.setTag(iconurl);
}

public String getIconurl() {
	return iconurl;
}
public void setIconurl(String iconurl) {
	this.iconurl = iconurl;
}
public ImageView getIv() {
	return iv;
}
public void setIv(ImageView iv) {
	this.iv = iv;
}

}
