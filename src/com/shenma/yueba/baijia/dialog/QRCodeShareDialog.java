package com.shenma.yueba.baijia.dialog;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.util.Base64Coder;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.yangjia.modle.kaixiaoPiaoBean;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-26 下午3:01:52  
 * 程序的简单说明   圈子二维码 分享对话框
 */

public class QRCodeShareDialog extends Dialog implements android.view.View.OnClickListener{
	Context context;
	RelativeLayout ll;
	ImageView qzcodeshare_layouyt_close_imageview;
	TextView qzcodeshare_layouyt_name_textview;
	TextView qzcodeshare_layout_title_textview;
	Button qzcodeshare_layout_share_button;
	kaixiaoPiaoBean obj;
	private TextView qzcodeshare_layouyt_count_textview;
	private ImageView qzcodeshare_layout_content_imageview;
	public QRCodeShareDialog(Context context,kaixiaoPiaoBean obj) {
		super(context, R.style.MyDialog);
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		this.context=context;
		this.obj=obj;
		setOwnerActivity((Activity)context);
        //this.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.color.color_transparent));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		ll=(RelativeLayout)RelativeLayout.inflate(context, R.layout.qzcodeshare_layout, null);
		setContentView(ll);
		initView();
	}
	
	void initView()
	{
		qzcodeshare_layouyt_close_imageview=(ImageView)ll.findViewById(R.id.qzcodeshare_layouyt_close_imageview);
		qzcodeshare_layout_content_imageview = (ImageView)ll.findViewById(R.id.qzcodeshare_layout_content_imageview);
		qzcodeshare_layout_title_textview=(TextView)ll.findViewById(R.id.qzcodeshare_layout_title_textview);
		qzcodeshare_layouyt_name_textview=(TextView)ll.findViewById(R.id.qzcodeshare_layouyt_name_textview);
		qzcodeshare_layouyt_count_textview = (TextView)ll.findViewById(R.id.qzcodeshare_layouyt_count_textview);
		qzcodeshare_layout_share_button=(Button)ll.findViewById(R.id.qzcodeshare_layout_share_button);
		qzcodeshare_layout_share_button.setOnClickListener(this);
		qzcodeshare_layouyt_close_imageview.setOnClickListener(this);
		qzcodeshare_layouyt_name_textview.setText("订单号："+obj.getOrderNo());
		qzcodeshare_layouyt_count_textview.setText("￥"+obj.getAmount());
		byte[] bytes = Base64Coder.decode(obj.getQrCode());
		LayoutParams params = qzcodeshare_layout_content_imageview.getLayoutParams();
		params.width = ToolsUtil.getDisplayWidth(context)/4*3;
		params.height = ToolsUtil.getDisplayWidth(context)/4*3;
		qzcodeshare_layout_content_imageview.setLayoutParams(params);
		qzcodeshare_layout_content_imageview.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
		FontManager.changeFonts(context,qzcodeshare_layouyt_name_textview,qzcodeshare_layouyt_count_textview);
	}
	
	@Override
	public void show() {
		
		super.show();
		
	}

	@Override
	public void onClick(View v) {
		
		switch(v.getId())
		{
		case R.id.qzcodeshare_layouyt_close_imageview:
			this.cancel();
			break;
		case R.id.qzcodeshare_layout_share_button:
			break;
		}
	}
}
