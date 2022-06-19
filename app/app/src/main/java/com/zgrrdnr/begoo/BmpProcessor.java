package com.zgrrdnr.begoo;

import android.graphics.Bitmap;

import java.nio.ByteBuffer;

public class BmpProcessor
{
    public static Bitmap byteArrayToBmp(int width, int height, byte[] img)
    {
        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        ByteBuffer buffer = ByteBuffer.wrap(img);
        bmp.copyPixelsFromBuffer(buffer);

        return bmp;
    }

    public static Bitmap scaleBmp(float scaleFactorX, float scaleFactorY, Bitmap bmp)
    {
        int width  = (int) (bmp.getWidth() * scaleFactorX);
        int height = (int) (bmp.getHeight() * scaleFactorY);

        return Bitmap.createScaledBitmap(bmp, width, height, false);
    }
}
