package com.example.gameapplication.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameapplication.R;
import com.example.gameapplication.helper.BitmapMethods;
import com.example.gameapplication.main.MainActivity;

public enum Weapons implements BitmapMethods {
    SWORD(R.drawable.big_sword),
    SHADOW(R.drawable.shadow);
    final Bitmap weaponImg;

    Weapons(int sword) {
        options.inScaled = false;
        weaponImg = getScaledBitmap(BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(),sword,options));
    }

    public Bitmap getWeaponImg() {
        return weaponImg;
    }

    public int getWidth(){
        return weaponImg.getWidth();
    }

    public int getHeight(){
        return weaponImg.getHeight();
    }
}
