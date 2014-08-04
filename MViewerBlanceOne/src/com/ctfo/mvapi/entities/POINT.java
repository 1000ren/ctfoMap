package com.ctfo.mvapi.entities;

/**
 * @author fangwei
 * 
 * 屏幕坐标点
 * 
 */
public class POINT
{
	public POINT()
	{
		x = 0;
		y = 0;
	}

	/**
	 * 构造函数
	 * 
	 * @param X
	 *            X坐标
	 * @param Y
	 *            Y坐标
	 * @return
	 */
	public POINT(int X, int Y)
	{
		x = X;
		y = Y;
	}

	/**
	 * 构造函数
	 * 
	 * @param point
	 *            点坐标
	 * @return
	 */
	public POINT(POINT point)
	{
		x = point.x;
		y = point.y;
	}

	/**
	 * 设置X坐标
	 * 
	 * @param X
	 *            X坐标
	 * @return
	 */
	public void setX(int X)
	{
		x = X;
	}

	/**
	 * 设置Y坐标
	 * 
	 * @param Y
	 *            Y坐标
	 * @return
	 */
	public void setY(int Y)
	{
		y = Y;
	}

	/**
	 * 获取X坐标
	 * 
	 * @return X
	 */
	public final int getX()
	{
		return x;
	}

	/**
	 * 获取Y坐标
	 * 
	 * @return Y
	 */
	public final int getY()
	{
		return y;
	}

	// X坐标
	public int x = 0;

	// Y坐标
	public int y = 0;

}
