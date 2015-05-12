package com.shenma.yueba.baijia.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.modle.ProductManagerForOnLineBean;
import com.shenma.yueba.util.FontManager;

public class ProductManagerFragmentForOnLineAdapter extends BaseAdapterWithUtil {
	private List<ProductManagerForOnLineBean> mList;
	private int flag;
	public ProductManagerFragmentForOnLineAdapter(Context ctx,
			List<ProductManagerForOnLineBean> mList,int flag) {
		super(ctx);
		this.mList = mList;
		this.flag = flag;
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
		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(ctx, R.layout.product_manager_list_item,
					null);
			holder.iv_product = (ImageView) convertView
					.findViewById(R.id.iv_product);
			holder.tv_product_name = (TextView) convertView
					.findViewById(R.id.tv_product_name);
			holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
			holder.tv_description = (TextView) convertView
					.findViewById(R.id.tv_description);
			holder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
			holder.tv_price = (TextView) convertView
					.findViewById(R.id.tv_price);
			
			holder.tv_down = (TextView) convertView
					.findViewById(R.id.tv_down);
			holder.tv_copy = (TextView) convertView.findViewById(R.id.tv_copy);
			holder.tv_modify = (TextView) convertView.findViewById(R.id.tv_modify);
			
			holder.tv_up = (TextView) convertView
					.findViewById(R.id.tv_up);
			holder.tv_copy_willbeoffline = (TextView) convertView.findViewById(R.id.tv_copy_willbeoffline);
			holder.tv_delete = (TextView) convertView.findViewById(R.id.tv_delete);
			
			holder.ll_online = (LinearLayout) convertView
					.findViewById(R.id.ll_online);
			holder.ll_offline = (LinearLayout) convertView
					.findViewById(R.id.ll_offline);
			if(0== flag){//online product
				holder.ll_online.setVisibility(View.VISIBLE);
				holder.ll_offline.setVisibility(View.GONE);
			}else{
				//offline or will be offline
				holder.ll_online.setVisibility(View.GONE);
				holder.ll_offline.setVisibility(View.VISIBLE);
			}
			FontManager.changeFonts(ctx, holder.tv_product_name,
					holder.tv_product_name, holder.tv_price,
					holder.tv_description, holder.tv_size, holder.tv_price,
					holder.tv_down, holder.tv_copy_willbeoffline, holder.tv_modify,
					holder.tv_up, holder.tv_copy, holder.tv_delete
					);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		return convertView;
	}

	class Holder {
		ImageView iv_product;
		TextView tv_product_name;
		TextView tv_price;
		TextView tv_description;
		TextView tv_date;
		TextView tv_size;
		LinearLayout ll_online,ll_offline;
		
		TextView tv_down;
		TextView tv_copy_willbeoffline;
		TextView tv_modify;

		
		TextView tv_up;
		TextView tv_copy;
		TextView tv_delete;
	}

}
