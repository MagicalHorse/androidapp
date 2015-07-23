package com.shenma.yueba.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

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

/**
 * 友盟社会化分享
 * 
 * @author a
 * 
 */
public class SocicalShareUtil {
	String appId = "wx0bd15e11e7c3090f";
	String appSecret = "e3ff58518855345970755d08a3540c26";
	private Context ctx;
	private final UMSocialService mController = UMServiceFactory
			.getUMSocialService("com.umeng.share");
	private SHARE_MEDIA mPlatform = SHARE_MEDIA.SINA;
	public SocicalShareUtil(Context ctx) {
		this.ctx = ctx;
	}

	
	
	
	public void setTitle(){
		
	}


	/**
	 * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
	 *       image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
	 *       要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
	 *       : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
	 * @return
	 */
//	private void addQQQZonePlatform() {
//		String appId = "100424468";
//		String appKey = "c7394704798a158208a74ab60104f0ba";
//		// 添加QQ支持, 并且设置QQ分享内容的target url
//		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler((Activity) ctx, appId,
//				appKey);
//		qqSsoHandler.setTargetUrl("http://www.umeng.com/social");
//		qqSsoHandler.addToSocialSDK();
//
//		// 添加QZone平台
//		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler((Activity) ctx,
//				appId, appKey);
//		qZoneSsoHandler.addToSocialSDK();
//	}

	/**
	 * @功能描述 : 添加微信平台分享
	 * @return
	 */
	private void addWXPlatform(Context ctx,String title,String content,String image,String url) {
		// 添加微信平台
		UMWXHandler wxHandler = new UMWXHandler(ctx, appId, appSecret);
		wxHandler.addToSocialSDK();
		WeiXinShareContent weixinContent = new WeiXinShareContent();
		weixinContent.setTitle(title);
		weixinContent.setShareContent(content);
		weixinContent.setTargetUrl(url);
		weixinContent.setShareImage(new UMImage(ctx, image));
		UMSocialService controller = UMServiceFactory.getUMSocialService(
				"com.umeng.share", RequestType.SOCIAL);
		controller.setShareMedia(weixinContent);
		controller.getConfig().closeToast();
	}

	
	private void addWXCircle(final Context ctx,String title,String content,String image,String url){
		UMWXHandler wxHandler = new UMWXHandler(ctx, appId, appSecret);
		wxHandler.setToCircle(true);
		wxHandler.addToSocialSDK();
		CircleShareContent circleMedia = new CircleShareContent();
		circleMedia.setTitle(title);
		circleMedia.setShareContent(content);
		circleMedia.setShareImage(new UMImage(ctx, image));
		circleMedia.setTargetUrl(url);
		UMSocialService controller = UMServiceFactory.getUMSocialService(
				"com.umeng.share", RequestType.SOCIAL);
		controller.setShareMedia(circleMedia);
		controller.getConfig().closeToast();
		controller.postShare(ctx, SHARE_MEDIA.WEIXIN_CIRCLE,
				new SnsPostListener() {

					@Override
					public void onComplete(SHARE_MEDIA arg0, int arg1,
							SocializeEntity arg2) {
						Toast.makeText(ctx, "分享成功", 1000).show();
						
					}

					@Override
					public void onStart() {
						Toast.makeText(ctx, "分享中...", 1000).show();
						
					}

				});
	}
	
	public void showShareDialog() {
		mController.getConfig().setPlatforms(SHARE_MEDIA.WEIXIN,
				SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE,
				SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT);
		mController.openShare((Activity) ctx, false);
	}
}
