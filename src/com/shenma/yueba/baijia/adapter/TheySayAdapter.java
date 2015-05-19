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
import com.shenma.yueba.baijia.modle.TheySayBean;
import com.shenma.yueba.util.FontManager;

public class TheySayAdapter extends BaseAdapterWithUtil {
	private List<TheySayBean> mList;
	public TheySayAdapter(Context ctx,List<TheySayBean> mList) {
		super(ctx);
		this.mList = mList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
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
			convertView = View.inflate(ctx, R.layout.they_say_item, null);
			holder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_pic);
			holder.tv_introduce = (TextView) convertView.findViewById(R.id.tv_introduce);
			holder.tv_zan_count = (TextView) convertView.findViewById(R.id.tv_zan_count);
			holder.tv_chat = (TextView) convertView.findViewById(R.id.tv_chat);
			holder.tv_share = (TextView) convertView.findViewById(R.id.tv_share);
			FontManager.changeFonts(ctx,holder.tv_introduce,holder.tv_zan_count,holder.tv_chat,holder.tv_share);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		return convertView;
	}
	
	
	class Holder{
		ImageView iv_pic;
		TextView tv_zan_count;
		TextView tv_introduce;
		TextView tv_chat;
		TextView tv_share;
	}

}
