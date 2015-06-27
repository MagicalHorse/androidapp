package com.shenma.yueba.baijia.modle;

import java.io.Serializable;

/**  
 * @author gyj  
 * @version 创建时间：2015-6-8 下午7:43:09  
 * 程序的简单说明  
 */

public class MyFavoriteProductListInfo implements Serializable{
	int Id;// 产品编号
	String Name;// 产品名称
	MyFavoriteProductListPic  pic=new MyFavoriteProductListPic();
	double Price;//价格
	MyFavoriteProductListLikeUser LikeUser=new MyFavoriteProductListLikeUser();
	
	int FavoriteCount;//关注的人数
	boolean IsFavorite=false; //是否关注

	
	
	
	public int getFavoriteCount() {
		return FavoriteCount;
	}
	public void setFavoriteCount(int favoriteCount) {
		FavoriteCount = favoriteCount;
	}
	public boolean isIsFavorite() {
		return IsFavorite;
	}
	public void setIsFavorite(boolean isFavorite) {
		IsFavorite = isFavorite;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public MyFavoriteProductListPic getPic() {
		return pic;
	}
	public void setPic(MyFavoriteProductListPic pic) {
		this.pic = pic;
	}
	public double getPrice() {
		return Price;
	}
	public void setPrice(double price) {
		Price = price;
	}
	public MyFavoriteProductListLikeUser getLikeUser() {
		return LikeUser;
	}
	public void setLikeUser(MyFavoriteProductListLikeUser likeUser) {
		LikeUser = likeUser;
	}

}
