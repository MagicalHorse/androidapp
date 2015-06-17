package com.shenma.yueba.yangjia.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.adapter.BaseAdapterWithUtil;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.yangjia.modle.TaskListItem;

public class TastRewardAdapter extends BaseAdapterWithUtil {
	private List<TaskListItem> mList;
	private int tag;
	public TastRewardAdapter(Context ctx,List<TaskListItem> mList) {
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
			convertView = View.inflate(ctx, R.layout.task_reward_item, null);
			holder.tv_board_reward = (TextView) convertView.findViewById(R.id.tv_board_reward);
			holder.tv_progress = (TextView) convertView.findViewById(R.id.tv_progress);
			holder.iv= (ImageView) convertView.findViewById(R.id.iv);
			FontManager.changeFonts(ctx, holder.tv_board_reward,holder.tv_progress);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		holder.tv_board_reward.setText(ToolsUtil.nullToString(mList.get(position).getName()));
		holder.tv_progress.setText(ToolsUtil.nullToString(mList.get(position).getTip()));
		bitmapUtils.display(holder.iv, mList.get(position).getIcon());
		return convertView;
	}
	
	
	class Holder{
		TextView tv_board_reward;
		TextView tv_progress;
		ImageView iv;
	}

}
