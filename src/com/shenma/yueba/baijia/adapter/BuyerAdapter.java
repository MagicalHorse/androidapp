package com.shenma.yueba.baijia.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenma.yueba.ChatActivity;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.ApproveBuyerDetailsActivity;
import com.shenma.yueba.baijia.activity.ShopMainActivity;
import com.shenma.yueba.baijia.modle.BaseRequest;
import com.shenma.yueba.baijia.modle.LikeUsersInfoBean;
import com.shenma.yueba.baijia.modle.ProductPicInfoBean;
import com.shenma.yueba.baijia.modle.ProductTagsInfoBean;
import com.shenma.yueba.baijia.modle.ProductsInfoBean;
import com.shenma.yueba.baijia.modle.UsersInfoBean;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ShareUtil;
import com.shenma.yueba.util.ShareUtil.ShareListener;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.MyGridView;
import com.shenma.yueba.view.RoundImageView;
import com.shenma.yueba.view.TagImageView;

/**
 * @author gyj
 * @version 创建时间：2015-6-11 上午10:55:13 程序的简单说明
 */

public class BuyerAdapter extends BaseAdapter {
	List<ProductsInfoBean> Products = new ArrayList<ProductsInfoBean>();
	Activity activity;
	HttpControl httpContril = new HttpControl();

	public BuyerAdapter(List<ProductsInfoBean> Products, Activity activity) {
		this.Products = Products;
		this.activity = activity;
	}

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
			convertView = activity.getLayoutInflater().inflate(
					R.layout.buyersteetfragment_item, null);
			holder.v = convertView;
			holder.customImage = (RoundImageView) convertView
					.findViewById(R.id.baijia_tab1_item_icon_imageview);
			setOnclickListener(holder.customImage);
			holder.baijia_tab1_item_productname_textview = (TextView) convertView
					.findViewById(R.id.baijia_tab1_item_productname_textview);
			holder.baijia_tab1_item_productaddress_textview = (TextView) convertView
					.findViewById(R.id.baijia_tab1_item_productaddress_textview);
			holder.baijia_tab1_item_time_textview = (TextView) convertView
					.findViewById(R.id.baijia_tab1_item_time_textview);
			holder.baijia_tab1_item_productcontent_imageview = (ImageView) convertView
					.findViewById(R.id.baijia_tab1_item_productcontent_imageview);
			holder.baijia_tab1_item_productcontent_tagimageview=(TagImageView)convertView.findViewById(R.id.baijia_tab1_item_productcontent_tagimageview);
			DisplayMetrics displayMetrics = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay()
					.getMetrics(displayMetrics);
			int height = displayMetrics.widthPixels;
			holder.baijia_tab1_item_productcontent_imageview.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height));
			holder.baijia_tab1_item_productcontent_tagimageview.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height));
			
			
			
			setOnclickListener(holder.baijia_tab1_item_productcontent_imageview);

			holder.buyersteetfragmeng_item_price_textview = (TextView) convertView
					.findViewById(R.id.buyersteetfragmeng_item_price_textview);
			holder.tv_count = (TextView) convertView
					.findViewById(R.id.tv_count);
			setOnclickListener(holder.tv_count);
			holder.buyersteetfragmeng_item_desc_textview = (TextView) convertView
					.findViewById(R.id.buyersteetfragmeng_item_desc_textview);
			holder.buyersteetfragmeng_item_siliao_button = (TextView) convertView
					.findViewById(R.id.buyersteetfragmeng_item_siliao_button);
			setOnclickListener(holder.buyersteetfragmeng_item_siliao_button);
			holder.buyersteetfragment_item_footer_linearlyout_content_textview = (TextView) convertView
					.findViewById(R.id.buyersteetfragment_item_footer_linearlyout_content_textview);

			holder.buyersteetfragmeng_item_share_button = (TextView) convertView
					.findViewById(R.id.buyersteetfragmeng_item_share_button);
			setOnclickListener(holder.buyersteetfragmeng_item_share_button);
			convertView.setTag(holder);
            
			holder.ll_attentionpeople_contener = (LinearLayout) convertView
					.findViewById(R.id.ll_attentionpeople_contener);

			// holder.approvebuyerdetails_attentionvalue_gridview=(MyGridView)convertView.findViewById(R.id.approvebuyerdetails_attentionvalue_gridview);
			// 更改字体
			FontManager
					.changeFonts(
							activity,
							holder.baijia_tab1_item_productname_textview,
							holder.baijia_tab1_item_productaddress_textview,
							holder.baijia_tab1_item_time_textview,
							holder.buyersteetfragmeng_item_price_textview,
							holder.buyersteetfragmeng_item_desc_textview,
							holder.buyersteetfragmeng_item_share_button,
							holder.buyersteetfragmeng_item_siliao_button,
							holder.tv_count,
							holder.buyersteetfragment_item_footer_linearlyout_content_textview);

		} else {
			holder = (Holder) convertView.getTag();

		}
		LayoutParams textParams = holder.tv_count.getLayoutParams();
		textParams.width = ToolsUtil.getDisplayWidth(activity) / 7;
		holder.tv_count.setLayoutParams(textParams);
		int count = Products.get(position).getLikeUsers().getCount();
		holder.tv_count.setText("" + count);

		if (Products != null && Products.get(position) != null
				&& Products.get(position).getLikeUsers() != null
				&& Products.get(position).getLikeUsers().getUsers() != null) {
			final List<UsersInfoBean> users = Products.get(position)
					.getLikeUsers().getUsers();
			holder.ll_attentionpeople_contener.removeAllViews();
			for (int i = 0; i < users.size(); i++) {
				RoundImageView riv = new RoundImageView(activity);
				int ll_with = ToolsUtil.getDisplayWidth(activity) / 7 * 6;
				int viewWith = (ll_with / 8);
				LayoutParams params = new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				params.width = viewWith;
				params.height = viewWith;
				riv.setLayoutParams(params);
				if (i != 8) {
					MyApplication.getInstance().getImageLoader()
							.displayImage(users.get(i).getLogo(), riv);
				} else {
					riv.setBackgroundResource(R.drawable.test003);
				}
				riv.setTag(users.get(i).getUserId());
				riv.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (((Integer)v.getTag()) <= 0) {
							return;
						}
						Intent intent = new Intent(activity,
								ShopMainActivity.class);
						intent.putExtra("DATA", (Integer)v.getTag());
						activity.startActivity(intent);
					}
				});

				holder.ll_attentionpeople_contener.addView(riv);
			}
		}
		initValue(holder, Products.get(position));
		return convertView;
	}

	class Holder {
		View v;
		RoundImageView customImage;
		TextView baijia_tab1_item_productname_textview // 商品名称
				,
				baijia_tab1_item_productaddress_textview// 地址
				,
				baijia_tab1_item_time_textview// 时间
				,
				buyersteetfragmeng_item_price_textview,
				buyersteetfragmeng_item_desc_textview// 描述
				, tv_count// 关注度
				;// 价格
		// 商品描述
		ImageView baijia_tab1_item_productcontent_imageview;
		// 私聊 与 分享按钮
		TextView buyersteetfragmeng_item_siliao_button,
				buyersteetfragmeng_item_share_button;
		// 关注人列表
		MyGridView approvebuyerdetails_attentionvalue_gridview;
		// 广告图片
		LinearLayout
				ll_attentionpeople_contener;
		// 内容
		TextView buyersteetfragment_item_footer_linearlyout_content_textview;
		TagImageView baijia_tab1_item_productcontent_tagimageview;

	}

	/****
	 * 加载数据
	 * **/

	void initValue(Holder holder, ProductsInfoBean productsInfoBean) {
		holder.baijia_tab1_item_productcontent_tagimageview.removeAllViews();
		List<ProductTagsInfoBean> tags_list=productsInfoBean.getProductPic().getTags();
		if(tags_list!=null && tags_list.size()>0)
		{
		   for(int i=0;i<tags_list.size();i++)
		   {
			   ProductTagsInfoBean productTagsInfoBean= tags_list.get(i);
			   DisplayMetrics displayMetrics = new DisplayMetrics();
				activity.getWindowManager().getDefaultDisplay()
						.getMetrics(displayMetrics);
				int height = displayMetrics.widthPixels;
			  int tagx= (int)(productTagsInfoBean.getPosX()*height);
			  int tagy=(int)(productTagsInfoBean.getPosY()*height);
			  Log.i("TAG", "----->>tagx:"+tagx+"  tagy:"+tagy);
			  holder.baijia_tab1_item_productcontent_tagimageview.addTextTagCanNotMove(ToolsUtil.nullToString(productTagsInfoBean.getTagName()), tagx, tagy);
		   }
		}
		
		
		// 私聊
		holder.buyersteetfragmeng_item_siliao_button.setTag(productsInfoBean);

		// 分享
		holder.buyersteetfragmeng_item_share_button.setTag(productsInfoBean);

		// 商品名称
		holder.baijia_tab1_item_productname_textview.setText(productsInfoBean
				.getBuyerName());
		// 地址
		holder.baijia_tab1_item_productaddress_textview
				.setText(productsInfoBean.getBuyerAddress());
		// 商品价格
		holder.buyersteetfragmeng_item_price_textview.setText("￥"
				+ Double.toString(productsInfoBean.getPrice()));
		// 商品的图片地址
		String url = productsInfoBean.getBuyerLogo() + "640x0.jpg";
		// 买家头像
		holder.customImage.setTag(productsInfoBean.getBuyerid());
		holder.customImage.setImageResource(R.drawable.default_pic);
		// 下载买家头像
		initPic(productsInfoBean.getBuyerLogo(), holder.customImage,
				R.drawable.default_pic);
		ProductPicInfoBean productPicInfoBean = productsInfoBean
				.getProductPic();
		if (productPicInfoBean == null) {
			productPicInfoBean = new ProductPicInfoBean();
		}
		// 发布时间
		holder.baijia_tab1_item_time_textview.setText(ToolsUtil
				.nullToString(productsInfoBean.getCreateTime()));
		// 产品描述
		holder.buyersteetfragmeng_item_desc_textview.setText(productsInfoBean
				.getProductName());
		// 商品内容图片
		holder.baijia_tab1_item_productcontent_imageview
				.setTag(productsInfoBean.getProductId());
		if (productsInfoBean.getPromotion() == null
				|| !productsInfoBean.getPromotion().isIsShow()) {
			holder.buyersteetfragment_item_footer_linearlyout_content_textview
					.setVisibility(View.GONE);

		} else {
			holder.buyersteetfragment_item_footer_linearlyout_content_textview
					.setVisibility(View.VISIBLE);
			holder.buyersteetfragment_item_footer_linearlyout_content_textview
					.setText(ToolsUtil.nullToString(productsInfoBean
							.getPromotion().getDescriptionText())
							+ " "
							+ productsInfoBean.getPromotion().getTipText());
		}
		String pic_name = productPicInfoBean.getName();
		Log.i("TAG", "pic_name=" + pic_name);
		// 加载商品图片
		initPic(ToolsUtil.getImage(ToolsUtil.nullToString(pic_name), 640, 0),
				holder.baijia_tab1_item_productcontent_imageview,
				R.drawable.default_pic);
		LikeUsersInfoBean userinfo = productsInfoBean.getLikeUsers();
		if (userinfo != null && userinfo.getUsers() != null) {
			holder.tv_count.setTag(productsInfoBean);
			holder.tv_count.setSelected(userinfo.isIsLike());
			holder.tv_count.setText(userinfo.getCount() + "");
			// holder.approvebuyerdetails_attentionvalue_gridview.setAdapter(new
			// UserIconAdapter(userinfo.getUsers(),activity,holder.approvebuyerdetails_attentionvalue_gridview));
		} else {
			holder.tv_count.setText("0");
			// holder.approvebuyerdetails_attentionvalue_gridview.setAdapter(new
			// UserIconAdapter(userinfo.getUsers(),activity,holder.approvebuyerdetails_attentionvalue_gridview));
		}

	}

	/****
	 * 设置点击事件
	 * **/
	void setOnclickListener(View v) {
		v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.baijia_tab1_item_productcontent_imageview:
					if (v.getTag() != null && v.getTag() instanceof Integer) {
						Integer _id = (Integer) v.getTag();
						Intent intent = new Intent(activity,
								ApproveBuyerDetailsActivity.class);
						intent.putExtra("productID", _id);
						activity.startActivity(intent);
					}
					break;
				case R.id.baijia_tab1_item_icon_imageview:// 商铺详细
					if (v.getTag() == null || !(v.getTag() instanceof Integer)) {
						return;
					}
					Intent intent = new Intent(activity, ShopMainActivity.class);
					intent.putExtra("DATA", (Integer) v.getTag());
					activity.startActivity(intent);
					break;
				case R.id.tv_count:// 商品喜欢/取消喜欢
					if (!MyApplication.getInstance().isUserLogin(activity)) {
						return;
					}
					if (v.getTag() != null
							|| v.getTag() instanceof ProductsInfoBean) {
						ProductsInfoBean bean = (ProductsInfoBean) v.getTag();
						if (bean.getLikeUsers() != null) {
							if (bean.getLikeUsers().isIsLike()) {
								setLikeOrUnLike(bean, 0, (TextView) v);
							} else {
								setLikeOrUnLike(bean, 1, (TextView) v);
							}
						}

					}
					break;
				case R.id.buyersteetfragmeng_item_siliao_button:// 私聊
					if (!MyApplication.getInstance().isUserLogin(activity)) {
						return;
					}
					if (v.getTag() != null
							&& v.getTag() instanceof ProductsInfoBean) {
						ProductsInfoBean bean = (ProductsInfoBean) v.getTag();
						Intent intentsiliao = new Intent(activity,
								ChatActivity.class);
						intentsiliao.putExtra("Chat_Type",
								ChatActivity.chat_type_private);
						intentsiliao.putExtra("Chat_NAME", bean.getBuyerName());// 圈子名字
						intentsiliao.putExtra("toUser_id", bean.getBuyerid());
						activity.startActivity(intentsiliao);
					}

					break;
				case R.id.buyersteetfragmeng_item_share_button:// 分享
					if (!MyApplication.getInstance().isUserLogin(activity)) {
						return;
					}
					if (v.getTag() != null
							&& v.getTag() instanceof ProductsInfoBean) {
						ProductsInfoBean bean = (ProductsInfoBean) v.getTag();
						String content = bean.getProductName();
						String url = bean.getShareLink();
						String icon = ToolsUtil.getImage(ToolsUtil
								.nullToString(bean.getProductPic().getName()),
								320, 0);
						shareUrl(bean.getProductId(), content, url, icon);
					}

					break;
				}

			}
		});
	}

	/*****
	 * 设置喜欢 或取消喜欢
	 * 
	 * @param bean
	 *            ProductsInfoBean 商品对象
	 * @param Status
	 *            int 0表示取消喜欢 1表示喜欢
	 * @param v
	 *            TextView
	 * ***/
	void setLikeOrUnLike(final ProductsInfoBean bean, final int Status,
			final TextView v) {
		httpContril.setLike(bean.getProductId(), Status,
				new HttpCallBackInterface() {

					@Override
					public void http_Success(Object obj) {
						int count = bean.getLikeUsers().getCount();
						switch (Status) {
						case 0:
							v.setSelected(false);
							count--;
							if (count < 0) {
								count = 0;
							}
							bean.getLikeUsers().setIsLike(false);
							bean.getLikeUsers().setCount(count);
							v.setText(count + "");
							break;
						case 1:
							count++;
							v.setSelected(true);
							v.setText(count + "");
							bean.getLikeUsers().setIsLike(true);
							bean.getLikeUsers().setCount(count);
							break;
						}
					}

					@Override
					public void http_Fails(int error, String msg) {
						MyApplication.getInstance().showMessage(activity, msg);
					}
				}, activity);
	}

	/****
	 * 加载图片
	 * */
	void initPic(final String url, final ImageView iv, final int image) {
		Log.i("TAG", "URL:" + url);
		MyApplication
				.getInstance()
				.getImageLoader()
				.displayImage(url, iv,
						MyApplication.getInstance().getDisplayImageOptions());
	}

	/********
	 * 分享
	 * 
	 * @param content
	 *            String 内容提示
	 * @param url
	 *            String 链接地址
	 * @param icon
	 *            String 图片地址
	 * ****/
	void shareUrl(final int productid, String content, String url, String icon) {
		ShareUtil.shareAll(activity, "我市内容", url, icon, new ShareListener() {

			@Override
			public void sharedListener_sucess() {
				requestShared(productid);
			}

			@Override
			public void sharedListener_Fails(String msg) {
				MyApplication.getInstance().showMessage(activity, msg);
			}
		});
	}

	/*****
	 * 分享成功后 回调
	 * ****/
	void requestShared(int productid) {
		HttpControl httpControl = new HttpControl();
		httpControl.createProductShare(productid, false,
				new HttpCallBackInterface() {

					@Override
					public void http_Success(Object obj) {
						if (obj != null && obj instanceof BaseRequest) {
							MyApplication.getInstance().showMessage(activity,
									"分享成功");
						} else {
							MyApplication.getInstance().showMessage(activity,
									"分享失败");
						}
					}

					@Override
					public void http_Fails(int error, String msg) {
						MyApplication.getInstance().showMessage(activity, msg);
					}
				}, activity);
	}
}
