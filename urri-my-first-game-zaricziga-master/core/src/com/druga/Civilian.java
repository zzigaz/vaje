package com.druga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

public class Civilian extends GameObjectDynamic{
    public final long createNextInTime;
    public Sound civilianSound;
    private float rotate;
    private float rotateSpeed;
    private boolean rescued;
    private final Texture civilianImage;
    private static final long CREATE_CIVILIAN_TIME = 1000000000;
    public Civilian(float x, float y, float width, float height, Texture image, Sound s, int speed) {
        super(x, y, width, height,speed);
        this.civilianImage = new Texture(Gdx.files.internal("civilian.png"));
        this.createNextInTime = TimeUtils.nanoTime();
        this.civilianSound = Gdx.audio.newSound(Gdx.files.internal("health.mp3"));
    }


    public Texture getCivilianImage(){
        return this.civilianImage;
    }


    @Override
    public void update(float deltaTime) {
        rotate -= deltaTime * 300;
        if(rotate>360) rotate -= 360;
        if(rotate<-360) rotate += 360;

    }
    public boolean isTimeToCreateNew(){
        if (TimeUtils.nanoTime() - this.createNextInTime > CREATE_CIVILIAN_TIME)
            return true;
        return false;
    }

    @Override
    public void render(Batch render) {
        render.draw(civilianImage, position.x, position.y, bounds.width/2, bounds.height/2, bounds.width,bounds.height, 1, 1, rotate,0,0, (int)bounds.width, (int)bounds.height,false,false);
    }


}
