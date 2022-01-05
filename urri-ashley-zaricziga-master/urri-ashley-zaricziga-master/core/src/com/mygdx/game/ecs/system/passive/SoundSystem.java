package com.mygdx.game.ecs.system.passive;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

import com.mygdx.game.assets.AssetDescriptors;

public class SoundSystem extends EntitySystem {

    private final AssetManager assetManager;

    private Sound pickSound;
    private Sound talibanSound;

    public SoundSystem(AssetManager assetManager) {
        this.assetManager = assetManager;
        setProcessing(false); // passive system
        init();
    }

    private void init() {
        pickSound = assetManager.get(AssetDescriptors.PICK_SOUND);
        talibanSound = assetManager.get(AssetDescriptors.TALIBAN_SOUND);
    }

    public void pick() {
        pickSound.play();
    }
    public void shoot() {
        talibanSound.play();
    }

}
