package com.shenma.yueba.baijia.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.modle.BaijiaPayInfoBean;
import com.shenma.yueba.util.ToolsUtil;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-16 上午10:24:18  
 * 程序的简单说明  
 */

public class BaiJiaPayAdapter extends BaseAdapter{
	List<BaijiaPayInfoBean> bean=new ArrayList<BaijiaPayInfoBean>();
    Context context;
    
    public BaiJiaPayAdapter(List<BaijiaPayInfoBean> bean,Context context)
    {
    	this.bean=bean;
    	this.context=context;
    }
    
	@Override
	public int getCount() {
		return bean.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if(convertView==null)
		{
			holder=new Holder();
			convertView=LinearLayout.inflate(context, R.layout.baijiapay_layout_item, null);
			convertView.setTag(holder);
			holder.iv=(ImageView)convertView.findViewById(R.id.baijiapay_layout_item_imageview);
			holder.tv1=(TextView)convertView.findViewById(R.id.baijiapay_layout_item_textview1);
			holder.tv2=(TextView)convertView.findViewById(R.id.baijiapay_layout_item_textview2);
			holder.tv3=(TextView)convertView.findViewById(R.id.baijiapay_layout_item_textview3);
			holder.tv4=(TextView)convertView.findViewById(R.id.baijiapay_layout_item_textview4);
			ToolsUtil.setFontStyle(context, convertView, R.id.baijiapay_layout_item_textview1,R.id.baijiapay_layout_item_textview2,R.id.baijiapay_layout_item_textview3,R.id.baijiapay_layout_item_textview4);
		}else
		{
			holder=(Holder)convertView.getTag();
		}
		BaijiaPayInfoBean baijiaPayInfoBean=bean.get(position);
		if(baijiaPayInfoBean.getIcon()>0)
		{
			holder.iv.setImageResource(baijiaPayInfoBean.getIcon());
		}else
		{
			holder.iv.setImageResource(R.drawable.default_pic);
		}
		holder.tv1.setText(ToolsUtil.nullToString(baijiaPayInfoBean.getExtname1()));
		holder.tv2.setText(ToolsUtil.nullToString(baijiaPayInfoBean.getExtname2()));
		holder.tv3.setText(ToolsUtil.nullToString(baijiaPayInfoBean.getExtname3()));
		holder.tv4.setText(ToolsUtil.nullToString(baijiaPayInfoBean.getExtname4()));
		return convertView;
	}
	
    class Holder
    {
    	TextView tv1,tv2,tv3,tv4;
    	ImageView iv;
    }
}
