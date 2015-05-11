/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bontzel.neofighter.objects;

import com.bontzel.neofighter.framework.GameObject;
import com.bontzel.neofighter.framework.KeyInput;
import com.bontzel.neofighter.framework.ObjectId;
import com.bontzel.neofighter.framework.Texture;
import com.bontzel.neofighter.window.Animation;
import com.bontzel.neofighter.window.Game;
import com.bontzel.neofighter.window.Handler;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cosmin
 */
public class Mob extends GameObject {

    Handler handler;
    private float width = 32, height = 32;
    int type;
    private Animation walkUp, idleUp;
    private float[] path;
    private int delta = 1;
    private float ix;
    private float iy;
    private float fy;

    Texture tex = Game.getInstance();

    public Mob(float x, float y, Handler handler, int type, ObjectId id) {
        super(x, y, id);
        this.handler = handler;
        this.type = type;
        ix = x;
        iy = y;
        this.setHealth_points(4);

        fy = iy - 150;

        walkUp = new Animation(5, tex.mob1[1], tex.mob1[2], tex.mob1[1], tex.mob1[0]);
        idleUp = new Animation(5, tex.mob1[0]);

        EXP_upon_death = 3;
        attack_power = 2;

    }

    @Override
    public void tick(LinkedList<GameObject> object) {
        y += velY;
        x += velX;

       // Collision(object);
        
        float aux;

        //////////////////////// patrol path
        if (hurt == 0) {
            if (y > fy) {
                y -= 2;
            } else {
                y += 2;
            }
            if (y == fy) {
                aux = fy;
                fy = iy;
                iy = aux;
            }
        }else ;
            
       ///////////////////////// patrol path

        walkUp.runAnimation();
    }

    @Override
    public void render(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.red);
        g2d.draw(getBounds());

        Timer timer = new Timer();

        /*
         timer.schedule(new TimerTask() {
         public void run() {
         velY = 0;
         }
         }, 400);
         */
        walkUp.drawAnimation(g, (int) x - 8, (int) y - 14, 50, 50);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) width, (int) height);
    }

    private void Collision(LinkedList<GameObject> object) {

        for (int i = 0; i < handler.object.size(); i++) {
            final GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ObjectId.Player) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    if (tempObject.getAttacking() == 1) {
                        // velY = -10;
                        int delay = 300;

                        Timer timer = new Timer();

                        timer.schedule(new TimerTask() {
                            public void run() {
                                velY = 0;
                            }
                        }, delay);

                    }

                }
            }
        }

    }

    /*
     setPath();
     for (int i = 0; i < 8; i += 2) {
     while (x != path[i] && y != path[i + 1]) {
     if (x < path[i]) {
     velX = 2;
     } else if (x > path[i]) {
     velX = -2;
     } else if (y < path[i + 1]) {
     velY = 2;
     } else if (y > path[i + 1]) {
     velY = -2;
     }
     }
     velX = 0;
     velY = 0;
     }
     velX = 0;
     velY = 0;
     */

    /*
     private void setPath() {
     path = new float[10];
     path[0] = x + 10;
     path[1] = y;
     path[2] = x + 10;
     path[3] = y + 10;
     path[4] = x;
     path[5] = y + 10;
     path[6] = x;
     path[7] = y;

     }
     */
}
