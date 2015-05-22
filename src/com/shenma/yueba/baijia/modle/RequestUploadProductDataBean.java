package com.shenma.yueba.baijia.modle;
/**  
 * @author gyj  
 * @version 创建时间：2015-5-22 下午6:04:20  
 * 程序的简单说明  上传商品信息 返回的数据
 */

public class RequestUploadProductDataBean {
	String id="";// : 商品编号,
    String image="";// : 320大小图片地址 如："http://xxx.xxx.xxx/fileupload/img/product/20130521/5b72da42-9f10-40bf-b84a-f6ebd68948e3_320x0.jpg",
    double price;// : 销售价格,
    String brand_name="";//品牌名称",
    String category_name="";//分类名称"
    String publishstatus_s="";// 发布状态描述 "未发布/已发布/全网发布"
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getBrand_name() {
		return brand_name;
	}
	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public String getPublishstatus_s() {
		return publishstatus_s;
	}
	public void setPublishstatus_s(String publishstatus_s) {
		this.publishstatus_s = publishstatus_s;
	}

}
