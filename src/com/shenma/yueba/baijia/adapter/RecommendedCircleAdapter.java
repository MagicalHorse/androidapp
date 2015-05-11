package com.shenma.yueba.baijia.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.RecommendedCircleBean;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.view.RoundImageView;

public class RecommendedCircleAdapter extends BaseAdapterWithUtil {
	private List<RecommendedCircleBean> mList;
	public RecommendedCircleAdapter(Context ctx,List<RecommendedCircleBean> mList) {
		super(ctx);
		this.mList = mList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 50;
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
			convertView = View.inflate(ctx, R.layout.circle_item, null);
			holder.riv = (RoundImageView) convertView.findViewById(R.id.riv);
			holder.tv_circle_name = (TextView) convertView.findViewById(R.id.tv_circle_name);
			holder.tv_count = (TextView) convertView.findViewById(R.id.tv_count);
			holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
			FontManager.changeFonts(ctx, holder.riv,holder.tv_address,holder.tv_circle_name,holder.tv_count);
			MyApplication.getInstance().getImageLoader().displayImage("http://img3.redocn.com/20091221/20091217_fa2a743db1f556f82b9asJ320coGmYFf.jpg", holder.riv);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		return convertView;
	}
	
	
	class Holder{
		RoundImageView riv;
		TextView tv_circle_name;
		TextView tv_count;
		TextView tv_address;
	}

}
