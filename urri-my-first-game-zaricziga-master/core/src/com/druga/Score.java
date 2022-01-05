package com.druga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Score extends GameObject{
    private int rescuedScore;
    private int soldierHealth;
    public BitmapFont font;

    public Score(int rescuedScore, int soldierHealth) {
        this.rescuedScore = rescuedScore;
        this.soldierHealth = soldierHealth;
        font = new BitmapFont();
        font.getData().setScale(2);


    }

    public int getSoldierHealth() {
        return soldierHealth;
    }

    public void setSoldierHealth(int soldierHealth) {
        this.soldierHealth = soldierHealth;
    }

    public int getRescuedScore() {
        return rescuedScore;
    }

    public void setRescuedScore(int rescuedScore) {
        this.rescuedScore = rescuedScore;
    }

    public boolean isEnd(){
        if(this.soldierHealth == 0 || this.soldierHealth < 0){
            return false;
        }else{
            return true;
        }
    }
    @Override
    public void render(Batch render) {
        font.setColor(Color.YELLOW);
        font.draw(render, "" + getRescuedScore(), Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 20);
        font.setColor(Color.GREEN);
        font.draw(render, "" + getSoldierHealth(), 20, Gdx.graphics.getHeight() - 20);
    }



}
