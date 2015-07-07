package im.form;

import java.io.Serializable;
/*****
 * 信息接收对象
 * ***/
public class RequestMessageBean implements Serializable{
	
	public final static String type_img="img";//图片
	public final static String type_produtc_img="product_img";//商品图片
	public final static String notice="notice";//notice
	public final static String type_empty="";//空串的是普通消息。
	
	int __v;
	int fromUserId;
	int toUserId;
	String roomId="";
	String userName="";
	/****
	 * 图片是img    商品图片是product_img    广播是notice    空串的是普通消息。
	 * ***/
	String type="";
	int productId;
	String body="";
	String _id="";
	String creationDate="";
	
	String Id;// 消息id
	String userIp;
	
	String logo="";//用户头像
    String sharelink="";//如果是商品，这里显示商品链接
	
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getSharelink() {
		return sharelink;
	}
	public void setSharelink(String sharelink) {
		this.sharelink = sharelink;
	}
	public int get__v() {
		return __v;
	}
	public void set__v(int __v) {
		this.__v = __v;
	}
	public int getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(int fromUserId) {
		this.fromUserId = fromUserId;
	}
	public int getToUserId() {
		return toUserId;
	}
	public void setToUserId(int toUserId) {
		this.toUserId = toUserId;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
}
