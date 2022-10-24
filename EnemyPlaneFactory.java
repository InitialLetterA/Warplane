package com.MyGame;

import java.awt.*;
import java.util.Random;

public class EnemyPlaneFactory implements PlaneFactory {
    @Override
    public Plane createPlane() {
        return null;
    }

    @Override
    public Plane createPlane(Graphics g) {
        //random location for enemy location
        Random r = new Random();
        int x = r.nextInt(410);//create random int number from 0-410
        int y = r.nextInt(150);//create random int number from 0-150
        EnemyPlane ep = new EnemyPlane(g);
        ep.setPlaneX(x);
        ep.setPlaneY(y);
        ep.setPlaneImagePath("images/enemy/enemy1.png");
        return ep;
    }
}
