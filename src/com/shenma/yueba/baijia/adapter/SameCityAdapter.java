package com.shenma.yueba.baijia.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.ApproveBuyerDetailsActivity;
import com.shenma.yueba.baijia.modle.BrandCityWideInfo;
import com.shenma.yueba.baijia.modle.BrandListBean;
import com.shenma.yueba.baijia.modle.MyCircleBean;
import com.shenma.yueba.baijia.modle.SameCityBean;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.RoundImageView;

public class SameCityAdapter extends BaseAdapterWithUtil {
	private List<BrandCityWideInfo> items;
	HttpControl httpControl =new HttpControl();
	Context ctx;
	PullToRefreshListView pull_refresh_list;
	public SameCityAdapter(Context ctx,List<BrandCityWideInfo> items,PullToRefreshListView pull_refresh_list) {
		super(ctx);
		this.ctx=ctx;
		this.items = items;
		this.pull_refresh_list=pull_refresh_list;
	}

	@Override
	public int getCount() {
		
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@SuppressWarnings("null")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if(convertView == null){
			holder = new Holder();
			convertView = View.inflate(ctx, R.layout.same_city_list_item, null);
			ToolsUtil.setFontStyle(ctx, convertView, R.id.tv_nick_name,R.id.tv_belong,R.id.tv_attention);
			holder.iv_head = (RoundImageView) convertView.findViewById(R.id.iv_head);
			holder.nick_name = (TextView) convertView.findViewById(R.id.tv_nick_name);
			holder.tv_belong = (TextView) convertView.findViewById(R.id.tv_belong);
			holder.tv_attention = (TextView) convertView.findViewById(R.id.tv_attention);
			holder.brandlist_item_imageview1=(ImageView)convertView.findViewById(R.id.brandlist_item_imageview1);
			holder.brandlist_item_imageview2=(ImageView)convertView.findViewById(R.id.brandlist_item_imageview2);
			holder.brandlist_item_imageview3=(ImageView)convertView.findViewById(R.id.brandlist_item_imageview3);
			holder.tv_attention.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(v.getTag()!=null)
					{
						BrandCityWideInfo brandCityWideInfo=(BrandCityWideInfo)v.getTag();
						if(brandCityWideInfo.isIsFavorite())//如果是已关注
						{
							//取消关注
							submitAttention(0,brandCityWideInfo);
						}else
						{
							//添加关注
							submitAttention(1,brandCityWideInfo);
						}
					}
				}
			});
			
			
			holder.brandlist_item_imageview1.setOnClickListener(onClickListener);
			holder.brandlist_item_imageview2.setOnClickListener(onClickListener);
			holder.brandlist_item_imageview3.setOnClickListener(onClickListener);
			
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		
		BrandCityWideInfo brandCityWideInfo=items.get(position);
		MyApplication.getInstance().getImageLoader().displayImage(ToolsUtil.nullToString(brandCityWideInfo.getBuyerLogo()), holder.iv_head, MyApplication.getInstance().getDisplayImageOptions());
		holder.nick_name.setText(ToolsUtil.nullToString(brandCityWideInfo.getUserName()));
		holder.tv_belong.setText(ToolsUtil.nullToString(brandCityWideInfo.getAddress()));
		holder.tv_attention.setTag(brandCityWideInfo);
		if(brandCityWideInfo.isIsFavorite())//如果已经关注
		{
			holder.tv_attention.setText("取消关注");
		}else
		{
			holder.tv_attention.setText("关注");
		}
		
		String[] pic_array=brandCityWideInfo.getPic();
		holder.brandlist_item_imageview1.setImageResource(R.drawable.default_pic);
		holder.brandlist_item_imageview2.setImageResource(R.drawable.default_pic);
		holder.brandlist_item_imageview3.setImageResource(R.drawable.default_pic);
		holder.brandlist_item_imageview1.setTag(brandCityWideInfo.getProductId());
		holder.brandlist_item_imageview2.setTag(brandCityWideInfo.getProductId());
		holder.brandlist_item_imageview3.setTag(brandCityWideInfo.getProductId());
		if(pic_array!=null)
		{
			for(int i=0;i<pic_array.length;i++)
			{
			  switch(i)
			  {
			  case 0:
				  MyApplication.getInstance().getImageLoader().displayImage(ToolsUtil.getImage(ToolsUtil.nullToString(pic_array[0]), 320, 0), holder.brandlist_item_imageview1, MyApplication.getInstance().getDisplayImageOptions());
				  break;
			  case 1:
				  MyApplication.getInstance().getImageLoader().displayImage(ToolsUtil.getImage(ToolsUtil.nullToString(pic_array[1]), 320, 0), holder.brandlist_item_imageview2, MyApplication.getInstance().getDisplayImageOptions());
				  break;
			  case 2:
				  MyApplication.getInstance().getImageLoader().displayImage(ToolsUtil.getImage(ToolsUtil.nullToString(pic_array[2]), 320, 0), holder.brandlist_item_imageview3, MyApplication.getInstance().getDisplayImageOptions());
				  break;
			  }
			}
		}
		return convertView;
	}
	
	
	class Holder{
		RoundImageView iv_head;
		TextView nick_name;
		TextView tv_belong;
		TextView tv_attention;
		ImageView brandlist_item_imageview1,brandlist_item_imageview2,brandlist_item_imageview3;
		
	}

	
	OnClickListener onClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
			case R.id.brandlist_item_imageview1:
			case R.id.brandlist_item_imageview2:
			case R.id.brandlist_item_imageview3:
				Intent intent=new Intent(ctx,ApproveBuyerDetailsActivity.class);
				intent.putExtra("productID", (Long)v.getTag());
				ctx.startActivity(intent);
				break;
			}
		}
	};
	
	/****
	 * 提交收藏与取消收藏商品
	 * @param type int   0表示取消收藏   1表示收藏
	 * @param brandCityWideInfo BrandCityWideInfo  商品对象
	 * **/
	void submitAttention(final int Status,final BrandCityWideInfo brandCityWideInfo)
	{
		httpControl.setFavor(brandCityWideInfo.getProductId(), Status, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				View v=pull_refresh_list.findViewWithTag(brandCityWideInfo);
				if(v!=null && v instanceof TextView)
				{
					switch(Status)
					{
					case 0:
						((TextView)v).setText("关注");
						brandCityWideInfo.setIsFavorite(false);
						break;
					case 1:
						((TextView)v).setText("取消关注");
						brandCityWideInfo.setIsFavorite(true);
						break;
					}
					
				}
				SameCityAdapter.this.notifyDataSetChanged();
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				MyApplication.getInstance().showMessage(ctx, msg);
			}
		}, ctx);
	}
	
}
