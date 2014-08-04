package com.ctfo.mvapi.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * @author fangwei
 * 
 * 自定义图像按钮类。
 * 
 */
public class CPImageButton extends LinearLayout
{
    public static final int IMAGEID_INVALID = 0xFFFFFFFF;

    int mImageID_Press;

    int mImageID_Normal;

    int mImageID_Enable;

    boolean mbEnable;

    ImageButton mButton;

    Context context;

    private OnImageButtonClickListener mClickListener;

    public CPImageButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mImageID_Press = IMAGEID_INVALID;
        mImageID_Normal = IMAGEID_INVALID;
        mImageID_Enable = IMAGEID_INVALID;

        mbEnable = false;
        mClickListener = null;

        this.context = context;
        this.setGravity(Gravity.CENTER);
    }

    protected void callBack(View arg0, MotionEvent arg1)
    {
        if (null != mClickListener) {
            mClickListener.OnClick(arg0, arg1);
        }
    }

    public void setClickListenr(OnImageButtonClickListener listener)
    {
        mClickListener = listener;
    }

    public void setBkImage(int imageId)
    {
        mImageID_Normal = imageId;
        refreshImage(mImageID_Normal);
    }

    public void setMotionImage(int down, int up, int enable)
    {
        mImageID_Press = down;
        mImageID_Normal = up;
        mImageID_Enable = enable;
        refreshImage(mImageID_Normal);
    }

    public void setEnable(boolean enable)
    {
        if (mbEnable == enable) {
            return;
        }

        mbEnable = enable;

        if (enable) {
            refreshImage(mImageID_Enable);
        } else {
            refreshImage(mImageID_Normal);
        }
    }

    private void refreshImage(int resId)
    {
        if (IMAGEID_INVALID != resId) {
            if (null == mButton) {
                mButton = new ImageButton(context);
                addView(mButton);

                mButton.setOnTouchListener(new OnTouchListener() {
                    public boolean onTouch(View arg0, MotionEvent arg1) {
                        if (mbEnable) {
                            return false;
                        }

                        if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                            refreshImage(mImageID_Press);
                        } else if (arg1.getAction() == MotionEvent.ACTION_UP) {
                            refreshImage(mImageID_Normal);
                        }

                        CPImageButton.this.callBack((View) arg0.getParent(),
                                arg1);

                        return false;
                    }
                });
            }

            mButton.setBackgroundResource(resId);
            mButton.invalidate();
        }
    }

    public interface OnImageButtonClickListener
    {
        public void OnClick(View arg0, MotionEvent arg1);
    }
}
