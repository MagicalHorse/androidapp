package com.shenma.yueba.baijia.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.modle.TagListItemBean;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.ToolsUtil;

public class ProductTagsListAdapter extends BaseAdapterWithUtil {
	private List<TagListItemBean> mList;
	private String key;
	public ProductTagsListAdapter(Context ctx, List<TagListItemBean> mList) {
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

	
	public void setKey(String key){
		this.key = key;
	}
	
	@SuppressWarnings("null")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(ctx, R.layout.item_text, null);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		String  name= mList.get(position).getName();
//		if(!TextUtils.isEmpty(key)){
//			String[] strArr = name.split(key);
//			for (int i = 0; i < strArr.length; i++) {
//				
//			}
//			ToolsUtil.setKeyColor(holder.tv_name, normalBegin, redCenter, normalEnd)
//		}else{
			holder.tv_name.setText(ToolsUtil.nullToString(mList.get(position).getName()));
			FontManager.changeFonts(ctx, holder.tv_name);
//		}
		
	
		return convertView;
	}

	class Holder {
		TextView tv_name;
	}

}
