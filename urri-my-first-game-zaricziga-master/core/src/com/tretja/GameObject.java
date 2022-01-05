package com.tretja;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class GameObject {
    public  Vector2 position;
    public  Rectangle bounds;

    public GameObject(float x, float y, float width, float height){
        this.position  = new Vector2(x,y);
        this.bounds = new Rectangle(x - width / 2, y - height / 2, width, height);
    }
    public GameObject(){
        this.position = new Vector2(0,0);
        this.bounds = new Rectangle(0 , 0, 0,0);
    }

    public abstract void render(Batch render);
}
