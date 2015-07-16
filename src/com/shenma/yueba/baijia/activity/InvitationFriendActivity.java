package com.shenma.yueba.baijia.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.InvitationAdapter;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-26 上午7:35:18  
 * 程序的简单说明  邀请好友
 */

public class InvitationFriendActivity extends BaseActivityWithTopView{
ListView invitationfriend_listview;
List<String> str_list=new ArrayList<String>();
List<Integer> icon_list=new ArrayList<Integer>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);//加入回退栈
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		setContentView(R.layout.invitationfriend_layout);
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		str_list.add("邀请微信好友");
		str_list.add("邀请通讯录好友");
		icon_list.add(new Integer(R.drawable.test002));
		icon_list.add(new Integer(R.drawable.test001));
		initView();
	}
	
	void initView()
	{
		setTitle("邀请好友");
		setLeftTextView(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				InvitationFriendActivity.this.finish();
			}
		});
		invitationfriend_listview=(ListView)findViewById(R.id.invitationfriend_listview);
		InvitationAdapter invitationAdapter=new InvitationAdapter(this,str_list,icon_list);
		invitationfriend_listview.setAdapter(invitationAdapter);
		invitationfriend_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				
			}
		});
	}
	
	
	
	  @Override
	    protected void onDestroy() {
	    	MyApplication.getInstance().removeActivity(this);//加入回退栈
	    	super.onDestroy();
	    }
}
