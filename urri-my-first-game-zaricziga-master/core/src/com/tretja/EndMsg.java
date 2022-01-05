package com.tretja;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EndMsg extends GameObject {
    private SpriteBatch batch;

    public EndMsg(float x, float y, float width, float height) {
        super(x, y, width, height);
        this.font = new BitmapFont();
        this.font.getData().setScale(2);
        this.font.setColor(Color.RED);
        batch = new SpriteBatch();


    }
    public BitmapFont font;
    public void Win(Texture winImage){
        batch.begin();
        batch.draw(winImage,0,0);
        batch.end();
    }
      @Override
public void render(Batch render) {
          this.font.draw(render, "the end",5, 5);
}



    public void Lose(String message) {
        batch.begin();
        font.setColor(Color.RED);
        font.draw(batch, message, Gdx.graphics.getHeight() / 2f, Gdx.graphics.getHeight() / 2f);
        batch.end();
    }
    public void Pause(Texture pauseImage){
        batch.begin();
        batch.draw(pauseImage,0,0);
        batch.end();
    }

}
