package com.shenma.yueba.baijia.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenma.yueba.R;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-26 下午3:01:52  
 * 程序的简单说明   圈子二维码 分享对话框
 */

public class CreateOrderDialog extends Dialog implements android.view.View.OnClickListener{
	Context context;
	RelativeLayout ll;
	ImageView qzcodeshare_layouyt_close_imageview;
	TextView qzcodeshare_layout_title_textview;
	Button qzcodeshare_layout_share_button;
	Object obj;
	public CreateOrderDialog(Context context,Object obj) {
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
		ll=(RelativeLayout)RelativeLayout.inflate(context, R.layout.createorder_dialog_layout, null);
		setContentView(ll);
		initView();
	}
	
	void initView()
	{
		qzcodeshare_layouyt_close_imageview=(ImageView)ll.findViewById(R.id.qzcodeshare_layouyt_close_imageview);
		qzcodeshare_layout_title_textview=(TextView)ll.findViewById(R.id.qzcodeshare_layout_title_textview);
		qzcodeshare_layout_share_button=(Button)ll.findViewById(R.id.qzcodeshare_layout_share_button);
		qzcodeshare_layout_share_button.setOnClickListener(this);
		qzcodeshare_layouyt_close_imageview.setOnClickListener(this);
		
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
