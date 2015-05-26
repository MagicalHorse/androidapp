package com.shenma.yueba.baijia.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.adapter.InvitationAdapter;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-26 上午7:35:18  
 * 程序的简单说明  邀请好友
 */

public class InvitationFriendActivity extends Activity{
ListView invitationfriend_listview;
Map<Integer,String> map=new HashMap<Integer, String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		setContentView(R.layout.invitationfriend_layout);
		super.onCreate(savedInstanceState);
		map.put(R.drawable.test002, "邀请微信好友");
		map.put(R.drawable.test001, "邀请通讯录好友");
		initView();
	}
	
	void initView()
	{
		invitationfriend_listview=(ListView)findViewById(R.id.invitationfriend_listview);
		InvitationAdapter invitationAdapter=new InvitationAdapter(this,map);
		invitationfriend_listview.setAdapter(invitationAdapter);
	}
}
