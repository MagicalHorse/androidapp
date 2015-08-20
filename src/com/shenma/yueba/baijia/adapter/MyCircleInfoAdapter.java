package com.shenma.yueba.baijia.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.CircleInfoActivity;
import com.shenma.yueba.baijia.modle.BaseRequest;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.RoundImageView;
import com.shenma.yueba.yangjia.activity.CircleInvitectivity;
import com.shenma.yueba.yangjia.modle.Users;

public class MyCircleInfoAdapter extends BaseAdapterWithUtil {
	private Context ctx;
	private boolean showDelete;
	private List<Users> mList = new ArrayList<Users>();
	private String circleId;
	boolean IsOwer = false;// 是否为创建者

	public MyCircleInfoAdapter(Context ctx, List<Users> mList, String cirlceId,
			boolean IsOwer) {
		super(ctx);
		this.circleId = cirlceId;
		this.mList = mList;
		this.ctx = ctx;
		this.IsOwer = IsOwer;
	}

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
		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(ctx, R.layout.grid_item, null);
			holder.riv_head = (RoundImageView) convertView
					.findViewById(R.id.riv_head);
			holder.tv_text = (TextView) convertView.findViewById(R.id.tv_text);
			holder.iv_delete = (ImageView) convertView
					.findViewById(R.id.iv_delete);
			FontManager.changeFonts(ctx, holder.tv_text);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.riv_head.setTag(mList.get(position));
		if (IsOwer) {
			if (position == mList.size() - 2) {
				holder.riv_head.setTag(null);
				holder.riv_head.setBackgroundResource(R.drawable.plus);
				holder.tv_text.setText("邀请好友");

			} else if (position == mList.size() - 1) {
				holder.riv_head.setTag(null);
				holder.riv_head.setBackgroundResource(R.drawable.reduce);
				holder.tv_text.setText("删除成员");
			}

		}

		//如果显示 删除图标
		if (showDelete) {
			if (position == 0) {
			} else {
				holder.iv_delete.setVisibility(View.VISIBLE);
			}
			if(IsOwer)
			{
				if (position == mList.size() - 2) {
					holder.iv_delete.setVisibility(View.GONE);

				} else if (position == mList.size() - 1) {
					holder.iv_delete.setVisibility(View.GONE);
				}
			}
			
		} else {
			holder.iv_delete.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(mList.get(position).getLogo())) {
			initBitmap(mList.get(position).getLogo(), holder.riv_head);
		}
		holder.tv_text.setText(mList.get(position).getNickName());

		holder.iv_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//如果不是创建者 则不响应
				if(!IsOwer)
				{
					return;
				}
				// 删除成员
				renameCircleName(circleId, mList.get(position).getUserId(),
						ctx, true);
			}
		});
		holder.riv_head.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if(v.getTag()!=null && v.getTag() instanceof Users)
				{
					Users users=(Users)v.getTag();
					ToolsUtil.forwardShopMainActivity(ctx,Integer.parseInt(users.getUserId()));
				}
				if(!IsOwer)
				{
					return;
				}
				// 踢出圈子
				if (position == mList.size() - 1) {
					if (showDelete == true) {
						showDelete = false;
						notifyDataSetChanged();
					} else {
						showDelete = true;
						notifyDataSetChanged();
					}
				}

				if (position == mList.size() - 2) {
					// 邀请加入圈子
					Intent intent = new Intent(ctx, CircleInvitectivity.class);
					intent.putExtra("circleId", circleId);
					((CircleInfoActivity) ctx).startActivityForResult(intent,
							Constants.REQUESTCODE);
					
					
					
					
				}
			}
		});
		return convertView;
	}

	class Holder {
		RoundImageView riv_head;
		TextView tv_text;
		ImageView iv_delete;
	}

	public void renameCircleName(String circleId, final String userId,
			final Context ctx, boolean showDialog) {
		HttpControl httpControl = new HttpControl();
		httpControl.removeCircleMember(circleId, userId, showDialog,
				new HttpCallBackInterface() {

					@Override
					public void http_Success(Object obj) {

						BaseRequest result = (BaseRequest) obj;
						if (200 == result.getStatusCode()) {// 修改成功
							Toast.makeText(ctx, "删除成功", 1000).show();
							/*
							 * for (int i = 0; i < mList.size(); i++) {
							 * if(userId.equals(mList.get(i).getUserId())){
							 * mList.remove(i); notifyDataSetChanged(); } }
							 */
							((CircleInfoActivity) ctx).getCircleDetail(
									MyCircleInfoAdapter.this.circleId, ctx,
									true);
						}
					}

					@Override
					public void http_Fails(int error, String msg) {
						Toast.makeText(ctx, msg, 1000).show();

					}
				}, ctx);
	}
	
	void initBitmap(final String url, final ImageView iv)
	{
		MyApplication.getInstance().getBitmapUtil().display(iv, url);
	}
}
