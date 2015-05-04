package com.shenma.yueba.baijia.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.modle.DynamicBean;
import com.shenma.yueba.util.FontManager;

public class DynamicAdapter extends BaseAdapterWithUtil {
	private List<DynamicBean> mList;
	public DynamicAdapter(Context ctx,List<DynamicBean> mList) {
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
			convertView = View.inflate(ctx, R.layout.dynamic_item, null);
			holder.iv_dynamic_icon = (ImageView) convertView.findViewById(R.id.iv_dynamic_icon);
			holder.iv_dynamic_img = (ImageView) convertView.findViewById(R.id.iv_dynamic_img);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_dynamic_type = (TextView) convertView.findViewById(R.id.tv_dynamic_type);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			FontManager.changeFonts(ctx, holder.tv_name,holder.tv_time,holder.tv_dynamic_type);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		return convertView;
	}
	
	
	class Holder{
		ImageView iv_dynamic_icon;
		ImageView iv_dynamic_img;
		TextView tv_name;
		TextView tv_dynamic_type;
		TextView tv_time;
	}

}
