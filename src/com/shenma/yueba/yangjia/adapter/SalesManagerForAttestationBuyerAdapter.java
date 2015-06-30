package com.shenma.yueba.yangjia.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.adapter.BaseAdapterWithUtil;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.yangjia.modle.OrderItem;
import com.shenma.yueba.yangjia.modle.ProductItemBean;

public class SalesManagerForAttestationBuyerAdapter extends BaseAdapterWithUtil {
	private List<OrderItem> mList;
	private int tag;//标记不同的订单状态
	public SalesManagerForAttestationBuyerAdapter(Context ctx,List<OrderItem> mList,int tag) {
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
			convertView = View.inflate(ctx, R.layout.sales_manager_item_for_attestation_buyer, null);
			holder.iv_product = (ImageView) convertView.findViewById(R.id.iv_product);
			holder.tv_commission = (TextView) convertView.findViewById(R.id.tv_commission);
			holder.tv_order_number = (TextView) convertView.findViewById(R.id.tv_order_number);
			holder.tv_order_status = (TextView) convertView.findViewById(R.id.tv_order_status);
			holder.tv_money_payed = (TextView) convertView.findViewById(R.id.tv_money_payed);
			holder.tv_product_name = (TextView) convertView.findViewById(R.id.tv_product_name);
			holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
			holder.tv_description = (TextView) convertView.findViewById(R.id.tv_description);
			holder.tv_same_product_count = (TextView) convertView.findViewById(R.id.tv_same_product_count);
			holder.tv_huohao = (TextView) convertView.findViewById(R.id.tv_huohao);
			holder.tv_guige = (TextView) convertView.findViewById(R.id.tv_guige);
			holder.ll_bottom = (LinearLayout) convertView.findViewById(R.id.ll_bottom);
			holder.tv_bottom = (TextView) convertView.findViewById(R.id.tv_bottom);
			FontManager.changeFonts(ctx, holder.tv_order_number,holder.tv_order_status,
					holder.tv_money_payed,holder.tv_product_name,holder.tv_price,holder.tv_description,
					holder.tv_same_product_count,holder.tv_huohao,holder.tv_guige,holder.tv_commission);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		OrderItem item = mList.get(position);
		if(item!=null){
			holder.tv_order_number.setText(TextUtils.isEmpty(item.getOrderNo())?"":"订单号："+item.getOrderNo());
			holder.tv_order_status.setText(TextUtils.isEmpty(item.getStatusName())?"":item.getStatusName());
			holder.tv_commission.setText(TextUtils.isEmpty(item.getInCome())?"":"佣金：￥"+item.getInCome());
			ToolsUtil.setTextColorBlackAndRed(holder.tv_money_payed, "实付：￥", ToolsUtil.getTextColorRed(item.getAmount()));
			if(item.getProducts()!=null && item.getProducts().size()>0){//说明有商品
				List<ProductItemBean>  products = item.getProducts();
				ProductItemBean productItme = products.get(0);
				if(!TextUtils.isEmpty(productItme.getPicture())){
					bitmapUtils.display(holder.iv_product, productItme.getPicture());
					holder.tv_price.setText(TextUtils.isEmpty(productItme.getPrice())?"":productItme.getPrice()+"￥");
					holder.tv_product_name.setText(TextUtils.isEmpty(productItme.getBrandName())?"":productItme.getBrandName());
					holder.tv_description.setText(TextUtils.isEmpty(productItme.getName())?"":productItme.getName());
					holder.tv_same_product_count.setText(TextUtils.isEmpty(productItme.getCount())?"":"X"+productItme.getCount());
					holder.tv_huohao.setText(TextUtils.isEmpty(productItme.getStoreItemNo())?"":"货号："+productItme.getStoreItemNo());
					holder.tv_guige.setText(TextUtils.isEmpty(productItme.getSizeName())?"":"规格："+productItme.getSizeName());
				}
			}
			
		}
	
		
		
		if(0 == tag){//全部订单
			
		}else if(1 == tag){//待付款
			
		}else if(2 == tag){//专柜自提
			
		}else if(3 == tag){//售后服务
			
		}
		
		return convertView;
	}
	
	
	class Holder{
		TextView tv_order_number;//订单号
		TextView tv_order_status;//订单状态
		TextView tv_money_payed;//实付价格
		ImageView iv_product;//商品图片
		TextView tv_product_name;//商品名称
		TextView tv_price;//商品价格
		TextView tv_description;//商品描述
		TextView tv_same_product_count;//商品个数
		TextView tv_huohao;//货号
		TextView tv_guige;//规格
		TextView tv_commission;//规格
		LinearLayout ll_bottom;
		TextView tv_bottom;
	}

}
