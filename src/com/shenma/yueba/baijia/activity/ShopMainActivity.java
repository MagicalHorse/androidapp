package com.shenma.yueba.baijia.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.maxwin.view.XListView;
import me.maxwin.view.XListView.IXListViewListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dodola.model.DuitangInfo;
import com.dodowaterfall.Helper;
import com.example.android.bitmapfun.util.ImageFetcher;
import com.shenma.yueba.R;
import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.baijia.adapter.StaggeredAdapter;
import com.shenma.yueba.view.imageshow.CustomImageView;
/*****
 * 本类定义 店铺商品首页显示页面 
 * 1.显示商家logo  名称  地址   店铺描述 以及 商品图片等信息
 * @author gyj
 * @date 2015-05-05
 * ***/
public class ShopMainActivity extends BaseActivityWithTopView {
	//商品logo 
    CustomImageView shop_main_layout_icon_imageview;
    //店铺名称
    TextView shop_main_layout_name_textview;
    //商场名称
    TextView shop_main_layout_market_textview;
    //私聊按钮
    ImageButton shop_main_siliao_imagebutton;
    //关注按钮
    ImageButton shop_main_attention_imagebutton;
    //关注 值
    TextView shop_main_attention_textview;
    //粉丝值
    TextView shop_main_fans_textview;
    //赞值
    TextView shop_main_praise_textview;
    XListView xListView;
    
    
    private ImageFetcher mImageFetcher;
    private StaggeredAdapter mAdapter = null;
    private int currentPage = 0;
    ContentTask task = new ContentTask(this, 2);
    //商品描述
    TextView shap_main_description1_textview,shap_main_description2_textview,shap_main_description3_textview;
    /*RelativeLayout shop_stay_layout_tab1_relativelayout,
	shop_stay_layout_tab2_relativelayout,
	shop_stay_layout_tab3_relativelayout,hid_shop_stay_layout_tab1_relativelayout,hid_shop_stay_layout_tab2_relativelayout,hid_shop_stay_layout_tab3_relativelayout;*/
    FragmentManager fm;
	Map<Integer, Fragment> fragmentmap=new HashMap<Integer, Fragment>();
    @Override
	protected void onCreate(Bundle savedInstanceState) {
    	MyApplication.getInstance().addActivity(this);//加入回退栈
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shop_main_layout);
		super.onCreate(savedInstanceState);
		initView();
	}
	
	/****
	 * 初始化视图信息
	 * **/
	@SuppressLint("NewApi")
	void initView()
	{
		setTitle("店铺名称");
		setLeftTextView(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				ShopMainActivity.this.finish();
			}
		});
		fm=this.getFragmentManager();
		xListView=(XListView)findViewById(R.id.xListView);
		LinearLayout headview=(LinearLayout)LinearLayout.inflate(this, R.layout.shop_main_head_layout, null);
		xListView.addHeader(this,headview);
		shop_main_layout_icon_imageview=(CustomImageView)headview.findViewById(R.id.shop_main_layout_icon_imageview);
		shop_main_layout_name_textview=(TextView)headview.findViewById(R.id.shop_main_layout_name_textview);
		shop_main_layout_market_textview=(TextView)headview.findViewById(R.id.shop_main_layout_market_textview);
		shop_main_siliao_imagebutton=(ImageButton)headview.findViewById(R.id.shop_main_siliao_imagebutton); 
		shop_main_attention_imagebutton=(ImageButton)headview.findViewById(R.id.shop_main_attention_imagebutton); 
		shop_main_attention_textview=(TextView)headview.findViewById(R.id.shop_main_attention_textview); 
		shop_main_fans_textview=(TextView)headview.findViewById(R.id.shop_main_fans_textview); 
		shop_main_praise_textview=(TextView)headview.findViewById(R.id.shop_main_praise_textview); 
		shap_main_description1_textview=(TextView)headview.findViewById(R.id.shap_main_description1_textview); 
		shap_main_description2_textview=(TextView)headview.findViewById(R.id.shap_main_description2_textview); 
		shap_main_description3_textview=(TextView)headview.findViewById(R.id.shap_main_description3_textview); 
		
		xListView.setPullLoadEnable(true);
		xListView.setXListViewListener(new IXListViewListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				AddItemToContainer(++currentPage, 1);
			}
			
			@Override
			public void onLoadMore() {
				// TODO Auto-generated method stub
				AddItemToContainer(++currentPage, 2);
			}
		});

        mImageFetcher = new ImageFetcher(this, 240);
        mImageFetcher.setLoadingImage(R.drawable.empty_photo);
        mAdapter = new StaggeredAdapter(this, xListView,mImageFetcher);
        RelativeLayout shop_stay_layout_tab1_relativelayout=(RelativeLayout)headview.findViewById(R.id.shop_stay_layout_tab1_relativelayout);
        setTabView(shop_stay_layout_tab1_relativelayout, "商品", "123", true);
        RelativeLayout shop_stay_layout_tab2_relativelayout=(RelativeLayout)headview.findViewById(R.id.shop_stay_layout_tab2_relativelayout);
        setTabView(shop_stay_layout_tab2_relativelayout, "上新", "12", false);
        RelativeLayout shop_stay_layout_tab3_relativelayout=(RelativeLayout)headview.findViewById(R.id.shop_stay_layout_tab3_relativelayout);
        setTabView(shop_stay_layout_tab3_relativelayout, "圈子", "5", false);
        
        shop_stay_layout_tab1_relativelayout.setOnClickListener(onClickListener);
        shop_stay_layout_tab2_relativelayout.setOnClickListener(onClickListener);
        shop_stay_layout_tab3_relativelayout.setOnClickListener(onClickListener);
	}
	
	
	OnClickListener onClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId())
			{
			case R.id.shop_stay_layout_tab1_relativelayout:
				break;
			case R.id.shop_stay_layout_tab2_relativelayout:
				break;
			case R.id.shop_stay_layout_tab3_relativelayout:
				break;
			}
		}
	};
	
	private class ContentTask extends AsyncTask<String, Integer, List<DuitangInfo>> {

        private Context mContext;
        private int mType = 1;

        public ContentTask(Context context, int type) {
            super();
            mContext = context;
            mType = type;
        }

        @Override
        protected List<DuitangInfo> doInBackground(String... params) {
            try {
                return parseNewsJSON(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<DuitangInfo> result) {
            if (mType == 1) {

                mAdapter.addItemTop(result);
                mAdapter.notifyDataSetChanged();
                xListView.stopRefresh();

            } else if (mType == 2) {
            	xListView.stopLoadMore();
                mAdapter.addItemLast(result);
                mAdapter.notifyDataSetChanged();
            }

        }

        @Override
        protected void onPreExecute() {
        }

        public List<DuitangInfo> parseNewsJSON(String url) throws IOException {
            List<DuitangInfo> duitangs = new ArrayList<DuitangInfo>();
            String json = "";
            if (Helper.checkConnection(mContext)) {
                try {
                    json = Helper.getStringFromUrl(url);

                } catch (IOException e) {
                    Log.e("IOException is : ", e.toString());
                    e.printStackTrace();
                    return duitangs;
                }
            }
            Log.d("MainActiivty", "json:" + json);

            try {
                if (null != json) {
                    JSONObject newsObject = new JSONObject(json);
                    JSONObject jsonObject = newsObject.getJSONObject("data");
                    JSONArray blogsJson = jsonObject.getJSONArray("blogs");

                    for (int i = 0; i < blogsJson.length(); i++) {
                        JSONObject newsInfoLeftObject = blogsJson.getJSONObject(i);
                        DuitangInfo newsInfo1 = new DuitangInfo();
                        newsInfo1.setAlbid(newsInfoLeftObject.isNull("albid") ? "" : newsInfoLeftObject.getString("albid"));
                        newsInfo1.setIsrc(newsInfoLeftObject.isNull("isrc") ? "" : newsInfoLeftObject.getString("isrc"));
                        newsInfo1.setMsg(newsInfoLeftObject.isNull("msg") ? "" : newsInfoLeftObject.getString("msg"));
                        newsInfo1.setHeight(newsInfoLeftObject.getInt("iht"));
                        duitangs.add(newsInfo1);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return duitangs;
        }
    }

	/**
     * 添加内容
     * 
     * @param pageindex
     * @param type
     *            1为下拉刷新 2为加载更多
     */
    private void AddItemToContainer(int pageindex, int type) {
        if (task.getStatus() != Status.RUNNING) {
            String url = "http://www.duitang.com/album/1733789/masn/p/" + pageindex + "/24/";
            Log.d("MainActivity", "current url:" + url);
            ContentTask task = new ContentTask(this, type);
            task.execute(url);

        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        mImageFetcher.setExitTasksEarly(false);
        xListView.setAdapter(mAdapter);
        AddItemToContainer(currentPage, 2);
    }
    
    /****
     * 设置 tab切换文字的信息 以及是否显示底部横条
     * @param v View 父类视图
     * @param str1 信息1
     * @param str2 信息2
     * @param b booelean是否显示 横条 true显示  false不显示
     * */
    void setTabView(View v,String str1,String str2,boolean b)
    {
    	TextView shop_stay_layout_item_textview1=(TextView)v.findViewById(R.id.shop_stay_layout_item_textview1);
    	TextView shop_stay_layout_item_textview2=(TextView)v.findViewById(R.id.shop_stay_layout_item_textview2);
    	View shop_stay_layout_item_line_view=(View)v.findViewById(R.id.shop_stay_layout_item_line_view);
    	shop_stay_layout_item_textview1.setText(str1);
    	shop_stay_layout_item_textview2.setText(str2);
    	if(b)
    	{
    		shop_stay_layout_item_line_view.setVisibility(View.VISIBLE);
    	}else
    	{
    		shop_stay_layout_item_line_view.setVisibility(View.GONE);
    	}
    }
	
}
