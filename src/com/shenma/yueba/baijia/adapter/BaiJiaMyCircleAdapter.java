package com.shenma.yueba.baijia.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.MyCircleInfo;
import com.shenma.yueba.util.ToolsUtil;

public class BaiJiaMyCircleAdapter extends BaseAdapterWithUtil {
	BitmapUtils bu;
	private List<MyCircleInfo> items;

	public BaiJiaMyCircleAdapter(Context ctx, List<MyCircleInfo> items) {
		super(ctx);
		this.items = items;
		bu=new BitmapUtils(ctx);
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
		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(ctx, R.layout.my_circle_item, null);
			holder.iv_head = (ImageView) convertView.findViewById(R.id.iv_head);
			holder.tv_msg_count = (TextView) convertView
					.findViewById(R.id.tv_msg_count);
			holder.tv_product_name = (TextView) convertView
					.findViewById(R.id.tv_product_name);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.tv_msg = (TextView) convertView.findViewById(R.id.tv_msg);
			convertView.setTag(holder);
			ToolsUtil.setFontStyle(ctx, convertView, R.id.tv_product_name,R.id.tv_msg,R.id.tv_time,R.id.tv_msg_count);
		} else {
			holder = (Holder) convertView.getTag();
		}
		MyCircleInfo myCircleInfo = items.get(position);
		MyApplication.getInstance().getImageLoader().displayImage(ToolsUtil.nullToString(myCircleInfo.getLogo()), holder.iv_head,  MyApplication.getInstance().getDisplayImageOptions());
		
		holder.tv_product_name.setText(ToolsUtil.nullToString(myCircleInfo.getName()));
		holder.tv_msg_count.setText(Integer.toString(myCircleInfo.getMemberCount()));
		holder.tv_time.setText("");
		holder.tv_msg.setText("");
		
		return convertView;
	}

	class Holder {
		ImageView iv_head;
		TextView tv_msg_count;
		TextView tv_product_name;
		TextView tv_msg;
		TextView tv_time;

	}

}
