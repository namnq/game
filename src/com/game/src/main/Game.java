/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.src.main;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author SuperStar
 */
public class Game extends Canvas implements Runnable {

    public static final int WIDTH = 1190;
    public static final int HEIGHT = 590;
    public static final String TITLE = "BOM BOM";

    private boolean boom = false;
    private boolean running = false;
    private Thread thread;

    private BufferedImage image = new BufferedImage(WIDTH / 2, HEIGHT / 2, BufferedImage.TYPE_INT_RGB);
    private BufferedImage background = null;

    private static Player p;
    private Controller c;

    public void init() {
        requestFocus();
        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            background = loader.loadImage("image/background.png");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        addKeyListener(new KeyInput(this));

        p = new Player(0, 0);
        c = new Controller(this);
    }

    private synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    private synchronized void stop() {
        if (!running) {
            return;
        }
        p = null;
//        running = false;
//        try {
//            thread.join();
//        } catch (InterruptedException ex) {
//            ex.printStackTrace();
//        }
//        System.exit(1);
    }

    @Override
    public void run() {
        init();
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if (delta >= 5) {
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(updates + " Ticks, Fps " + frames);
                updates = 0;
                frames = 0;
            }
            if (p != null) {
                if (Controller.comeToDie(p)) {
                    System.out.println("You lose!!!");
                    stop();
                }
            }
        }
//        stop();
    }

    private void tick() {
        if (p != null) {
            p.tick();
        }
        c.tick();
    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

        g.drawImage(background, 0, 0, null);
        if (p != null) {
            p.render(g);
        }else{
            g.drawString("YOU LOSE", WIDTH/2, HEIGHT/2);
            
        }
        c.render(g);

        g.dispose();
        bs.show();
    }

    public void keyPressed(KeyEvent e) {
        if (p != null) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_RIGHT) {
//            if( p.getVelX() == 0 )
//                p.setY(Math.round(p.getY() / 40) * 40);
                p.setVelX(2);
            } else if (key == KeyEvent.VK_LEFT) {
//            if( p.getVelX() == 0 )
//                p.setY(Math.round(p.getY() / 40) * 40);
                p.setVelX(-2);
            } else if (key == KeyEvent.VK_UP) {
//            if( p.getVelY() == 0 )
//                p.setX(Math.round(p.getX() / 40) * 40);
                p.setVelY(-2);
            } else if (key == KeyEvent.VK_DOWN) {
//            if( p.getVelY() == 0 )
//                p.setX(Math.round(p.getX() / 40) * 40);
                p.setVelY(2);
            } else if (key == KeyEvent.VK_SPACE && !boom) {
                double x = Math.round(p.getX() / 40) * 40;
                double y = Math.round(p.getY() / 40) * 40;
                if (c.getBoom() < 5) {
                    c.addBoom(new Boom(x, y));
                }
                boom = true;
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        if (p != null) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_RIGHT) {
                p.setVelX(0);
            } else if (key == KeyEvent.VK_LEFT) {
                p.setVelX(0);
            } else if (key == KeyEvent.VK_UP) {
                p.setVelY(0);
            } else if (key == KeyEvent.VK_DOWN) {
                p.setVelY(0);
            } else if (key == KeyEvent.VK_SPACE) {
                boom = false;
            }
        }
    }

    public static void main(String[] args) {

        Game game = new Game();

        game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        game.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        game.setMinimumSize(new Dimension(WIDTH, HEIGHT));

        JFrame frame = new JFrame(game.TITLE);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game.start();
    }

    public static IObject getPlayer() {
        return p;
    }
//
//    public BufferedImage getSpriteSheet(){
//        return spriteSheet;
//    }
//    
//    public BufferedImage getBoomSheet(){
//        return boomSheet;
//    }
//    
//    public BufferedImage getDeadSheet(){
//        return deadSheet;
//    }
//    
//    public BufferedImage getCreepSheet(){
//        return creepSheet;
//    }
}
