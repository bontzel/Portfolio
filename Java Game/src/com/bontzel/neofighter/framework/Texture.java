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

import com.bontzel.neofighter.window.BufferedImageLoader;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cosmin
 */
public class Texture {

    SpriteSheet bs, ps, ms;
    private BufferedImage map_sheet = null;
    private BufferedImage player_sheet = null;
    private BufferedImage mob1_sheet = null;
    
    
    public BufferedImage[] tile = new BufferedImage[5];
    public BufferedImage[] player_movement = new BufferedImage[32];
    public BufferedImage[] block = new BufferedImage[0];
    public BufferedImage[] player_action =  new BufferedImage[32];
    public BufferedImage[] mob1 = new BufferedImage[10];
    
    

    public Texture() {

        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            map_sheet = loader.loadImage("/block_sheet.png");
            player_sheet = loader.loadImage("/player_sheet.png");
            mob1_sheet = loader.loadImage("/mob1_sheet.png");
        } catch (IOException ex) {
            Logger.getLogger(Texture.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
        ps = new SpriteSheet(player_sheet);
        bs = new SpriteSheet(map_sheet);
        ms = new SpriteSheet(mob1_sheet);
        
        getTextures();
    }
    
    private void getTextures() {
        tile[0] = bs.grabImage(1, 5, 16, 16); //simple grass floor
        tile[1] = bs.grabImage(4, 5, 16, 16); //pebble
        tile[3] = bs.grabImage(1, 2, 16, 16); //tall grass left edge
        tile[2] = bs.grabImage(3, 2, 16, 16); //tall grass middle
        tile[4] = bs.grabImage(5, 2, 16, 16); //tall grass right edge
        
        //player move up
        player_movement[0] = ps.grabImage(1, 1, 32, 32);
        player_movement[1] = ps.grabImage(2, 1, 32, 32); //idle up
        player_movement[2] = ps.grabImage(3, 1, 32, 32);
        player_movement[3] = ps.grabImage(4, 1, 32, 32);
        
        //player move NE
        player_movement[4] = ps.grabImage(5, 1, 32, 32);
        player_movement[5] = ps.grabImage(6, 1, 32, 32); //idle NE
        player_movement[6] = ps.grabImage(7, 1, 32, 32);
        player_movement[7] = ps.grabImage(8, 1, 32, 32);
        
        //player move right
        player_movement[8] = ps.grabImage(9, 1, 32, 32);
        player_movement[9] = ps.grabImage(10, 1, 32, 32); //idle right
        player_movement[10] = ps.grabImage(11, 1, 32, 32);
        player_movement[11] = ps.grabImage(12, 1, 32, 32);
        
        //player move SE
        
        player_movement[12] = ps.grabImage(13, 1, 32, 32);
        player_movement[13] = ps.grabImage(14, 1, 32, 32); 
        player_movement[14] = ps.grabImage(15, 1, 32, 32);
        player_movement[15] = ps.grabImage(16, 1, 32, 32);
        
        //player move Down
        
        player_movement[16] = ps.grabImage(1, 2, 32, 32);
        player_movement[17] = ps.grabImage(2, 2, 32, 32); //idle Down
        player_movement[18] = ps.grabImage(3, 2, 32, 32);
        player_movement[19] = ps.grabImage(4, 2, 32, 32);
        
        
        //player move SW
        
        player_movement[20] = ps.grabImage(5, 2, 32, 32);
        player_movement[21] = ps.grabImage(6, 2, 32, 32); //idle SW
        player_movement[22] = ps.grabImage(7, 2, 32, 32);
        player_movement[23] = ps.grabImage(8, 2, 32, 32);
        
        //player Walk Left
        
        player_movement[24] = ps.grabImage(9, 2, 32, 32);
        player_movement[25] = ps.grabImage(10, 2, 32, 32); //idle Left
        player_movement[26] = ps.grabImage(11, 2, 32, 32);
        player_movement[27] = ps.grabImage(12, 2, 32, 32);
        
        //player Walk NW
        
        player_movement[28] = ps.grabImage(13, 2, 32, 32);
        player_movement[29] = ps.grabImage(14, 2, 32, 32); 
        player_movement[30] = ps.grabImage(15, 2, 32, 32);
        player_movement[31] = ps.grabImage(16, 2, 32, 32);
        
        //player punch up
        
        player_action[0] = ps.grabImage(1, 3, 32, 32);
        player_action[1] = ps.grabImage(2, 3, 32, 32);
        
        //bunny walk up
        
        mob1[0] = ms.grabImage(1, 1, 34, 34);
        mob1[1] = ms.grabImage(2, 1, 34, 34);
        mob1[2] = ms.grabImage(3, 1, 34, 34);
        
        
        
    }
}

