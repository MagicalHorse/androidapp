package com.shenma.yueba.baijia.adapter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.shenma.yueba.R;
import com.shenma.yueba.baijia.modle.MyMessage;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.util.MyDate;
import com.shenma.yueba.util.MyPreference;
import com.shenma.yueba.util.ToolsUtil;

public class ChattingListViewAdapter extends BaseAdapter {
	
	private TextView mTv;
	private int animationFlag = 0;//用于动画显示
	private String otherImage;//聊天的人的头像
	private Handler mHandler = new Handler();
	private int IMVT_RIGHT_MSG = 0;// 自己的
	private int IMVT_LEFT_MSG = 1;// 别人的
	private MediaPlayer mMediaPlayer = new MediaPlayer();
	/** 消息集合 **/
	private ArrayList<MyMessage> listData;
	/** 时间字符串集合 **/
	private ArrayList<String> listTime;
	/** 是否显示集合 **/
	private ArrayList<Boolean> listIsShow;
	private Context context;
	private LayoutInflater mInflater;
	private ViewHolder holder;
	private String otherIcon = null;
	private int indexOfListView;
	private String myIcon;
	private String isLeft;
	private static final int POLL_INTERVAL = 300;//定时任务的时间间隔
	public ChattingListViewAdapter(ArrayList<MyMessage> listData,Context context) {
		this.listData = listData;
		this.context = context;
		mInflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		return listData.size();
	}
	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
		@Override
	public int getItemViewType(int position) {
		if("true".equals(listData.get(position).getIsLeft())){
			return IMVT_LEFT_MSG;
		}
		return IMVT_RIGHT_MSG;
	}
	@Override
	public int getViewTypeCount() {
		return 2;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		holder = null;
		final MyMessage msg = listData.get(position);
		isLeft = listData.get(position).getIsLeft();
		if (convertView == null) {
			if(!"true".equals(isLeft)){//自己
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_txt_right, null);
			}else{
				convertView = mInflater.inflate(
						R.layout.chatting_item_msg_txt_left, null);
			}
			holder = new ViewHolder();
			holder.Chatting_Item_TxtView_Msg = (TextView) convertView
					.findViewById(R.id.Chatting_Item_TxtView_Msg);
			mTv = holder.Chatting_Item_TxtView_Msg;
			holder.Chatting_Item_TxtTime = (TextView) convertView
					.findViewById(R.id.Chatting_Item_TxtTime);
			holder.Chatting_Item_ImgView_Icon = (ImageView) convertView
					.findViewById(R.id.Chatting_Item_ImgView_Icon);
			holder.iv_pic = (ImageView) convertView.findViewById(R.id.iv_pic);
			holder.rl_pic = (RelativeLayout) convertView.findViewById(R.id.rl_pic);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.rl_pic.setVisibility(View.GONE);
		holder.Chatting_Item_TxtView_Msg.setVisibility(View.GONE);
	   if(msg.getImagePath()!=null && msg.getImagePath().length()>0){//如果是图片文件
			holder.rl_pic.setVisibility(View.VISIBLE);
			if(!"true".equals(isLeft)){//自己
				//PublicMethod.loadImageFromLocal(context, msg.getImagePath(), R.drawable.default_pic, holder.iv_pic);
			}else{
				//PublicMethod.loadImage(context, msg.getImagePath(), R.drawable.default_pic, holder.iv_pic);
			}
			holder.Chatting_Item_TxtView_Msg.setText("");
		}
		else{
			holder.Chatting_Item_TxtView_Msg.setVisibility(View.VISIBLE);
			holder.Chatting_Item_TxtView_Msg.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
			holder.Chatting_Item_TxtView_Msg.setText(ToolsUtil.analysisFace(context,msg.getBody()),BufferType.SPANNABLE);
		}
		//时间
		holder.Chatting_Item_TxtTime.setText(msg.getMsgTime());
		
		//头像
		if(!"true".equals(isLeft)){//自己
			if(MyPreference.getStringValue(context,Constants.USER_HEAD_IMAGE)!=null && MyPreference.getStringValue(context,Constants.USER_HEAD_IMAGE).length()>0){
				//Picasso.with(context).load(MyPreference.getStringValue(context,Constants.USER_HEAD_IMAGE)).into(holder.Chatting_Item_ImgView_Icon);
			}
		}else{//别人
			if(otherImage!=null && otherImage.length()>0){
				//Picasso.with(context).load(otherImage).into(holder.Chatting_Item_ImgView_Icon);
			}
			
		}
		
		convertView.setEnabled(false);
		return convertView;
	}

	public void setIsLeft(String isLeftValue){
		isLeft = isLeftValue;
	}
	
	
	private String SetDate(Date date) {
		if (date == null) {
			return "时间为空";
		}
		/** 当前时间 年月日 **/
		Date currentDate = new Date(System.currentTimeMillis());
		/** 两个时间差的天数 **/
		long ApartDays = MyDate.getDatetimeIntervalUsingDay(currentDate, date);
		/** 得到消息的小时 包含上下午 **/
		String hourExtraInfo = MyDate.GetHourExtraInfo(date);
		/** 当前时间 年 **/
		int yearCurrentDate = currentDate.getYear();
		/** 消息时间 年 **/
		int yearMsgDate = date.getYear();
		/** 周几 **/
		String indexOfWeek = MyDate.getIndexOfWeek(date);
		if (ApartDays == 0) {// 今天
			return hourExtraInfo;
		} else if (ApartDays == 1) {// 昨天
			return ("昨天" + " " + hourExtraInfo);
		} else if (ApartDays == 2) {// 前天
			return (indexOfWeek + " " + hourExtraInfo);
		} else if (yearCurrentDate == (yearMsgDate)) {// 今年
			return (MyDate.AddMDChina(date) + " " + hourExtraInfo);
		} else {// 今年之前
			return (MyDate.AddYMDChina(date) + " " + hourExtraInfo);
		}

	}

	private class ViewHolder {
		ImageView Chatting_Item_ImgView_Icon;
		TextView Chatting_Item_TxtView_Msg;
		TextView Chatting_Item_TxtTime;
		RelativeLayout rl_pic;//
		ImageView iv_pic;//用于显示图片
	}
	
	//设置头像的数据
	public void setImageForOther(String otherImage){
		this.otherImage = otherImage;
	}
	
	
	/**
	 * 播放语音文件
	 * @param name
	 */
	private void playMusic(String name,final boolean isMySelf) {
		try {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.stop();
			}
			mMediaPlayer.reset();
			mMediaPlayer.setDataSource(name);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
			mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				public void onCompletion(MediaPlayer mp) {
//					if(isMySelf){
//						mTv.setCompoundDrawablesWithIntrinsicBounds(0, 0,
//								R.drawable.chatto_voice_playing, 0);
//					}else{
//						mTv.setCompoundDrawablesWithIntrinsicBounds(0, 0,
//								R.drawable.chatto_voice_playing2, 0);
//					}
//					mHandler.removeCallbacks(mPollTask);
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
}
