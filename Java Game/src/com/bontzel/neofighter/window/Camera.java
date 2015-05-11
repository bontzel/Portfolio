/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bontzel.neofighter.window;
import com.bontzel.neofighter.framework.GameObject;

/**
 *
 * @author Cosmin
 */
public class Camera {
    
    private float x;
    private float y;
    
    public Camera(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
    
    public void tick(GameObject player) {
      
        x = -player.getX() + Game.WIDTH/2;
   
        y = -player.getY() + Game.HEIGHT/2;
    }
    
    
}

