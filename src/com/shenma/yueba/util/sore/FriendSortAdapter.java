package com.shenma.yueba.util.sore;


import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.view.RoundImageView;
import com.shenma.yueba.yangjia.activity.CircleInvitectivity;
import com.shenma.yueba.yangjia.modle.FansItemBean;

public class FriendSortAdapter extends BaseAdapter implements SectionIndexer{
	private List<FansItemBean> list = null;
	private Context mContext;
	
	public FriendSortAdapter(Context mContext, List<FansItemBean> list) {
		this.mContext = mContext;
		this.list = list;
	}
	
	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * @param list
	 */
	public void updateListView(List<FansItemBean> list){
		this.list = list;
		notifyDataSetChanged();
	}

	public List<FansItemBean>  getListData()
	{
		return list;
	}
	
	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final FansItemBean mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.invite_to_circle_item, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.cb = (CheckBox) view.findViewById(R.id.cb);
			viewHolder.riv_head = (RoundImageView) view.findViewById(R.id.riv_head);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		
		//根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);
		
		//如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if(position == getPositionForSection(section)){
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getSortLetters());
		}else{
			viewHolder.tvLetter.setVisibility(View.GONE);
		}
	
		viewHolder.tvTitle.setText(this.list.get(position).getUserName());
		initPic(list.get(position).getUserLogo(), viewHolder.riv_head);
		
		
		viewHolder.cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					((CircleInvitectivity)mContext).setIdToList(list.get(position).getUserId());
				//	list.get(position).setChecked(true);
				}else{
					((CircleInvitectivity)mContext).removeIdFromList(list.get(position).getUserId());
					//list.get(position).setChecked(false);
				}
			//	notifyDataSetChanged();
			}
		});
		viewHolder.cb.setChecked(list.get(position).isChecked());
		FontManager.changeFonts(mContext, viewHolder.tvTitle,viewHolder.tvLetter);
		return view;

	}
	
	/****
	 * 加载图片
	 * */
	void initPic(final String url, final ImageView iv) {
		MyApplication.getInstance().getBitmapUtil().display(iv, url);
	}

	final static class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
		CheckBox cb;
		RoundImageView riv_head;
	}


	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		
		return -1;
	}
	
	/**
	 * 提取英文的首字母，非英文字母用#代替。
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String  sortStr = str.trim().substring(0, 1).toUpperCase();
		// 正则表达式，判断首字母是否是英文字母
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}