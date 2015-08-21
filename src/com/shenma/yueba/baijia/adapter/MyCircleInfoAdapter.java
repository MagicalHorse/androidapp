package com.shenma.yueba.baijia.adapter;

import java.util.ArrayList;
import java.util.List;

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

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyCircleInfoAdapter extends BaseAdapterWithUtil {
	private Context ctx;
	private boolean showDelete=false;
	private List<Users> mList = new ArrayList<Users>();
	private String circleId;

	public MyCircleInfoAdapter(Context ctx, List<Users> mList, String cirlceId,
			boolean IsOwer) {
		super(ctx);
		this.circleId = cirlceId;
		this.mList = mList;
		this.ctx = ctx;
	}

	public int getCount() {
		
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		
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
			
			holder.iv_delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(v.getTag()!=null && v.getTag() instanceof Users)
					{
						Users u=(Users)v.getTag();
						// 删除成员
						renameCircleName(circleId,u.getUserId(),ctx, true);
					}
				}
			});
			
			
			holder.riv_head.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					if(v.getTag()!=null && v.getTag() instanceof Users)
					{
						Users users=(Users)v.getTag();
						switch(users.getUser_Type())
						{
						case data:
							ToolsUtil.forwardShopMainActivity(ctx,Integer.parseInt(users.getUserId()));
							break;
						case jia:
							// 邀请加入圈子
							Intent intent = new Intent(ctx, CircleInvitectivity.class);
							intent.putExtra("circleId", circleId);
							((CircleInfoActivity) ctx).startActivityForResult(intent,
									Constants.REQUESTCODE);
							break;
						case jian:
							if(showDelete)
							{
								showDelete=false;
								for(int i=0;i<mList.size();i++)
								{
									mList.get(i).setShowDelete(false);
								}
								notifyDataSetChanged();
								
							}else
							{
								showDelete=true;
								for(int i=0;i<mList.size();i++)
								{
									mList.get(i).setShowDelete(true);
								}
								notifyDataSetChanged();
							}
							break;
						
						}
					}
					
				}
			});
			
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		Users users=mList.get(position);
		holder.riv_head.setTag(users);
		holder.iv_delete.setTag(users);
		holder.tv_text.setText(users.getNickName());
		switch(users.getUser_Type())
		{
		  case data:
			  initBitmap(ToolsUtil.nullToString(users.getLogo()), holder.riv_head);
			  if(users.isShowDelete())
			  {
				  holder.iv_delete.setVisibility(View.VISIBLE);
			  }
			  break;
		  case jia:
			  holder.riv_head.setImageResource(R.drawable.plus);
			  break;
		  case jian:
			  holder.riv_head.setImageResource(R.drawable.reduce);
			  break;
		}
		
		if(users.isShowDelete())
		{
			switch(users.getUser_Type())
			{
			  case data:
				  holder.iv_delete.setVisibility(View.VISIBLE);
				  break;
			  case jia:
				  holder.iv_delete.setVisibility(View.GONE);
				  break;
			  case jian:
				  holder.iv_delete.setVisibility(View.GONE);
				  break;
			}
		}else
		{
			holder.iv_delete.setVisibility(View.GONE);
		}
		
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
