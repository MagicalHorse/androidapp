package com.shenma.yueba.baijia.modle;

public class UserFlowStatusBean {

	private boolean IsFlow;// 是否关注
	private String QRCode;// 买手公众号二维码
	private String Name;// 公众号账号
	public boolean isIsFlow() {
		return IsFlow;
	}
	public void setIsFlow(boolean isFlow) {
		IsFlow = isFlow;
	}
	public String getQRCode() {
		return QRCode;
	}
	public void setQRCode(String qRCode) {
		QRCode = qRCode;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}

	
	
}
