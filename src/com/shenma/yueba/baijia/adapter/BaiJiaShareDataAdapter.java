package com.shenma.yueba.baijia.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.BaiJiaShareInfoBean;
import com.shenma.yueba.util.ToolsUtil;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-2 下午7:26:13  
 * 程序的简单说明  
 */

public class BaiJiaShareDataAdapter extends BaseAdapter{
	List<BaiJiaShareInfoBean> bean_array=new ArrayList<BaiJiaShareInfoBean>();
	Context context;
	public BaiJiaShareDataAdapter(Context context,List<BaiJiaShareInfoBean> bean_array)
	{
		this.bean_array=bean_array;
		this.context=context;
	}
	
	@Override
	public int getCount() {
		
		return bean_array.size();
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
			convertView=(RelativeLayout)LinearLayout.inflate(context, R.layout.baijiashare_layout_item, null);
			holder.baijiashare_layout_item_head_imageview=(ImageView)convertView.findViewById(R.id.baijiashare_layout_item_head_imageview);
			holder.baijiashare_layout_item_icon_imageview=(ImageView)convertView.findViewById(R.id.baijiashare_layout_item_icon_imageview);
			holder.baijiashare_layout_item_name_textview=(TextView)convertView.findViewById(R.id.baijiashare_layout_item_name_textview);
			holder.baijiashare_layout_item_price_textview=(TextView)convertView.findViewById(R.id.baijiashare_layout_item_price_textview);
			holder.baijiashare_layout_item_head_imageview.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(v.getTag()!=null)
					{
						BaiJiaShareInfoBean bean=(BaiJiaShareInfoBean)v.getTag();
						if(bean.isIscheck())
						{
							bean.setIscheck(false);
							v.setSelected(false);
						}else
						{
							bean.setIscheck(true);
							v.setSelected(true);
						}
					}
				}
			});
			convertView.setTag(holder);
		}else
		{
			holder=(Holder)convertView.getTag();
		}
		setValue(holder,position);
		return convertView;
	}

	class Holder
	{
		ImageView baijiashare_layout_item_head_imageview;
		ImageView baijiashare_layout_item_icon_imageview;
		TextView baijiashare_layout_item_name_textview;
		TextView baijiashare_layout_item_price_textview;
	}
	
	void setValue(Holder holder,int position)
	{
		BaiJiaShareInfoBean bean =bean_array.get(position);
		holder.baijiashare_layout_item_head_imageview.setTag(bean);
		if(bean.isIscheck())
		{
			holder.baijiashare_layout_item_head_imageview.setSelected(true);
		}else
		{
			holder.baijiashare_layout_item_head_imageview.setSelected(false);
		}
		MyApplication.getInstance().getImageLoader().displayImage(ToolsUtil.getImage(ToolsUtil.nullToString(bean.getLogo()), 320, 0), holder.baijiashare_layout_item_icon_imageview, MyApplication.getInstance().getDisplayImageOptions());
		holder.baijiashare_layout_item_name_textview.setText(ToolsUtil.nullToString(bean.getName()));
		holder.baijiashare_layout_item_price_textview.setText("￥"+Double.toString(bean.getPrice()));
	}
}