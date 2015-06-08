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
import android.widget.Toast;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.BaseRequest;
import com.shenma.yueba.baijia.modle.GridVIewItemBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.view.RoundImageView;
import com.shenma.yueba.yangjia.activity.CircleInvitectivity;
import com.shenma.yueba.yangjia.activity.ModifyCircleNameActivity;
import com.shenma.yueba.yangjia.modle.Users;

public class MyCircleInfoAdapter extends BaseAdapterWithUtil{
	private Context ctx;
	private boolean showDelete;
	private List<Users> mList = new ArrayList<Users>();
	private String circleId;
	public MyCircleInfoAdapter(Context ctx,List<Users> mList) {
		super(ctx);
		this.mList = mList;
		this.ctx = ctx;
	}

	
	
	
	
	public String getCircleId() {
		return circleId;
	}





	public void setCircleId(String circleId) {
		this.circleId = circleId;
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
				if(position == 0){
					holder.iv_delete.setVisibility(View.GONE);
				}else{
					holder.iv_delete.setVisibility(View.VISIBLE);
				}
			}else{
				holder.iv_delete.setVisibility(View.GONE);
			}
			holder.tv_text.setText(mList.get(position).getNickName());
			MyApplication.getInstance().getImageLoader().displayImage(mList.get(position).getLogo(), holder.riv_head);
		}
		holder.iv_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//删除成员
				renameCircleName(circleId, mList.get(position).getUserId(), ctx, true);
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

	
	
	public void renameCircleName(String circleId, final String userId, final Context ctx,
			boolean showDialog) {
		HttpControl httpControl = new HttpControl();
		httpControl.removeCircleMember(circleId, userId, showDialog, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {

				BaseRequest result = (BaseRequest) obj;
				if (200 == result.getStatusCode()) {// 修改成功
					Toast.makeText(ctx, "删除成功", 1000).show();
				}
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				Toast.makeText(ctx, msg, 1000).show();
				
			}
		}, ctx);
}
}
