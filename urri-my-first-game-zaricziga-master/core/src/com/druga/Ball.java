package com.druga;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class Ball extends GameObjectDynamic{
    private Texture ballImage;
    public int dir = 1;
    public int timer = 0;
    public Ball(float x, float y, float width, float height, Texture image, int Speed) {
        super(x, y, width, height,Speed);
        this.ballImage = image;
    }
    @Override
    public void update(float deltaTime) {

    }
    public void render(Batch render) {
        render.draw(ballImage, position.x, position.y);
    }


}
