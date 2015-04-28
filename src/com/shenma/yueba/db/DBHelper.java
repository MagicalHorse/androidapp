package com.shenma.yueba.db;

/**
 * 数据库的工具类
 */
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.constants.Constants;

@Singleton
public class DBHelper {
	private SqlLiteHelper helper;
	private String userid;
	@Inject
	Context context;

	// /**
	// * 切换用户方法
	// * @param userid 要切换到的用户id
	// */
	// public void switchToUser(String userid){
	// if(helper!=null){
	// if(userid.equals(this.userid)){
	// return;
	// }else{
	// getHelper().close();
	// }
	// }
	// this.userid=userid;
	// helper = new SqlLiteHelper(this, context,userid);
	// }
	//
	/**
	 * 返回数据库帮助类对象的单例
	 * 
	 * @return
	 */
	public SqlLiteHelper getHelper() {
		if (helper == null) {
			helper = new SqlLiteHelper(this, context, userid);
		}
		return helper;
	}
	//
	// /**
	// * 判断是否包含某个好友的记录
	// */
	// public boolean judgeExistFromContactInfoById(String id) {
	// SQLiteDatabase db = getHelper().getReadableDatabase();
	// Cursor cur = db.rawQuery("select "+Constants.CONTACTS_ID+" from "
	// +Constants.CONTACTS_INFO+" where "+Constants.CONTACTS_ID +" = ?", new
	// String[]{id});
	// boolean exist = cur.moveToFirst();
	// cur.close();
	// return exist;
	// }
	//
	// /**
	// * 保存好友信息
	// * @param frindType 好友类型，店还是技师
	// * @param id 好友id
	// * @param sex 好友性别
	// * @param nickName 好友昵称
	// * @param distance 好友距离
	// * @param headImage 好友头像
	// * @param jid 好友的Jid,xmpp标记用户的唯一标识
	// * @param workstate 忙闲状态
	// * @param signature 签名
	// * @return
	// */
	// public long SaveFriendsInfo(String frindType,String id, String sex,
	// String nickName, String distance, String headImage, String jid,
	// String workstate,String signature) {
	// long count = 0;
	// SQLiteDatabase db = getHelper().getWritableDatabase();
	// if(judgeExistFromContactInfoById(id)){
	// String sql =
	// "delete from "+Constants.CONTACTS_INFO+" where "+Constants.CONTACTS_ID+"="+"'"+id+"'";
	// db.execSQL(sql);
	// }
	// ContentValues values = new ContentValues();
	// values.put(Constants.FRIEND_TYPE,null == id ? "" : frindType);
	// values.put(Constants.CONTACTS_ID,null == id ? "" : id);
	// values.put(Constants.CONTACTS_SEX,null == sex ? "" : sex);
	// values.put(Constants.CONTACTS_NICKNAME,null ==nickName ? "" : nickName);
	// values.put(Constants.CONTACTS_DISTANCE,null ==distance? "" : distance);
	// values.put(Constants.CONTACTS_HEADIMAGE,null ==headImage? "" :
	// headImage);
	// values.put(Constants.CONTACTS_JID,null ==jid? "" : jid);
	// values.put(Constants.CONTACTS_WORKSTATE,null ==workstate? "" :
	// workstate);
	// values.put(Constants.CONTACTS_SIGNATURE,null ==signature? "" :
	// signature);
	// count = db.insert(Constants.CONTACTS_INFO, null,values);
	// return count;
	// }
	//
	// /**
	// * 删除好友信息
	// */
	// public void DeleteFriendsInfo() {
	// SQLiteDatabase db = getHelper().getWritableDatabase();
	// String sql = "delete  from  "+Constants.CONTACTS_INFO;
	// db.execSQL(sql);
	// }
	//
	//
	//
	//
	// /**
	// * 从好友库中查询好友信息
	// * @param contacts_jid 要查询的好友的id
	// * @return
	// */
	// public MyMessage selectFriendInfo(String contacts_jid) {
	// SQLiteDatabase db = getHelper().getReadableDatabase();
	// MyMessage msg = null;
	// Cursor cur = db.rawQuery("select * from "+Constants.CONTACTS_INFO
	// +" where "+Constants.CONTACTS_JID+" = ?",new String[]{contacts_jid});
	// for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
	// msg = new MyMessage();
	// msg.setId(cur.getInt(cur.getColumnIndex("mainId")));
	// msg.setNickname(cur.getString(cur.getColumnIndex(Constants.CONTACTS_NICKNAME)));
	// msg.setHeadImg(cur.getString(cur.getColumnIndex(Constants.CONTACTS_HEADIMAGE)));
	// }
	// cur.close();
	// return msg;
	// }
	//
	//
	// /**
	// * 存储文字聊天消息
	// * @param myid 用户自己的id
	// * @param otherId 和自己聊天的人的id
	// * @param MsgContent 消息内容
	// * @param isLeft 是不是展现在聊天窗口的左边，也就是别人，而不是自己
	// * @param isRead 是否已经读过
	// * @param msgType 消息类型
	// * @param msgTime 消息事件
	// * @return
	// */
	// public long saveTextMsg(String myid, String otherId, String MsgContent,
	// String isLeft, String isRead,String msgType,String msgTime) {
	// SQLiteDatabase db = getHelper().getWritableDatabase();
	// ContentValues values = new ContentValues();
	// values.put(Constants.MYID,null == myid ? "" : myid);
	// values.put(Constants.OTHERID,null == otherId ? "" : otherId);
	// values.put(Constants.MsgContent,null == MsgContent ? "" : MsgContent);
	// values.put(Constants.IsLeft,null == isLeft ? "" : isLeft);
	// values.put(Constants.IsRead,null == isRead ? "" : isRead);
	// values.put(Constants.MSG_TYPE,null == msgType ? "" : msgType);
	// values.put(Constants.MSG_TIME,null == msgTime ? "" : msgTime);
	// long count = db.insert(Constants.MsgInfo,null, values);
	// return count;
	// }
	//
	//
	//
	//
	//
	// /**
	// * 存储音频聊天消息（目前该功能已经屏蔽）
	// * @param myid 用户自己的id
	// * @param otherId 和自己聊天的人的id
	// * @param MsgContent 消息内容
	// * @param isLeft 是不是展现在聊天窗口的左边，也就是别人，而不是自己
	// * @param isRead 是否已经读过
	// * @param msgType 消息类型
	// * @param msgTime 消息事件
	// * @param audioPath 语音路径
	// * @return
	// */
	// public long saveAudioMsg(String myId, String otherId, String MsgContent,
	// String isLeft, String isRead,String msgType,String msgTime,String
	// audioPath) {
	// SQLiteDatabase db = getHelper().getWritableDatabase();
	// ContentValues values = new ContentValues();
	// values.put(Constants.MYID,null == myId ? "" : myId);
	// values.put(Constants.OTHERID,null == otherId ? "" : otherId);
	// values.put(Constants.MsgContent,null == MsgContent ? "" : MsgContent);
	// values.put(Constants.IsLeft,null == isLeft ? "" : isLeft);
	// values.put(Constants.IsRead,null == isRead ? "" : isRead);
	// values.put(Constants.MSG_TYPE,null == msgType ? "" : msgType);
	// values.put(Constants.MSG_TIME,null == msgTime ? "" : msgTime);
	// values.put(Constants.AUDIO_PATH,null == audioPath ? "" : audioPath);
	// long count = db.insert(Constants.MsgInfo,null, values);
	// return count;
	// }
	//
	//
	// /**
	// * 存储图片聊天消息
	// * @param myid 用户自己的id
	// * @param otherId 和自己聊天的人的id
	// * @param MsgContent 消息内容
	// * @param isLeft 是不是展现在聊天窗口的左边，也就是别人，而不是自己
	// * @param isRead 是否已经读过
	// * @param msgType 消息类型
	// * @param msgTime 消息事件
	// * @param imagePath 图片路径
	// * @param lon 经度
	// * @param lat 纬度
	// * @return
	// */
	// public long saveImageMsg(String myId, String otherId, String MsgContent,
	// String isLeft, String isRead,String msgType,String msgTime,String
	// imagePath,String lon,String lat) {
	// SQLiteDatabase db = getHelper().getWritableDatabase();
	// ContentValues values = new ContentValues();
	// values.put(Constants.MYID,null == myId ? "" : myId);
	// values.put(Constants.OTHERID,null == otherId ? "" : otherId);
	// values.put(Constants.MsgContent,null == MsgContent ? "" : MsgContent);
	// values.put(Constants.IsLeft,null == isLeft ? "" : isLeft);
	// values.put(Constants.IsRead,null == isRead ? "" : isRead);
	// values.put(Constants.MSG_TYPE,null == msgType ? "" : msgType);
	// values.put(Constants.MSG_TIME,null == msgTime ? "" : msgTime);
	// values.put(Constants.IMAGE_PATH,null == imagePath ? "" : imagePath);
	// values.put(Constants.LON,null == lon ? "" : lon);
	// values.put(Constants.LAT,null == lat ? "" : lat);
	// long count = db.insert(Constants.MsgInfo,null, values);
	// return count;
	// }
	//
	//
	// /**
	// * 存储所有人聊天最后一条消息
	// * @param myid 用户自己的id
	// * @param otherId 和自己聊天的人的id
	// * @param MsgContent 消息内容
	// * @param isLeft 是不是展现在聊天窗口的左边，也就是别人，而不是自己
	// * @param isRead 是否已经读过
	// * @param msgType 消息类型
	// * @param msgTime 消息事件
	// * @return
	// */
	// public boolean saveLastMsg(String myId, String otherId, String
	// MsgContent,
	// String isLeft, String isRead,String msgType,String msgTime) {
	// SQLiteDatabase db = getHelper().getWritableDatabase();
	// if(!judgeExistFromMsgInfoByOtherPhone(otherId)){
	// ContentValues values = new ContentValues();
	// values.put(Constants.MYID,null == myId ? "" : myId);
	// values.put(Constants.OTHERID,null == otherId ? "" : otherId);
	// values.put(Constants.MsgContent,null == MsgContent ? "" : MsgContent);
	// values.put(Constants.IsLeft,null == isLeft ? "" : isLeft);
	// values.put(Constants.IsRead,null == isRead ? "" : isRead);
	// values.put(Constants.MSG_TYPE,null == msgType ? "" : msgType);
	// values.put(Constants.MSG_TIME,null == msgTime ? "" : msgTime);
	// long count = db.insert(Constants.MsgInfoForLastMsg,null, values);
	// return count>0 ? true:false;
	// }else{
	// ContentValues values = new ContentValues();
	// values.put(Constants.MsgContent, MsgContent);
	// values.put(Constants.MSG_TIME, msgTime);
	// int count = db.update(Constants.MsgInfoForLastMsg, values,
	// Constants.OTHERID + " = ? ", new String[]{otherId});
	// return count>0 ? true:false;
	// }
	//
	// }
	//
	//
	// /**
	// * 判断数据库里是否包含和该人的聊天记录
	// * @param otherId 要查询的人的id
	// * @return
	// */
	// public boolean judgeExistFromMsgInfoByOtherPhone(String otherId){
	// SQLiteDatabase db = getHelper().getReadableDatabase();
	// ContentValues values = new ContentValues();
	// String selectStr =Constants.OTHERID+" = ?";
	// Cursor cur = db.query(Constants.MsgInfoForLastMsg, null,selectStr,new
	// String[] {otherId}, null, null, null);
	// if(!cur.moveToFirst()){
	// return false;
	// }else{
	// return true;
	// }
	// }
	//
	// /**
	// * 根据用户jid查询当前聊天界面的历史消息
	// * @param myId 用户自己的id
	// * @param otherId 要查询的人的id
	// * @return
	// */
	// public ArrayList<MyMessage> selectAllMsgByUserIdAndMyId(String
	// myId,String otherId) {
	// SQLiteDatabase db = getHelper().getReadableDatabase();
	// ArrayList<MyMessage> listData = new ArrayList<MyMessage>();
	// MyMessage msg;
	// String selectStr = Constants.MYID+" = ? and "+Constants.OTHERID+" = ?";
	// Cursor cur = db.query(Constants.MsgInfo, null,selectStr,new String[] {
	// myId, otherId}, null, null, null);
	// for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
	// msg = new MyMessage();
	// String isLeft = cur.getString(cur.getColumnIndex(Constants.IsLeft));
	// msg.setId(cur.getInt(cur.getColumnIndex("mainId")));
	// msg.setIsLeft(isLeft);
	// msg.setBody(cur.getString(cur.getColumnIndex(Constants.MsgContent)));
	// msg.setMsgTime(cur.getString(cur.getColumnIndex(Constants.MSG_TIME)));
	// msg.setAudioPath(cur.getString(cur.getColumnIndex(Constants.AUDIO_PATH)));
	// msg.setImagePath(cur.getString(cur.getColumnIndex(Constants.IMAGE_PATH)));
	// listData.add(msg);
	// }
	// cur.close();
	// return listData;
	// }
	//
	//
	// /**
	// * 删除消息库里的消息
	// * @param myId 用户自己的id
	// * @param otherId 要删除的人的id
	// * @return
	// */
	// public boolean deleteMsgById(String myId,String otherId){
	// SQLiteDatabase db = getHelper().getWritableDatabase();
	// String selectStr = Constants.MYID+" = ? and "+Constants.OTHERID+" = ?";
	// int result = db.delete(Constants.MsgInfo, selectStr, new
	// String[]{myId,otherId});
	// if(result>0){
	// return true;
	// }else{
	// return false;
	// }
	// }
	//
	//
	//
	//
	// /**
	// * 删除最后一条消息库里的消息，也就是消息列表里的消息
	// * @param myId 用户自己的id
	// * @param otherId 要删除的人的id
	// * @return
	// */
	// public boolean deleteLastMsgById(String myId,String otherId){
	// SQLiteDatabase db = getHelper().getWritableDatabase();
	// String selectStr = Constants.MYID+" = ? and "+Constants.OTHERID+" = ?";
	// int result = db.delete(Constants.MsgInfoForLastMsg, selectStr, new
	// String[]{myId,otherId});
	// if(result>0){
	// return true;
	// }else{
	// return false;
	// }
	// }
	//
	//
	// /**
	// * 查询每个人聊天的最后一条记录，用于消息列表展示
	// * @param myId 用户自己的id
	// * @return
	// */
	// public ArrayList<MyMessage> selectLastMsg(String myId) {
	// if(helper == null){
	// helper = MyApplication.getDbHelp().getHelper();
	// }
	// SQLiteDatabase db = helper.getReadableDatabase();
	// ArrayList<MyMessage> listData = new ArrayList<MyMessage>();
	// MyMessage msg;
	// Cursor cur = db.rawQuery("select * from "+Constants.MsgInfoForLastMsg
	// +" where "+Constants.MYID+" = ?",new String[]{myId});
	// for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
	// msg = new MyMessage();
	// String isLeft = cur.getString(cur.getColumnIndex(Constants.IsLeft));
	// msg.setId(cur.getInt(cur.getColumnIndex("mainId")));
	// msg.setIsLeft(isLeft);
	// msg.setOtherId(cur.getString(cur.getColumnIndex(Constants.OTHERID)));
	// msg.setMsgTime(cur.getString(cur.getColumnIndex(Constants.MSG_TIME)));
	// msg.setBody(cur.getString(cur.getColumnIndex(Constants.MsgContent)));
	// msg.setMsgtype(cur.getString(cur.getColumnIndex(Constants.MSG_TYPE)));
	// listData.add(msg);
	// }
	// cur.close();
	// return listData;
	// }
	//
	//
	//
	//
	// /**
	// * 根据id修改和某人聊天的消息为已读
	// * @param otherId 要修改的人的id
	// * @return
	// */
	// public boolean updateIsReadStateById(String otherId) {
	// SQLiteDatabase db = getHelper().getWritableDatabase();
	// ContentValues values = new ContentValues();
	// values.put(Constants.IsRead, "true");
	// int count = db.update(Constants.MsgInfo, values, Constants.OTHERID +
	// " = ? ", new String[]{otherId});
	// return count>0 ? true:false;
	// }
	//
	//
	// /**
	// * 查询好友列表信息
	// */
	// public ArrayList<ArrayList<FriendListBean>> selectFriendList(){
	// SQLiteDatabase db = getHelper().getReadableDatabase();
	// ArrayList<ArrayList<FriendListBean>> allList = new
	// ArrayList<ArrayList<FriendListBean>>();
	// ArrayList<FriendListBean> massList = new ArrayList<FriendListBean>();
	// ArrayList<FriendListBean> storeList = new ArrayList<FriendListBean>();
	// FriendListBean bean;
	// Cursor cur = db.rawQuery("select * from "+Constants.CONTACTS_INFO, null);
	// for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
	// bean = new FriendListBean();
	// String friendType =
	// cur.getString(cur.getColumnIndex(Constants.FRIEND_TYPE));
	// if(Constants.IsMass.equals(friendType)){//是技师
	// bean.setMass_id(cur.getString(cur.getColumnIndex(Constants.CONTACTS_ID)));
	// }
	// if(Constants.IsStore.equals(friendType)){//是店面
	// bean.setStore_id(cur.getString(cur.getColumnIndex(Constants.CONTACTS_ID)));
	// }
	// if(Constants.IsUser.equals(friendType)){//是客人
	// bean.setUser_id(cur.getString(cur.getColumnIndex(Constants.CONTACTS_ID)));
	// }
	// bean.setFriend_distance(cur.getString(cur.getColumnIndex(Constants.CONTACTS_DISTANCE)));
	// bean.setFriend_headImage(cur.getString(cur.getColumnIndex(Constants.CONTACTS_HEADIMAGE)));
	// bean.setFriend_jid(cur.getString(cur.getColumnIndex(Constants.CONTACTS_JID)));
	// bean.setFriend_nickName(cur.getString(cur.getColumnIndex(Constants.CONTACTS_NICKNAME)));
	// bean.setFriend_sex(cur.getString(cur.getColumnIndex(Constants.CONTACTS_SEX)));
	// bean.setFriend_workstate(cur.getString(cur.getColumnIndex(Constants.CONTACTS_WORKSTATE)));
	// bean.setFriend_signature(cur.getString(cur.getColumnIndex(Constants.CONTACTS_SIGNATURE)));
	// if(Constants.IsMass.equals(friendType)){//是技师
	// massList.add(bean);
	// }
	// if(Constants.IsStore.equals(friendType)){//是店面
	// storeList.add(bean);
	// }
	// }
	// allList.add(0, storeList);
	// allList.add(1, massList);
	// cur.close();
	// return allList;
	// }
	//
	//
	// /**
	// * 根据id查询好友信息
	// * @param id 好友id
	// * @return
	// */
	// public FriendListBean selectFriendInfoById(String id){
	// SQLiteDatabase db = helper.getReadableDatabase();
	// FriendListBean bean = null;
	// String selectStr = "select * from "+Constants.CONTACTS_INFO
	// +" where "+Constants.CONTACTS_JID + " = ? ";
	// Cursor cur = db.rawQuery(selectStr, new String[]{id});
	// for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
	// bean = new FriendListBean();
	// String friendType =
	// cur.getString(cur.getColumnIndex(Constants.FRIEND_TYPE));
	// if(Constants.IsMass.equals(friendType)){//是技师
	// bean.setMass_id(cur.getString(cur.getColumnIndex(Constants.CONTACTS_ID)));
	// }
	// if(Constants.IsStore.equals(friendType)){//是店面
	// bean.setStore_id(cur.getString(cur.getColumnIndex(Constants.CONTACTS_ID)));
	// }
	// if(Constants.IsUser.equals(friendType)){//是客人
	// bean.setUser_id(cur.getString(cur.getColumnIndex(Constants.CONTACTS_ID)));
	// }
	// bean.setFriend_distance(cur.getString(cur.getColumnIndex(Constants.CONTACTS_DISTANCE)));
	// bean.setFriend_headImage(cur.getString(cur.getColumnIndex(Constants.CONTACTS_HEADIMAGE)));
	// bean.setFriend_jid(cur.getString(cur.getColumnIndex(Constants.CONTACTS_JID)));
	// bean.setFriend_nickName(cur.getString(cur.getColumnIndex(Constants.CONTACTS_NICKNAME)));
	// bean.setFriend_sex(cur.getString(cur.getColumnIndex(Constants.CONTACTS_SEX)));
	// bean.setFriend_workstate(cur.getString(cur.getColumnIndex(Constants.CONTACTS_WORKSTATE)));
	// }
	// cur.close();
	// return bean;
	// }
	//
	// /**
	// * 根据id删除好友
	// * @param friendId 好友id
	// * @return
	// */
	// public boolean deleteFriendInfoById(String friendId){
	// SQLiteDatabase db = helper.getReadableDatabase();
	// int result = db.delete(Constants.CONTACTS_INFO, Constants.CONTACTS_JID +
	// " = ? ",new String[] { friendId });
	// if(result>0){
	// return true;
	// }else{
	// return false;
	// }
	// }
	//
	// /**
	// * 存储给本店聊天的人的信息
	// * @param friend_sex 好友性别
	// * @param friend_nickname 好友昵称
	// * @param friend_distance 好友距离
	// * @param friend_head_image 好友图片
	// * @param user_id 自己id
	// * @param friend_jid 好友id
	// * @return
	// */
	// public boolean saveChatUserInfo(String friend_sex, String
	// friend_nickname,
	// String friend_distance, String friend_head_image, String user_id,
	// String friend_jid) {
	// SQLiteDatabase db = getHelper().getWritableDatabase();
	// ContentValues values = new ContentValues();
	// values.put(Constants.FRIEND_SEX, null == friend_sex ? "" : friend_sex);
	// values.put(Constants.FRIEND_NICKNAME, null == friend_nickname ? ""
	// : friend_nickname);
	// values.put(Constants.FRIEND_DISTANCE, null == friend_distance ? ""
	// : friend_distance);
	// values.put(Constants.FRIEND_HEAD_IMAGE, null == friend_head_image ? ""
	// : friend_head_image);
	// values.put(Constants.USER_ID, null == user_id ? "" : user_id);
	// values.put(Constants.FRIEND_JID, null == friend_jid ? "" : friend_jid);
	// long count = db.insert(Constants.CHAT_USER_INFO, null, values);
	// return count > 0 ? true : false;
	// }
	//
	//
	//
	//
	//
	// /**
	// * 根据id查询用户信息
	// * @param id 要查询的人的id
	// * @return
	// */
	// public MyMessage getChatUserInfoById(String id) {
	// SQLiteDatabase db = getHelper().getReadableDatabase();
	// MyMessage msg = null;
	// Cursor cur = db.rawQuery("select * from " + Constants.CHAT_USER_INFO
	// + " where " + Constants.FRIEND_JID + " = ?",
	// new String[] { id });
	// for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
	// msg = new MyMessage();
	// msg.setId(cur.getInt(cur.getColumnIndex("mainId")));
	// msg.setNickname(cur.getString(cur
	// .getColumnIndex(Constants.FRIEND_NICKNAME)));
	// msg.setHeadImg(cur.getString(cur
	// .getColumnIndex(Constants.FRIEND_HEAD_IMAGE)));
	// msg.setOtherId(id);
	// }
	// cur.close();
	// return msg;
	// }
}
