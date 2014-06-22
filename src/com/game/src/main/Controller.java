/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.src.main;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author SuperStar
 */
public class Controller {

    public static final int BARRIER_MAX = 50;
    private static LinkedList<Boom> boms = new LinkedList<Boom>();
    private static LinkedList<Creep> creeps = new LinkedList<Creep>();
    private static LinkedList<CreepStupid> creepsStupid = new LinkedList<CreepStupid>();
    private static LinkedList<Barrier> barriers = new LinkedList<Barrier>();
    Random r = new Random();
    Boom tempBoom;
    Creep tempCreep;
    CreepStupid tempCreepStupid;
    Game game;

    public Controller(Game game) {
        this.game = game;
//        addCreep(new Creep(0, 40, 1, 0));
        for (int i = 0; i < BARRIER_MAX; i++) {
            int x = r.nextInt(30) * 40;
            int y = r.nextInt(15) * 40;
            addBarrier(new Barrier(x, y));
        }
        for (int i = 0; i < 2; i++) {
            do {
                tempCreep = new Creep(r.nextInt(30) * 40, r.nextInt(15) * 40, r.nextInt(1) + 2, 0);
            } while (checkBarrier(tempCreep));
            addCreep(tempCreep);
            do {
                tempCreep = new Creep(r.nextInt(30) * 40, r.nextInt(15) * 40, 0, r.nextInt(1) + 2);
            } while (checkBarrier(tempCreep));
            addCreep(tempCreep);
            do {
                tempCreep = new Creep(r.nextInt(30) * 40, r.nextInt(15) * 40, -r.nextInt(1) - 2, 0);
            } while (checkBarrier(tempCreep));
            addCreep(tempCreep);
            do {
                tempCreep = new Creep(r.nextInt(30) * 40, r.nextInt(15) * 40, 0, -r.nextInt(1) - 2);
            } while (checkBarrier(tempCreep));
            addCreep(tempCreep);
        }
        for (int i = 0; i < 5; i++) {
            do {
                tempCreepStupid = new CreepStupid(r.nextInt(30) * 40, r.nextInt(15) * 40);
            } while (checkBarrier(tempCreepStupid));
            addCreepStupid(tempCreepStupid);
        }
    }

    public static boolean checkBarrier(IObject iObject) {
        if (iObject.getX() > 1200 - 40 || iObject.getY() > 600 - 40
                || iObject.getX() < 0 || iObject.getY() < 0) {
            return true;
        }
        for (int i = 0; i < BARRIER_MAX; i++) {
            if (iObject.getBounds().intersects(barriers.get(i).getBounds())) {
                return true;
            }
        }
        for (int i = 0; i < boms.size(); i++) {
            if (!(iObject instanceof Player) && boms.get(i).isBOOM() == 0 && iObject.getBounds().intersects(boms.get(i).getBounds())) {
                return true;
            }
        }
        return false;
    }

    public static boolean comeToDie(IObject iObject) {
        for (int i = 0; i < creeps.size(); i++) {
            if (iObject.getBounds().intersects(creeps.get(i).getBounds())) {
                return true;
            }
        }
        for (int i = 0; i < creepsStupid.size(); i++) {
            if (iObject.getBounds().intersects(creepsStupid.get(i).getBounds())) {
                return true;
            }
        }
        for (int i = 0; i < boms.size(); i++) {
            if (boms.get(i).isBOOM() != 0 && iObject.getBounds().intersects(boms.get(i).getBounds())) {
                return true;
            }
        }
        return false;
    }

    public static GameObject getDestination(IObject creep) {
//        IObject temp = creeps.get(0);
//        for( int i = 1; i < creeps.size(); i++ ){
//            if( getDistance(creep, creeps.get(i)) < getDistance(creep, temp) ){
//                temp = creeps.get(i);
//            }
//        }
        IObject temp = Game.getPlayer();
        if (temp != null) {
            double x = creep.getX();
            double y = creep.getY();
            if ((temp.getX() - x) * (temp.getX() - x) > (temp.getY() - y) * (temp.getY() - y)) {
                x = Math.round(temp.getX() / 40) * 40;
            } else {
                y = Math.round(temp.getY() / 40) * 40;
            }
            System.out.println("Stupid: " + creep.hashCode() + " follow: " + temp.hashCode() + " x = " + x + " y = " + y);
            return new GameObject(x, y);
        }
                    return new GameObject(0, 0);

    }

    private static double getDistance(IObject o1, IObject o2) {
        return (o1.getX() - o2.getX()) * (o1.getX() - o2.getX())
                + (o1.getY() - o2.getY()) * (o1.getY() - o2.getY());
    }

    public void tick() {
        for (int i = 0; i < boms.size(); i++) {
            tempBoom = boms.get(i);
            if (tempBoom.isBOOM() == 2) {
                removeBoom(tempBoom);
            } else if (tempBoom.isBOOM() == 1) {
                removeCreep(tempBoom);
            }

            tempBoom.tick();
        }

        for (int i = 0; i < creeps.size(); i++) {
            tempCreep = creeps.get(i);

            tempCreep.tick();
        }

        for (int i = 0; i < creepsStupid.size(); i++) {
            tempCreepStupid = creepsStupid.get(i);

            tempCreepStupid.tick();
        }
    }

    private void removeCreep(IObject boom) {
        int i = 0;
        while (i < creeps.size()) {
            tempCreep = creeps.get(i);
            if (boom.getBounds().intersects(tempCreep.getBounds())) {
                System.out.println("remove " + tempCreep.hashCode());
                removeCreep(tempCreep);
            } else {
                i++;
            }
        }
        i = 0;
        while (i < creepsStupid.size()) {
            tempCreepStupid = creepsStupid.get(i);
            if (boom.getBounds().intersects(tempCreepStupid.getBounds())) {
                System.out.println("remove SP " + tempCreepStupid.hashCode());
                removeCreepStupid(tempCreepStupid);
            } else {
                i++;
            }
        }
    }

    public void render(Graphics g) {
        for (int i = 0; i < boms.size(); i++) {
            tempBoom = boms.get(i);

            tempBoom.render(g);
        }
        for (int i = 0; i < creeps.size(); i++) {
            tempCreep = creeps.get(i);

            tempCreep.render(g);
        }
        for (int i = 0; i < creepsStupid.size(); i++) {
            tempCreepStupid = creepsStupid.get(i);

            tempCreepStupid.render(g);
        }
        for (int i = 0; i < barriers.size(); i++) {
            Barrier barrier = barriers.get(i);

            barrier.render(g);
        }
    }

    public void addBoom(Boom boom) {
        boms.add(boom);
    }

    public void addBarrier(Barrier barrier) {
        barriers.add(barrier);
    }

    public void removeBoom(Boom boom) {
        boms.remove(boom);
    }

    public void addCreep(Creep creep) {
        creeps.add(creep);
    }

    public void removeCreep(Creep creep) {
        creeps.remove(creep);
    }

    public void addCreepStupid(CreepStupid creep) {
        creepsStupid.add(creep);
    }

    public void removeCreepStupid(CreepStupid creep) {
        creepsStupid.remove(creep);
    }

    public int getBoom() {
        return boms.size();
    }
}
