package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.mygdx.assets.AssetDescriptors;
import com.mygdx.assets.RegionNames;
import com.mygdx.game.CellActor;
import com.mygdx.game.CellState;
import com.mygdx.game.Chess;
import com.mygdx.game.Logic.Figures;
import com.mygdx.game.Logic.GameManager;
import com.mygdx.game.SoundClass;
import com.mygdx.game.Time;
import com.mygdx.game.TimeActor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JOptionPane;


public class GameScreen extends ScreenAdapter {

    private static final Logger log = new Logger(GameScreen.class.getSimpleName(), Logger.DEBUG);

    private final Chess game;
    private final AssetManager assetManager;
    private Array<Figures> arraj;
    List<TextureRegion> figure;
    private final Preferences PREFS;

    private Viewport viewport;
    private Viewport hudViewport;
    private String zmagovalecString = "Luka";
    private boolean firstGrid = true;
    private Stage gameplayStage;
    private Stage hudStage;
    private int timer;
    private Skin skin;
    private TextureAtlas gameplayAtlas;
    private Image infoImage;
    private  TimeActor timeActor;
    Label ti;;
    private int zmagovalec;
    private CellState move = GameManager.INSTANCE.getInitMove();
    private Time time = GameManager.INSTANCE.InitTime();
    private SoundClass soundclass = GameManager.INSTANCE.InitSound();

    private static final String INIT_LEADERBOARD_KEY = "initLeaderboard";
    private static final String INIT_LEADERBOARD_INTEGER_KEY = "initLeaderboardInteger";
    Map<Integer, String> map = new HashMap<Integer, String>();
    private String beli = "";
    private String crni = "";
    public GameScreen(Chess game) {
         beli= JOptionPane.showInputDialog("Ime belega igralca: ");

         crni= JOptionPane.showInputDialog("Ime crnega igralca: ");
        this.game = game;
        PREFS = Gdx.app.getPreferences(Chess.class.getSimpleName());
        arraj = new Array<>();
        figure = new ArrayList<>();
        assetManager = game.getAssetManager();
    }

    @Override
    public void show() {
        viewport = new FitViewport(800, 650);
        hudViewport = new FitViewport(800, 650);

        gameplayStage = new Stage(viewport, game.getBatch());
        hudStage = new Stage(hudViewport, game.getBatch());

        skin = assetManager.get(AssetDescriptors.UI_SKIN);
        gameplayAtlas = assetManager.get(AssetDescriptors.GAMEPLAY);

        gameplayStage.addActor(createGrid(8, 8, 55));
        hudStage.addActor(createInfo());
        ti = new Label(String.valueOf(timeActor.getTime()),skin);
        hudStage.addActor(createTime());
        hudStage.addActor(createBackButton());

        Gdx.input.setInputProcessor(new InputMultiplexer(gameplayStage, hudStage));
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }
    int ii = 0;

    @Override
    public void render(float delta) {
        final TextureRegion xRegion = gameplayAtlas.findRegion(RegionNames.WHITE_QUEEN);
        final TextureRegion oRegion = gameplayAtlas.findRegion(RegionNames.BLACK_QUEEN);
        ScreenUtils.clear(195 / 255f, 195 / 255f, 195 / 255f, 0f);
        if(ii == 60){
            timeActor.setTime();
            ti.setText(timeActor.getTime());
            ii = 0;
        }
        ii++;
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
    private Actor createBackButton() {
        final TextButton backButton = new TextButton("Back", skin);
        backButton.setWidth(100);
        backButton.setPosition(800/ 2f - backButton.getWidth() / 2f, -5f);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuScreen(game));
            }
        });
        return backButton;
    }
    final Table table = new Table();
    final Table grid = new Table();
    CellActor kliknjen;
    public void obrni(CellActor clickedCell,TextureRegion xRegion,TextureRegion oRegion){
        switch (move) {
            case WHITE:
                infoImage.setDrawable(new TextureRegionDrawable(oRegion));
                move = CellState.BLACK;
                break;
            case BLACK:

                infoImage.setDrawable(new TextureRegionDrawable(xRegion));
                move = CellState.WHITE;
                break;

        }
    }
    private void createFigures(){
        int s = 0;
        for(int i = 0; i < 64;i++) {
            if (i >= 0 && i < 16) {
                Figures object = new Figures(figure.get(s).toString(),1,i);
                arraj.add(object);
                s++;

            } else if (i >= 48) {
                Figures object = new Figures(figure.get(s).toString(),0,i);
                arraj.add(object);
                s++;
            }

        }
    }
    private Actor createGrid(int rows, int columns, final float cellSize) {
        table.setDebug(false);   // turn on all debug lines (table, cell, and widget)

        grid.defaults().size(cellSize);   // all cells will be the same size
        grid.setDebug(false);

        final TextureRegion emptyRegion = gameplayAtlas.findRegion(RegionNames.CELL_EMPTY);
        final TextureRegion xRegion = gameplayAtlas.findRegion(RegionNames.WHITE_QUEEN);
        final TextureRegion oRegion = gameplayAtlas.findRegion(RegionNames.BLACK_QUEEN);
        if (move == CellState.WHITE) {
            infoImage = new Image(xRegion);
        } else if (move == CellState.BLACK) {
            infoImage = new Image(oRegion);
        }
        if (time == Time.EASY) {
            timeActor = new TimeActor(6000);
        } else if (time == Time.MEDIUM) {
            timeActor = new TimeActor(1200);
        }else if(time == Time.HARD)
            timeActor = new TimeActor(600);


        figure.add(gameplayAtlas.findRegion(RegionNames.BLACK_ROCK));
        figure.add(gameplayAtlas.findRegion(RegionNames.BLACK_HORSE_LEFT));
        figure.add(gameplayAtlas.findRegion(RegionNames.BLACk_BISHOP));
        figure.add(gameplayAtlas.findRegion(RegionNames.BLACK_KING));
        figure.add(gameplayAtlas.findRegion(RegionNames.BLACK_QUEEN));
        figure.add(gameplayAtlas.findRegion(RegionNames.BLACk_BISHOP));
        figure.add(gameplayAtlas.findRegion(RegionNames.BLACK_HORSE_RIGHT));
        figure.add(gameplayAtlas.findRegion(RegionNames.BLACK_ROCK));
        figure.add(gameplayAtlas.findRegion(RegionNames.BLACK_PAWN));
        figure.add(gameplayAtlas.findRegion(RegionNames.BLACK_PAWN));
        figure.add(gameplayAtlas.findRegion(RegionNames.BLACK_PAWN));
        figure.add(gameplayAtlas.findRegion(RegionNames.BLACK_PAWN));
        figure.add(gameplayAtlas.findRegion(RegionNames.BLACK_PAWN));
        figure.add(gameplayAtlas.findRegion(RegionNames.BLACK_PAWN));
        figure.add(gameplayAtlas.findRegion(RegionNames.BLACK_PAWN));
        figure.add(gameplayAtlas.findRegion(RegionNames.BLACK_PAWN));
        figure.add( gameplayAtlas.findRegion(RegionNames.WHITE_PAWN));
        figure.add( gameplayAtlas.findRegion(RegionNames.WHITE_PAWN));
        figure.add( gameplayAtlas.findRegion(RegionNames.WHITE_PAWN));
        figure.add( gameplayAtlas.findRegion(RegionNames.WHITE_PAWN));
        figure.add( gameplayAtlas.findRegion(RegionNames.WHITE_PAWN));
        figure.add( gameplayAtlas.findRegion(RegionNames.WHITE_PAWN));
        figure.add( gameplayAtlas.findRegion(RegionNames.WHITE_PAWN));
        figure.add( gameplayAtlas.findRegion(RegionNames.WHITE_PAWN));
        figure.add(gameplayAtlas.findRegion(RegionNames.WHITE_ROCK));
        figure.add(gameplayAtlas.findRegion(RegionNames.WHITE_HORSE_LEFT));
        figure.add(gameplayAtlas.findRegion(RegionNames.WHITE_BISHOP));
        figure.add(gameplayAtlas.findRegion(RegionNames.WHITE_QUEEN));
        figure.add(gameplayAtlas.findRegion(RegionNames.WHITE_KING));
        figure.add(gameplayAtlas.findRegion(RegionNames.WHITE_BISHOP));
        figure.add(gameplayAtlas.findRegion(RegionNames.WHITE_HORSE_RIGHT));
        figure.add(gameplayAtlas.findRegion(RegionNames.WHITE_ROCK));
        createFigures();
        int i = 0;
        int s = 0;
        int num = 0;
        if(GameManager.INSTANCE.InitSound().name() == "ON"){
            GameManager.INSTANCE.music.stop();
            GameManager.INSTANCE.music2.play();
            GameManager.INSTANCE.music2.setLooping(true);

        }
        if(GameManager.INSTANCE.InitSound().name() == "OFF"){
            GameManager.INSTANCE.music.stop();
            GameManager.INSTANCE.music2.stop();

        }
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                final CellActor cell = new CellActor(emptyRegion,"prazno",num,column,row,emptyRegion.toString());
                num++;
                cell.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        CellActor clickedCell = (CellActor) event.getTarget(); // it will be an image for sure :-)
                        Sound sound = Gdx.audio.newSound(Gdx.files.internal("sounds/pick.wav"));
                        if(kliknjen != null){
                            if(move.equals( CellState.WHITE)){
                                for (Iterator<Figures> it = arraj.iterator(); it.hasNext(); ) {
                                    Figures object = it.next();
                                    if(object.getName().equals(kliknjen.pridobiIme())) {
                                        log.debug(String.valueOf(kliknjen.x));
                                        log.debug(String.valueOf(kliknjen.y));
                                        if (object.Logic(kliknjen, clickedCell) == true) {
                                            //   clickedCell.setState(move);
                                            if(object.getBlackOrWhite() ==0) {
                                                if(clickedCell.getName().equals("black_king3")){
                                                    zmagovalec = timeActor.getTime();
                                                    zmagovalecString = beli;
                                                    checkLeaderBoard();
                                                    log.debug("zmagal bel");
                                                }
                                                sound = Gdx.audio.newSound(Gdx.files.internal("sounds/premik.wav"));
                                                clickedCell.setDrawable(gameplayAtlas.findRegion(kliknjen.getTextureRegion()));
                                                clickedCell.setNewCellName(kliknjen.getName());
                                                clickedCell.setTextureName(kliknjen.getTextureRegion());
                                                kliknjen.setRegion(emptyRegion);
                                                kliknjen.setName("prazno");
                                                kliknjen.setTextureName(emptyRegion.toString());
                                                obrni(clickedCell, xRegion, oRegion);
                                            }

                                        }
                                    }
                                }

                            }else if(move.equals(CellState.BLACK)){
                                for (Iterator<Figures> it = arraj.iterator(); it.hasNext(); ) {
                                    Figures object = it.next();
                                    if (object.getName().equals(kliknjen.pridobiIme())) {

                                        if (object.Logic(kliknjen, clickedCell) == true) {
                                            log.debug("Texture region: " + kliknjen.getTextureRegion());
                                            if (object.getBlackOrWhite() == 1) {
                                                if(clickedCell.getName().equals("white_king60")){
                                                    log.debug("zmagal crn");
                                                    zmagovalec = timeActor.getTime();
                                                    zmagovalecString = crni;
                                                    checkLeaderBoard();
                                                }
                                                sound = Gdx.audio.newSound(Gdx.files.internal("sounds/premik.wav"));
                                                // clickedCell.setState(move);
                                                clickedCell.setDrawable(gameplayAtlas.findRegion(kliknjen.getTextureRegion()));
                                                clickedCell.setNewCellName(kliknjen.getName());
                                                clickedCell.setTextureName(kliknjen.getTextureRegion());
                                                kliknjen.setRegion(emptyRegion);
                                                kliknjen.setName("prazno");
                                                kliknjen.setTextureName(emptyRegion.toString());
                                                obrni(clickedCell, xRegion, oRegion);
                                            }
                                        }
                                    }
                                }

                            }
                            log.debug(gameplayAtlas.findRegion(RegionNames.WHITE_KING).toString());

                            //System.out.println(cell2.toString());
                            kliknjen = null;

                        }else{

                            kliknjen = clickedCell;
                            log.debug(kliknjen.getName());

                        }
                if(soundclass.name() == "ON"){
                    sound.play();
                }


                    }
                });
                if(i >= 0 && i <16) {
                    cell.setDrawable(new TextureRegionDrawable(figure.get(s)));
                    cell.setName(figure.get(s).toString());
                    cell.setTextureName(figure.get(s).toString());

                    s++;

                }else if(i >= 48){
                    cell.setDrawable(new TextureRegionDrawable(figure.get(s)));
                    cell.setName(figure.get(s).toString());
                    cell.setTextureName(figure.get(s).toString());

                    s++;
                }
                i++;
                grid.add(cell);
            }
            grid.row();
        }

        table.add(grid).row();
        table.center();
        table.setFillParent(true);
        table.pack();

        return table;
    }
    private void checkLeaderBoard(){
        int i = 0;
        while(true){
            String leader = PREFS.getString(INIT_LEADERBOARD_KEY+i,"");
            String keyNumber = PREFS.getString(INIT_LEADERBOARD_INTEGER_KEY+i, "");

            if(leader == "")
                break;

            map.put(Integer.parseInt(keyNumber), leader);
            System.out.println(map.size());
            System.out.println(keyNumber + " " + leader);

            i++;

        }
        System.out.println(map.size());
        System.out.println(map.size());
        System.out.println(map.size());

        int ma = 0;
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey()
                    + " Value : " + entry.getValue());
            System.out.println(ma);
            ma++;
        }
        System.out.println("");
        Map<Integer, String> treeMap = new TreeMap<Integer, String>(
                new Comparator<Integer>() {

                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o2.compareTo(o1);
                    }

                });
        treeMap.putAll(map);
        int l = 0;
        Map<Integer, String> tmpMap = new HashMap<Integer, String>();

        for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {

            if(l==9){
                if(entry.getKey() < zmagovalec)
                    tmpMap.put(zmagovalec, zmagovalecString);
            else
                tmpMap.put(entry.getKey(), entry.getValue());
            }
            l++;
        }
        int a = 0;
        for (Map.Entry<Integer, String> entry : tmpMap.entrySet()) {
            PREFS.putString(INIT_LEADERBOARD_INTEGER_KEY+a,String.valueOf(entry.getKey()));
            PREFS.flush();
            PREFS.putString(INIT_LEADERBOARD_KEY+a,String.valueOf(entry.getValue()));
            PREFS.flush();
            a++;
        }
    }
    private Actor createTime(){

        final Table table = new Table();
        table.add(new Label("Time: ", skin));
        table.add( ti);
        table.center();
        table.pack();
        table.setPosition(
                400 / 2f - table.getWidth() / 2f,
                533 - table.getHeight() + 70f
        );
        return table;
    }
    private Actor createInfo() {
        final Table table = new Table();
        table.add(new Label("Turn: ", skin));
        table.add(infoImage).size(30).row();
        table.center();
        table.pack();
        table.setPosition(
                800 / 2f - table.getWidth() / 2f,
                533 - table.getHeight() + 70f
        );
        return table;
    }
}
