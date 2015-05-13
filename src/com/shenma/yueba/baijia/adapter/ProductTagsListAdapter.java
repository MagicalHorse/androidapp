package com.shenma.yueba.baijia.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.modle.TagListBackBean;

public class ProductTagsListAdapter extends BaseAdapterWithUtil {
	private List<TagListBackBean> mList;

	public ProductTagsListAdapter(Context ctx, List<TagListBackBean> mList) {
		super(ctx);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 50;
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
		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(ctx, R.layout.tag_list_item, null);
			MyApplication
					.getInstance()
					.getImageLoader()
					.displayImage(mList.get(position).getImageUrl(),
							holder.iv_tag);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		return convertView;
	}

	class Holder {
		ImageView iv_tag;
	}

}
