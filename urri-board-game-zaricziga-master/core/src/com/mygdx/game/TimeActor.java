package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class TimeActor extends Image {
    private int time;
    public TimeActor(int i){
        this.time = i;
    }

    public void setTime(){
        this.time = this.time -1;
    }
    public int getTime(){
        return this.time;
    }
}
