package com.shenma.yueba.application;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.shenma.yueba.R;

public class MyApplication extends Application {

	private static List<Activity> activityList = new LinkedList<Activity>();
	private static MyApplication instance;
	private BitmapUtils bitmapUtils;
	public BitmapDisplayConfig bigPicDisplayConfig;
	
	
	
	/**
	 * /** 初始化图片加载类MyApplication123
	 */
	private ImageLoader ivL;
	private DisplayImageOptions options;
	
	private String text;
	private String ddddddd;
	private String cccc;
	private DisplayImageOptions optionsForRound;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		initBitmapUtils();
		initImageLoader(getApplicationContext());
		initDisplayImageOptions();
		initRoundDisplayImageOptions();

	}

	public static MyApplication getInstance() {
		if (instance == null) {
			instance = new MyApplication();
		}
		return instance;
	}

	/**
	 * 将acitivity加入到堆栈
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		if (activityList != null && activity != null) {
			activityList.add(activity);
		}
	}

	/**
	 * 移除某个activity
	 * 
	 * @param activity
	 */
	public static void removeActivity(Activity activity) {
		if (activityList != null && activity != null
				&& activityList.contains(activity)) {
			activityList.remove(activity);
		}
	}

	/**
	 * 退出应用程序
	 */
	public static void exit() {
		if (activityList != null) {
			for (Activity activity : activityList) {
				if (activity != null) {
					activity.finish();
				}
			}
		}
		System.exit(0);
	}

	/**
	 * 将堆栈中的所有activity移除
	 */
	public static void removeAllActivity() {
		if (activityList != null) {
			for (Activity activity : activityList) {
				if (activity != null) {
					activity.finish();
				}
			}
		}
	}

	/**
	 * 查看该Activity是否存在在堆栈中
	 * 
	 * @param cls
	 * @return
	 */
	public boolean findActivity(Class<?> cls) {
		boolean hasGet = false;
		for (Activity activity : activityList) {
			if (activity.getClass().equals(cls)) {
				hasGet = true;
				break;
			}
		}
		return hasGet;
	}

	public ImageLoader getImageLoader() {
		return ivL;
	}

	public DisplayImageOptions getDisplayImageOptions() {
		return options;
	}

	public DisplayImageOptions getRoundDisplayImageOptions() {
		return optionsForRound;
	}

	private void initBitmapUtils() {
		bitmapUtils = new BitmapUtils(this);
		bigPicDisplayConfig = new BitmapDisplayConfig();
		// bigPicDisplayConfig.setShowOriginal(true); // 显示原始图片,不压缩, 尽量不要使用,
		// 图片太大时容易OOM。
		bigPicDisplayConfig.setBitmapConfig(Bitmap.Config.RGB_565);
		bigPicDisplayConfig.setBitmapMaxSize(BitmapCommonUtils
				.getScreenSize(this));
		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);// 默认背景图片
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.ic_launcher);// 加载失败图片
		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);// 设置图片压缩类型
	}

	public BitmapUtils getBitmapUtil() {
		if (bitmapUtils == null) {
			bitmapUtils = new BitmapUtils(this);
		}
		return bitmapUtils;
	}

	/**
	 * 加载图片配置
	 * 
	 * @param context
	 */
	public void initImageLoader(Context context) {
		// DON'T COPY THIS CODE TO YOUR PROJECT! This is just example of ALL
		// options using.
		File cacheDir = StorageUtils.getCacheDirectory(context);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.memoryCacheExtraOptions(200, 200)
				// default = device screen dimensions
				.discCacheExtraOptions(200, 200, CompressFormat.JPEG, 50, null)
				.threadPoolSize(5)
				// default
				.threadPriority(Thread.NORM_PRIORITY - 1)
				// default
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				// default
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13)
				// default
				.discCache(new UnlimitedDiscCache(cacheDir))
				// default
				.discCacheSize(50 * 1024 * 1024).discCacheFileCount(100)
				.discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
				.imageDownloader(new BaseImageDownloader(context)) // default
				.imageDecoder(new BaseImageDecoder(true)) // default
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
				.writeDebugLogs().build();
		ivL = ImageLoader.getInstance();
		ivL.init(config);
	}

	public void initDisplayImageOptions() {
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.defalt_pic)
				.showImageForEmptyUri(R.drawable.defalt_pic)
				.showImageOnFail(R.drawable.defalt_pic)
				.resetViewBeforeLoading(false).cacheOnDisc(true)
				.cacheInMemory(true).imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	public void initRoundDisplayImageOptions() {
		optionsForRound = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.defalt_pic)
				.showImageForEmptyUri(R.drawable.defalt_pic)
				.showImageOnFail(R.drawable.defalt_pic)
				.resetViewBeforeLoading(false).cacheOnDisc(true)
				.cacheInMemory(true).displayer(new RoundedBitmapDisplayer(55))
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}
	
	/******
	 * 显示提示信息
	 * @param Context context
	 * @param String msg 提示的消息
	 * ***/
	public void showMessage(Context context,String msg)
	{
		Toast.makeText(context, msg, 1000).show();
	}
}
