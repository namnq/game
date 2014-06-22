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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

/**
 *
 * @author SuperStar
 */
public class Boom extends GameObject implements IObject{
    private long time = 0;
    
    private int isBOOM = 0;
    private static BufferedImage boom = null;
    private static BufferedImage dead = null;
    private boolean isSound = true;
    static {
        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            boom = loader.loadImage("image/BOM.png");
            dead = loader.loadImage("image/dead.png");
            
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public Boom(double x, double y) {
        super(x, y);
        time = System.currentTimeMillis();
        isBOOM = 0;
    }
    
    public void tick(){
//        y = y - 1;
    }

    public void render(Graphics g) {
        if( System.currentTimeMillis() - time > 3000 ) {
            if( isSound ){
                music();
                isSound = false;
            }
            g.drawImage(dead, (int)x - 40, (int)y - 40, null);
            if( System.currentTimeMillis() - time > 3300 ) isBOOM = 2;
            else isBOOM = 1;
        } else g.drawImage(boom, (int)x, (int)y, null);
    }

    public int isBOOM() {
        return isBOOM;
    }

    @Override
    public Rectangle getBounds() {
        if( isBOOM != 0 ) 
            return new Rectangle( (int)x - 40, (int)y - 40, 120, 120 );
        else return new Rectangle( (int)x, (int)y, 40, 40 );
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }
    
    public static void music() {
        try{
            String soundName = "boom.wav";    
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {}
    }
}
