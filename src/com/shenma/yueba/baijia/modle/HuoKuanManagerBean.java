package com.shenma.yueba.baijia.modle;

public class HuoKuanManagerBean {
	private String TotalAmount;//总货款
	private String PickedAmount;//已提现
	private String CanPickAmount;//可提现
	private String  FrozenAmount;//已冻结
	private String RmaAmount;//退款
	private String PickedPercent;//已经提现比例,
	private String CanPickPercent;//可提现比例
	private String FrozenPercent;//冻结的比例
	private String RmaPercent;//退货的比例
	public String getTotalAmount() {
		return TotalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		TotalAmount = totalAmount;
	}
	public String getPickedAmount() {
		return PickedAmount;
	}
	public void setPickedAmount(String pickedAmount) {
		PickedAmount = pickedAmount;
	}
	public String getCanPickAmount() {
		return CanPickAmount;
	}
	public void setCanPickAmount(String canPickAmount) {
		CanPickAmount = canPickAmount;
	}
	public String getFrozenAmount() {
		return FrozenAmount;
	}
	public void setFrozenAmount(String frozenAmount) {
		FrozenAmount = frozenAmount;
	}
	public String getRmaAmount() {
		return RmaAmount;
	}
	public void setRmaAmount(String rmaAmount) {
		RmaAmount = rmaAmount;
	}
	public String getPickedPercent() {
		return PickedPercent;
	}
	public void setPickedPercent(String pickedPercent) {
		PickedPercent = pickedPercent;
	}
	public String getCanPickPercent() {
		return CanPickPercent;
	}
	public void setCanPickPercent(String canPickPercent) {
		CanPickPercent = canPickPercent;
	}
	public String getFrozenPercent() {
		return FrozenPercent;
	}
	public void setFrozenPercent(String frozenPercent) {
		FrozenPercent = frozenPercent;
	}
	public String getRmaPercent() {
		return RmaPercent;
	}
	public void setRmaPercent(String rmaPercent) {
		RmaPercent = rmaPercent;
	}
	
}
