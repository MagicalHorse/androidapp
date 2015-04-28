package com.shenma.yueba.util;



import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.Bitmap;

/**
 * 本地内存缓存类
 * @author zhou
 *
 */
public class ImageMemoryCache {
	
	//图片缓存大小
	private static final int MAX_CACHE_SIZE = 8 * 1024 * 1024; // 8M
	private int count = 0;
	private HashMap<String, SoftReference<Bitmap>> mSoftBitmapCache;

	public ImageMemoryCache() {
		mSoftBitmapCache = new HashMap<String, SoftReference<Bitmap>>();
	}
	
	/**
	 * 放入图片
	 * @param key
	 * @param bitmap
	 * @return
	 */
	public boolean putBitmap(String key, Bitmap bitmap) {
		if(bitmap == null) {
			return false;
		}
		int size = bitmap.getRowBytes() * bitmap.getHeight();
		count += size;
		if(count > MAX_CACHE_SIZE) {
			clear();
			count = size;
		}
		synchronized (mSoftBitmapCache) {
			mSoftBitmapCache.put(key, new SoftReference<Bitmap>(bitmap));
		}
		return true;
	}

	/**
	 * 获得图片
	 * @param key
	 * @return
	 */
	public Bitmap getBitmap(String key) {
		Bitmap bitmap = null;
		if(mSoftBitmapCache.containsKey(key)) {
			synchronized (mSoftBitmapCache) {
				SoftReference<Bitmap> softReference = mSoftBitmapCache.get(key);
				if (softReference != null) {
					bitmap = softReference.get();
				}
			}
		}
		return bitmap;
	}
	
	/**
	 * 清理缓存
	 */
	public void clear() {
		try {
			synchronized (mSoftBitmapCache) {
				mSoftBitmapCache.clear();
			}
			System.gc();
			System.gc();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
