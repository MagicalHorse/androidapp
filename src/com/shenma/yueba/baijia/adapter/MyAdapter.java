package com.shenma.yueba.baijia.adapter;

import java.util.List;

import com.shenma.yueba.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapterWithUtil {

	private List<String> mList;
	private Context ctx;
	public MyAdapter(List<String> list,Context ctx){
		super(ctx);
		this.mList = list;
		this.ctx = ctx;
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
		View view = View.inflate(ctx, R.layout.item, null);
		TextView tv = (TextView) view.findViewById(R.id.tv);
		tv.setText(mList.get(position));
		return view;
	}

}
