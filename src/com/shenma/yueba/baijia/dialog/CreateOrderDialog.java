package com.shenma.yueba.baijia.dialog;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.shenma.yueba.view.XCFlowLayout;

/**  
 * @author gyj  
 * @version 创建时间：2015-5-26 下午3:01:52  
 * 程序的简单说明   下单对话框
 */

public class CreateOrderDialog extends AlertDialog implements android.view.View.OnClickListener{
	Context context;
	RelativeLayout ll;
	EditText createorder_dialog_layout_countvalue_edittext;
	RequestProductDetailsInfoBean bean;
	ProductsDetailsInfoBean productsDetailsInfoBean;
	//库存最大值
	int maxValue=0;
	//当前选中的尺寸对象
	PrioductSizesInfoBean currCheckedFouce=null;
	Button createorder_dialog_layout_submit_button;
	List<View> view_array=new ArrayList<View>();
	TextView createorder_dialog_layout_repertoryvalue_textview;
	List<PrioductSizesInfoBean> size_list=new ArrayList<PrioductSizesInfoBean>();
	XCFlowLayout flowlayout;
	
	public CreateOrderDialog(Context context,RequestProductDetailsInfoBean bean) {
		super(context, R.style.MyDialog);
		//super(context);
		//requestWindowFeature(getWindow().FEATURE_NO_TITLE);
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
		if(productsDetailsInfoBean.getSizes()!=null && productsDetailsInfoBean.getSizes().size()>0)
		{
			size_list.clear();
			size_list.addAll(productsDetailsInfoBean.getSizes());
		}
		flowlayout=(XCFlowLayout)ll.findViewById(R.id.flowlayout);
		//减
		Button create_dialog_jian_button=(Button)ll.findViewById(R.id.create_dialog_jian_button);
		create_dialog_jian_button.setOnClickListener(this);
		//加
		Button create_dialog_jia_button=(Button)ll.findViewById(R.id.create_dialog_jia_button);
		create_dialog_jia_button.setOnClickListener(this);
		//提交
		createorder_dialog_layout_submit_button=(Button)ll.findViewById(R.id.createorder_dialog_layout_submit_button);
		createorder_dialog_layout_submit_button.setOnClickListener(this);
		//取消
		Button createorder_dialog_layout_cancell_button=(Button)ll.findViewById(R.id.createorder_dialog_layout_cancell_button);
		createorder_dialog_layout_cancell_button.setOnClickListener(this);
		//库存
		createorder_dialog_layout_repertoryvalue_textview=(TextView)ll.findViewById(R.id.createorder_dialog_layout_repertoryvalue_textview);
		
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
					createorder_dialog_layout_submit_button.setEnabled(false);
				}else if(value>maxValue)
				{
					createorder_dialog_layout_countvalue_edittext.setText(Integer.toString(maxValue));
					createorder_dialog_layout_submit_button.setEnabled(true);
				}
				if(s.toString().length()>1)//如果位数大于1位
				{
					char c=s.toString().charAt(0);
					if(c==0)
					{
						createorder_dialog_layout_countvalue_edittext.setText(Integer.toString(value));
					}
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

		addstandardButton();
	}
	
	@Override
	public void show() {
		
		super.show();
		WindowManager.LayoutParams param=this.getWindow().getAttributes();
		DisplayMetrics dm=new DisplayMetrics();
		((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
		param.width=dm.widthPixels;
	    //param.height=;
	    Log.i("TAG", "HEIGHT----->:"+dm.heightPixels);
		this.getWindow().setAttributes(param);
		this.getWindow().setBackgroundDrawableResource(R.color.color_transparent);
	}

	
	
	@Override
	public void onClick(View v) {
		
		switch(v.getId())
		{
		case R.id.create_dialog_jian_button://减
			if(currCheckedFouce==null)
			{
				MyApplication.getInstance().showMessage(context, "请先选择商品规格");
				return;
			}
			int value=Integer.parseInt(createorder_dialog_layout_countvalue_edittext.getText().toString());
			value--;
			if(value<=0)
			{
				value=0;
			}
			createorder_dialog_layout_countvalue_edittext.setText(Integer.toString(value));
			break;
		case R.id.create_dialog_jia_button://加
			if(currCheckedFouce==null)
			{
				MyApplication.getInstance().showMessage(context, "请先选择商品规格");
				return;
			}
			int values=Integer.parseInt(createorder_dialog_layout_countvalue_edittext.getText().toString());
			values++;
			if(values>=maxValue)
			{
				values=maxValue;
			}
			createorder_dialog_layout_countvalue_edittext.setText(Integer.toString(values));
			break;
		case R.id.createorder_dialog_layout_cancell_button://取消
			CreateOrderDialog.this.cancel();
			break;
		case R.id.createorder_dialog_layout_submit_button://提交
			int count=Integer.parseInt(createorder_dialog_layout_countvalue_edittext.getText().toString().trim());
			if(count<=0)
			{
				//商品数量必须大于零
				MyApplication.getInstance().showMessage(context, "商品数量必须大于零");
				return;
			}else if(currCheckedFouce==null)
			{
				MyApplication.getInstance().showMessage(context, "请选择商品规格");
				return;
			}
			
			Intent intent=new Intent(context,AffirmOrderActivity.class);
			intent.putExtra("COUNT", count);//购买数量
			intent.putExtra("CHECKEDPrioductSizes", currCheckedFouce);//选择的规格尺寸
			intent.putExtra("DATA", bean);//商品信息
			context.startActivity(intent);
			CreateOrderDialog.this.cancel();
			break;
		
		}
	}
	
	
	/******
	 * 设置当前选中的尺寸样式
	 * ***/
	void setCheckFouse()
	{
		for(int i=0;i<view_array.size();i++)
		{
			view_array.get(i).setSelected(false);
		}
		//根据currCheckedFouce 值设置 库存组大值
		if(currCheckedFouce!=null)
		{
			int inventory=currCheckedFouce.getInventory();//库存
			if(inventory<0)
			{
				inventory=0;
			}
			maxValue=inventory;
			createorder_dialog_layout_repertoryvalue_textview.setText(Integer.toString(inventory));
			int currvalue=Integer.parseInt(createorder_dialog_layout_countvalue_edittext.getText().toString().trim());
			if(currvalue>maxValue)
			{
				createorder_dialog_layout_countvalue_edittext.setText(Integer.toString(maxValue));
			}
		}
	}
	
	
	void addstandardButton()
	{
		for(int i=0;i<size_list.size();i++)
		{
			PrioductSizesInfoBean prioductSizesInfoBean=size_list.get(i);
			TextView btn=new TextView(context);
			btn.setGravity(Gravity.CENTER);
			FontManager.changeFonts(context, btn);
			btn.setText(prioductSizesInfoBean.getSize());
			btn.setTag(prioductSizesInfoBean);
			btn.setTextSize(12);
			btn.setBackgroundResource(R.drawable.gridviewitem_background);
			MarginLayoutParams lp = new MarginLayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			lp.leftMargin = 15;
	        lp.rightMargin = 15;
	        lp.topMargin = 13;
	        lp.bottomMargin = 13;
	        btn.setPadding(30, 20, 30, 20);
			btn.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					currCheckedFouce=(PrioductSizesInfoBean)v.getTag();
					setCheckFouse();
					v.setSelected(true);
					
				}
			});
			flowlayout.addView(btn, lp);
			if(!view_array.contains(btn))
			{
				view_array.add(btn);
			}
			setCheckFouse();
		}
	}
	
	
}
