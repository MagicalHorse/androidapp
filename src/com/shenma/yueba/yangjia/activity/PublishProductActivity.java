package com.shenma.yueba.yangjia.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.shenma.yueba.util.HttpControl.HttpCallBackInterface;
import com.shenma.yueba.util.ProductImagesBean;
import com.shenma.yueba.util.SizeBean;
import com.shenma.yueba.util.ToolsUtil;
import com.shenma.yueba.view.TagImageView;
import com.shenma.yueba.yangjia.modle.StateBean;
import com.shenma.yueba.yangjia.modle.TagListBean;
import com.shenma.yueba.yangjia.modle.TagsBean;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.publish_product);
		super.onCreate(savedInstanceState);
		getIntentData();
		initView();
	}

	private void getIntentData() {
		TagListBean bean = (TagListBean) getIntent().getSerializableExtra(
				"tagListBean");
		tagList = bean.getTagList();
		MyApplication
				.getInstance()
				.getPublishUtil()
				.getBean()
				.getImages()
				.get(Integer.valueOf(MyApplication.getInstance()
						.getPublishUtil().getIndex())).setTags(tagList);

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
		setImageView();
		tv_product_number = getView(R.id.tv_product_number);
		et_product_number = getView(R.id.et_product_number);
		tv_price_title = getView(R.id.tv_price_title);
		et_price = getView(R.id.et_price);
		tv_yuan = getView(R.id.tv_yuan);
		et_introduce = getView(R.id.et_introduce);
		tv_retain = getView(R.id.tv_retain);
		et_guige = getView(R.id.et_guige);
		et_kucun = getView(R.id.et_kucun);
		tv_add_guige = getView(R.id.tv_add_guige);
		tv_publish = getView(R.id.tv_publish);
		tv_add_guige.setOnClickListener(this);
		tv_publish.setOnClickListener(this);
		FontManager.changeFonts(mContext, tv_product_number, et_product_number,
				tv_price_title, et_price, tv_yuan, et_introduce, tv_retain,
				et_guige, et_kucun, tv_add_guige, tv_publish);
		progressDialog = new CustomProgressDialog(mContext);

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
			params.height = ToolsUtil.getDisplayWidth(mContext) / 3 - ToolsUtil.dip2px(mContext, 15);
			params.width = ToolsUtil.getDisplayWidth(mContext) / 3 - ToolsUtil.dip2px(mContext, 15);
			iv_pic.setLayoutParams(params);
			bean.setPosition(i);
			// for (int j = 0; j <tagList.size(); j++) {
			// double x= tagList.get(j).get("x");
			// double y = tagList.get(j).get("y");
			//
			// layout_tag_image.addTextTag("aaaa",((int)((iv_pic.getX()+iv_pic.getWidth())*x)),
			// ((int)(iv_pic.getHeight()*y)));
			// }

			if (new File(FileUtils.getRootPath() + "/tagPic/" + "tagPic" + i
					+ ".png").exists()) {
				iv_pic.setImageBitmap(BitmapFactory.decodeFile(FileUtils
						.getRootPath() + "/tagPic/" + "tagPic" + i + ".png"));
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
		HttpControl httpControl = new HttpControl();
		httpControl.createBuyerProductInfo(MyApplication.getInstance()
				.getPublishUtil().getBean(), new HttpCallBackInterface() {
			@Override
			public void http_Success(Object obj) {
				Toast.makeText(mContext, "发布成功",1000).show();

			}

			@Override
			public void http_Fails(int error, String msg) {
				// TODO Auto-generated method stub

			}
		}, PublishProductActivity.this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_add_guige:// 添加规格
			for (int i = 0; i < ll_guige_container.getChildCount(); i++) {
				ll_guige_container.getChildAt(i).setTag(i);
			}
			View view = View.inflate(mContext, R.layout.guige_item, null);
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
		if (new File(FileUtils.getRootPath() + "/tagPic/" + "tagPic0.png")
				.exists()) {
			pic1 = FileUtils.getRootPath() + "/tagPic/" + "tagPic0.png";
			pic = pic1;
		}
		if (new File(FileUtils.getRootPath() + "/tagPic/" + "tagPic1.png")
				.exists()) {
			pic2 = FileUtils.getRootPath() + "/tagPic/" + "tagPic1.png";
		}
		if (new File(FileUtils.getRootPath() + "/tagPic/" + "tagPic2.png")
				.exists()) {
			pic3 = FileUtils.getRootPath() + "/tagPic/" + "tagPic2.png";
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
		String guige = et_guige.getText().toString().trim();
		if(TextUtils.isEmpty(guige)){
			Toast.makeText(mContext, "规格不能为空", 1000).show();
			return;
		}
		String kucun = et_kucun.getText().toString().trim();
		if(TextUtils.isEmpty(kucun)){
			Toast.makeText(mContext, "库存不能为空", 1000).show();
			return;
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
			if(!TextUtils.isEmpty(et_guige.getText().toString().trim())){
				bean.setGuiGe(et_guige.getText().toString().trim());
			}
			
			bean.setKuCun(et_kucun.getText().toString().trim());
			Sizes.add(bean);
		}
		RequestUploadProductDataBean bean = MyApplication.getInstance()
				.getPublishUtil().getBean();
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
					if (!TextUtils.isEmpty(pic2)) {
						uploadImage();
					} else {
						publishProduct();
					}
					break;
				case 1:// 第二张上传完毕
					upPicProgress = 2;
					if (!TextUtils.isEmpty(pic3)) {
						uploadImage();
					} else {
						publishProduct();
					}
					break;
				case 2:// 第三张上传完毕
					if (progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
					if (new File(FileUtils.getRootPath() + "/tagPic/"
							+ "tagPic0.png").exists()) {
						String imageName1 = pic1.substring(
								pic1.lastIndexOf("/") + 1, pic1.length());
						MyApplication.getInstance().getPublishUtil().getBean()
								.getImages().get(0).setImageUrl(imageName1);
						new File(FileUtils.getRootPath() + "/tagPic/"
								+ "tagPic0.png").delete();
					}
					if (new File(FileUtils.getRootPath() + "/tagPic/"
							+ "tagPic1.png").exists()) {
						String imageName2 = pic2.substring(
								pic2.lastIndexOf("/") + 1, pic2.length());
						MyApplication.getInstance().getPublishUtil().getBean()
								.getImages().get(0).setImageUrl(imageName2);
						new File(FileUtils.getRootPath() + "/tagPic/"
								+ "tagPic1.png").delete();
					}
					if (new File(FileUtils.getRootPath() + "/tagPic/"
							+ "tagPic2.png").exists()) {
						String imageName3 = pic3.substring(
								pic3.lastIndexOf("/") + 1, pic3.length());
						MyApplication.getInstance().getPublishUtil().getBean()
								.getImages().get(0).setImageUrl(imageName3);
						new File(FileUtils.getRootPath() + "/tagPic/"
								+ "tagPic2.png").delete();
					}
					FileUtils.delAllFile(FileUtils.getRootPath() + "/tagPic/");
					MyApplication.getInstance().getPublishUtil().setBean(null);

				default:
					break;
				}

			}
		});
	}
}
