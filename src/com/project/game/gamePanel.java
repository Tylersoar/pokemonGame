package com.project.game;

import com.project.game.states.GameStateManager;
import com.project.game.util.KeyHandler;
import com.project.game.util.MouseHandler;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.image.BufferedImage;

public class gamePanel extends JPanel implements Runnable {

    public static int width;
    public static int height;

    private Thread thread;
    private boolean running = false;

    private BufferedImage img;
    private Graphics2D g;

    private MouseHandler mouse;
    private KeyHandler key;

    private GameStateManager gsm;


    public gamePanel(int width, int height) {
        this.width = width;
        this.height = height;

        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();

    }

    public void addNotify() {
        super.addNotify();

        if (thread == null) {
            thread = new Thread(this, "GameThread");
            thread.start();

        }
    }

    public void init() {
        running = true;

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) img.getGraphics();

        mouse = new MouseHandler(this);
        key = new KeyHandler(this);

        gsm = new GameStateManager();

    }

    public void run() {
        init();

        final double GAME_HERTZ = 120.0;
        final double TBU = 1000000000 / 120.0; //Time Before Update

        final int MUBR = 5; //Must Update Before Render

        double lastUpdateTime = System.nanoTime();

        final double TARGET_FPS = 120;
        final double TIBR = 1000000000 / TARGET_FPS; // Total Time Before Render

        int frameCount = 0;
        int lastSecondTime = (int) (lastUpdateTime / 1000000000);
        int oldFrameCount = 0;

        while (running) {
            double now = System.nanoTime();
            int updateCount = 0;
            while (((now - lastUpdateTime) > TBU) && (updateCount < MUBR)) {
                update();
                input(mouse, key);
                lastUpdateTime += TBU;
                updateCount++;

            }
            if (now - lastUpdateTime > TBU){
                lastUpdateTime = now - TBU;
            }

            input(mouse, key);
            render();
            draw();
            lastUpdateTime = now;
            frameCount++;

            int thisSecond = (int) (lastUpdateTime / 1000000000);
            if (thisSecond > lastSecondTime){
                if(frameCount != oldFrameCount){
                    System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
                    oldFrameCount = frameCount;
                }
                frameCount = 0;
                lastSecondTime = thisSecond;
            }
            while (now - lastUpdateTime < TIBR && now - lastUpdateTime < TBU){
                Thread.yield();

                try{
                    Thread.sleep(1);
                }catch (Exception e){
                    System.out.println("ERROR: yielding thread");
                }
                now = System.nanoTime();
            }
        }

    }


    public void update() {
        gsm.update();


    }
    public void input(MouseHandler mouse, KeyHandler key){
        gsm.input(mouse, key);

    }

    public void render() {
        if (g != null) {
            g.setColor(new Color(78, 210, 250));
            g.fillRect(0, 0, width, height);
            gsm.render(g);
        }

    }

    public void draw() {
        Graphics g2 = (Graphics) this.getGraphics();
        g2.drawImage(img, 0, 0, width, height, null);
        g2.dispose();

    }

}


