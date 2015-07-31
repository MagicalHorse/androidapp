package com.shenma.yueba.baijia.activity;



import com.shenma.yueba.application.MyApplication;
import com.shenma.yueba.view.imageshow.TouchImageView;

import android.app.Activity;
import android.os.Bundle;

public class TouchImageViewActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(getWindow().FEATURE_NO_TITLE);
    	super.onCreate(savedInstanceState);
        String img_url=this.getIntent().getStringExtra("IMG_URL");
        TouchImageView img = new TouchImageView(this);
        MyApplication.getInstance().getBitmapUtil().display(img, img_url);
        setContentView(img);
    }
}