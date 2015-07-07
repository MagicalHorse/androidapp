package im.control;

import im.form.BaseChatBean;
import android.content.Context;
import android.view.View;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-3 下午5:37:09  
 * 程序的简单说明  
 */

public abstract class ChatBaseManager {
	Context context;
	public ChatBaseManager(Context context)
	{
		this.context=context;
	}
	
	//初始化视图
	public abstract void initView(View v);
	//是否显示视图
	public abstract void isshow(boolean b,BaseChatBean bean);
}
