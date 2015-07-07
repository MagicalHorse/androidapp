package com.shenma.yueba.baijia.adapter;


import im.control.ChatViewManager;
import im.form.BaseChatBean;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
/*****
 * 聊天界面适配器
 * ***/
public class ChattingAdapter extends BaseAdapter {
	Context context;
	List<BaseChatBean> bean_list=new ArrayList<BaseChatBean>();
	public ChattingAdapter(Context context,List<BaseChatBean> bean_list)
	{
		this.context=context;
		this.bean_list=bean_list;
	}
	
	@Override
	public int getCount() {
		
		return bean_list.size();
	}

	@Override
	public Object getItem(int arg0) {
		
		return null;
	}

	@Override
	public long getItemId(int position) {
		
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ChatViewManager vm;
		if(convertView==null)
		{
			vm=new ChatViewManager(context);
			convertView=vm.initView();
			convertView.setTag(vm);
		}else
		{
			vm=(ChatViewManager)convertView.getTag();
		}
		
		vm.setView(bean_list.get(position),convertView);
		return convertView;
	}
	
	
}
