package com.example.gameapplication.helper;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;

import com.example.gameapplication.entities.enemies.Vampire;
import com.example.gameapplication.maps.OutsideMap.Building;
import com.example.gameapplication.maps.GameMaps;
import com.example.gameapplication.maps.Portal;
import com.example.gameapplication.maps.Tiles;

import java.util.ArrayList;

public class HelpMethods {

    private static ArrayList<Integer> insideHouseCollisonList = new ArrayList<>();
    private static void addToInsideHouseCollisonList() {
        int[] collisions = {0, 1, 2, 3, 4, 5, 6,7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 23, 24, 28, 30, 31, 32, 35, 36, 37, 42, 43, 44, 47, 48, 63, 64, 65, 69, 70, 71, 72, 73, 74, 78, 79, 80, 81, 94, 95, 96, 99, 100, 101, 106, 107, 108, 111, 112, 127, 128, 129, 142, 143, 145, 146, 147, 148, 149, 150, 151, 153, 154, 155, 156, 157, 158, 159};
        for (int i : collisions){
            insideHouseCollisonList.add(i);
        }
    }

    public static void AddPortalToGameMap(GameMaps gameMapLocatedIn, GameMaps gameMapTarget, int buildingIndex) {
        float houseX = gameMapLocatedIn.getBuildingArrayList().get(buildingIndex).getPos().x;
        float houseY = gameMapLocatedIn.getBuildingArrayList().get(buildingIndex).getPos().y;
//        System.out.println(houseX+"x");
//        System.out.println(houseY+"y");
        RectF hitbox = gameMapLocatedIn.getBuildingArrayList().get(buildingIndex).getBuildingType().getPortalHitbox();
//        System.out.println(hitbox + "portal hitbox");
        RectF tempRect = new RectF (hitbox.left + houseX, hitbox.top + houseY, hitbox.right + houseX, hitbox.bottom + houseY);
//        System.out.println(tempRect);
        Portal portal = new Portal(tempRect, gameMapTarget);
//        System.out.println(buildingIndex + "index" + gameMapLocatedIn.getBuildingArrayList().get(buildingIndex).getBuildingType());
        gameMapLocatedIn.addPortal(portal);
    }

    public static RectF CreateHitboxForDoorway(GameMaps gameMapLocatedIn, int buildingIndex) {
        Building building = gameMapLocatedIn.getBuildingArrayList().get(buildingIndex);

        float x = building.getPos().x;
        float y = building.getPos().y;
        RectF hitbox = gameMapLocatedIn.getBuildingArrayList().get(buildingIndex).getBuildingType().getPortalHitbox();
        System.out.println(hitbox + "portal hitbox");
        return new RectF(hitbox.left + x, hitbox.top + y, hitbox.right + x, hitbox.bottom + y);

    }

    public static RectF CreateHitboxForDoorway(int xTile, int yTile) {
        float x = xTile * GameConstant.Maps.SIZE;
        float y = yTile * GameConstant.Maps.SIZE;
        System.out.println(new RectF(x, y, x + GameConstant.Maps.SIZE, y + GameConstant.Maps.SIZE));
        return new RectF(x, y, x + GameConstant.Maps.SIZE, y + GameConstant.Maps.SIZE);

    }

    public static void ConnectTwoDoorways(GameMaps gameMapOne, RectF hitboxOne, GameMaps gameMapTwo, RectF hitboxTwo) {

        Portal portalOne = new Portal(hitboxOne, gameMapOne);
        Portal portalTwo = new Portal(hitboxTwo, gameMapTwo);

        portalOne.connectPortal(portalTwo);
        portalTwo.connectPortal(portalOne);
    }

    public static ArrayList<Vampire> GetVampireRandomized(int amt, int[][] gameMapArray){
        int width = (gameMapArray[0].length - 1) * GameConstant.Maps.SIZE;
        int height = (gameMapArray.length - 1) * GameConstant.Maps.SIZE;

        ArrayList<Vampire> vampireArrayList = new ArrayList<>();
        for (int i = 0; i < amt; i++){
            float x = (float)(Math.random()) * width;
            float y = (float)(Math.random()) * height;
            vampireArrayList.add(new Vampire(new PointF(x,y)));
        }
        return vampireArrayList;
    }

    public static boolean canMoveHere(float x, float y, GameMaps gameMaps){
        addToInsideHouseCollisonList();
        if(x < 0 || y < 0){return false;}

        if(x >= (gameMaps.getWidth() * GameConstant.Maps.SIZE) || y >= (gameMaps.getHeight() * GameConstant.Maps.SIZE)){return false;}

        int tileX = (int)(x / GameConstant.Maps.SIZE);
        int tileY = (int)(y / GameConstant.Maps.SIZE);

        int tileID = gameMaps.getSpriteID(tileX, tileY);

        return isTileWalkable(tileID, gameMaps.getFloorType());
    }

    public static boolean CanWalkHere(RectF hitbox, float deltaX, float deltaY, GameMaps gameMap) {
        addToInsideHouseCollisonList();
        if (hitbox.left + deltaX < 0 || hitbox.top + deltaY < 0)
            return false;
        else if (hitbox.right + deltaX >= gameMap.getWidth() * GameConstant.Maps.SIZE)
            return false;
        else if (hitbox.bottom + deltaY >= gameMap.getHeight() * GameConstant.Maps.SIZE)
            return false;

        Point[] tileCords = GetTileCords(hitbox, deltaX, deltaY);
        int[] tileIds = GetTileIds(tileCords, gameMap);

        return tileLoop(tileIds, gameMap.getFloorType());
    }

    private static boolean tileLoop(int[] tileIds, Tiles floorType) {
        for (int i : tileIds){
            if (!(isTileWalkable(i, floorType))){
                return false;
            }
        }
        return true;
    }

    private static Point[] GetTileCords(RectF hitbox, float deltaX, float deltaY) {
        Point[] tileCords = new Point[4];

        int left = (int) ((hitbox.left + deltaX) / GameConstant.Maps.SIZE);
        int right = (int) ((hitbox.right + deltaX) / GameConstant.Maps.SIZE);
        int top = (int) ((hitbox.top + deltaY) / GameConstant.Maps.SIZE);
        int bottom = (int) ((hitbox.bottom + deltaY) / GameConstant.Maps.SIZE);

        tileCords[0] = new Point(left, top);
        tileCords[1] = new Point(right, top);
        tileCords[2] = new Point(left, bottom);
        tileCords[3] = new Point(right, bottom);

        return tileCords;

    }

    private static int[] GetTileIds(Point[] tileCords, GameMaps gameMap) {
        int[] tileIds = new int[4];

        for (int i = 0; i < tileCords.length; i++)
            tileIds[i] = gameMap.getSpriteID(tileCords[i].x, tileCords[i].y);

        return tileIds;
    }

    public static boolean isTileWalkable(int tileID, Tiles tileType){
        if(tileType == Tiles.INSIDE_RESTAURANT) {
//            System.out.println("Function called");
            return checkForCollsion(tileID);
        } // Need to create an array of collision blocks in my png
        return true;

    }

    public static boolean checkForCollsion(int tileID) {
        for (int i : insideHouseCollisonList) {
//            System.out.println(i);
            if (i == tileID) {
//                System.out.println("False");
                return false; // Tile is not walkable
            }
        }
        return true; // Tile is walkable
    }

}
