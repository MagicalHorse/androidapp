package com.shenma.yueba.util;

import java.util.List;

import com.shenma.yueba.yangjia.modle.HistoryItem;

public class RewardDetailItem {

	
	private String id;
	private String name;
	private String desc;
	private String amount;
	private String surplusamount;
	private String tip;
	private List<HistoryItem> history;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getSurplusamount() {
		return surplusamount;
	}
	public void setSurplusamount(String surplusamount) {
		this.surplusamount = surplusamount;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public List<HistoryItem> getHistory() {
		return history;
	}
	public void setHistory(List<HistoryItem> history) {
		this.history = history;
	}
	
}
