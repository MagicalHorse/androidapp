package com.shenma.yueba.baijia.view;

import android.app.Activity;
import android.view.View;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-15 上午10:28:54  
 * 程序的简单说明  
 */

public abstract class BaseView{

	
	public abstract View getView(Activity activity);
	public abstract void firstInitData(Activity activity);
}
