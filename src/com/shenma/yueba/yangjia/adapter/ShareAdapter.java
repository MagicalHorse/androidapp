package com.shenma.yueba.yangjia.adapter;



import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenma.yueba.R;

public class ShareAdapter extends BaseAdapter {
	private Context ctx;
	private List<String> list;
	public ShareAdapter(Context ctx,List<String> list){
		this.ctx = ctx;
		this.list = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(ctx, R.layout.share_item, null);
		ImageView bt_logo = (ImageView) view.findViewById(R.id.bt_logo);
		TextView tv_share = (TextView) view.findViewById(R.id.tv_share);
		if("新浪微博".equals(list.get(position))){
			bt_logo.setBackgroundResource((R.drawable.logo_sinaweibo));
			tv_share.setText("新浪微博");
		}else if("腾讯微博".equals(list.get(position))){
			bt_logo.setBackgroundResource((R.drawable.logo_tencentweibo));
			tv_share.setText("腾讯微博");
		}else if("微信好友".equals(list.get(position))){
			bt_logo.setBackgroundResource((R.drawable.logo_wechat));
			tv_share.setText("微信好友");
		}else if("微信朋友圈".equals(list.get(position))){
			bt_logo.setBackgroundResource((R.drawable.logo_wechatmoments));
			tv_share.setText("微信朋友圈");
		}
		return view;
	}

}
