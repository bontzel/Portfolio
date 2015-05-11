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

/**
 *
 * @author Cosmin
 */
public class Player extends GameObject {

    private float width = 32, height = 54;
    Handler handler;

    private Animation playerIdle, playerWalkUp, playerIdleUp, playerWalkNE, playerWalkRight, playerIdleRight, playerWalkSE, playerWalkDown, playerIdleDown;
    private Animation playerWalkSW, playerWalkLeft, playerIdleLeft, playerWalkNW;
    private Animation playerPunchUp;

    private int delay = 250;
    private int latch = 0;
    private int punch_time = 30;
    private int cooldown = 20;

    Texture tex = Game.getInstance();

    public Player(float x, float y, Handler handler, ObjectId id) {
        super(x, y, id);
        this.handler = handler;

        playerWalkUp = new Animation(5, tex.player_movement[2], tex.player_movement[3], tex.player_movement[2], tex.player_movement[1], tex.player_movement[0], tex.player_movement[1]);
        playerIdleUp = new Animation(5, tex.player_movement[1]);
        playerWalkNE = new Animation(5, tex.player_movement[6], tex.player_movement[7], tex.player_movement[6], tex.player_movement[5], tex.player_movement[4], tex.player_movement[5]);
        playerWalkRight = new Animation(5, tex.player_movement[10], tex.player_movement[11], tex.player_movement[10], tex.player_movement[9], tex.player_movement[8], tex.player_movement[9]);
        playerIdleRight = new Animation(5, tex.player_movement[9]);
        playerWalkSE = new Animation(5, tex.player_movement[14], tex.player_movement[15], tex.player_movement[14], tex.player_movement[13], tex.player_movement[12], tex.player_movement[13]);
        playerWalkDown = new Animation(5, tex.player_movement[18], tex.player_movement[19], tex.player_movement[18], tex.player_movement[17], tex.player_movement[16], tex.player_movement[17]);
        playerIdleDown = new Animation(5, tex.player_movement[17]);
        playerWalkSW = new Animation(5, tex.player_movement[22], tex.player_movement[23], tex.player_movement[22], tex.player_movement[21], tex.player_movement[20], tex.player_movement[21]);
        playerWalkLeft = new Animation(5, tex.player_movement[26], tex.player_movement[27], tex.player_movement[26], tex.player_movement[25], tex.player_movement[24], tex.player_movement[25]);
        playerIdleLeft = new Animation(5, tex.player_movement[25]);
        playerWalkNW = new Animation(5, tex.player_movement[30], tex.player_movement[31], tex.player_movement[30], tex.player_movement[29], tex.player_movement[28], tex.player_movement[29]);
        playerPunchUp = new Animation(5, tex.player_action[0], tex.player_action[1], tex.player_action[0]);

        this.setAttack_power(2);
        health_points = 10;
    }

    @Override
    public void tick(LinkedList<GameObject> object) {

        x += velX;
        y += velY;
        
        if(attacking == 1) {
            if(punch_time > 0) punch_time--;      //attack time, useful for animation mostly
            else {
                punch_time = 30;
                attacking = 0;
                on_Cd = true;
            }
        }
            
         if(on_Cd) {
             if(cooldown > 0) cooldown--;        // cooldown time .. so the player wouldn't be that much OP
             else {
                 on_Cd = false;
                 cooldown = 20;
             }
         }
            
        

        Collision(object);

        playerWalkUp.runAnimation();
        playerIdleUp.runAnimation();
        playerWalkNE.runAnimation();
        playerWalkRight.runAnimation();
        playerIdleRight.runAnimation();
        playerWalkSE.runAnimation();
        playerWalkDown.runAnimation();
        playerIdleDown.runAnimation();
        playerWalkSW.runAnimation();
        playerWalkLeft.runAnimation();
        playerIdleLeft.runAnimation();
        playerWalkNW.runAnimation();
        playerPunchUp.runAnimation2();
    }

    private void Collision(LinkedList<GameObject> object) {

        for (int i = 0; i < handler.object.size(); i++) {
            final GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ObjectId.Block) {

                if (getBoundsTop().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() + 32;
                    velY = 0;

                }

                if (getBounds().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() - height + 1;
                    velY = 0;

                }

                if (getBoundsRight().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() - 58;
                    velX = 0;

                }

                if (getBoundsLeft().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() + 34;
                    velX = 0;

                }
            } else if (tempObject.getId() == ObjectId.Mob) {
                if (getBoundsTop().intersects(tempObject.getBounds())) {
                    if (attacking == 1 && facing == 4) {

                        tempObject.setY(y - 40);
                        tempObject.setHealth_points(tempObject.getHealth_points() - attack_power);
                        tempObject.setHurt(1);

                        Timer timer = new Timer();
                        int delay = 300;

                        tempObject.setVelY(-4);

                        timer.schedule(new TimerTask() {
                            public void run() {
                                tempObject.setVelY(0);
                                tempObject.setHurt(0);
                            }
                        }, delay);

                        if (tempObject.getHealth_points() <= 0) {
                            experience_points += tempObject.getEXP_upon_death();
                            handler.object.remove(tempObject);
                        }
                    } else {
                        getHurt(tempObject, 0);
                    }
                }

            }
        }
    }

    @Override
    public void render(Graphics g) {
        // g.setColor(Color.BLUE);
        // g.drawImage(tex.player[0],(int)x , (int)y , 72, 96, null);

        //draw moves here
        if (velY < 0 && velX == 0) {
            facing = 4;
        } else if (velY > 0 && velX == 0) {
            facing = 0;
        } else if (velX > 0 && velY == 0) {
            facing = 6;
        } else if (velX < 0 && velY == 0) {
            facing = 2;
        }

        if (attacking == 0) {
            if (velX == 0 && velY == 0) {
                playerWalkUp.resetAnimation();
                playerWalkNE.resetAnimation();
                playerWalkRight.resetAnimation();
                playerWalkDown.resetAnimation();
                playerWalkSE.resetAnimation();
                playerPunchUp.resetAnimation();

                switch (facing) {

                    case 4:
                        playerIdleUp.drawAnimation(g, (int) x - 12, (int) y - 9, 56, 70);
                        break;
                    case 6:
                        playerIdleRight.drawAnimation(g, (int) x - 12, (int) y - 9, 56, 70);
                        break;
                    case 0:
                        playerIdleDown.drawAnimation(g, (int) x - 12, (int) y - 9, 56, 70);
                        break;
                    case 2:
                        playerIdleLeft.drawAnimation(g, (int) x - 12, (int) y - 9, 56, 70);
                        break;
                }
            } else {
                switch (facing) {
                    case 4:
                        playerWalkUp.drawAnimation(g, (int) x - 12, (int) y - 9, 56, 70);
                        break;
                    case 5:
                        playerWalkNE.drawAnimation(g, (int) x - 12, (int) y - 9, 56, 70);
                        break;
                    case 6:
                        playerWalkRight.drawAnimation(g, (int) x - 12, (int) y - 9, 56, 70);
                        break;
                    case 7:
                        playerWalkSE.drawAnimation(g, (int) x - 12, (int) y - 9, 56, 70);
                        break;
                    case 0:
                        playerWalkDown.drawAnimation(g, (int) x - 12, (int) y - 9, 56, 70);
                        break;
                    case 1:
                        playerWalkSW.drawAnimation(g, (int) x - 12, (int) y - 9, 56, 70);
                        //System.out.println("got here");
                        break;
                    case 2:
                        playerWalkLeft.drawAnimation(g, (int) x - 12, (int) y - 9, 56, 70);
                        break;
                    case 3:
                        playerWalkNW.drawAnimation(g, (int) x - 12, (int) y - 9, 56, 70);
                        break;
                }
            }
        } else {
            switch (facing) {
                case 4:
                    playerPunchUp.drawAnimation(g, (int) x - 12, (int) y - 9, 56, 70);
                    break;
            }
        }

        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.red);
        g2d.draw(getBounds());
        g2d.draw(getBoundsLeft());      //collision boundaries
        g2d.draw(getBoundsRight());
        g2d.draw(getBoundsTop());

        g.setColor(Color.white);
        g.drawString(Float.toString(velX) + " " + Float.toString(velY) + " " + Integer.toString(facing), 25, 25);

        if (experience_points == TLvUP_EXP_points) {
            TLvUP_EXP_points *= 2;
            level += 1;
            //System.out.println(level + " " + experience_points);
            latch = 1;
        }

        if (delay > 0 && latch == 1) {
            delay--;
            g.drawString("Level Up!", (int) x, (int) y - 10);
        } else {
            delay = 250;
            latch = 0;
        }

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) ((int) x + width / 2 - width / 4), (int) ((int) y + height / 2), (int) width / 2, (int) height / 2);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle((int) ((int) x + width / 2 - width / 4), (int) y, (int) width / 2, (int) height / 2);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle((int) ((int) x + width - 5), (int) y + 5, (int) 5, (int) height - 10);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle((int) x, (int) y + 5, (int) 5, (int) height - 10);
    }

    private void getHurt(GameObject tempObject, int facing) {

        hurt = 1;
        health_points -= tempObject.getAttack_power();
        y += 25;

        // switch needed here
        velY = 2;

        Timer timer = new Timer();
        int delay = 300;

        timer.schedule(new TimerTask() {
            public void run() {
                velY = 0;
                hurt = 0;
            }
        }, delay);

    }

    @Override
    public void attack() {
        if (attacking != 1) {
            attacking = 1;

            int delay1 = 500;// in ms 

            switch (facing) {
                case 4:
                    velY = -2;
            }

            Timer timer = new Timer();

            timer.schedule(new TimerTask() {
                public void run() {
                    velY = 0;
                }
            }, delay1);

        }
    }

}
