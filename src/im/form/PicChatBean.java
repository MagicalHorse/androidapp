package im.form;

import im.control.SocketManger.SocketManagerListener;

import android.os.SystemClock;

import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.util.SharedUtil;

/**  
 * @author gyj  
 * @version 创建时间：2015-7-6 上午11:13:48  
 * 程序的简单说明  图片数据
 */

public class PicChatBean extends BaseChatBean{
int progress=0;//上传进度
int maxProgress=0;//总进度
boolean isUpload=false;//是否上传或下载中
boolean isSuccess=false;//是否完成
String picaddress="";//图片的地址
String ali_content;//阿里云返回的数据
	
	public String getAli_content() {
	return ali_content;
}

public void setAli_content(String ali_content) {
	this.ali_content = ali_content;
}

	public int getProgress() {
	return progress;
}

public void setProgress(int progress) {
	this.progress = progress;
}

public int getMaxProgress() {
	return maxProgress;
}

public void setMaxProgress(int maxProgress) {
	this.maxProgress = maxProgress;
}

public boolean isUpload() {
	return isUpload;
}

public void setUpload(boolean isUpload) {
	this.isUpload = isUpload;
}

public boolean isSuccess() {
	return isSuccess;
}

public void setSuccess(boolean isSuccess) {
	this.isSuccess = isSuccess;
}

public String getPicaddress() {
	return picaddress;
}

public void setPicaddress(String picaddress) {
	this.picaddress = picaddress;
}

	public PicChatBean() {
		super(ChatType.pic_type,RequestMessageBean.type_img);
		
	}

	@Override
	public void sendData(SocketManagerListener listener) {
		while(true)
		{
			SystemClock.sleep(1000);
			if(isSuccess)//如果上传完成
			{
				//sssss
			}
		}
	}

	@Override
	public void setValue(Object obj) {
		super.setParentView(obj);
	}
	
	
	void uploadPic()
	{
		
	}

}
