package com.shenma.yueba.baijia.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.BaseAdapter;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.core.BitmapSize;
import com.shenma.yueba.R;


/**
 * 
 * @author a
 */
public abstract class BaseAdapterWithUtil extends BaseAdapter {
	public Context ctx;
	public BitmapUtils bitmapUtils;
	public BitmapDisplayConfig bigPicDisplayConfig;
	public BaseAdapterWithUtil(Context ctx) {
		this.ctx = ctx;
		bigPicDisplayConfig = new BitmapDisplayConfig();
	    //bigPicDisplayConfig.setShowOriginal(true); // 
	    bigPicDisplayConfig.setBitmapConfig(Bitmap.Config.RGB_565);
	    bigPicDisplayConfig.setBitmapMaxSize(BitmapCommonUtils.getScreenSize(ctx));
		bitmapUtils = new BitmapUtils(ctx);
		bitmapUtils.configDefaultLoadingImage(R.drawable.default_pic);//
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.default_pic);//
		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);//
	}
}
