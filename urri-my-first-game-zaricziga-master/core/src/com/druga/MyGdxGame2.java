package com.druga;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class MyGdxGame2 extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Array<Civilian> civilians;
	private Array<Taliban> talibans;
	private Array<Ball> balls;
	private Tyre tyre;
	private int touched = 0;
	private float posX = 0;
	private float posY = 0;
	private Ball ball;
	private int speed = 80;
	private int dir = 0;
	private int timer = 0;
	private int time = 0;
	private int tempHight= 0;
	private int endMss = 0;
	EndMsg konec;
	GameObject game;
	Soldier vojak;
	Score score;
	@Override
	public void create() {
		batch = new SpriteBatch();
		Assets.Load();
		/*
		civilianImage = new Texture(Gdx.files.internal("civilian.png"));
		talibanImage = new Texture(Gdx.files.internal("taliban1.jpg"));
		civilianSound = Gdx.audio.newSound(Gdx.files.internal("health.mp3"));
		talibanSound = Gdx.audio.newSound(Gdx.files.internal("gun.wav"));
		ballImage = new Texture(Gdx.files.internal("zoga.jpg"));
		backgroundImage = new Texture(Gdx.files.internal("background.jpg"));
		winImage = new Texture(Gdx.files.internal("winner.jpg"));
		tyreImage = new Texture(Gdx.files.internal("kolo.jpg"));
				soldierImage = new Texture(Gdx.files.internal("soldier.jpg"));

		*/

		civilians = new Array<Civilian>();
		konec = new EndMsg(0.0f,0.0f,(float)Gdx.graphics.getHeight() / 2, (float)Gdx.graphics.getHeight() / 2);
		talibans = new Array<Taliban>();
		balls = new Array<Ball>();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		score = new Score(0,100);
		vojak = new Soldier(Gdx.graphics.getWidth() / 2f - Assets.soldierImage.getWidth() / 2f,20,Assets.soldierImage.getWidth(),Assets.soldierImage.getHeight(), Assets.soldierImage, 200);
		tyre = new Tyre(100, 100,Assets.tyreImage.getWidth(), Assets.tyreImage.getHeight(), Assets.tyreImage, Assets.civilianSound,300);
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
		int counter = 0;
		if (score.getSoldierHealth() == 0 || score.getSoldierHealth() < 0) {
			konec.Lose("The End");
		} else if(score.getRescuedScore() == 6){
			konec.Win(Assets.winImage);
		}
		else{
			if (Gdx.input.isTouched() && dir == 0){
				spawnBall();
				dir = 1;
			};
			if(dir != 0 && dir < 5){
				dir++;
			}
			if(dir ==5){
				dir = 0;
			}
			if (Gdx.input.isKeyPressed(Keys.LEFT)) vojak.commandMoveLeft();
			if (Gdx.input.isKeyPressed(Keys.RIGHT)) vojak.commandMoverRight();
			if (Gdx.input.isKeyPressed(Keys.A)) vojak.commandMoveLeftCorner();
			if (Gdx.input.isKeyPressed(Keys.S)) vojak.commandMoveRightCorner();
			if (Gdx.input.isKeyPressed(Keys.ESCAPE)) commandExitGame();
			if (Gdx.input.isKeyPressed(Keys.UP)) vojak.commandMoveUp();
			if (Gdx.input.isKeyPressed(Keys.DOWN)) vojak.commandMoveDown();
			for (Iterator<Ball> it = balls.iterator(); it.hasNext(); ) {
				Ball bal = it.next();
				if(bal.position.y < 0)
				{
					bal.dir = 0;
				}

				if(bal.dir == 0){
					bal.position.y +=  bal.getSpeed() * Gdx.graphics.getDeltaTime();
					bal.setSpeed(bal.getSpeed()-10);
					if(bal.getSpeed() < 0){
						bal.setSpeed(bal.getSpeed()+3);
						bal.dir = 1;
					}
				}


				if(bal.dir == 1){
					bal.position.y -=  bal.getSpeed() * Gdx.graphics.getDeltaTime();
					bal.setSpeed(bal.getSpeed()+10);
					if(bal.getSpeed() > 0){
						bal.setSpeed(bal.getSpeed()-5);

					}

				}

				if( bal.getSpeed()< 5 &&  bal.getSpeed() > -5){
					bal.timer++;
					if(bal.timer == 20) {
						bal.position.y = 0;
						bal.dir = 5;
					}
				}
			}
			for (Iterator<Civilian> it = civilians.iterator(); it.hasNext(); ) {
				Civilian civilian = it.next();
				if ((civilians.size - 1) == counter) {
					if(civilian.isTimeToCreateNew() == true){
						spawnCivilian();
					}

				}
				counter++;
				civilian.position.y -=  civilian.getSpeed() * Gdx.graphics.getDeltaTime();
				civilian.update(Gdx.graphics.getDeltaTime());
				if (civilian.position.y + civilian.getCivilianImage().getHeight() < 0)
					it.remove();
				if (civilian.position.x < vojak.position.x + vojak.bounds.width && civilian.position.x + civilian.bounds.width > vojak.position.x && civilian.position.y < vojak.position.y + vojak.bounds.height && civilian.position.y + civilian.bounds.height > vojak.position.y) {
					score.setRescuedScore(score.getRescuedScore() + 1);
					civilian.civilianSound.play();
					it.remove();
				}
			}
			if (civilians.size == 0) {
				spawnCivilian();
			}
			int counter2 = 0;
			for (Iterator<Taliban> it = talibans.iterator(); it.hasNext(); ) {
				Taliban taliban = it.next();
				if ((talibans.size - 1) == counter2) {
					if(taliban.isTimeToCreateNew() == true){
						spawnTaliban();
					}
				}
				counter2++;
				taliban.setVelocityY();
				if (taliban.position.y + Assets.talibanImage.getHeight() < 0)
					it.remove();
				if (taliban.position.x < vojak.position.x + vojak.bounds.width && taliban.position.x + taliban.bounds.width > vojak.position.x && taliban.position.y < vojak.position.y + vojak.bounds.height && taliban.position.y + taliban.bounds.height > vojak.position.y) {
					score.setSoldierHealth(score.getSoldierHealth() - 1);
					if (timer == 0) {
						taliban.talibanSound.play();
						timer++;
					} else if (timer == 10) {
						timer = 0;
					} else {
						timer++;
					}
				}
				;
			}

			if (civilians.size == 0) {
				spawnCivilian();
			}


			batch.begin();
			batch.draw(Assets.backgroundImage, 0, 0);
			{
				for (int i = 0; i < civilians.size; i++) {
					civilians.get(i).render(batch);
				}
				for (int i = 0; i < talibans.size; i++) {
					talibans.get(i).render(batch);
				}
			}

			score.render(batch);
			for (int i = 0; i < balls.size; i++) {
				balls.get(i).render(batch);
			}
			if(tyre.position.x < 0)
			{
				tyre.dir = 0;
			}
			if(tyre.position.x > Gdx.graphics.getWidth()-tyre.bounds.width){
				tyre.dir = 1;
			}
			if(tyre.dir == 0){
				tyre.position.x +=  tyre.getSpeed() * Gdx.graphics.getDeltaTime();
			}

			if(tyre.dir == 1){
				tyre.position.x -=  tyre.getSpeed() * Gdx.graphics.getDeltaTime();
			}
			tyre.update(Gdx.graphics.getDeltaTime());
			tyre.render(batch);
			vojak.render(batch);
			batch.end();

		}

	}
	@Override
	public void dispose() {
		Assets.dispose();
		batch.dispose();
		score.font.dispose();
	}

	private void spawnCivilian() {
		Civilian civilian = new Civilian(MathUtils.random(0, Gdx.graphics.getWidth() - Assets.civilianImage.getWidth()), Gdx.graphics.getHeight(),Assets.civilianImage.getWidth(), Assets.civilianImage.getHeight(), Assets.civilianImage, Assets.civilianSound,200 );
		civilians.add(civilian);
	}

	private void spawnTaliban() {
		Taliban taliban = new Taliban(MathUtils.random(0, Gdx.graphics.getWidth() - Assets.talibanImage.getWidth()), Gdx.graphics.getHeight(),Assets.talibanImage.getWidth(), Assets.talibanImage.getHeight(), Assets.talibanImage, Assets.talibanSound, 100);
		talibans.add(taliban);
	}

	private void spawnBall(){
		Vector3 touchPos = new Vector3();
		touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		camera.unproject(touchPos);
		ball = new Ball(touchPos.x, touchPos.y, Assets.ballImage.getWidth(), Assets.ballImage.getHeight(), Assets.ballImage, 80);
		balls.add(ball);
		touched = 1;
	}

	private void commandExitGame() {
		Gdx.app.exit();
	}
}
