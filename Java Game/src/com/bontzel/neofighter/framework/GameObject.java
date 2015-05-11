/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bontzel.neofighter.framework;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Cosmin
 */
public abstract class GameObject {

    protected float x, y;
    protected float velX = 0, velY = 0;
    protected ObjectId id;
    protected int facing;
    protected int attacking = 0;
    protected int health_points;
    protected int experience_points = 0;
    protected int level = 1;
    protected int attack_power;
    protected int hurt;
    protected boolean dead = false;
    protected int TLvUP_EXP_points = 6;
    protected int EXP_upon_death;
    protected boolean on_Cd = false;

    public int getTLvUP_EXP_points() {
        return TLvUP_EXP_points;
    }

    public void setTLvUP_EXP_points(int TLvUP_EXP_points) {
        this.TLvUP_EXP_points = TLvUP_EXP_points;
    }

    public boolean isOn_Cd() {
        return on_Cd;
    }

    public void setOn_Cd(boolean on_Cd) {
        this.on_Cd = on_Cd;
    }

    public int getEXP_upon_death() {
        return EXP_upon_death;
    }

    public void setEXP_upon_death(int EXP_upon_death) {
        this.EXP_upon_death = EXP_upon_death;
    }

    public int getHurt() {
        return hurt;
    }

    public void setHurt(int hurt) {
        this.hurt = hurt;
    }

    public int getHealth_points() {
        return health_points;
    }

    public void setHealth_points(int health_points) {
        this.health_points = health_points;
    }

    public int getExperience_points() {
        return experience_points;
    }

    public void setExperience_points(int experience_points) {
        this.experience_points = experience_points;
    }

    public int getLevel() {
        return level;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public GameObject(float x, float y, ObjectId id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void tick(LinkedList<GameObject> object);

    public abstract void render(Graphics g);

    public abstract Rectangle getBounds();

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getVelX() {
        return velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public ObjectId getId() {
        return id;
    }

    public int getAttack_power() {
        return attack_power;
    }

    public void setAttack_power(int attack_power) {
        this.attack_power = attack_power;
    }

    public int getFacing() {
        return facing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public int getAttacking() {
        return attacking;
    }

    public void setAttacking(int attacking) {
        this.attacking = attacking;
    }

    public void attack() {
        ;
    }
    

    
}
