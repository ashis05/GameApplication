package com.example.gameapplication.maps.Cave;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameapplication.R;
import com.example.gameapplication.helper.BitmapMethods;
import com.example.gameapplication.main.MainActivity;

public enum Elements implements BitmapMethods {
    RANDOM(0, 0, 16, 32),
    SHORT_POLE(112, 272, 16, 32),
    CRYSTAL_FIGURE(48, 240, 32, 32),
    FIGURE(80, 240, 32, 32),
    FROG(48, 272, 32, 32),
    LONG_POLE(0, 240, 16, 48),
    FLAG(384, 320, 16, 32);

    public Bitmap houseImg;

    Elements(int x, int y, int width, int height) {
        options.inScaled = false;

        Bitmap atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), R.drawable.tileset_house, options);
        houseImg = getScaledBitmap(Bitmap.createBitmap(atlas, x, y, width, height));
    }

    public Bitmap getHouseImg() {
        return houseImg;
    }
}
