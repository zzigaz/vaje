package com.mygdx.game.ecs.system.passive;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;

import com.mygdx.game.assets.AssetDescriptors;
import com.mygdx.game.assets.RegionNames;
import com.mygdx.game.config.GameConfig;
import com.mygdx.game.ecs.component.PowerUpComponent;
import com.mygdx.game.ecs.component.TalibanComponent;
import com.mygdx.game.ecs.component.CivilianComponent;
import com.mygdx.game.ecs.component.BoundsComponent;
import com.mygdx.game.ecs.component.CleanUpComponent;
import com.mygdx.game.ecs.component.DimensionComponent;
import com.mygdx.game.ecs.component.MovementComponentXYR;
import com.mygdx.game.ecs.component.PositionComponent;
import com.mygdx.game.ecs.component.SoldierComponent;
import com.mygdx.game.ecs.component.TextureComponent;
import com.mygdx.game.ecs.component.WorldWrapComponent;
import com.mygdx.game.ecs.component.ZOrderComponent;

public class EntityFactorySystem extends EntitySystem {

    private static final int BACK_GROUND_Z_ORDER = 0;
    private static final int TALIBAN_Z_ORDER = 1;
    private static final int CIVILIAN_Z_ORDER = 2;
    private static final int SOLDIER_Z_ORDER = 3;
    private static final int POWERUP_Z_ORDER = 4;


    private final AssetManager assetManager;

    private PooledEngine engine;
    private TextureAtlas gamePlayAtlas;

    public EntityFactorySystem(AssetManager assetManager) {
        this.assetManager = assetManager;
        setProcessing(false);   // passive system
        init();
    }

    private void init() {
        gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
    }

    @Override
    public void addedToEngine(Engine engine) {
        this.engine = (PooledEngine) engine;
    }

    public void createSoldier() {
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = GameConfig.WIDTH / 2f - GameConfig.SOLDIER_WIDTH / 2f;
        position.y = 20;

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.SOLDIER_WIDTH;
        dimension.height = GameConfig.SOLDIER_HEIGHT;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x, position.y);
        bounds.rectangle.setSize(dimension.width, dimension.height);

        MovementComponentXYR movement = engine.createComponent(MovementComponentXYR.class);

        SoldierComponent soldier = engine.createComponent(SoldierComponent.class);

        WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = gamePlayAtlas.findRegion(RegionNames.SOLDIER);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = SOLDIER_Z_ORDER;

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(movement);
        entity.add(soldier);
        entity.add(worldWrap);
        entity.add(texture);
        entity.add(zOrder);
        engine.addEntity(entity);
    }

    public void createTaliban() {
        PositionComponent position = engine.createComponent(PositionComponent.class);

        position.x = MathUtils.random(0, GameConfig.WIDTH - GameConfig.TALIBAN_WIDTH);
        position.y = GameConfig.HEIGHT;

        MovementComponentXYR movementComponent = engine.createComponent(MovementComponentXYR.class);
        movementComponent.xSpeed = -GameConfig.TALIBAN_SPEED_X_MIN * MathUtils.random(-1f, 1f);
        movementComponent.ySpeed = -GameConfig.TALIBAN_SPEED_X_MIN * MathUtils.random(1f, 2f);
        movementComponent.rSpeed = MathUtils.random(-1f, 1f);

        float randFactor = MathUtils.random(1f, 4f);
        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.TALIBAN_WIDTH * randFactor;
        dimension.height = GameConfig.TALIBAN_HEIGHT * randFactor;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x, position.y);
        bounds.rectangle.setSize(dimension.width, dimension.height);

        TalibanComponent talibanComponent = engine.createComponent(TalibanComponent.class);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = gamePlayAtlas.findRegion(RegionNames.TALIBAN);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = TALIBAN_Z_ORDER;

         //WorldWrapComponent worldWrap = engine.createComponent(WorldWrapComponent.class);

        CleanUpComponent cleanUp = engine.createComponent(CleanUpComponent.class);

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(movementComponent);
        entity.add(talibanComponent);
        entity.add(texture);
        entity.add(zOrder);
        //entity.add(worldWrap);
        entity.add(cleanUp);
        engine.addEntity(entity);
    }
    public void createBackground(){
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = 0;
        position.y = 0;

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.WIDTH;
        dimension.height = GameConfig.HEIGHT;




        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = gamePlayAtlas.findRegion(RegionNames.BACK_GROUND);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = BACK_GROUND_Z_ORDER;

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(texture);
        entity.add(zOrder);
        engine.addEntity(entity);
    }
    public void createCivilian() {
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = MathUtils.random(0, GameConfig.WIDTH - GameConfig.CIVILIAN_SIZE);
        position.y = GameConfig.HEIGHT;

        MovementComponentXYR movementComponent = engine.createComponent(MovementComponentXYR.class);
        movementComponent.xSpeed = GameConfig.TALIBAN_SPEED_X_MIN * MathUtils.random(-0.1f, 0.1f);
        movementComponent.ySpeed = -GameConfig.TALIBAN_SPEED_X_MIN * MathUtils.random(1f, 2f);
        movementComponent.rSpeed = MathUtils.random(-1f, 1f);

        CivilianComponent civilian = engine.createComponent(CivilianComponent.class);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.CIVILIAN_SIZE;
        dimension.height = GameConfig.CIVILIAN_SIZE;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x, position.y);
        bounds.rectangle.setSize(dimension.width, dimension.height);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = gamePlayAtlas.findRegion(RegionNames.CIVILIAN);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = CIVILIAN_Z_ORDER;

        CleanUpComponent cleanUp = engine.createComponent(CleanUpComponent.class);

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(movementComponent);
        entity.add(civilian);
        entity.add(texture);
        entity.add(zOrder);
        entity.add(cleanUp);
        engine.addEntity(entity);
    }

    public void createPowerUp() {
        PositionComponent position = engine.createComponent(PositionComponent.class);
        position.x = MathUtils.random(0, GameConfig.WIDTH - GameConfig.POWER_UP_SIZE);
        position.y = GameConfig.HEIGHT;

        MovementComponentXYR movementComponent = engine.createComponent(MovementComponentXYR.class);
        movementComponent.xSpeed = GameConfig.TALIBAN_SPEED_X_MIN * MathUtils.random(-0.1f, 0.1f);
        movementComponent.ySpeed = -GameConfig.TALIBAN_SPEED_X_MIN * MathUtils.random(1f, 2f);
        movementComponent.rSpeed = MathUtils.random(-1f, 1f);

        PowerUpComponent powerUp = engine.createComponent(PowerUpComponent.class);

        DimensionComponent dimension = engine.createComponent(DimensionComponent.class);
        dimension.width = GameConfig.POWER_UP_SIZE;
        dimension.height = GameConfig.POWER_UP_SIZE;

        BoundsComponent bounds = engine.createComponent(BoundsComponent.class);
        bounds.rectangle.setPosition(position.x, position.y);
        bounds.rectangle.setSize(dimension.width, dimension.height);

        TextureComponent texture = engine.createComponent(TextureComponent.class);
        texture.region = gamePlayAtlas.findRegion(RegionNames.POWER_UP);

        ZOrderComponent zOrder = engine.createComponent(ZOrderComponent.class);
        zOrder.z = POWERUP_Z_ORDER;

        CleanUpComponent cleanUp = engine.createComponent(CleanUpComponent.class);

        Entity entity = engine.createEntity();
        entity.add(position);
        entity.add(dimension);
        entity.add(bounds);
        entity.add(movementComponent);
        entity.add(powerUp);
        entity.add(texture);
        entity.add(zOrder);
        entity.add(cleanUp);
        engine.addEntity(entity);
    }
}
