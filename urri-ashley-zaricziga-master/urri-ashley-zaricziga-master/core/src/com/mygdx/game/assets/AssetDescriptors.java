package com.mygdx.game.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetDescriptors {

    public static final AssetDescriptor<BitmapFont> FONT32 =
            new AssetDescriptor<BitmapFont>(AssetPaths.UI_FONT32, BitmapFont.class);

    public static final AssetDescriptor<TextureAtlas> GAME_PLAY =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GAME_PLAY, TextureAtlas.class);

    public static final AssetDescriptor<Sound> PICK_SOUND =
            new AssetDescriptor<Sound>(AssetPaths.PICK_SOUND, Sound.class);

    public static final AssetDescriptor<Sound> TALIBAN_SOUND =
            new AssetDescriptor<Sound>(AssetPaths.TALIBAN_SOUND, Sound.class);

    //public static final AssetDescriptor<ParticleEffect> PARTICLE_BLOOD =
    //        new AssetDescriptor<ParticleEffect>(AssetPaths.PARTICLE_BLOOD, ParticleEffect.class);

    //public static final AssetDescriptor<ParticleEffect> PARTICLE_UP =
    //        new AssetDescriptor<ParticleEffect>(AssetPaths.PARTICLE_UP, ParticleEffect.class);
    private AssetDescriptors() {
    }
}
