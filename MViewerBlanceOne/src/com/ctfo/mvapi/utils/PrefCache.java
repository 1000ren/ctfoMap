package com.ctfo.mvapi.utils;

/**
 * @author fangwei
 * 
 * ���������ࡣ
 * 
 */
public class PrefCache
{
    private static PrefCache sInstance = new PrefCache();

    static PrefCache getInstance()
    {
        return sInstance;
    }

    public static void destroyInstance()
    {
        mbackGroundRunning = false;
    }

    public static boolean mbackGroundRunning = false;

    public static float density = 1.0f;

    private PrefCache()
    {
    }

}
