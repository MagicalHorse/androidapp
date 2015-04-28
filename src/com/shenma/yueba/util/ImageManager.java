package com.shenma.yueba.util;



import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;

/**
 * 图片管理类
 * @author zhou
 *
 */
public class ImageManager {

	//下载地址
	public static final String mLocalImagePath = Environment.getExternalStorageDirectory() + "/maimaihui/";
	private static ImageManager mImageManager;
	
	private ImageDownload mImageDownload;
	private ImageMemoryCache mImageMemoryCache;
	private ImageLocalCache mImageLocalCache;
	
	/**
	 * 单例模式 获取ImageManager对象
	 * @return
	 */
	public static ImageManager instantiate() {
		if (mImageManager == null) {
			mImageManager = new ImageManager();
		}
		return mImageManager;
	}
	
	public void clearMemory() {
		mImageMemoryCache.clear();
	}
	
	public ImageManager() {
		mImageDownload = new ImageDownload(mLocalImagePath);
		mImageMemoryCache = new ImageMemoryCache();
		mImageLocalCache = new ImageLocalCache(mLocalImagePath);
	}
	
	/**
	 * 获得图片
	 * @param imageUrl
	 * @param key
	 * @param size
	 * @return
	 */
	public Bitmap getBitmap(String imageUrl, String key, float size) {
		Bitmap bitmap = null;
		try {
			bitmap = getCacheBitmap(key, size);
			if(bitmap == null) {
				bitmap = getNetBitmap(imageUrl, key, size);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return bitmap;
	}
	
	/**
	 * 获得图片
	 * @param imageUrl
	 * @param key
	 * @param size
	 * @return
	 */
	public Bitmap getBitmap2(String imageUrl, String key) {
		Bitmap bitmap = null;
		try {
			bitmap = getCacheBitmap2(key);
			if(bitmap == null) {
				bitmap = getNetBitmap2(imageUrl, key);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return bitmap;
	}
	
	/**
	 * 联网下载图片
	 * @param imageUrl
	 * @param key
	 * @param size
	 * @return
	 */
	public Bitmap getNetBitmap(String imageUrl, String key, float size) {
		Bitmap bitmap = null;
		try {
			boolean flag = mImageDownload.downloadImage(imageUrl, key);
			if(flag) {
				bitmap = getCacheBitmap(key, size);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return bitmap;
	}
	
	/**
	 * 联网下载图片
	 * @param imageUrl
	 * @param key
	 * @param size
	 * @return
	 */
	public Bitmap getNetBitmap2(String imageUrl, String key) {
		Bitmap bitmap = null;
		try {
			boolean flag = mImageDownload.downloadImage(imageUrl, key);
			if(flag) {
				bitmap = getCacheBitmap2(key);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return bitmap;
	}
	
	/**
	 * 从缓存中获得图片
	 * @param key
	 * @param size
	 * @return
	 */
	public Bitmap getCacheBitmap(String key, float size) {
		Bitmap bitmap = null;
		try {
			bitmap = mImageMemoryCache.getBitmap(key);
			if(bitmap != null) {
				return bitmap;
			}
			bitmap = mImageLocalCache.getBitmap(key, size);
			if(bitmap != null) {
				mImageMemoryCache.putBitmap(key, bitmap);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return bitmap;
	}
	
	/**
	 * 从缓存中获得图片
	 * @param key
	 * @param size
	 * @return
	 */
	public Bitmap getCacheBitmap2(String key) {
		Bitmap bitmap = null;
		try {
			bitmap = mImageMemoryCache.getBitmap(key);
			if(bitmap != null) {
				return bitmap;
			}
			bitmap = mImageLocalCache.getBitmap2(key);
			if(bitmap != null) {
				mImageMemoryCache.putBitmap(key, bitmap);
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return bitmap;
	}
	
	/**
	 * 保存图片
	 * @param bitmap
	 * @param key
	 * @return
	 */
	public boolean saveBitmap(Bitmap bitmap, String key) {
		BufferedOutputStream bos = null;
		try {
	        File picDir = new File(ImageManager.mLocalImagePath);
	        if (!picDir.exists()) {
	        	picDir.mkdir();
	        }
	        bos = new BufferedOutputStream(new FileOutputStream(new File(key)));
	        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
	        return true;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
		        bos.close();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 压缩图片 按照宽高进行压缩
	 * @param paramBitmap
	 * @param width
	 * @param height
	 * @return
	 */
	public Bitmap scale(Bitmap paramBitmap, int width, int height) {
		if(paramBitmap == null) {
			return paramBitmap;
		}
		if(width <=0 || height <= 0) {
			return paramBitmap;
		}
		
		int y = height;
		int x = y * paramBitmap.getWidth() / paramBitmap.getHeight();
		if (x < width) {
			x = width;
			y = x * paramBitmap.getHeight() / paramBitmap.getWidth();
		}
		Bitmap localBitmap1 = Bitmap.createScaledBitmap(paramBitmap, x, y, true);
		Bitmap localBitmap2;
		if (localBitmap1.getWidth() > width) {
			localBitmap2 = Bitmap.createBitmap(localBitmap1, 
					(localBitmap1.getWidth() - width) / 2, 0, width, height);
		} else {
			localBitmap2 = Bitmap.createBitmap(localBitmap1,
					0, (localBitmap1.getHeight() - height) / 2, width, height);
		}
		return localBitmap2;
	}
	
	/**
	 * 给图片做水印
	 * @param background
	 * @param mark
	 * @return
	 */
	public Bitmap markImage(Bitmap background, Bitmap mark) {
		if(background == null) {
			return null;
		}
		int bg_width = background.getWidth();
		int bg_height = background.getHeight();
		int mark_width = mark.getWidth();
		int mark_height = mark.getHeight();
		Bitmap newbmp = Bitmap.createBitmap(bg_width, bg_height, Config.ARGB_8888);
		Canvas canvas = new Canvas(newbmp);
		canvas.drawBitmap(background, 0, 0, null);
		canvas.drawBitmap(mark, bg_width - mark_width - 10, bg_height - mark_height - 10, null);
		canvas.save(Canvas.ALL_SAVE_FLAG);
		return newbmp;
	}
	
	/**
	 * 按正方形裁切图片
	 * @param bitmap 源图片
	 * @return
	 */
    public Bitmap imageCrop(Bitmap bitmap) {
        int w = bitmap.getWidth(); // 得到图片的宽, 高
        int h = bitmap.getHeight();

        int wh = w > h ? h : w;// 裁切后所取的正方形区域边长

        int retX = w > h ? (w - h) / 2 : 0;//基于原图, 取正方形左上角x坐标
        int retY = w > h ? 0 : (h - w) / 2;

        return Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null, false);
    }
	
    /**
     * 获取圆形图片
     * @param bitmap 源图片
     * @param crop 是否切成正方形
     * @param stroke 是否加白边
     * @return
     */
	public Bitmap imageRounded(Bitmap bitmap, boolean crop, boolean stroke,int edgeWith){
		if(crop && bitmap.getWidth() != bitmap.getHeight()) {
			bitmap = imageCrop(bitmap);
		}
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(outBitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        float roundPX = bitmap.getWidth() / 2;
        paint.setAntiAlias(true);//设置画笔为无锯齿  
        canvas.drawARGB(0, 0, 0, 0);//设置背景
        paint.setColor(0xff424242);//设置画笔颜色
        canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        if(stroke) {
        	if(edgeWith>=0){
        		paint.setStrokeWidth(edgeWith);
        	}else{
        		paint.setStrokeWidth(20);
        	}
    		paint.setStyle(Style.STROKE);
    		paint.setColor(Color.rgb(204, 204, 204));
            canvas.drawArc(rectF, 0, 360, false, paint);
        }
        return outBitmap;
    }
	
	/**
	 * 通过本地路径获取图片
	 * @param imagePath
	 * @param size
	 * @return
	 */
	public Bitmap getBitmapFromPath(String imagePath, float size) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imagePath, opts);
		if(size>0.0){
			int be = 0;
			be = (int) (opts.outWidth / (float) size);
			if (be <= 0) {
				be = 1;
			}
			opts.inSampleSize = be;
		}
		opts.inJustDecodeBounds = false;
		opts.inPurgeable = true;
		return BitmapFactory.decodeFile(imagePath, opts);
	}
	
	/**
	 * 通过本地路径获取图片
	 * @param imagePath
	 * @param size
	 * @return
	 */
	public Bitmap getBitmapFromPath2(String imagePath) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = false;
		opts.inPurgeable = true;
		return BitmapFactory.decodeFile(imagePath, opts);
	}
	
	/**
	 * 根据流读取图片
	 * @param data
	 * @param size
	 * @return
	 */
	public Bitmap getBitmapFromBytes(byte[] data, float size) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(data, 0, data.length, opts);
		int be = 0;
		be = (int) (opts.outHeight / (float) size);
		if (be <= 0) {
			be = 1;
		}
		opts.inSampleSize = be;
		opts.inJustDecodeBounds = false;
		opts.inPurgeable = true;
		return BitmapFactory.decodeByteArray(data, 0, data.length, opts);
	}
 
	/**
	 * 压缩图片到100k以下
	 * @param bitmap
	 * @return
	 */
	public byte[] compressBitmapToBelow100b(Bitmap bitmap) {
		if (bitmap == null) {
			return null;
		}
		long size = bitmap.getRowBytes() * bitmap.getHeight();
		int quality = 100;
		if (size > 3 * 1024 * 1024) {
			quality = 20;
		} else if (size > 2 * 1024 * 1024) {
			quality = 30;
		} else if (size > 1024 * 1024) {
			quality = 40;
		} else {
			quality = 50;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, quality, baos);

		return baos.toByteArray();
	}

	 /**
	 * 从Assets中读取图片
	 */
	public Bitmap getImageFromAssetsFile(Context context, String fileName) {
	    Bitmap image = null;
	    AssetManager am = context.getResources().getAssets();
	    try {
	        InputStream is = am.open(fileName);
	        image = BitmapFactory.decodeStream(is);
	        is.close();
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    }
	    return image;
	}
	
}
