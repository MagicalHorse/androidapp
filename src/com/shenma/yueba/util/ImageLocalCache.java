package com.shenma.yueba.util;


import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.support.v4.util.LruCache;

/**
 * 本地缓存类
 * @author zhou
 *
 */
public class ImageLocalCache {

	//缓存大小
	private static final int MAX_CACHE_SIZE = 30 * 1024 * 1024; // 30M
	//保存图片位置
	private String mLocalPath;
	//硬盘cache
	private LruCache<String, Long> mHardFileCache;
	
	/**
	 * 构造函数
	 * @param localPath
	 */
	public ImageLocalCache(String localPath) {
		mLocalPath = localPath;
		mHardFileCache = new LruCache<String, Long>(MAX_CACHE_SIZE) {
			@Override
			public int sizeOf(String key, Long value) {
				return value.intValue();
			}

			@Override
			protected void entryRemoved(boolean evicted, String key, Long oldValue,
					Long newValue) {
				if(evicted) {
					File file = getFile(key);
					if (file != null) {
						file.delete();
					}
				}
			}
		};
	}

	/**
	 * 读取图片
	 * @param key
	 * @param size
	 * @return
	 */
	public Bitmap getBitmap(String key, float size) {
		Bitmap bitmap = null;
		try {
			File bitmapFile = getFile(key);
			if (bitmapFile != null) {
				bitmap = ImageManager.instantiate().getBitmapFromPath(mLocalPath + key, size);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 读取图片
	 * @param key
	 * @param size
	 * @return
	 */
	public Bitmap getBitmap2(String key) {
		Bitmap bitmap = null;
		try {
			File bitmapFile = getFile(key);
			if (bitmapFile != null) {
				bitmap = ImageManager.instantiate().getBitmapFromPath2(mLocalPath + key);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return bitmap;
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
		File file = getFile(key);
		if (file != null) {
			return true;
		}
		boolean result = false;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(getFile(key));
			boolean saved = bitmap.compress(CompressFormat.JPEG, 100, fos);
			if (saved) {
				synchronized (mHardFileCache) {
					mHardFileCache.put(key, getFile(key).length());
				}
				result = true;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if(fos != null) {
					fos.flush();
					fos.close();
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 获得图片文件
	 * @param key
	 * @return
	 */
	private File getFile(String key) {
		File file = new File(mLocalPath + key);
		if(!file.exists() || !file.isFile())  {
			return null;
		}
		return file;
	}

}
