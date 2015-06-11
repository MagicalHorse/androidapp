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
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.BaiJiaOrdeDetailsInfoBean;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.ToolsUtil;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-1 上午11:22:39  
 * 程序的简单说明   定义 订单确认 的商品列表适配器
 */

public class BaijiaOrderDetailsAdapter extends BaseAdapter{
Context context;
List<BaiJiaOrdeDetailsInfoBean> obj_list=new ArrayList<BaiJiaOrdeDetailsInfoBean>();
	public BaijiaOrderDetailsAdapter(Context context,List<BaiJiaOrdeDetailsInfoBean> obj_list)
	{
		this.context=context;
		this.obj_list=obj_list;
	}
	
	@Override
	public int getCount() {
		
		return obj_list.size();
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
			convertView=(LinearLayout)LinearLayout.inflate(context, R.layout.baijiaorderdetails_item, null);
			holder.affirmorder_item_productname_textview=(TextView)convertView.findViewById(R.id.affirmorder_item_productname_textview);
			holder.affirmorder_item_productsize_textview=(TextView)convertView.findViewById(R.id.affirmorder_item_productsize_textview);
			holder.affirmorder_item_productcount_textview=(TextView)convertView.findViewById(R.id.affirmorder_item_productcount_textview);
			holder.affirmorder_item_productprice_textview=(TextView)convertView.findViewById(R.id.affirmorder_item_productprice_textview);
			holder.affirmorder_item_icon_imageview=(ImageView)convertView.findViewById(R.id.affirmorder_item_icon_imageview);
			convertView.setTag(holder);
			FontManager.changeFonts(context,holder.affirmorder_item_productname_textview,holder.affirmorder_item_productsize_textview,holder.affirmorder_item_productcount_textview,holder.affirmorder_item_productprice_textview);
		}else
		{
			holder=(Holder)convertView.getTag();
		}
		setValue(holder,position);
		return convertView;
	}

 class	Holder 
 {
	 ImageView affirmorder_item_icon_imageview;//商品图片
	 TextView affirmorder_item_productname_textview;//商品名称
	 TextView affirmorder_item_productsize_textview;//尺寸大小等信息
	 TextView affirmorder_item_productcount_textview;//购买数量
	 TextView affirmorder_item_productprice_textview;//单价
 }
 
 void setValue(Holder holder,int position)
 {
	 BaiJiaOrdeDetailsInfoBean baiJiaOrdeDetailsInfoBean= obj_list.get(position);
	 MyApplication.getInstance().getImageLoader().displayImage(ToolsUtil.getImage(ToolsUtil.nullToString(baiJiaOrdeDetailsInfoBean.getProductPic()), 320, 0), holder.affirmorder_item_icon_imageview, MyApplication.getInstance().getDisplayImageOptions());
	 holder.affirmorder_item_productname_textview.setText(ToolsUtil.nullToString(baiJiaOrdeDetailsInfoBean.getProductName()));
	 holder.affirmorder_item_productsize_textview.setText(ToolsUtil.nullToString(baiJiaOrdeDetailsInfoBean.getSizeName()+"  "+baiJiaOrdeDetailsInfoBean.getSizeValue()));
	 holder.affirmorder_item_productcount_textview.setText(ToolsUtil.nullToString("x"+baiJiaOrdeDetailsInfoBean.getProductCount()));
	 holder.affirmorder_item_productprice_textview.setText(ToolsUtil.nullToString("￥"+baiJiaOrdeDetailsInfoBean.getPrice()));
	 
 }
 
}
