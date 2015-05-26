package com.shenma.yueba.baijia.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.GridVIewItemBean;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.view.RoundImageView;
import com.shenma.yueba.yangjia.activity.CircleInvitectivity;

public class MyCircleInfoAdapter extends BaseAdapterWithUtil{
	private Context ctx;
	private boolean showDelete;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		final Holder holder;
		if(convertView == null){
			holder = new Holder();
			convertView = View.inflate(ctx, R.layout.grid_item, null);
			holder.riv_head = (RoundImageView) convertView.findViewById(R.id.riv_head);
			holder.tv_text = (TextView) convertView.findViewById(R.id.tv_text);
			holder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);
			FontManager.changeFonts(ctx, holder.tv_text);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		if(position == mList.size()-2){
			holder.riv_head.setBackgroundResource(R.drawable.plus);
			holder.tv_text.setText("邀请好友");
			
		}else if(position == mList.size()-1){
			holder.riv_head.setBackgroundResource(R.drawable.reduce);
			holder.tv_text.setText("删除成员");
		}else{
			if(showDelete){
				holder.iv_delete.setVisibility(View.VISIBLE);
			}else{
				holder.iv_delete.setVisibility(View.GONE);
			}
			holder.tv_text.setText(mList.get(position).getName());
			MyApplication.getInstance().getImageLoader().displayImage(mList.get(position).getHead(), holder.riv_head);
		}
		holder.iv_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//删除成员
				
			}
		});
		holder.riv_head.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				//踢出圈子
				if(position == mList.size()-1){
					if(showDelete == true){
						showDelete = false;
						notifyDataSetChanged();
					}else{
						showDelete = true;
						notifyDataSetChanged();
					}
				}
				if(position == mList.size()-2){
					//邀请加入圈子
					Intent intent = new Intent(ctx,CircleInvitectivity.class);
					ctx.startActivity(intent);
				}
			}
		});
		return convertView;
	}
	
	class Holder{
		RoundImageView riv_head;
		TextView tv_text;
		ImageView iv_delete;
	}

}
