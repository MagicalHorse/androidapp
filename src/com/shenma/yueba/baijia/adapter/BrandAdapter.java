package com.shenma.yueba.baijia.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.BrandInfo;
import com.shenma.yueba.util.ToolsUtil;

public class BrandAdapter extends BaseAdapterWithUtil {
	private List<BrandInfo> items;
	public BrandAdapter(Context ctx,List<BrandInfo> items) {
		super(ctx);
		this.items = items;
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

	@SuppressWarnings("null")
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
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		BrandInfo brandInfo=items.get(position);
		initPic(holder.iv_brand, ToolsUtil.nullToString(brandInfo.getBrandLogo()));
		
		initPic(holder.brandlist_item_imageview1, ToolsUtil.nullToString(brandInfo.getBrandLogo()));
		initPic(holder.brandlist_item_imageview2, ToolsUtil.nullToString(brandInfo.getBrandLogo()));
		initPic(holder.brandlist_item_imageview3, ToolsUtil.nullToString(brandInfo.getBrandLogo()));
		initPic(holder.brandlist_item_imageview4, ToolsUtil.nullToString(brandInfo.getBrandLogo()));
		
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
		MyApplication.getInstance().getImageLoader().displayImage(url, iv, MyApplication.getInstance().getDisplayImageOptions());
	}

}
