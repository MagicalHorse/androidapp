package com.shenma.yueba.baijia.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.modle.BrandListBean;

public class LabelAdapter extends BaseAdapterWithUtil {
	private List<BrandListBean> mList;
	public LabelAdapter(Context ctx,List<BrandListBean> mList) {
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
			convertView=RelativeLayout.inflate(ctx, R.layout.label_item_layout, null);
			holder.textview = (TextView)convertView.findViewById(R.id.label_item_layout_textview);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		return convertView;
	}
	
	
	class Holder{
		TextView textview;
		
	}

}
