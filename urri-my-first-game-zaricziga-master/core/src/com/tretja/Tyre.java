package com.tretja;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Tyre extends GameObjectDynamic {
    private Texture tyreImage;
    private Sound tyreSound;
    public float rotateSpeed;
    public float rotate;
    public int speed;
    public int dir;
    public Tyre(float x, float y, float width, float height, Texture image, Sound s, int speed) {
        super(x, y, width, height,speed);
        this.tyreImage = image;
        this.tyreSound = Assets.civilianSound;
        this.speed = speed;
    }

    @Override
    public void update(float deltaTime) {
        if(dir == 0) {
            rotate += deltaTime * 300;
        }else{
            rotate -= deltaTime * 300;

        }
        if(rotate>360) rotate -= 360;
        if(rotate<-360) rotate += 360;

    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public boolean isTimeToCreateNew(long last) {
        return false;
    }

    public void setId(int a) {

    }

    @Override
    public Texture getImage() {
        return null;
    }

    @Override
    public Sound playSound() {
        return null;
    }


    public void reset() {
        free();
    }

    public void free() {

    }

    @Override
    public void render(Batch render) {
        render.draw(tyreImage, position.x, position.y, bounds.width/2, bounds.height/2, bounds.width,bounds.height, 1, 1, rotate,0,0, (int)bounds.width, (int)bounds.height,false,false);

    }
    public void setVelocityX(){
        position.x -= this.speed * Gdx.graphics.getDeltaTime();;
    }
    public void setVelocityXplus(){
        position.x += this.speed * Gdx.graphics.getDeltaTime();;
    }

}
