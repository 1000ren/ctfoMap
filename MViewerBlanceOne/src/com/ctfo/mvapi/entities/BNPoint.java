package com.ctfo.mvapi.entities;

import java.io.Serializable;

/**
 * 图片旋转记录实体类
 * @author fangwei
 *
 */
public class BNPoint implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	//触控的X坐标
	public float x; 
	//触控的Y坐标
	public float y;
	//上一次触控的X坐标
	public float oldX; 
	//上一次触控的Y坐标
	public float oldY;
	//是否存在上次触控位置
	public boolean hasOld = false; 

	public BNPoint(float x, float y) 
	{
		this.x = x; 
		this.y = y;
	}
	
	/**
	 * 记录上次触控位置，和新的触控位置
	 * @param x
	 * @param y
	 */
	public void setLocation(float x,float y)  
    {
		//把原来位置记录为旧位置
	    oldX=this.x;  
	    oldY=this.y;
	    
	    //设置是否已经有上一次位置的标志位
	    hasOld=true; 
	    
	    //设置新位置
	    this.x=x;     
	    this.y=y;
    }
}
