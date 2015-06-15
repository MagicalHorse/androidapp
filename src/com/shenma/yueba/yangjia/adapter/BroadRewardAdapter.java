package com.shenma.yueba.yangjia.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.adapter.BaseAdapterWithUtil;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.yangjia.modle.HistoryItem;

public class BroadRewardAdapter extends BaseAdapterWithUtil {
	private List<HistoryItem> mList;
	public BroadRewardAdapter(Context ctx,List<HistoryItem> mList) {
		super(ctx);
		this.mList = mList;
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
			convertView = View.inflate(ctx, R.layout.broad_reward_list_item, null);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
			FontManager.changeFonts(ctx, holder.tv_time,holder.tv_content);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		return convertView;
	}
	
	
	class Holder{
		TextView tv_time;
		TextView tv_content;
	}

}
