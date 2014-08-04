package com.ctfo.mvapi.entities;

import android.graphics.Bitmap;

/**
 * @author fangwei
 * 
 * Tile包含的信息，目前将图片与文字放在一起管理
 *
 */
public class TileMapInfo
{
	public String code;
	
	public Bitmap tileBitmap;
	
	public TileTextInfo tileTextInfo;

	public TileMapInfo( String code, Bitmap tileBitmap, TileTextInfo tileTextInfo )
	{
		this.code = code;
		this.tileBitmap = tileBitmap;
		this.tileTextInfo = tileTextInfo;
	}
	public TileMapInfo( String code, Bitmap tileBitmap)
	{
	    this.code = code;
	    this.tileBitmap = tileBitmap;
	}

}
