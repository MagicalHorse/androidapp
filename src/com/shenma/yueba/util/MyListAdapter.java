package com.shenma.yueba.util;


import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shenma.yueba.R;

	public class MyListAdapter extends BaseAdapter {
		private Context ctx;
		private List<String> strs;
		private int positionIndex = -1;
		
		public MyListAdapter(Context ctx,List<String>strs) {
			this.ctx = ctx;
			this.strs = strs;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return strs.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return strs.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(ctx,
					R.layout.pop_item, null);
			TextView tv_item = (TextView) view.findViewById(R.id.tv_item);
			tv_item.setText(strs.get(position));
//			if (position == positionIndex) {
//				tv_item.setBackgroundResource(R.drawable.popitem_bg);
//			}
			return view;
		}

	}