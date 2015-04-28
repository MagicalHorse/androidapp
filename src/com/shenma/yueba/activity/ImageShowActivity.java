//package com.shenma.yueba.activity;
//
//
//import java.util.List;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.View;
//import android.view.Window;
//import android.view.WindowManager;
//
//import com.shenma.yueba.R;
///**
// * 显示大图片
// * @author Administrator
// */
//public class ImageShowActivity extends Activity implements OnItemChangeListener,OnTochImageClickListener{
//	public static final String BIGIMAGES="bigImages";
//	public static final String IMAGE_INDEX="imageIndex";
//	private int index=0;
//	private GalleryViewPager mViewPager;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题�?
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.image_show_viewpager);
//		index = getIntent().getIntExtra(IMAGE_INDEX, 0);
//		List<String> list = getIntent().getStringArrayListExtra(BIGIMAGES);
//		UrlPagerAdapter pagerAdapter = new UrlPagerAdapter(this, list);
//		pagerAdapter.setOnClickListener(this);
//	    pagerAdapter.setOnItemChangeListener(this);
//	    mViewPager = (GalleryViewPager)findViewById(R.id.viewer);
//	    mViewPager.setOffscreenPageLimit(2);
//	    mViewPager.setAdapter(pagerAdapter);
//	    if(index<list.size()){
//	    	mViewPager.setCurrentItem(index);
//	    }
//	}
//	@Override
//	public void onChick(View view,int position) {
//		ImageShowActivity.this.finish();
//	}
//	@Override
//	public void onItemLongChick(View view, int position) {
//		
//	}
//	@Override
//	public void onItemChange(int currentPosition) {
//	}
//}
