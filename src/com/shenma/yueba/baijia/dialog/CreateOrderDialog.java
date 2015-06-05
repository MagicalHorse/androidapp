package com.shenma.yueba.baijia.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.activity.AffirmOrderActivity;
import com.shenma.yueba.baijia.modle.PrioductSizesInfoBean;
import com.shenma.yueba.baijia.modle.ProductsDetailsInfoBean;
import com.shenma.yueba.baijia.modle.RequestProductDetailsInfoBean;
import com.shenma.yueba.util.FontManager;
import com.shenma.yueba.util.ToolsUtil;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-26 下午3:01:52  
 * 程序的简单说明   下单对话框
 */

public class CreateOrderDialog extends Dialog implements android.view.View.OnClickListener{
	Context context;
	RelativeLayout ll;
	EditText createorder_dialog_layout_countvalue_edittext;
	RequestProductDetailsInfoBean bean;
	ProductsDetailsInfoBean productsDetailsInfoBean;
	List<PrioductSizesInfoBean> size_list=new ArrayList<PrioductSizesInfoBean>();
	public CreateOrderDialog(Context context,RequestProductDetailsInfoBean bean) {
		super(context, R.style.MyDialog);
		requestWindowFeature(getWindow().FEATURE_NO_TITLE);
		this.context=context;
		this.bean=bean;
		setOwnerActivity((Activity)context);
		productsDetailsInfoBean=bean.getData();
		if(bean==null || productsDetailsInfoBean==null)
		{
			this.cancel();
			MyApplication.getInstance().showMessage(context, "数据为空");
		}
        //this.getWindow().setBackgroundDrawable(context.getResources().getDrawable(R.color.color_transparent));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		ll=(RelativeLayout)RelativeLayout.inflate(context, R.layout.createorder_dialog_layout, null);
		setContentView(ll);
		initView();
	}
	
	void initView()
	{
		ToolsUtil.setFontStyle(context, ll, R.id.chat_product_head_layout_name_textview,R.id.chat_product_head_layout_price_textview,R.id.createorder_dialog_layout_color_textview,R.id.createorder_dialog_layout_colorvalue_textview,R.id.createorder_dialog_layout_size_textview,R.id.createorder_dialog_layout_count_textview,R.id.create_dialog_jian_button,R.id.createorder_dialog_layout_countvalue_edittext,R.id.create_dialog_jia_button,R.id.createorder_dialog_layout_cancell_button,R.id.createorder_dialog_layout_submit_button,R.id.createorder_dialog_layout_repertory_textview,R.id.createorder_dialog_layout_repertoryvalue_textview);
		//头像
		ImageView chat_product_head_layout_imageview=(ImageView)ll.findViewById(R.id.chat_product_head_layout_imageview);
		String[] pic_array=productsDetailsInfoBean.getProductPic();
		if(pic_array!=null && pic_array.length>0)
		{
			MyApplication.getInstance().getImageLoader().displayImage(ToolsUtil.getImage(pic_array[0], 320, 0), chat_product_head_layout_imageview, MyApplication.getInstance().getDisplayImageOptions());
		}
		//产品名称
		TextView chat_product_head_layout_name_textview=(TextView)ll.findViewById(R.id.chat_product_head_layout_name_textview);
		chat_product_head_layout_name_textview.setText(ToolsUtil.nullToString(productsDetailsInfoBean.getProductName()));
		//产品价格
		TextView chat_product_head_layout_price_textview=(TextView)ll.findViewById(R.id.chat_product_head_layout_price_textview);
		chat_product_head_layout_price_textview.setText("￥"+productsDetailsInfoBean.getPrice());
		//颜色类型
		TextView createorder_dialog_layout_colorvalue_textview=(TextView)ll.findViewById(R.id.createorder_dialog_layout_colorvalue_textview);
		//设置尺寸
		GridView createorder_dialog_layout_size_gridview=(GridView)ll.findViewById(R.id.createorder_dialog_layout_size_gridview);
		if(productsDetailsInfoBean.getSizes()!=null && productsDetailsInfoBean.getSizes().size()>0)
		{
			size_list.clear();
			size_list.addAll(productsDetailsInfoBean.getSizes());
		}
		
		createorder_dialog_layout_size_gridview.setAdapter(baseAdapter);
		//减
		Button create_dialog_jian_button=(Button)ll.findViewById(R.id.create_dialog_jian_button);
		create_dialog_jian_button.setOnClickListener(this);
		//加
		Button create_dialog_jia_button=(Button)ll.findViewById(R.id.create_dialog_jia_button);
		create_dialog_jia_button.setOnClickListener(this);
		//提交
		Button createorder_dialog_layout_submit_button=(Button)ll.findViewById(R.id.createorder_dialog_layout_submit_button);
		createorder_dialog_layout_submit_button.setOnClickListener(this);
		//取消
		Button createorder_dialog_layout_cancell_button=(Button)ll.findViewById(R.id.createorder_dialog_layout_cancell_button);
		createorder_dialog_layout_cancell_button.setOnClickListener(this);
		//库存
		TextView createorder_dialog_layout_repertoryvalue_textview=(TextView)ll.findViewById(R.id.createorder_dialog_layout_repertoryvalue_textview);
		
		//数量
		createorder_dialog_layout_countvalue_edittext=(EditText)ll.findViewById(R.id.createorder_dialog_layout_countvalue_edittext);
		createorder_dialog_layout_countvalue_edittext.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				CharSequence text = createorder_dialog_layout_countvalue_edittext.getText();				
				if (text instanceof Spannable) {
					Spannable spanText = (Spannable)text;	
					Selection.setSelection(spanText, text.length());				
					}
				if(s.toString().equals(""))
				{
					createorder_dialog_layout_countvalue_edittext.setText(0+"");
					return;
				}
				int value=Integer.parseInt(s.toString());
				if(value<0)
				{
					createorder_dialog_layout_countvalue_edittext.setText(0+"");
				}else if(value>10)
				{
					createorder_dialog_layout_countvalue_edittext.setText(10+"");
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
		baseAdapter.notifyDataSetChanged();
	}
	
	@Override
	public void show() {
		
		super.show();
		
	}

	
	
	@Override
	public void onClick(View v) {
		
		switch(v.getId())
		{
		case R.id.create_dialog_jian_button://减
			int value=Integer.parseInt(createorder_dialog_layout_countvalue_edittext.getText().toString());
			value--;
			if(value<=0)
			{
				value=1;
			}
			createorder_dialog_layout_countvalue_edittext.setText(Integer.toString(value));
			break;
		case R.id.create_dialog_jia_button://加
			int values=Integer.parseInt(createorder_dialog_layout_countvalue_edittext.getText().toString());
			values++;
			if(values>=10)
			{
				values=10;
			}
			createorder_dialog_layout_countvalue_edittext.setText(Integer.toString(values));
			break;
		case R.id.createorder_dialog_layout_cancell_button://取消
			CreateOrderDialog.this.cancel();
			break;
		case R.id.createorder_dialog_layout_submit_button://提交
			Intent intent=new Intent(context,AffirmOrderActivity.class);
			context.startActivity(intent);
			CreateOrderDialog.this.cancel();
			break;
		
		}
	}
	
	BaseAdapter baseAdapter=new BaseAdapter() {
		
		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			PrioductSizesInfoBean prioductSizesInfoBean=size_list.get(arg0);
			Button btn=new Button(context);
			FontManager.changeFonts(context, btn);
			btn.setText(prioductSizesInfoBean.getSize());
			btn.setTag(prioductSizesInfoBean);
			btn.setTextSize(12);
			btn.setBackgroundResource(R.drawable.gridviewitem_background);
			btn.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, (int)context.getResources().getDimension(R.dimen.shop_main_marginleft30_dimen)));
			//btn.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
			return btn;
		}
		
		@Override
		public long getItemId(int arg0) {
			
			return arg0;
		}
		
		@Override
		public Object getItem(int arg0) {
			
			return null;
		}
		
		@Override
		public int getCount() {
			
			return size_list.size();
		}
	};
}
