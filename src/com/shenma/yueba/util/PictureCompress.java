package com.shenma.yueba.util;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;

import com.shenma.yueba.constants.Constants;

public class PictureCompress {
	private static String TAG="PictureCompress";
	public static final String IMAGE_UNSPECIFIED = "image/*";

	public static Bitmap startPhotoZoomY(String picture) {
	    BitmapFactory.Options newOpts = new BitmapFactory.Options();
		//开始读入图片，此时把options.inJustDecodeBounds 设回true了
		newOpts.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(picture,newOpts);//此时返回bm为空
		newOpts.inJustDecodeBounds = false;
		int w = newOpts.outWidth;
		int h = newOpts.outHeight;
		//现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
		float hh = 720f;//这里设置高度为800f
		float ww = 640f;//这里设置宽度为480f
		//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;//be=1表示不缩放
		if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
			be = (int) (newOpts.outWidth / ww);
		} else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
			be = (int) (newOpts.outHeight / hh);
		}
		if (be <= 0)
			be = 1;
			newOpts.inSampleSize = be;//设置缩放比例
			//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
			bitmap = BitmapFactory.decodeFile(picture, newOpts);
			return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
	}
	public static Bitmap compressImage(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while ( baos.toByteArray().length / 1024>100) {	//循环判断如果压缩后图片是否大于100kb,大于继续压缩	 不生效 大于100kb	
			baos.reset();//重置baos即清空baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;//每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
		return bitmap;
	}
	
	public static Intent getZoomIntent(Uri uri){
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
			intent.putExtra("crop", "true");
			// aspectX aspectY 是宽高的比例
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			// outputX outputY 是裁剪图片宽高
			intent.putExtra("outputX", 100);
			intent.putExtra("outputY", 100);
			intent.putExtra("return-data", true);
			return intent;
	}
	
	public static void saveBitmapToSDCard(Bitmap bmp, String imgName) {
		OutputStream os = null;
		File file = new File(Constants.SD);
		file.mkdir();
		String filename = imgName;
		file = new File(Constants.SD, filename);
		try {
			os = new FileOutputStream(file);
			bmp.compress(CompressFormat.JPEG, 100, os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void deleteBitmapFromSDCard(String imgName) {
		String filename = imgName;
		File file = new File(Constants.SD, filename);
		try {
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/**  
	 *  处理图片   
	 * @param bm 所要转换的bitmap  
	 * @param newWidth新的宽  
	 * @param newHeight新的高    
	 * @return 指定宽高的bitmap  
	 */  
	 public static Bitmap zoomImg(Bitmap bm, int newWidth ,int newHeight){    
	    // 获得图片的宽高    
	    int width = bm.getWidth();    
	    int height = bm.getHeight();    
	    // 计算缩放比例    
	    float scaleWidth = ((float) newWidth) / width;    
	    float scaleHeight = ((float) newHeight) / height;    
	    // 取得想要缩放的matrix参数    
	    Matrix matrix = new Matrix();    
	    matrix.postScale(scaleWidth, scaleHeight);    
	    // 得到新的图片    
	    Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);    
	    return newbm;    
	}  
	public static  String[] getImgPathFromContent(String str){
		String[] array=new String[2];
		array[0]=str.substring(0, str.indexOf(","));
		array[1]=str.substring(str.indexOf(",")+1, str.length());
		return array;
	}
	/**
	 * 如果宽度超过640则按比例压缩
	 * @param oldBigImgPath
	 * @return
	 */
	public static String newBigImgPath(String oldBigImgPath){
		String newBigName=String.valueOf(System.currentTimeMillis())+".png";
		int[] wh=getBitMapWH(oldBigImgPath);
		int imgH=wh[1]*640/wh[0];
		Bitmap bigBitmap = zoomImg(getBitmap(oldBigImgPath), 640, imgH);
		PictureCompress.saveBitmapToSDCard(bigBitmap,newBigName);//按比例缩放的小图
		return Constants.SD +newBigName;
	}
	/**
	 * 得到图片的宽高
	 * @param path
	 * @return
	 */
	public static int[] getBitMapWH(String path){
		  Bitmap bm = BitmapFactory.decodeFile(path); 
		  int[] WandH=new int[2];
		  WandH[0]=bm.getWidth();
		  WandH[1]=bm.getHeight();
		  return WandH;
	}
	/**
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap getBitmap(String path){
		  Bitmap bm = BitmapFactory.decodeFile(path); 
		  return bm;
	}
	/**
	 * 获得文件大小
	 * @param path
	 * @return
	 */
	public static String getFileLenght(String path){
		File file=new File(path);
		return (file.length()/1024)>1024?(file.length()/1024/1024)+"MB":(file.length()/1024)+"KB";
	}
}
