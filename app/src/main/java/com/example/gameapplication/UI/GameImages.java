package com.example.gameapplication.UI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameapplication.R;
import com.example.gameapplication.helper.BitmapMethods;
import com.example.gameapplication.main.MainActivity;

public enum GameImages implements BitmapMethods {
    MENU_IMAGE(R.drawable.mainmenu_menubackground);

    private final Bitmap image;

    GameImages(int imageiD) {
        options.inScaled = false;
        image = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(),imageiD,options);
    }

    public Bitmap getImage() {
        return image;
    }
}
