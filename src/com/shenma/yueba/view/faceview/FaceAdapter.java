package com.shenma.yueba.view.faceview;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.shenma.yueba.R;

public class FaceAdapter extends BaseAdapter {
	private List<Integer> integers;
	private LayoutInflater layoutifo;

	public FaceAdapter(Context context, List<Integer> integers) {
		super();
		this.integers = integers;
		layoutifo = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return integers.size();
	}

	@Override
	public Object getItem(int arg0) {
		return integers.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View conview, ViewGroup arg2) {
		Viewholder viewholder;
		if (conview == null) {
			conview = layoutifo.inflate(R.layout.face_dialog_item, null);
			viewholder = new Viewholder();
			viewholder.imageView = (ImageView) conview
					.findViewById(R.id.faceitemimageView1);
			conview.setTag(viewholder);
		} else {
			viewholder = (Viewholder) conview.getTag();
		}
		viewholder.imageView.setImageResource(integers.get(position));
		return conview;
	}
}
class Viewholder {
	public ImageView imageView;
}
