package com.shenma.yueba.yangjia.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.adapter.BaseAdapterWithUtil;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.yangjia.modle.IdentificationBuyerListBean;

public class IdentificationBuyerAdapter extends BaseAdapterWithUtil {
	private List<IdentificationBuyerListBean> mList;
	public IdentificationBuyerAdapter(Context ctx,List<IdentificationBuyerListBean> mList) {
		super(ctx);
		this.mList = mList;
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
		if(convertView == null){
			holder = new Holder();
			convertView = View.inflate(ctx, R.layout.attention_identification_buyer_list_item, null);
			holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_grade = (TextView) convertView.findViewById(R.id.tv_grade);
			holder.cb = (CheckBox) convertView.findViewById(R.id.cb);
			FontManager.changeFonts(ctx, holder.tv_name,holder.tv_grade);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		return convertView;
	}
	
	
	class Holder{
		ImageView iv_icon;
		TextView tv_name;
		TextView tv_grade;
		CheckBox cb;
	}

}
