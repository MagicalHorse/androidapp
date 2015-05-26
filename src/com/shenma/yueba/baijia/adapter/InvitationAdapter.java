package com.shenma.yueba.baijia.adapter;

import java.util.Map;

import com.shenma.yueba.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-26 上午7:59:02  
 * 程序的简单说明   推荐好友适配器
 */

public class InvitationAdapter extends BaseAdapter{
	Context context;
	Map<Integer,String> map;
	public InvitationAdapter(Context context,Map<Integer,String> map)
	{
		this.context=context;
		this.map=map;
	}
	@Override
	public int getCount() {
		
		return map.size();
	}

	@Override
	public Object getItem(int position) {
		
		return null;
	}

	@Override
	public long getItemId(int position) {
		
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		RelativeLayout rl=(RelativeLayout)RelativeLayout.inflate(context, R.layout.invitation_layout_item, null);
		return rl;
	}

	class Holder
	{
		TextView tv;
		ImageView iv;
	}
}
