package com.shenma.yueba.baijia.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.BrandListBean;
import com.shenma.yueba.baijia.modle.MyCircleBean;
import com.shenma.yueba.util.FontManager;

public class BrandAdapter extends BaseAdapterWithUtil {
	private List<BrandListBean> mList;
	public BrandAdapter(Context ctx,List<BrandListBean> mList) {
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
			convertView = View.inflate(ctx, R.layout.brand_list_item, null);
			holder.iv_brand = (ImageView) convertView.findViewById(R.id.iv_brand);
			holder.tv_msg_count = (TextView) convertView.findViewById(R.id.tv_msg_count);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		return convertView;
	}
	
	
	class Holder{
		ImageView iv_brand;
		TextView tv_msg_count;
		TextView tv_product_name;
		TextView tv_msg;
		TextView tv_time;
		
	}

}
