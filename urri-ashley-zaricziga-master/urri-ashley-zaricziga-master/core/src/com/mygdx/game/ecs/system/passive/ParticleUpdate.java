package com.mygdx.game.ecs.system.passive;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;


public class ParticleUpdate extends EntitySystem {

    private ParticleSystem particleSystem;
    private final SpriteBatch batch;
    private final Viewport hudViewport;
    private final GlyphLayout layout = new GlyphLayout();

    public ParticleUpdate(SpriteBatch batch, Viewport hudViewport, ParticleSystem particleSystem) {
        this.batch = batch;
        this.hudViewport = hudViewport;
        this.particleSystem = particleSystem;
    }

    @Override
    public void update(float deltaTime) {
        hudViewport.apply();
        batch.setProjectionMatrix(hudViewport.getCamera().combined);
        batch.begin();
        particleSystem.blood().update(Gdx.graphics.getDeltaTime());
        particleSystem.blood().draw(batch);
        particleSystem.up().update(Gdx.graphics.getDeltaTime());
        particleSystem.up().draw(batch);
        batch.end();
    }
}