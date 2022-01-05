package com.peta;


import static java.lang.Thread.sleep;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.peta.util.ViewportUtils;
import com.peta.util.debug.DebugCameraController;
import com.peta.util.debug.MemoryInfo;

import java.util.Iterator;


public class MyGdxGame5 extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private int touched = 0;
	private float posX = 0;
	private float posY = 0;
	private int speed = 80;
	private int counterr = 0;
	private int dir = 0;
	private int timer = 0;
	private int time = 0;
	private int tempHight= 0;
	private int endMss = 0;
	private int pause = 0;
	private int powerUp = 0;
	private long lastCivilian;
	private long lastTaliban;
	private long lastPowerUp;
	long startTime;
	long elapsedTime;
	long elapsedSeconds;
	long secondsDisplay = 1;
	private static final float WORLD_WIDTH = 600f;
	private static final float WORLD_HEIGHT = 300f;
	private Array<GameObjectDynamic> arraj;
	public static final Pool<Civilian> pool_civilians = Pools.get(Civilian.class, 50);
	EndMsg konec;
	GameObject game;
	Soldier vojak;
	Score score;

	// debug
	private DebugCameraController debugCameraController;
	private MemoryInfo memoryInfo;
	private boolean debug = false;
	private ShapeRenderer shapeRenderer;
	public Viewport viewport;

	@Override
	public void create() {
		arraj = new Array<GameObjectDynamic>();
		batch = new SpriteBatch();
		Assets.Load();

		konec = new EndMsg(0.0f,0.0f,(float)Gdx.graphics.getHeight() / 2, (float)Gdx.graphics.getHeight() / 2);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		score = new Score(0,100);
		vojak = new Soldier(Gdx.graphics.getWidth() / 2f - Assets.soldierImage.getWidth() / 2f,20, Assets.soldierImage.getWidth(), Assets.soldierImage.getHeight(), Assets.soldierImage, 200);
		// debug
		debugCameraController = new DebugCameraController();
		debugCameraController.setStartPosition(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f);
		memoryInfo = new MemoryInfo(500);
		shapeRenderer = new ShapeRenderer();
		viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
		spawnCivilian();
		spawnTaliban();
		spawnPowerUp();
	}


	/**
	 * Runs every frame.
	 */
	@Override
	public void render() {
		batch.setProjectionMatrix(viewport.getCamera().combined);

		Gdx.gl.glClearColor(0, 0, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		int counter = 0;
		if (Gdx.input.isKeyJustPressed(Keys.F1)) debug = !debug;

		if (debug) {
			debugCameraController.handleDebugInput(Gdx.graphics.getDeltaTime());
			memoryInfo.update();
		}
		if (score.getSoldierHealth() == 0 || score.getSoldierHealth() < 0) {
			konec.Lose("The End or type R to reset", batch);
			if(Gdx.input.isKeyPressed(Keys.R) ){
				vojak.reset();
				score.setRescuedScore(0);
				score.setSoldierHealth(100);

				resetDynamic();
			}
		} else if(score.getRescuedScore() == 126){
			konec.Win(Assets.winImage, batch);
		}
		else{
			if(Gdx.input.isKeyPressed(Keys.P) ){
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(pause == 0){

					pause = 1;
				}else if(pause == 1){
					pause = 0;
				}
			}else if(pause == 0) {

				if(powerUp == 1) {
					elapsedTime = System.currentTimeMillis() - startTime;
					elapsedSeconds = elapsedTime / 1000;
					secondsDisplay = elapsedSeconds % 60;
					vojak.setSped(1000);
					if (elapsedSeconds == 5) {
						powerUp = 0;
						vojak.setSped(450);

					}
				}

				if (Gdx.input.isKeyPressed(Keys.LEFT)) vojak.commandMoveLeft();
				if (Gdx.input.isKeyPressed(Keys.RIGHT)) vojak.commandMoverRight();
				if (Gdx.input.isKeyPressed(Keys.A)) vojak.commandMoveLeftCorner();
				if (Gdx.input.isKeyPressed(Keys.S)) vojak.commandMoveRightCorner();
				if (Gdx.input.isKeyPressed(Keys.ESCAPE)) commandExitGame();
				if (Gdx.input.isKeyPressed(Keys.UP)) vojak.commandMoveUp();
				if (Gdx.input.isKeyPressed(Keys.DOWN)) vojak.commandMoveDown();

				for (Iterator<GameObjectDynamic> it = arraj.iterator(); it.hasNext(); ) {
					GameObjectDynamic object = it.next();
					if (object.getId() == 0) {
						if (object.isTimeToCreateNew(lastTaliban) == true) {
							spawnTaliban();
						}
						object.setVelocityY();
						if (object.position.y + Assets.talibanImage.getHeight() < 0) {
							object.free();
							it.remove();
						}

						if (object.position.x < vojak.position.x + vojak.bounds.width && object.position.x + object.bounds.width > vojak.position.x && object.position.y < vojak.position.y + vojak.bounds.height && object.position.y + object.bounds.height > vojak.position.y) {
							score.setSoldierHealth(score.getSoldierHealth() - 1);
							if(powerUp == 1){
								score.setSoldierHealth(score.getSoldierHealth() + 1);
							}
							if (timer == 0) {
									if(powerUp == 1){
									}
									else
										{
											Assets.blood.start();
											Assets.blood.setPosition(vojak.position.x,vojak.position.y);
										object.playSound().play();
									}

								timer++;
							} else if (timer == 10) {
								timer = 0;
							} else {
								timer++;
							}
						}
					} else if (object.getId() == 1) {
						if (object.isTimeToCreateNew(lastCivilian) == true) {
							spawnCivilian();
						}

						object.position.y -= object.getSpeed() * Gdx.graphics.getDeltaTime();
						object.update(Gdx.graphics.getDeltaTime());
						if (object.position.y + object.getImage().getHeight() < 0) {
							object.free();
							it.remove();
						}
						if (object.position.x < vojak.position.x + vojak.bounds.width && object.position.x + object.bounds.width > vojak.position.x && object.position.y < vojak.position.y + vojak.bounds.height && object.position.y + object.bounds.height > vojak.position.y) {
							score.setRescuedScore(score.getRescuedScore() + 1);
							object.playSound().play();
							Assets.up.start();

							Assets.up.setPosition(
									object.position.x,
									object.position.y
							);

							object.free();
							it.remove();
							if(score.getSoldierHealth() < 50){
								spawnPowerUp();
							}
						}
					}else if(object.getId() == 3){

						object.position.y -= object.getSpeed() * Gdx.graphics.getDeltaTime();
						object.update(Gdx.graphics.getDeltaTime());
						if (object.position.y + object.getImage().getHeight() < 0) {
							object.free();
							it.remove();
						}
						if (object.position.x < vojak.position.x + vojak.bounds.width && object.position.x + object.bounds.width > vojak.position.x && object.position.y < vojak.position.y + vojak.bounds.height && object.position.y + object.bounds.height > vojak.position.y) {
							score.setSoldierHealth(score.getSoldierHealth()+5);
							object.playSound().play();
							object.free();
							it.remove();
							powerUp = 1;
							startTime = System.currentTimeMillis();



						}
					}
				}


				batch.begin();
				batch.draw(Assets.backgroundImage, 0, 0);
				{
					for (int i = 0; i < arraj.size; i++) {
						arraj.get(i).render(batch);
					}
				}


				score.render(batch);

				Assets.up.update(Gdx.graphics.getDeltaTime());
				Assets.up.draw(batch);
				Assets.blood.update(Gdx.graphics.getDeltaTime());
				Assets.blood.draw(batch);
				vojak.render(batch);

				batch.end();
			}
		}
		if(pause == 1){
			konec.Pause(Assets.pauseImage, batch);

		}
		if (debug) {
			debugCameraController.applyTo(camera);
			batch.begin();
			{

				// the average number of frames per second
				GlyphLayout layout = new GlyphLayout(score.font, "FPS:" + Gdx.graphics.getFramesPerSecond());
				score.font.setColor(Color.YELLOW);
				score.font.draw(batch, layout, Gdx.graphics.getWidth() - layout.width, Gdx.graphics.getHeight() - 50);

				// number of rendering calls, ever; will not be reset unless set manually
				score.font.setColor(Color.YELLOW);
				score.font.draw(batch, "RC:" + batch.totalRenderCalls, Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() - 20);

				memoryInfo.render(batch, score.font);

			}

			batch.end();
			batch.totalRenderCalls = 0;
			ViewportUtils.drawGrid(viewport, shapeRenderer, 50);

			// print rectangles
			shapeRenderer.setProjectionMatrix(camera.combined);
			// https://libgdx.badlogicgames.com/ci/nightlies/docs/api/com/badlogic/gdx/graphics/glutils/ShapeRenderer.html
			shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			{
				shapeRenderer.setColor(1, 1, 0, 1);
				for (Iterator<GameObjectDynamic> it = arraj.iterator(); it.hasNext(); ) {
					GameObjectDynamic object = it.next();
					shapeRenderer.rect(object.position.x,object.position.y, object.getImage().getWidth(), object.getImage().getHeight());
				}
				shapeRenderer.rect(vojak.position.x, vojak.position.y, vojak.getImage().getWidth(), vojak.getImage().getHeight());
			}
			shapeRenderer.end();
		}
	}
	@Override
	public void dispose() {
		Assets.dispose();
		batch.dispose();
		score.font.dispose();
	}

	private void spawnCivilian() {
		Civilian civilian = Civilian.POOL_CIVILIANS.obtain();
		civilian.setAll((MathUtils.random(0, Gdx.graphics.getWidth() - Assets.civilianImage.getWidth())), Gdx.graphics.getHeight(), Assets.civilianImage.getWidth(), Assets.civilianImage.getHeight(), Assets.civilianImage, Assets.civilianSound,200 );
		System.out.println((viewport.getScreenX()));
		arraj.add(civilian);
		lastCivilian = TimeUtils.nanoTime();
	}

	private void spawnTaliban() {
		Taliban taliban = Taliban.POOL_TALIBANS.obtain();
		taliban.setAll((MathUtils.random(0, Gdx.graphics.getWidth() - Assets.talibanImage.getWidth())), Gdx.graphics.getHeight(), Assets.talibanImage.getWidth(), Assets.talibanImage.getHeight(), Assets.talibanImage, Assets.talibanSound, 100);
		arraj.add(taliban);
		lastTaliban = TimeUtils.nanoTime();
	}

	private void spawnPowerUp(){
		PowerUp power = PowerUp.POOL_POWERUP.obtain();
		power.setAll((MathUtils.random(0, Gdx.graphics.getWidth() - Assets.powerImage.getWidth())), Gdx.graphics.getHeight(), Assets.powerImage.getWidth(), Assets.talibanImage.getHeight(), Assets.powerImage, Assets.civilianSound, 400);
		arraj.add(power);
		lastPowerUp = TimeUtils.nanoTime();
	}

	private void commandExitGame() {
		Gdx.app.exit();
	}
	private void resetDynamic(){
		for (Iterator<GameObjectDynamic> it = arraj.iterator(); it.hasNext(); ) {
			GameObjectDynamic object = it.next();
			object.free();
			it.remove();
		}
		spawnCivilian();
		spawnTaliban();
	}
}
