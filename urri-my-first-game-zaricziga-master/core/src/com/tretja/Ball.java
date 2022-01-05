package com.tretja;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.TimeUtils;

public class Ball extends GameObjectDynamic implements Pool.Poolable{
    public static final Pool<Ball> POOL_BALLS = Pools.get(Ball.class, 50);

    private Texture ballImage;
    public int dir = 1;
    public int timer = 0;
    public int id = 2;
    public Ball(float x, float y, float width, float height, Texture image, int Speed) {
        super(x, y, width, height,Speed);
        this.ballImage = image;
    }
    public Ball() {
        super(0.0f,0.0f, 0.0f, 0.0f,200);
        this.ballImage = new Texture(Gdx.files.internal("zoga.jpg"));
        this.createNextInTime = TimeUtils.nanoTime();
    }

    public void setAll(float x, float y, float width, float height){
        //  super(x, y, width, height,speed);
        this.position.x = x;
        this.position.y = y;
        this.bounds.height = height;
        this.bounds.width = width;
        this.createNextInTime = TimeUtils.nanoTime();
    }
    @Override
    public void update(float deltaTime) {

    }

    public int getId(){
        return this.id;
    }

    @Override
    public boolean isTimeToCreateNew(long last) {
        return false;
    }

    @Override
    public Texture getImage() {
        return null;
    }

    @Override
    public Sound playSound() {
        return null;
    }



    public void free() {
        POOL_BALLS.free(this);
    }

    public void render(Batch render) {
        render.draw(ballImage, position.x, position.y);
    }


    @Override
    public void reset() {
        POOL_BALLS.clear();
    }
}
