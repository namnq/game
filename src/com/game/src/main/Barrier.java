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



public class Barrier extends GameObject implements IObject{
    private static BufferedImage barrier = null;
    static {
        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            barrier = loader.loadImage("image/VC.png");
            
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
    
    public Barrier(double x, double y) {
        super(x, y);
    }


    public void tick(){
//        if( x <= 0 ) velX = -velX;
//        if( y <= 0 ) velY = -velY;
//        if( x >= 1200 - 40 ) velX = -velX;
//        if( y >= 600 -40 ) velY = -velY;
//        x = x + velX;
//        y = y + velY;
    }
    public Rectangle getBounds(){
        return new Rectangle( (int)x, (int)y, 40, 40 );
    }
    
    public void render(Graphics g){
        g.drawImage(barrier, (int)x, (int)y, null);
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
