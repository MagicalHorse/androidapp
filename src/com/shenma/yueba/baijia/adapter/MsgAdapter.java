package com.shenma.yueba.baijia.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.MsgBean;
import com.shenma.yueba.baijia.modle.RecommendedCircleBean;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.PhotoUtils;
import com.shenma.yueba.view.RoundImageView;

public class MsgAdapter extends BaseAdapterWithUtil {
	private List<MsgBean> mList;
	public MsgAdapter(Context ctx,List<MsgBean> mList) {
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
			convertView = View.inflate(ctx, R.layout.msg_item, null);
			holder.iv_msg = (ImageView) convertView.findViewById(R.id.iv_msg);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.tv_msg = (TextView) convertView.findViewById(R.id.tv_msg);
			MyApplication.getInstance().getImageLoader().displayImage("http://img3.redocn.com/20091221/20091217_fa2a743db1f556f82b9asJ320coGmYFf.jpg", holder.iv_msg, MyApplication.getInstance().getRoundDisplayImageOptions());
			//FontManager.changeFonts(ctx, holder.tv_name,holder.tv_time,holder.tv_msg);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		return convertView;
	}
	
	
	class Holder{
		ImageView iv_msg;
		TextView tv_name;
		TextView tv_time;
		TextView tv_msg;
	}

}
