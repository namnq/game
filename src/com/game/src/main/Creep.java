/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.game.src.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.Timer;

/**
 *
 * @author SuperStar
 */
public class Creep extends GameObject implements IObject{
    private double velX;
    private double velY;
    
    private static BufferedImage creep = null;
    static {
        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            creep = loader.loadImage("image/creep.png");
            
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public Creep(double x, double y, double velX, double velY) {
        super(x, y);
        this.velX = velX;
        this.velY = velY;
    }


    public void tick(){
        
        if( Controller.checkBarrier(this) ){
            velX = -velX;
            velY = -velY;
        }
        x = x + velX;
        y = y + velY;
//        if( x <= 0 ) velX = -velX;
//        if( y <= 0 ) velY = -velY;
//        if( x >= 1200 - 40 ) velX = -velX;
//        if( y >= 600 -40 ) velY = -velY;
    }
    
    public Rectangle getBounds(){
        return new Rectangle( (int)x, (int)y, 40, 40 );
    }
    
    public void render(Graphics g){
        g.drawImage(creep, (int)x, (int)y, null);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }


}
