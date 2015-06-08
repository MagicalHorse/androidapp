package com.shenma.yueba.baijia.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnPullEventListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase.State;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.lidroid.xutils.BitmapUtils;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.ShopMainActivity;
import com.shenma.yueba.baijia.adapter.UserIconAdapter;
import com.shenma.yueba.baijia.modle.BannersInfoBean;
import com.shenma.yueba.baijia.modle.FragmentBean;
import com.shenma.yueba.baijia.modle.HomeProductListInfoBean;
import com.shenma.yueba.baijia.modle.ImageStringBean;
import com.shenma.yueba.baijia.modle.LikeUsersInfoBean;
import com.shenma.yueba.baijia.modle.ProductListInfoBean;
import com.shenma.yueba.baijia.modle.ProductPicInfoBean;
import com.shenma.yueba.baijia.modle.ProductsInfoBean;
import com.shenma.yueba.baijia.modle.RequestProductListInfoBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ListViewUtils;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.MyGridView;
import com.shenma.yueba.view.RoundImageView;

public class MyBuyerFragment extends Fragment {
	List<FragmentBean> fragment_list = new ArrayList<FragmentBean>();
	List<View> footer_list = new ArrayList<View>();
	FragmentManager fragmentManager;
	ListView baijia_contact_listview;
	View parentview;
	PullToRefreshScrollView pulltorefreshscrollview;
	LayoutInflater inflater;
	LinearLayout showloading_layout_view;
	Timer timer;
	HttpControl httpContril = new HttpControl();
	// 当前页
	int currpage = Constants.CURRPAGE_VALUE;
	// 每页显示的条数
	int pagesize = Constants.PAGESIZE_VALUE;
	int maxcount = 8;
	//是否显示进度条
	boolean ishow=false;
	List<BannersInfoBean> Banners = new ArrayList<BannersInfoBean>();
	// 商品信息列表
	List<ProductsInfoBean> Products = new ArrayList<ProductsInfoBean>();
	BitmapUtils bitmapUtils;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		if (parentview == null) {
			ishow=true;
			parentview = inflater.inflate(R.layout.mybuyer_home_layout, null);
			bitmapUtils = new BitmapUtils(getActivity());
			initPullView();
			initView(parentview);
			requestFalshData();
		}
		ViewGroup vp = (ViewGroup)parentview.getParent();
		if (vp != null) {
			vp.removeView(parentview);
		}
		return parentview;

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

	}

	void initPullView() {
		pulltorefreshscrollview = (PullToRefreshScrollView)parentview.findViewById(R.id.pulltorefreshscrollview);
		// 设置标签显示的内容
		// pulltorefreshscrollview.getLoadingLayoutProxy().setLastUpdatedLabel("lastUpdateLabel");
		pulltorefreshscrollview.getLoadingLayoutProxy().setPullLabel("下拉刷新");
		pulltorefreshscrollview.getLoadingLayoutProxy().setRefreshingLabel(
				"刷新中。。。");
		pulltorefreshscrollview.getLoadingLayoutProxy().setReleaseLabel("松开刷新");
		pulltorefreshscrollview.setMode(Mode.BOTH);
		pulltorefreshscrollview
				.setOnPullEventListener(new OnPullEventListener<ScrollView>() {

					@Override
					public void onPullEvent(
							PullToRefreshBase<ScrollView> refreshView,
							State state, Mode direction) {
						if (direction == Mode.PULL_FROM_START) {
							pulltorefreshscrollview.getLoadingLayoutProxy()
									.setPullLabel("上拉刷新");
							pulltorefreshscrollview.getLoadingLayoutProxy()
									.setRefreshingLabel("刷新中。。。");
							pulltorefreshscrollview.getLoadingLayoutProxy()
									.setReleaseLabel("松开刷新");
						} else if (direction == Mode.PULL_FROM_END) {
							pulltorefreshscrollview.getLoadingLayoutProxy()
									.setPullLabel("下拉加载");
							pulltorefreshscrollview.getLoadingLayoutProxy()
									.setRefreshingLabel("加载中。。。");
							pulltorefreshscrollview.getLoadingLayoutProxy()
									.setReleaseLabel("松开加载");
						}
					}
				});

		pulltorefreshscrollview.setOnRefreshListener(new OnRefreshListener2() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {

				requestFalshData();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				requestData();
			}
		});
	}

	void initView(View v) {
		showloading_layout_view = (LinearLayout) v
				.findViewById(R.id.showloading_layout_view);
		showloading_layout_view.setVisibility(View.GONE);
		baijia_contact_listview = (ListView) v
				.findViewById(R.id.baijia_contact_listview);
		baijia_contact_listview.setAdapter(baseAdapter);

		baijia_contact_listview
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Intent intent = new Intent(getActivity(),
								ShopMainActivity.class);
						startActivity(intent);
					}
				});
	}

	void getImageView(ImageStringBean bean) {
		  bean.getIv().setTag(bean.getBean());
          initPic(ToolsUtil.getImage(bean.getBean().getPic(), 640, 0), bean.getIv(), R.drawable.default_pic);
	}

	BaseAdapter baseAdapter = new BaseAdapter() {

		@Override
		public int getCount() {

			return Products.size();
		}

		@Override
		public Object getItem(int position) {

			return null;
		}

		@Override
		public long getItemId(int position) {

			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			Holder holder;
			if (convertView == null) {
				holder = new Holder();
				convertView = inflater.inflate(
						R.layout.buyersteetfragment_item, null);
				holder.v = convertView;
				holder.customImage = (RoundImageView) convertView
						.findViewById(R.id.baijia_tab1_item_icon_imageview);
				holder.baijia_tab1_item_productname_textview = (TextView)convertView
						.findViewById(R.id.baijia_tab1_item_productname_textview);
				holder.baijia_tab1_item_productaddress_textview = (TextView) convertView
						.findViewById(R.id.baijia_tab1_item_productaddress_textview);
				holder.baijia_tab1_item_time_textview = (TextView) convertView
						.findViewById(R.id.baijia_tab1_item_time_textview);
				holder.baijia_tab1_item_productcontent_imageview = (ImageView) convertView
						.findViewById(R.id.baijia_tab1_item_productcontent_imageview);
				DisplayMetrics displayMetrics=new DisplayMetrics();
				getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
				int height=(displayMetrics.widthPixels/2);
				holder.baijia_tab1_item_productcontent_imageview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
				
				holder.buyersteetfragmeng_item_price_textview = (TextView) convertView
						.findViewById(R.id.buyersteetfragmeng_item_price_textview);
				holder.approvebuyerdetails_attention_textview=(TextView)convertView.findViewById(R.id.approvebuyerdetails_attention_textview);
				holder.buyersteetfragmeng_item_desc_textview=(TextView) convertView
						.findViewById(R.id.buyersteetfragmeng_item_desc_textview);
				holder.buyersteetfragmeng_item_siliao_button = (Button) convertView
						.findViewById(R.id.buyersteetfragmeng_item_siliao_button);
				//私聊按键
				holder.buyersteetfragmeng_item_siliao_button
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {

							}
						});
				
				holder.buyersteetfragmeng_item_share_button = (Button) convertView
						.findViewById(R.id.buyersteetfragmeng_item_share_button);
				//分享按键
				holder.buyersteetfragmeng_item_share_button
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								

							}
						});

				convertView.setTag(holder);
				holder.approvebuyerdetails_attentionvalue_gridview=(MyGridView)convertView.findViewById(R.id.approvebuyerdetails_attentionvalue_gridview);
			} else {
				holder = (Holder) convertView.getTag();

			}
			
			initValue(holder, Products.get(position));
			return convertView;
		}

	};

	/****
	 * 加载数据
	 * **/
	
	void initValue(Holder holder,ProductsInfoBean productsInfoBean)
	{
		holder.buyersteetfragmeng_item_siliao_button
				.setTag(productsInfoBean);
		holder.buyersteetfragmeng_item_share_button
				.setTag(productsInfoBean);

		holder.baijia_tab1_item_productname_textview
				.setText(productsInfoBean.getBuyerName());
		holder.baijia_tab1_item_productaddress_textview
				.setText(productsInfoBean.getBuyerAddress());
		holder.buyersteetfragmeng_item_price_textview.setText("￥"+Double
				.toString(productsInfoBean.getPrice()));
		String url=productsInfoBean.getBuyerLogo()+"640x0.jpg";
		holder.customImage.setTag(productsInfoBean.getBuyerLogo());
		holder.customImage.setImageResource(R.drawable.default_pic);
		initPic(productsInfoBean.getBuyerLogo(), holder.customImage,R.drawable.test002);
		ProductPicInfoBean productPicInfoBean = productsInfoBean.getProductPic();
		String picturd_src = productPicInfoBean.getName();
		holder.baijia_tab1_item_time_textview.setText(ToolsUtil.getTime(productsInfoBean.getCreateTime()));
		holder.buyersteetfragmeng_item_desc_textview.setText(productsInfoBean.getProductName());
		//holder.baijia_tab1_item_productcontent_imageview.setImageResource(R.drawable.default_pic);
		holder.baijia_tab1_item_productcontent_imageview.setTag(productPicInfoBean);
		initPic(ToolsUtil.getImage(productPicInfoBean.getName(), 640, 0), holder.baijia_tab1_item_productcontent_imageview, R.drawable.default_pic);
		//ProductName
		FontManager.changeFonts(getActivity(), holder.baijia_tab1_item_productname_textview,holder.baijia_tab1_item_productaddress_textview,holder.baijia_tab1_item_time_textview,holder.buyersteetfragmeng_item_price_textview,holder.buyersteetfragmeng_item_desc_textview,holder.buyersteetfragmeng_item_share_button,holder.buyersteetfragmeng_item_siliao_button,holder.approvebuyerdetails_attention_textview);
		LikeUsersInfoBean userinfo=productsInfoBean.getLikeUsers();
		if(userinfo!=null && userinfo.getUsers()!=null && userinfo.getUsers().size()>0)
		{
			holder.approvebuyerdetails_attention_textview.setText(userinfo.getCount()+"");
			holder.approvebuyerdetails_attentionvalue_gridview.setAdapter(new UserIconAdapter(userinfo.getUsers(),getActivity(),holder.approvebuyerdetails_attentionvalue_gridview));
		}else
		{
			holder.approvebuyerdetails_attention_textview.setText("0");
			holder.approvebuyerdetails_attentionvalue_gridview.setAdapter(new UserIconAdapter(userinfo.getUsers(),getActivity(),holder.approvebuyerdetails_attentionvalue_gridview));
		}
		
	}

	class Holder {
		View v;
		RoundImageView customImage;
		TextView baijia_tab1_item_productname_textview // 商品名称
				,
				baijia_tab1_item_productaddress_textview// 地址
				, baijia_tab1_item_time_textview// 时间
				, buyersteetfragmeng_item_price_textview,
				buyersteetfragmeng_item_desc_textview//描述
				,approvebuyerdetails_attention_textview//关注度
				;// 价格
		// 商品描述
		ImageView baijia_tab1_item_productcontent_imageview;
		// 私聊 与 分享按钮
		Button buyersteetfragmeng_item_siliao_button,
				buyersteetfragmeng_item_share_button;
		// 关注人列表
		MyGridView approvebuyerdetails_attentionvalue_gridview;
		
	}

	/******
	 * 上啦加载数据
	 * ***/
	void requestData() {
		pulltorefreshscrollview.setRefreshing();
		sendRequestData(1);
	}

	/******
	 * 下拉刷新数据
	 * ***/
	void requestFalshData() {
		pulltorefreshscrollview.setRefreshing();
		currpage = 1;
		sendRequestData(0);

	}

	/******
	 * 与网络通信请求数据
	 * 
	 * @param type
	 *            int 0 刷新 1 加载
	 * ***/
	void sendRequestData(final int type) {
		httpContril.getProduceHomeListData(currpage, pagesize,new HttpCallBackInterface() {

					@Override
					public void http_Success(Object obj) {
						pulltorefreshscrollview.onRefreshComplete();
						if (obj != null
								&& obj instanceof RequestProductListInfoBean) {
							RequestProductListInfoBean bean = (RequestProductListInfoBean) obj;
							HomeProductListInfoBean data = bean.getData();
							if (data != null) {
								int totalPage = data.getTotalpaged();
								if (currpage >= totalPage) {
									pulltorefreshscrollview
											.setMode(Mode.PULL_FROM_START);
								} else {
									pulltorefreshscrollview.setMode(Mode.BOTH);
								}
								switch (type) {
								case 0:
									falshData(data);
									break;
								case 1:
									addData(data);
									break;
								}
							} else {
								MyApplication.getInstance().showMessage(
										getActivity(), "没有任何数据");
							}

						}
					}

					@Override
					public void http_Fails(int error, String msg) {
						pulltorefreshscrollview.onRefreshComplete();
						MyApplication.getInstance().showMessage(getActivity(),
								msg);
					}
				}, getActivity(),ishow,true);
	}

	/***
	 * 加载数据
	 * **/
	void addData(HomeProductListInfoBean data) {
		ishow=false;
		showloading_layout_view.setVisibility(View.GONE);
		ProductListInfoBean item = data.getItems();
		if(item.getProducts()!=null)
		{
			Products.addAll(item.getProducts());
		}
		baseAdapter.notifyDataSetChanged();
		ListViewUtils.setListViewHeightBasedOnChildren(baijia_contact_listview);
		pulltorefreshscrollview.onRefreshComplete();
	}

	/***
	 * 刷新viewpager数据
	 * ***/
	void falshData(HomeProductListInfoBean data) {
		ishow=false;
		showloading_layout_view.setVisibility(View.GONE);
		ProductListInfoBean item = data.getItems();
		Banners.clear();
		Products.clear();
		
		if(item.getProducts()!=null)
		{
			Products=item.getProducts();
		}
		ListViewUtils.setListViewHeightBasedOnChildren(baijia_contact_listview);
		pulltorefreshscrollview.onRefreshComplete();

	}
	
	/****
	 * 加载图片
	 * */
	void initPic(final String url, final ImageView iv, final int image) {
		Log.i("TAG", "URL:"+url);
		MyApplication.getInstance().getImageLoader().displayImage(url, iv, MyApplication.getInstance().getDisplayImageOptions());
	}
}
