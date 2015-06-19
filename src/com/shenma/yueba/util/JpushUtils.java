package com.shenma.yueba.util;

import java.util.LinkedHashSet;
import java.util.Set;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class JpushUtils {
	private static final int MSG_SET_ALIAS = 1001;
	private static final int MSG_SET_TAGS = 1002;
	private Context ctx;
	public JpushUtils(Context ctx) {
		this.ctx = ctx;
	} 
	
	public void setTag(String tags){
        // 检查 tag 的有效性
		if (TextUtils.isEmpty(tags)) {
			Log.i("Jpush", "tag设置为空");
			return;
		}
		// ","隔开的多个 转换成 Set
		String[] sArray = tags.split(",");
		Set<String> tagSet = new LinkedHashSet<String>();
		for (String sTagItme : sArray) {
			if (!ExampleUtil.isValidTagAndAlias(sTagItme)) {
				Log.i("Jpush", "tag不合法");
				return;
			}
			tagSet.add(sTagItme);
		}
		
		//调用JPush API设置Tag
		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));

	} 
	
	public void setAlias(String alias){
		if (TextUtils.isEmpty(alias)) {
			Log.i("Jpush", "alias设置为空");
			return;
		}
		if (!ExampleUtil.isValidTagAndAlias(alias)) {
			Log.i("Jpush", "alias不合法");
			return;
		}
		
		//调用JPush API设置Alias
		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
	}
	
	

	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
            case 0:
                logs = "Set tag and alias success";
                break;
                
            case 6002:
                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                if (ExampleUtil.isConnected(ctx)) {
                	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                } else {
                }
                break;
            
            default:
                logs = "Failed with errorCode = " + code;
            }
            
        }
	    
	};
	
	 private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

	        @Override
	        public void gotResult(int code, String alias, Set<String> tags) {
	            String logs ;
	            switch (code) {
	            case 0:
	                logs = "Set tag and alias success";
	                break;
	                
	            case 6002:
	                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
	                if (ExampleUtil.isConnected(ctx)) {
	                	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
	                } else {
	                }
	                break;
	            
	            default:
	                logs = "Failed with errorCode = " + code;
	            }
	            
	            ExampleUtil.showToast(logs, ctx);
	        }
	        
	    };
	    

		
		

	    private final Handler mHandler = new Handler() {
	        @Override
	        public void handleMessage(android.os.Message msg) {
	            super.handleMessage(msg);
	            switch (msg.what) {
	            case MSG_SET_ALIAS:
	                JPushInterface.setAliasAndTags(ctx, (String) msg.obj, null, mAliasCallback);
	                break;
	                
	            case MSG_SET_TAGS:
	                JPushInterface.setAliasAndTags(ctx, null, (Set<String>) msg.obj, mTagsCallback);
	                break;
	                
	            default:
	            }
	        }
	    };
	
}
