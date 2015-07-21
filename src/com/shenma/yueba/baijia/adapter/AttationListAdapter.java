package com.shenma.yueba.baijia.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.AttationListBean;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.RoundImageView;
import com.shenma.yueba.yangjia.modle.AttationAndFansItemBean;

public class AttationListAdapter extends BaseAdapterWithUtil {
	private List<AttationAndFansItemBean> mList;
	int status=0;//关注
	public AttationListAdapter(Context ctx,List<AttationAndFansItemBean> mList,int status) {
		super(ctx);
		this.mList = mList;
		this.status=status;
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

	@SuppressWarnings("null")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if(convertView == null){
			holder = new Holder();
			convertView = View.inflate(ctx, R.layout.fans_list_item, null);
			holder.riv_head = (RoundImageView) convertView.findViewById(R.id.riv_head);
			holder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_attention_count = (TextView) convertView.findViewById(R.id.tv_attention_count);
			holder.tv_fans_count = (TextView) convertView.findViewById(R.id.tv_fans_count);
			holder.tv_atttention = (TextView) convertView.findViewById(R.id.tv_atttention);
			holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
			if(status==0)
			{
				holder.tv_fans_count.setVisibility(View.GONE);
				holder.tv_atttention.setVisibility(View.VISIBLE);
			}else if(status==1)
			{
				holder.tv_fans_count.setVisibility(View.VISIBLE);
				holder.tv_atttention.setVisibility(View.GONE);
			}else
			{
				holder.tv_fans_count.setVisibility(View.GONE);
				holder.tv_atttention.setVisibility(View.GONE);
			}
			holder.tv_atttention.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					//sssssssss
				}
			});
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		initValue(position,holder);
		return convertView;
	}
	
	
	class Holder{
		RoundImageView riv_head;
		TextView tv_type;
		TextView tv_name;
		TextView tv_attention_count;
		TextView tv_fans_count;
		TextView tv_atttention;
		TextView tv_address;
		
	}

	void initValue(int position,Holder holder)
	{
		AttationAndFansItemBean bean=mList.get(position);
		MyApplication.getInstance().getImageLoader().displayImage(ToolsUtil.nullToString(bean.getUserLogo()), holder.riv_head, MyApplication.getInstance().getDisplayImageOptions());
		holder.tv_name.setText(ToolsUtil.nullToString(bean.getUserName()));
		if(status==0)//是关注
		{
			holder.tv_atttention.setText(ToolsUtil.nullToString(bean.getFavoiteCount()));
			
			if(bean.isFavorite())
			{
				holder.tv_attention_count.setText("已关注");
			}else
			{
				holder.tv_attention_count.setText("关注");
			}
		}else if(status==1)//粉丝
		{
			holder.tv_fans_count.setText(ToolsUtil.nullToString(bean.getFavoiteCount()));
			
			if(bean.isFavorite())
			{
				holder.tv_attention_count.setText("已关注");
			}else
			{
				holder.tv_attention_count.setText("已关注");
			}
		}
		/*holder.tv_attention_count
		holder.tv_fans_count
		
		bean.isChecked()*/
		
		
	}


}
