package com.example.gameapplication.gamestates;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface GameStates {
    public void update(double delta);
    public void render(Canvas c);
    public void touchEvents(MotionEvent event);
}
