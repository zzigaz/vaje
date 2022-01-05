package com.cetrtaDebug;

//
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Iterator;

import com.cetrtaDebug.util.debug.DebugCameraController;
import com.cetrtaDebug.util.ViewportUtils;

//dt-deltaTime
//dv = -g*ds
//velocity+= -g*deltatime
public class MyGdxGame4D extends ApplicationAdapter {
    private Texture civilianImage;
    private Texture soldierImage;
    private Texture talibanImage;
    private Texture winImage;
    private Texture backgroundImage;
    private Sound civilianSound;
    private Sound healthSound;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Rectangle soldier;
    private Array<Rectangle> civilians;
    private Array<Rectangle> talibans;
    private long lastCivilianTime;
    private long lastTalibanTime;
    private int civiliansRescuePoints;
    private int soldierHealth;
    private int REMOVE_CIVILIAN = 0;// ns
    private int REMOVE_TALIBAN = 0;// ns
    private int timer = 0;
    public BitmapFont font;

    //Debug spremenljivke
    private Viewport viewport;
    private Viewport hudViewport;
    private DebugCameraController debugCameraController;
    private boolean debug = false;
    private ShapeRenderer renderer;

    // world units
    private static final float WORLD_WIDTH = 600f;
    private static final float WORLD_HEIGHT = 300f;


    private static final int SPEED = 600;
    private static final long CREATE_CIVILIAN_TIME = 1000000000;
    private static final int SPEED_CIVILIAN = 200;
    private static int SPEED_TALIBAN = 100;
    private static final long CREATE_TALIBAN_TIME = 2000000000;

    @Override
    public void create() {
        font = new BitmapFont();
        font.getData().setScale(2);
        civiliansRescuePoints = 0;
        soldierHealth = 100;

        soldierImage = new Texture(Gdx.files.internal("soldier.jpg"));
        civilianImage = new Texture(Gdx.files.internal("civilian.png"));
        talibanImage = new Texture(Gdx.files.internal("taliban1.jpg"));
        civilianSound = Gdx.audio.newSound(Gdx.files.internal("gun.wav"));
        healthSound = Gdx.audio.newSound(Gdx.files.internal("health.mp3"));
        backgroundImage = new Texture(Gdx.files.internal("background.jpg"));
        winImage = new Texture(Gdx.files.internal("winner.jpg"));
        camera = new OrthographicCamera();
        batch = new SpriteBatch();

        soldier = new Rectangle();
        soldier.x = Gdx.graphics.getWidth() / 2f - soldierImage.getWidth() / 2f;    // center the rocket horizontally
        soldier.y = 20;
        soldier.width = soldierImage.getWidth();
        soldier.height = soldierImage.getHeight();

        civilians = new Array<Rectangle>();
        talibans = new Array<Rectangle>();
        //Debug
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        hudViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f);
        renderer = new ShapeRenderer();

        spawnCivilian();
        spawnTaliban();
    }

    /**
     * Runs every frame.
     */
    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)) debug = !debug;

        if (debug) {
            debugCameraController.handleDebugInput(Gdx.graphics.getDeltaTime());
            debugCameraController.applyTo(camera);
        }
        hudViewport.apply();

        if (Gdx.input.isTouched()) commandTouched();    // mouse or touch screen
        if (Gdx.input.isKeyPressed(Keys.LEFT)) commandMoveLeft();
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) commandMoveRight();
        if (Gdx.input.isKeyPressed(Keys.A)) commandMoveLeftCorner();
        if (Gdx.input.isKeyPressed(Keys.S)) commandMoveRightCorner();
        if (Gdx.input.isKeyPressed(Keys.ESCAPE)) commandExitGame();
        if(Gdx.input.isKeyPressed(Keys.UP)) commandMoveUp();
        if (Gdx.input.isKeyPressed(Keys.DOWN)) commandMoveDown();

        // check if we need to create a new astronaut/asteroid
        if (TimeUtils.nanoTime() - lastCivilianTime > CREATE_CIVILIAN_TIME) spawnCivilian();
        if (TimeUtils.nanoTime() - lastTalibanTime > CREATE_TALIBAN_TIME) spawnTaliban();

        if (soldierHealth > 0 && civiliansRescuePoints < 2000) {

            for (Iterator<Rectangle> it = talibans.iterator(); it.hasNext(); ) {
                Rectangle taliban = it.next();
                taliban.y -= SPEED_TALIBAN * Gdx.graphics.getDeltaTime();
                if (taliban.overlaps(soldier)) {
                    if(timer == 0){
                        civilianSound.play();
                        timer++;
                    }else if(timer == 10){
                        timer = 0;
                    }else {
                        timer++;
                    }
                    soldierHealth--;
                }
            }

            for (Iterator<Rectangle> it = civilians.iterator(); it.hasNext(); ) {
                Rectangle civilian = it.next();
                civilian.y -= SPEED_CIVILIAN * Gdx.graphics.getDeltaTime();
                if (civilian.y + civilianImage.getHeight() < 0) it.remove();
                if (civilian.overlaps(soldier)) {
                    healthSound.play();
                    civiliansRescuePoints++;
                    if(soldierHealth < 95) {
                        soldierHealth += 5;
                    }
                    it.remove();
                }
            }
        } else {
            batch.begin();
            {
                font.setColor(Color.RED);
                font.draw(batch, "The END", Gdx.graphics.getHeight() / 2f, Gdx.graphics.getHeight() / 2f);
            }
            batch.end();
        }

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        if(civiliansRescuePoints == 2000){
            batch.begin();
            batch.draw(winImage, 0, 0);
            batch.end();

        }
        batch.setProjectionMatrix(hudViewport.getCamera().combined);
        if (soldierHealth > 0 && civiliansRescuePoints < 2000) {
            batch.begin();
            {

                batch.draw(backgroundImage, 0, 0);
                draw();

                for (Rectangle taliban : talibans) {
                    batch.draw(talibanImage, taliban.x, taliban.y);
                }
                for (Rectangle civilian : civilians) {
                    batch.draw(civilianImage, civilian.x, civilian.y);
                }
                batch.draw(soldierImage, soldier.x, soldier.y);

                font.setColor(Color.YELLOW);
                font.draw(batch, "" + civiliansRescuePoints, WORLD_WIDTH, WORLD_HEIGHT*1.58f);
                font.setColor(Color.GREEN);
                font.draw(batch, "" + soldierHealth, 0, WORLD_HEIGHT*1.58f);
            }
            batch.end();
        }
        if (debug) {
            renderDebug();
        }
    }
    private void draw() {
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        resize(screenWidth, screenHeight);
        String screenSize = "Screen/Window size: " + screenWidth + " x " + screenHeight + " px";
        String worldSize = "World size: " + (int) worldWidth + " x " + (int) worldHeight + " world units";
        String oneWorldUnit = "One world unit: " + (screenWidth / worldWidth) + " x " + (screenHeight / worldHeight) + " px";


        font.draw(batch,
                screenSize,
                20f,
                hudViewport.getWorldHeight() - 20f);

        font.draw(batch,
                worldSize,
                20f,
                hudViewport.getWorldHeight() - 50f);

        font.draw(batch,
                oneWorldUnit,
                20f,
                hudViewport.getWorldHeight() - 80f);
    }

    /**
     * Release all the native resources.
     */
    @Override
    public void dispose() {
        civilianImage.dispose();
        talibanImage.dispose();
        soldierImage.dispose();
        civilianSound.dispose();
        batch.dispose();
        font.dispose();
        renderer.dispose();

    }

    private void spawnCivilian() {
        Rectangle civilian = new Rectangle();
        civilian.x = MathUtils.random(0,WORLD_WIDTH - civilianImage.getWidth());
        civilian.y = Gdx.graphics.getHeight();
        civilian.width = civilianImage.getWidth();
        civilian.height = civilianImage.getHeight();
        civilians.add(civilian);
        lastCivilianTime = TimeUtils.nanoTime();
        REMOVE_CIVILIAN = 0;
    }

    private void spawnTaliban() {
        Rectangle taliban = new Rectangle();
        taliban.x = MathUtils.random(0, WORLD_WIDTH - talibanImage.getWidth());
        taliban.y =  Gdx.graphics.getHeight();
        taliban.width = talibanImage.getWidth();
        taliban.height = talibanImage.getHeight();
        talibans.add(taliban);
        lastTalibanTime = TimeUtils.nanoTime();
    }

    private void commandMoveLeft() {
        soldier.x -= SPEED * Gdx.graphics.getDeltaTime();
        if (soldier.x < 0) soldier.x = 0;
    }
    private void commandMoveUp(){
        soldier.y += SPEED * Gdx.graphics.getDeltaTime();
        if(soldier.y > Gdx.graphics.getHeight() - soldierImage.getHeight())
            soldier.y = Gdx.graphics.getHeight() - soldierImage.getHeight();
    }
    private void commandMoveDown(){
        soldier.y -= SPEED * Gdx.graphics.getDeltaTime();
        if(soldier.y < 0) soldier.y = 0;
    }

    private void commandMoveRight() {
        soldier.x += SPEED * Gdx.graphics.getDeltaTime();
        if (soldier.x > Gdx.graphics.getWidth() - soldierImage.getWidth())
            soldier.x = Gdx.graphics.getWidth() - soldierImage.getWidth();
    }

    private void commandMoveLeftCorner() {
        soldier.x = 0;
    }

    private void commandMoveRightCorner() {
        soldier.x = Gdx.graphics.getWidth() - soldierImage.getWidth();
    }

    private void commandTouched() {
        Vector3 touchPos = new Vector3();
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPos);
        soldier.x = touchPos.x - soldierImage.getWidth() / 2f;
    }

    private void commandExitGame() {
        Gdx.app.exit();
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
        ViewportUtils.debugPixelsPerUnit(viewport);
    }
    private void renderDebug() {
        ViewportUtils.drawGrid(viewport, renderer, 30);

        viewport.apply();

        Color oldColor = new Color(renderer.getColor());
        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        drawDebug();

        renderer.end();
        renderer.setColor(oldColor);
    }

    private void drawDebug() {
        renderer.setColor(Color.CYAN);
        renderer.circle(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 50f, 25);
    }
}
