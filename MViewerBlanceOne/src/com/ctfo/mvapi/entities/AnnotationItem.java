package com.ctfo.mvapi.entities;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;


public class AnnotationItem implements Serializable
{
	private static final long serialVersionUID = 2L;
	// id
	public int id = 0;
	// name
	public String name = new String("");
	// 类型编码（用于对应图标）
	public int code = 0;
	// 经度
	public double longitude = 0.0;
	// 纬度
	public double latitude = 0.0;
	// 标记方位
	public int labelDirect = 0;


	public AnnotationItem( int id, String name, int code, double lon, double lat, int direct )
	{
		this.id = id;
		this.name = name;
		this.code = code;
		this.longitude = lon;
		this.latitude = lat;
		this.labelDirect = direct;
	}
	
	public AnnotationItem()
	{
	}
	
	public JSONObject packageJson() 
	{
		JSONObject jo = new JSONObject();
		try 
		{
			jo.put("id", id);
			jo.put("name", name==null?"":name);
			jo.put("code", code);
			jo.put("longitude", longitude);
			jo.put("latitude", latitude);
			jo.put("labelDirect", labelDirect);
		} 
		catch(JSONException e)
    	{
    		Log.d("packageJson-1",e.getMessage());
    	}
		return jo;
	}
	
	public AnnotationItem setJSONObjectToObject(JSONObject jsonObj)
	{
		AnnotationItem oAnnotationItem = new AnnotationItem();
		try
		{
			oAnnotationItem.id =  jsonObj.getInt("id");
			if ( !jsonObj.isNull( "name" ) )
			{
				oAnnotationItem.name =  jsonObj.getString("name");
			}
			oAnnotationItem.code =  jsonObj.getInt("code");
			oAnnotationItem.longitude = jsonObj.getDouble("longitude");
			oAnnotationItem.latitude = jsonObj.getDouble("latitude");
			oAnnotationItem.labelDirect = jsonObj.getInt("labelDirect");
		}
		catch(JSONException e)
		{
			Log.d("setJSONObjectToObject-1",e.getMessage());
		}
		return oAnnotationItem;
	}

}
