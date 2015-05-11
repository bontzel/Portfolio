/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bontzel.neofighter.objects;

/**
 *
 * @author Cosmin
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.bontzel.neofighter.framework.GameObject;
import com.bontzel.neofighter.framework.ObjectId;
import com.bontzel.neofighter.framework.Texture;
import com.bontzel.neofighter.window.Game;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

/**
 *
 * @author Cosmin
 */
public class Block extends GameObject  {
    
    Texture tex = Game.getInstance();
    
    private int type;
    
    public Block(float x, float y, int type, ObjectId id) {
        super(x, y, id);
    }

    @Override
    public void tick(LinkedList<GameObject> object) {
    }

    @Override
    public void render(Graphics g) {
       if(type == 0)
           g.drawImage(tex.block[0], (int)x ,(int)y, null);
       if(type == 1)
           g.drawImage(tex.block[1], (int)x ,(int)y, null);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 32, 32);
    }
    
    
}

