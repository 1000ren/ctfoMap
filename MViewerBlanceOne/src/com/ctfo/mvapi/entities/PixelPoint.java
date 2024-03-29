package com.ctfo.mvapi.entities;

/**
 * @author fangwei
 * 
 * 标准格网坐标系下的像素点坐标
 *
 */
public class PixelPoint
{
	public int x;
	public int y;

	public PixelPoint()
	{
		x = 0;
		y = 0;
	}

	public PixelPoint(int i, int j)
	{
		x = i;
		y = j;
	}

	public void SetData(int nX, int nY)
	{
		x = nX;
		y = nY;
	}
}
