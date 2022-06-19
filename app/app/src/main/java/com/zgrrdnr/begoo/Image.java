package com.zgrrdnr.begoo;

import android.graphics.Bitmap;

public class Image
{
    private Bitmap bmp;
    private int     width;
    private int     height;
    private String  customStr;

    public Bitmap getBmp()
    {
        return bmp;
    }

    public void setBmp(Bitmap bmp)
    {
        this.bmp = bmp;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public String getCustomStr()
    {
        return customStr;
    }

    public void setCustomStr(String customStr)
    {
        this.customStr = customStr;
    }
}
