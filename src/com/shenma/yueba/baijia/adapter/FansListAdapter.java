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
import com.shenma.yueba.baijia.modle.FansListBean;
import com.shenma.yueba.baijia.modle.MyCircleBean;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.view.RoundImageView;

public class FansListAdapter extends BaseAdapterWithUtil {
	private List<FansListBean> mList;
	public FansListAdapter(Context ctx,List<FansListBean> mList) {
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
			convertView = View.inflate(ctx, R.layout.fans_list_item, null);
			holder.riv_head = (RoundImageView) convertView.findViewById(R.id.riv_head);
			holder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_attention_count = (TextView) convertView.findViewById(R.id.tv_attention_count);
			holder.tv_fans_count = (TextView) convertView.findViewById(R.id.tv_fans_count);
			holder.tv_atttention = (TextView) convertView.findViewById(R.id.tv_atttention);
			holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		return convertView;
	}
	
	
	class Holder{
		RoundImageView riv_head;
		TextView tv_type;
		TextView tv_name;
		TextView tv_attention_count;
		TextView tv_fans_count;
		TextView tv_atttention;
		TextView tv_address;
		
	}

}
