package com.shenma.yueba.baijia.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.GridVIewItemBean;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.view.RoundImageView;

public class MyCircleInfoAdapter extends BaseAdapterWithUtil{
	private Context ctx;
	private List<GridVIewItemBean> mList = new ArrayList<GridVIewItemBean>();
	public MyCircleInfoAdapter(Context ctx,List<GridVIewItemBean> mList) {
		super(ctx);
		this.mList = mList;
		this.ctx = ctx;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if(convertView == null){
			holder = new Holder();
			convertView = View.inflate(ctx, R.layout.grid_item, null);
			holder.riv_head = (RoundImageView) convertView.findViewById(R.id.riv_head);
			holder.tv_text = (TextView) convertView.findViewById(R.id.tv_text);
			FontManager.changeFonts(ctx, holder.tv_text);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		if(position == 0){
			holder.riv_head.setBackgroundResource(R.drawable.ic_launcher);
			holder.tv_text.setText("邀请好友");
			return convertView;
		}else if(position == 1){
			holder.riv_head.setBackgroundResource(R.drawable.ic_launcher);
			holder.tv_text.setText("删除成员");
			return convertView;
		}else{
			holder.tv_text.setText(mList.get(position).getName());
			MyApplication.getInstance().getImageLoader().displayImage(mList.get(position).getHead(), holder.riv_head);
			return convertView;
		}
		
	}
	
	class Holder{
		RoundImageView riv_head;
		TextView tv_text;
	}

}
