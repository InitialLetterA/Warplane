package com.MyGame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlaneWarsTest {
    @Test
    void GameTest(){
        PlaneWars planeWars = new PlaneWars();
        planeWars.init();
    }

    @Test
    void control() {
        //test some initial value
        PlaneWars  planeWars = new PlaneWars();
        System.out.println(planeWars.ourPlaneX);
        System.out.println(planeWars.ourPlaneY);
        System.out.println(planeWars.bg_y);
        System.out.println(planeWars.enemyPlanes);
    }

}