package com.ctfo.mvapi.map;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;


public class MapActivity extends Activity
{
    public static MapActivity mapActivity = null;
    
    public MapView mMapView = null;
    public MapController mMapController = null;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mapActivity = this;
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		if(keyCode == KeyEvent.KEYCODE_BACK && null != mMapView) 
		{ 
			mMapView.clearTileMapCache();
		}
		return super.onKeyDown(keyCode, event);
	}  
}
