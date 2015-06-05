package com.shenma.yueba.baijia.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.BrandCityWideInfo;
import com.shenma.yueba.baijia.modle.BrandListBean;
import com.shenma.yueba.baijia.modle.MyCircleBean;
import com.shenma.yueba.baijia.modle.SameCityBean;
import com.shenma.yueba.util.FontManager;

public class SameCityAdapter extends BaseAdapterWithUtil {
	private List<BrandCityWideInfo> items;
	public SameCityAdapter(Context ctx,List<BrandCityWideInfo> items) {
		super(ctx);
		this.items = items;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
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
			convertView = View.inflate(ctx, R.layout.same_city_list_item, null);
			holder.iv_head = (ImageView) convertView.findViewById(R.id.iv_head);
			holder.nick_name = (TextView) convertView.findViewById(R.id.tv_nick_name);
			holder.tv_belong = (TextView) convertView.findViewById(R.id.tv_belong);
			holder.tv_attention = (TextView) convertView.findViewById(R.id.tv_attention);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		return convertView;
	}
	
	
	class Holder{
		ImageView iv_head;
		TextView nick_name;
		TextView tv_belong;
		TextView tv_attention;
		
	}

}
