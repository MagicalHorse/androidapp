package im.control;

import im.form.BaseChatBean;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.RoundImageView;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-3 下午5:10:04  
 * 程序的简单说明  
 */

public class PicViewManager extends ChatBaseManager{
	
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
	ImageView chat_layout_item_leftimg_msg_imageview;//信息
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
		if(bean!=null)
		{
			MyApplication.getInstance().getImageLoader().displayImage(ToolsUtil.nullToString(bean.getLogo()), chat_layout_item_leftimg_icon_roundimageview, MyApplication.getInstance().getDisplayImageOptions());
			//图片
		}
	}
}
