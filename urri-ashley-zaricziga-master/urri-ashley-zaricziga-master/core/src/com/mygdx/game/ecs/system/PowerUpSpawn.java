package com.mygdx.game.ecs.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.systems.IntervalSystem;
import com.mygdx.game.config.GameConfig;
import com.mygdx.game.ecs.system.passive.EntityFactorySystem;


public class PowerUpSpawn extends IntervalSystem {
    private EntityFactorySystem factory;

    public PowerUpSpawn() {
        super(GameConfig.POWERUP_SPAWN_TIME);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        factory = engine.getSystem(EntityFactorySystem.class);
    }

    @Override
    protected void updateInterval() {
        factory.createPowerUp();
    }
}
