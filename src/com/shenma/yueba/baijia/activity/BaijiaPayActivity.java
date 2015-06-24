package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.BaiJiaPayAdapter;
import com.shenma.yueba.baijia.modle.BaijiaPayInfoBean;
import com.shenma.yueba.baijia.modle.ComputeAmountInfoBean;
import com.shenma.yueba.baijia.modle.CreatOrderInfoBean;
import com.shenma.yueba.wxapi.CreateWeiXinOrderManager;
import com.shenma.yueba.wxapi.WeiXinBasePayManager.WeiXinPayManagerListener;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-16 上午10:03:01  
 * 程序的简单说明  
 */

public class BaijiaPayActivity extends BaseActivityWithTopView{
View parentView;
ListView baijiapay_layout_paytype_listview;
BaiJiaPayAdapter baiJiaPayAdapter;
List<BaijiaPayInfoBean> bean=new ArrayList<BaijiaPayInfoBean>();
CreatOrderInfoBean creatOrderInfoBean;
//微信支付 商品名描述
String messageTitle="";
//微信支付商品信息描述
String messageDesc="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		parentView=this.getLayoutInflater().inflate(R.layout.baijiapaylayout, null);
		setContentView(parentView);
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		if(this.getIntent().getSerializableExtra("PAYDATA")!=null)
		{
			creatOrderInfoBean=(CreatOrderInfoBean)this.getIntent().getSerializableExtra("PAYDATA");
		}else
		{
			finish();
			MyApplication.getInstance().showMessage(BaijiaPayActivity.this, "数据错误，请从订单页面进入");
			return;
		}
		if(this.getIntent().getStringExtra("MessageTitle")!=null)
		{
			messageTitle=this.getIntent().getStringExtra("MessageTitle");
		}
		if(this.getIntent().getStringExtra("MessageDesc")!=null)
		{
			messageDesc=this.getIntent().getStringExtra("MessageDesc");
		}
		initView();
	}
	
	void initView()
	{
		setTitle("选择付款方式");
		setLeftTextView(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				BaijiaPayActivity.this.finish();
			}
		});
		bean.add(new BaijiaPayInfoBean(R.drawable.weixin_icon,BaijiaPayInfoBean.Type.weixinpay,"请支付",Double.toString(creatOrderInfoBean.getTotalAmount()),"微信支付","微信安全支付"));
		baiJiaPayAdapter=new BaiJiaPayAdapter(bean, BaijiaPayActivity.this);
		baijiapay_layout_paytype_listview=(ListView)parentView.findViewById(R.id.baijiapay_layout_paytype_listview);
		baijiapay_layout_paytype_listview.setAdapter(baiJiaPayAdapter);
		baijiapay_layout_paytype_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
				BaijiaPayInfoBean baijiaPayInfoBean=bean.get(arg2);
				switch(baijiaPayInfoBean.getType())
				{
				case weixinpay:
					//启动微信支付
					startWenXinPay();
					break;
				}
			}
		});
	}
	
	void startWenXinPay()
	{
		//WeiXinPayManagerbar wxpm=new WeiXinPayManagerbar(this);
		//wxpm.createWenXinPay(creatOrderInfoBean,messageTitle,messageDesc);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("messageTitle", messageTitle);
		map.put("TotalAmount", creatOrderInfoBean.getTotalAmount());
		map.put("OrderNo", creatOrderInfoBean.getOrderNo());
		CreateWeiXinOrderManager cwxm=new CreateWeiXinOrderManager(BaijiaPayActivity.this, new WeiXinPayManagerListener() {
			
			@Override
			public void success(Object obj) {
				
			}
			
			@Override
			public void fails(String msg) {
				MyApplication.getInstance().showMessage(BaijiaPayActivity.this, msg);
			}
		},map );
		cwxm.execute();
	}
}
