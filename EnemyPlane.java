package com.MyGame;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class EnemyPlane extends Plane {
    private Graphics g;

    public EnemyPlane(Graphics g) {
        this.g = g;
        //create time clock task
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                fire(g);
            }
        };
        //create time clock
        Timer t = new Timer();
        Date date = new Date();
        t.schedule(tt, date, 3000);//3000ms = 3s
    }

    Image bullet;
    //current location of bullet
    int bulletXX;
    int bulletYY;

    @Override
    public void fire(Graphics g) {
        if(isOver()){
            return;//if enemy was hit, then exit
        }
        new Thread() {
            public void run() {
                //the location of bullet is based on location of plane
                int bulletX = getPlaneX();
                int bulletY = getPlaneY();
                bullet = new ImageIcon("images/bullet/bullet.png").getImage();
                g.drawImage(bullet, 300, 300, null);
                while (true) {
                    g.drawImage(bullet, bulletX + 4, bulletY + 35, null);
                    bulletY += 5;
                    bulletXX = bulletX + 4;
                    bulletYY = bulletY;
                    if (bulletY >= 768) {
                        bullet = null;//release bullet in the memory which exit the windows
                        return;
                    }
                    //System.out.println("Enemy_bulletY"+bulletY);
                    try {
                        Thread.sleep(25);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

    @Override
    //get rectangle outer of enemy plane bullet(single)
    public List<Rectangle> getBulletRectangle() {
        List<Rectangle> list = new ArrayList<>();
        //avoid exception
        if(bullet!=null) {
            Rectangle r = new Rectangle(bulletXX, bulletYY, bullet.getWidth(null), bullet.getHeight(null));
            list.add(r);
        }
        return list;
    }
}
