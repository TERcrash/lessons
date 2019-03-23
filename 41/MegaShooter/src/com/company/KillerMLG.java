package com.company;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import processing.core.PApplet;
import shiffman.box2d.Box2DProcessing;

import java.util.ArrayList;

public class KillerMLG extends Character {

    private final Box2DProcessing box2D;
    boolean isShooting = false;
    boolean isMoving = false;

    int damage = 5;

    ArrayList<Bullet> bullets;

    Body body;

    KillerMLG(float x, float y, String[] appearance, PApplet p, Box2DProcessing box2D) {
        super(x, y, appearance, p);
        this.box2D = box2D;
        this.bullets = new ArrayList<>();
        health = 100;
        initPhysics();
    }

    void draw() {
        updatePhysics();
        super.draw();
        if (!onGround) {
            animation.stop(1);
        } else if (isMoving) {
            animation.play();
        } else {
            animation.stop(0);
        }
    }

    void jump() {
        if (onGround) {
            speed = -11f;
            y--;
        }
    }

    void moveRight() {
        x = x + 6;
        way = right;
    }

    void moveLeft() {
        x = x - 6;
        way = left;
    }

    Bullet createBullet() {
        if (way == right) {
            return new Bullet(x + 40, y - 24, way, parent);
        } else {
            return new Bullet(x - 40, y - 24, way, parent);
        }
    }

    void shoot() {
        if (isShooting) {
            bullets.add(createBullet());
        }
    }

    Boolean isDead() {
        if (health <= 0) {
            return true;
        }
        return false;
    }

    void initPhysics() {
        BodyDef bd = new BodyDef();
        bd.position.set(box2D.coordPixelsToWorld(x, y));
        bd.type = BodyType.DYNAMIC;
        bd.fixedRotation = true;

        body = box2D.createBody(bd);
        body.setLinearVelocity(new Vec2(0, 0));

        PolygonShape ps = new PolygonShape();
        float size = box2D.scalarPixelsToWorld(100);
        ps.setAsBox(size / 2, size / 2);

        FixtureDef fd = new FixtureDef();
        fd.shape = ps;
        fd.density = 20000;
        fd.restitution = 0;
        fd.friction = 5;
        body.createFixture(fd);
    }

    void updatePhysics() {
        x = box2D.coordWorldToPixels(body.getPosition()).x;
        y = box2D.coordWorldToPixels(body.getPosition()).y;
    }
}
