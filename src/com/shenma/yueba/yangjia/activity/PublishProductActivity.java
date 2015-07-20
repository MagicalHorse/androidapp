package com.shenma.yueba.yangjia.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.callback.SaveCallback;
import com.alibaba.sdk.android.oss.model.OSSException;
import com.lidroid.xutils.BitmapUtils;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.BaseActivityWithTopView;
import com.shenma.yueba.baijia.activity.BuyerCertificationActivity2;
import com.shenma.yueba.baijia.modle.RequestUploadProductDataBean;
import com.shenma.yueba.camera2.ActivityCapture;
import com.shenma.yueba.util.CustomProgressDialog;
import com.shenma.yueba.util.FileUtils;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.HttpControl;
import com.shenma.yueba.util.SharedUtil;
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ProductImagesBean;
import com.shenma.yueba.util.SizeBean;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.TagImageView;
import com.shenma.yueba.yangjia.modle.StateBean;
import com.shenma.yueba.yangjia.modle.TagListBean;
import com.shenma.yueba.yangjia.modle.TagsBean;
import com.umeng.analytics.MobclickAgent;

/**
 * 发布商品
 * 
 * @author a
 * 
 */
public class PublishProductActivity extends BaseActivityWithTopView implements
		OnClickListener {
	public String pic1, pic2, pic3, pic;
	private LinearLayout ll_guige_container;
	private TextView tv_product_number;
	private EditText et_product_number;
	private TextView tv_price_title;
	private EditText et_price;
	private TextView tv_yuan;
	private EditText et_introduce;
	private TextView tv_retain;
	private EditText et_guige;
	private EditText et_kucun;
	private TextView tv_add_guige;
	private TextView tv_publish;
	private LinearLayout ll_pictures_container;
	private List<TagsBean> tagList;
	private List<View> littleImageViewList = new LinkedList<View>();
	private List<StateBean> dataList = new ArrayList<StateBean>();
	private List<View> GuigeViewList = new LinkedList<View>();
	private int index;
	private List<SizeBean> Sizes = new LinkedList<SizeBean>();
	private CustomProgressDialog progressDialog;
	private int upPicProgress = 0;
	private int maxSize = 100;
	private String imageEndStr = "_240x0.jpg";
	private boolean onePicLoadFinished, twoPicLoadFinished, ThreePicFinished;
	private String productId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		MyApplication.getInstance().addActivity(this);//加入回退栈
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.publish_product);
		super.onCreate(savedInstanceState);
		initView();
		getIntentData();
	}

	private void getIntentData() {
		TagListBean bean = (TagListBean) getIntent().getSerializableExtra(
				"tagListBean");
		RequestUploadProductDataBean detailBean = (RequestUploadProductDataBean) getIntent()
				.getSerializableExtra("data");
		productId = getIntent().getStringExtra("id");
		if(TextUtils.isEmpty(productId) && TextUtils.isEmpty(MyApplication.getInstance().getPublishUtil().getBean().getId())){//说明是修改商品
			tv_publish.setText("发布");
		}else{
			MyApplication.getInstance().getPublishUtil().getBean().setId(productId);
			tv_publish.setText("修改");
			if(detailBean!=null){
				MyApplication.getInstance().getPublishUtil().setBean(detailBean);
			}else{
		    	detailBean =  MyApplication.getInstance().getPublishUtil().getBean();
		    }
		}
		// 修改商品
		if (detailBean != null) {
			FileUtils.delAllFile(FileUtils.getRootPath() + "/tagPic/");
			MyApplication.getInstance().getPublishUtil().setBean(detailBean);
			MyApplication.getInstance().getPublishUtil().getBean().setId(productId);
			List<ProductImagesBean> imagesList = MyApplication.getInstance()
					.getPublishUtil().getBean().getImages();
			if (imagesList != null && imagesList.size() > 0) {
				String imageUrl = imagesList.get(0).getImageUrl();
				if (!TextUtils.isEmpty(imageUrl)) {
					saveBitmapToFile(0, imageUrl + imageEndStr);
				}
			}
			et_product_number.setText(ToolsUtil.nullToString(MyApplication.getInstance().getPublishUtil().getBean().getSku_Code()));
			et_price.setText(ToolsUtil.nullToString(MyApplication.getInstance().getPublishUtil().getBean().getPrice()));
			et_introduce.setText(ToolsUtil.nullToString(MyApplication.getInstance().getPublishUtil().getBean().getDesc()));
			List<SizeBean> sizeList = MyApplication.getInstance().getPublishUtil().getBean().getSizes();
			if(sizeList!=null){
				for (int i = 0; i < sizeList.size(); i++) {
					if(i == 0){
						et_guige.setText(sizeList.get(i).getName());
						et_kucun.setText(sizeList.get(i).getInventory());
					}else{
						View view = View.inflate(mContext, R.layout.guige_item, null);
						EditText et_guige = (EditText) view.findViewById(R.id.et_guige);
						EditText et_kucun = (EditText) view.findViewById(R.id.et_kucun);
						FontManager.changeFonts(mContext, et_guige,et_kucun);
						et_guige.setText(sizeList.get(i).getName());
						et_kucun.setText(sizeList.get(i).getInventory());
						ll_guige_container.addView(view);
					}
					ll_guige_container.setVisibility(View.VISIBLE);
				
				}
			}
			
			return;
		}
		if (bean != null) {
			tagList = bean.getTagList();
			if(tagList!=null && tagList.size()>0){
				MyApplication
				.getInstance()
				.getPublishUtil()
				.getBean()
				.getImages()
				.get(Integer.valueOf(MyApplication.getInstance()
						.getPublishUtil().getIndex())).setTags(tagList);
			}
			setImageView();
		}


	}

	private void initView() {
		setTitle("发布商品");
		setLeftTextView(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PublishProductActivity.this.finish();
			}
		});
		ll_pictures_container = getView(R.id.ll_pictures_container);
		ll_guige_container = getView(R.id.ll_guige_container);
		tv_product_number = getView(R.id.tv_product_number);
		et_product_number = getView(R.id.et_product_number);
		tv_price_title = getView(R.id.tv_price_title);
		et_price = getView(R.id.et_price);
		tv_yuan = getView(R.id.tv_yuan);
		et_introduce = getView(R.id.et_introduce);
		tv_retain = getView(R.id.tv_retain);
		tv_retain.setText(maxSize + "字");
		et_introduce.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				tv_retain.setText(maxSize - s.length() + "字");
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		et_guige = getView(R.id.et_guige);
		et_kucun = getView(R.id.et_kucun);
		tv_add_guige = getView(R.id.tv_add_guige);
		tv_publish = getView(R.id.tv_publish);
		tv_add_guige.setOnClickListener(this);
		tv_publish.setOnClickListener(this);
		FontManager.changeFonts(mContext, tv_top_title,tv_product_number, et_product_number,
				tv_price_title, et_price, tv_yuan, et_introduce, tv_retain,
				et_guige, et_kucun, tv_add_guige, tv_publish);
		progressDialog = new CustomProgressDialog(mContext)
				.createDialog(mContext);

	}

	private void setImageView() {
		for (int i = 0; i < 3; i++) {
			StateBean bean = new StateBean();
			View view = View.inflate(mContext, R.layout.tag_imageview, null);
			TagImageView layout_tag_image = (TagImageView) view
					.findViewById(R.id.layout_tag_image);
			ImageView iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
			iv_pic.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					StateBean bean = (StateBean) v.getTag();
					if (dataList.contains(bean)) {
						index = dataList.indexOf(bean);
						boolean canClick = true;
						for (int j = 0; j < index; j++) {
							if (dataList.get(j).isSetPic()) {
								continue;
							} else {
								canClick = false;
							}
						}
						if (canClick) {
							if (bean.isSetPic()) {
								Intent intent = new Intent(
										PublishProductActivity.this,
										EditPicActivity.class);
								MyApplication.getInstance().getPublishUtil()
										.setFrom("publish");
								if(!(""+index).equals(MyApplication.getInstance().getPublishUtil().getIndex())){
									MyApplication.getInstance().getPublishUtil()
									.setOtherPic(true);
								}else{
									MyApplication.getInstance().getPublishUtil()
									.setOtherPic(false);
								}
								MyApplication.getInstance().getPublishUtil()
										.setIndex(index + "");
								startActivity(intent);
								PublishProductActivity.this.finish();

							} else {
								Intent intent = new Intent(
										PublishProductActivity.this,
										ActivityCapture.class);
								MyApplication.getInstance().getPublishUtil()
										.setFrom("publish");
								MyApplication.getInstance().getPublishUtil()
										.setIndex(index + "");
								MyApplication.getInstance().finishActivity(
										EditPicActivity.class);
								startActivity(intent);
								PublishProductActivity.this.finish();
							}
						} else {
							Toast.makeText(mContext, "请按顺序添加图片", 1000).show();
						}

					}
				}
			});
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.height = ToolsUtil.getDisplayWidth(mContext) / 3
					- ToolsUtil.dip2px(mContext, 15);
			params.width = ToolsUtil.getDisplayWidth(mContext) / 3
					- ToolsUtil.dip2px(mContext, 15);
			iv_pic.setLayoutParams(params);
			bean.setPosition(i);
			// for (int j = 0; j <tagList.size(); j++) {
			// double x= tagList.get(j).get("x");
			// double y = tagList.get(j).get("y");
			//
			// layout_tag_image.addTextTag("aaaa",((int)((iv_pic.getX()+iv_pic.getWidth())*x)),
			// ((int)(iv_pic.getHeight()*y)));
			// }

			File files = new File(FileUtils.getRootPath() + "/tagPic/" + "tagPic"+ SharedUtil.getUserId(mContext) + i + ".png");
			if (files.exists()) {
				iv_pic.setImageBitmap(BitmapFactory.decodeFile(FileUtils.getRootPath() + "/tagPic/" + "tagPic"+ SharedUtil.getUserId(mContext) + i + ".png"));
				bean.setSetPic(true);
			} else {
				iv_pic.setBackgroundResource(R.drawable.default_pic);
			}
			if (tagList != null && tagList.size() > 0) {
				bean.setSetTag(true);
			}
			iv_pic.setTag(bean);
			dataList.add(bean);
			ll_pictures_container.addView(view);

		}

		for (int i = 0; i < ll_pictures_container.getChildCount(); i++) {
			littleImageViewList.add(ll_pictures_container.getChildAt(i));
		}
		// for (int i = 0; i < littleImageViewList.size(); i++) {
		// littleImageViewList.get(i).findViewById(R.id.iv_pic).setOnClickListener(new
		// OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// if(((StateBean)littleImageViewList.get(i).findViewById(R.id.iv_pic).getTag()).getPosition()!=littleImageViewList.size()){
		//
		// }
		//
		// }
		// });
		//
		// }

	}

	private void publishProduct() {
		if (progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
		HttpControl httpControl = new HttpControl();
		List<ProductImagesBean> imageList = MyApplication.getInstance()
				.getPublishUtil().getBean().getImages();
		List<ProductImagesBean> cacheImageList = new ArrayList<ProductImagesBean>();
		for (int i = 0; i < imageList.size(); i++) {
			if (!TextUtils.isEmpty(imageList.get(i).getImageUrl())) {
				cacheImageList.add(imageList.get(i));
			}
		}
		MyApplication.getInstance().getPublishUtil().getBean().setImages(cacheImageList);
		
		String id = MyApplication.getInstance().getPublishUtil().getBean().getId();
		if(TextUtils.isEmpty(id)){
			httpControl.createBuyerProductInfo(MyApplication.getInstance()
					.getPublishUtil().getBean(), new HttpCallBackInterface() {
				@Override
				public void http_Success(Object obj) {
					Toast.makeText(mContext, "发布成功", 1000).show();
//					FileUtils.delAllFile(FileUtils.getRootPath() + "/tagPic/");
					PublishProductActivity.this.finish();
					MyApplication.getInstance().getPublishUtil().setBean(null);
					MyApplication.getInstance().getPublishUtil().setIndex("0");
					MyApplication.getInstance().getPublishUtil().setFrom("");
					MyApplication.getInstance().getPublishUtil().setUri(null);
					MyApplication.getInstance().finishActivity(
							EditPicActivity.class);
				}

				@Override
				public void http_Fails(int error, String msg) {
					Toast.makeText(mContext, msg, 1000).show();
				}
			}, PublishProductActivity.this);
		}else{
			httpControl.updateBuyerProductInfo(MyApplication.getInstance()
					.getPublishUtil().getBean(), new HttpCallBackInterface() {
				@Override
				public void http_Success(Object obj) {
					Toast.makeText(mContext, "修改成功", 1000).show();
//					FileUtils.delAllFile(FileUtils.getRootPath() + "/tagPic/");
					PublishProductActivity.this.finish();
					MyApplication.getInstance().getPublishUtil().setBean(null);
					MyApplication.getInstance().getPublishUtil().setIndex("0");
					MyApplication.getInstance().getPublishUtil().setFrom("");
					MyApplication.getInstance().getPublishUtil().setUri(null);
					MyApplication.getInstance().finishActivity(
							EditPicActivity.class);
				}
				@Override
				public void http_Fails(int error, String msg) {
					Toast.makeText(mContext, msg, 1000).show();
				}
			}, PublishProductActivity.this);
			
		}
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_add_guige:// 添加规格
			for (int i = 0; i < ll_guige_container.getChildCount(); i++) {
				ll_guige_container.getChildAt(i).setTag(i);
			}
			View view = View.inflate(mContext, R.layout.guige_item, null);
			EditText et_guige = (EditText) view.findViewById(R.id.et_guige);
			EditText et_kucun = (EditText) view.findViewById(R.id.et_kucun);
			view.setTag(ll_guige_container.getChildCount());
			final ImageView iv_delete = (ImageView) view
					.findViewById(R.id.iv_delete);
			iv_delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int tag = (Integer) ((View) iv_delete.getParent()).getTag();
					for (int i = 0; i < ll_guige_container.getChildCount(); i++) {
						if (tag == (Integer) ll_guige_container.getChildAt(i)
								.getTag()) {
							ll_guige_container.removeViewAt(i);

						}
					}

				}
			});
			FontManager.changeFonts(mContext, et_guige,et_kucun);
			ll_guige_container.addView(view);
			break;
		case R.id.tv_publish:// 发布商品
			uploadImage();
			break;
		default:
			break;
		}

	}

	private void uploadImage() {
		if (new File(FileUtils.getRootPath() + "/tagPic/" + "tagPic"
				+ SharedUtil.getUserId(mContext) + "0.png").exists()) {
			pic1 = FileUtils.getRootPath() + "/tagPic/" + "tagPic"
					+ SharedUtil.getUserId(mContext) + "0.png";
			pic = pic1;
		}
		if (new File(FileUtils.getRootPath() + "/tagPic/" + "tagPic"
				+ SharedUtil.getUserId(mContext) + "1.png").exists()) {
			pic2 = FileUtils.getRootPath() + "/tagPic/" + "tagPic"
					+ SharedUtil.getUserId(mContext) + "1.png";
		}
		if (new File(FileUtils.getRootPath() + "/tagPic/" + "tagPic"
				+ SharedUtil.getUserId(mContext) + "2.png").exists()) {
			pic3 = FileUtils.getRootPath() + "/tagPic/" + "tagPic"
					+ SharedUtil.getUserId(mContext) + "2.png";
		}
		if (TextUtils.isEmpty(pic1) && TextUtils.isEmpty(pic2)
				&& TextUtils.isEmpty(pic3)) {
			Toast.makeText(mContext, "商品图片不能为空", 1000).show();
			return;
		}
		if (TextUtils.isEmpty(et_product_number.getText().toString().trim())) {
			Toast.makeText(mContext, "货号不能为空", 1000).show();
			return;
		}
		if (TextUtils.isEmpty(et_price.getText().toString().trim())) {
			Toast.makeText(mContext, "价格不能为空", 1000).show();
			return;
		}
		if (TextUtils.isEmpty(et_introduce.getText().toString().trim())) {
			Toast.makeText(mContext, "商品介绍不能为空", 1000).show();
			return;
		}
		Sizes.clear();
		SizeBean bean1 = new SizeBean();
		String guige = et_guige.getText().toString().trim();
		if (TextUtils.isEmpty(guige)) {
			Toast.makeText(mContext, "规格不能为空", 1000).show();
			return;
		} else {
			bean1.setName(guige);
		}
		String kucun = et_kucun.getText().toString().trim();
		if (TextUtils.isEmpty(kucun)) {
			Toast.makeText(mContext, "库存不能为空", 1000).show();
			return;
		} else {
			bean1.setInventory(kucun);
			Sizes.add(bean1);
		}

		for (int i = 0; i < ll_guige_container.getChildCount(); i++) {
			EditText et_guige = (EditText) ll_guige_container.getChildAt(i)
					.findViewById(R.id.et_guige);
			EditText et_kucun = (EditText) ll_guige_container.getChildAt(i)
					.findViewById(R.id.et_kucun);
			if (TextUtils.isEmpty(et_guige.getText().toString().trim())
					|| TextUtils.isEmpty(et_kucun.getText().toString().trim())) {
				Toast.makeText(mContext, "请将库存规格填写完整", 1000).show();
				return;
			}
			SizeBean bean = new SizeBean();
			if (!TextUtils.isEmpty(et_guige.getText().toString().trim())) {
				bean.setName(et_guige.getText().toString().trim());
			}

			bean.setInventory(et_kucun.getText().toString().trim());
			Sizes.add(bean);
		}
		RequestUploadProductDataBean bean = MyApplication.getInstance()
				.getPublishUtil().getBean();
		bean.setPrice(et_price.getText().toString().trim());
		bean.setSizes(Sizes);
		bean.setDesc(et_introduce.getText().toString().trim());
		bean.setSku_Code(et_product_number.getText().toString().trim());

		if (!progressDialog.isShowing()) {
			progressDialog.show();
		}
		HttpControl httpControl = new HttpControl();
		if (upPicProgress == 0) {
			pic = pic1;
		}
		if (upPicProgress == 1) {
			pic = pic2;
		}
		if (upPicProgress == 2) {
			pic = pic3;
		}
		if (TextUtils.isEmpty(pic)) {
			Toast.makeText(mContext, "商品图片不能为空", 1000).show();
			return;
		}
		httpControl.syncUpload(pic, new SaveCallback() {

			@Override
			public void onProgress(String arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFailure(String arg0, OSSException arg1) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(String arg0) {
				switch (upPicProgress) {
				case 0:// 第一张图片上传完毕
					upPicProgress = 1;
					if (new File(FileUtils.getRootPath() + "/tagPic/"
							+ "tagPic" + SharedUtil.getUserId(mContext)
							+ "0.png").exists()) {
						String imageName1 = pic1.substring(
								pic1.lastIndexOf("/") + 1, pic1.length());
						MyApplication.getInstance().getPublishUtil().getBean()
								.getImages().get(0).setImageUrl(imageName1);
					}
					if (!TextUtils.isEmpty(pic2)) {
						uploadImage();
					} else {
						runOnUiThread(new Runnable() {
							public void run() {
								publishProduct();
							}
						});

					}
					break;
				case 1:// 第二张上传完毕
					if (new File(FileUtils.getRootPath() + "/tagPic/"
							+ "tagPic" + SharedUtil.getUserId(mContext)
							+ "1.png").exists()) {
						String imageName2 = pic2.substring(
								pic2.lastIndexOf("/") + 1, pic2.length());
						MyApplication.getInstance().getPublishUtil().getBean()
								.getImages().get(1).setImageUrl(imageName2);
					}
					upPicProgress = 2;
					if (!TextUtils.isEmpty(pic3)) {
						uploadImage();
					} else {
						runOnUiThread(new Runnable() {
							public void run() {
								publishProduct();
							}
						});
					}
					break;
				case 2:// 第三张上传完毕
					if (progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
					if (new File(FileUtils.getRootPath() + "/tagPic/"
							+ "tagPic" + SharedUtil.getUserId(mContext)
							+ "2.png").exists()) {
						String imageName3 = pic3.substring(
								pic3.lastIndexOf("/") + 1, pic3.length());
						MyApplication.getInstance().getPublishUtil().getBean()
								.getImages().get(2).setImageUrl(imageName3);
					}
//					MyApplication.getInstance().getPublishUtil().setBean(null);
					runOnUiThread(new Runnable() {
						public void run() {
							publishProduct();
						}
					});
				default:
					break;
				}

			}
		});
	}

	private void saveBitmapToFile(final int index, String imageUrl) {
		File dir = new File(FileUtils.getRootPath() + "/tagPic/");
		if (!dir.exists()) {
			if (!dir.exists())
				dir.mkdirs();
		}
		File file = new File(dir, "tagPic" + SharedUtil.getUserId(mContext)
				+ index + ".png");
		File file2 = new File(dir, "tagPic_yuan" + SharedUtil.getUserId(mContext)
				+ index + ".png");
		 if (file.exists()) {
		 file.delete();
		 }
		 if (file2.exists()) {
			 file2.delete();
			 }
		try {
			final FileOutputStream out = new FileOutputStream(file);
			final FileOutputStream out2 = new FileOutputStream(file2);
			
			MyApplication.getInstance().getImageLoader()
					.loadImage(imageUrl, new ImageLoadingListener() {
						@Override
						public void onLoadingStarted(String arg0, View arg1) {

						}

						@Override
						public void onLoadingFailed(String arg0, View arg1,
								FailReason arg2) {

						}

						@Override
						public void onLoadingComplete(String arg0, View arg1,
								Bitmap bitmap) {
							try {
								bitmap.compress(Bitmap.CompressFormat.PNG, 100,
										out);
								out.flush();
								out.close();
								
								bitmap.compress(Bitmap.CompressFormat.PNG, 100,
										out2);
								out2.flush();
								out2.close();
							} catch (Exception e) {
								// TODO: handle exception
							}

							if (index < MyApplication.getInstance()
									.getPublishUtil().getBean().getImages()
									.size()-1) {
								saveBitmapToFile(index + 1, MyApplication
										.getInstance().getPublishUtil()
										.getBean().getImages().get(index+1)
										.getImageUrl()
										+ imageEndStr);
							} else {
								setImageView();
							}

						}

						@Override
						public void onLoadingCancelled(String arg0, View arg1) {

						}
					});
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
	@Override
	protected void onDestroy() {
		MyApplication.getInstance().addActivity(this);
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
}
