package com.shenma.yueba.baijia.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.MsgListInfo;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.RoundImageView;

public class MsgAdapter extends BaseAdapterWithUtil {
	private List<MsgListInfo> mList;
	public MsgAdapter(Context ctx,List<MsgListInfo> mList) {
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if(convertView == null){
			holder = new Holder();
			convertView = View.inflate(ctx, R.layout.msg_item, null);
			holder.iv_msg = (RoundImageView)convertView.findViewById(R.id.iv_msg);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
			holder.tv_msg = (TextView) convertView.findViewById(R.id.tv_msg);
			holder.tv_msg_count = (TextView) convertView.findViewById(R.id.tv_msg_count);
			FontManager.changeFonts(ctx, holder.tv_name,holder.tv_time,holder.tv_msg);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		setValue(holder,position);
		return convertView;
	}
	
	
	class Holder{
		RoundImageView iv_msg;
		TextView tv_name;
		TextView tv_time;
		TextView tv_msg;
		TextView tv_msg_count;
	}

	void setValue(Holder holder, int position)
	{
		MsgListInfo msgListInf=	mList.get(position);
		holder.tv_msg_count.setText(msgListInf.getUnReadCount()+"");
		if(msgListInf.getUnReadCount()>0)
		{
			holder.tv_msg_count.setVisibility(View.VISIBLE);
		}else
		{
			holder.tv_msg_count.setVisibility(View.GONE);
		}
		initBitmap(ToolsUtil.nullToString(msgListInf.getLogo()), holder.iv_msg);
		holder.tv_name.setText(ToolsUtil.nullToString(msgListInf.getName()));
		holder.tv_time.setText(ToolsUtil.nullToString(msgListInf.getUpdateTime()));
		holder.tv_msg.setText(ToolsUtil.nullToString(msgListInf.getUnReadMessage()));
	}
	
	void initBitmap(final String url, final ImageView iv)
	{
		MyApplication.getInstance().getBitmapUtil().display(iv, url);
	}
}
