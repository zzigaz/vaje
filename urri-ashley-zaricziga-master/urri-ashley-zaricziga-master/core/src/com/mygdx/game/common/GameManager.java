package com.mygdx.game.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import com.mygdx.game.Talibans;
public class GameManager {

    public static final GameManager INSTANCE = new GameManager();

    private static final String BEST_RESULT = "BEST_RESULT";

    private final Preferences PREFS;

    private int result;
    private int health;

    private GameManager() {
        PREFS = Gdx.app.getPreferences(Talibans.class.getSimpleName());
    }

    public int getResult() {
        return result;
    }

    public void resetResult() {
        result = 0;
        health = 100;
    }

    public void incResult() {
        result++;
    }

    public boolean isGameOver() {
        return health <= 0;
    }

    public int getHealth() {
        return health;
    }
    public void health() {
        health += 5;
        if (health == 0) {
            if (result > getBestResult()) setBestResult(result);
        }
    }
    public void damage() {
        health--;
        if (health == 0) {
            if (result > getBestResult()) setBestResult(result);
        }
    }

    public void setBestResult(int result) {
        PREFS.putInteger(BEST_RESULT, result);
        PREFS.flush();
    }

    public int getBestResult() {
        return PREFS.getInteger(BEST_RESULT, 0);
    }
}
