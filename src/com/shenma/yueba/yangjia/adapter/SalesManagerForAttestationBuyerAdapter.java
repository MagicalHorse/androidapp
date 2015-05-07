package com.shenma.yueba.yangjia.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.shenma.yueba.yangjia.modle.SalesManagerForAttestationBuyerListBean;

public class SalesManagerForAttestationBuyerAdapter extends BaseAdapterWithUtil {
	private List<SalesManagerForAttestationBuyerListBean> mList;
	public SalesManagerForAttestationBuyerAdapter(Context ctx,List<SalesManagerForAttestationBuyerListBean> mList) {
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
			convertView = View.inflate(ctx, R.layout.sales_manager_item_for_attestation_buyer, null);
			holder.ll_more_product_infomation = (LinearLayout) convertView.findViewById(R.id.ll_more_product_infomation);
			holder.ll_more_show = (LinearLayout) convertView.findViewById(R.id.ll_more_show);
			holder.iv_product = (ImageView) convertView.findViewById(R.id.iv_product);
			holder.tv_order_number = (TextView) convertView.findViewById(R.id.tv_order_number);
			holder.tv_waiting_for_send = (TextView) convertView.findViewById(R.id.tv_waiting_for_send);
			holder.tv_more_show = (TextView) convertView.findViewById(R.id.tv_more_show);
			holder.tv_product_count = (TextView) convertView.findViewById(R.id.tv_product_count);
			holder.tv_money_payed = (TextView) convertView.findViewById(R.id.tv_money_payed);
			holder.tv_product_name = (TextView) convertView.findViewById(R.id.tv_product_name);
			holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
			holder.tv_description = (TextView) convertView.findViewById(R.id.tv_description);
			holder.tv_same_product_count = (TextView) convertView.findViewById(R.id.tv_same_product_count);
			holder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
			FontManager.changeFonts(ctx, holder.tv_order_number,holder.tv_waiting_for_send,holder.tv_more_show,
					holder.tv_product_count,holder.tv_money_payed,holder.tv_product_name,holder.tv_price,holder.tv_description,
					holder.tv_same_product_count,holder.tv_size);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		return convertView;
	}
	
	
	class Holder{
		LinearLayout ll_more_product_infomation;
		TextView tv_order_number;
		TextView tv_waiting_for_send;
		LinearLayout ll_more_show;
		TextView tv_more_show;
		TextView tv_product_count;
		TextView tv_money_payed;
		ImageView iv_product;
		TextView tv_product_name;
		TextView tv_price;
		TextView tv_description;
		TextView tv_same_product_count;
		TextView tv_size;
	}

}
