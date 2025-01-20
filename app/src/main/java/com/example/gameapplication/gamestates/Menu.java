package com.example.gameapplication.gamestates;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.gameapplication.UI.ButtonImage;
import com.example.gameapplication.UI.CustomButton;
import com.example.gameapplication.UI.GameImages;
import com.example.gameapplication.main.Game;
import com.example.gameapplication.main.MainActivity;

public class Menu extends BaseState implements GameStates{

    private Paint paint;
    private CustomButton startButton;
    private int menuX = MainActivity.PhoneWidth / 6;
    private int menuY = 200;

    private int btnStartX = menuX + GameImages.MENU_IMAGE.getImage().getWidth() / 2 - ButtonImage.MENU_START.getWidth() / 2;
    private int btnStartY = menuY + 100;

    public Menu(Game game){
        super(game);
        startButton = new CustomButton(btnStartX, btnStartY, ButtonImage.MENU_START.getWidth(), ButtonImage.MENU_START.getHeight());
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void render(Canvas c) {
        c.drawBitmap(GameImages.MENU_IMAGE.getImage(), menuX, menuY, null);
        c.drawBitmap(ButtonImage.MENU_START.getButtonImage(startButton.isPushed()),startButton.getHitbox().left,startButton.getHitbox().top,null);
    }

    @Override
    public void touchEvents(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (isIn(event, startButton))
                startButton.setPushed(true);
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (isIn(event, startButton))
                if (startButton.isPushed())
                    game.setCurrentGameState(Game.GameState.PLAYING);

            startButton.setPushed(false);
        }

    }

    private boolean isIn(MotionEvent e, CustomButton b){
        return b.getHitbox().contains(e.getX(),e.getY());
    }
}
