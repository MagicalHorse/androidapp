package com.shenma.yueba.baijia.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.modle.ProductManagerForOnLineBean;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.ToolsUtil;

public class ProductManagerFragmentForOnLineAdapter extends BaseAdapterWithUtil
		implements OnClickListener {
	private List<ProductManagerForOnLineBean> mList;
	private int flag;

	public ProductManagerFragmentForOnLineAdapter(Context ctx,
			List<ProductManagerForOnLineBean> mList, int flag) {
		super(ctx);
		this.mList = mList;
		this.flag = flag;
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
		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(ctx, R.layout.product_manager_list_item,
					null);
			holder.iv_product = (ImageView) convertView
					.findViewById(R.id.iv_product);
			holder.tv_brand_name = (TextView) convertView
					.findViewById(R.id.tv_brand_name);
			holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
			holder.tv_description = (TextView) convertView
					.findViewById(R.id.tv_description);
			holder.tv_size = (TextView) convertView.findViewById(R.id.tv_size);
			holder.tv_price = (TextView) convertView
					.findViewById(R.id.tv_price);

			holder.tv_button1 = (TextView) convertView
					.findViewById(R.id.tv_button1);
			holder.tv_button2 = (TextView) convertView
					.findViewById(R.id.tv_button1);
			holder.tv_button3 = (TextView) convertView
					.findViewById(R.id.tv_button3);
			holder.tv_button4 = (TextView) convertView
					.findViewById(R.id.tv_button4);

			if (flag == 0) {// 在线商品
				holder.tv_button1.setText("下架");
				holder.tv_button2.setText("复制");
				holder.tv_button3.setText("分享");
				holder.tv_button4.setText("修改");
			} else if (flag == 1) {// 即将下线
				holder.tv_button1.setText("下架");
				holder.tv_button2.setText("复制");
				holder.tv_button3.setText("分享");
				holder.tv_button4.setText("修改");
			} else if (flag == 2) {// 下线商品
				holder.tv_button1.setText("上架");
				holder.tv_button2.setText("复制");
				holder.tv_button3.setText("分享");
				holder.tv_button4.setText("删除");
			}
			holder.tv_button1.setOnClickListener(this);
			holder.tv_button2.setOnClickListener(this);
			holder.tv_button3.setOnClickListener(this);
			holder.tv_button4.setOnClickListener(this);

			FontManager.changeFonts(ctx,
					holder.tv_brand_name, holder.tv_price,
					holder.tv_description, holder.tv_size, holder.tv_price,
					holder.tv_button1, holder.tv_button2, holder.tv_button3,
					holder.tv_button4);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		bitmapUtils.display(holder.iv_product, mList.get(position).getPic());
		holder.tv_brand_name.setText(ToolsUtil.nullToString(mList.get(position).getBrandName()));
		holder.tv_date.setText(ToolsUtil.nullToString(mList.get(position).getExpireTime()));
		holder.tv_description.setText(ToolsUtil.nullToString(mList.get(position).getProductName()));
		holder.tv_size.setText(ToolsUtil.nullToString(TextUtils.isEmpty(mList.get(position).getStoreItemNo())?"":"货号："+mList.get(position).getStoreItemNo()));
		holder.tv_price.setText(ToolsUtil.nullToString(ToolsUtil.nullToString(mList.get(position).getPrice())));
		return convertView;
	}

	class Holder {
		ImageView iv_product;
		TextView tv_brand_name;
		TextView tv_price;
		TextView tv_description;
		TextView tv_date;
		TextView tv_size;

		TextView tv_button1;
		TextView tv_button2;
		TextView tv_button3;
		TextView tv_button4;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_button1:
			if (flag == 0 || flag == 1) {// 上架

			} else if (flag == 2) {// 下架

			}
			break;
		case R.id.tv_button2:// 复制

			break;

		case R.id.tv_button3:// 分享

			break;

		case R.id.tv_button4:
			if (flag == 0 || flag == 1) {// 修改

			} else if (flag == 2) {// 删除

			}
			break;

		default:
			break;
		}

	}
}
