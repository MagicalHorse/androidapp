package com.shenma.yueba.yangjia.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.BaseAdapterWithUtil;
import com.shenma.yueba.baijia.modle.MsgBean;
import com.shenma.yueba.baijia.modle.RecommendedCircleBean;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.PhotoUtils;
import com.shenma.yueba.view.RoundImageView;
import com.shenma.yueba.yangjia.modle.CurrentRankingBean;

public class CurrentRankingAdapter extends BaseAdapterWithUtil {
	private List<CurrentRankingBean> mList;
	public CurrentRankingAdapter(Context ctx,List<CurrentRankingBean> mList) {
		super(ctx);
		this.mList = mList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 10;
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
			convertView = View.inflate(ctx, R.layout.ranking_item_layout, null);
			holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
			holder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_classification = (TextView) convertView.findViewById(R.id.tv_classification);
			holder.tv_sales_title = (TextView) convertView.findViewById(R.id.tv_sales_title);
			holder.tv_sales_count = (TextView) convertView.findViewById(R.id.tv_sales_count);
//			FontManager.changeFonts(ctx, holder.tv_name,holder.tv_number,holder.tv_classification,
//					holder.tv_sales_title,holder.tv_sales_count);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		return convertView;
	}
	
	
	class Holder{
		ImageView iv_icon;
		TextView tv_name;
		TextView tv_number;
		TextView tv_classification;
		TextView tv_sales_title;
		TextView tv_sales_count;
	}

}
