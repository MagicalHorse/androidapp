package im.control;

import im.control.PicViewManager.PicView_Type;
import im.form.BaseChatBean;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.shenma.yueba.R;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-3 下午3:55:57  
 * 程序的简单说明  
 */

public class ChatViewManager {
	LinkViewManager leftlinkViewManager;
	LinkViewManager rightlinkViewManager;
	TextViewManager leftTextViewManager;
	TextViewManager rightTextViewManager;
	PicViewManager leftPicViewManager;
	PicViewManager rightPicViewManager;
	Context context;
	public ChatViewManager(Context context)
	{
		this.context=context;
		leftlinkViewManager=new LinkViewManager(context,LinkViewManager.TextView_Type.left);
		rightlinkViewManager=new LinkViewManager(context,LinkViewManager.TextView_Type.right);
		leftTextViewManager=new TextViewManager(context,TextViewManager.TextView_Type.left);
		rightTextViewManager=new TextViewManager(context,TextViewManager.TextView_Type.right);
		leftPicViewManager=new PicViewManager(context,PicView_Type.left);
		rightPicViewManager=new PicViewManager(context,PicView_Type.right);
	}
	
	
	/******
	 * 根据消息类型 显示消息视图
	 * @param bean BaseChatBean 
	 * @param view View 
	 * **/
	public  void setView(final BaseChatBean bean,final View view)
	{
		if(bean==null || view ==null)
		{
			new Throwable("bean=null or view=null",new NullPointerException());
		}
		
		View chat_layout_item_leftlink_include=view.findViewById(R.id.chat_layout_item_linkleft_include);//左链接
		View chat_layout_item_rightlink_include=view.findViewById(R.id.chat_layout_item_linkright_include);//左链接
		View chat_layout_item_leftmsg_include=view.findViewById(R.id.chat_layout_item_leftmsg_include);//左聊天
		View chat_layout_item_rightmsg_include=view.findViewById(R.id.chat_layout_item_rightmsg_include);//右聊天
		View chat_layout_item_rightimg_include=view.findViewById(R.id.chat_layout_item_rightimg_include);//右图片
		View chat_layout_item_leftimg_include=view.findViewById(R.id.chat_layout_item_leftimg_include);//左图片
		chat_layout_item_leftlink_include.setVisibility(View.GONE);
		chat_layout_item_rightlink_include.setVisibility(View.GONE);
		chat_layout_item_leftmsg_include.setVisibility(View.GONE);
		chat_layout_item_rightimg_include.setVisibility(View.GONE);
		chat_layout_item_leftimg_include.setVisibility(View.GONE);
		chat_layout_item_rightmsg_include.setVisibility(View.GONE);
		switch(bean.getChattype())
		{
		case link_type:
			if(bean.isIsoneself())
			{
				bean.setBaseManager(rightlinkViewManager);
			}else
			{
				bean.setBaseManager(leftlinkViewManager);
			}
			
			break;
		case text_trype:
			if(bean.isIsoneself())
			{
				bean.setBaseManager(rightTextViewManager);
			}else
			{
				bean.setBaseManager(leftTextViewManager);
			}
			break;
		case pic_type:
			if(bean.isIsoneself())
			{
				bean.setBaseManager(rightPicViewManager);
			}else
			{
				bean.setBaseManager(leftPicViewManager);
			}
			break;
			default:
			if(bean.isIsoneself())
			{
				bean.setBaseManager(rightTextViewManager);
			}else
			{
				bean.setBaseManager(leftTextViewManager);
			}
			break;
				
		}
		bean.getBaseManager().isshow(true,bean);
	}

	/*****
	 * 初始化视图
	 * **/
	public View initView()
	{
		RelativeLayout parentView=(RelativeLayout)RelativeLayout.inflate(context, R.layout.chat_layout_item, null);
		
		leftlinkViewManager.initView(parentView);//左链接初始化
		rightlinkViewManager.initView(parentView);//右链接初始化
		
		leftTextViewManager.initView(parentView);//初始化左聊天
		
		rightTextViewManager.initView(parentView);//右侧始化左聊天
		
		leftPicViewManager.initView(parentView);//左侧图片聊天
		rightPicViewManager.initView(parentView);//右侧图片聊天
		return parentView;
	}
}
