package com.cetrta;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObjectDynamic extends GameObject {
    public int CREATE;
    public Vector2 velocity;
    public final Vector2 accel;
    public long createNextInTime;
    private int speed;
    public int id;
    public int dir;
    public int timer;
    public GameObjectDynamic (float x, float y, float width, float height, int speed) {
        super(x, y, width, height);
        velocity = new Vector2();
        velocity.y = speed;
        this.speed = speed;
        accel = new Vector2();
    }
    public void setSpeed(int speed1){
        this.speed = speed1;
    }
    public int getSpeed(){
        return this.speed;
    }
    public void setVelocityY(){
        position.y -= this.speed * Gdx.graphics.getDeltaTime();
    }
    public abstract void update(float deltaTime);
    public abstract int getId();
    public abstract boolean isTimeToCreateNew(long last);

    public abstract Texture getImage();
    public abstract Sound playSound();
    public abstract void free();
}
