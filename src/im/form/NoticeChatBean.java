package im.form;

import android.content.Context;
import im.control.SocketManger.SocketManagerListener;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-6 上午11:14:49  
 * 程序的简单说明  推广数据
 */

public class NoticeChatBean extends BaseChatBean{
  
	public NoticeChatBean(Context context) {
		super(ChatType.notice_type,RequestMessageBean.notice,context);
	}

	@Override
	public void sendData(SocketManagerListener listener) {
		
	}

	@Override
	public void setValue(Object obj) {
		super.setParentView(obj);
	}

}
