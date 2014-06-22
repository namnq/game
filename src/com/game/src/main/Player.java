/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.game.src.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 *
 * @author SuperStar
 */
public class Player extends GameObject implements IObject{
    private double velX;
    private double velY;

    private static BufferedImage player = null;
    static {
        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            player = loader.loadImage("image/4444.png");
            
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
    
    public Player(double x, double y) {
        super(x, y);
    }
    
    public void tick(){
        x = x + velX;
        y = y + velY;
        if( Controller.checkBarrier(this) ){
            x = x - velX;
            y = y - velY;
            int xMod = (int)x % 40;
            int yMod = (int)y % 40;
            if( (xMod > 32) ) x = x + 40 - xMod;
            if( (yMod > 32) ) y = y + 40 - yMod;
            if( (xMod < 8) ) x = x - xMod;
            if( (yMod < 8) ) y = y - yMod;
        }
    }
    
    public void render(Graphics g){
        g.drawImage(player, (int)x, (int)y, null);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public double getVelX() {
        return velX;
    }

    public double getVelY() {
        return velY;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle( (int)x + 1, (int)y + 1, 38, 38 );
    }
}
