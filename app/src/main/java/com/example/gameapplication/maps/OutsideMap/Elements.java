package com.example.gameapplication.maps.OutsideMap;

import static com.example.gameapplication.helper.BitmapMethods.options;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameapplication.R;
import com.example.gameapplication.helper.BitmapMethods;
import com.example.gameapplication.main.MainActivity;

public enum Elements implements BitmapMethods {
    LIGHT_POLE(96, 48, 16, 32),
    HANGER_LEFT(176, 32, 16, 32),
    HANGER_RIGHT(208, 32, 16, 32),
    HANGER_CLOTHES(224, 32, 32,32),
    EMPTY_CART(0, 48, 32, 32),
    FULL_CART(32, 48, 32, 32),
    BOXES(64, 48, 2, 2),
    WELL(112, 16, 16, 32),
    BOARD_SIGN(16,32,16,16),
    RIP(64, 32, 16, 16);


    public Bitmap houseImg;

    Elements(int x, int y, int width, int height) {
        options.inScaled = false;

        Bitmap atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), R.drawable.tileset_element, options);
        houseImg = getScaledBitmap(Bitmap.createBitmap(atlas, x, y, width, height));
    }

    public Bitmap getHouseImg() {
        return houseImg;
    }
}
