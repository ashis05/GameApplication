package com.example.gameapplication.UI;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.MotionEvent;

import com.example.gameapplication.gamestates.Playing;
import com.example.gameapplication.main.Game;
import com.example.gameapplication.main.MainActivity;

public class PlayingUI {

    private final Playing playing;
    //UI
    private final PointF joyStickCenterPos = new PointF(400,MainActivity.PhoneHeight - 400);
    private final PointF attackButtonCenterPos = new PointF(MainActivity.PhoneWidth - 400,MainActivity.PhoneHeight - 400);
    public final float radius = 150;
    private Paint circlePaint = new Paint();
    private boolean TouchDown;
    private CustomButton menuButton;
    private final Paint redPaint = new Paint();
    private int attackButtonPointerID = -1;

    private int joyStickPointerID = -1;


    public PlayingUI(Playing playing) {
        redPaint.setColor(Color.RED);
        this.playing = playing;
        menuButton = new CustomButton(5,5, ButtonImage.PLAYING_MENU.getWidth(), ButtonImage.PLAYING_MENU.getHeight());

    }

    public void drawUI(Canvas c) {
        c.drawCircle(joyStickCenterPos.x,joyStickCenterPos.y,radius,circlePaint);
        c.drawCircle(attackButtonCenterPos.x,attackButtonCenterPos.y,radius,circlePaint);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(Color.WHITE);
        circlePaint.setStrokeWidth(5);
        c.drawBitmap(ButtonImage.PLAYING_MENU.getButtonImage(menuButton.isPushed(menuButton.getPointerID())), menuButton.getHitbox().left, menuButton.getHitbox().top, null);
    }

    private boolean isInsideRadius(PointF eventPos, PointF circle){
        float a = Math.abs(eventPos.x - circle.x);
        float b = Math.abs(eventPos.y - circle.y);
        float c = (float)Math.hypot(a,b);
        if (c <= radius) {
            return true;
        }
        return false;
    }

    private boolean checkInsideAttackButton(PointF eventPos){
        return isInsideRadius(eventPos, attackButtonCenterPos);
    }

    private boolean checkInsideJoystick(PointF eventPos, int pointerID){
        if (isInsideRadius(eventPos, joyStickCenterPos)) {
            TouchDown = true;
            joyStickPointerID = pointerID;
            return true;
        }
        return false;
    }

    public void touchEvents(MotionEvent event){
        final int action = event.getActionMasked();
        final int actionIndex = event.getActionIndex();
        final int pointerID = event.getPointerId(actionIndex);

        final PointF eventPos = new PointF(event.getX(actionIndex),event.getY(actionIndex));

        switch (action){
            case MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN: {
                if (checkInsideJoystick(eventPos, pointerID)) {
                    TouchDown = true;
                    System.out.println("Joystick");
                }else if (checkInsideAttackButton(eventPos)) {
                    if(attackButtonPointerID < 0){
                        playing.setAttacking(true);
                        attackButtonPointerID = pointerID;
                    }
                }
                else{
                    if (isIn(eventPos,menuButton)){
                        menuButton.setPushed(true, pointerID);
                    }
                }
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                if (TouchDown) {
                    for (int i = 0; i < event.getPointerCount(); i++) {
                        if (event.getPointerId(i) == joyStickPointerID){
                            float xDiff = event.getX(i) - joyStickCenterPos.x;
                            float yDiff = event.getY(i) - joyStickCenterPos.y;
                            playing.setPlayerMoveTrue(new PointF(xDiff,yDiff));
                        }
                    }
                }
                break;
            }
            case MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP:{
                if (pointerID == joyStickPointerID){
                    resetVoidButton();
                } else{
                    if (isIn(eventPos, menuButton)) {
                        if (menuButton.isPushed(pointerID)) {
                            playing.setGameState();
                            resetVoidButton();
                        }
                    }
                }
                menuButton.onPush(pointerID);
                if (pointerID == attackButtonPointerID){
                    playing.setAttacking(false);
                    attackButtonPointerID = -1;
                }
                break;
            }
        }
    }

    private void resetVoidButton(){
        TouchDown = false;
        playing.setPlayerMoveFalse();
        joyStickPointerID = -1;
    }

    private boolean isIn(PointF eventPos, CustomButton b){
        return b.getHitbox().contains(eventPos.x,eventPos.y);
    }

}
