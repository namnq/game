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
public class CreepStupid extends GameObject implements IObject{
    private double velX;
    private double velY;
    private GameObject destination;
    int count = 0;
    
    private static BufferedImage creep = null;
    static {
        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            creep = loader.loadImage("image/CreepStupid.png");
            
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public CreepStupid(double x, double y) {
        super(x, y);
        destination = new GameObject(x, y);
    }


    public void tick(){
        if( Controller.checkBarrier(this) ){
            changeMove();
        }
        if( destination.x == x && destination.y == y ){
            changeMove();
        }
        x = x + velX;
        y = y + velY;
        count++;
    }
    
    private void changeMove(){
        x = Math.round(x / 40) * 40;
        y = Math.round(y / 40) * 40;
        destination = Controller.getDestination(this);
        velY = 0;
        velX = 0;
        if( destination.x - x > 0) velX = 1;
        if( destination.x - x < 0) velX = -1;
        if( destination.y - y > 0) velY = 1;
        if( destination.y - y < 0) velY = -1;
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
