package com.shenma.yueba.baijia.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.BrandInfo;
import com.shenma.yueba.baijia.modle.ProductInFo;
import com.shenma.yueba.util.ToolsUtil;

public class BrandAdapter extends BaseAdapterWithUtil {
	private List<BrandInfo> items;
	Context context;
	public BrandAdapter(Context ctx,List<BrandInfo> items) {
		super(ctx);
		this.items = items;
		this.context=ctx;
	}

	@Override
	public int getCount() {
		
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if(convertView == null){
			holder = new Holder();
			convertView = View.inflate(ctx, R.layout.brand_list_item, null);
			holder.iv_brand = (ImageView) convertView.findViewById(R.id.iv_brand);
			holder.brandlist_item_imageview1 = (ImageView) convertView.findViewById(R.id.brandlist_item_imageview1);
			holder.brandlist_item_imageview2 = (ImageView) convertView.findViewById(R.id.brandlist_item_imageview2);
			holder.brandlist_item_imageview3 = (ImageView) convertView.findViewById(R.id.brandlist_item_imageview3);
			holder.brandlist_item_imageview4 = (ImageView) convertView.findViewById(R.id.brandlist_item_imageview4);
			/*holder.brandlist_item_imageview1.setOnClickListener(onClickListener);
			holder.brandlist_item_imageview2.setOnClickListener(onClickListener);
			holder.brandlist_item_imageview3.setOnClickListener(onClickListener);
			holder.brandlist_item_imageview4.setOnClickListener(onClickListener);*/
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		
		DisplayMetrics dm=new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		int width=dm.widthPixels/4;
		if(width>0)
		{
			Log.i("TAG", "--->>WIDTH:"+width);
			holder.brandlist_item_imageview1.setLayoutParams(new LinearLayout.LayoutParams(width, width));
			holder.brandlist_item_imageview2.setLayoutParams(new LinearLayout.LayoutParams(width, width));
			holder.brandlist_item_imageview3.setLayoutParams(new LinearLayout.LayoutParams(width, width));
			holder.brandlist_item_imageview4.setLayoutParams(new LinearLayout.LayoutParams(width, width));
		}
		
		holder.brandlist_item_imageview1.setVisibility(View.INVISIBLE);
		holder.brandlist_item_imageview2.setVisibility(View.INVISIBLE);
		holder.brandlist_item_imageview3.setVisibility(View.INVISIBLE);
		holder.brandlist_item_imageview4.setVisibility(View.INVISIBLE);
		BrandInfo brandInfo=items.get(position);
		holder.iv_brand.setTag(brandInfo.getBrandId());
		initPic(holder.iv_brand, ToolsUtil.nullToString(brandInfo.getBrandLogo()));
		List<ProductInFo> productInFo_list=brandInfo.getProduct();
		for(int i=0;i<productInFo_list.size();i++)
		{
			ProductInFo productInFo=productInFo_list.get(i);
			switch(i)
			{
			case 0:
				initPic(holder.brandlist_item_imageview1, ToolsUtil.getImage(ToolsUtil.nullToString(productInFo.getPic()), 320, 0));
				holder.brandlist_item_imageview1.setTag(productInFo.getProductId());
				break;
			case 1:
				initPic(holder.brandlist_item_imageview2, ToolsUtil.getImage(ToolsUtil.nullToString(productInFo.getPic()), 320, 0));
				holder.brandlist_item_imageview2.setTag(productInFo.getProductId());
				break;
			case 2:
				initPic(holder.brandlist_item_imageview3, ToolsUtil.getImage(ToolsUtil.nullToString(productInFo.getPic()), 320, 0));
				holder.brandlist_item_imageview3.setTag(productInFo.getProductId());
				break;
			case 3:
				initPic(holder.brandlist_item_imageview4, ToolsUtil.getImage(ToolsUtil.nullToString(productInFo.getPic()), 320, 0));
				holder.brandlist_item_imageview4.setTag(productInFo.getProductId());
				break;
			}
		}
		
		return convertView;
	}
	
	
	class Holder{
		ImageView iv_brand;
		ImageView brandlist_item_imageview1;
		ImageView brandlist_item_imageview2;
		ImageView brandlist_item_imageview3;
		ImageView brandlist_item_imageview4;
		
	}
	
	void initPic(ImageView iv,String url)
	{
		if(iv!=null)
		{
			iv.setVisibility(View.VISIBLE);
		    MyApplication.getInstance().getImageLoader().displayImage(url, iv, MyApplication.getInstance().getDisplayImageOptions());
		}
	}

	
	OnClickListener onClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
			
			}
		}
	};
}
