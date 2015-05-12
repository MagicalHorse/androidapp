package com.shenma.yueba.yangjia.modle;

import java.io.Serializable;

/**
 * @author gyj
 * @version 创建时间：2015-5-12 下午2:15:38 
 * 程序的简单说明 本类定义创建收货地址信息类 用于上传地址数据中 将 此对象转换成JSON格式
 */

public class ContactsAddressResponseBean implements Serializable {
	int Id;// 编号
	String ShippingContactPerson = "";// 联系人
	String ShippingAddress = "";// 联系人地址
	String ShippingContactPhone = "";// 联系人号码不能为空
	String ShippingZipCode = "";// 邮编不能为空
	String ShippingProvince = "";// 地址省不能为空
	int ShippingProvinceId;// 地址省份不能为空
	String ShippingCity = "";// 地址城市不能为空
	int ShippingCityId;// 地址城市不能为空
	int ShippingDistrictId;// 地址地区不能为空
	String ShippingDistrict = "";// 地址地区不能为空
	boolean IsDefault = false;// 是否默认地址

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getShippingContactPerson() {
		return ShippingContactPerson;
	}

	public void setShippingContactPerson(String shippingContactPerson) {
		ShippingContactPerson = shippingContactPerson;
	}

	public String getShippingAddress() {
		return ShippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		ShippingAddress = shippingAddress;
	}

	public String getShippingContactPhone() {
		return ShippingContactPhone;
	}

	public void setShippingContactPhone(String shippingContactPhone) {
		ShippingContactPhone = shippingContactPhone;
	}

	public String getShippingZipCode() {
		return ShippingZipCode;
	}

	public void setShippingZipCode(String shippingZipCode) {
		ShippingZipCode = shippingZipCode;
	}

	public String getShippingProvince() {
		return ShippingProvince;
	}

	public void setShippingProvince(String shippingProvince) {
		ShippingProvince = shippingProvince;
	}

	public int getShippingProvinceId() {
		return ShippingProvinceId;
	}

	public void setShippingProvinceId(int shippingProvinceId) {
		ShippingProvinceId = shippingProvinceId;
	}

	public String getShippingCity() {
		return ShippingCity;
	}

	public void setShippingCity(String shippingCity) {
		ShippingCity = shippingCity;
	}

	public int getShippingCityId() {
		return ShippingCityId;
	}

	public void setShippingCityId(int shippingCityId) {
		ShippingCityId = shippingCityId;
	}

	public int getShippingDistrictId() {
		return ShippingDistrictId;
	}

	public void setShippingDistrictId(int shippingDistrictId) {
		ShippingDistrictId = shippingDistrictId;
	}

	public String getShippingDistrict() {
		return ShippingDistrict;
	}

	public void setShippingDistrict(String shippingDistrict) {
		ShippingDistrict = shippingDistrict;
	}

	public boolean isIsDefault() {
		return IsDefault;
	}

	public void setIsDefault(boolean isDefault) {
		IsDefault = isDefault;
	}

}
