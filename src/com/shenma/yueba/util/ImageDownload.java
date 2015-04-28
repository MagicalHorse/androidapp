package com.shenma.yueba.util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * 图片下载类
 * 
 * @author zhou
 * 
 */
public class ImageDownload {

	private String mLocalPath;

	public ImageDownload(String localPath) {
		mLocalPath = localPath;
	}

	/**
	 * 下载图片
	 * 
	 * @param imageUrl
	 * @param imageName
	 * @return
	 */
	public boolean downloadImage(String imageUrl, String imageName) {
		if (imageUrl == null || "".equals(imageUrl)) {
			return false;
		}
		try {
			return writeTimes(3, imageUrl, imageName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * 写入本地 最多写入times次
	 * 
	 * @param times
	 * @param imageUrl
	 * @param imageName
	 * @return
	 * @throws Exception
	 */
	private boolean writeTimes(int times, String imageUrl, String imageName)
			throws Exception {
		boolean flag = false;
		for (int i = 0; i < times; i++) {
			if (writeImage(new URL((String) imageUrl).openStream(), imageName)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/**
	 * 保存图片
	 * 
	 * @param is
	 * @param imageName
	 * @return
	 */
	private boolean writeImage(InputStream is, String imageName) {
		FileOutputStream fos = null;
		File image = null;
		try {
			File dir = new File(mLocalPath);
			if (!dir.exists()) {
				boolean flag = dir.mkdirs();
				if (!flag) {
					return false;
				}
			}
			image = new File(dir, imageName);
			if (!image.exists()) {
				image.createNewFile();
			}
			fos = new FileOutputStream(image);
			byte[] buffer = new byte[1024];
			while (true) {
				int size = is.read(buffer);
				if (size == -1) {
					break;
				}
				fos.write(buffer, 0, size);
			}
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				if (image != null) {
					image.delete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		} finally {
			// if(image != null) {//为了保证服务器能随时更新，去掉本地sd存储
			// image.delete();
			// }
			try {
				if (fos != null) {
					fos.close();
					fos = null;
				}
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
