package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.BaiJiaPayAdapter;
import com.shenma.yueba.baijia.modle.BaijiaPayInfoBean;

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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		parentView=this.getLayoutInflater().inflate(R.layout.baijiapaylayout, null);
		setContentView(parentView);
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		initView();
	}
	
	void initView()
	{
		bean.add(new BaijiaPayInfoBean(R.drawable.weixin_icon,BaijiaPayInfoBean.Type.weixinpay,"请支付","0.00","微信支付","微信安全支付"));
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
					
					break;
				}
			}
		});
	}
}