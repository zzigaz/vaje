package com.mygdx.game.common;

import com.badlogic.ashley.core.ComponentMapper;

import com.mygdx.game.ecs.component.PowerUpComponent;
import com.mygdx.game.ecs.component.TalibanComponent;
import com.mygdx.game.ecs.component.CivilianComponent;
import com.mygdx.game.ecs.component.BoundsComponent;
import com.mygdx.game.ecs.component.DimensionComponent;
import com.mygdx.game.ecs.component.MovementComponentXYR;
import com.mygdx.game.ecs.component.PositionComponent;
import com.mygdx.game.ecs.component.SoldierComponent;
import com.mygdx.game.ecs.component.TextureComponent;
import com.mygdx.game.ecs.component.ZOrderComponent;

//TODO Explain how Mappers work (see ComponentMapper and Entity implementation)
public final class Mappers {

    public static final ComponentMapper<TalibanComponent> TALIBAN =
            ComponentMapper.getFor(TalibanComponent.class);

    public static final ComponentMapper<CivilianComponent> CIVILIAN =
            ComponentMapper.getFor(CivilianComponent.class);

    public static final ComponentMapper<PowerUpComponent> POWERUP =
            ComponentMapper.getFor(PowerUpComponent.class);
    public static final ComponentMapper<BoundsComponent> BOUNDS =
            ComponentMapper.getFor(BoundsComponent.class);

    public static final ComponentMapper<DimensionComponent> DIMENSION =
            ComponentMapper.getFor(DimensionComponent.class);

    public static final ComponentMapper<MovementComponentXYR> MOVEMENT =
            ComponentMapper.getFor(MovementComponentXYR.class);

    public static final ComponentMapper<PositionComponent> POSITION =
            ComponentMapper.getFor(PositionComponent.class);

    public static final ComponentMapper<SoldierComponent> SOLDIER =
            ComponentMapper.getFor(SoldierComponent.class);

    public static final ComponentMapper<TextureComponent> TEXTURE =
            ComponentMapper.getFor(TextureComponent.class);

    public static final ComponentMapper<ZOrderComponent> Z_ORDER =
            ComponentMapper.getFor(ZOrderComponent.class);
    private Mappers() {
    }
}
