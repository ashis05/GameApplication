package com.example.gameapplication.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gameapplication.main.MainActivity;
import com.example.gameapplication.R;
import com.example.gameapplication.helper.BitmapMethods;
import com.example.gameapplication.helper.GameConstant;

public enum GameCharacter implements BitmapMethods {
    PLAYER(R.drawable.player_spritesheet),
    VAMPIRE(R.drawable.vampire_spritesheet);

    private Bitmap spriteSheet;
    private Bitmap[][] playerSprite = new Bitmap[7][4];


    GameCharacter(int playerSpritesheet) {
        options.inScaled = false;
        spriteSheet = BitmapFactory.decodeResource(MainActivity.getGameContext().getResources(),playerSpritesheet,options);
        for (int i = 0; i < playerSprite.length; i++)
        {
            for(int j = 0; j < playerSprite[i].length; j++)
            {
                playerSprite[i][j] = getScaledBitmap(Bitmap.createBitmap(spriteSheet, GameConstant.Maps.DEFAULT_SIZE * j, GameConstant.Maps.DEFAULT_SIZE * i, GameConstant.Maps.DEFAULT_SIZE, GameConstant.Maps.DEFAULT_SIZE));
            }
        }
    }

    public Bitmap getSpriteSheet() {
        return spriteSheet;
    }

    public Bitmap getSprite(int yPos, int xPos)
    {
        return playerSprite[yPos][xPos];
    }


}
