package com.shenma.yueba.baijia.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.MyBuyerBean;
import com.shenma.yueba.baijia.modle.MyCircleBean;
import com.shenma.yueba.util.FontManager;

public class MyBuyerAdapter extends BaseAdapterWithUtil {
	private List<MyBuyerBean> mList;
	public MyBuyerAdapter(Context ctx,List<MyBuyerBean> mList) {
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
			convertView = View.inflate(ctx, R.layout.my_buyer_item, null);
			holder.iv_head = (ImageView) convertView.findViewById(R.id.iv_head);
			holder.iv_big_image = (ImageView) convertView.findViewById(R.id.iv_big_image);
			holder.tv_buyer_name = (TextView) convertView.findViewById(R.id.tv_buyer_name);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
			holder.tv_introduce = (TextView) convertView.findViewById(R.id.tv_introduce);
			holder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
			holder.tv_chat = (TextView) convertView.findViewById(R.id.tv_chat);
			holder.tv_share = (TextView) convertView.findViewById(R.id.tv_share);
			holder.tv_zan = (TextView) convertView.findViewById(R.id.tv_zan);
			MyApplication.getInstance().getImageLoader().displayImage("http://img3.redocn.com/20091221/20091217_fa2a743db1f556f82b9asJ320coGmYFf.jpg", holder.iv_head, MyApplication.getInstance().getRoundDisplayImageOptions());
			FontManager.changeFonts(ctx, holder.tv_buyer_name,holder.tv_time,holder.tv_address,
					holder.tv_money,holder.tv_introduce,holder.tv_chat,holder.tv_share,holder.tv_zan);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		return convertView;
	}
	
	
	class Holder{
		ImageView iv_head;
		ImageView iv_big_image;
		TextView tv_msg_count;
		TextView tv_product_name;
		TextView tv_msg;
		TextView tv_buyer_name;
		TextView tv_time;
		TextView tv_address;
		TextView tv_introduce;
		TextView tv_money;
		TextView tv_chat;
		TextView tv_share;
		TextView tv_zan;
	}

}
