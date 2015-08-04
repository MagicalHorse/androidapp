package com.shenma.yueba.yangjia.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.baijia.adapter.ProductTagsListAdapter;
import com.shenma.yueba.baijia.modle.BaseRequest;
import com.shenma.yueba.baijia.modle.TagListBackBean;
import com.shenma.yueba.baijia.modle.TagListItemBean;
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
import com.umeng.analytics.MobclickAgent;

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
	private String type;
	private ProductTagsListAdapter adapter;
	private List<TagListItemBean> mList = new ArrayList<TagListItemBean>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);//加入回退栈
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_tag);
		super.onCreate(savedInstanceState);
		getIntentData();
		initView();
	}

	
	
	private void getIntentData() {
		type = getIntent().getStringExtra("type");
		
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
		iv_delete.setVisibility(View.GONE);
		et_search = getView(R.id.et_search);
		et_search.addTextChangedListener(this);
		pull_refresh_list = getView(R.id.pull_refresh_list);
		adapter = new ProductTagsListAdapter(mContext, mList);
		pull_refresh_list.setAdapter(adapter);
		pull_refresh_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent = new Intent();
				String id = mList.get(position-1).getId();
				String name = mList.get(position-1).getName();
				intent.putExtra("id", id);
				intent.putExtra("type", "1".equals(type)?"51":"50");
				if(mList.get(position-1).isNewTag()){//如果是添加新标签
					intent.putExtra("name", et_search.getText().toString().trim());
				}else{
					intent.putExtra("name", name);
				}
				setResult(Constants.RESULTCODE, intent);
				AddTagActivity.this.finish(); 
			}
		});
		FontManager.changeFonts(mContext,tv_top_title, et_search);
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		String content = arg0.toString();
		if(content.length()== 0 ){
			if(mList!=null && mList.size()>0){
				if(mList.get(0).isNewTag()){
					mList.remove(0);
					adapter.notifyDataSetChanged();
				}
			}
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
		getTagByName();
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

	
	
	
	public void getTagByName(){
		HttpControl httpControl = new HttpControl();
		httpControl.getProductTag(et_search.getText().toString().trim(),type, new HttpCallBackInterface() {
			
			@Override
			public void http_Success(Object obj) {
				TagListBackBean bean = (TagListBackBean) obj;
				if(bean.getData()!=null){
					mList.clear();
					if(bean.getData().size()>0){
						if(!TextUtils.isEmpty(et_search.getText().toString().trim())){
							if(!isConstantsKey(et_search.getText().toString().trim(), bean.getData())){
								TagListItemBean item = new TagListItemBean();
								item.setId("");
								item.setName("添加新标签："+et_search.getText().toString().trim());
								item.setNewTag(true);
								mList.add(item);
							}
						}
						mList.addAll(bean.getData());
					}else{
							TagListItemBean item = new TagListItemBean();
							item.setId("");
							item.setName("添加新标签："+et_search.getText().toString().trim());
							item.setNewTag(true);
							mList.add(item);
					}
					adapter.notifyDataSetChanged();
					
					
					
					
//					mList.clear();
//					if(bean.getData().size()>0){
//						if("0".equals(type) && !TextUtils.isEmpty(et_search.getText().toString().trim())){
//							if(!isConstantsKey(et_search.getText().toString().trim(), bean.getData())){
//								TagListItemBean item = new TagListItemBean();
//								item.setId("");
//								item.setName("添加新标签："+et_search.getText().toString().trim());
//								item.setNewTag(true);
//								mList.add(item);
//							}
//						}
//						mList.addAll(bean.getData());
//					}else{
//						if("0".equals(type)){
//							TagListItemBean item = new TagListItemBean();
//							item.setId("");
//							item.setName("添加新标签："+et_search.getText().toString().trim());
//							item.setNewTag(true);
//							mList.add(item);
//						}
//					}
//					adapter.notifyDataSetChanged();
				
					
				
				}
				
			}
			
			@Override
			public void http_Fails(int error, String msg) {
				// TODO Auto-generated method stub
				
			}
		}, AddTagActivity.this, 0);
		
	}
	
	
	
	  @Override
	    protected void onDestroy() {
	    	MyApplication.getInstance().removeActivity(this);//加入回退栈
	    	super.onDestroy();
	    }
	  
	  public void onResume() {
			super.onResume();
			MobclickAgent.onResume(this);
			}
			public void onPause() {
			super.onPause();
			MobclickAgent.onPause(this);
			}
			
			
			
			
			
			private boolean isConstantsKey(String key,List<TagListItemBean> lists){
				boolean isConstants = false;
				for (int i = 0; i < lists.size(); i++) {
					if(key!=null && key.equals(lists.get(i).getName())){
						isConstants =  true;
						break;
					}
				}
				return isConstants;
				
			}
			
}




