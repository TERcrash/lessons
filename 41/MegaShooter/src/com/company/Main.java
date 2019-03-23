package com.company;

import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

import java.io.IOException;
import java.util.HashMap;

public class Main extends PApplet {
    Box2DProcessing box2D;

    World world;
    HashMap<java.lang.Character, Boolean> keys = new HashMap<>();

    String game = "GAME";
    String gameOver = "GAMEOVER";
    String screen = game;


    public static void main(String[] args) {
        PApplet.main(Main.class);
    }

    public void settings() {
        size(1000, 700);
        smooth(0);
    }

    public void setup() {
        box2D = new Box2DProcessing(this);

        box2D.createWorld();
        box2D.listenForCollisions();

        try {
            world = new World(this, box2D);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw() {
        if (screen == game) {
            game();
        } else if (screen == gameOver) {
            gameOver();
        }
    }

    void game() {
        background(192, 197, 206);
        world.draw();
        handleKeys();

        if (world.killerMLG.isDead()) {
            screen = gameOver;
        }
    }

    void gameOver() {
        handleKeys();
        background(45);
        textSize(70);
        text("YOU DIED", width / 3, height / 4);
        textSize(30);
        text("Press F to pay respects", width / 3, height / 2);

        if (isKeyPressed('f')) {
            println("Страна гордится тобой");
            restart();
        }
    }

    void restart() {

        try {
            world = new World(this, box2D);
        } catch (IOException e) {
            e.printStackTrace();
        }
        screen = game;
    }

    private void handleKeys() {
        world.killerMLG.isMoving = false;
        if (isKeyPressed('d')) {
            world.killerMLG.moveRight();
            world.killerMLG.isMoving = true;
        }
        if (isKeyPressed('a')) {
            world.killerMLG.moveLeft();
            world.killerMLG.isMoving = true;
        }
        if (isKeyPressed(' ')) {
            world.killerMLG.jump();
        }
    }

    private boolean isKeyPressed(char key) {
        return keys.containsKey(key) && keys.get(key);
    }

    public void keyPressed() {
        keys.put(key, true);
    }

    public void keyReleased() {
        keys.put(key, false);
    }

    public void mousePressed() {
        world.killerMLG.isShooting = true;
    }

    public void mouseReleased() {
        world.killerMLG.isShooting = false;
    }
}
