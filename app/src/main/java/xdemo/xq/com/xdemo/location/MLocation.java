package xdemo.xq.com.xdemo.location;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;

/**
 * 高德定位
 * 
 * */
public class MLocation implements AMapLocationListener {
	private String longitude, latitude, cityCode,city;
	private Context mContext;

	private LocationManagerProxy mAMapLocationManager = null;
	public MLocation(Context mContext) {
		super();
		this.mContext = mContext;
	}

	public void getPosition() {
		mAMapLocationManager = LocationManagerProxy.getInstance(mContext.getApplicationContext());
		mAMapLocationManager.requestLocationData(LocationProviderProxy.AMapNetwork,2000, 10,this);
	}

	public void removeLocation() {
		if (mAMapLocationManager != null) {
			mAMapLocationManager.removeUpdates(this);
			mAMapLocationManager.destroy();
		}
		mAMapLocationManager = null;
	}

	OnlocationFinished FinishEdListener;

	public void setFinishEdListener(OnlocationFinished finishEdListener) {
		FinishEdListener = finishEdListener;
	}

	@Override
	public void onLocationChanged(AMapLocation aMapLocation) {
		if (aMapLocation != null) {
			longitude = aMapLocation.getLongitude() + "";
			latitude = aMapLocation.getLatitude() + "";
			cityCode = aMapLocation.getCityCode() + "";
			city = aMapLocation.getCity();
			if (FinishEdListener != null)
				FinishEdListener.locationFinished(latitude, longitude,cityCode,city);
		} else {
			getPosition();
		}
	}

	@Override
	public void onLocationChanged(Location location) {

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

	@Override
	public void onProviderEnabled(String provider) {

	}

	@Override
	public void onProviderDisabled(String provider) {

	}

	public interface OnlocationFinished {void locationFinished(String latitude, String longitude, String cityCode, String city);
	}
}
