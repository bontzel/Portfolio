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
import com.bontzel.neofighter.window.Handler;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;
import static java.awt.event.KeyEvent.VK_Z;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Cosmin
 */
public class KeyInput extends KeyAdapter {

    Handler handler;
    private Set<Integer> pressed = new HashSet<Integer>();

    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    public synchronized void keyPressed(KeyEvent e) {

        pressed.add(e.getKeyCode());
        int currentKey = 0;

        for (int i = 0; i < handler.object.size(); i++) {
            final GameObject tempObject = handler.object.get(i);

            if (tempObject.id == ObjectId.Player) {

                if (pressed.size() == 2) {
                    Iterator<Integer> it = pressed.iterator();
                    currentKey = it.next();
                    currentKey = currentKey ^ it.next();
                } else if (pressed.size() == 1) {
                    Iterator<Integer> it = pressed.iterator();
                    currentKey = it.next();
                }

                if (currentKey == (VK_DOWN ^ VK_LEFT)) {
                    if (tempObject.getAttacking() == 0) {
                        tempObject.setVelY(4);
                        tempObject.setVelX(-4);
                        tempObject.setFacing(1);
                    }
                } else if (currentKey == (VK_UP ^ VK_LEFT)) {
                    if (tempObject.getAttacking() == 0) {
                        tempObject.setVelY(-4);
                        tempObject.setVelX(-4);
                        tempObject.setFacing(3);
                    }
                } else if (currentKey == (VK_UP ^ VK_RIGHT)) {
                    if (tempObject.getAttacking() == 0) {
                        tempObject.setVelY(-4);
                        tempObject.setVelX(4);
                        tempObject.setFacing(5);
                    }
                } else if (currentKey == (VK_RIGHT ^ VK_DOWN)) {
                    if (tempObject.getAttacking() == 0) {
                        tempObject.setVelY(4);
                        tempObject.setVelX(4);
                        tempObject.setFacing(7);
                    }
                }

                if (currentKey == VK_RIGHT) {
                    if (tempObject.getAttacking() == 0) {
                        tempObject.setVelX(4);
                        tempObject.setFacing(6);
                    }
                } else if (currentKey == VK_LEFT) {
                    if (tempObject.getAttacking() == 0) {
                        tempObject.setVelX(-4);
                        tempObject.setFacing(2);
                    }
                } else if (currentKey == VK_UP) {
                    if (tempObject.getAttacking() == 0) {
                        tempObject.setFacing(4);
                        tempObject.setVelY(-4);
                    }
                } else if (currentKey == VK_DOWN) {
                    if (tempObject.getAttacking() == 0) {
                        tempObject.setVelY(4);
                        tempObject.setFacing(0);
                    }
                } else if (currentKey == VK_Z) {
                    if (!tempObject.isOn_Cd()) {
                        tempObject.attack();
                    }
                }

            }
        }
        //System.out.println((VK_RIGHT | VK_DOWN) == (VK_RIGHT | VK_DOWN));

        if (currentKey == VK_ESCAPE) {
            System.exit(1);
        }

    }

    public synchronized void keyReleased(KeyEvent e) {

        pressed.remove(e.getKeyCode());
        int currentKey = e.getKeyCode();

        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.id == ObjectId.Player) {
                if (currentKey == VK_RIGHT) {
                    tempObject.setVelX(0);

                } else if (currentKey == VK_LEFT) {

                    tempObject.setVelX(0);
                } else if (currentKey == VK_UP) {
                    tempObject.setVelY(0);

                } else if (currentKey == VK_DOWN) {

                    tempObject.setVelY(0);
                }

            }
        }
    }

}
