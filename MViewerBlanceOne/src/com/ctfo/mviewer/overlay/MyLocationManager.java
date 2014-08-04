package com.ctfo.mviewer.overlay;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.ctfo.mvapi.entities.GeoPoint;
import com.ctfo.mvapi.entities.POINT;
import com.ctfo.mvapi.location.LocationManager;
import com.ctfo.mvapi.map.MapView;
import com.ctfo.mviewer.R;

public class MyLocationManager extends LocationManager 
{
	private GeoPoint mPoint;
	private Bitmap mBitmapPoint;
	
	public MyLocationManager(Context mContext)
	{
		 InputStream is = mContext.getResources().openRawResource(R.drawable.myloaction);
		 mBitmapPoint = BitmapFactory.decodeStream(is);
	}
	
	public void draw(Canvas canvas, MapView mapView)
	{
		if(null != mPoint)
		{
			POINT pt = mapView.map2Display(mPoint.getLongitude(), mPoint.getLatitude());
			canvas.drawBitmap(mBitmapPoint, pt.x-mBitmapPoint.getWidth()/2, pt.y-mBitmapPoint.getHeight()/2, null);
		}
	}
	
	/**
	 * 获取我的位置
	 * @param point
	 */
	public GeoPoint getMyLocation()
	{
		return mPoint;
	}
	
	/**
	 * 设置我的位置点
	 * @param point
	 */
	public void setMyLocation(GeoPoint point)
	{
		mPoint = point;
	}
}
