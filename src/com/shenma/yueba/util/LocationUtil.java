package com.shenma.yueba.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class LocationUtil {

	private double latitude = 0.0;
	private double longitude = 0.0;
	LocationManager locationManager;
	private Context ctx;

	public LocationUtil(Context ctx) {
		this.ctx = ctx;
		locationManager = (LocationManager) ctx
				.getSystemService(Context.LOCATION_SERVICE);
	}

	public void getLocation() {
			Location location = locationManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			if (location != null && 0 != location.getLatitude()
					&& 0 != location.getLongitude()) {
				latitude = location.getLatitude();
				longitude = location.getLongitude();
				SharedUtil.setStringPerfernece(ctx, "latitude", latitude + "");
				SharedUtil
						.setStringPerfernece(ctx, "longitude", longitude + "");
				Log.i("location", latitude + "----" + longitude);
			}else{
				Location locationNetWork = locationManager
						.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				if (locationNetWork != null && locationNetWork.getLatitude() != 0
						&& locationNetWork.getLongitude() != 0) {
					latitude = location.getLatitude(); // 经度
					longitude = location.getLongitude(); // 纬度
					SharedUtil.setStringPerfernece(ctx, "latitude", latitude + "");
					SharedUtil.setStringPerfernece(ctx, "longitude", longitude + "");
					Log.i("location", latitude + "----" + longitude);
					return;
			}
		}

		}

}
