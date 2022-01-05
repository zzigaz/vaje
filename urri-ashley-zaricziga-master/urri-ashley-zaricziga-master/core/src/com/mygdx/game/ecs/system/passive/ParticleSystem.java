package com.mygdx.game.ecs.system.passive;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.mygdx.game.assets.AssetDescriptors;

public class ParticleSystem extends EntitySystem {

    private final AssetManager assetManager;

    private static ParticleEffect blood;
    private static ParticleEffect up;

    public ParticleSystem(AssetManager assetManager) {
        this.assetManager = assetManager;
        setProcessing(false); // passive system
        init();
    }

    private void init() {
        blood = new ParticleEffect();
        up = new ParticleEffect();
        up.load(Gdx.files.internal("particles/up.pe"), Gdx.files.internal(""));
        blood.load(Gdx.files.internal("particles/blood.pe"), Gdx.files.internal("particles"));
    }

    public ParticleEffect blood() {
        return blood;
    }

    public ParticleEffect up() {
        return up;
    }

}
