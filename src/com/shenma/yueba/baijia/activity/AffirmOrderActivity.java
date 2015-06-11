package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.AffirmAdapter;
import com.shenma.yueba.baijia.modle.PrioductSizesInfoBean;
import com.shenma.yueba.baijia.modle.ProductsDetailsInfoBean;
import com.shenma.yueba.baijia.modle.RequestCreatOrderInfoBean;
import com.shenma.yueba.baijia.modle.RequestProductDetailsInfoBean;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
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
//提货人手机号
TextView affirmorder_item_tihuophonevalue_textview;
//买手号码
TextView affirmorder_layout_novalue_textview;
//买手电话
TextView affirmorder_layout_phonevalue_textview;
//提货地址
TextView affirmorder_layout_pickupvalue_textview;
//商品个数
TextView affirmorder_item_allcount_textview;
//实际付款
TextView affirmorder_item_pricevalue_textview;
//打烊
TextView affirmorder_item_youhuicontext_textview;
//提交
Button affrimorder_layout_footer_sumit_button;
HttpControl httpControl=new HttpControl();
List<ProductsDetailsInfoBean> productlist=new ArrayList<ProductsDetailsInfoBean>();
ProductsDetailsInfoBean productsDetailsInfoBean;
int buyCount=-1;//购买数量
PrioductSizesInfoBean currCheckedFouce=null;//选择的尺寸数据
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		parentview=this.getLayoutInflater().inflate(R.layout.affirmorder_layout, null);
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		setContentView(parentview);
		super.onCreate(savedInstanceState);
		if(this.getIntent().getSerializableExtra("DATA")!=null && this.getIntent().getSerializableExtra("DATA") instanceof RequestProductDetailsInfoBean)
		{
			RequestProductDetailsInfoBean bean=(RequestProductDetailsInfoBean)this.getIntent().getSerializableExtra("DATA");
			productsDetailsInfoBean=bean.getData();
			productlist.add(productsDetailsInfoBean);
			buyCount=this.getIntent().getIntExtra("COUNT", -1);
			if(buyCount<=0 || this.getIntent().getSerializableExtra("CHECKEDPrioductSizes")==null)
			{
				MyApplication.getInstance().showMessage(this, "数据错误");
				finish();
				return;
			}
			currCheckedFouce=(PrioductSizesInfoBean)this.getIntent().getSerializableExtra("CHECKEDPrioductSizes");
			if(productsDetailsInfoBean!=null)
			{
				initView();
				ToolsUtil.setFontStyle(AffirmOrderActivity.this, parentview, R.id.affirmorder_item_youhuititle_textview,R.id.affirmorder_item_youhuicontext_textview);
			}else
			{
				MyApplication.getInstance().showMessage(this, "数据错误");
				finish();
				return;
			}
			
		}else
		{
			MyApplication.getInstance().showMessage(this, "数据错误");
			finish();
			return;
		}
		
	}
	
	void initView()
	{
		setTitle("确认订单");
		setLeftTextView(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				submitData();
			}
		});

		//买手号码
		affirmorder_layout_novalue_textview=(TextView)findViewById(R.id.affirmorder_layout_novalue_textview);
		// 买手电话
		affirmorder_layout_phonevalue_textview=(TextView)findViewById(R.id.affirmorder_layout_phonevalue_textview);
		//提货地址
		affirmorder_layout_pickupvalue_textview=(TextView)findViewById(R.id.affirmorder_layout_pickupvalue_textview);
		//商品总个数
		affirmorder_item_allcount_textview=(TextView)findViewById(R.id.affirmorder_item_allcount_textview);
		//实际付款
		affirmorder_item_pricevalue_textview=(TextView)findViewById(R.id.affirmorder_item_pricevalue_textview);
		//打烊
		affirmorder_item_youhuicontext_textview=(TextView)findViewById(R.id.affirmorder_item_youhuicontext_textview);
		
		//提货电话
		affirmorder_item_tihuophonevalue_textview=(TextView)findViewById(R.id.affirmorder_item_tihuophonevalue_textview);
		affirmorder_layout_icon_roundimageview=(RoundImageView)findViewById(R.id.affirmorder_layout_icon_roundimageview);
		affirmorder_layout_icon_roundimageview.setTag(productsDetailsInfoBean);
		affirmorder_layout_icon_roundimageview.setOnClickListener(onClickListener);
		MyApplication.getInstance().getImageLoader().displayImage(ToolsUtil.nullToString(productsDetailsInfoBean.getBuyerLogo()), affirmorder_layout_icon_roundimageview);
		affirmorder_layout_icon_imageview=(ImageView)findViewById(R.id.affirmorder_layout_icon_imageview);
		//设置联系电话
		affirmorder_layout_icon_imageview.setTag(ToolsUtil.nullToString(productsDetailsInfoBean.getBuyerMobile()));
		//电话
		affirmorder_layout_icon_imageview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String phoneNo=(String)v.getTag();
				if(!phoneNo.equals(""))
				{
					//调用拨号键
					Uri telUri = Uri.parse("tel:+phoneNo+");
					Intent intent= new Intent(Intent.ACTION_DIAL, telUri);
					startActivity(intent); 
				}
				
			}
		});
		
		affirmorder_layout_product_listview=(ListView)findViewById(R.id.affirmorder_layout_product_listview);
		AffirmAdapter affirmAdapter=new AffirmAdapter(this,productlist,currCheckedFouce,buyCount);
		affirmorder_layout_product_listview.setAdapter(affirmAdapter);
		ListViewUtils.setListViewHeightBasedOnChildren(affirmorder_layout_product_listview);
		
		ToolsUtil.setFontStyle(this, parentview, R.id.tv_top_title, null);
		ToolsUtil.setFontStyle(this, parentview, R.id.affirmorder_layout_no_textview, null);
		ToolsUtil.setFontStyle(this, parentview, R.id.affirmorder_layout_phone_textview, null);
		ToolsUtil.setFontStyle(this, parentview, R.id.affirmorder_layout_pickup_textview, null);
		ToolsUtil.setFontStyle(this, parentview, R.id.affrimorder_layout_footer_countpricename_textview, null);
		ToolsUtil.setFontStyle(this, parentview, R.id.affrimorder_layout_footer_sumit_button, null);
		
		//需要负值的
		ToolsUtil.setFontStyle(this, parentview, R.id.affirmorder_layout_novalue_textview, productsDetailsInfoBean.getBuyerName());
		ToolsUtil.setFontStyle(this, parentview, R.id.affirmorder_layout_phonevalue_textview, productsDetailsInfoBean.getBuyerMobile());
		ToolsUtil.setFontStyle(this, parentview, R.id.affirmorder_layout_pickupvalue_textview, ToolsUtil.nullToString(productsDetailsInfoBean.getPickAddress()));
		ToolsUtil.setFontStyle(this, parentview, R.id.affirmorder_item_allcount_textview, "共"+buyCount+"件商品");
		ToolsUtil.setFontStyle(this, parentview, R.id.affirmorder_item_price_textview, null);
		double allPrice=0.00;
		allPrice=buyCount * productsDetailsInfoBean.getPrice();
		ToolsUtil.setFontStyle(this, parentview, R.id.affirmorder_item_pricevalue_textview, ToolsUtil.DounbleToString_2(allPrice));
		
		//确定
		affrimorder_layout_footer_sumit_button=(Button)parentview.findViewById(R.id.affrimorder_layout_footer_sumit_button);
		affrimorder_layout_footer_sumit_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(affirmorder_item_tihuophonevalue_textview.getText().toString().trim().equals(""))
				{
					MyApplication.getInstance().showMessage(AffirmOrderActivity.this, "请输入提货人手机号");
					return;
				}
				submitData();
			}
		});
		ToolsUtil.setFontStyle(this, parentview, R.id.affrimorder_layout_footer_countprice_textview, ToolsUtil.DounbleToString_2(allPrice));
		
		ToolsUtil.setFontStyle(this, parentview, R.id.affirmorder_item_tihuophone_textview,R.id.affirmorder_item_tihuophonevalue_textview,R.id.affirmorder_item_tihuophonetitle_textview);
		
		
	}
	
	/******
	 * 提交 确认商品的数据
	 * ***/
	void submitData()
	{
		String phone=affirmorder_item_tihuophonevalue_textview.getText().toString().trim();
		httpControl.createProductOrder(phone,productsDetailsInfoBean.getProductId(), buyCount, currCheckedFouce.getSizeId(), true, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
		      if(obj !=null && obj instanceof RequestCreatOrderInfoBean)
		      {
		    	  MyApplication.getInstance().showMessage(AffirmOrderActivity.this, "下单成功");
		    	  finish();
		      }
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				MyApplication.getInstance().showMessage(AffirmOrderActivity.this, msg);
			}
		}, AffirmOrderActivity.this);
	}
	
	
	OnClickListener onClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
			case R.id.affirmorder_layout_icon_roundimageview:
				if(v.getTag() !=null && v.getTag() instanceof ProductsDetailsInfoBean)
				{
					ProductsDetailsInfoBean bean=(ProductsDetailsInfoBean)v.getTag();
					Intent intent=new Intent(AffirmOrderActivity.this,ShopMainActivity.class);
					startActivity(intent);
				}
				
				break;
			}
		}
	};
}
