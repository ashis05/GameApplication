package com.example.gameapplication.maps;

import android.graphics.PointF;
import android.graphics.RectF;

import java.util.ArrayList;

public class Portal {
//    private ArrayList<RectF> hitbox = new ArrayList<>();
    private RectF hitbox;
    private boolean active = true;
    private final GameMaps gameMapLocatedIn;
    private Portal portalConnectedTo;

    @Override
    public String toString() {
        return "Portal{" +
                "hitbox=" + hitbox +
                ", active=" + active +
                ", gameMap=" + gameMapLocatedIn +
                '}';
    }

    public Portal(RectF portalHitbox, GameMaps gameMapLocatedIn) {
        this.hitbox = portalHitbox;
        this.gameMapLocatedIn = gameMapLocatedIn;
        gameMapLocatedIn.addPortal(this);
    }

    public boolean isPlayerInsidePortal(RectF playerHitbox, float cameraX, float cameraY) {
            return playerHitbox.intersects(hitbox.left + cameraX, hitbox.top + cameraY, hitbox.right + cameraX, hitbox.bottom + cameraY);
    }

    public void connectPortal(Portal destinationPortal) {
        this.portalConnectedTo = destinationPortal;
    }

    public Portal getPortalConnectedTo() {
        if (portalConnectedTo != null)
            return portalConnectedTo;
        return null;
    }

    public boolean isPortalActive() {
        return active;
    }

    public void setPortalActive(boolean active) {
        this.active = active;
    }

    public PointF getPosOfPortal() {
        return new PointF(hitbox.left, hitbox.top);
    }

    public GameMaps getGameMapLocatedIn() {
        return gameMapLocatedIn;
    }
}
