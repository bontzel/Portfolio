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
public class Tile extends GameObject {

    Texture tex = Game.getInstance();

    private int type;

    public Tile(float x, float y, int type, ObjectId id) {
        super(x, y, id);
        this.type = type;
    }

    @Override
    public void tick(LinkedList<GameObject> object) {
    }

    @Override
    public void render(Graphics g) {

        switch (type) {
            case 0:
                g.drawImage(tex.tile[0], (int) x, (int) y, 32, 32, null);
                break;
            case 1:
                g.drawImage(tex.tile[1], (int) x, (int) y, 32, 32, null);
                break;
            case 2:
                g.drawImage(tex.tile[2], (int) x, (int) y, 32, 32, null);
                break;
        }
      //System.out.println(type);

    }

    @Override
    public Rectangle getBounds() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
