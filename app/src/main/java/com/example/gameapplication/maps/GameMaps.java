package com.example.gameapplication.maps;

import com.example.gameapplication.entities.enemies.Vampire;
import com.example.gameapplication.maps.OutsideMap.Building;
import com.example.gameapplication.maps.OutsideMap.Element;

import java.util.ArrayList;

public class GameMaps {
    private int[][] mapID;
    private Tiles tilesType;
    private ArrayList<Building> buildingArrayList;
    private ArrayList<Portal> portalArrayList;
    private ArrayList<Vampire> vampireArrayList;
    private ArrayList<Element> outsideElementArrayList = new ArrayList<>();
    private ArrayList<com.example.gameapplication.maps.Cave.Element> caveElementArrayList = new ArrayList<>();

    public GameMaps(int[][] mapID, Tiles tilesType, ArrayList<Building> buildingArrayList, ArrayList<Vampire> vampireArrayList, ArrayList<?> elementArrayList) {
        this.mapID = mapID;
        this.tilesType = tilesType;
        this.buildingArrayList = buildingArrayList;
        this.vampireArrayList = vampireArrayList;
        this.portalArrayList = new ArrayList<>();

        if (tilesType.equals(Tiles.CAVE)) {
            System.out.println("Cave Element");
            this.caveElementArrayList = (ArrayList<com.example.gameapplication.maps.Cave.Element>) elementArrayList;
        } else {
            System.out.println("Outside Element");
            this.outsideElementArrayList = (ArrayList<Element>) elementArrayList;
        }
        System.out.println(caveElementArrayList);
        System.out.println(outsideElementArrayList);
    }

    public GameMaps(int[][] mapID, Tiles tilesType, ArrayList<Building> buildingArrayList, ArrayList<Vampire> vampireArrayList){
        this.mapID = mapID;
        this.tilesType = tilesType;
        this.buildingArrayList = buildingArrayList;
        this.vampireArrayList = vampireArrayList;
        this.portalArrayList = new ArrayList<>();
    }

    public void addPortal(Portal portal) {
        this.portalArrayList.add(portal);
    }

    public ArrayList<Vampire> getVampireArrayList() {
        return vampireArrayList;
    }

    public ArrayList<Portal> getPortalArrayList() {
        return portalArrayList;
    }

    public ArrayList<Building> getBuildingArrayList() {
        return buildingArrayList;
    }

    public ArrayList<Element> getOutsideElementArrayList() {
        return outsideElementArrayList;
    }

    public ArrayList<com.example.gameapplication.maps.Cave.Element> getCaveElementArrayList() {
        return caveElementArrayList;
    }

    public Tiles getFloorType() {
        return tilesType;
    }
    public int getSpriteID(int indexX, int indexY){return mapID[indexY][indexX];}
    public int getWidth(){return mapID[0].length;}
    public int getHeight(){return mapID.length;}

}
