package com.MyGame;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OurPlaneTest {

    @Test
    void fire() {
        OurPlane ourPlane = new OurPlane();
        int bulletxx1 = ourPlane.bulletXX1;
        int bulletxx2 = ourPlane.bulletXX2;
        System.out.println(bulletxx1);
        System.out.println(bulletxx2);
        assertEquals(0,bulletxx1);
        assertEquals(0,bulletxx2);
    }

    @Test
    void getBulletRectangle() {
        //check whether the rectangle create correctly
        OurPlane ourPlane = new OurPlane();
        List<Rectangle> listRectangle = ourPlane.getBulletRectangle();
        for (int i = 0; i < listRectangle.size(); i++) {
            System.out.println("The "+(i+1)+" Rectangle parameter:");
            Rectangle tmpRectangle = listRectangle.get(i);
            System.out.println(tmpRectangle.x);
            System.out.println(tmpRectangle.y);
            System.out.println(tmpRectangle.height);
            System.out.println(tmpRectangle.width);
            assertEquals(null, tmpRectangle);
        }
    }
    }