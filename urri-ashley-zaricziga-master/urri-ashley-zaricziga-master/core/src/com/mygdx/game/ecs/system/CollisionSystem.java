package com.mygdx.game.ecs.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Intersector;

import com.mygdx.game.common.GameManager;
import com.mygdx.game.common.Mappers;
import com.mygdx.game.config.GameConfig;
import com.mygdx.game.ecs.component.PowerUpComponent;
import com.mygdx.game.ecs.component.TalibanComponent;
import com.mygdx.game.ecs.component.CivilianComponent;
import com.mygdx.game.ecs.component.BoundsComponent;
import com.mygdx.game.ecs.component.SoldierComponent;
import com.mygdx.game.ecs.system.passive.ParticleSystem;
import com.mygdx.game.ecs.system.passive.SoundSystem;

public class CollisionSystem extends EntitySystem {

    private static final Family SOLDIER_FAMILY = Family.all(SoldierComponent.class, BoundsComponent.class).get();
    private static final Family TALIBAN_FAMILY = Family.all(TalibanComponent.class, BoundsComponent.class).get();
    private static final Family CIVILIAN_FAMILY = Family.all(CivilianComponent.class, BoundsComponent.class).get();
    private static final Family POWERUP_FAMILY = Family.all(PowerUpComponent.class, BoundsComponent.class).get();
    long startTime;
    long elapsedTime;
    long elapsedSeconds;
    long secondsDisplay = 1;
    public static ParticleEffect blood;

    private int timer = 0;
    private SoundSystem soundSystem;
    private ParticleSystem particleSystem;
    private int powerNow = 0;

    public CollisionSystem() {
    }

    @Override
    public void addedToEngine(Engine engine) {

        soundSystem = engine.getSystem(SoundSystem.class);
        particleSystem = engine.getSystem(ParticleSystem.class);
    }

    @Override
    public void update(float deltaTime) {
        if (GameManager.INSTANCE.isGameOver()) return;

        ImmutableArray<Entity> soldier = getEngine().getEntitiesFor(SOLDIER_FAMILY);
        ImmutableArray<Entity> taliban = getEngine().getEntitiesFor(TALIBAN_FAMILY);
        ImmutableArray<Entity> civilian = getEngine().getEntitiesFor(CIVILIAN_FAMILY);
        ImmutableArray<Entity> powerUp = getEngine().getEntitiesFor(POWERUP_FAMILY);

        for (Entity solderiEntity : soldier) {
            BoundsComponent soldierBounds = Mappers.BOUNDS.get(solderiEntity);

            // check collision with taliban
            for (Entity talibanEntity : taliban) {
                TalibanComponent talibanBounds = Mappers.TALIBAN.get(talibanEntity);

                if (talibanBounds.hit) {
                    continue;
                }

                BoundsComponent talibanBound = Mappers.BOUNDS.get(talibanEntity);

                if (Intersector.overlaps(soldierBounds.rectangle, talibanBound.rectangle)) {
                    GameManager.INSTANCE.damage();
                    if (timer == 0) {
                        soundSystem.shoot();
                        particleSystem.blood().start();
                        particleSystem.blood().setPosition(soldierBounds.rectangle.x, soldierBounds.rectangle.y);
                        timer++;
                    } else if (timer == 10) {
                        timer = 0;
                    } else {
                        timer++;
                    }
                }
            }
            for (Entity powerUpEntity : powerUp) {
                PowerUpComponent power = Mappers.POWERUP.get(powerUpEntity);

                if (power.hit) {
                    continue;
                }

                BoundsComponent powerUpBounds = Mappers.BOUNDS.get(powerUpEntity);

                if (Intersector.overlaps(soldierBounds.rectangle, powerUpBounds.rectangle)) {
                    GameManager.INSTANCE.health();
                    power.hit = true;
                    GameManager.INSTANCE.incResult();
                    soundSystem.pick();
                    powerNow = 1;
                    GameConfig.SOLDIER_SPEED = 1000f;
                    getEngine().removeEntity(powerUpEntity);
                }
            }
            if(powerNow == 1) {

                System.out.println(elapsedSeconds);
                elapsedSeconds++;

                if (elapsedSeconds == 300) {
                    powerNow = 0;
                    elapsedSeconds = 0;
                    GameConfig.SOLDIER_SPEED = 400f;

                }
            }
            // check collision with civilian
            for (Entity civilianEntity : civilian) {
                CivilianComponent civiliann = Mappers.CIVILIAN.get(civilianEntity);

                if (civiliann.hit) {
                    continue;
                }

                BoundsComponent civilianBounds = Mappers.BOUNDS.get(civilianEntity);

                if (Intersector.overlaps(soldierBounds.rectangle, civilianBounds.rectangle)) {
                    civiliann.hit = true;
                    GameManager.INSTANCE.incResult();
                    soundSystem.pick();
                    particleSystem.up().start();
                    particleSystem.up().setPosition(soldierBounds.rectangle.x, soldierBounds.rectangle.y);
                    getEngine().removeEntity(civilianEntity);
                }
            }
        }
    }
}
