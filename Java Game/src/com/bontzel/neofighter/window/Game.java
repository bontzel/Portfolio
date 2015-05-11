/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bontzel.neofighter.window;

import com.bontzel.neofighter.framework.GameObject;
import com.bontzel.neofighter.framework.KeyInput;
import com.bontzel.neofighter.framework.ObjectId;
import com.bontzel.neofighter.framework.Texture;
import com.bontzel.neofighter.objects.Mob;
import com.bontzel.neofighter.objects.Player;
import com.bontzel.neofighter.objects.Tile;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cosmin
 */
public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = -3221243551435L;

    private boolean running = false;
    private Thread thread;

    public static int WIDTH, HEIGHT;

    private BufferedImage level = null;

    Handler handler;

    Camera cam;

    static Texture tex;

    public synchronized void start() {
        if (running) {
            return;
        }

        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void init() {

        WIDTH = getWidth();
        HEIGHT = getHeight();

        tex = new Texture();

        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            level = loader.loadImage("/level_sheet.png");
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }

        handler = new Handler();

        cam = new Camera(0, 0);

        LoadImageLevel(level);

       //handler.addObject(new Player(100, 100, handler, ObjectId.Player));
        //handler.createLevel();
        // this.addKeyListener(new KeyInput(handler));
        // InputRunnable ip = new InputRunnable(this, handler);
        // ip.start();
        this.addKeyListener(new KeyInput(handler));

    }

    @Override
    public void run() {

        init();
        this.requestFocus();

        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }

    }

    private void tick() {
        handler.tick();
        for (int i = 0; i < handler.object.size(); i++) {
            if (handler.object.get(i).getId() == ObjectId.Player) {
                cam.tick(handler.object.get(i));
            }
        }
        ;
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;
        ///////////////////////////////////////

        //Drah here bro !
        g.setColor(Color.BLACK);                      //
        g.fillRect(0, 0, getWidth(), getHeight());    //background 

        g2d.translate(cam.getX(), cam.getY());    // cam stuff start

        handler.render(g);

        g2d.translate(-cam.getX(), -cam.getY()); // cam stuff end

        ///////////////////////////////////////
        /*
         for(int i = 0; i < handler.object.size (); i++) {
         GameObject tempObject = handler.object.get(i);
         if(tempObject.getId() == ObjectId.Mob)
         tempObject.patrol();
         }
         */
        g.dispose();
        bs.show();
    }

    private void LoadImageLevel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        for (int xx = 0; xx < h; xx++) {
            for (int yy = 0; yy < w; yy++) {
                int pixel = image.getRGB(xx, yy);

                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;

                //blocks read from image
                if (red == 0 && green == 255 && blue == 0) {
                    handler.addObject(new Tile(xx * 32, yy * 32, 0, ObjectId.Tile));
                }
                if (red == 200 && green == 255 && blue == 200) {
                    handler.addObject(new Tile(xx * 32, yy * 32, 1, ObjectId.Tile));
                }
                if (red == 0 && green == 0 && blue == 255) {
                    handler.addObject(new Tile(xx * 32, yy * 32, 0, ObjectId.Tile));
                }
                if (red == 0 && green == 164 && blue == 0) {
                    handler.addObject(new Tile(xx * 32, yy * 32, 2, ObjectId.Tile));
                }
                if (red == 255 && green == 0 && blue == 0) {
                    handler.addObject(new Tile(xx * 32, yy * 32, 0, ObjectId.Tile));
                    handler.addObject(new Mob(xx * 32, yy * 32, this.handler, 0, ObjectId.Mob));
                }
            }
        }
        handler.addObject(new Player(60, 60, this.handler, ObjectId.Player));
    }

    public static Texture getInstance() {
        return tex;
    }

    public static void main(String args[]) {
        new Window(800, 600, "NeoFighter", new Game());
    }
}
