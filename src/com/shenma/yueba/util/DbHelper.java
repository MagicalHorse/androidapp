package com.shenma.yueba.util;

import com.google.inject.Singleton;

import android.content.Context;
import android.widget.Toast;

@Singleton
public class DbHelper {
	public void show(Context ctx){
		Toast.makeText(ctx, "robo", Toast.LENGTH_LONG).show();
	}
}
