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
import com.shenma.yueba.baijia.adapter.ImageTextlAdapter.Holder;
import com.shenma.yueba.baijia.modle.BrandListBean;
import com.shenma.yueba.baijia.modle.BrandSearchInfo;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.RoundImageView;
import com.shenma.yueba.view.imageshow.CustomImageView;

public class SearchBuyerAdapter extends BaseAdapterWithUtil {
	private List<BrandSearchInfo> mList;
	public SearchBuyerAdapter(Context ctx,List<BrandSearchInfo> mList) {
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
			convertView=RelativeLayout.inflate(ctx, R.layout.search_buyer_item_layout, null);
			holder.search_buyer_item_textview = (TextView)convertView.findViewById(R.id.search_buyer_item_textview);
			ToolsUtil.setFontStyle(ctx, convertView, R.id.search_buyer_item_textview);
			holder.search_buyer_item_imageview = (RoundImageView)convertView.findViewById(R.id.search_buyer_item_imageview);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		setValue(holder, position);
		return convertView;
	}
	
	
	class Holder{
		TextView search_buyer_item_textview;
		RoundImageView search_buyer_item_imageview;
		
	}

	
	void setValue(Holder holder,int position)
	{
		BrandSearchInfo brandSearchInfo=mList.get(position);
		String url=ToolsUtil.getImage(ToolsUtil.nullToString(brandSearchInfo.getLogo()), 320, 0);
		initBitmap(url, holder.search_buyer_item_imageview);
		holder.search_buyer_item_textview.setText(brandSearchInfo.getName());
	}
	
	void initBitmap(final String url, final ImageView iv)
	{
		MyApplication.getInstance().getBitmapUtil().display(iv, url);
	}
}
