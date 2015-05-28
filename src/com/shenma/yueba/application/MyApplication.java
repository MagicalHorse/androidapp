package com.shenma.yueba.application;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import roboguice.RoboGuice;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Typeface;
import android.os.StrictMode;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.OSSService;
import com.alibaba.sdk.android.oss.OSSServiceProvider;
import com.alibaba.sdk.android.oss.model.AccessControlList;
import com.alibaba.sdk.android.oss.model.ClientConfiguration;
import com.alibaba.sdk.android.oss.model.TokenGenerator;
import com.alibaba.sdk.android.oss.util.OSSToolKit;
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
import com.shenma.yueba.baijia.activity.LoginAndRegisterActivity;
import com.shenma.yueba.baijia.modle.UserRequestBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.db.DBHelper;
import com.shenma.yueba.util.SharedUtil;
import com.shenma.yueba.util.ToolsUtil;

public class MyApplication extends Application {

	private static List<Activity> activityList = new LinkedList<Activity>();
	private static MyApplication instance;
	private BitmapUtils bitmapUtils;
	public BitmapDisplayConfig bigPicDisplayConfig;

	public float kuanggaobi = (float) 1.0;// 相册截图的宽高比

	private Map<String, Integer> mFaceMap = new LinkedHashMap<String, Integer>();
	private static DBHelper dbHelper;// 数据库帮助类
	/**
	 * /** 初始化图片加载类MyApplication123
	 */
	private ImageLoader ivL;
	private DisplayImageOptions options;

	private String text;
	private String ddddddd;
	private String cccc;
	private DisplayImageOptions optionsForRound;
	private UserRequestBean userRequestBean;
	private Typeface tf;

	static final String accessKey = "***********"; // 测试代码没有考虑AK/SK的安全性
	static final String screctKey = "***********";

	public static OSSService ossService = OSSServiceProvider.getService();

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		initBitmapUtils();
		initImageLoader(getApplicationContext());
		initDisplayImageOptions();
		initRoundDisplayImageOptions();
		initFaceMap();
		dbHelper = RoboGuice.getBaseApplicationInjector(this).getInstance(
				DBHelper.class);
		initAliOSS();
	}

	@SuppressLint("NewApi")
	private void initAliOSS() {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		// 初始化设置
		ossService.setApplicationContext(this.getApplicationContext());
		ossService.setGlobalDefaultTokenGenerator(new TokenGenerator() { // 设置全局默认加签器
					@Override
					public String generateToken(String httpMethod, String md5,
							String type, String date, String ossHeaders,
							String resource) {

						String content = httpMethod + "\n" + md5 + "\n" + type
								+ "\n" + date + "\n" + ossHeaders + resource;

						return OSSToolKit.generateToken(accessKey, screctKey,
								content);
					}
				});
		ossService.setGlobalDefaultHostId("oss-cn-hangzhou.aliyuncs.com");
		ossService
				.setCustomStandardTimeWithEpochSec(System.currentTimeMillis() / 1000);
		ossService.setGlobalDefaultACL(AccessControlList.PUBLIC_READ); // 默认为private

		ClientConfiguration conf = new ClientConfiguration();
		conf.setConnectTimeout(15 * 1000); // 设置全局网络连接超时时间，默认30s
		conf.setSocketTimeout(15 * 1000); // 设置全局socket超时时间，默认30s
		conf.setMaxConnections(50); // 设置全局最大并发网络链接数, 默认50
		ossService.setClientConfiguration(conf);

	}

	public static MyApplication getInstance() {
		if (instance == null) {
			instance = new MyApplication();
		}
		return instance;
	}

	public Typeface getTypeface() {
		if (tf == null) {
			// tf = Typeface.createFromAsset(this.getAssets(),
			// "fonts/youyuan.ttf");
			// tf = Typeface.createFromAsset(this.getAssets(),
			// "fonts/dongqing.otf");
			tf = Typeface.createFromAsset(this.getAssets(), "fonts/hanyi.ttf");
			return tf;
		} else {
			return tf;
		}
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
				.showImageOnLoading(R.drawable.default_pic)
				.showImageForEmptyUri(R.drawable.default_pic)
				.showImageOnFail(R.drawable.default_pic)
				.resetViewBeforeLoading(false).cacheOnDisc(true)
				.cacheInMemory(true).imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	public void initRoundDisplayImageOptions() {
		optionsForRound = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.default_pic)
				.showImageForEmptyUri(R.drawable.default_pic)
				.showImageOnFail(R.drawable.default_pic)
				.resetViewBeforeLoading(false).cacheOnDisc(true)
				.cacheInMemory(true).displayer(new RoundedBitmapDisplayer(15))
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	/******
	 * 显示提示信息
	 * 
	 * @param Context
	 *            context
	 * @param String
	 *            msg 提示的消息
	 * ***/
	public void showMessage(Context context, String msg) {
		Toast.makeText(context, msg, 1000).show();
	}

	/*****
	 * 判断用户是否已经登录
	 * ***/
	public boolean isUserLogin(Activity activity) {
		boolean status = SharedUtil.getBooleanPerfernece(
				this.getApplicationContext(), SharedUtil.user_loginstatus);
		if (status) {
			return true;
		} else {
			showMessage(activity, "请先登录");
			Intent intent = new Intent(activity, LoginAndRegisterActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return false;
		}
	}

	private void initScreenWithAndHeight() {
		int width = ToolsUtil.getDisplayHeight(getApplicationContext());
		Constants.SCREENHEITH = width;
	}

	public Map<String, Integer> getFaceMap() {
		if (!mFaceMap.isEmpty())
			return mFaceMap;
		return null;
	}

	/**
	 * 初始化表情
	 */
	private void initFaceMap() {
		// TODO Auto-generated method stub
		mFaceMap.put("[微笑]", R.drawable.biaoqing001);
		mFaceMap.put("[色色]", R.drawable.biaoqing002);
		mFaceMap.put("[嘻嘻]", R.drawable.biaoqing003);
		mFaceMap.put("[偷笑]", R.drawable.biaoqing004);
		mFaceMap.put("[害羞]", R.drawable.biaoqing005);
		mFaceMap.put("[大哭]", R.drawable.biaoqing006);
		mFaceMap.put("[流泪]", R.drawable.biaoqing007);
		mFaceMap.put("[耍酷]", R.drawable.biaoqing008);
		mFaceMap.put("[发怒]", R.drawable.biaoqing009);
		mFaceMap.put("[吃惊]", R.drawable.biaoqing010);
		mFaceMap.put("[疑问]", R.drawable.biaoqing011);
		mFaceMap.put("[好衰]", R.drawable.biaoqing012);
		mFaceMap.put("[吐舌]", R.drawable.biaoqing013);
		mFaceMap.put("[调皮]", R.drawable.biaoqing014);
		mFaceMap.put("[惊恐]", R.drawable.biaoqing015);
		mFaceMap.put("[睡觉]", R.drawable.biaoqing016);
		mFaceMap.put("[困乏]", R.drawable.biaoqing017);
		mFaceMap.put("[不屑]", R.drawable.biaoqing018);
		mFaceMap.put("[晕晕]", R.drawable.biaoqing019);
		mFaceMap.put("[悠闲]", R.drawable.biaoqing020);
		mFaceMap.put("[尴尬]", R.drawable.biaoqing021);
		mFaceMap.put("[脸红]", R.drawable.biaoqing022);
		mFaceMap.put("[安慰]", R.drawable.biaoqing023);
		mFaceMap.put("[闭嘴]", R.drawable.biaoqing024);
		mFaceMap.put("[狂吐]", R.drawable.biaoqing025);
		mFaceMap.put("[饥饿]", R.drawable.biaoqing026);
		mFaceMap.put("[鄙视]", R.drawable.biaoqing027);
		mFaceMap.put("[不爽]", R.drawable.biaoqing028);
		mFaceMap.put("[强大]", R.drawable.biaoqing029);
		mFaceMap.put("[胜利]", R.drawable.biaoqing030);
		mFaceMap.put("[弱爆]", R.drawable.biaoqing031);
		mFaceMap.put("[握手]", R.drawable.biaoqing032);
		mFaceMap.put("[勾引]", R.drawable.biaoqing033);
		mFaceMap.put("[爱你]", R.drawable.biaoqing034);
		mFaceMap.put("[抱拳]", R.drawable.biaoqing035);
		mFaceMap.put("[OK]", R.drawable.biaoqing036);
		mFaceMap.put("[便便]", R.drawable.biaoqing037);
		mFaceMap.put("[吃饭]", R.drawable.biaoqing038);
		mFaceMap.put("[爱心]", R.drawable.biaoqing039);
		mFaceMap.put("[心碎]", R.drawable.biaoqing040);
		mFaceMap.put("[咖啡]", R.drawable.biaoqing041);
		mFaceMap.put("[钱币]", R.drawable.biaoqing042);
		mFaceMap.put("[西瓜]", R.drawable.biaoqing043);
		mFaceMap.put("[吃药]", R.drawable.biaoqing044);
		mFaceMap.put("[内裤]", R.drawable.biaoqing045);
		mFaceMap.put("[内衣]", R.drawable.biaoqing046);
		mFaceMap.put("[强壮]", R.drawable.biaoqing047);
		mFaceMap.put("[猪头]", R.drawable.biaoqing048);
		mFaceMap.put("[玫瑰]", R.drawable.biaoqing049);
		mFaceMap.put("[凋谢]", R.drawable.biaoqing050);
		mFaceMap.put("[蛋糕]", R.drawable.biaoqing051);
		mFaceMap.put("[流汗]", R.drawable.biaoqing052);
		mFaceMap.put("[围观]", R.drawable.biaoqing053);
		mFaceMap.put("[夜晚]", R.drawable.biaoqing054);
		mFaceMap.put("[亲亲]", R.drawable.biaoqing055);
		mFaceMap.put("[礼物]", R.drawable.biaoqing056);
		mFaceMap.put("[憨笑]", R.drawable.biaoqing057);
		mFaceMap.put("[咧嘴]", R.drawable.biaoqing058);
		mFaceMap.put("[可爱]", R.drawable.biaoqing059);
		mFaceMap.put("[阴笑]", R.drawable.biaoqing060);
		mFaceMap.put("[捂嘴]", R.drawable.biaoqing061);
		mFaceMap.put("[气炸]", R.drawable.biaoqing062);
		mFaceMap.put("[呜呜]", R.drawable.biaoqing063);
		mFaceMap.put("[狂暴]", R.drawable.biaoqing064);
		mFaceMap.put("[好囧]", R.drawable.biaoqing065);
		mFaceMap.put("[惊吓]", R.drawable.biaoqing066);
		mFaceMap.put("[好色]", R.drawable.biaoqing067);
		mFaceMap.put("[飞吻]", R.drawable.biaoqing068);
		mFaceMap.put("[坏坏]", R.drawable.biaoqing069);
		mFaceMap.put("[捂眼]", R.drawable.biaoqing070);
		mFaceMap.put("[可怜]", R.drawable.biaoqing071);
		mFaceMap.put("[发呆]", R.drawable.biaoqing072);
		mFaceMap.put("[封嘴]", R.drawable.biaoqing073);
		mFaceMap.put("[叹气]", R.drawable.biaoqing074);
		mFaceMap.put("[鬼脸]", R.drawable.biaoqing075);
		mFaceMap.put("[委屈]", R.drawable.biaoqing076);
		mFaceMap.put("[抠鼻]", R.drawable.biaoqing077);
		mFaceMap.put("[吓尿]", R.drawable.biaoqing078);
		mFaceMap.put("[斜视]", R.drawable.biaoqing079);
		mFaceMap.put("[鄙视]", R.drawable.biaoqing080);
		mFaceMap.put("[敲打]", R.drawable.biaoqing081);
		mFaceMap.put("[晕乎]", R.drawable.biaoqing082);
		mFaceMap.put("[恶心]", R.drawable.biaoqing083);
		mFaceMap.put("[鼓掌]", R.drawable.biaoqing084);
	}

}
