package com.peta;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class EndMsg extends GameObject {

    public EndMsg(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.font = new BitmapFont();
        this.font.getData().setScale(2);
        this.font.setColor(Color.RED);


    }
    public BitmapFont font;
    public void Win(Texture winImage, Batch render){
        render.begin();
        render.draw(winImage,0,0);
        render.end();
    }
      @Override
public void render(Batch render) {
          this.font.draw(render, "the end",5, 5);
}



    public void Lose(String message,Batch render) {
        render.begin();
        font.setColor(Color.RED);
        font.draw(render, message, Gdx.graphics.getHeight() / 2f, Gdx.graphics.getHeight() / 2f);
        render.end();
    }
    public void Pause(Texture pauseImage, Batch render){
        render.begin();
        render.draw(pauseImage,0,0);
        render.end();
    }

}
