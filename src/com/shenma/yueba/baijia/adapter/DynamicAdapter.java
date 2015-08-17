package com.shenma.yueba.baijia.adapter;

import java.util.List;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.UserDynamicInfo;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.RoundImageView;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DynamicAdapter extends BaseAdapterWithUtil {
	private List<UserDynamicInfo> mList;
	public DynamicAdapter(Context ctx,List<UserDynamicInfo> mList) {
		super(ctx);
		this.mList = mList;
	}

	@Override
	public int getCount() {
		
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if(convertView == null){
			holder = new Holder();
			convertView = View.inflate(ctx, R.layout.dynamic_item, null);
			holder.iv_dynamic_icon = (RoundImageView) convertView.findViewById(R.id.iv_dynamic_icon);
			holder.iv_dynamic_icon.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(v.getTag()!=null && v.getTag() instanceof UserDynamicInfo)
					{
						UserDynamicInfo info=(UserDynamicInfo)v.getTag();
						ToolsUtil.forwardShopMainActivity(ctx, info.getUserId());
					}
				}
			});
			holder.iv_dynamic_img = (ImageView) convertView.findViewById(R.id.iv_dynamic_img);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_dynamic_type = (TextView) convertView.findViewById(R.id.tv_dynamic_type);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			FontManager.changeFonts(ctx, holder.tv_name,holder.tv_time,holder.tv_dynamic_type);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		setValue(holder,position);
		return convertView;
	}
	
	
	
	class Holder{
		RoundImageView iv_dynamic_icon;//头像
		ImageView iv_dynamic_img;//图片
		TextView tv_name;//名字
		TextView tv_dynamic_type;//内容
		TextView tv_time;//时间
	}

	
	void setValue(Holder holder,int position)
	{
		UserDynamicInfo infobean=mList.get(position);
		holder.tv_name.setText(ToolsUtil.nullToString(infobean.getUserName()));
		holder.tv_dynamic_type.setText(ToolsUtil.nullToString(infobean.getContext()));
		holder.tv_time.setText(ToolsUtil.nullToString(infobean.getCreateTime()));
		initBitmap(ToolsUtil.nullToString(infobean.getLogo()), holder.iv_dynamic_icon);
		initBitmap(ToolsUtil.nullToString(infobean.getDataLogo()), holder.iv_dynamic_img);
		holder.iv_dynamic_icon.setTag(infobean);
	}
	void initBitmap(final String url, final ImageView iv)
	{
		MyApplication.getInstance().getBitmapUtil().display(iv, url);
	}
}
