package com.ctfo.mvapi.map;


/**
 * @author fangwei
 * 
 * Overlay是一个基类，它表示可以覆盖在地图上方显示的overlay。添加一个overlay时，
 * 从这个基类派生出一个子类，创建一个实例，然后把它加入到一个列表中。
 * 
 */
public class Overlay
{
	/**
	 * 在每个item上绘制一个标记点。
	 * @param canvas 待绘制的画布。
	 * @param mapView 请求绘制的MapView。
	 * @param shadow 一个布尔值，若为true，绘制阴影层；如果为false，则绘制overlay的内容。
	 */
	public void draw(android.graphics.Canvas canvas, MapView mapView, boolean shadow)
	{
	}
	
}
