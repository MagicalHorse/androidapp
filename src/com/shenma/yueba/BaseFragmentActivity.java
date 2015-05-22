package com.shenma.yueba;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BaseFragmentActivity extends FragmentActivity{

	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
	}
	
	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		// TODO Auto-generated method stub
		return super.onCreateView(name, context, attrs);
	}
	
	
	
	protected void setCursorAndText(int index,ArrayList<ImageView> cursorImageList,ArrayList<TextView> titleTextList) {
		for (int i = 0; i < cursorImageList.size(); i++) {
			if (i != index) {
				cursorImageList.get(i).setVisibility(View.INVISIBLE);
			} else {
				cursorImageList.get(i).setVisibility(View.VISIBLE);
			}
		}
		for (int j = 0; j < titleTextList.size(); j++) {
			if (j != index) {
				titleTextList.get(j).setTextSize(17);
			} else {
				titleTextList.get(j).setTextSize(20);
			}
		}
	}
}
