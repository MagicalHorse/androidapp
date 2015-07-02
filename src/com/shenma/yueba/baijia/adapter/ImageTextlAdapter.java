package com.shenma.yueba.baijia.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.BrandSearchInfo;
import com.shenma.yueba.util.ToolsUtil;

public class ImageTextlAdapter extends BaseAdapterWithUtil {
	private List<BrandSearchInfo> mList;
	Context ctx;
	public ImageTextlAdapter(Context ctx,List<BrandSearchInfo> mList) {
		super(ctx);
		this.ctx=ctx;
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
			ToolsUtil.setFontStyle(ctx,convertView, R.id.imagetext_item_textview);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		setValue(holder,position);
		return convertView;
	}
	
	
	class Holder{
		ImageView imagetext_item_imageview;
		TextView imagetext_item_textview;
		
	}
	
	void setValue(Holder holder,int position)
	{
		BrandSearchInfo brandSearchInfo=mList.get(position);
		String url=ToolsUtil.getImage(ToolsUtil.nullToString(brandSearchInfo.getLogo()), 320, 0);
		MyApplication.getInstance().getImageLoader().displayImage(url, holder.imagetext_item_imageview, MyApplication.getInstance().getDisplayImageOptions());
		holder.imagetext_item_textview.setText(brandSearchInfo.getName());
	}

}
