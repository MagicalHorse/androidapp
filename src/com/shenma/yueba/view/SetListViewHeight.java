package com.shenma.yueba.view;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
/**
 *@author baiyuliang
 *@date 2014-07-15
 *@blog http://blog.csdn.net/baiyuliang2013
 */
public class SetListViewHeight {
	
	public static void setListViewHeightBasedOnChildren(ListView listView) { 
        ListAdapter listAdapter = listView.getAdapter();  
        if (listAdapter == null) { 
            return; 
        } 
        int totalHeight = 0; 
        for (int i = 0; i < listAdapter.getCount(); i++) { 
            View listItem = listAdapter.getView(i, null, listView); 
            listItem.measure(0, 0); 
            totalHeight += listItem.getMeasuredHeight(); 
        } 
        ViewGroup.LayoutParams params = listView.getLayoutParams(); 
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)); 
        listView.setLayoutParams(params); 
    } 

}
