package com.MyGame;

import java.awt.*;

public class OurPlaneFactory implements PlaneFactory{
    //create hero plane
    @Override
    public Plane createPlane() {
        OurPlane ourPlane = new OurPlane();
        ourPlane.setPlaneImagePath("images/hero/hero02.png");
        return ourPlane;
    }

    @Override
    public Plane createPlane(Graphics g) {
        return null;
    }
}
