package im.form;

import im.control.SocketManger;
import im.control.SocketManger.SocketManagerListener;
import im.form.BaseChatBean.ChatType;

import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.util.SharedUtil;
import com.shenma.yueba.util.ToolsUtil;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-3 下午4:02:10  
 * 程序的简单说明  文本消息
 */

public class TextChatBean extends BaseChatBean{

	public TextChatBean() {
		super(ChatType.text_trype,RequestMessageBean.type_empty);
	}

	@Override
	public void sendData(SocketManagerListener listener) {
		MessageBean bean=getMessageBean();
		SocketManger.the(listener).sendMsg(bean);
	}

	/*****
	 * 接收到的消息 进行item的赋值
	 * ***/
	@Override
	public void setValue(Object obj) {
		
		super.setParentView(obj);
	}

}
