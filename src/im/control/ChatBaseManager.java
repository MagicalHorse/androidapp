package im.control;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shenma.yueba.ChatActivity;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.constants.HttpConstants;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.RoundImageView;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import im.form.BaseChatBean;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-3 下午5:37:09  
 * 程序的简单说明   本类定义通信管理基础类  用于定义 通信的通用方法 以及通用对象的创建
 * 该类是抽象了  需要子类实现 抽象的方法
 */

public abstract class ChatBaseManager {
	HttpControl httpControl=new HttpControl();
	Context context;
	TextView chat_layout_item_msg_time_textview;//时间
	TextView chat_layout_item_msg_name_textview;//名字
	RoundImageView chat_layout_item_img_icon_roundimageview;//头像
	View parentView;
	
	public enum ChatView_Type
	{
		left,
		right;
	}
	
	
	public ChatBaseManager(Context context)
	{
		this.context=context;
	}
	
	/*****
	 * 通用对象的初始化方法  需要传递父视图的对象
	 * @param v View 父视图的对象
	 * ****/
	public void parentinitView(View v)
	{
		if(v!=null)
		{
			parentView=v;
			chat_layout_item_msg_time_textview=(TextView)v.findViewById(R.id.chat_layout_item_msg_time_textview);
			chat_layout_item_msg_name_textview=(TextView)v.findViewById(R.id.chat_layout_item_msg_name_textview);
			chat_layout_item_img_icon_roundimageview=(RoundImageView)v.findViewById(R.id.chat_layout_item_img_icon_roundimageview);
			ToolsUtil.setFontStyle(context, v, R.id.chat_layout_item_msg_time_textview,R.id.chat_layout_item_msg_name_textview);
		}
	}
	
	/******
	 * 显示或隐藏 对象的方法  该方法中 调用了 子对象的 child_isshow()方法
	 * 用于进行赋值 操作
	 * @param b  boolan 是否显示   true 是  false 
	 * @param bean  BaseChatBean  传递的参数对象
	 * ****/
	public void isshow(boolean b,BaseChatBean bean)
	{
		if (b) {
			parentView.setVisibility(View.VISIBLE);
		} else {
			parentView.setVisibility(View.GONE);
		}
		chat_layout_item_msg_time_textview.setText(ToolsUtil.nullToString(bean.getCreationDate()));
		chat_layout_item_msg_name_textview.setText(ToolsUtil.nullToString(bean.getUserName()));
		child_isshow(b, bean);
		if(bean.getLogo()==null || bean.getLogo().equals(""))
		{
			//首先从 软引用列表中 查询是否存在相应的 bitmap对象 如果存在 直接引用 不存在 则调用接口请求数据
			if(ChatActivity.userid_logo.containsKey(bean.getFrom_id()))
			{
				String save_logo= ChatActivity.userid_logo.get(bean.getFrom_id());
				if(save_logo!=null)
				{
					initBitmap(save_logo);
					return;
				}
			}
			getUserLogo(bean.getFrom_id());
		}else
		{
			initBitmap(ToolsUtil.nullToString(bean.getLogo()));
		}
	}
	
	/*****
	 * 初始化视图 
	 * @param v View 父视图对象
	 */
	public abstract void initView(View v);
	/****
	 * 是否显示视图   并赋值（子视图对象须实现该方法）
	 * @param b boolean 是否显示   true 是  false
	 * @param bean  BaseChatBean  传递的参数对象
	 */
	public abstract void child_isshow(boolean b,BaseChatBean bean);
	
	/******
	 * 根据用户id获取用户图片
	 * ***/
	public void getUserLogo(final int user_id)
	{
		HttpUtils httpUtils = new HttpUtils(8000);
		String url=HttpConstants.GETUSERICON;
		Map<String, String> map=new HashMap<String, String>();
		map.put("userId", Integer.toString(user_id));
		RequestParams rp=httpControl.setBaseRequestParams(map, context);
		httpUtils.send(HttpMethod.POST,url, rp, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				String logo="";
				if(responseInfo!=null)
				{
					JSONObject json;
					try {
						json = new JSONObject(responseInfo.result);
						if(json.has("logo"))
						{
							logo=(String)json.get("logo");
							if(logo!=null)
							{
								if(!ChatActivity.userid_logo.containsKey(user_id))
								{
									ChatActivity.userid_logo.put(user_id, logo);
								}
							}
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					initBitmap(logo);
				}
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				initBitmap("");
			}
		});
	}
	
	
	void initBitmap(String logo)
	{
		if(chat_layout_item_img_icon_roundimageview!=null)
		{
			MyApplication.getInstance().getBitmapUtil().display(chat_layout_item_img_icon_roundimageview, ToolsUtil.nullToString(logo));
		}
	}
}
