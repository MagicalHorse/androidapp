package com.shenma.yueba.yangjia.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.BaseAdapterWithUtil;
import com.shenma.yueba.baijia.modle.MyCircleBean;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.PhotoUtils;

public class MyCircleForSocialAdapter extends BaseAdapterWithUtil {
	private List<MyCircleBean> mList;

	public MyCircleForSocialAdapter(Context ctx, List<MyCircleBean> mList) {
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
			convertView = View.inflate(ctx, R.layout.my_circle_for_social_item,
					null);
			holder.iv_circle_head = (ImageView) convertView
					.findViewById(R.id.iv_circle_head);
			holder.tv_circle_name = (TextView) convertView
					.findViewById(R.id.tv_circle_name);
			holder.tv_attention_count = (TextView) convertView
					.findViewById(R.id.tv_attention_count);
			String url = "http://b.hiphotos.baidu.com/image/h%3D360/sign=ff31be24ce134954611eee62664f92dd/ac6eddc451da81cb983585685766d01608243147.jpg";
			MyApplication.getInstance().getImageLoader().displayImage(url, holder.iv_circle_head, MyApplication.getInstance().getRoundDisplayImageOptions());
			FontManager.changeFonts(ctx, holder.tv_circle_name,
					holder.tv_attention_count);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		return convertView;
	}

	class Holder {
		ImageView iv_circle_head;
		TextView tv_circle_name;
		TextView tv_attention_count;
	}

}
