package com.example.gameapplication.maps;

import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;

import com.example.gameapplication.gamestates.Playing;
import com.example.gameapplication.helper.GameConstant;
import com.example.gameapplication.helper.HelpMethods;
import com.example.gameapplication.main.MainActivity;
import com.example.gameapplication.maps.OutsideMap.Building;
import com.example.gameapplication.maps.OutsideMap.Buildings;
import com.example.gameapplication.maps.OutsideMap.Element;
import com.example.gameapplication.maps.OutsideMap.Elements;

import java.util.ArrayList;

public class MapManager {
    GameMaps currentMap, insideMap, outsideMap, caveMap;
    private float cameraX, cameraY;
    private Playing playing;

    public MapManager(Playing playing){
        this.playing = playing;
        initTestMap();
    }

    public void setCameraValues(float cameraX, float cameraY){
        this.cameraX = cameraX;
        this.cameraY = cameraY;

    }

    public void drawMap(Canvas c){
        for (int i = 0; i < currentMap.getHeight(); i++){
            for (int j = 0; j < currentMap.getWidth(); j++){
                c.drawBitmap(currentMap.getFloorType().getTile(currentMap.getSpriteID(j,i)), j * GameConstant.Maps.SIZE + cameraX, i * GameConstant.Maps.SIZE + cameraY, null);
            }
        }
    }

    public void drawBuildings(Canvas c) {
        if (currentMap.getBuildingArrayList() != null)
            for (Building b : currentMap.getBuildingArrayList())
                c.drawBitmap(b.getBuildingType().getHouseImg(), b.getPos().x + cameraX, b.getPos().y + cameraY, null);
    }

    public void drawOutsideElements(Canvas c){
        if (currentMap.getOutsideElementArrayList() != null){
            int count = 0;
            System.out.println("Outside Element fn");
            for(Element e : currentMap.getOutsideElementArrayList()){
                System.out.println(e + " " + count);
                count++;
                c.drawBitmap(e.getElementType().getHouseImg(), e.getPos().x + cameraX, e.getPos().y + cameraY, null);
            }
        }
        else {
            System.out.println("Null Outside Array");
        }
    }
    public void drawCaveElements(Canvas c) {
        if (currentMap.getCaveElementArrayList() != null){
            System.out.println("Cave Element fn");
            for (com.example.gameapplication.maps.Cave.Element a : currentMap.getCaveElementArrayList()){
                c.drawBitmap(a.getElementType().getHouseImg(), a.getPos().x + cameraX, a.getPos().y + cameraY, null);
            }
        }
        else {
            System.out.println("Null Cave Array");
        }
    }


     //c.drawBitmap(Element.getElementType().getHouseImg(), Element.getPos().x + cameraX, Element.getPos().y + cameraY, null);

    public Portal isPlayerOnPortal(RectF playerHitbox) {
//        System.out.println("player:" + (currentMap.getPortalArrayList()).size());
        for (Portal portal : currentMap.getPortalArrayList()) {
//            System.out.println(portal.toString());
//            System.out.println(portal.isPlayerInsidePortal(playerHitbox, cameraX, cameraY));
            if (portal.isPlayerInsidePortal(playerHitbox, cameraX, cameraY))
                return portal;
        }
        return null;
    }

    public void changeMap(Portal portalTarget) {
        this.currentMap = portalTarget.getGameMapLocatedIn();

        float cX = MainActivity.PhoneWidth / 2 - portalTarget.getPosOfPortal().x;
        float cY = MainActivity.PhoneHeight / 2 - portalTarget.getPosOfPortal().y;

        playing.setCameraValues(new PointF(cX, cY));
        cameraX = cX;
        cameraY = cY;

        playing.setPortalJustPassed(true);
    }



    public boolean canMoveHere(float x, float y){
        if(x < 0 || y < 0){return false;}

        if(x >= getMaxWidth() || y >= getMaxHeight()){return false;}

        return true;
    }

    public GameMaps getCurrentMap() {
        return currentMap;
    }

    public GameMaps getInsideMap() {
        return insideMap;
    }

    public GameMaps getOutsideMap() {
        return outsideMap;
    }

    public GameMaps getCaveMap() {
        return caveMap;
    }

    public int getMaxWidth(){
        return currentMap.getWidth() * GameConstant.Maps.SIZE;
    }
    public int getMaxHeight(){
        return currentMap.getHeight() * GameConstant.Maps.SIZE;
    }

    private void initTestMap() {
        ArrayList<Building> buildingArrayList = new ArrayList<>();
        addToBuildingArray(buildingArrayList);
        ArrayList<Element> outsideElementArrayList = new ArrayList<>();
        addToOutsideElementArray(outsideElementArrayList);
        ArrayList<com.example.gameapplication.maps.Cave.Element> caveElementArrayList = new ArrayList<>();
        addToCaveElementArray(caveElementArrayList);

        insideMap = new GameMaps(mapArray(Tiles.INSIDE_RESTAURANT), Tiles.INSIDE_RESTAURANT, null, HelpMethods.GetVampireRandomized(0,(mapArray(Tiles.INSIDE_RESTAURANT))));
        outsideMap = new GameMaps(mapArray(Tiles.OUTSIDE), Tiles.OUTSIDE, buildingArrayList, HelpMethods.GetVampireRandomized(5,(mapArray(Tiles.OUTSIDE))), outsideElementArrayList);
        caveMap = new GameMaps(mapArray(Tiles.CAVE), Tiles.CAVE, null, HelpMethods.GetVampireRandomized(0, (mapArray(Tiles.CAVE))), caveElementArrayList);
        currentMap = outsideMap;

        HelpMethods.ConnectTwoDoorways(
                outsideMap,
                HelpMethods.CreateHitboxForDoorway(outsideMap, 4),
                insideMap,
                HelpMethods.CreateHitboxForDoorway(8, 9));
        HelpMethods.ConnectTwoDoorways(
                outsideMap,
                HelpMethods.CreateHitboxForDoorway(outsideMap, 5),
                caveMap,
                HelpMethods.CreateHitboxForDoorway(4, 14));
    }

    private void addToCaveElementArray(ArrayList<com.example.gameapplication.maps.Cave.Element> caveElementArrayList) {
        caveElementArrayList.add(new com.example.gameapplication.maps.Cave.Element(new PointF(32 * GameConstant.Maps.SIZE, 3 * GameConstant.Maps.SIZE), com.example.gameapplication.maps.Cave.Elements.RANDOM));
        caveElementArrayList.add(new com.example.gameapplication.maps.Cave.Element(new PointF(32 * GameConstant.Maps.SIZE,3 * GameConstant.Maps.SIZE), com.example.gameapplication.maps.Cave.Elements.SHORT_POLE));
        caveElementArrayList.add(new com.example.gameapplication.maps.Cave.Element(new PointF(32 * GameConstant.Maps.SIZE,6 * GameConstant.Maps.SIZE), com.example.gameapplication.maps.Cave.Elements.SHORT_POLE));
        caveElementArrayList.add(new com.example.gameapplication.maps.Cave.Element(new PointF(46 * GameConstant.Maps.SIZE,2 * GameConstant.Maps.SIZE), com.example.gameapplication.maps.Cave.Elements.CRYSTAL_FIGURE));
        caveElementArrayList.add(new com.example.gameapplication.maps.Cave.Element(new PointF(48 * GameConstant.Maps.SIZE,2 * GameConstant.Maps.SIZE), com.example.gameapplication.maps.Cave.Elements.FIGURE));
        caveElementArrayList.add(new com.example.gameapplication.maps.Cave.Element(new PointF(46 * GameConstant.Maps.SIZE,6 * GameConstant.Maps.SIZE), com.example.gameapplication.maps.Cave.Elements.FROG));
        caveElementArrayList.add(new com.example.gameapplication.maps.Cave.Element(new PointF(32 * GameConstant.Maps.SIZE,3 * GameConstant.Maps.SIZE), com.example.gameapplication.maps.Cave.Elements.LONG_POLE));
        caveElementArrayList.add(new com.example.gameapplication.maps.Cave.Element(new PointF(57 * GameConstant.Maps.SIZE,3 * GameConstant.Maps.SIZE), com.example.gameapplication.maps.Cave.Elements.FLAG));
        caveElementArrayList.add(new com.example.gameapplication.maps.Cave.Element(new PointF(32 * GameConstant.Maps.SIZE,3 * GameConstant.Maps.SIZE), com.example.gameapplication.maps.Cave.Elements.LONG_POLE));

    }

    private void addToOutsideElementArray(ArrayList<Element> elementArrayList) {
        elementArrayList.add(new Element(new PointF(10 * GameConstant.Maps.SIZE,4 * GameConstant.Maps.SIZE), Elements.LIGHT_POLE));
        elementArrayList.add(new Element(new PointF(11 * GameConstant.Maps.SIZE,4 * GameConstant.Maps.SIZE), Elements.HANGER_LEFT));
        elementArrayList.add(new Element(new PointF(14 * GameConstant.Maps.SIZE,4 * GameConstant.Maps.SIZE), Elements.HANGER_RIGHT));
        elementArrayList.add(new Element(new PointF(12 * GameConstant.Maps.SIZE,4 * GameConstant.Maps.SIZE), Elements.HANGER_CLOTHES));
        elementArrayList.add(new Element(new PointF(15 * GameConstant.Maps.SIZE,4 * GameConstant.Maps.SIZE), Elements.LIGHT_POLE));
        elementArrayList.add(new Element(new PointF(19 * GameConstant.Maps.SIZE,4 * GameConstant.Maps.SIZE), Elements.LIGHT_POLE));
        elementArrayList.add(new Element(new PointF(20 * GameConstant.Maps.SIZE,5 * GameConstant.Maps.SIZE), Elements.BOARD_SIGN));
        elementArrayList.add(new Element(new PointF(21 * GameConstant.Maps.SIZE,5 * GameConstant.Maps.SIZE), Elements.RIP));
        elementArrayList.add(new Element(new PointF(10 * GameConstant.Maps.SIZE,4 * GameConstant.Maps.SIZE), Elements.LIGHT_POLE));
        elementArrayList.add(new Element(new PointF(22 * GameConstant.Maps.SIZE,4 * GameConstant.Maps.SIZE), Elements.EMPTY_CART));
        elementArrayList.add(new Element(new PointF(23 * GameConstant.Maps.SIZE,12 * GameConstant.Maps.SIZE), Elements.LIGHT_POLE));
        elementArrayList.add(new Element(new PointF(28 * GameConstant.Maps.SIZE,12 * GameConstant.Maps.SIZE), Elements.LIGHT_POLE));
        elementArrayList.add(new Element(new PointF(28 * GameConstant.Maps.SIZE,10 * GameConstant.Maps.SIZE), Elements.FULL_CART));
        elementArrayList.add(new Element(new PointF(30 * GameConstant.Maps.SIZE,10 * GameConstant.Maps.SIZE), Elements.WELL));
        elementArrayList.add(new Element(new PointF(29 * GameConstant.Maps.SIZE,12 * GameConstant.Maps.SIZE), Elements.BOXES));
    }

    private int[][] mapArray(Tiles gameMaps){
        int[][] mapID = new int[gameMaps.getTileHeight()][gameMaps.getTileWidth()];
        int count =0;
        for(int i = 0; i < gameMaps.getTileHeight(); i++){
            for(int j = 0; j < gameMaps.getTileWidth(); j++){
                mapID[i][j] = count;
                count++;
            }
        }
        return mapID;
    }

    private void addToBuildingArray(ArrayList<Building> buildingArrayList){
        buildingArrayList.add(new Building(new PointF(6 * GameConstant.Maps.SIZE, 1 * GameConstant.Maps.SIZE), Buildings.HOUSE_ONE));
        buildingArrayList.add(new Building(new PointF(14 * GameConstant.Maps.SIZE, 8 * GameConstant.Maps.SIZE), Buildings.HOUSE_ONE));
        buildingArrayList.add(new Building(new PointF(24 * GameConstant.Maps.SIZE, 5 * GameConstant.Maps.SIZE), Buildings.HOUSE_ONE));
        buildingArrayList.add(new Building(new PointF(16 * GameConstant.Maps.SIZE, 1 * GameConstant.Maps.SIZE), Buildings.HOUSE_TWO));
        buildingArrayList.add(new Building(new PointF(2 * GameConstant.Maps.SIZE, 7 * GameConstant.Maps.SIZE), Buildings.HOUSE_THREE));
        buildingArrayList.add(new Building(new PointF(27 * GameConstant.Maps.SIZE,23 * GameConstant.Maps.SIZE), Buildings.CAVE));
    }
}

