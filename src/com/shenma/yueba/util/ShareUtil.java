package com.shenma.yueba.util;



import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.shenma.yueba.R;
import com.shenma.yueba.view.MyGridView;
import com.shenma.yueba.yangjia.adapter.ShareAdapter;
import com.umeng.socialize.bean.RequestType;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

public class ShareUtil {
	private static BottomPopupWindow pop;
	static String appId = "wx0bd15e11e7c3090f";
	static String appSecret = "e3ff58518855345970755d08a3540c26";
	public static void shareAll(final Activity activity, final String title,final String content,final String url, final String imgurl,ShareListener shareListener) {
		List<String> list = new ArrayList<String>();
//		if (SharedUtil.getWeiboQQ(activity)) {
//			list.add("腾讯微博");
//		}
//		if (SharedUtil.getWeiboSina(activity)) {
//			list.add("新浪微博");
//		}
		if (SharedUtil.getWeixinFriends(activity)) {
			list.add("微信好友");
		}
		if (SharedUtil.getFriendCircle(activity)) {
			list.add("微信朋友圈");
		}
		final String[] strArray = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			strArray[i] = list.get(i);
		}
		getShareDialog(activity,title,content,url,imgurl,shareListener);
	}

	public static void shareAll2(final Activity activity, final String title,final String content,final String url, final Bitmap bitmap,ShareListener shareListener) {
		List<String> list = new ArrayList<String>();
//		if (SharedUtil.getWeiboQQ(activity)) {
//			list.add("腾讯微博");
//		}
//		if (SharedUtil.getWeiboSina(activity)) {
//			list.add("新浪微博");
//		}
		if (SharedUtil.getWeixinFriends(activity)) {
			list.add("微信好友");
		}
		if (SharedUtil.getFriendCircle(activity)) {
			list.add("微信朋友圈");
		}
		final String[] strArray = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			strArray[i] = list.get(i);
		}
		getShareDialog(activity,title,content,url,bitmap,shareListener);
	}
	
	// 微信好友
	public static void shareWeixinFriend(final Activity activity,String title,String content, String url, String imgurl,final ShareListener shareListener) {
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(activity, appId, appSecret);
		wxHandler.addToSocialSDK();
		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setTitle(title);
		weixinContent.setShareContent(content);
		weixinContent.setTargetUrl(url);
		if(TextUtils.isEmpty(imgurl)){
			weixinContent.setShareImage(new UMImage(activity, BitmapFactory.decodeResource(activity.getResources(), R.drawable.icon)));
		}else{
			weixinContent.setShareImage(new UMImage(activity, imgurl));
		}
		UMSocialService controller = UMServiceFactory.getUMSocialService(
				"com.umeng.share", RequestType.SOCIAL);
		controller.setShareMedia(weixinContent);
		controller.postShare(activity, SHARE_MEDIA.WEIXIN,
				new SnsPostListener() {

					@Override
					public void onStart() {
					}

					@Override
					public void onComplete(SHARE_MEDIA arg0, int arg1,
							SocializeEntity arg2) {
						if (200 == arg1) {
							if(shareListener!=null)
							{
								shareListener.sharedListener_sucess();
							}
							//Toast.makeText(activity, "分享成功", Toast.LENGTH_SHORT).show();
						} else {
							if(shareListener!=null)
							{
								shareListener.sharedListener_Fails("分享失败");
							}
							//Toast.makeText(activity, "分享失败", Toast.LENGTH_SHORT).show();
						}
					}
				});
	}

	// 微信朋友圈
	public static void shareWeixinFriends(final Activity activity,String title,
			String content, String url, String imgurl,final ShareListener shareListener) {
		UMWXHandler wxCircleHandler = new UMWXHandler(activity, appId, appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
		CircleShareContent circleMedia = new CircleShareContent();
	
		if(!TextUtils.isEmpty(content)){
			circleMedia.setTitle(content);
		}
		
		if(!TextUtils.isEmpty(content)){
			circleMedia.setShareContent(content);
		}
		if(TextUtils.isEmpty(imgurl)){
			circleMedia.setShareImage(new UMImage(activity, BitmapFactory.decodeResource(activity.getResources(), R.drawable.icon)));
		}else{
		circleMedia.setShareImage(new UMImage(activity, imgurl));
		}
		circleMedia.setTargetUrl(url);
		com.umeng.socialize.utils.Log.LOG = true;
		UMSocialService controller = UMServiceFactory.getUMSocialService("com.umeng.share", RequestType.SOCIAL);
		controller.setShareMedia(circleMedia);
		controller.getConfig().closeToast();
		
		controller.postShare(activity, SHARE_MEDIA.WEIXIN_CIRCLE,
				new SnsPostListener() {

					@Override
					public void onComplete(SHARE_MEDIA arg0, int arg1,
							SocializeEntity arg2) {
						if (200 == arg1) {
							if(shareListener!=null)
							{
								shareListener.sharedListener_sucess();
							}
							//Toast.makeText(activity, "分享成功", Toast.LENGTH_SHORT).show();
						} else {
							if(shareListener!=null)
							{
								shareListener.sharedListener_Fails("分享失败");
							}
							//Toast.makeText(activity, "分享失败", Toast.LENGTH_SHORT).show();
						}
					}

					
					@Override
					public void onStart() {
						
					}

				});
	}

	
	
	// 微信好友
		public static void shareWeixinFriend(final Activity activity,String title,String content, String url, Bitmap bitmap,final ShareListener shareListener) {
			// 添加微信平台
			UMWXHandler wxHandler = new UMWXHandler(activity, appId, appSecret);
			wxHandler.addToSocialSDK();
			WeiXinShareContent weixinContent = new WeiXinShareContent();
			weixinContent.setTitle(title);
			weixinContent.setShareContent(content);
			weixinContent.setTargetUrl(url);
				weixinContent.setShareImage(new UMImage(activity, bitmap));
			UMSocialService controller = UMServiceFactory.getUMSocialService(
					"com.umeng.share", RequestType.SOCIAL);
			controller.setShareMedia(weixinContent);
			controller.postShare(activity, SHARE_MEDIA.WEIXIN,
					new SnsPostListener() {

						@Override
						public void onStart() {
						}

						@Override
						public void onComplete(SHARE_MEDIA arg0, int arg1,
								SocializeEntity arg2) {
							if (200 == arg1) {
								if(shareListener!=null)
								{
									shareListener.sharedListener_sucess();
								}
								//Toast.makeText(activity, "分享成功", Toast.LENGTH_SHORT).show();
							} else {
								if(shareListener!=null)
								{
									shareListener.sharedListener_Fails("分享失败");
								}
								//Toast.makeText(activity, "分享失败", Toast.LENGTH_SHORT).show();
							}
						}
					});
		}

		// 微信朋友圈
		public static void shareWeixinFriends(final Activity activity,String title,
				String content, String url, Bitmap bitmap,final ShareListener shareListener) {
			UMWXHandler wxCircleHandler = new UMWXHandler(activity, appId, appSecret);
			wxCircleHandler.setToCircle(true);
			wxCircleHandler.addToSocialSDK();
			CircleShareContent circleMedia = new CircleShareContent();
		
			if(!TextUtils.isEmpty(content)){
				circleMedia.setTitle(content);
			}
			
			if(!TextUtils.isEmpty(content)){
				circleMedia.setShareContent(content);
			}
				circleMedia.setShareImage(new UMImage(activity, bitmap));
			circleMedia.setTargetUrl(url);
			com.umeng.socialize.utils.Log.LOG = true;
			UMSocialService controller = UMServiceFactory.getUMSocialService("com.umeng.share", RequestType.SOCIAL);
			controller.setShareMedia(circleMedia);
			controller.getConfig().closeToast();
			
			controller.postShare(activity, SHARE_MEDIA.WEIXIN_CIRCLE,
					new SnsPostListener() {

						@Override
						public void onComplete(SHARE_MEDIA arg0, int arg1,
								SocializeEntity arg2) {
							if (200 == arg1) {
								if(shareListener!=null)
								{
									shareListener.sharedListener_sucess();
								}
								//Toast.makeText(activity, "分享成功", Toast.LENGTH_SHORT).show();
							} else {
								if(shareListener!=null)
								{
									shareListener.sharedListener_Fails("分享失败");
								}
								//Toast.makeText(activity, "分享失败", Toast.LENGTH_SHORT).show();
							}
						}

						
						@Override
						public void onStart() {
							
						}

					});
		}

		
	
	
	
	
	private static void share(final Activity activity, final SHARE_MEDIA type,
			final String content, final String url) {
		   final UMSocialService controller = UMServiceFactory
		            .getUMSocialService("com.umeng.share");
		    SHARE_MEDIA mPlatform = SHARE_MEDIA.SINA;
		    SHARE_MEDIA mPlatform2 = SHARE_MEDIA.TENCENT;
		    controller.getConfig().setPlatformOrder(mPlatform,mPlatform2);
//        // 添加新浪SSO授权
//		controller.getConfig().setSsoHandler(new SinaSsoHandler());
//        // 添加腾讯微博SSO授权
//		controller.getConfig().setSsoHandler(new TencentWBSsoHandler());
//		boolean is = OauthHelper.isAuthenticated(activity, type);
//		if (!is) {
//			controller.doOauthVerify(activity, type, new UMAuthListener() {
//				@Override
//				public void onStart(SHARE_MEDIA arg0) {
//					Toast.makeText(activity, "授权开始", Toast.LENGTH_SHORT).show();
//				}
//
//				@Override
//				public void onError(SocializeException arg0, SHARE_MEDIA arg1) {
//					Toast.makeText(activity, "授权错误", Toast.LENGTH_SHORT).show();
//				}
//
//				@Override
//				public void onComplete(Bundle arg0, SHARE_MEDIA arg1) {
//					Toast.makeText(activity, "授权完成", Toast.LENGTH_SHORT).show();
//					shareImageAndText(activity, controller, type, content, url);
//				}
//
//				@Override
//				public void onCancel(SHARE_MEDIA arg0) {
//					Toast.makeText(activity, "授权取消", Toast.LENGTH_SHORT).show();
//				}
//			});
//		} else {
//			shareImageAndText(activity, controller, type, content, url);
//		}

		shareImageAndText(activity, controller, type, content, url);
	}

	private static void shareImageAndText(Activity activity,
			UMSocialService controller, SHARE_MEDIA type, String content,
			String url) {
		controller.setShareContent(content);
		controller.setShareMedia(new UMImage(activity, url));
		controller.postShare(activity, type, new SnsPostListener() {

			@Override
			public void onStart() {
			}

			@Override
			public void onComplete(SHARE_MEDIA arg0, int arg1,
					SocializeEntity arg2) {
			}
		});
	}
	
	
	public static void getShareDialog(final Context ctx,final String title,final String content,
			final String url, final String imgurl,final ShareListener shareListener){
		final List<String> list = new ArrayList<String>();
//		if (SharedUtil.getWeiboQQ(ctx)) {
//			list.add("腾讯微博");
//		}
//		if (SharedUtil.getWeiboSina(ctx)) {
//			list.add("新浪微博");
//		}
		if (SharedUtil.getWeixinFriends(ctx)) {
			list.add("微信好友");
		}
		if (SharedUtil.getFriendCircle(ctx)) {
			list.add("微信朋友圈");
		}
		
		
		View view = View.inflate(ctx, R.layout.share_layout2, null);
		MyGridView mgv = (MyGridView) view.findViewById(R.id.mgv);
		Button bt_quit = (Button) view.findViewById(R.id.bt_quit);
		pop = new BottomPopupWindow((Activity)ctx, view);
		pop.showAtLocation(view.findViewById(R.id.parent), Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
		mgv.setAdapter(new ShareAdapter(ctx, list));
		mgv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(ToolsUtil.isNetworkConnected(ctx)){
					pop.dismiss();
					if("腾讯微博".equals(list.get(position))){
						share((Activity)ctx, SHARE_MEDIA.TENCENT, content+"-->请点击："+url,imgurl);
					}else if("新浪微博".equals(list.get(position))){
						share((Activity)ctx, SHARE_MEDIA.SINA, content+"-->请点击："+url,imgurl);
					}else if("微信好友".equals(list.get(position))){
						shareWeixinFriend((Activity)ctx,title, content, url, imgurl,shareListener);
					}else if("微信朋友圈".equals(list.get(position))){
						shareWeixinFriends((Activity)ctx, title,content, url, imgurl,shareListener);
					}
				}else{
					Toast.makeText(ctx, "网络不可用，请检查网络连接", 1000).show();
				}
			
			}
		});
		bt_quit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				pop.dismiss();
			}
		});
	}
	
	
	
	public static void getShareDialog(final Context ctx,final String title,final String content,
			final String url, final Bitmap bitmap,final ShareListener shareListener){
		final List<String> list = new ArrayList<String>();
//		if (SharedUtil.getWeiboQQ(ctx)) {
//			list.add("腾讯微博");
//		}
//		if (SharedUtil.getWeiboSina(ctx)) {
//			list.add("新浪微博");
//		}
		if (SharedUtil.getWeixinFriends(ctx)) {
			list.add("微信好友");
		}
		if (SharedUtil.getFriendCircle(ctx)) {
			list.add("微信朋友圈");
		}
		
		
		View view = View.inflate(ctx, R.layout.share_layout2, null);
		MyGridView mgv = (MyGridView) view.findViewById(R.id.mgv);
		Button bt_quit = (Button) view.findViewById(R.id.bt_quit);
		pop = new BottomPopupWindow((Activity)ctx, view);
		pop.showAtLocation(view.findViewById(R.id.parent), Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
		mgv.setAdapter(new ShareAdapter(ctx, list));
		mgv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(ToolsUtil.isNetworkConnected(ctx)){
					pop.dismiss();
					if("腾讯微博".equals(list.get(position))){
						//share((Activity)ctx, SHARE_MEDIA.TENCENT, content+"-->请点击："+url,imgurl);
					}else if("新浪微博".equals(list.get(position))){
						//share((Activity)ctx, SHARE_MEDIA.SINA, content+"-->请点击："+url,imgurl);
					}else if("微信好友".equals(list.get(position))){
						shareWeixinFriend((Activity)ctx,title, content, url, bitmap,shareListener);
					}else if("微信朋友圈".equals(list.get(position))){
						shareWeixinFriends((Activity)ctx, title,content, url, bitmap,shareListener);
					}
				}else{
					Toast.makeText(ctx, "网络不可用，请检查网络连接", 1000).show();
				}
			
			}
		});
		bt_quit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				pop.dismiss();
			}
		});
	}
	
	//分享接口监听
	public interface ShareListener
	{
		void sharedListener_sucess();//分享成
		void sharedListener_Fails(String msg);//分享失败
	}
}
