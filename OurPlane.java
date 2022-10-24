package com.MyGame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OurPlane extends Plane{
    //current location of bullet
    int bulletXX1;
    int bulletXX2;
    int bulletYY;
    Image bullet;
    Image bullet2;
    @Override
    public void fire(Graphics g) {
        new Thread() {
            //location of bullet
            int bulletX = getPlaneX()-15;
            int bulletY = getPlaneY();
            public void run() {
                //load bullet image to java
                bullet = new ImageIcon("images/bullet/bullet.png").getImage();
                bullet2 = new ImageIcon("images/bullet/bullet.png").getImage();
                while (true) {
                    g.drawImage(bullet, bulletX, bulletY, null);
                    g.drawImage(bullet2, bulletX+45, bulletY, null);
                    bulletY -= 20;
                    bulletXX1 = bulletX;
                    bulletXX2 = bulletX+45;
                    bulletYY = bulletY;
                    if(bulletY <= 0){//release bullet
                        bullet = null; //release memory resource
                        bullet2 = null;
                        return;
                    }
                    try { //slow the repaint
                        Thread.sleep(25); //every 50ms have a pause
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    //hero bullet is double
    public List<Rectangle> getBulletRectangle() {
        List<Rectangle> list = new ArrayList<>();
        if(bullet != null && bullet2 != null) {//avoid nullptr exception
            Rectangle r1 = new Rectangle(bulletXX1, bulletYY, bullet.getWidth(null), bullet.getHeight(null));
            Rectangle r2 = new Rectangle(bulletXX2, bulletYY, bullet.getWidth(null), bullet.getHeight(null));
            list.add(r1);
            list.add(r2);
        }
        return list;
    }
}
