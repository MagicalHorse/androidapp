package com.shenma.yueba.yangjia.fragment;

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

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.MainActivityForBaiJia;
import com.shenma.yueba.baijia.activity.UserConfigActivity;
import com.shenma.yueba.baijia.adapter.CircleFragmentPagerAdapter;
import com.shenma.yueba.baijia.fragment.BaseFragment;
import com.shenma.yueba.util.CustomProgressDialog;
import com.shenma.yueba.util.DialogUtils;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.yangjia.activity.BuyerIndentificationActivity;
import com.shenma.yueba.yangjia.activity.IdentificationBuyerListActivity;
import com.shenma.yueba.yangjia.activity.MainActivityForYangJia;

/**
 * 败家--个人账户
 * 
 * @author a
 * 
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MeFragmentForYangJia extends BaseFragment implements
		OnClickListener {
	private View view;
	private ImageView iv_setting;
	private ImageView iv_icon;
	private TextView tv_nickname;
	private TextView tv_grade;
	private TextView tv_store;
	private TextView tv_store_introduce;
	private TextView tv_invite_buyer;
	private TextView tv_to_baijia;

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
		view = inflater.inflate(R.layout.me_fragment_for_yangjia, null);
		iv_setting = (ImageView) view.findViewById(R.id.iv_setting);
		iv_icon = (ImageView) view.findViewById(R.id.iv_icon);

		tv_nickname = (TextView) view.findViewById(R.id.tv_nickname);
		tv_grade = (TextView) view.findViewById(R.id.tv_grade);
		tv_store = (TextView) view.findViewById(R.id.tv_store);
		tv_store_introduce = (TextView) view
				.findViewById(R.id.tv_store_introduce);
		tv_invite_buyer = (TextView) view.findViewById(R.id.tv_invite_buyer);
		tv_to_baijia = (TextView) view.findViewById(R.id.tv_to_baijia);

		iv_setting.setOnClickListener(this);
		tv_store.setOnClickListener(this);
		tv_store_introduce.setOnClickListener(this);
		tv_invite_buyer.setOnClickListener(this);
		tv_to_baijia.setOnClickListener(this);

		FontManager.changeFonts(getActivity(), tv_nickname, tv_grade, tv_store,
				tv_store_introduce, tv_invite_buyer, tv_to_baijia);
	}

	@Override
	public void onResume() {
		Log.i("CircleFragment", "onResume");
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_setting:// 设置
			// Intent userConfigIntent = new
			// Intent(getActivity(),UserConfigActivity.class);
			// startActivity(userConfigIntent);
			break;
		case R.id.tv_store:// 店铺首页
			break;
		case R.id.tv_store_introduce:// 店铺说明
			Intent identificationBuyerIntent = new Intent(getActivity(),
					BuyerIndentificationActivity.class);
			startActivity(identificationBuyerIntent);

			break;
		case R.id.tv_invite_buyer:// 邀请买手
			break;
		case R.id.tv_to_baijia:// 我要败家
			CustomProgressDialog dialog = new CustomProgressDialog(
					getActivity());
			dialog.show();
			MyApplication.getInstance().removeAllActivity();
			Intent intent = new Intent(getActivity(),
					MainActivityForBaiJia.class);
			startActivity(intent);
			dialog.dismiss();
			break;
		default:
			break;
		}

	}
}
