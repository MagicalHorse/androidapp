package com.shenma.yueba.baijia.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.modle.BrandListBean;

public class ImageTextlAdapter extends BaseAdapterWithUtil {
	private List<BrandListBean> mList;
	public ImageTextlAdapter(Context ctx,List<BrandListBean> mList) {
		super(ctx);
		this.mList = mList;
	}

	@Override
	public int getCount() {
		
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		
		return mList.get(position);
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
			convertView=RelativeLayout.inflate(ctx, R.layout.imagetext_item_layout, null);
			holder.imagetext_item_imageview = (ImageView)convertView.findViewById(R.id.imagetext_item_imageview);
			holder.imagetext_item_textview = (TextView)convertView.findViewById(R.id.imagetext_item_textview);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		return convertView;
	}
	
	
	class Holder{
		ImageView imagetext_item_imageview;
		TextView imagetext_item_textview;
		
	}

}
