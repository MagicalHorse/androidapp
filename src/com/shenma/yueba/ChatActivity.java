package com.shenma.yueba;

/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.ClipboardManager;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.AffirmOrderActivity;
import com.shenma.yueba.baijia.activity.BaiJiaShareDataActivity;
import com.shenma.yueba.baijia.activity.CircleInfoActivity;
import com.shenma.yueba.baijia.adapter.ChattingListViewAdapter;
import com.shenma.yueba.baijia.dialog.CreateOrderDialog;
import com.shenma.yueba.baijia.modle.MyMessage;
import com.shenma.yueba.baijia.modle.ProductsDetailsInfoBean;
import com.shenma.yueba.baijia.modle.RequestProductDetailsInfoBean;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.db.DBHelper;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.MyPreference;
import com.shenma.yueba.util.NetUtils;
import com.shenma.yueba.util.SoftKeyboardUtil;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.PasteEditText;
import com.shenma.yueba.view.faceview.FaceView;
import com.shenma.yueba.view.faceview.FaceView.OnChickCallback;

/**
 * 聊天页面
 * 
 */
public class ChatActivity extends RoboActivity implements OnClickListener,OnChickCallback {
	private static final int REQUEST_CODE_EMPTY_HISTORY = 2;
	public static final int REQUEST_CODE_CONTEXT_MENU = 3;
	private static final int REQUEST_CODE_MAP = 4;
	public static final int REQUEST_CODE_TEXT = 5;
	public static final int REQUEST_CODE_VOICE = 6;
	public static final int REQUEST_CODE_PICTURE = 7;
	public static final int REQUEST_CODE_LOCATION = 8;
	public static final int REQUEST_CODE_NET_DISK = 9;
	public static final int REQUEST_CODE_FILE = 10;
	public static final int REQUEST_CODE_COPY_AND_PASTE = 11;
	public static final int REQUEST_CODE_PICK_VIDEO = 12;
	public static final int REQUEST_CODE_DOWNLOAD_VIDEO = 13;
	public static final int REQUEST_CODE_VIDEO = 14;
	public static final int REQUEST_CODE_DOWNLOAD_VOICE = 15;
	public static final int REQUEST_CODE_SELECT_USER_CARD = 16;
	public static final int REQUEST_CODE_SEND_USER_CARD = 17;
	public static final int REQUEST_CODE_CAMERA = 18;
	public static final int REQUEST_CODE_LOCAL = 19;
	public static final int REQUEST_CODE_CLICK_DESTORY_IMG = 20;
	public static final int REQUEST_CODE_GROUP_DETAIL = 21;
	public static final int REQUEST_CODE_SELECT_VIDEO = 23;
	public static final int REQUEST_CODE_SELECT_FILE = 24;
	public static final int REQUEST_CODE_ADD_TO_BLACKLIST = 25;

	public static final int RESULT_CODE_COPY = 1;
	public static final int RESULT_CODE_DELETE = 2;
	public static final int RESULT_CODE_FORWARD = 3;
	public static final int RESULT_CODE_OPEN = 4;
	public static final int RESULT_CODE_DWONLOAD = 5;
	public static final int RESULT_CODE_TO_CLOUD = 6;
	public static final int RESULT_CODE_EXIT_GROUP = 7;

	public static final int CHATTYPE_SINGLE = 1;
	public static final int CHATTYPE_GROUP = 2;

	public static final String COPY_IMAGE = "EASEMOBIMG";
	private PasteEditText mEditTextContent;
	private ArrayList<MyMessage> mList = new ArrayList<MyMessage>();
	private View buttonSend;
	private LinearLayout btnContainer;
	private int position;
	private ClipboardManager clipboard;
	private InputMethodManager manager;
	@Inject
	DBHelper dbhelper;// 数据库帮助类对象
	private int chatType;
	public static ChatActivity activityInstance = null;
	// 给谁发送消息
	private String toChatUsername;
	private File cameraFile;
	static int resendPos;
	private ImageView iv_emoticons_normal;
	private RelativeLayout edittext_layout;
	private ProgressBar loadmorePB;
	private boolean isloading;
	private final int pagesize = 20;
	private boolean haveMoreData = true;
	private Button btnMore;
	private FaceView fView;
	private PullToRefreshListView chat_list;
	TextView tv_top_right;//设置
	private ChattingListViewAdapter chatAdapter;// 聊天列表adapter
	RequestProductDetailsInfoBean bean;
	CreateOrderDialog createOrderDialog;
	String circleId=null;//圈子ID
	int buyerId=-1;//买手ID
	RelativeLayout chat_product_head_layout_include;//商品信息
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		buyerId=this.getIntent().getIntExtra("buyerId", -1);
		if(buyerId<0)
		{
			MyApplication.getInstance().showMessage(this, "数据错误");
			finish();
			return;
		}
		initView();
		initDataFromHistory();
	}

	/**
	 * 初始化历史数据
	 */
	private void initDataFromHistory() {
		// String userId = MyPreference
		// .getStringValue(ChatActivity.this,Constants.USERID);
		// String friendMobile = PublicMethod.spitJidBeforeAt(jid);
		//
		// if (dbhelper.selectAllMsgByUserIdAndMyId(userId, friendMobile) !=
		// null) {
		// listData = dbhelper.selectAllMsgByUserIdAndMyId(userId,
		// friendMobile);
		// }
		chatAdapter = new ChattingListViewAdapter(mList, this);
		// FriendListBean bean = dbhelper.selectFriendInfoById(jid);// 查询聊天的好友信息
		// MyMessage msg = dbhelper.getChatUserInfoById(PublicMethod
		// .spitJidBeforeAt(jid));// 查询非好友聊天的顾客信息
		// if (bean != null) {
		// tv_theme.setText((bean.getFriend_nickName() != null) ? bean
		// .getFriend_nickName() : PublicMethod
		// .nullToString(PublicMethod.spitJidBeforeAt(jid)));// 设置标题
		// chatAdapter.setImageForOther(bean.getFriend_headImage());// 设置头像
		// } else if (msg != null) {
		// tv_theme.setText((msg.getNickname() != null) ? msg.getNickname()
		// : PublicMethod.nullToString(PublicMethod
		// .spitJidBeforeAt(jid)));// 设置标题
		// chatAdapter.setImageForOther(msg.getHeadImg());// 设置头像
		// } else {
		// tv_theme.setText(PublicMethod.spitJidBeforeAt(jid));
		// }
		chat_list.setAdapter(chatAdapter);
		// chatList.setSelection(listData.size());
	}

	/**
	 * initView
	 */
	protected void initView() {
		chat_product_head_layout_include=(RelativeLayout)findViewById(R.id.chat_product_head_layout_include);
		tv_top_right=(TextView)findViewById(R.id.tv_top_right);
		if(this.getIntent().getStringExtra("circleId")!=null)
		{
			circleId=this.getIntent().getStringExtra("circleId");
		}
		if(circleId!=null)
		{
			tv_top_right.setVisibility(View.VISIBLE);
		}else
		{
			tv_top_right.setVisibility(View.GONE);
		}
		
		setProduct();
		
		tv_top_right.setOnClickListener(this);
		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		chat_list = (PullToRefreshListView) findViewById(R.id.chat_list);
		chat_list.setMode(Mode.PULL_FROM_START);
		chat_list.setAdapter(new ChattingListViewAdapter(mList, this));
		fView = (FaceView) findViewById(R.id.faceLayout);
		fView.setOnChickCallback(this);
		mEditTextContent = (PasteEditText) findViewById(R.id.et_sendmessage);
		edittext_layout = (RelativeLayout) findViewById(R.id.edittext_layout);
		buttonSend = findViewById(R.id.btn_send);
		buttonSend.setOnClickListener(this);
		btnContainer = (LinearLayout) findViewById(R.id.ll_btn_container);
		iv_emoticons_normal = (ImageView) findViewById(R.id.iv_emoticons_normal);
		iv_emoticons_normal.setOnClickListener(this);
		iv_emoticons_normal.setVisibility(View.VISIBLE);
		loadmorePB = (ProgressBar) findViewById(R.id.pb_load_more);
		btnMore = (Button) findViewById(R.id.btn_more);
		btnMore.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (btnContainer.getVisibility() != View.VISIBLE) {
					btnContainer.setVisibility(View.VISIBLE);
					hideKeyboard();
					hideFace();
				} else {
					btnContainer.setVisibility(View.GONE);
				}

			}
		});
		edittext_layout.setBackgroundResource(R.drawable.shape_linearlayout10);
		edittext_layout.requestFocus();
		mEditTextContent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				iv_emoticons_normal.setVisibility(View.VISIBLE);
				btnContainer.setVisibility(View.GONE);
			}
		});
		// 监听文字框
		mEditTextContent.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!TextUtils.isEmpty(s)) {
					btnMore.setVisibility(View.GONE);
					buttonSend.setVisibility(View.VISIBLE);
				} else {
					btnMore.setVisibility(View.VISIBLE);
					buttonSend.setVisibility(View.GONE);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

	}

	/**
	 * 选择文件
	 */
	private void selectFileFromLocal() {
		Intent intent = null;
		if (Build.VERSION.SDK_INT < 19) {
			intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("*/*");
			intent.addCategory(Intent.CATEGORY_OPENABLE);

		} else {
			intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		}
		startActivityForResult(intent, REQUEST_CODE_SELECT_FILE);
	}

	/**
	 * 从图库获取图片
	 */
	public void selectPicFromLocal() {
		Intent intent;
		if (Build.VERSION.SDK_INT < 19) {
			intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");

		} else {
			intent = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		}
		startActivityForResult(intent, REQUEST_CODE_LOCAL);
	}

	/**
	 * 点击清空聊天记录
	 * 
	 * @param view
	 */
	public void emptyHistory(View view) {
		startActivityForResult(
				new Intent(this, AlertDialog.class)
						.putExtra("titleIsCancel", true)
						.putExtra("msg", "是否清空所有聊天记录").putExtra("cancel", true),
				REQUEST_CODE_EMPTY_HISTORY);
	}

	/**
	 * 点击文字输入框
	 * 
	 * @param v
	 */
	public void editClick(View v) {
		if (btnContainer.getVisibility() == View.VISIBLE) {
			btnContainer.setVisibility(View.GONE);
		}
	}

	public List<String> getExpressionRes(int getSum) {
		List<String> reslist = new ArrayList<String>();
		for (int x = 1; x <= getSum; x++) {
			String filename = "ee_" + x;

			reslist.add(filename);

		}
		return reslist;

	}

	@Override
	protected void onDestroy() {
             super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (fView.getVisibility() == View.VISIBLE) {
			hideFace();
		}
	}

	/**
	 * 隐藏软键盘
	 */
	private void hideKeyboard() {
		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 返回
	 * 
	 * @param view
	 */
	public void back(View view) {
		finish();
	}

	/**
	 * 覆盖手机返回键
	 */
	@Override
	public void onBackPressed() {
		if (btnContainer.getVisibility() != View.VISIBLE
				&& fView.getVisibility() != View.VISIBLE) {
			super.onBackPressed();
		} else {
			if (btnContainer.getVisibility() == View.VISIBLE) {
				btnContainer.setVisibility(View.GONE);
			}
			if (fView.getVisibility() == View.VISIBLE) {
				fView.setVisibility(View.GONE);
			}
		}
	}

	/**
	 * listview滑动监听listener
	 * 
	 */
	private class ListScrollListener implements OnScrollListener {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			switch (scrollState) {
			case OnScrollListener.SCROLL_STATE_IDLE:
				if (view.getFirstVisiblePosition() == 0 && !isloading
						&& haveMoreData) {
					loadmorePB.setVisibility(View.VISIBLE);
					// sdk初始化加载的聊天记录为20条，到顶时去db里获取更多
					// List<EMMessage> messages;
					try {
						// 获取更多messges，调用此方法的时候从db获取的messages
						// sdk会自动存入到此conversation中
						if (chatType == CHATTYPE_SINGLE)
							;
						// messages =
						// conversation.loadMoreMsgFromDB(adapter.getItem(0).getMsgId(),
						// pagesize);
						else
							;
						// messages =
						// conversation.loadMoreGroupMsgFromDB(adapter.getItem(0).getMsgId(),
						// pagesize);
					} catch (Exception e1) {
						loadmorePB.setVisibility(View.GONE);
						return;
					}
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
					}
					// if (messages.size() != 0) {
					// // 刷新ui
					// adapter.notifyDataSetChanged();
					// listView.setSelection(messages.size() - 1);
					// if (messages.size() != pagesize)
					// haveMoreData = false;
					// } else {
					// haveMoreData = false;
					// }
					loadmorePB.setVisibility(View.GONE);
					isloading = false;

				}
				break;
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {

		}

	}

	@Override
	protected void onNewIntent(Intent intent) {
		// 点击notification bar进入聊天页面，保证只有一个聊天页面
		String username = intent.getStringExtra("userId");
		if (toChatUsername.equals(username))
			super.onNewIntent(intent);
		else {
			finish();
			startActivity(intent);
		}

	}

	public String getToChatUsername() {
		return toChatUsername;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_top_right://设置
			Intent intent=new Intent(this,CircleInfoActivity.class);
			intent.putExtra("circleId",circleId);
			startActivity(intent);
			break;
		case R.id.iv_emoticons_normal:// 点击显示表情框
			showOrHideIMM();
			break;
		case R.id.btn_send:// 发送消息
			String content = mEditTextContent.getText().toString().trim();
			if ("".equals(content)) {
				Toast.makeText(ChatActivity.this, "发送消息不能为空", 1000).show();
				return;
			}
			if (!NetUtils.isNetworkConnected(this)) {
				Toast.makeText(ChatActivity.this, "网络不可用", 1000).show();
				return;
			}
			dbhelper.saveTextMsg(MyPreference.getStringValue(ChatActivity.this,Constants.USERID), "", content, "false", "true",Constants.NORMAL, ToolsUtil.getCurrentTime());
			// dbhelper.saveLastMsg(
			// MyPreference.getStringValue(ChatActivity.this,Constants.USERID),
			// PublicMethod.spitJidBeforeAt(jid), content, "false",
			// "true", Constants.NORMAL, PublicMethod.getCurrentTime());
			// xmppservice.sendMsg(jid, content);
			setListDate(MyPreference.getStringValue(ChatActivity.this,Constants.USERID), "111", content, "false", "true",ToolsUtil.getCurrentTime());
			mEditTextContent.setText("");
			break;
		case R.id.btn_camera://拍照
			openCamera();
			break;
		case R.id.btn_picture://照片
			openPicture();
		    break;
		case R.id.btn_link://链接
			openLink();
		    break;
		case R.id.btn_collention://收藏
			openCollention();
		    break;
		default:
			break;
		}

	}
	
	/****
	 * 打开照相机并回去返回的图片 
	 * **/
	void openCamera()
	{
		Intent intent=new Intent();
		intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 200);  

	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK)
		{
			switch(requestCode)
			{
			case 200:
				cameraCallBack(data);//相机回调
				break;
			case 300://链接回调
				break;
			}
		}
	}
	
	/****
	 * 相机回调
	 * **/
	void cameraCallBack(Intent data)
	{
		if(data!=null)
		{
			Bundle bundle = data.getExtras();  
            Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式  
            if(bitmap!=null)
            {
            	ByteArrayOutputStream baos = new ByteArrayOutputStream();    
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                String str= Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
            }
		}
	}
	
	
	
	/****
	 * 打开照图片 
	 * **/
	void openPicture()
	{
		
	}
	
	/****
	 * 链接
	 * **/
	void openLink()
	{
		Intent intent=new Intent(ChatActivity.this,BaiJiaShareDataActivity.class);
		startActivityForResult(intent, 300);
	}
	
	/****
	 * 收藏
	 * **/
	void openCollention()
	{
		
	}
	
	

	/**
	 * 将自己发送的普通文字消息刷新到listview上
	 */
	private void setListDate(String from, String to, String content,
			String isLeft, String isRead, String msgTime) {
		MyMessage myMsg2 = new MyMessage();
		myMsg2.setBody(content);
		myMsg2.setOtherId(from);
		myMsg2.setIsLeft(isLeft);
		myMsg2.setMsgTime(msgTime);
		mList.add(myMsg2);
		chatAdapter.notifyDataSetChanged();
		// chat_list.setSelection(mList.size());
	}

	/**
	 * 显示表情
	 */
	private void showFace() {
		iv_emoticons_normal
				.setBackgroundResource(R.drawable.chatting_biaoqing_btn_enable);
		iv_emoticons_normal.setTag(1);
		fView.setVisibility(View.VISIBLE);
	}

	/**
	 * 隐藏表情
	 */
	private void hideFace() {
		iv_emoticons_normal
				.setBackgroundResource(R.drawable.chatting_biaoqing_btn_normal);
		iv_emoticons_normal.setTag(null);
		fView.setVisibility(View.GONE);
	}

	/**
	 * 控制显示或者隐藏表情
	 */
	private void showOrHideIMM() {
		if (iv_emoticons_normal.getTag() == null) {
			// 隐藏软键
			SoftKeyboardUtil.hide(ChatActivity.this, mEditTextContent);
			// 显示表情
			showFace();
			// 隐藏其他底部view
			btnContainer.setVisibility(View.GONE);
		} else {
			// 显示软键盘
			SoftKeyboardUtil.show(ChatActivity.this, mEditTextContent);
			// 隐藏表情
			hideFace();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		SoftKeyboardUtil.hide(ChatActivity.this,
				ChatActivity.this.getCurrentFocus());
		int height = fView.getTop();
		int y = (int) event.getY() + 50;
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (y < height) {
				if (fView.getVisibility() == View.VISIBLE) {
					hideFace();
					return true;
				} else {
					return super.onTouchEvent(event);
				}
			}
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void onChick(int arg1, int resId, String arg2) {
		SpannableString ss = new SpannableString(arg2);
		Drawable d = getResources().getDrawable(resId);
		d.setBounds(0, 0, 50, 50);// 设置表情图片的显示大小
		ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BOTTOM);
		ss.setSpan(span, 0, arg2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		mEditTextContent.getText().insert(mEditTextContent.getSelectionStart(),
				ss);
	}

	/*****
	 * 设置商品信息
	 * ***/
	void setProduct() {
		if(this.getIntent().getSerializableExtra("DATA")==null)
		{
			chat_product_head_layout_include.setVisibility(View.GONE);
			return;
		}else
		{
			chat_product_head_layout_include.setVisibility(View.VISIBLE);
		}
		
		// 产品图片
		ImageView chat_product_head_layout_imageview = (ImageView) findViewById(R.id.chat_product_head_layout_imageview);
		// 产品名称
		TextView chat_product_head_layout_name_textview = (TextView) findViewById(R.id.chat_product_head_layout_name_textview);
		// 价格
		TextView chat_product_head_layout_price_textview = (TextView) findViewById(R.id.chat_product_head_layout_price_textview);
		// 立即购买
		Button chat_product_head_layout_button = (Button) findViewById(R.id.chat_product_head_layout_button);
		chat_product_head_layout_button.setEnabled(false);
		chat_product_head_layout_button.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if(createOrderDialog!=null)
						{
							createOrderDialog.cancel();
						}
						createOrderDialog=new CreateOrderDialog(ChatActivity.this, (RequestProductDetailsInfoBean)v.getTag());
						createOrderDialog.show();
					}
				});
		Object obj = this.getIntent().getSerializableExtra("DATA");
		if (obj != null && obj instanceof RequestProductDetailsInfoBean) {
			bean = (RequestProductDetailsInfoBean) obj;
			ProductsDetailsInfoBean productsDetailsInfoBean = bean.getData();
			if (productsDetailsInfoBean != null) {
				String[] pic_array = productsDetailsInfoBean.getProductPic();
				chat_product_head_layout_button.setTag(bean);
				if (pic_array != null && pic_array.length > 0) {
					MyApplication
							.getInstance()
							.getImageLoader()
							.displayImage(
									ToolsUtil.nullToString(ToolsUtil.getImage(
											pic_array[0], 320, 0)),
									chat_product_head_layout_imageview);
				}

				chat_product_head_layout_name_textview
						.setText(ToolsUtil.nullToString(productsDetailsInfoBean
								.getProductName()));
				chat_product_head_layout_price_textview
						.setText(ToolsUtil.nullToString("￥"
								+ productsDetailsInfoBean.getPrice()));
				chat_product_head_layout_button.setEnabled(true);
			}

		} else {
			chat_product_head_layout_include.setVisibility(View.GONE);
			chat_product_head_layout_button.setEnabled(false);
		}
         FontManager.changeFonts(this, chat_product_head_layout_name_textview,chat_product_head_layout_name_textview,chat_product_head_layout_price_textview,chat_product_head_layout_button);
	}
}