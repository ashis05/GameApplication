package com.example.gameapplication.UI;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameapplication.R;
import com.example.gameapplication.helper.BitmapMethods;
import com.example.gameapplication.main.MainActivity;

public enum ButtonImage implements BitmapMethods {
    MENU_START(R.drawable.mainmenu_button_start,300,140),
    PLAYING_MENU(R.drawable.playing_button_menu,140,140);

    private int height, width;
    private Bitmap normal, pushed;

    ButtonImage(int ButtonID, int width, int height){
        options.inScaled = false;
        this.height = height;
        this.width = width;

        Bitmap buttonAtlas = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(),ButtonID,options);
        normal = Bitmap.createBitmap(buttonAtlas,0,0,width,height);
        pushed = Bitmap.createBitmap(buttonAtlas,width,0,width,height);
    }

    public Bitmap getButtonImage(boolean isButtonPushed){
        if (isButtonPushed){
            return pushed;
        }
        return normal;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
