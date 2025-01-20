package com.example.gameapplication.entities;

import android.graphics.PointF;

import com.example.gameapplication.main.MainActivity;

public class Player extends Character{
    public Player( ) {
        super(new PointF(MainActivity.PhoneWidth/2, MainActivity.PhoneHeight/2) , GameCharacter.PLAYER);
    }
    public void update(double delta, boolean movePlayer){
        if (movePlayer){
            updateAnimation();
        }
    }
}
