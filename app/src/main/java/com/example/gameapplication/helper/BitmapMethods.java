package com.example.gameapplication.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public interface BitmapMethods {
    BitmapFactory.Options options = new BitmapFactory.Options();
    default Bitmap getScaledBitmap(Bitmap bitmap)
    {
        return Bitmap.createScaledBitmap(bitmap, bitmap.getWidth() * GameConstant.Maps.SCALE_MULTIPLIER, bitmap.getHeight() * GameConstant.Maps.SCALE_MULTIPLIER,false);
    }
}

