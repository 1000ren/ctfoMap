package com.ctfo.mviewer.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import cm.framework.net.ClientSession;
import cm.framework.net.ControlRunnable;
import cm.framework.net.IErrorListener;
import cm.framework.net.IResponseListener;
import cm.framework.protocol.BaseHttpRequest;
import cm.framework.protocol.BaseHttpResponse;
import cm.framework.protocol.ErrorResponse;

import com.ctfo.mvapi.MVApi;
import com.ctfo.mvapi.entities.GeoPoint;
import com.ctfo.mvapi.map.MapActivity;
import com.ctfo.mvapi.map.MapDef;
import com.ctfo.mvapi.map.MapView;
import com.ctfo.mvapi.map.MapViewListener;
import com.ctfo.mvapi.utils.CPImageButton;
import com.ctfo.mvapi.utils.CPImageButton.OnImageButtonClickListener;
import com.ctfo.mviewer.R;
import com.ctfo.mviewer.entity.BusStation;
import com.ctfo.mviewer.overlay.BusLineOverlay;
import com.ctfo.mviewer.overlay.BusStationOverlay;
import com.ctfo.mviewer.overlay.MyLocationManager;
import com.ctfo.mviewer.protocol.GetBusLineListRequest;
import com.ctfo.mviewer.protocol.GetBusLineListResponse;
import com.ctfo.mviewer.protocol.GetBusStationListRequest;
import com.ctfo.mviewer.protocol.GetBusStationListResponse;
import com.ctfo.mviewer.protocol.GetBusStopListRequest;
import com.ctfo.mviewer.protocol.GetBusStopListResponse;


public class MVMapActivity extends MapActivity
{
    public static MVMapActivity mapActivity;
    static ViewControl viewCtrl;
    private EditText mEditText;
    private EditText mWidth;
    private EditText mHeight;
    private LinearLayout mLinearLayout;
    private LinearLayout mLinearLayout2;
    private Button mButton;
    private Button mButtonCompass;
    private BusStationOverlay mBusStationOverlay;
    private BusLineOverlay mBusLineOverlay;
    private Context mContext;
    private ImageView mMyLocation;
    private MyLocationManager mMyLocationManager;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        

        setContentView(R.layout.activity_map);
        mapActivity = this;
        mContext = this;
        mMapView = (MapView) findViewById(R.id.mapview);
        mMapController = mMapView.getController();

        mMyLocation = (ImageView) findViewById(R.id.mylocation);
        
        mLinearLayout = (LinearLayout) findViewById(R.id.ll1);
        mEditText = (EditText) findViewById(R.id.editText);
        mButton = (Button) findViewById(R.id.button);
        mLinearLayout.setVisibility(8);
        
        mWidth = (EditText) findViewById(R.id.editTextWidth);
        mHeight = (EditText) findViewById(R.id.editTextHeight);
        mButtonCompass = (Button) findViewById(R.id.buttonCompass);
        mLinearLayout2 = (LinearLayout) findViewById(R.id.ll2);
        mLinearLayout2.setVisibility(8);
        
        viewCtrl = new ViewControl();
        viewCtrl.InitView();
        
        //监听mapview
        mMapView.setOnMapViewListener(new MapViewListener() 
        {
			@Override
			public void onMapMoveFinish() 
			{
				mMyLocation.setImageResource(R.drawable.direct_disenable);
			}

			@Override
			public void onMapZoomChanged() 
			{
			}
		});
        mMapController.setCenter( new GeoPoint(MapDef.cLon, MapDef.cLat));
        mMapController.setZoom(15);
        
        //单击我的位置监听
        mMyLocation.setOnClickListener(new OnClickListener() 
        {
			@Override
			public void onClick(View v) 
			{
				mMapController.setCenter( new GeoPoint(MapDef.cLon, MapDef.cLat));
				mMyLocation.setImageResource(R.drawable.direct_enable);
			}
		});
        
        mMyLocationManager = new MyLocationManager(this);
        mMyLocationManager.setMyLocation(new GeoPoint(MapDef.cLon, MapDef.cLat));
        
        mMapView.setMyLocation(mMyLocationManager);
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
    public boolean onPrepareOptionsMenu(Menu menu)
	{
        boolean result = super.onPrepareOptionsMenu(menu);

        menu.clear();
        
//        Command.addMenuItem(menu, Command.MENU_BACKPOS);
//        Command.addMenuItem(menu, Command.MENU_JUMP);
//
//        Command.addMenuItem(menu, Command.MENU_ROTATE);
        Command.addMenuItem(menu, Command.MENU_PERSPECTIVE);
        
        Command.addMenuItem(menu, Command.MENU_CLEARSRC);
        Command.addMenuItem(menu, Command.MENU_CLEARCACHE);

        // 查询站点
        Command.addMenuItem(menu, Command.MENU_SEARCHSTATION);
        // 查询线路
        Command.addMenuItem(menu, Command.MENU_SEARCHLINE);
        // 查询POI
//        Command.addMenuItem(menu, Command.MENU_SEARCHPOI);
        //设置指南针位置
//        Command.addMenuItem(menu, Command.MENU_COMPASSSIZE);

        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        final int iSelectedCMD = item.getItemId();

        switch (iSelectedCMD) 
        {
	        case Command.MENU_BACKPOS:
	        {
	        	mMapController.setCenter( new GeoPoint(MapDef.cLon, MapDef.cLat) );
	        	break;
	        }
	        case Command.MENU_JUMP:
	        {
	        	mMapController.setCenter( new GeoPoint(116.35564, 39.94054) );
	        	break;
	        }
	        case Command.MENU_ROTATE:
	        {
	        	//mMapView.mMapDisplay.moveMap(-10, 0);
	        	//mMapView.refresh();
	        	break;
	        }
	        case Command.MENU_PERSPECTIVE:
	        {
	        	mMapView.setPerspectiveTrans(true);
	        	break;
	        }
	        case Command.MENU_CLEARSRC:
	        {
	        	// 
	        	mHandler.sendEmptyMessage(2);
	        	mMapView.setMapAngle(0);
	        	mMapView.setPerspectiveTrans(false);
	        	mLinearLayout.setVisibility(4);
	        	mLinearLayout2.setVisibility(4);
	        	break;
	        }
	        case Command.MENU_CLEARCACHE:
	        {
	        	MVApi.clearTileCache();
	        	mHandler.sendEmptyMessage(2);
	        	break;
	        }
	        case Command.MENU_SEARCHSTATION:
	        {
	        	searchstation();
	        	break;
	        }
	        case Command.MENU_SEARCHLINE:
	        {
	        	searchLine();
	        	break;
	        }
	        case Command.MENU_SEARCHPOI:
	        {
	        	// 
	        	break;
	        }
	        case Command.MENU_COMPASSSIZE:
	        {
	        	setCompassSize();
	        	break;
	        }
        	default:
        	{
        		break;
        	}
        }
        return true;
    }
    
    
    private void searchstation()
    {
    	mLinearLayout2.setVisibility(4);
    	mLinearLayout.setVisibility(0);
    	mHandler.sendEmptyMessage(2);
    	mButton.setText("搜索站点");
    	mButton.setOnClickListener(new OnClickListener() 
    	{
			@Override
			public void onClick(View v) 
			{
				String mKeyword = mEditText.getText().toString();
				
				ClientSession.getInstance().setDefStateReceiver(null);
				ClientSession.getInstance().setDefErrorReceiver(null);
				ClientSession.getInstance().asynGetResponse(new GetBusStationListRequest( 
						"", mKeyword, 1, 10 , "110000"),
						new IResponseListener()
				{
					@Override
					public void onResponse(BaseHttpResponse arg0, BaseHttpRequest arg1,ControlRunnable arg2) 
					{
							GetBusStationListResponse busStationListResponse = (GetBusStationListResponse) arg0;
							
							Bitmap marker = BitmapFactory.decodeResource(getResources(), R.drawable.icon_poi);
				        	mBusStationOverlay = new BusStationOverlay(marker,mContext,busStationListResponse.mBusStationRes.mBusStationList,getResources());
				        	BusStation oBusStation = busStationListResponse.mBusStationRes.mBusStationList.get(0);
				        	mHandler.sendEmptyMessage(0);
				        	mMapController.setCenter( oBusStation.gPoint);
					}
				}, new IErrorListener() 
				{
					@Override
					public void onError(ErrorResponse arg0, BaseHttpRequest arg1,
							ControlRunnable arg2) 
					{
						if(null != arg0.getErrorDesc() && !arg0.equals("") && arg0.getErrorType() != 1007)
						{
							Message msg = new Message();
							msg.what = 3;
							msg.obj = arg0.getErrorDesc();
							mHandler.sendMessage(msg);
						}
						
					}
				});
			}
		});
    }
    
    private void setCompassSize()
    {
    	mLinearLayout.setVisibility(4);
    	mLinearLayout2.setVisibility(0);
    	
    	
    	
    	mButtonCompass.setOnClickListener(new OnClickListener() 
    	{
			@Override
			public void onClick(View v) 
			{
				String width = mWidth.getText().toString();
		    	String height = mHeight.getText().toString();
		    	if(width==null || width.equals("") || height==null || height.equals(""))
		    	{
		    		Message msg = new Message();
					msg.what = 3;
					msg.obj = "请正确填写高度和宽度";
					mHandler.sendMessage(msg);
		    	}
		    	else
		    	{
		    		mMapView.setCompassSize(Integer.parseInt(width), Integer.parseInt(height));
		    		mLinearLayout2.setVisibility(4);
		    	}
			}
		});
    }
    
    private void searchLine()
    {
		mLinearLayout.setVisibility(0);
		mLinearLayout2.setVisibility(4);
		mHandler.sendEmptyMessage(2);
		mButton.setText("搜索线路");
		mButton.setOnClickListener(new OnClickListener() 
		{
			@Override
			public void onClick(View v) 
			{
				String mKeyword = mEditText.getText().toString();
				ClientSession.getInstance().asynGetResponse(
								new GetBusLineListRequest("", mKeyword, 1, 1,
										"110000"), new IResponseListener() 
								{
									@Override
									public void onResponse(
											BaseHttpResponse arg0,
											BaseHttpRequest arg1,
											ControlRunnable arg2) 
									{
										GetBusLineListResponse busLineListResponse = (GetBusLineListResponse) arg0;

										if (null != busLineListResponse.mBusLineRes.mBusLineList
												&& busLineListResponse.mBusLineRes.mBusLineList
														.size() > 0) 
										{
											ClientSession.getInstance().asynGetResponse(new GetBusStopListRequest( 
													"", busLineListResponse.mBusLineRes.mBusLineList.get(0).lineid , busLineListResponse.mBusLineRes.mBusLineList.get(0).admincode),
													new IResponseListener()
											{
														@Override
														public void onResponse(BaseHttpResponse arg0, BaseHttpRequest arg1,ControlRunnable arg2)
														{
															GetBusStopListResponse busstopListResponse = (GetBusStopListResponse) arg0;
															mBusLineOverlay = new BusLineOverlay(busstopListResponse.mBusLine,
																	getResources());
															mHandler.sendEmptyMessage(1);
															mMapController.setCenter( busstopListResponse.mBusLine.gPoints.get(1));
														}
											});
										}

									}
								}, new IErrorListener() 
								{
									@Override
									public void onError(ErrorResponse arg0, BaseHttpRequest arg1,
											ControlRunnable arg2) 
									{
										if(arg0 != null && null != arg0.getErrorDesc() && !arg0.equals("") && arg0.getErrorType() != 1007)
										{
											Message msg = new Message();
											msg.what = 3;
											msg.obj = arg0.getErrorDesc();
											mHandler.sendMessage(msg);
										}
										
									}
								});
			}
		});
    }
    
    private Handler mHandler = new Handler()
    {
    	@Override
    	public void handleMessage(Message msg) 
    	{
    		super.handleMessage(msg);
    		switch(msg.what)
    		{
	    		case 0://站点
	    			mMapView.setItemized(mBusStationOverlay);
	    			break;
	    		case 1://线路
	    			mMapView.setOverlay(mBusLineOverlay);
	    			break;
	    			
	    		case 2://清空
	    			mMapView.clearAllLines();
	    			mMapView.clearAllPOIs();
	    			break;
	    		case 3:
	    			Toast.makeText(mContext, msg.obj.toString(), Toast.LENGTH_LONG).show();
	    			break;
    		}
    	}
    };

    OnImageButtonClickListener clickListener_spec = new OnImageButtonClickListener()
    {
        // @Override
        public void OnClick(View arg0, MotionEvent arg1)
        {
            if (arg1.getAction() == MotionEvent.ACTION_UP)
            {
                switch (arg0.getId())
                {
                case R.id.map_btn_zoomin:
                	mMapController.zoomIn();
                    break;
                case R.id.map_btn_zoomout:
                	mMapController.zoomOut();
                    break;
                default:
                    break;
                }
            }
        }
    };
    
    private class ViewControl
    {
        CPImageButton btnZoomout;
        CPImageButton btnZoomin;

        private void InitView()
        {
            btnZoomout = (CPImageButton) findViewById(R.id.map_btn_zoomout);
            btnZoomin = (CPImageButton) findViewById(R.id.map_btn_zoomin);

            btnZoomout.setMotionImage(R.drawable.navmain_bt_zoomout2,
                    R.drawable.navmain_bt_zoomout1,
                    R.drawable.navmain_bt_zoomout3);

            btnZoomin.setMotionImage(R.drawable.navmain_bt_zoomin2,
                    R.drawable.navmain_bt_zoomin1,
                    R.drawable.navmain_bt_zoomin3);

            btnZoomout.setClickListenr(clickListener_spec);
            btnZoomin.setClickListenr(clickListener_spec);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {

            AppExit();
            return true;

        } 
        return super.onKeyDown(keyCode, event);
    }
    
    public void AppExit()
    {
        new AlertDialog.Builder(mContext).setTitle( R.string.app_name ).setMessage(
        "MViewer will be closed, Are you sure ?").setPositiveButton( "退出",
        new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                 {
                     System.exit(0);
                }
            }
        }).setNegativeButton( "取消",
        new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }
}
