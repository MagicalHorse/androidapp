package com.shenma.yueba.yangjia.activity;

import java.io.File;
import java.util.List;
import java.util.Map;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.util.FileUtils;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.TagImageView;
import com.shenma.yueba.yangjia.modle.TagListBean;

/**
 * 发布商品
 * 
 * @author a
 * 
 */
public class PublishProductActivity extends BaseActivityWithTopView implements
		OnClickListener {

	private TextView tv_product_number;
	private EditText et_product_number;
	private TextView tv_price_title;
	private EditText et_price;
	private TextView tv_yuan;
	private EditText et_introduce;
	private TextView tv_retain;
	private EditText et_guige;
	private EditText et_kucun;
	private TextView tv_add_guige;
	private TextView tv_publish;
	private LinearLayout ll_pictures_container;
	private List<Map<String, Double>> tagList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.publish_product);
		super.onCreate(savedInstanceState);
		getIntentData();
		initView();
	}

	private void getIntentData() {
		TagListBean bean = (TagListBean) getIntent().getSerializableExtra("tagListBean");
		 tagList = bean.getTagList();
		
		
		
	}

	private void initView() {
		ll_pictures_container = getView(R.id.ll_pictures_container);
		setImageView();
		tv_product_number = getView(R.id.tv_product_number);
		et_product_number = getView(R.id.et_product_number);
		tv_price_title = getView(R.id.tv_price_title);
		et_price = getView(R.id.et_price);
		tv_yuan = getView(R.id.tv_yuan);
		et_introduce = getView(R.id.et_introduce);
		tv_retain = getView(R.id.tv_retain);
		et_guige = getView(R.id.et_guige);
		et_kucun = getView(R.id.et_kucun);
		tv_add_guige = getView(R.id.tv_add_guige);
		tv_publish = getView(R.id.tv_publish);
		tv_add_guige.setOnClickListener(this);
		tv_publish.setOnClickListener(this);
		FontManager.changeFonts(mContext, tv_product_number, et_product_number,
				tv_price_title, et_price, tv_yuan, et_introduce, tv_retain,
				et_guige, et_kucun, tv_add_guige, tv_publish);

	}

	private void setImageView() {
		for (int i = 0; i < 3; i++) {
			View view = View.inflate(mContext, R.layout.tag_imageview, null);
			TagImageView layout_tag_image = (TagImageView) view.findViewById(R.id.layout_tag_image);
			ImageView iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.height = ToolsUtil.getDisplayWidth(mContext)/3-10;
			params.width = ToolsUtil.getDisplayWidth(mContext)/3-10;
			iv_pic.setLayoutParams(params);
//			for (int j = 0; j <tagList.size(); j++) {
//				double x= tagList.get(j).get("x");
//				double y = tagList.get(j).get("y");
//				
//				layout_tag_image.addTextTag("aaaa",((int)((iv_pic.getX()+iv_pic.getWidth())*x)), ((int)(iv_pic.getHeight()*y)));
//			}
			if(new File(FileUtils.getRootPath()+"/tagPic/"+"tagPic"+i+".jpg").exists()){
				iv_pic.setImageBitmap(BitmapFactory.decodeFile(FileUtils.getRootPath()+"/tagPic/"+"tagPic"+i+".jpg"));
			}else{
				iv_pic.setBackgroundResource(R.drawable.ic_launcher);
			}
			ll_pictures_container.addView(view);
			
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_add_guige://添加规格

			break;
		case R.id.tv_publish://发布商品

			break;
		default:
			break;
		}

	}
}
