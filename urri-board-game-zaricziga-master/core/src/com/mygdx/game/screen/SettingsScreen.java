package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.assets.AssetDescriptors;
import com.mygdx.assets.RegionNames;
import com.mygdx.game.CellState;
import com.mygdx.game.Chess;
import com.mygdx.game.Logic.GameManager;
import com.mygdx.game.SoundClass;
import com.mygdx.game.Time;

public class SettingsScreen extends ScreenAdapter {
    private final Chess game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;

    private ButtonGroup<CheckBox> checkBoxGroup;
    private ButtonGroup<CheckBox> checkBoxGroup2;
    private ButtonGroup<CheckBox> checkBoxGroup3;
    private CheckBox checkBoxON;
    private CheckBox checkBoxOFF;

    private CheckBox checkBoxE;
    private CheckBox checkBoxM;
    private CheckBox checkBoxH;
    private CheckBox checkBoxX;
    private CheckBox checkBoxO;
    public SettingsScreen(Chess game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(800, 533);
        stage = new Stage(viewport, game.getBatch());

        stage.addActor(createUi());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 0f);

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

    private Actor createUi() {
        Table table = new Table();
        table.defaults().pad(20);

        Skin uiSkin = assetManager.get(AssetDescriptors.UI_SKIN);
        TextureAtlas gameplayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY);

        TextureRegion backgroundRegion = gameplayAtlas.findRegion(RegionNames.BACKGROUND);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        ChangeListener listener = new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                CheckBox checked = checkBoxGroup.getChecked();
                if (checked == checkBoxE) {
                    GameManager.INSTANCE.setTimeState(Time.EASY);
                } else if (checked == checkBoxM) {
                    GameManager.INSTANCE.setTimeState(Time.MEDIUM);
                }else if(checked == checkBoxH){
                    GameManager.INSTANCE.setTimeState(Time.HARD);
                }

                CheckBox checked2 = checkBoxGroup2.getChecked();
                if (checked2 == checkBoxX) {
                    GameManager.INSTANCE.setInitMove(CellState.WHITE);
                } else if (checked2 == checkBoxO) {
                    GameManager.INSTANCE.setInitMove(CellState.BLACK);
                }

                CheckBox checked3 = checkBoxGroup3.getChecked();
                if (checked3 == checkBoxON) {
                    GameManager.INSTANCE.setSound(SoundClass.ON);
                } else if (checked3 == checkBoxOFF) {
                    GameManager.INSTANCE.setSound(SoundClass.OFF);
                }

            }
        };

        checkBoxE = new CheckBox(Time.EASY.name(), uiSkin);
        checkBoxM = new CheckBox(Time.MEDIUM.name(), uiSkin);
        checkBoxH = new CheckBox(Time.HARD.name(), uiSkin);
        checkBoxX = new CheckBox(CellState.WHITE.name(), uiSkin);
        checkBoxO = new CheckBox(CellState.BLACK.name(), uiSkin);

        checkBoxON = new CheckBox(SoundClass.ON.name(), uiSkin);
        checkBoxOFF = new CheckBox(SoundClass.OFF.name(), uiSkin);

        checkBoxE.addListener(listener);
        checkBoxM.addListener(listener);
        checkBoxH.addListener(listener);
        checkBoxX.addListener(listener);
        checkBoxO.addListener(listener);
        checkBoxOFF.addListener(listener);
        checkBoxON.addListener(listener);
        checkBoxGroup = new ButtonGroup<>(checkBoxE, checkBoxM,checkBoxH);
        checkBoxGroup.setChecked(GameManager.INSTANCE.getInitMove().name()); //INSTANCE.getInitMove().name());

        checkBoxGroup2 = new ButtonGroup<>(checkBoxX, checkBoxO);
        checkBoxGroup2.setChecked(CellState.O.name());

        checkBoxGroup3 = new ButtonGroup<>(checkBoxON, checkBoxOFF);
        checkBoxGroup3.setChecked(SoundClass.ON.name());

        TextButton backButton = new TextButton("Back", uiSkin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });

        Table contentTable = new Table(uiSkin);

        TextureRegion menuBackground = gameplayAtlas.findRegion(RegionNames.MENU_BACKGROUND);
        contentTable.setBackground(new TextureRegionDrawable(menuBackground));

        contentTable.add(new Label("Settings", uiSkin)).padBottom(50).colspan(2).row();
        contentTable.add(new Label("Choose game difficulty:", uiSkin)).colspan(2).row();
        contentTable.add(checkBoxE);
        contentTable.add(checkBoxM).row();
        contentTable.add(checkBoxH).row();
        contentTable.add(new Label("Choose starter:", uiSkin)).colspan(2).row();
        contentTable.add(checkBoxX).row();
        contentTable.add(checkBoxO).row();
        contentTable.add(checkBoxON).row();
        contentTable.add(checkBoxOFF).row();
        contentTable.add(backButton).width(100).padTop(50).colspan(2);

        table.add(contentTable);
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }
}
