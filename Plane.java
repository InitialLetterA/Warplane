package com.MyGame;

/*
describe abstract plane class
 */
import javax.swing.*;
import java.awt.*;
import java.util.List;

//plane abstract parent class
public abstract class Plane {
    //plane location
    private int planeX;
    private int planeY;
    //check whether the plane still exist
    private boolean over = false;
    private String planeImagePath;

    public int getPlaneX() {
        return planeX;
    }

    public void setPlaneX(int planeX) {
        this.planeX = planeX;
    }

    public int getPlaneY() {
        return planeY;
    }

    public void setPlaneY(int planeY) {
        this.planeY = planeY;
    }

    //    public String getPlaneImagePath(String s) {return planeImagePath;}
    public String getPlaneImagePath() {
        return planeImagePath;
    }

    public void setPlaneImagePath(String planeImagePath) {
        this.planeImagePath = planeImagePath;
    }

    //draw plane
    Image plane;

    public void drawPlane(Graphics g) {
        //load plane image to java
        plane = new ImageIcon(planeImagePath).getImage();
        g.drawImage(plane, planeX, planeY, null);
    }

    //fire
    public abstract void fire(Graphics g);

    //get rectangle of the outer boundary of plane
    public Rectangle getPlaneRectangle() {
        //based on plane image object, get the width and height of image
        int width = plane.getWidth(null);
        int height = plane.getHeight(null);
        //create rectangle area outer the plane
        Rectangle r = new Rectangle(planeX, planeY, width, height);
        return r;
    }

    //get rectangle of the outer boundary of bullet
    public abstract List<Rectangle> getBulletRectangle();

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    //implement explode
    public void explode(Graphics g) {
        //load boom picture
        new Thread() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    Image flower = new ImageIcon("images/boom/boom01.png").getImage();
                    g.drawImage(flower, planeX, planeY, null);
                    try{
                        Thread.sleep(25);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
