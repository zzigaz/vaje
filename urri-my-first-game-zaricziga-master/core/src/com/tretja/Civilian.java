package com.tretja;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.TimeUtils;

public class Civilian extends GameObjectDynamic implements Pool.Poolable {
    public long createNextInTime;
    public Sound civilianSound;
    private float rotate;
    private float rotateSpeed;
    private boolean rescued;
    private  Texture civilianImage;
    private int CREATE = 700000000;
    public int id = 1;
    public static final Pool<Civilian> POOL_CIVILIANS = Pools.get(Civilian.class, 50);

    public Civilian(float x, float y, float width, float height, Texture image, Sound s, int speed) {
        super(x, y, width, height,speed);
        this.civilianImage = new Texture(Gdx.files.internal("civilian.png"));
        this.createNextInTime = TimeUtils.nanoTime();
        this.civilianSound = Gdx.audio.newSound(Gdx.files.internal("health.mp3"));
    }
    public Civilian() {
        super(0.0f,0.0f, 0.0f, 0.0f,200);
        this.civilianImage = new Texture(Gdx.files.internal("civilian.png"));
        this.createNextInTime = TimeUtils.nanoTime();
        this.civilianSound = Gdx.audio.newSound(Gdx.files.internal("health.mp3"));
    }
    public void setAll(float x, float y, float width, float height, Texture image, Sound s, int speed){
        //  super(x, y, width, height,speed);
        this.position.x = x;
        this.position.y = y;
        this.bounds.height = height;
        this.bounds.width = width;
        this.createNextInTime = TimeUtils.nanoTime();
    }
    public Texture getCivilianImage(){
        return this.civilianImage;
    }

    public int getId(){
        return this.id;
    }
    @Override
    public void update(float deltaTime) {
        rotate -= deltaTime * 300;
        if(rotate>360) rotate -= 360;
        if(rotate<-360) rotate += 360;

    }
    public boolean isTimeToCreateNew(long last){
        if (TimeUtils.nanoTime() - last > CREATE) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Texture getImage() {
        return this.civilianImage;
    }

    @Override
    public Sound playSound() {
        return this.civilianSound;
    }

    @Override
    public void render(Batch render) {
        render.draw(civilianImage, position.x, position.y, bounds.width/2, bounds.height/2, bounds.width,bounds.height, 1, 1, rotate,0,0, (int)bounds.width, (int)bounds.height,false,false);
    }


    @Override
    public void reset() {
    }
    public void free(){
        POOL_CIVILIANS.free(this);
    }




}