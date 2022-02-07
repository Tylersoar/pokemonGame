package com.project.game.states;

import com.project.game.util.KeyHandler;
import com.project.game.util.MouseHandler;

import java.awt.Graphics2D;

public abstract class GameStates {

    private GameStateManager gsm;

    public GameStates(GameStateManager gsm){
        this.gsm = gsm;
    }

    public abstract void update();
    public abstract void input(MouseHandler mouse, KeyHandler key);
    public abstract void render (Graphics2D g);
}

