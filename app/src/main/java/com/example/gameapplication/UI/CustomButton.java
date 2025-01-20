package com.example.gameapplication.UI;

import android.graphics.RectF;

public class CustomButton {

    private final RectF hitbox;
    private boolean pushed;
    private int pointerID = -1;

    public CustomButton(float x, float y, float width, float height) {
        hitbox = new RectF(x,y,x + width,y + height);
    }

    public void setPushed(boolean pushed, int pointerID) {
        if (this.pushed)
            return;
        this.pushed = pushed;
        this.pointerID = pointerID;
    }

    public void setPushed(boolean pushed) {
        this.pushed = pushed;
    }

    public void onPush(int pointerID){
        if (this.pointerID != pointerID)
            return;
        this.pointerID = -1;
        this.pushed = false;
    }

    public boolean isPushed(int pointerID) {
        if (this.pointerID != pointerID)
            return false;
        return pushed;
    }

    public boolean isPushed() {
        return pushed;
    }

    public RectF getHitbox() {
        return hitbox;
    }

    public int getPointerID() {
        return pointerID;
    }
}
