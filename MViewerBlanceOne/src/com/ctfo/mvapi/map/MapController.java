package com.ctfo.mvapi.map;

import android.view.KeyEvent;
import android.view.View;

import com.ctfo.mvapi.entities.GeoPoint;

/**
 * @author fangwei
 * 
 * 工具类，用于控制地图的缩放（ZoomIn、ZoomOut）和平移（Pan）。
 * 
 */
public class MapController implements android.view.View.OnKeyListener
{
	MapDisplay mMapDisplay = null;
	
	private long mLastTime = 0;

	public MapController( MapDisplay mapDisplay )
	{
		mMapDisplay = mapDisplay;
	}

	// 从当前位置开始到给定的geopoint动画显示地图。
	public void animateTo( GeoPoint geopoint )
	{
		// 动画...
		if ( mMapDisplay.setMapPos( geopoint ) )
		{
			MapView.mthis.refresh();
		}
	}

	// 在给定的中心点GeoPoint上设置地图视图。
	public void setCenter( GeoPoint geopoint )
	{
		if ( mMapDisplay.setMapPos( geopoint ) )
		{
			MapView.mthis.refresh();
		}
	}
	
	/**
	 * 判断上一次点击和这次的间隔，防止频繁点击频繁调用
	 * @return
	 */
	private boolean timeJudge()
	{
		long time = System.currentTimeMillis();
		if(time - mLastTime > 500)
		{
			mLastTime  = time;
			return true;
		}
		else
		{
			return false;
		}
	}

	// 放大一个级别
	public void zoomIn()
	{
		if ( timeJudge() && mMapDisplay.zoomIn() )
		{
			MapView.mthis.refresh();
		}
	}
	
	// 放大N个级别
	public void zoomIn(int level)
	{
		if ( timeJudge() && mMapDisplay.zoomIn(level) )
		{
			MapView.mthis.refresh();
		}
	}

	// 缩小一个级别
	public void zoomOut()
	{
		if ( timeJudge() && mMapDisplay.zoomOut() )
		{
			MapView.mthis.refresh(); 
		}
	}
	
	// 缩小N个级别
	public void zoomOut(int level)
	{
		if ( timeJudge() && mMapDisplay.zoomOut(level) )
		{
			MapView.mthis.refresh(); 
		}
	}

	// 设置显示比例等级
	public void setZoom( int level )
	{
		if ( timeJudge() && mMapDisplay.setMapScale( level ) )
		{
			MapView.mthis.refresh();
		}
	}

	// 设置合适的显示等级
	public void setFitView( GeoPoint[] pts )
	{
		// 
	}

	// 计算两点间距离
	public double calculateDistance( GeoPoint pt1, GeoPoint pt2 )
	{
		return MapTile.getDistince(pt1.getLongitude(), pt1.getLatitude(), pt2.getLongitude(), pt2.getLatitude());
	}

	
	// 处理按键事件，把事件变换为适度的地图平移。
	@Override
	public boolean onKey(View arg0, int arg1, KeyEvent arg2)
	{
		return false;
	}
	
	
}
