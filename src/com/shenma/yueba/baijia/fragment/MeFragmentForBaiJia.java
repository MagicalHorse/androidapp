package com.shenma.yueba.baijia.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.activity.BuyerCertificationActivity1;
import com.shenma.yueba.baijia.activity.MyCollectionActivity;
import com.shenma.yueba.baijia.activity.UserConfigActivity;
import com.shenma.yueba.baijia.adapter.MyFragmentPagerAdapter;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.SharedUtil;
import com.shenma.yueba.yangjia.activity.MainActivityForYangJia;

/**
 * 败家--个人账户
 * 
 * @author a
 * 
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MeFragmentForBaiJia extends BaseFragment implements OnClickListener {
	private View view;
	private ImageView iv_setting;
	private ImageView iv_icon;
	private TextView tv_nickname;
	private TextView tv_style;
	private TextView tv_attention_count;
	private TextView tv_attention_title;
	private TextView tv_fans_count;
	private TextView tv_fans_title;
	private TextView tv_collection_count;
	private TextView tv_collection_title;
	private TextView tv_all_order;
	private TextView tv_waiting_for_send;
	private TextView tv_waiting_for_recieve;
	private TextView tv_pick_by_myself;
	private TextView tv_will_yangjia;//我要养家
	private TextView tv_my_collection;//我的收藏
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i("CircleFragment", "oncreate");
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("CircleFragment", "oncreateView");

		if (view == null) {
			initViews(inflater);
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;
	}


	/**
	 * 初始化view
	 */
	private void initViews(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.me_fragment_for_baijia, null);
		iv_setting = (ImageView) view.findViewById(R.id.iv_setting);
		iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
		tv_nickname = (TextView) view.findViewById(R.id.tv_nickname);
		tv_style = (TextView) view.findViewById(R.id.tv_style);
		tv_attention_count = (TextView) view.findViewById(R.id.tv_attention_count);
		tv_attention_title = (TextView) view.findViewById(R.id.tv_attention_title);
		tv_fans_count = (TextView) view.findViewById(R.id.tv_fans_count);
		tv_fans_title = (TextView) view.findViewById(R.id.tv_fans_title);
		tv_collection_count = (TextView) view.findViewById(R.id.tv_collection_count);
		tv_collection_title = (TextView) view.findViewById(R.id.tv_collection_title);
		tv_all_order = (TextView) view.findViewById(R.id.tv_all_order);
		tv_waiting_for_send = (TextView) view.findViewById(R.id.tv_waiting_for_send);
		tv_waiting_for_recieve = (TextView) view.findViewById(R.id.tv_waiting_for_recieve);
		tv_pick_by_myself = (TextView) view.findViewById(R.id.tv_pick_by_myself);
		tv_my_collection = (TextView) view.findViewById(R.id.tv_my_collection);
		tv_will_yangjia = (TextView) view.findViewById(R.id.tv_will_yangjia);
		iv_setting.setOnClickListener(this);
		tv_my_collection.setOnClickListener(this);
		tv_all_order.setOnClickListener(this);
		tv_waiting_for_send.setOnClickListener(this);
		tv_waiting_for_recieve.setOnClickListener(this);
		tv_pick_by_myself.setOnClickListener(this);
		tv_will_yangjia.setOnClickListener(this);
		/*FontManager.changeFonts(getActivity(), tv_nickname, tv_style,tv_attention_count,
				tv_attention_title,tv_fans_count,tv_fans_title,tv_collection_count,
				tv_collection_title,tv_all_order,tv_waiting_for_send,
				tv_waiting_for_recieve,tv_pick_by_myself,tv_will_yangjia,tv_my_collection);*/
	}

	@Override
	public void onResume() {
		Log.i("CircleFragment", "onResume");
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_setting://设置
			Intent userConfigIntent = new Intent(getActivity(),UserConfigActivity.class);
			startActivity(userConfigIntent);
			break;
		case R.id.tv_all_order://全部
			break;
		case R.id.tv_waiting_for_send://待发货
			break;
		case R.id.tv_waiting_for_recieve://待收货
			break;
		case R.id.tv_pick_by_myself://自提
			break;
		case R.id.tv_buyer://申请买手或者我是买手
			break;
		case R.id.tv_will_yangjia://我要养家
			/**
			 * -2表示还没有申请过认证买手  ，-1表示身亲被拒绝  ，0表示正在申请中，1表示申请通过
			 */
			if("0".equals(SharedUtil.getAuditStatus(getActivity()))){//正在审核，不可再次点击审核
				Toast.makeText(getActivity(), "申请审核中，请耐心等待...", Toast.LENGTH_LONG).show();
			}else if("-2".equals(SharedUtil.getAuditStatus(getActivity()))){//还未审核，可以进入
				Intent buyerCertificaitonIntent = new Intent(getActivity(),BuyerCertificationActivity1.class);
				startActivity(buyerCertificaitonIntent);
			}else if("-1".equals(SharedUtil.getAuditStatus(getActivity()))){
				Toast.makeText(getActivity(), "您的审核已被拒绝，请三天后重试...", Toast.LENGTH_LONG).show();
			}else if("1".equals(SharedUtil.getAuditStatus(getActivity()))){
				startActivity(new Intent(getActivity(), MainActivityForYangJia.class));
			}
			break;
		case R.id.tv_my_collection://我的收藏
			Intent intent3 = new Intent(getActivity(),MyCollectionActivity.class);
			startActivity(intent3);
			break;
		default:
			break;
		}

	}
}
