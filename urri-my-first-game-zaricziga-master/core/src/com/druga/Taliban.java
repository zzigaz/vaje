package com.druga;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.TimeUtils;

public class Taliban extends GameObjectDynamic{
    public long createNextInTime;
    private long createTime;
    private Texture talibanImage;
    public Sound talibanSound;
    private static final long CREATE_TALIBAN_TIME = 2000000000;
    public Taliban(float x, float y, float width, float height, Texture image, Sound s, int Speed) {
        super(x, y, width, height,Speed);
        this.talibanImage = image;
        this.createNextInTime = TimeUtils.nanoTime();
        this.talibanSound = s;
    }
    public boolean isTimeToCreateNew(){
        if (TimeUtils.nanoTime() - this.createNextInTime > CREATE_TALIBAN_TIME)
            return true;
        return false;
    }
    @Override
    public void update(float deltaTime) {

    }
    @Override
    public void render(Batch render) {
        render.draw(talibanImage, position.x, position.y);
    }



}
