/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bontzel.neofighter.framework;

/**
 *
 * @author Cosmin
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.image.BufferedImage;

/**
 *
 * @author Cosmin
 */
public class SpriteSheet {
    private BufferedImage image;
    
    public SpriteSheet( BufferedImage image) {
        this.image = image;
    }
    
    public BufferedImage grabImage( int col, int row, int width, int height) { 
        BufferedImage img = image.getSubimage((col * width) - width, (row * height) - height, width, height);
        return img;
    }
    
    public BufferedImage grabImageCoord(int x, int y, int width, int height) {
        BufferedImage img = image.getSubimage(x, y, width, height);
        return img;
    }
}
