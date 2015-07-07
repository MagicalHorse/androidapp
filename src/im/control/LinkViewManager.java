package im.control;

import im.form.BaseChatBean;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.RoundImageView;

/**
 * @author gyj
 * @version 创建时间：2015-7-3 下午4:21:21 程序的简单说明
 */

public class LinkViewManager extends ChatBaseManager {

	public enum TextView_Type {
		left, right;
	}

	public LinkViewManager(Context context, TextView_Type type) {
		super(context);
		this.type = type;

	}
    RoundImageView chat_layout_item_leftmsg_icon_roundimageview;//头像
	TextView_Type type = TextView_Type.left;
	RelativeLayout ll;
	TextView chat_layout_item_leftmsg_name_textview;// 名字
	TextView chat_layout_item_leftmsg_time_textview;// 时间
	TextView chat_layout_item_leftmsg_msg_textview;// 内容
	ImageView chat_layout_item_leftmsg_productimg_textview;// 商品图片

	/*******
	 * 初始化视图
	 * ***/
	public void initView(View view) {
		if (view == null) {
			new Throwable("holder=null or view =null",new NullPointerException());
		}
		switch (type) {
		case left:
			ll = (RelativeLayout) view.findViewById(R.id.chat_layout_item_linkleft_include);
			break;
		case right:
			ll = (RelativeLayout) view.findViewById(R.id.chat_layout_item_linkright_include);
			break;
		}

		infaterView();
	}

	void infaterView() {
		chat_layout_item_leftmsg_icon_roundimageview=(RoundImageView)ll.findViewById(R.id.chat_layout_item_leftmsg_icon_roundimageview);
		chat_layout_item_leftmsg_name_textview = (TextView) ll.findViewById(R.id.chat_layout_item_leftmsg_name_textview);
		chat_layout_item_leftmsg_time_textview = (TextView) ll.findViewById(R.id.chat_layout_item_leftmsg_time_textview);
		chat_layout_item_leftmsg_msg_textview = (TextView) ll.findViewById(R.id.chat_layout_item_leftmsg_msg_textview);
		chat_layout_item_leftmsg_productimg_textview = (ImageView) ll.findViewById(R.id.chat_layout_item_leftmsg_productimg_textview);
	}

	/*****
	 * 是否显示视图
	 * ***/
	public void isshow(boolean b, BaseChatBean bean) {
		if (b) {
			ll.setVisibility(View.VISIBLE);
		} else {
			ll.setVisibility(View.GONE);
		}
		String contecnt_str=ToolsUtil.getImage(ToolsUtil.nullToString((String)bean.getContent()), 320, 0);
		chat_layout_item_leftmsg_name_textview.setText(ToolsUtil.nullToString(bean.getUserName()));
		chat_layout_item_leftmsg_time_textview.setText(ToolsUtil.nullToString(bean.getCreationDate()));
		chat_layout_item_leftmsg_msg_textview.setText(ToolsUtil.analysisFace(context,contecnt_str),BufferType.SPANNABLE);
		MyApplication.getInstance().getImageLoader().displayImage(ToolsUtil.nullToString(bean.getLogo()), chat_layout_item_leftmsg_icon_roundimageview, MyApplication.getInstance().getDisplayImageOptions());
		MyApplication.getInstance().getImageLoader().displayImage(contecnt_str, chat_layout_item_leftmsg_productimg_textview, MyApplication.getInstance().getDisplayImageOptions());
	}
}
