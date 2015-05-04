package com.shenma.yueba.receiver;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.BaseAdapterWithUtil;
import com.shenma.yueba.baijia.modle.MsgBean;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.yangjia.modle.SalesItemBean;

public class SalesAdapter extends BaseAdapterWithUtil {
	private List<SalesItemBean> mList = new ArrayList<SalesItemBean>();

	public SalesAdapter(Context ctx,List<SalesItemBean> mList) {
		super(ctx);
		this.mList = mList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressWarnings("null")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if(convertView == null){
			
			holder = new Holder();
			convertView = View.inflate(ctx, R.layout.sales_manager_list_item, null);
			holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
			holder.tv_grade = (TextView) convertView.findViewById(R.id.tv_grade);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
			holder.iv_product = (ImageView) convertView.findViewById(R.id.iv_product);
			holder.tv_product_name = (TextView) convertView.findViewById(R.id.tv_product_name);
			holder.tv_description = (TextView) convertView.findViewById(R.id.tv_description);
			holder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
			holder.tv_from_title = (TextView) convertView.findViewById(R.id.tv_from_title);
			holder.tv_from_content = (TextView) convertView.findViewById(R.id.tv_from_content);
//			MyApplication.getInstance().getImageLoader().displayImage("http://img3.redocn.com/20091221/20091217_fa2a743db1f556f82b9asJ320coGmYFf.jpg", 
//					holder.iv_icon, MyApplication.getInstance().getRoundDisplayImageOptions());
//			MyApplication.getInstance().getImageLoader().displayImage("http://img3.redocn.com/20091221/20091217_fa2a743db1f556f82b9asJ320coGmYFf.jpg", 
//					holder.iv_product, MyApplication.getInstance().getRoundDisplayImageOptions());
			FontManager.changeFonts(ctx, holder.tv_grade,holder.tv_name,holder.tv_time,holder.tv_money,
					holder.tv_product_name,holder.tv_description,holder.tv_size,holder.tv_from_title,
					holder.tv_from_content);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		return convertView;
	}
	
	
	class Holder{
		ImageView iv_icon;
		ImageView iv_product;
		TextView tv_grade;
		TextView tv_name;
		TextView tv_time;
		TextView tv_money;
		TextView tv_product_name;
		TextView tv_description;
		TextView tv_size;
		TextView tv_from_title;
		TextView tv_from_content;
	}

}
