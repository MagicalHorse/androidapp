package com.shenma.yueba.baijia.modle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-22 下午5:31:07  
 * 程序的简单说明  上传商品信息(买手)请求数据类
 */

public class ResponseUploadProductInfoBean implements Serializable{
	int Id;//编号 必填",
    int Brand_Id;//品牌编号 必填",
    int Sales_Code;//"销售码 必填",
    int Sku_Code;//"货号 必填",
    double Price;//"销售价格 必填",
    int Tag_Id;//"分类 编号 必填",
    int ProductType;// "商品类型 2 自拍商品 4 通用商品",
    List<ProductColorsInfoBean> Colors=new ArrayList<ProductColorsInfoBean>();
    double UnitPrice;//吊牌价格",
    String AccessToken="";//微信accessToken 删除"
    String Image_mediaIds="";//" : 图片编号 必填 如：[1001, 1002, 1003],删除
    String Images="";// 图片URL数组 四张图片 如果是微信服务器上的图片，url为：http://file.api.weixin.qq.com/cgi-bin/media/get?access_token={token}&media_id={media}
    String Desc="";//描述",
    String Name="";//商品名称 必填"
    String PublishStatus="";//商品发布状态 默认为未发布 （0：未发布 1：发布 2：全网发布）"
    List<ProductExtendPropertyBean> ExtendPropertys=new ArrayList<ProductExtendPropertyBean>();
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getBrand_Id() {
		return Brand_Id;
	}
	public void setBrand_Id(int brand_Id) {
		Brand_Id = brand_Id;
	}
	public int getSales_Code() {
		return Sales_Code;
	}
	public void setSales_Code(int sales_Code) {
		Sales_Code = sales_Code;
	}
	public int getSku_Code() {
		return Sku_Code;
	}
	public void setSku_Code(int sku_Code) {
		Sku_Code = sku_Code;
	}
	public double getPrice() {
		return Price;
	}
	public void setPrice(double price) {
		Price = price;
	}
	public int getTag_Id() {
		return Tag_Id;
	}
	public void setTag_Id(int tag_Id) {
		Tag_Id = tag_Id;
	}
	public int getProductType() {
		return ProductType;
	}
	public void setProductType(int productType) {
		ProductType = productType;
	}
	public List<ProductColorsInfoBean> getColors() {
		return Colors;
	}
	public void setColors(List<ProductColorsInfoBean> colors) {
		Colors = colors;
	}
	public double getUnitPrice() {
		return UnitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		UnitPrice = unitPrice;
	}
	public String getAccessToken() {
		return AccessToken;
	}
	public void setAccessToken(String accessToken) {
		AccessToken = accessToken;
	}
	public String getImage_mediaIds() {
		return Image_mediaIds;
	}
	public void setImage_mediaIds(String image_mediaIds) {
		Image_mediaIds = image_mediaIds;
	}
	public String getImages() {
		return Images;
	}
	public void setImages(String images) {
		Images = images;
	}
	public String getDesc() {
		return Desc;
	}
	public void setDesc(String desc) {
		Desc = desc;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getPublishStatus() {
		return PublishStatus;
	}
	public void setPublishStatus(String publishStatus) {
		PublishStatus = publishStatus;
	}
	public List<ProductExtendPropertyBean> getExtendPropertys() {
		return ExtendPropertys;
	}
	public void setExtendPropertys(List<ProductExtendPropertyBean> extendPropertys) {
		ExtendPropertys = extendPropertys;
	}
    
}
