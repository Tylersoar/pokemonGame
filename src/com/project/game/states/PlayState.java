package com.project.game.states;

import com.project.game.util.KeyHandler;
import com.project.game.util.MouseHandler;

import java.awt.*;

public class PlayState extends GameStates{

    public PlayState(GameStateManager gsm) {
        super(gsm);
    }

    public void update(){

    }
    public void input(MouseHandler mouse, KeyHandler key){
        if (key.up.down){
            System.out.println("w is being pressed");
        }

    }
    public void render(Graphics2D g){
        g.setColor(Color.orange);
        g.fillRect(100,100,64,64);
    }

}
