package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.assets.AssetDescriptors;
import com.mygdx.assets.RegionNames;
import com.mygdx.game.Chess;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LeaderboardScreen extends ScreenAdapter {

    private static final Logger log = new Logger(GameScreen.class.getSimpleName(), Logger.DEBUG);

    private final Chess game;
    private final AssetManager assetManager;
    Map<Integer, String> map = new HashMap<Integer, String>();
    private static final String INIT_MOVE_KEY = "initMove";
    private static final String INIT_TIME_KEY = "initTime";
    private static final String INIT_LEADERBOARD_KEY = "initLeaderboard";
    private static final String INIT_LEADERBOARD_INTEGER_KEY = "initLeaderboardInteger";

    private final Preferences PREFS;
    private Viewport viewport;
    private Viewport hudViewport;

    private Stage gameplayStage;
    private Stage hudStage;

    private Skin skin;
    private TextureAtlas gameplayAtlas;
    private Image infoImage;

    public LeaderboardScreen(Chess game) {
        this.game = game;
        PREFS = Gdx.app.getPreferences(Chess.class.getSimpleName());
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(800, 533);
    hudViewport = new FitViewport(800, 533);

        gameplayStage = new Stage(viewport, game.getBatch());
        hudStage = new Stage(hudViewport, game.getBatch());

        skin = assetManager.get(AssetDescriptors.UI_SKIN);
        gameplayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY);

        gameplayStage.addActor(createGrid(11, 1, 1));
        hudStage.addActor(createInfo());
        hudStage.addActor(createBackButton());

        Gdx.input.setInputProcessor(new InputMultiplexer(gameplayStage, hudStage));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(195 / 255f, 195 / 255f, 195 / 255f, 0f);

        // update
        gameplayStage.act(delta);
        hudStage.act(delta);

        // draw
        gameplayStage.draw();
        hudStage.draw();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        gameplayStage.dispose();
        hudStage.dispose();
    }

    private Actor createGrid(int rows, int columns, final float cellSize) {
        final Table table = new Table();
        table.setDebug(false);   // turn on all debug lines (table, cell, and widget)

        final Table grid = new Table();
        grid.defaults().size(cellSize);   // all cells will be the same size
        grid.setDebug(false);

/*

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                final CellActor cell = new CellActor(emptyRegion);
                cell.addListener(new ClickListener() {

                });
                grid.add(cell);
            }
            grid.row();
        }
*/
        table.add(grid).row();
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }

    private Actor createBackButton() {
        final TextButton backButton = new TextButton("Back", skin);
        backButton.setWidth(100);
        backButton.setPosition(backButton.getWidth()-50, 20f);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });
        return backButton;
    }

    private Actor createInfo() {
        final Table table = new Table();
        int i = 0;
        table.add(new Label("TOP 10: ", skin)).padBottom(10).padLeft(350).fillX().row();

        while(true){
            String leader = PREFS.getString(INIT_LEADERBOARD_KEY+i,"");
            if(leader == "")
                break;
            map.put(Integer.valueOf(PREFS.getString(INIT_LEADERBOARD_INTEGER_KEY+i,"")),PREFS.getString(INIT_LEADERBOARD_KEY+i,""));
            i++;

        }
        Map<Integer, String> treeMap = new TreeMap<Integer, String>(
                new Comparator<Integer>() {

                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o2.compareTo(o1);
                    }

                });
        treeMap.putAll(map);
        int count = 1;
        for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {
             table.add(new Label(count+": " + entry.getValue()+"     " + entry.getKey(),skin)).padLeft(390).padBottom(5).fillX().row();
            count++;
        }
        table.pack();
        table.setPosition(
                500 / 2f - table.getWidth() / 2f,
                50
        );
        return table;
    }
}
