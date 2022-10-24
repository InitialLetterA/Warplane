package com.MyGame;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import static java.lang.System.exit;

public class PlaneWars extends JFrame { //using window function JFrame to create game windows
    OurPlane ourPlane;

    //manage all the functions; initialization
    public void init() {
        sound("sound/game_music.wav");
        OurPlaneFactory opf = new OurPlaneFactory();
        //create our plane
        ourPlane = (OurPlane) opf.createPlane();
        //register
        control();
        createWindow();
    }

    //create empty window
    public void createWindow() {
        //add name on the title
        setTitle("War Plane");
        //limit the windows size
        setResizable(false);
        //when we close the window, stop the program
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //set the window location in the screen, window's length and width
        setBounds(500, 60, 512, 768);
        //default window is hidden, we need to set it to show
        setVisible(true);//the default argument is false
        //add enemy
        addEnemyPlanes();
        //continually repaint
        while (true) {
            repaint();
            //slow the repaint
            try {
                Thread.sleep(25); //every 50ms have a pause
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //launch entry
    public static void main(String[] args) {
        //run the program
        PlaneWars pw = new PlaneWars();
        pw.init();
    }


    //pencil to draw the picture
    public void paint(Graphics g) {//g can be treated as a pen
        addBackground(g);
        addPlane(g);
        for (EnemyPlane ep : enemyPlanes) {
            ep.drawPlane(g);
        }
        //apply collision detection
        crash();
        crashHero();

    }

    private int score = 0;
    int bg_y = 10;
    public void addBackground(Graphics g) {
        //load background to java
        Image bg = new ImageIcon("images/bg/bg.jpg").getImage();
        //background in windows
        g.drawImage(bg,
                0, bg_y, 512, 768 + bg_y,//image
                0, 0, 512, 768,      //windows
                null);
        //background upper the windows
        g.drawImage(bg,
                0, -768 + bg_y, 512, bg_y,//image
                0, 0, 512, 768,      //windows
                null);
        bg_y += 2;
        //set the background to initial location
        if (bg_y >= 768) {
            bg_y = 0;
        }

        //add score
        int x = 320;
        int y = 100;
        Font font = new Font(Font.SANS_SERIF, Font.BOLD, 30);
        g.setColor(new Color(0xFF6100));
        g.setFont(font);
        g.drawString("Score: " + score, x, y);
    }

    int ourPlaneX = 200;
    int ourPlaneY = 600;

    public void addPlane(Graphics g) {
        ourPlane.drawPlane(g);
        ourPlane.setPlaneX(ourPlaneX);
        ourPlane.setPlaneY(ourPlaneY);
    }

    enum Direction{
        SPACE, UP, DOWN, LEFT, RIGHT;
    }
    //keyboard control plane
    public void control() {
        //keyboard listening
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int KeyCode = e.getKeyCode();//get key identification code
                Direction space = Direction.SPACE;
                Direction up = Direction.UP;
                Direction down = Direction.DOWN;
                Direction left = Direction.LEFT;
                Direction right = Direction.RIGHT;
                if (KeyCode == 37) {//left
                    ourPlaneX -= 10;
                    System.out.println("keycode: " + left);
                } else if (KeyCode == 39) {//right
                    ourPlaneX += 10;
                    System.out.println("keycode: " + right);
                } else if (KeyCode == 38) {//up
                    ourPlaneY -= 10;
                    System.out.println("keycode: " + up);
                } else if (KeyCode == 40) {//down
                    ourPlaneY += 10;
                    System.out.println("keycode: " + down);
                } else if (KeyCode == 32) {//fire, space
                    Graphics a = getGraphics();//get a from current windows
                    System.out.println("keycode: " + space);
                    ourPlane.fire(a);
                    sound("sound/fire_bullet.wav");
                }
            }
        });
    }


    //put enemy planes together using arraylist
    ArrayList<EnemyPlane> enemyPlanes = new ArrayList<>();

    public void addEnemyPlanes() {
        //create enemy factory object
        EnemyPlaneFactory epf = new EnemyPlaneFactory();
        //create enemy plane1 by factory object
        Graphics a = getGraphics();//get pen from current screen
        EnemyPlane enemyPlane = (EnemyPlane) epf.createPlane(a);
        enemyPlanes.add(enemyPlane);
        //create second enemy
        EnemyPlane enemyPlane2 = (EnemyPlane) epf.createPlane(a);
        enemyPlanes.add(enemyPlane2);
    }

    //collision detection
    public void crash() {
        //hero bullet rectangle
        //first bulletRectangle.get(0),second bulletRectangle.get(1)
        List<Rectangle> bulletRectangle = ourPlane.getBulletRectangle();
        if (bulletRectangle.size() > 0) {
            //avoid ConcurrentModificationException
            ListIterator<EnemyPlane> enemyPlaneListIterator = enemyPlanes.listIterator();
            //enemy boundary rectangle
            while (enemyPlaneListIterator.hasNext()) {
                EnemyPlane ep = enemyPlaneListIterator.next();
                Rectangle epPlaneRectangle = ep.getPlaneRectangle();
                //collision detection, any one of bullet hit the enemy
                if (epPlaneRectangle.intersects(bulletRectangle.get(0)) || epPlaneRectangle.intersects(bulletRectangle.get(1))) {
                    System.out.println("Enemy was hit！");
                    score += 100;
                    ep.setOver(true);//being hit
                    enemyPlaneListIterator.remove();//remove plane which was hit
                    //show the boom
                    Graphics g = this.getGraphics();
                    ep.explode(g);
                    sound("sound/big_plane_killed.wav");

                    //when the enemies all gone, add new enemy
                    if (enemyPlanes.size() == 0) {
                        //delay  adding new enemy
                        new Thread(() -> {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            addEnemyPlanes();
                        }).start();
                    }
                }
            }
        }
    }

    public void crashHero() {
        //hero plane collision detection//add
        for (EnemyPlane ep : enemyPlanes) {
            List<Rectangle> bulletRectangleEnemy = ep.getBulletRectangle();//add
            if (bulletRectangleEnemy.size() > 0) {
                Rectangle opPlaneRectangle = ourPlane.getPlaneRectangle();
                if (opPlaneRectangle.intersects(bulletRectangleEnemy.get(0))) {
                    System.out.println("Hero get hit!!!");
                    ourPlane.setOver(true);
                    Graphics g = this.getGraphics();
                    ourPlane.explode(g);
                    sound("sound/game_over.wav");
                    addGameOver(g);
                    try {
                        Thread.sleep(1000); //2000ms have a pause
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //exit when plane got hit
                    exit(0);
                }
            }
        }
    }

    public void addGameOver(Graphics g) {
        Image go = new ImageIcon("images/bg/gameover.jpg").getImage();
        g.drawImage(go, 200, 300, 512, 768,
                0, 0, 512, 768, null);
        new Thread(){
            public void run(){
                for (int i = 0; i < 10; i++) {
                    try{
                        Thread.sleep(25);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    //music
    public void sound(String str) {
        File file = new File(str);
        try (AudioInputStream stream = AudioSystem.getAudioInputStream(file)) {
            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //applied double buffering to deal with flash
    @Override
    public void repaint() {
        super.repaint();
        update(getGraphics());
    }

    private Image offScreenImage;

    //override update function
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            //create a new image buffering space, the size of the image is same as windows size
            offScreenImage = this.createImage(this.getWidth(), this.getHeight());
            //get the pen, save as gImage
            Graphics gImage = offScreenImage.getGraphics();
            //buffering the image which will be drawn
            paint(gImage);
            g.drawImage(offScreenImage, 0, 0, null);
        }
    }


}
