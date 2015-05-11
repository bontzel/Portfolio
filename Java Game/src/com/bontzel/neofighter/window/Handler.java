/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bontzel.neofighter.window;

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
import com.bontzel.neofighter.objects.Block;
import java.awt.Graphics;
import java.util.LinkedList;

/**
 *
 * @author Cosmin
 */
public class Handler {
    
    public LinkedList<GameObject> object = new LinkedList();
    
    private GameObject tempObject;
    
    public void tick() {
        for(int i = 0; i < object.size(); i++) {
            tempObject = object.get(i);
            
            tempObject.tick(object);
        }
    }
    
    public void render(Graphics g) {
        for(int i = 0; i < object.size(); i++) {
            tempObject = object.get(i);
            
            tempObject.render(g);
        }
    }
    
    public void addObject(GameObject object) {
        this.object.add(object);
    }
    
    public void removeObject(GameObject object) {
        this.object.remove(object);
    }
    
    /*
    public void createLevel() {
        for(int xx = 0; xx < Game.WIDTH*2; xx += 32)
            addObject(new Block(xx, Game.HEIGHT - 32, ObjectId.Block ));
        //for(int xx = 0; xx < Game.HEIGHT + 32; xx += 32)
            //addObject(new Block(Game.WIDTH - 32, xx, ObjectId.Block ));
       for(int xx = 0; xx < Game.HEIGHT + 32; xx += 32)
            addObject(new Block(0, xx, ObjectId.Block ));
        for(int xx = 128; xx < Game.WIDTH - 128; xx += 32)
            addObject(new Block(xx, Game.HEIGHT - 256, ObjectId.Block ));
        
    }
    */
    
}

