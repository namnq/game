/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.game.src.main;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author SuperStar
 */
public interface IObject {
    public void tick();
    public Rectangle getBounds();
    public void render(Graphics g);
    public double getX();
    public double getY();
}
