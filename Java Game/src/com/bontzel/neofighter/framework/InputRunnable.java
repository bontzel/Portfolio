/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.bontzel.neofighter.framework;

import com.bontzel.neofighter.window.Handler;
import java.awt.Canvas;

/**
 *
 * @author Cosmin
 */
public class InputRunnable implements Runnable {

    Canvas game;
    Handler handler;
    
    public InputRunnable(Canvas game, Handler handler) {
        this.game = game;
        this.handler = handler;
    }
    
    public synchronized void start() {
        Thread thread = new Thread(this);
        thread.start();
    }
    
    @Override
    public void run() {
        
        game.addKeyListener(new KeyInput(handler));
    }
    
    
    
}
