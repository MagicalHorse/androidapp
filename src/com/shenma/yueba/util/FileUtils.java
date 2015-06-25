package com.shenma.yueba.util;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;

import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.constants.Constants;

public class FileUtils {
	
	
	//获得应用的存储目录
	public static String getRootPath() {
		String rootPath;
		boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED); //判断sd卡是否存在
		if (sdCardExist) {
			rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();//获取跟目录
		} else {
			rootPath = MyApplication.getInstance().getApplicationContext().getFilesDir().getAbsolutePath();
		}
		return rootPath;
	}

	public static boolean hasSDCard() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	// ------------------------------------文件的相关方法--------------------------------------------

	/**
	 * 将数据写入一个文件
	 *
	 * @param destFilePath 要创建的文件的路径
	 * @param data         待写入的文件数据
	 * @param startPos     起始偏移量
	 * @param length       要写入的数据长度
	 * @return 成功写入文件返回true, 失败返回false
	 */
	public static boolean writeFile(String destFilePath, byte[] data, int startPos, int length) {
		try {
			if (!createFile(destFilePath)) {
				return false;
			}
			FileOutputStream fos = new FileOutputStream(destFilePath);
			fos.write(data, startPos, length);
			fos.flush();
			if (null != fos) {
				fos.close();
				fos = null;
			}
			return true;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 从一个输入流里写文件
	 *
	 * @param destFilePath 要创建的文件的路径
	 * @param in           要读取的输入流
	 * @return 写入成功返回true, 写入失败返回false
	 */
	public static boolean writeFile(String destFilePath, InputStream in) {
		try {
			if (!createFile(destFilePath)) {
				return false;
			}
			FileOutputStream fos = new FileOutputStream(destFilePath);
			int readCount = 0;
			int len = 1024;
			byte[] buffer = new byte[len];
			while ((readCount = in.read(buffer)) != -1) {
				fos.write(buffer, 0, readCount);
			}
			fos.flush();
			if (null != fos) {
				fos.close();
				fos = null;
			}
			if (null != in) {
				in.close();
				in = null;
			}
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean appendFile(String filename, byte[] data, int datapos, int datalength) {
		try {
			createFile(filename);
			RandomAccessFile rf = new RandomAccessFile(filename, "rw");
			rf.seek(rf.length());
			rf.write(data, datapos, datalength);
			if (rf != null) {
				rf.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * 读取文件，返回以byte数组形式的数据
	 *
	 * @param filePath 要读取的文件路径名
	 * @return
	 */
	public static byte[] readFile(String filePath) {
		try {
			if (isFileExist(filePath)) {
				FileInputStream fi = new FileInputStream(filePath);
				return readInputStream(fi);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从一个数量流里读取数据,返回以byte数组形式的数据。
	 * </br></br>
	 * 需要注意的是，如果这个方法用在从本地文件读取数据时，一般不会遇到问题，但如果是用于网络操作，就经常会遇到一些麻烦(available()方法的问题)。所以如果是网络流不应该使用这个方法。
	 *
	 * @param in 要读取的输入流
	 * @return
	 * @throws java.io.IOException
	 */
	public static byte[] readInputStream(InputStream in) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			byte[] b = new byte[in.available()];
			int length = 0;
			while ((length = in.read(b)) != -1) {
				os.write(b, 0, length);
			}

			b = os.toByteArray();

			in.close();
			in = null;

			os.close();
			os = null;

			return b;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 读取网络流
	 *
	 * @param in
	 * @return
	 */
	public static byte[] readNetWorkInputStream(InputStream in) {
		ByteArrayOutputStream os = null;
		try {
			os = new ByteArrayOutputStream();

			int readCount = 0;
			int len = 1024;
			byte[] buffer = new byte[len];
			while ((readCount = in.read(buffer)) != -1) {
				os.write(buffer, 0, readCount);
			}

			in.close();
			in = null;

			return os.toByteArray();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != os) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				os = null;
			}
		}
		return null;
	}

	/**
	 * 将一个文件拷贝到另外一个地方
	 *
	 * @param sourceFile    源文件地址
	 * @param destFile      目的地址
	 * @param shouldOverlay 是否覆盖
	 * @return
	 */
	public static boolean copyFiles(String sourceFile, String destFile, boolean shouldOverlay) {
		try {
			if (shouldOverlay) {
				deleteFile(destFile);
			}
			FileInputStream fi = new FileInputStream(sourceFile);
			writeFile(destFile, fi);
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 判断文件是否存在
	 *
	 * @param filePath 路径名
	 * @return
	 */
	public static boolean isFileExist(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}

	/**
	 * 创建一个文件，创建成功返回true
	 *
	 * @param filePath
	 * @return
	 */
	public static boolean createFile(String filePath) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}

				return file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 删除一个文件
	 *
	 * @param filePath 要删除的文件路径名
	 * @return true if this file was deleted, false otherwise
	 */
	public static boolean deleteFile(String filePath) {
		try {
			File file = new File(filePath);
			if (file.exists()) {
				return file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 删除 directoryPath目录下的所有文件，包括删除删除文件夹
	 *
	 * @param
	 */
	public static void deleteDirectory(File dir) {
		if (dir.isDirectory()) {
			File[] listFiles = dir.listFiles();
			for (int i = 0; i < listFiles.length; i++) {
				deleteDirectory(listFiles[i]);
			}
		}
		dir.delete();
	}

	/**
	 * 获取文件夹大小
	 *
	 * @param file File实例
	 * @return long 单位为M
	 * @throws Exception
	 */
	public static long getFolderSize(File file) throws Exception {
		long size = 0;
		File[] fileList = file.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isDirectory()) {
				size = size + getFolderSize(fileList[i]);
			} else {
				size = size + fileList[i].length();
			}
		}
		return size / 1048576;
	}

	/**
	 * 字符串转流
	 *
	 * @param str
	 * @return
	 */
	public static InputStream String2InputStream(String str) {
		ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
		return stream;
	}

	/**
	 * 流转字符串
	 *
	 * @param is
	 * @return
	 */
	public static String inputStream2String(InputStream is) {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";

		try {
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	//批量更改文件后缀
	public static void reNameSuffix(File dir, String oldSuffix, String newSuffix) {
		if (dir.isDirectory()) {
			File[] listFiles = dir.listFiles();
			for (int i = 0; i < listFiles.length; i++) {
				reNameSuffix(listFiles[i], oldSuffix, newSuffix);
			}
		} else {
			dir.renameTo(new File(dir.getPath().replace(oldSuffix, newSuffix)));
		}
	}

	public static void writeImage(Bitmap bitmap, String destPath, int quality) {
		try {
			FileUtils.deleteFile(destPath);
			if (FileUtils.createFile(destPath)) {
				FileOutputStream out = new FileOutputStream(destPath);
				if (bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)) {
					out.flush();
					out.close();
					out = null;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 清理sdcard上面时间早于time的缓存图片
	 *
	 * @param time
	 */
	public static void clear(long time, String path) {
		if (hasSDCard()) {
			return;
		}
		clearFile(time, System.currentTimeMillis(), path);
	}

	/**
	 * @param time
	 * @param currentTime
	 * @param path
	 */
	private static void clearFile(long time, long currentTime, String path) {
		if (TextUtils.isEmpty(path)) {
			return;
		}
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (file.isFile()) {
			// 删除早于time的文件
			if (file.lastModified() < time) {
				file.delete();
			}
			// 清理由于系统时间不准确可能引入的脏文件
			else if (file.lastModified() > currentTime + 1000 * 60 * 60 * 24) {
				file.delete();
			} else {
				//todo
			}
		} else {
			File[] files = file.listFiles();
			if (files != null) {
				for (File file2 : files) {
					clearFile(time, currentTime, file2.getAbsolutePath());
				}
			}
		}
	}
	
	/**
	 * 存放json缓存的路径
	 */
	// 图片在SD卡中的缓存路径
		private static final String JSON_PATH = Environment
				.getExternalStorageDirectory().toString()
				+ File.separator
				+ Constants.PROJECT_NAME + File.separator + "json" + File.separator;
	
	/**
	 * 判断SD是否可以
	 * 
	 * @return
	 */
	public static boolean isSdcardExist() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	/**
	 * 创建根目录
	 * 
	 * @param path
	 *            目录路径
	 */
	public static void createDirFile(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}
	
	/**
	 * 创建文件
	 * 
	 * @param path
	 *            文件路径
	 * @return 创建的文件
	 */
	public static File createNewFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				return null;
			}
		}
		return file;
	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath
	 *            文件夹的路径
	 */
	public static void delFolder(String folderPath) {
		delAllFile(folderPath);
		String filePath = folderPath;
		filePath = filePath.toString();
		java.io.File myFilePath = new java.io.File(filePath);
		myFilePath.delete();
	}

	/**
	 * 删除文件
	 * 
	 * @param path
	 *            文件的路径
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);
				delFolder(path + "/" + tempList[i]);
			}
		}
	}

	/**
	 * 获取文件的Uri
	 * 
	 * @param path
	 *            文件的路径
	 * @return
	 */
	public static Uri getUriFromFile(String path) {
		File file = new File(path);
		return Uri.fromFile(file);
	}
	public static boolean exists(String file) {
		return new File(file).exists();
	}
	/**
	 * 换算文件大小
	 * 
	 * @param size
	 * @return
	 */
	public static String formatFileSize(long size) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "未知大小";
		if (size < 1024) {
			fileSizeString = df.format((double) size) + "B";
		} else if (size < 1048576) {
			fileSizeString = df.format((double) size / 1024) + "K";
		} else if (size < 1073741824) {
			fileSizeString = df.format((double) size / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) size / 1073741824) + "G";
		}
		return fileSizeString;
	}
	
	
	
	/**
	 * 保存json到Beautician文件夹中
	 * 
	 * @param str
	 *            json字符串
	 * @param filename
	 *            文件名字
	 */
	public static void saveJson(String str, String filename) {
		if (str != null) {
			FileOutputStream out;
			try {
				File dir = new File(JSON_PATH);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				File file = new File(JSON_PATH + filename);
				out = new FileOutputStream(file);
				out.write(str.replace("\n", "\r\n").getBytes("utf-8"));
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 读取json文件
	 * 
	 * @param filename
	 * @return
	 */
	public static String getLastJson(String filename) {
		FileInputStream in;
		try {
			File file = new File(JSON_PATH + filename);
			in = new FileInputStream(file);
			return getStreamString(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	/**
	 * 将一个输入流转化为字符串
	 */
	public static String getStreamString(FileInputStream tInputStream) {
		if (tInputStream != null) {
			try {
				BufferedReader tBufferedReader = new BufferedReader(
						new InputStreamReader(tInputStream));
				StringBuffer tStringBuffer = new StringBuffer();
				String sTempOneLine = new String("");
				while ((sTempOneLine = tBufferedReader.readLine()) != null) {
					tStringBuffer.append(sTempOneLine + "\r\n");
				}
				return tStringBuffer.toString();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	
	private static int FILE_SIZE = 4 * 1024;

	public static boolean hasSdcard() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	public static boolean createPath(String path) {
		File f = new File(path);
		if (!f.exists()) {
			Boolean o = f.mkdirs();
			return o;
		}
		return true;
	}


	public static File saveFile(String file, InputStream inputStream) {
		File f = null;
		OutputStream outSm = null;

		try {
			f = new File(file);
			String path = f.getParent();
			if (!createPath(path)) {
				return null;
			}

			if (!f.exists()) {
				f.createNewFile();
			}

			outSm = new FileOutputStream(f);
			byte[] buffer = new byte[FILE_SIZE];
			while ((inputStream.read(buffer)) != -1) {
				outSm.write(buffer);
			}
			outSm.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;

		} finally {
			try {
				if (outSm != null)
					outSm.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return f;
	}

	public static Drawable getImageDrawable(String file) {
		if (!exists(file))
			return null;
		try {
			InputStream inp = new FileInputStream(new File(file));
			return BitmapDrawable.createFromStream(inp, "img");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
