package com.shenma.yueba.baijia.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.modle.ProductManagerForOnLineBean;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.yangjia.fragment.ProductManagerFragmentForOnLine;

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

	public void setFlag(int flag) {
		this.flag = flag;
	}

	@SuppressWarnings("null")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
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
					.findViewById(R.id.tv_button2);
			holder.tv_button3 = (TextView) convertView
					.findViewById(R.id.tv_button3);
			holder.tv_button4 = (TextView) convertView
					.findViewById(R.id.tv_button4);
			holder.tv_button1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (flag == 1 || flag == 2) {// 上架
						setOnLineOrOffLine(position,mList.get(position).getProductId(), 1);
					} else if (flag == 0) {// 下架
						setOnLineOrOffLine(position,mList.get(position).getProductId(), 0);
					}
				}
			});
			
			holder.tv_button4.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {

					if (flag == 1 || flag == 2) {// 修改
						
					} else if (flag == 0) {// 删除
						deleteProduct(position, mList.get(position).getProductId());
					}
				}
			});

			FontManager.changeFonts(ctx, holder.tv_brand_name, holder.tv_price,
					holder.tv_description, holder.tv_size, holder.tv_price,
					holder.tv_button1, holder.tv_button2, holder.tv_button3,
					holder.tv_button4);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		if (flag == 1) {// 在线商品
			holder.tv_button1.setText("下架");
			holder.tv_button2.setText("复制");
			holder.tv_button3.setText("分享");
			holder.tv_button4.setText("修改");
		} else if (flag == 2) {// 即将下线
			holder.tv_button1.setText("下架");
			holder.tv_button2.setText("复制");
			holder.tv_button3.setText("分享");
			holder.tv_button4.setText("修改");
		} else if (flag == 0) {// 下线商品
			holder.tv_button1.setText("上架");
			holder.tv_button2.setText("复制");
			holder.tv_button3.setText("分享");
			holder.tv_button4.setText("删除");
		}

		if(TextUtils.isEmpty(mList.get(position).getPic())){
			bitmapUtils.display(holder.iv_product,"aaa");
		}else{
			bitmapUtils.display(holder.iv_product,
					ToolsUtil.getImage(mList.get(position).getPic(), 120, 0));
		}
		holder.tv_brand_name.setText(ToolsUtil.nullToString(mList.get(position)
				.getBrandName()));
		holder.tv_date.setText(ToolsUtil.nullToString(mList.get(position)
				.getExpireTime()));
		holder.tv_description.setText(ToolsUtil.nullToString(mList
				.get(position).getProductName()));
		holder.tv_size.setText(ToolsUtil.nullToString(TextUtils.isEmpty(mList
				.get(position).getStoreItemNo()) ? "" : "货号："
				+ mList.get(position).getStoreItemNo()));
		holder.tv_price.setText("￥"
				+ ToolsUtil.nullToString(mList.get(position).getPrice()));
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
	
	
	
	
	/**
	 * 上线/下线接口
	 * @param orderId
	 * @param status
	 */
	private void setOnLineOrOffLine(final int position,String id,final int status){
		HttpControl httpControl = new HttpControl();
		httpControl.setProductOnLineOrOffLine(id, status, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				Toast.makeText(ctx, status ==1?"上线成功":"下线成功", 1000).show();
				mList.remove(position);
				notifyDataSetChanged();
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				// TODO Auto-generated method stub
				
			}
		}, ctx, true, true);
		
		
	}
	
	
	
	/**
	 * 删除商品接口
	 * @param orderId
	 * @param status
	 */
	private void deleteProduct(final int position,String id){
		HttpControl httpControl = new HttpControl();
		httpControl.deleteProduct(id, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				Toast.makeText(ctx, "删除成功", 1000).show();
				mList.remove(position);
				notifyDataSetChanged();
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				// TODO Auto-generated method stub
				
			}
		}, ctx, true, true);
		
		
	}
	
	
}
