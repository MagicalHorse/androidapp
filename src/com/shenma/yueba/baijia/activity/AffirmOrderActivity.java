package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.adapter.AffirmAdapter;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.ListViewUtils;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.RoundImageView;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-1 上午10:19:39  
 * 程序的简单说明  
 */

public class AffirmOrderActivity extends BaseActivityWithTopView{
	//头像
RoundImageView affirmorder_layout_icon_roundimageview;
//拨打电话
ImageView affirmorder_layout_icon_imageview;
View parentview;
ListView affirmorder_layout_product_listview;
//提交
Button affrimorder_layout_footer_sumit_button;
HttpControl httpControl=new HttpControl();
List<Object> productlist=new ArrayList<Object>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		parentview=this.getLayoutInflater().inflate(R.layout.affirmorder_layout, null);
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		setContentView(parentview);
		super.onCreate(savedInstanceState);
		initView();
	}
	
	void initView()
	{
		productlist.add(null);
		setTitle("确认订单");
		setLeftTextView(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				submitData();
			}
		});
		affirmorder_layout_icon_roundimageview=(RoundImageView)findViewById(R.id.affirmorder_layout_icon_roundimageview);
		affirmorder_layout_icon_imageview=(ImageView)findViewById(R.id.affirmorder_layout_icon_imageview);
		//头像监听
		affirmorder_layout_icon_imageview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
			}
		});
		
		affirmorder_layout_product_listview=(ListView)findViewById(R.id.affirmorder_layout_product_listview);
		AffirmAdapter affirmAdapter=new AffirmAdapter(this,productlist);
		affirmorder_layout_product_listview.setAdapter(affirmAdapter);
		ListViewUtils.setListViewHeightBasedOnChildren(affirmorder_layout_product_listview);
		
		ToolsUtil.setFontStyle(this, parentview, R.id.tv_top_title, null);
		ToolsUtil.setFontStyle(this, parentview, R.id.affirmorder_layout_no_textview, null);
		ToolsUtil.setFontStyle(this, parentview, R.id.affirmorder_layout_phone_textview, null);
		ToolsUtil.setFontStyle(this, parentview, R.id.affirmorder_layout_pickup_textview, null);
		ToolsUtil.setFontStyle(this, parentview, R.id.affrimorder_layout_footer_countpricename_textview, null);
		ToolsUtil.setFontStyle(this, parentview, R.id.affrimorder_layout_footer_sumit_button, null);
		
		//需要负值的
		ToolsUtil.setFontStyle(this, parentview, R.id.affirmorder_layout_novalue_textview, null);
		ToolsUtil.setFontStyle(this, parentview, R.id.affirmorder_layout_phonevalue_textview, null);
		ToolsUtil.setFontStyle(this, parentview, R.id.affirmorder_layout_pickupvalue_textview, null);
		ToolsUtil.setFontStyle(this, parentview, R.id.affrimorder_layout_footer_countprice_textview, null);
		ToolsUtil.setFontStyle(this, parentview, R.id.affirmorder_item_allcount_textview, null);
		ToolsUtil.setFontStyle(this, parentview, R.id.affirmorder_item_price_textview, null);
		ToolsUtil.setFontStyle(this, parentview, R.id.affirmorder_item_pricevalue_textview, null);
		
		affrimorder_layout_footer_sumit_button=(Button)parentview.findViewById(R.id.affrimorder_layout_footer_sumit_button);
		
		
		
		
	}
	
	/******
	 * 提交 确认商品的数据
	 * ***/
	void submitData()
	{
		
	}
}
