package com.example.gameapplication.maps;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameapplication.main.MainActivity;
import com.example.gameapplication.R;
import com.example.gameapplication.helper.BitmapMethods;
import com.example.gameapplication.helper.GameConstant;

public enum Tiles implements BitmapMethods {
    OUTSIDE(R.drawable.mapvillage,33,30),
    INSIDE_RESTAURANT(R.drawable.home_restaurant, 16,10),
    CAVE(R.drawable.cave, 63, 15);

    private Bitmap[] maps;
    private int tileWidth, tileHeight;
    Tiles(int tileset_floor, int tileWidth, int tileHeight) {
        options.inScaled = false;
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        maps = new Bitmap[tileHeight * tileWidth];
        Bitmap tileSheet = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(),tileset_floor,options);
        for (int i = 0; i < tileHeight; i++)
        {
            for(int j = 0; j < tileWidth; j++)
            {
                int index = i * tileWidth + j;
                maps[index] = getScaledBitmap(Bitmap.createBitmap(tileSheet, GameConstant.Maps.DEFAULT_SIZE * j, GameConstant.Maps.DEFAULT_SIZE * i, GameConstant.Maps.DEFAULT_SIZE, GameConstant.Maps.DEFAULT_SIZE));
            }
        }

    }
    public Bitmap getTile(int id){
        return maps[id];
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }
}
