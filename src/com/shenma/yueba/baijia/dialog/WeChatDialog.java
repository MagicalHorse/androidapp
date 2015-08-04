package com.shenma.yueba.baijia.dialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.Base64Coder;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.SharedUtil;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.yangjia.modle.kaixiaoPiaoBean;

/**
 * @author zkk
 * @version 创建时间：2015-8-04-11:01
 */

public class WeChatDialog extends Dialog implements
		android.view.View.OnClickListener, OnLongClickListener {
	Context context;
	RelativeLayout ll;
	kaixiaoPiaoBean obj;

	public WeChatDialog(Context context, kaixiaoPiaoBean obj) {
		super(context, R.style.MyDialog);
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		this.context = context;
		this.obj = obj;
		setOwnerActivity((Activity) context);
		// this.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.color.color_transparent));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ll = (RelativeLayout) RelativeLayout.inflate(context,
				R.layout.wechat_dialog_layout, null);
		setContentView(ll);
		initView();
	}

	void initView() {
		ImageView iv_code = (ImageView) ll.findViewById(R.id.iv_code);
		iv_code.setOnLongClickListener(this);
		initBitmap(SharedUtil.getHeadImage(getContext()), iv_code);
		TextView tv_title = (TextView) ll.findViewById(R.id.tv_title);
		TextView tv_wechat_name = (TextView) ll
				.findViewById(R.id.tv_wechat_name);
		TextView tv_save = (TextView) ll.findViewById(R.id.tv_save);
		ImageView iv_close = (ImageView) ll.findViewById(R.id.iv_close);
		iv_close.setOnClickListener(this);
		byte[] bytes = Base64Coder.decode(obj.getQrCode());
		LayoutParams params = iv_code.getLayoutParams();
		params.width = ToolsUtil.getDisplayWidth(context) / 4 * 3;
		params.height = ToolsUtil.getDisplayWidth(context) / 4 * 3;
		iv_code.setLayoutParams(params);
		iv_code.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0,
				bytes.length));
		FontManager.changeFonts(context, tv_title, tv_wechat_name, tv_save);
	}

	@Override
	public void show() {

		super.show();

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.iv_close:
			this.cancel();
			break;
		}
	}

	void initBitmap(final String url, final ImageView iv) {
		MyApplication.getInstance().getBitmapUtil().display(iv, url);
	}

	@Override
	public boolean onLongClick(View arg0) {
		byte[] bytes = Base64Coder.decode(obj.getQrCode());
		Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0,
				bytes.length);
		saveBitmapToAlbum(context, bitmap);
		return false;
	}
	
	
	
	
	
	/**
	 * 保存图片到相册
	 */
		public static void saveBitmapToAlbum(Context context, Bitmap bmp) {
		    // 首先保存图片
		    File appDir = new File(Environment.getExternalStorageDirectory(), Constants.APP_WENJIANJIA);
		    if (!appDir.exists()) {
		        appDir.mkdirs();
		    }
		    String fileName = "huimaishou"+System.currentTimeMillis() + ".jpg";
		    File file = new File(appDir, fileName);
		    try {
		        FileOutputStream fos = new FileOutputStream(file);
		        bmp.compress(CompressFormat.JPEG, 100, fos);
		        fos.flush();
		        fos.close();
		    } catch (FileNotFoundException e) {
		        e.printStackTrace();
		    } catch (IOException e) {
		        e.printStackTrace();
			}
		    
		    // 其次把文件插入到系统图库
		    try {
		        MediaStore.Images.Media.insertImage(context.getContentResolver(),
						file.getAbsolutePath(), fileName, null);
		    } catch (FileNotFoundException e) {
		        e.printStackTrace();
		    }
		    // 最后通知图库更新
		    context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + appDir+fileName)));
	}
	
}
