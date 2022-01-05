package com.cetrta;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.TimeUtils;

public class PowerUp extends GameObjectDynamic {
    public long createNextInTime;
    private long createTime;
    private Texture powerUpImage;
    public Sound powerUpSound;
    private int CREATE = 200000000;
    public int id = 3;
    public static final Pool<PowerUp> POOL_POWERUP = Pools.get(PowerUp.class, 50);

    public PowerUp(float x, float y, float width, float height, Texture image, Sound s, int Speed) {
        super(x, y, width, height, Speed);
        this.powerUpImage = image;
        this.createNextInTime = TimeUtils.nanoTime();
        this.powerUpSound = s;
    }


    public PowerUp() {
        super(0.0f, 0.0f, 0.0f, 0.0f, 250);
        this.powerUpImage = Assets.powerImage;
        this.createNextInTime = TimeUtils.nanoTime();
        this.powerUpSound = Assets.civilianSound;
        this.id = 3;
    }

    public boolean isTimeToCreateNew(long last) {
        if (TimeUtils.nanoTime() - last > CREATE) {
            return true;
        } else {
            return false;
        }
    }

    public void setAll(float x, float y, float width, float height, Texture image, Sound s, int speed) {
        this.position.x = x;
        this.position.y = y;
        this.bounds.height = height;
        this.bounds.width = width;
        this.createNextInTime = TimeUtils.nanoTime();
    }

    public int getId() {
        return this.id;
    }

    @Override
    public Texture getImage() {
        return this.powerUpImage;
    }

    @Override
    public Sound playSound() {
        return this.powerUpSound;
    }

    @Override
    public void free() {
        POOL_POWERUP.free(this);
    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(Batch render) {
        render.draw(powerUpImage, position.x, position.y);
    }
}


