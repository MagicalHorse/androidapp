package com.shenma.yueba.yangjia.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.callback.SaveCallback;
import com.alibaba.sdk.android.oss.model.OSSException;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.shenma.yueba.R;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.baijia.modle.BaseRequest;
import com.shenma.yueba.constants.Constants;
import com.shenma.yueba.constants.HttpConstants;
import com.shenma.yueba.util.CustomProgressDialog;
import com.shenma.yueba.util.FileUtils;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ParserJson;
import com.shenma.yueba.util.PhotoUtils;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.SelectePhotoType;

/**
 * 标签搜索
 * 
 * @author a
 * 
 */
public class AddTagActivity extends BaseActivityWithTopView implements TextWatcher, OnClickListener {

	private EditText et_search;
	private ImageView iv_delete;//删除
	private PullToRefreshListView pull_refresh_list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_tag);
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		setTitle("添加标签");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AddTagActivity.this.finish();
			}
		});
		iv_delete = getView(R.id.iv_delete);
		iv_delete.setOnClickListener(this);
		et_search = getView(R.id.et_search);
		et_search.addTextChangedListener(this);
		pull_refresh_list = getView(R.id.pull_refresh_list);
		FontManager.changeFonts(mContext, et_search);
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		String content = arg0.toString();
		if(content.length()== 0 ){
			iv_delete.setVisibility(View.GONE);
		}else{
			iv_delete.setVisibility(View.VISIBLE);
		}
		
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_delete://清空搜索内容
			et_search.setText("");
			break;
		default:
			break;
		}
		
	}

}
