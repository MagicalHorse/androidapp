package com.shenma.yueba.baijia.modle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-22 下午5:31:07  
 * 程序的简单说明  上传商品信息(买手)请求数据类
 */

public class ApplyAuthBuyerBean implements Serializable{
	private String ProvinceId;// 省id
	private String CityId;//市id
	private String DistrictId;//街道id
	private String ProvinceName;// 省名称
	private String CityName;// 亨氏名称
	private String DistrictName;//街道名称
	private String Address;// 地址
	private String StoreName;//门店名称
	private String SectionName;// 专柜名称
	private String SectionLocate;//专柜地址
	private String WorkCard;//工牌
	private String CardBack;//身份证背面
	private String CardFront;//身份证正面
//	private CardBean WorkCard;//工牌
//	private CardBean CardBack;//身份证背面
//	private CardBean CardFront;//身份证正面
	public String getProvinceId() {
		return ProvinceId;
	}
	public void setProvinceId(String provinceId) {
		ProvinceId = provinceId;
	}
	public String getCityId() {
		return CityId;
	}
	public void setCityId(String cityId) {
		CityId = cityId;
	}
	public String getDistrictId() {
		return DistrictId;
	}
	public void setDistrictId(String districtId) {
		DistrictId = districtId;
	}
	public String getProvinceName() {
		return ProvinceName;
	}
	public void setProvinceName(String provinceName) {
		ProvinceName = provinceName;
	}
	public String getCityName() {
		return CityName;
	}
	public void setCityName(String cityName) {
		CityName = cityName;
	}
	public String getDistrictName() {
		return DistrictName;
	}
	public void setDistrictName(String districtName) {
		DistrictName = districtName;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getStoreName() {
		return StoreName;
	}
	public void setStoreName(String storeName) {
		StoreName = storeName;
	}
	public String getSectionName() {
		return SectionName;
	}
	public void setSectionName(String sectionName) {
		SectionName = sectionName;
	}
	public String getSectionLocate() {
		return SectionLocate;
	}
	public void setSectionLocate(String sectionLocate) {
		SectionLocate = sectionLocate;
	}
	public String getWorkCard() {
		return WorkCard;
	}
	public void setWorkCard(String workCard) {
		WorkCard = workCard;
	}
	public String getCardBack() {
		return CardBack;
	}
	public void setCardBack(String cardBack) {
		CardBack = cardBack;
	}
	public String getCardFront() {
		return CardFront;
	}
	public void setCardFront(String cardFront) {
		CardFront = cardFront;
	}

	
	
}
