package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.assets.RegionNames;
import com.sun.org.apache.bcel.internal.generic.RET;

public class CellActor extends Image {

    private CellState state;
    private String name;
    private String textureName;
    private int cellNumber;
    public int x;
    public int y;
    public CellActor(TextureRegion region, String name, int CellNumber, int x, int y, String textureName) {
        super(region);


        this.name = name;
        this.setName(name+ String.valueOf(this.cellNumber));
        this.cellNumber = CellNumber;
        this.x = x;
        this.y = y;
        this.textureName = textureName;
        state = CellState.EMPTY;

    }
    public int getCellNumber(){
        return this.cellNumber;
    }
    public void setName(String name){
        this.name = name+ String.valueOf(this.cellNumber);
    }
    public void setNewCellName(String name){
        this.name = name;
    }
    public String pridobiIme(){
        return this.name;
    }
    public void setTextureName(String s){
        this.textureName = s;
    }
    public void setRegion(TextureRegion reg){
            this.setDrawable(reg);
    }
    @Override
    public String getName(){
        return this.name;
    }
    public String getTextureRegion(){
        return this.textureName;
    }
    public void setState(CellState state) {
        this.state = state;
    }

    public void setDrawable(TextureRegion region) {
        super.setDrawable(new TextureRegionDrawable(region));
        addAnimation(); // play animation when region changed
    }

    public boolean isEmpty() {
        return state == CellState.EMPTY;
    }

    private void addAnimation() {
        setOrigin(Align.center);
        addAction(
                Actions.sequence(
                        Actions.parallel(
                                Actions.rotateBy(720, 0.25f),
                                Actions.scaleTo(0, 0, 0.25f)
                        ),
                        Actions.scaleTo(1, 1, 0.25f)
                )
        );
    }
}