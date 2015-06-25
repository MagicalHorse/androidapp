package com.shenma.yueba.util;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.shenma.yueba.R;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-24 下午4:01:26  
 * 程序的简单说明  
 */

public class ButtonManager {
public final static String WAITPAY="付款";//0
public final static String CANCELPAY="撤销退款";//1
public final static String QUERENTIHUO="确认提货";//2
public final static String SHENQINGTUIKUAN="申请退款";//3
	
	/***
	 * 根据订单状态返回操作按钮
	 * **/
	public static List<View> getButton(Activity activity,int type)
	{
		List<View> view_list=new ArrayList<View>();
		switch(type)
		{
		case 0://代付款
			view_list.add(createWaitPayButton(activity, 0));//付款按钮
			break;
		case 1://申请退款 确认提货
			view_list.add(createWaitPayButton(activity, 3));//申请退款
			view_list.add(createWaitPayButton(activity, 2));// 确认提货
			break;
		case 15://确认提货  申请退款
		case 16:
			view_list.add(createWaitPayButton(activity, 2));//确认提货
			view_list.add(createWaitPayButton(activity, 3));//申请退款
			break;
		//申请退款
		case 3:
			view_list.add(createWaitPayButton(activity, 1));//撤销退款
			//撤销退款
			break;
		}
		
		return view_list;
		
		
	}
	
	/***
	 * 创建操作按钮
	 * **/
	static View createWaitPayButton(Activity activity,int type)
	{
		View v=activity.getLayoutInflater().inflate(R.layout.button_layout, null);
		Button btn=(Button)v.findViewById(R.id.baijia_orderdetails_sqtk_button);
		String str="";
		int back_resource_id=-1;
		switch(type)
		{
		case 0:
			str=WAITPAY;
			back_resource_id=R.drawable.yeollow_background;
			break;
		case 1:
			str=CANCELPAY;
			back_resource_id=R.drawable.applyrefund_background;
			break;
		case 2:
			str=QUERENTIHUO;
			back_resource_id=R.drawable.yeollow_background;
			break;
		case 3:
			str=SHENQINGTUIKUAN;
			back_resource_id=R.drawable.applyrefund_background;
			break;
		}
		btn.setText(str);
		btn.setBackgroundResource(back_resource_id);
		return v;
	}
}
