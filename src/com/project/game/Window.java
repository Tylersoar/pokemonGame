package com.project.game;

import javax.swing.JFrame;

public class Window extends JFrame{

    public Window() {
        setTitle("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new gamePanel(1280,720));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }
}
