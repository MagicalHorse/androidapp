package com.shenma.yueba.yangjia.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.fragment.BaseFragment;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.yangjia.activity.BoradRewardActivity;
import com.shenma.yueba.yangjia.activity.OpenRewardActivity;
import com.shenma.yueba.yangjia.activity.OrderRewardActivity;

/**
 * 红榜奖励
 * 
 * @author a
 */
public class TaskRewardFragment extends BaseFragment implements
		OnClickListener {

	private View view;
	private TextView tv_top_title;
	private TextView tv_open_reward;
	private TextView tv_progress_open_title;
	private TextView tv_open_retain;
	private TextView tv_open_had_finished;
	private TextView tv_board_reward;
	private TextView tv_progress_board_title;
	private TextView tv_board_info;
	private TextView tv_order_reward;
	private TextView tv_progress_order_title;
	private TextView tv_order_retain;
	private TextView tv_order_had_finished;
	private LinearLayout ll_open_reward,ll_board_reward,ll_order_reward;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			initView(inflater);
		}
		// 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
//		getIndexInfo(true);//联网获取数据
		return view;
	}

	private void initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.task_reward, null);
		tv_top_title = (TextView) view.findViewById(R.id.tv_top_title);
		tv_top_title.setVisibility(View.VISIBLE);
		tv_top_title.setText("任务奖励");
		
		tv_open_reward = (TextView) view.findViewById(R.id.tv_open_reward);
		tv_progress_open_title = (TextView) view.findViewById(R.id.tv_progress_open_title);
		
		tv_open_retain = (TextView) view.findViewById(R.id.tv_open_retain);
		tv_open_had_finished = (TextView) view.findViewById(R.id.tv_open_had_finished);
		
		tv_board_reward = (TextView) view.findViewById(R.id.tv_board_reward);
		tv_progress_board_title = (TextView) view.findViewById(R.id.tv_progress_board_title);
		tv_board_info = (TextView) view.findViewById(R.id.tv_board_info);
		
		tv_order_reward = (TextView) view.findViewById(R.id.tv_order_reward);
		tv_progress_order_title = (TextView) view.findViewById(R.id.tv_progress_order_title);
		tv_order_retain = (TextView) view.findViewById(R.id.tv_order_retain);
		tv_order_had_finished = (TextView) view.findViewById(R.id.tv_order_had_finished);
		
		ll_open_reward = (LinearLayout) view.findViewById(R.id.ll_open_reward);
		ll_board_reward = (LinearLayout) view.findViewById(R.id.ll_board_reward);
		ll_order_reward = (LinearLayout) view.findViewById(R.id.ll_order_reward);
		
		ll_open_reward.setOnClickListener(this);
		ll_board_reward.setOnClickListener(this);
		ll_order_reward.setOnClickListener(this);
		
		FontManager.changeFonts(getActivity(), tv_top_title, tv_open_reward,
				tv_progress_open_title, tv_open_retain,
				tv_open_had_finished, tv_board_reward,
				tv_progress_board_title, tv_board_info, tv_order_reward,
				tv_progress_order_title, tv_order_retain, tv_order_had_finished
			);
	}

	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_open_reward://查看二維碼大图
			Intent openIntent = new Intent(getActivity(), OpenRewardActivity.class);
			startActivity(openIntent);
			break;
		case R.id.ll_board_reward:// 红榜奖励
			Intent intent = new Intent(getActivity(), BoradRewardActivity.class);
			startActivity(intent);
			break;
		case R.id.ll_order_reward:// 冲单奖励
			Intent orderIntent = new Intent(getActivity(), OrderRewardActivity.class);
			startActivity(orderIntent);
			break;
		default:
			break;
		}

	}

	
	
//	/**
//	 * 联网获取首页信息
//	 */
//	public void getIndexInfo(boolean showProgress){
//		HttpControl httpControl=new HttpControl();
//		httpControl.getBuyerIndexInfo(new HttpCallBackInterface() {
//			
//			@Override
//			public void http_Success(Object obj) {
//				mPullRefreshScrollView.onRefreshComplete();
//				BuyerIndexInfoBean bean = (BuyerIndexInfoBean) obj;
//				BuyerIndexInfo data = bean.getData();
//				
//				
//				
//				String codeUrl = data.getBarcode();
//				String shopName = data.getShopname();
//				Goodsamount goodsamount = data.getGoodsamount();
//				tv_today_huokuan_money.setText(ToolsUtil.nullToString(goodsamount.getTodaygoodsamount()));
//				tv_all_huokuan_money.setText(ToolsUtil.nullToString(goodsamount.getTotalgoodsamount()));
//				
//				bitmapUtils.display(iv_qr_code, codeUrl);
//				urlList.add(codeUrl);
//				tv_qr_name.setText(ToolsUtil.nullToString(shopName));
//				
//				//处理返回来的数据
//				Product product = data.getProduct();//商品管理
//				tv_products_online_count.setText(ToolsUtil.nullToString(product!=null?product.getOnlineCount():""));
//				tv_products_offlining_count.setText(ToolsUtil.nullToString(product!=null?product.getSoonDownCount():""));
//				
//				Favorite favorite = data.getFavorite();//社交管理
//				tv_my_society_count.setText(ToolsUtil.nullToString(favorite!=null?favorite.getFavoritecount():""));
//				tv_my_circle_count.setText(ToolsUtil.nullToString(favorite!=null?favorite.getGroupcount():""));
//				
//				Order order= data.getOrder();//销售管理
//				tv_today_sales_count.setText(ToolsUtil.nullToString(order!=null?order.getTodayorder():""));
//				tv_all_sales_count.setText(ToolsUtil.nullToString(order!=null?order.getAllorder():""));
//				
//				income = data.getIncome();
//				tv_today_earnings_money.setText(ToolsUtil.nullToString(income!=null?income.getToday_income():""));
//				tv_all_earnings_money.setText(ToolsUtil.nullToString(income!=null?income.getTotal_income():""));
//				
//			}
//			
//			@Override
//			public void http_Fails(int error, String msg) {
//				Toast.makeText(getActivity(), msg, 1000).show();
//				
//			}
//		}, getActivity(), showProgress, true);
//		
//		
//		}
	}
