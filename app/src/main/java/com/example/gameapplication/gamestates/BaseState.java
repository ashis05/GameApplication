package com.example.gameapplication.gamestates;

import com.example.gameapplication.main.Game;

public abstract class BaseState {

    protected Game game;

    public BaseState(Game game){
        this.game = game;
    }

    public Game getGame(){
        return game;
    }
}
