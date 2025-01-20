package com.example.gameapplication.entities;

import static com.example.gameapplication.helper.GameConstant.Maps.CHARACTER_HITBOX_SIZE;
import android.graphics.PointF;

import com.example.gameapplication.helper.GameConstant;

public class Character extends Entity{
    protected int aniTick, aniIndex;
    protected int faceDir = GameConstant.Face_Dir.DOWN;
    protected GameCharacter gameCharType;

    public Character(PointF pos, GameCharacter GameCharType){
        super(pos, CHARACTER_HITBOX_SIZE, CHARACTER_HITBOX_SIZE);
        this.gameCharType = GameCharType;

    }
    protected void updateAnimation() {
        aniTick++;
        if (aniTick >= GameConstant.Animation.SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GameConstant.Animation.AMT)
                aniIndex = 0;
        }
    }

    public void setFaceDir(int faceDir) {
        this.faceDir = faceDir;
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getFaceDir() {
        return faceDir;
    }

    public GameCharacter getGameCharType() {
        return gameCharType;
    }

    public void resetAnimation() {
        aniTick = 0;
        aniIndex = 0;
    }
}
