package com.example.gameapplication.maps.OutsideMap;

import static com.example.gameapplication.helper.BitmapMethods.options;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

import com.example.gameapplication.R;
import com.example.gameapplication.helper.BitmapMethods;
import com.example.gameapplication.helper.GameConstant;
import com.example.gameapplication.main.MainActivity;


public enum Buildings implements BitmapMethods {


    HOUSE_ONE(0, 0, 64, 48, 16,32,16,8),
    HOUSE_TWO(368, 0, 48, 48, 384, 32, 16, 8),
    HOUSE_THREE(464, 0, 64, 64, 480, 48, 16, 8),
    CAVE(0, 112, 48, 48, 16, 144, 16, 8),
    ENTRY_GATE_ONE(256, 256, 32, 32),
    ENTRY_GATE_TWO(272,256,32,32);



    Bitmap houseImg;
    RectF portalHitbox;

    Buildings(int x, int y, int width, int height, int portalX, int portalY, int portalWidth, int portalHeight) {
        options.inScaled = false;

        Bitmap atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), R.drawable.tileset_house, options);
        houseImg = getScaledBitmap(Bitmap.createBitmap(atlas, x, y, width, height));

        portalHitbox = new RectF(
                (portalX - x) * GameConstant.Maps.SCALE_MULTIPLIER,
                (portalY - y) * GameConstant.Maps.SCALE_MULTIPLIER,
                (portalWidth + portalX - x) * GameConstant.Maps.SCALE_MULTIPLIER,
                (portalHeight + portalY - y)* GameConstant.Maps.SCALE_MULTIPLIER
        );
//        System.out.println(portalHitbox);
    }

    Buildings(int x, int y, int width, int height) {
        options.inScaled = false;

        Bitmap atlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(), R.drawable.tileset_house, options);
        houseImg = getScaledBitmap(Bitmap.createBitmap(atlas, x, y, width, height));

    }

    public RectF getPortalHitbox() {
        return portalHitbox;
    }

    public Bitmap getHouseImg() {
        return houseImg;
    }


}