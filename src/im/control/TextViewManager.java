package im.control;

import im.form.BaseChatBean;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.RoundImageView;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-3 下午5:10:04  
 * 程序的简单说明  
 */

public class TextViewManager extends ChatBaseManager{
	public enum TextView_Type
	{
		left,
		right;
	}
	/*****
	 * @param 
	 * ***/
	public TextViewManager(Context context,TextView_Type type ) {
		super(context);
		this.type=type;
		
	}
	TextView_Type type=TextView_Type.left;
	RoundImageView chat_layout_item_leftmsg_icon_roundimageview;//头像
	TextView chat_layout_item_leftmsg_msg_textview;//信息
	TextView chat_layout_item_leftmsg_name_textview;//名称
	TextView chat_layout_item_leftmsg_time_textview;//时间
	RelativeLayout ll;
	/*******
	 * 初始化视图
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
			ll=(RelativeLayout)view.findViewById(R.id.chat_layout_item_leftmsg_include);
			break;
		case right:
			ll=(RelativeLayout)view.findViewById(R.id.chat_layout_item_rightmsg_include);
			break;
		}
		
		infaterView();
	}
	
	void infaterView()
	{
		chat_layout_item_leftmsg_msg_textview=(TextView)ll.findViewById(R.id.chat_layout_item_leftmsg_msg_textview);
		chat_layout_item_leftmsg_icon_roundimageview=(RoundImageView)ll.findViewById(R.id.chat_layout_item_leftmsg_icon_roundimageview);
		chat_layout_item_leftmsg_name_textview=(TextView)ll.findViewById(R.id.chat_layout_item_leftmsg_name_textview);
		chat_layout_item_leftmsg_time_textview=(TextView)ll.findViewById(R.id.chat_layout_item_leftmsg_time_textview);
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
		chat_layout_item_leftmsg_name_textview.setText(ToolsUtil.nullToString(bean.getUserName()));
		chat_layout_item_leftmsg_time_textview.setText(ToolsUtil.nullToString(bean.getCreationDate()));
		chat_layout_item_leftmsg_msg_textview.setText(ToolsUtil.analysisFace(context,(String)bean.getContent()),BufferType.SPANNABLE);
		MyApplication.getInstance().getImageLoader().displayImage(ToolsUtil.nullToString(bean.getLogo()), chat_layout_item_leftmsg_icon_roundimageview, MyApplication.getInstance().getDisplayImageOptions());
		
	}
}
