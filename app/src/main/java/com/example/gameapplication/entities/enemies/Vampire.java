package com.example.gameapplication.entities.enemies;

import static com.example.gameapplication.main.MainActivity.PhoneHeight;
import static com.example.gameapplication.main.MainActivity.PhoneWidth;

import android.graphics.PointF;

import com.example.gameapplication.entities.Character;
import com.example.gameapplication.entities.GameCharacter;
import com.example.gameapplication.helper.GameConstant;
import com.example.gameapplication.main.MainActivity;
import com.example.gameapplication.maps.GameMaps;

import java.util.Random;

public class Vampire extends Character {
    private long lastDirChange = System.currentTimeMillis();
    private Random rand = new Random();

    public Vampire(PointF pos) {
        super(pos, GameCharacter.VAMPIRE);
    }

    public void update(double delta, GameMaps gameMaps){
        updateMove(delta, gameMaps);
        updateAnimation();
    }

    private void updateMove(double delta, GameMaps gameMaps) {
        if (System.currentTimeMillis() - lastDirChange >= 3000) {
            faceDir = rand.nextInt(4);
            lastDirChange = System.currentTimeMillis();
        }

        switch (faceDir) {
            case GameConstant.Face_Dir.DOWN:
                hitbox.top += delta * 300;
                hitbox.bottom += delta * 300;
                if (hitbox.bottom >= (gameMaps.getHeight() * GameConstant.Maps.SIZE)) {
                    faceDir = GameConstant.Face_Dir.UP;
                }
                break;

            case GameConstant.Face_Dir.UP:
                hitbox.top -= delta * 300;
                hitbox.bottom -= delta * 300;
                if (hitbox.top <= 0) {
                    faceDir = GameConstant.Face_Dir.DOWN;
                }
                break;

            case GameConstant.Face_Dir.RIGHT:
                hitbox.left  += delta * 300;
                hitbox.right += delta * 300;
                if (hitbox.right  >= (gameMaps.getWidth() * GameConstant.Maps.SIZE)) {
                    faceDir = GameConstant.Face_Dir.LEFT;
                }
                break;

            case GameConstant.Face_Dir.LEFT:
                hitbox.left  -= delta * 300;
                hitbox.right -= delta * 300;
                if (hitbox.left  <= 0) {
                    faceDir = GameConstant.Face_Dir.RIGHT;
                }
                break;
        }
    }
}
