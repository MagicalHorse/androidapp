package com.shenma.yueba.yangjia.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.adapter.BaseAdapterWithUtil;
import com.shenma.yueba.baijia.modle.MyAttentionAndFansForSocialBean;
import com.shenma.yueba.view.RoundImageView;

public class MyAttentionAndFansForSocialAdapter extends BaseAdapterWithUtil {
	private List<MyAttentionAndFansForSocialBean> mList;

	public MyAttentionAndFansForSocialAdapter(Context ctx, List<MyAttentionAndFansForSocialBean> mList) {
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
		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(ctx, R.layout.my_attention_and_fans_for_social_item,
					null);
			holder.riv_head = (RoundImageView) convertView
					.findViewById(R.id.riv_head);
			holder.tv_name = (TextView) convertView
					.findViewById(R.id.tv_name);
			holder.tv_atttention_count = (TextView) convertView
					.findViewById(R.id.tv_atttention_count);
			holder.tv_fans_count = (TextView) convertView
					.findViewById(R.id.tv_fans_count);
			holder.tv_attention = (TextView) convertView
					.findViewById(R.id.tv_attention);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		return convertView;
	}

	class Holder {
		RoundImageView riv_head;
		TextView tv_name;
		TextView tv_atttention_count;
		TextView tv_fans_count;
		TextView tv_attention;
	}

}
