package com.ctfo.mviewer.overlay;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.ctfo.mvapi.entities.GeoPoint;
import com.ctfo.mvapi.entities.POINT;
import com.ctfo.mvapi.map.MapView;
import com.ctfo.mvapi.map.Overlay;
import com.ctfo.mviewer.R;
import com.ctfo.mviewer.entity.BusLine;

public class BusLineOverlay extends Overlay 
{
	private BusLine mBusLine = null;
	private Paint mPaintLine = new Paint();
	private Bitmap mBitMapStart = null;
	private Bitmap mBitMapEnd = null;
	private Resources mResources = null;

	public BusLineOverlay(BusLine busline ,Resources resources )
	{
		this.mResources = resources;
		mBusLine = busline;
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow)
	{
		super.draw(canvas, mapView, shadow); 
		mPaintLine.setColor(mResources.getColor(R.color.line_bus));
		mPaintLine.setStrokeWidth(8.0f);
		mPaintLine.setStyle(Paint.Style.STROKE);
		mPaintLine.setAntiAlias(true);
		mPaintLine.setStrokeJoin(Paint.Join.ROUND);
		mPaintLine.setStrokeCap(Paint.Cap.ROUND);
		
		mBitMapStart = BitmapFactory.decodeResource(mResources, R.drawable.icon_nav_start);
		mBitMapEnd = BitmapFactory.decodeResource(mResources, R.drawable.icon_nav_end);
		
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		// 画线
		for (int a=1;a<mBusLine.gPoints.size();a++)
		{
			GeoPoint gPt =mBusLine.gPoints.get(a);
			GeoPoint point = new GeoPoint( (int)(gPt.getLongitude()*1E6), (int)(gPt.getLatitude()*1E6) );
			POINT points = mapView.map2Display(point.getLongitude(), point.getLatitude());
			
			GeoPoint gPtLast =mBusLine.gPoints.get(a-1);
			GeoPoint pointLast = new GeoPoint( (int)(gPtLast.getLongitude()*1E6), (int)(gPtLast.getLatitude()*1E6) );
			POINT pointLasts = mapView.map2Display(pointLast.getLongitude(), pointLast.getLatitude());
			if(a==1)
			{
				canvas.drawBitmap(mBitMapStart, pointLasts.x-16, pointLasts.y-45, mPaintLine);
				canvas.drawCircle(pointLasts.x, pointLasts.y, 8, paint);
			}
			else if(a==mBusLine.gPoints.size()-1)
			{
				canvas.drawBitmap(mBitMapEnd, points.x-16, points.y-45, mPaintLine);
				canvas.drawCircle(pointLasts.x, pointLasts.y, 8, paint);
			}
				canvas.drawLine( points.x, points.y, pointLasts.x, pointLasts.y, mPaintLine);
		}
	}
	
}
