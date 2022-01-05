package com.druga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector3;

public class Soldier extends GameObjectDynamic {
    private static final int SPEED = 600;
    private Texture soldierImage;

    public Soldier(float x, float y, float width, float height, Texture image, int speed) {
        super(x, y, width, height, speed);
        this.soldierImage = image;

    }
    public void commandMoverRight(){
        this.position.x += SPEED * Gdx.graphics.getDeltaTime();
        if ( this.position.x > Gdx.graphics.getWidth() - soldierImage.getWidth())
            this.position.x = Gdx.graphics.getWidth() - soldierImage.getWidth();
    }
    public void commandMoveLeft() {
        this.position.x -= SPEED * Gdx.graphics.getDeltaTime();
        if (this.position.x < 0) this.position.x = 0;
    }
    public void commandMoveUp(){
       this.position.y += SPEED * Gdx.graphics.getDeltaTime();
        if(this.position.y > Gdx.graphics.getHeight() - soldierImage.getHeight())
           this.position.y = Gdx.graphics.getHeight() - soldierImage.getHeight();
    }
    public void commandMoveDown(){
       this.position.y -= SPEED * Gdx.graphics.getDeltaTime();
        if(this.position.y < 0)this.position.y = 0;
    }

    public void commandMoveLeftCorner() {
        this.position.x = 0;
    }

    public void commandMoveRightCorner() {
        this.position.x = Gdx.graphics.getWidth() - soldierImage.getWidth();
    }
    @Override
    public void update(float deltaTime) {

    }
    public void render(Batch render) {
        render.draw(soldierImage, position.x, position.y);

    }



}
