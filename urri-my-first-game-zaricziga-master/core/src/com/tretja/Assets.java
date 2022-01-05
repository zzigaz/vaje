package com.tretja;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Assets {
    public static Texture civilianImage;
    public static Texture talibanImage;
    public static Texture ballImage;
    public static Texture backgroundImage;
    public static Sound talibanSound;
    public static Sound civilianSound;
    public static Texture pauseImage;
    public static Texture winImage;
    public static Texture powerImage;

    public static Texture tyreImage;
    public static BitmapFont font;
    public static Texture soldierImage;

    public static void Load() {

        civilianImage = new Texture(Gdx.files.internal("civilian.png"));
        talibanImage = new Texture(Gdx.files.internal("taliban1.jpg"));
        civilianSound = Gdx.audio.newSound(Gdx.files.internal("health.mp3"));
        talibanSound = Gdx.audio.newSound(Gdx.files.internal("gun.wav"));
        ballImage = new Texture(Gdx.files.internal("zoga.jpg"));
        backgroundImage = new Texture(Gdx.files.internal("background.jpg"));
        winImage = new Texture(Gdx.files.internal("winner.jpg"));
        tyreImage = new Texture(Gdx.files.internal("kolo.jpg"));
        soldierImage = new Texture(Gdx.files.internal("soldier.jpg"));
        pauseImage = new Texture(Gdx.files.internal("pausa.jpg"));
        powerImage = new Texture(Gdx.files.internal("power.jpg"));
        font = new BitmapFont();
        font.getData().setScale(2);
    }
    public static void dispose() {
        civilianImage.dispose();
        talibanImage.dispose();
        civilianSound.dispose();
        talibanSound.dispose();
        ballImage.dispose();
        backgroundImage.dispose();
        winImage.dispose();
        pauseImage.dispose();
        powerImage.dispose();
        tyreImage.dispose();
        font.dispose();

    }
}
