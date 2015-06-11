package com.shenma.yueba.yangjia.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.adapter.BaseAdapterWithUtil;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.yangjia.modle.HuoKuanItem;
import com.shenma.yueba.yangjia.modle.OrderItem;

public class HuoKuanIncomeAndOutGoingAdapter extends BaseAdapterWithUtil {
	private List<HuoKuanItem> mList;
	private int tag;//标记不同的订单状态
	public HuoKuanIncomeAndOutGoingAdapter(Context ctx,List<HuoKuanItem> mList,int tag) {
		super(ctx);
		this.mList = mList;
		this.tag = tag;
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
			convertView = View.inflate(ctx, R.layout.huokuan_income_and_outgoing_item, null);
			holder.tv_earning_money_title = (TextView) convertView.findViewById(R.id.tv_earning_money_title);
			holder.tv_money_number = (TextView) convertView.findViewById(R.id.tv_money_number);
			holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
			holder.tv_order_number_title = (TextView) convertView.findViewById(R.id.tv_order_number_title);
			holder.tv_order_muber = (TextView) convertView.findViewById(R.id.tv_order_muber);
			holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
			FontManager.changeFonts(ctx, holder.tv_earning_money_title,holder.tv_money_number,
					holder.tv_status,holder.tv_order_number_title,holder.tv_order_muber,holder.tv_date);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		holder.tv_money_number.setText(ToolsUtil.nullToString(mList.get(position).getAmount()));
		holder.tv_status.setText(ToolsUtil.nullToString(mList.get(position).getStatusName()));
		holder.tv_order_muber.setText(ToolsUtil.nullToString(mList.get(position).getOrderNo()));
		holder.tv_date.setText(ToolsUtil.nullToString(mList.get(position).getCreateDate()));
		return convertView;
	}
	
	
	class Holder{
		TextView tv_earning_money_title;//
		TextView tv_order_status;//订单状态
		TextView tv_date;//商品描述
		TextView tv_order_muber;//商品个数
		TextView tv_order_number_title;//货号
		TextView tv_money_number;//规格
		TextView tv_status;//规格
	}

}
