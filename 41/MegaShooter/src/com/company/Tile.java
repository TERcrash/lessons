package com.company;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import processing.core.PApplet;
import processing.core.PImage;
import shiffman.box2d.Box2DProcessing;

public class Tile {
    float x;
    float y;

    static int size = 64;

    PImage skin;

    PApplet parent;

    Tile(float x, float y, PImage skin, PApplet p, Box2DProcessing box2D) {
        parent = p;

        this.x = x;
        this.y = y;
        this.skin = skin;

        initPhysics(box2D);
    }

    void draw() {
        parent.image(skin, x, y);
    }

    void initPhysics(Box2DProcessing box2D){
        BodyDef bd = new BodyDef();
        bd.position.set(box2D.coordPixelsToWorld(x, y));
        bd.type = BodyType.STATIC;
        Body body = box2D.createBody(bd);

        PolygonShape ps = new PolygonShape();
        ps.setAsBox(box2D.scalarPixelsToWorld(size / 2), box2D.scalarPixelsToWorld(size / 2));

        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.restitution = 0;
        body.createFixture(fd);
    }
}
