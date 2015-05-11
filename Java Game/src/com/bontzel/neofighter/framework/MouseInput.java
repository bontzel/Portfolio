/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bontzel.neofighter.framework;

import com.bontzel.neofighter.window.Handler;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Cosmin
 */
public class MouseInput extends MouseAdapter {

    Handler handler;

    public MouseInput(Handler handler) {
        this.handler = handler;
    }

    /*
    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.id == ObjectId.Player) {
                float X = tempObject.getX();
                float Y = tempObject.getY();

                if (mx > tempObject.getX()) {
                    tempObject.setVelX(2);
                } else if (mx < tempObject.getX()) {
                    tempObject.setVelX(-2);
                } else if (my > tempObject.getY()) {
                    tempObject.setVelY(2);
                } else if (my < tempObject.getY()) {
                    tempObject.setVelY(-2);
                } else if (my > Y && mx > X) {
                    tempObject.setVelX(2);
                    tempObject.setVelY(2);
                } else if (my < Y && mx < X) {
                    tempObject.setVelX(-2);
                    tempObject.setVelY(-2);
                } else if (my < Y && mx > X) {
                    tempObject.setVelY(-2);
                    tempObject.setVelX(2);
                } else if (my > Y && mx < X) {
                    tempObject.setVelX(-2);
                    tempObject.setVelY(2);
                }

            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.id == ObjectId.Player) {

                if (mx == tempObject.getX()) {
                    tempObject.setVelX(0);
                }
                // if(mx < tempObject.getX())
                // tempObject.setVelX(-2);
                if (my == tempObject.getY()) {
                    tempObject.setVelY(0);
                }
                // if(my < tempObject.getY())
                //tempObject.setVelY(-2);
            }
        }
    }
    */
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        int[] next = {0,0};
        int[] prev = {0,0};
        
          for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.id == ObjectId.Player) {
                
                int X = (int)tempObject.getX();
                int Y = (int)tempObject.getY();
                next[0] = X;
                next[1] = Y;
                prev[0] = X;
                prev[1] = Y;
                
               // System.out.println("here!");
           
                while(next[0] != mx && next[1] != my) {
                    next = getNextLinePoint(next[0], next[1], mx, my);
                    if(next[0] - prev[0] > 0) tempObject.setVelX((float)1); else  tempObject.setVelX((float)-1);
                    if(next[1] - prev[1] > 0) tempObject.setVelY((float)1); else  tempObject.setVelY((float)-1);
                   // tempObject.setVelX();
                    //tempObject.setVelY();
                    prev = next;
                }
                 tempObject.setVelX(0);
                tempObject.setVelY(0);
                    
               // System.out.println("got out!");
            }
          }
        
        
    }

    public int[] getNextLinePoint(int x, int y, int x2, int y2) {
        int w = x2 - x;
        int h = y2 - y;
        int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
        if (w < 0) {
            dx1 = -1;
        } else if (w > 0) {
            dx1 = 1;
        }
        if (h < 0) {
            dy1 = -1;
        } else if (h > 0) {
            dy1 = 1;
        }
        if (w < 0) {
            dx2 = -1;
        } else if (w > 0) {
            dx2 = 1;
        }
        int longest = Math.abs(w);
        int shortest = Math.abs(h);
        if (!(longest > shortest)) {
            longest = Math.abs(h);
            shortest = Math.abs(w);
            if (h < 0) {
                dy2 = -1;
            } else if (h > 0) {
                dy2 = 1;
            }
            dx2 = 0;
        }
        int numerator = longest >> 1;
        numerator += shortest;
        if (!(numerator < longest)) {
            numerator -= longest;
            x += dx1;
            y += dy1;
        } else {
            x += dx2;
            y += dy2;
        }
        int[] res = {x, y};
        return res;
    }

    
    
}
