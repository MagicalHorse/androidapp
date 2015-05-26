package com.shenma.yueba.baijia.adapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	List<String> str_list=new ArrayList<String>();
	List<Integer> icon_list=new ArrayList<Integer>();
	public InvitationAdapter(Context context,List<String> str_list,List<Integer> icon_list)
	{
		this.context=context;
		this.str_list=str_list;
		this.icon_list=icon_list;
	}
	@Override
	public int getCount() {
		
		return str_list.size();
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
		Holder holder ;
		if(convertView==null)
		{
			holder=new Holder();
			convertView=(RelativeLayout)RelativeLayout.inflate(context, R.layout.invitation_layout_item, null);
			holder.tv=(TextView)convertView.findViewById(R.id.invitation_layout_item_textview);
			holder.iv=(ImageView)convertView.findViewById(R.id.invitation_layout_item_imageview);
			convertView.setTag(holder);
		}else
		{
			holder=(Holder)convertView.getTag();
		}
		holder.tv.setText(str_list.get(position));
		holder.iv.setImageResource(icon_list.get(position));
		return convertView;
	}

	class Holder
	{
		TextView tv;
		ImageView iv;
	}
}
