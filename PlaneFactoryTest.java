package com.MyGame;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class PlaneFactoryTest {

    @Test
    void createPlane() {
        OurPlaneFactory ourPlaneFactory = new OurPlaneFactory();
        Plane plane = ourPlaneFactory.createPlane();
        //test the location of plane. use x as test parameter
        assertEquals(0,plane.getPlaneX());
        //test whether the plane exist
        assertEquals(false, plane.isOver());
    }

    @Test
    void testCreatePlane() {
    }
}