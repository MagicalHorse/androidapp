package com.shenma.yueba.baijia.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.AttationListActivity;
import com.shenma.yueba.baijia.modle.AttationListBean;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.RoundImageView;
import com.shenma.yueba.yangjia.modle.AttationAndFansItemBean;

public class AttationListAdapter extends BaseAdapterWithUtil {
	private List<AttationAndFansItemBean> mList;
	int status=0;//关注
	HttpControl httpControl=new HttpControl();
	Context context;
	PullToRefreshListView pullToRefreshListView;
	public AttationListAdapter(Context ctx,List<AttationAndFansItemBean> mList,int status,PullToRefreshListView pullToRefreshListView) {
		super(ctx);
		this.mList = mList;
		this.context=ctx;
		this.status=status;
		this.pullToRefreshListView=pullToRefreshListView;
	}

	@Override
	public int getCount() {
		
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if(convertView == null){
			holder = new Holder();
			convertView = View.inflate(ctx, R.layout.fans_list_item, null);
			holder.riv_head = (RoundImageView) convertView.findViewById(R.id.riv_head);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_attention_count = (TextView) convertView.findViewById(R.id.tv_attention_count);
			holder.tv_fans_count = (TextView) convertView.findViewById(R.id.tv_fans_count);
			holder.tv_atttention = (TextView) convertView.findViewById(R.id.tv_atttention);
			holder.tv_address = (TextView) convertView.findViewById(R.id.tv_address);
			ToolsUtil.setFontStyle(ctx, convertView,R.id.tv_name,R.id.tv_attention_count,R.id.tv_fans_count,R.id.tv_atttention,R.id.tv_address);
			holder.tv_atttention.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(v.getTag()!=null && v.getTag() instanceof AttationAndFansItemBean)
					{
						AttationAndFansItemBean bean=(AttationAndFansItemBean)v.getTag();
						if(status==0)//如果是关注
						{
							if(bean.isFavorite())
							{
								sendFans(bean,0);
							}else
							{
								sendFans(bean,1);
							}
							
						}else if(status==1)//如果是粉丝
						{
							if(bean.isFavorite())
							{
								sendAttation(bean,0);
							}else
							{
								sendAttation(bean,1);
							}
							
						}
					}
					AttationListAdapter.this.notifyDataSetChanged();
				}
			});
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		initValue(position,holder);
		return convertView;
	}
	
	
	class Holder{
		RoundImageView riv_head;
		TextView tv_name;
		TextView tv_attention_count;
		TextView tv_fans_count;
		TextView tv_atttention;
		TextView tv_address;
		
	}

	void initValue(int position,Holder holder)
	{
		AttationAndFansItemBean bean=mList.get(position);
		MyApplication.getInstance().getImageLoader().displayImage(ToolsUtil.nullToString(bean.getUserLogo()), holder.riv_head, MyApplication.getInstance().getDisplayImageOptions());
		holder.tv_name.setText(ToolsUtil.nullToString(bean.getUserName()));
		holder.tv_atttention.setTag(bean);
		holder.tv_fans_count.setText("关注  "+ToolsUtil.nullToString(bean.getFansCount()));
		holder.tv_attention_count.setText("粉丝  "+ToolsUtil.nullToString(bean.getFavoiteCount()));
		if(status==0)//是关注
		{
			
			
			if(bean.isFavorite())
			{
				holder.tv_atttention.setText("取消关注");
			}else
			{
				holder.tv_atttention.setText(" 关注 ");
			}
		}else if(status==1)//粉丝
		{
			
			
			if(bean.isFavorite())
			{
				holder.tv_atttention.setText("取消关注");
			}else
			{
				holder.tv_atttention.setText(" 关注 ");
			}
		}
		
		
		
	}

	
	void sendFans(final AttationAndFansItemBean bean,final int Status)
	{
		sendAttation(bean,Status);
	}
	
	/*****
	 * @param bean AttationAndFansItemBean
	 * @param Status int  1表示关注 0表示取消关注
	 * ****/
	void sendAttation(final AttationAndFansItemBean bean,final int Status)
	{
		   int favoriteId=Integer.parseInt(bean.getUserId());
           httpControl.setFavoite(favoriteId, Status, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
//				if(Status==0) //1表示关注 0表示取消关注
//				{
//					bean.setFavorite(false);
//				}else if(Status==1)
//				{
//					bean.setFavorite(true);
//				}
//				if(pullToRefreshListView.findViewWithTag(bean)!=null &&  pullToRefreshListView.findViewWithTag(bean) instanceof TextView)
//				{
//					TextView tv=(TextView)pullToRefreshListView.findViewWithTag(bean);
//					if(Status==0)
//					{
//						tv.setText("关注");
//					}else if(Status==1)
//					{
//						tv.setText("取消关注");
//					}
//				}
				
				((AttationListActivity)ctx).refresh();

			}
			
			@Override
			public void http_Fails(int error, String msg) {
				MyApplication.getInstance().showMessage(ctx, msg);
			}
		}, context);
	}

}
