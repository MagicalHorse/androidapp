package com.shenma.yueba.baijia.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.sax.StartElementListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenma.yueba.ChatActivity;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.ApproveBuyerDetailsActivity;
import com.shenma.yueba.baijia.activity.ShopMainActivity;
import com.shenma.yueba.baijia.modle.LikeUsersInfoBean;
import com.shenma.yueba.baijia.modle.ProductPicInfoBean;
import com.shenma.yueba.baijia.modle.ProductsInfoBean;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ShareUtil;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.MyGridView;
import com.shenma.yueba.view.RoundImageView;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-11 上午10:55:13  
 * 程序的简单说明  
 */

public class BuyerAdapter extends BaseAdapter{
	List<ProductsInfoBean> Products=new ArrayList<ProductsInfoBean>();
	Activity activity;
	HttpControl httpContril = new HttpControl();
	public BuyerAdapter(List<ProductsInfoBean> Products,Activity activity)
	{
		this.Products=Products;
		this.activity=activity;
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
			convertView = activity.getLayoutInflater().inflate(R.layout.buyersteetfragment_item, null);
			holder.v = convertView;
			holder.customImage = (RoundImageView) convertView
					.findViewById(R.id.baijia_tab1_item_icon_imageview);
			setOnclickListener(holder.customImage);
			holder.baijia_tab1_item_productname_textview = (TextView)convertView
					.findViewById(R.id.baijia_tab1_item_productname_textview);
			holder.baijia_tab1_item_productaddress_textview = (TextView) convertView
					.findViewById(R.id.baijia_tab1_item_productaddress_textview);
			holder.baijia_tab1_item_time_textview = (TextView) convertView
					.findViewById(R.id.baijia_tab1_item_time_textview);
			holder.baijia_tab1_item_productcontent_imageview = (ImageView) convertView
					.findViewById(R.id.baijia_tab1_item_productcontent_imageview);
			DisplayMetrics displayMetrics=new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
			int height=displayMetrics.widthPixels;
			holder.baijia_tab1_item_productcontent_imageview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
			setOnclickListener(holder.baijia_tab1_item_productcontent_imageview);
			
			holder.buyersteetfragmeng_item_price_textview = (TextView) convertView
					.findViewById(R.id.buyersteetfragmeng_item_price_textview);
			holder.approvebuyerdetails_attention_textview=(TextView)convertView.findViewById(R.id.approvebuyerdetails_attention_textview);
			setOnclickListener(holder.approvebuyerdetails_attention_textview);
			holder.buyersteetfragmeng_item_desc_textview=(TextView) convertView
					.findViewById(R.id.buyersteetfragmeng_item_desc_textview);
			holder.buyersteetfragmeng_item_siliao_button = (Button) convertView
					.findViewById(R.id.buyersteetfragmeng_item_siliao_button);
			setOnclickListener(holder.buyersteetfragmeng_item_siliao_button);
			//广告图片
			holder.buyersteetfragment_item_footer_linearlyout=(LinearLayout)convertView.findViewById(R.id.buyersteetfragment_item_footer_linearlyout);
            holder.buyersteetfragment_item_footer_linearlyout_content_textview=(TextView)convertView.findViewById(R.id.buyersteetfragment_item_footer_linearlyout_content_textview);
			
			holder.buyersteetfragmeng_item_share_button = (Button) convertView.findViewById(R.id.buyersteetfragmeng_item_share_button);
			setOnclickListener(holder.buyersteetfragmeng_item_share_button);
			convertView.setTag(holder);
			holder.approvebuyerdetails_attentionvalue_gridview=(MyGridView)convertView.findViewById(R.id.approvebuyerdetails_attentionvalue_gridview);
			//更改字体
			FontManager.changeFonts(activity, holder.baijia_tab1_item_productname_textview,holder.baijia_tab1_item_productaddress_textview,holder.baijia_tab1_item_time_textview,holder.buyersteetfragmeng_item_price_textview,holder.buyersteetfragmeng_item_desc_textview,holder.buyersteetfragmeng_item_share_button,holder.buyersteetfragmeng_item_siliao_button,holder.approvebuyerdetails_attention_textview,holder.buyersteetfragment_item_footer_linearlyout_content_textview);
			
		} else {
			holder = (Holder) convertView.getTag();

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
		//广告图片
		LinearLayout buyersteetfragment_item_footer_linearlyout;
		//内容
		TextView buyersteetfragment_item_footer_linearlyout_content_textview;
		
	}
	
	
	/****
	 * 加载数据
	 * **/
	
	void initValue(Holder holder,ProductsInfoBean productsInfoBean)
	{
		//私聊
		holder.buyersteetfragmeng_item_siliao_button.setTag(productsInfoBean.getBuyerid());
		
		//分享
		holder.buyersteetfragmeng_item_share_button.setTag(productsInfoBean);
        
		//商品名称
		holder.baijia_tab1_item_productname_textview.setText(productsInfoBean.getBuyerName());
		//地址
		holder.baijia_tab1_item_productaddress_textview.setText(productsInfoBean.getBuyerAddress());
		//商品价格
		holder.buyersteetfragmeng_item_price_textview.setText("￥"+Double.toString(productsInfoBean.getPrice()));
		//商品的图片地址
		String url=productsInfoBean.getBuyerLogo()+"640x0.jpg";
		//买家头像
		holder.customImage.setTag(productsInfoBean.getBuyerid());
		holder.customImage.setImageResource(R.drawable.default_pic);
		//下载买家头像
		initPic(productsInfoBean.getBuyerLogo(), holder.customImage,R.drawable.test002);
		ProductPicInfoBean productPicInfoBean = productsInfoBean.getProductPic();
		//发布时间
		holder.baijia_tab1_item_time_textview.setText(ToolsUtil.nullToString(productsInfoBean.getCreateTime()));
		//产品描述
		holder.buyersteetfragmeng_item_desc_textview.setText(productsInfoBean.getProductName());
		//商品内容图片
		holder.baijia_tab1_item_productcontent_imageview.setTag(productsInfoBean.getProductId());
		if(productsInfoBean.getPromotion()==null || !productsInfoBean.getPromotion().isIsShow())
		{
			holder.buyersteetfragment_item_footer_linearlyout.setVisibility(View.GONE);
			
		}else
		{
			holder.buyersteetfragment_item_footer_linearlyout.setVisibility(View.VISIBLE);
			holder.buyersteetfragment_item_footer_linearlyout_content_textview.setText(ToolsUtil.nullToString(productsInfoBean.getPromotion().getDescriptionText()));
		}
		
		//加载商品图片
		initPic(ToolsUtil.getImage(productPicInfoBean.getName(), 640, 0), holder.baijia_tab1_item_productcontent_imageview, R.drawable.default_pic);
		LikeUsersInfoBean userinfo=productsInfoBean.getLikeUsers();
		if(userinfo!=null && userinfo.getUsers()!=null)
		{
			holder.approvebuyerdetails_attention_textview.setTag(productsInfoBean);
			holder.approvebuyerdetails_attention_textview.setSelected(userinfo.isIsLike());
			
			holder.approvebuyerdetails_attention_textview.setText(userinfo.getCount()+"");
			holder.approvebuyerdetails_attentionvalue_gridview.setAdapter(new UserIconAdapter(userinfo.getUsers(),activity,holder.approvebuyerdetails_attentionvalue_gridview));
		}else
		{
			holder.approvebuyerdetails_attention_textview.setText("0");
			holder.approvebuyerdetails_attentionvalue_gridview.setAdapter(new UserIconAdapter(userinfo.getUsers(),activity,holder.approvebuyerdetails_attentionvalue_gridview));
		}
		
	}
	
	
	/****
	 * 设置点击事件
	 * **/
	void setOnclickListener(View v)
	{
		v.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				switch(v.getId())
				{
				case R.id.baijia_tab1_item_productcontent_imageview:
					if(v.getTag()!=null && v.getTag() instanceof Integer)
					{
						Integer _id =(Integer)v.getTag();
						Intent intent=new Intent(activity,ApproveBuyerDetailsActivity.class);
						intent.putExtra("productID", _id);
						activity.startActivity(intent);
					}
					break;
				case R.id.baijia_tab1_item_icon_imageview://商铺详细
					if(v.getTag()==null || !(v.getTag() instanceof Integer))
					{
						return;
					}
					Intent intent=new Intent(activity,ShopMainActivity.class);
					intent.putExtra("DATA",(Integer)v.getTag());
					activity.startActivity(intent);
					break;
				case R.id.approvebuyerdetails_attention_textview://商品喜欢/取消喜欢
					if(v.getTag()!=null || v.getTag() instanceof ProductsInfoBean)
					{
						ProductsInfoBean bean=(ProductsInfoBean)v.getTag();
						if(bean.getLikeUsers()!=null)
						{
							if(bean.getLikeUsers().isIsLike())
							{
								setLikeOrUnLike(bean, 0,(TextView)v);
							}else
							{
								setLikeOrUnLike(bean, 1,(TextView)v);
							}
						}
						
					}
				    break; 
				case R.id.buyersteetfragmeng_item_siliao_button://私聊
					Intent intentsiliao=new Intent(activity,ChatActivity.class);
					intentsiliao.putExtra("buyerId", (Integer)v.getTag());
					activity.startActivity(intentsiliao);
					break;
				case R.id.buyersteetfragmeng_item_share_button://分享
					if(v.getTag()!=null && v.getTag() instanceof ProductsInfoBean)
					{
						ProductsInfoBean bean=(ProductsInfoBean)v.getTag();
						String content="";
						String url="";
						String icon=ToolsUtil.getImage(ToolsUtil.nullToString(bean.getProductPic().getName()), 320, 0);
						shareUrl(content, url, icon);
					}
					
					break;
				}
				
			}
		});
	}
	
	
	/*****
	 * 设置喜欢 或取消喜欢
	 * @param bean  ProductsInfoBean 商品对象
	 * @param Status int 0表示取消喜欢   1表示喜欢
	 * @param v  TextView
	 * ***/
	void setLikeOrUnLike(final ProductsInfoBean bean,final int Status,final TextView v)
	{
		httpContril.setLike(bean.getProductId(), Status, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				int count=bean.getLikeUsers().getCount();
				switch(Status)
				{
				case 0:
					v.setSelected(false);
					count--;
					if(count<0)
					{
						count=0;
					}
					bean.getLikeUsers().setIsLike(false);
					bean.getLikeUsers().setCount(count);
					v.setText(count+"");
					break;
				case 1:
					count++;
					v.setSelected(true);
					v.setText(count+"");
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
		Log.i("TAG", "URL:"+url);
		MyApplication.getInstance().getImageLoader().displayImage(url, iv, MyApplication.getInstance().getDisplayImageOptions());
	}
	
	/********
	 *  分享
	 *  @param content String 内容提示
	 *  @param url String 链接地址
	 *  @param icon String 图片地址
	 * ****/
	void shareUrl(String content,String url,String icon)
	{
		ShareUtil.shareAll(activity, "我市内容", "我是url", "http://img3.3lian.com/2014/c2/61/d/17.jpg");
	}
}
