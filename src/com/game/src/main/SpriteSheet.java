/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.game.src.main;

import java.awt.image.BufferedImage;

/**
 *
 * @author SuperStar
 */
public class SpriteSheet {
    private BufferedImage image;

    public SpriteSheet(BufferedImage image) {
        this.image = image;
    }
    
    public BufferedImage grabImage(int col, int row, int width, int height){
        BufferedImage img = image.getSubimage((col * 40) - 40, (row * 40) - 40, width, height);
        return img;
    }
}
