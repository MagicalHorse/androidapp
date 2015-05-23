package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

public class MyAttentionAndFansForSocialBean implements Serializable{

	private String imageHead;//头像
	private String name;//姓名
	private String attentionCount;//关注数
	private String fansCount;//粉丝数
	private String attention;//关注or去关注
	public String getImageHead() {
		return imageHead;
	}
	public void setImageHead(String imageHead) {
		this.imageHead = imageHead;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAttentionCount() {
		return attentionCount;
	}
	public void setAttentionCount(String attentionCount) {
		this.attentionCount = attentionCount;
	}
	public String getFansCount() {
		return fansCount;
	}
	public void setFansCount(String fansCount) {
		this.fansCount = fansCount;
	}
	public String getAttention() {
		return attention;
	}
	public void setAttention(String attention) {
		this.attention = attention;
	}
	
	
	

	
	
}
