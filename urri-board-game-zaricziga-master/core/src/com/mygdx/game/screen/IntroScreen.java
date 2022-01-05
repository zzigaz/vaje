package com.mygdx.game.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.mygdx.assets.AssetDescriptors;
import com.mygdx.assets.RegionNames;
import com.mygdx.game.Chess;

public class IntroScreen extends ScreenAdapter {

    public static final float INTRO_DURATION_IN_SEC = 0f;   // duration of the (intro) animation

    private final Chess game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private TextureAtlas gameplayAtlas;

    private float duration = 0f;

    private Stage stage;
    private Image queen2;

    public IntroScreen(Chess game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(800, 533);
        stage = new Stage(viewport, game.getBatch());

        // load assets
        assetManager.load(AssetDescriptors.UI_FONT);
        assetManager.load(AssetDescriptors.UI_SKIN);
        assetManager.load(AssetDescriptors.GAMEPLAY);
        assetManager.finishLoading();   // blocks until all assets are loaded

        gameplayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY);
        queen2 = new Image(gameplayAtlas.findRegion(RegionNames.ANIMATION_2));
        stage.addActor(createBlackQueen());
        stage.addActor(createAnimation());

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(65 / 255f, 159 / 255f, 221 / 255f, 0f);

        duration += delta;

        // go to the MenuScreen after INTRO_DURATION_IN_SEC seconds
        if (duration > INTRO_DURATION_IN_SEC) {
            game.setScreen(new MenuScreen(game));
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    private Actor createBlackQueen() {
        // position the image to the center of the window
        queen2.setPosition(viewport.getWorldWidth() / 2f - queen2.getWidth() / 2f,
                viewport.getWorldHeight() / 2f - queen2.getHeight() / 2f);
        return queen2;
    }


    private Actor createAnimation() {
        Image queen = new Image(gameplayAtlas.findRegion(RegionNames.ANIMATION_1));

        // set positions x, y to center the image to the center of the window
        float posX = (viewport.getWorldWidth() / 2f) - queen.getWidth() - queen2.getWidth() / 2f;
        float posY = (viewport.getWorldHeight() / 2f) - queen.getHeight() / 2f;

        queen.setOrigin(Align.center);
        queen.setPosition(0,350/2f);
        queen.addAction(

                Actions.sequence(
                        Actions.parallel(
                                Actions.rotateBy(1080, 1.5f),   // rotate the image three times
                                Actions.moveTo(posX, posY, 1.5f)   // // move image to the center of the window
                        ),

                        Actions.rotateBy(75, 0.3f),  // rotate the image for 360 degrees to the left side
                        Actions.rotateBy(-75, 0.4f),  // rotate the image for 360 degrees to the left side
                        Actions.moveTo(posX+queen2.getWidth(), posY, 1.5f)   // // move image to the center of the window

                )

        );
        queen2.addAction(
                Actions.sequence(
                Actions.rotateBy(0,2.0f),
                        Actions.rotateBy(-120,1.2f),

                        Actions.removeActor()
                )
        );
        return queen;
    }

}
