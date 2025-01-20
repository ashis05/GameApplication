package com.example.gameapplication.maps.OutsideMap;

import android.graphics.Canvas;

import java.util.ArrayList;

public class BuildingManager {

    private ArrayList<Building> buildingArrayList;
    private float cameraX, cameraY;

    public BuildingManager() {

    }

    public void setCameraValues(float cameraX, float cameraY) {
        this.cameraX = cameraX;
        this.cameraY = cameraY;
    }

    public void drawBuilding(Canvas c) {
        for (Building b : buildingArrayList)
            c.drawBitmap(b.getBuildingType().getHouseImg(), b.getPos().x + cameraX, b.getPos().y + cameraY, null);
    }

    public ArrayList<Building> getBuildingArrayList() {
        return buildingArrayList;
    }
}