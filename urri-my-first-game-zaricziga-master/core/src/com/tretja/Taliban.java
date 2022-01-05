package com.tretja;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.TimeUtils;

public class Taliban extends GameObjectDynamic {
    public long createNextInTime;
    private long createTime;
    private Texture talibanImage;
    public Sound talibanSound;
    private int CREATE = 2000000000;
    public static final Pool<Taliban> POOL_TALIBANS = Pools.get(Taliban.class, 50);
    public Taliban(float x, float y, float width, float height, Texture image, Sound s, int Speed) {
        super(x, y, width, height,Speed);
        this.talibanImage = image;
        this.createNextInTime = TimeUtils.nanoTime();
        this.talibanSound = s;
        setId(0);
    }
    public void setId(int a){
        this.id = a;
    }
    public Taliban(){
        super(0.0f, 0.0f, 0.0f, 0.0f,100);
        this.talibanImage = new Texture(Gdx.files.internal("taliban1.jpg"));
        this.createNextInTime = TimeUtils.nanoTime();
        this.talibanSound =  Gdx.audio.newSound(Gdx.files.internal("gun.wav"));
        this.id = 0;
        setId(0);

    }
    public boolean isTimeToCreateNew(long last){
        if (TimeUtils.nanoTime() - last > CREATE) {
            return true;
        }else {
            return false;
        }
    }
    public void setAll(float x, float y, float width, float height, Texture image, Sound s, int speed){
        this.position.x = x;
        this.position.y = y;
        this.bounds.height = height;
        this.bounds.width = width;
        this.createNextInTime = TimeUtils.nanoTime();
    }
    public int getId(){
        return this.id;
    }
    @Override
    public Texture getImage() {
        return this.talibanImage;
    }

    @Override
    public Sound playSound() {
        return this.talibanSound;
    }

    @Override
    public void free(){
        POOL_TALIBANS.free(this);
    }

    @Override
    public void update(float deltaTime) {

    }
    @Override
    public void render(Batch render) {
        render.draw(talibanImage, position.x, position.y);
    }



}
