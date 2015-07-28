package im.control;

import im.form.BaseChatBean;
import im.form.PicChatBean;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.TouchImageViewActivity;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.RoundImageView;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-3 下午5:10:04  
 * 程序的简单说明  
 */

public class PicViewManager extends ChatBaseManager implements OnClickListener{
	
	public enum PicView_Type
	{
		left,
		right;
	}
	
	public PicViewManager(Context context,PicView_Type type) {
		super(context);
		this.type=type;
	}
	PicView_Type type=PicView_Type.left;
	RoundImageView chat_layout_item_leftimg_icon_roundimageview;//头像
	ImageView chat_layout_item_leftimg_msg_imageview;//图片
	TextView chat_layout_item_leftmsg_name_textview;//名称
	TextView chat_layout_item_leftmsg_time_textview;//时间
	LinearLayout chat_layout_item_leftimg_progress_linearlayout;//进度俯视图
	ProgressBar chat_layout_item_leftimg_progress_progressbar;//进度条
	TextView chat_layout_item_leftimg_progress_textview;//进度文本
	
	RelativeLayout ll;
	/*******
	 * 初始化左侧视图
	 * ***/
	public  void initView(View view)
	{
		if(view==null)
		{
			new Throwable("holder=null or view =null", new NullPointerException());
		}
		switch(type)
		{
		case left:
			ll=(RelativeLayout)view.findViewById(R.id.chat_layout_item_leftimg_include);
			break;
		case right:
			ll=(RelativeLayout)view.findViewById(R.id.chat_layout_item_rightimg_include);
			break;
		}
		
		chat_layout_item_leftimg_icon_roundimageview=(RoundImageView)ll.findViewById(R.id.chat_layout_item_leftimg_icon_roundimageview);
		chat_layout_item_leftimg_msg_imageview=(ImageView)ll.findViewById(R.id.chat_layout_item_leftimg_msg_imageview);
		chat_layout_item_leftimg_msg_imageview.setOnClickListener(this);
		chat_layout_item_leftmsg_name_textview=(TextView)ll.findViewById(R.id.chat_layout_item_leftmsg_name_textview);
		chat_layout_item_leftmsg_time_textview=(TextView)ll.findViewById(R.id.chat_layout_item_leftmsg_time_textview);
		
		chat_layout_item_leftimg_progress_linearlayout=(LinearLayout)ll.findViewById(R.id.chat_layout_item_leftimg_progress_linearlayout);
		chat_layout_item_leftimg_progress_progressbar=(ProgressBar)ll.findViewById(R.id.chat_layout_item_leftimg_progress_progressbar);
		chat_layout_item_leftimg_progress_textview=(TextView)ll.findViewById(R.id.chat_layout_item_leftimg_progress_textview);
		
	}
	
	
	@Override
	public void isshow(boolean b,BaseChatBean bean) {
		if(b)
		{
			ll.setVisibility(View.VISIBLE);
		}else
		{
			ll.setVisibility(View.GONE);
		}
		if(bean!=null && bean instanceof PicChatBean)
		{
			PicChatBean picbean=(PicChatBean)bean;
			chat_layout_item_leftmsg_name_textview.setText(ToolsUtil.nullToString(picbean.getUserName()));
			chat_layout_item_leftmsg_time_textview.setText(ToolsUtil.nullToString(picbean.getCreationDate()));
			chat_layout_item_leftimg_msg_imageview.setTag(bean);
			MyApplication.getInstance().getImageLoader().displayImage(ToolsUtil.nullToString(picbean.getLogo()), chat_layout_item_leftimg_icon_roundimageview, MyApplication.getInstance().getDisplayImageOptions());
			if(picbean.isSuccess())
			{
				MyApplication.getInstance().getImageLoader().displayImage(ToolsUtil.nullToString((String)picbean.getContent()), chat_layout_item_leftimg_msg_imageview, MyApplication.getInstance().getDisplayImageOptions());
			}
			
			
			//图片
			if(!picbean.isUpload() && picbean.isSuccess())//如果上传完成则  隐藏进度
			{
				chat_layout_item_leftimg_progress_linearlayout.setVisibility(View.GONE);
			}else
			{
				chat_layout_item_leftimg_progress_linearlayout.setVisibility(View.VISIBLE);
			}
			//计算上传进度
			int currcount=picbean.getProgress();
			int maxcount=picbean.getMaxProgress();
			int value=(int)(((float)currcount/(float)maxcount)*100);
		    if(value>=100)
		    {
		    	value=99;
		    }
			chat_layout_item_leftimg_progress_textview.setText(value+"%");
		}
		 
	}


	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.chat_layout_item_leftimg_msg_imageview:
			PicChatBean picbean=(PicChatBean)v.getTag();
			Intent intent=new Intent(context,TouchImageViewActivity.class);
			if(picbean.isSuccess())
			{
				intent.putExtra("IMG_URL", (String)picbean.getContent());
			    context.startActivity(intent);
			    ((Activity)context).overridePendingTransition(R.anim.img_scale_in, R.anim.img_scale_out);
			}
			
			break;
		}
	}
}
