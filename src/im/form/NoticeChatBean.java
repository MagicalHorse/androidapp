package im.form;

import android.content.Context;

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
	public void sendData() {
		
	}

	@Override
	public void setValue(Object obj) {
		super.setParentView(obj);
	}

}
