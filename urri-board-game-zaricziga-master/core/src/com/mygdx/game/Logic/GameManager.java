package com.mygdx.game.Logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.CellState;
import com.mygdx.game.Chess;
import com.mygdx.game.SoundClass;
import com.mygdx.game.Time;

import java.sql.Struct;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();
    Map<Integer, String> map = new HashMap<Integer, String>();
    private static final String INIT_MOVE_KEY = "initMove";
    private static final String INIT_TIME_KEY = "initTime";
    private static final String INIT_LEADERBOARD_KEY = "initLeaderboard";
    private static final String INIT_LEADERBOARD_INTEGER_KEY = "initLeaderboardInteger";
    private static final String INIT_SOUND_KEY = "initSound";
    public Music music = Gdx.audio.newMusic(Gdx.files.internal("sounds/ricki.mp3"));
    public Music music2 = Gdx.audio.newMusic(Gdx.files.internal("sounds/hansi.mp3"));
    private final Preferences PREFS;
    public Array<String> leaderBoard;
    private CellState initMove = CellState.WHITE;
    private Time initTime = Time.EASY;
    private SoundClass initSound = SoundClass.ON;

    private GameManager() {
        PREFS = Gdx.app.getPreferences(Chess.class.getSimpleName());
        leaderBoard = new Array<>();
        String moveName = PREFS.getString(INIT_MOVE_KEY, CellState.WHITE.name());
        initMove = CellState.valueOf(moveName);
        String timeName = PREFS.getString(INIT_TIME_KEY, Time.EASY.name());
        initTime = Time.valueOf(timeName);
        String soundName = PREFS.getString(INIT_SOUND_KEY, SoundClass.ON.name());
        initSound = SoundClass.valueOf(soundName);
        System.out.println(initMove);
        System.out.println(initSound);
        System.out.println(initTime);


/*
        PREFS.putString(INIT_LEADERBOARD_KEY+0,"ziga0");
        PREFS.flush();
        PREFS.putString(INIT_LEADERBOARD_KEY+1,"ziga1");
        PREFS.flush();
        PREFS.putString(INIT_LEADERBOARD_KEY+2,"ziga2");
        PREFS.flush();
        PREFS.putString(INIT_LEADERBOARD_KEY+3,"ziga3");
        PREFS.flush();
        PREFS.putString(INIT_LEADERBOARD_KEY+4,"ziga4");
        PREFS.flush();
        PREFS.putString(INIT_LEADERBOARD_KEY+5,"ziga5");
        PREFS.flush();
        PREFS.putString(INIT_LEADERBOARD_KEY+6,"ziga6");
        PREFS.flush();
        PREFS.putString(INIT_LEADERBOARD_KEY+7,"ziga7");
        PREFS.flush();
        PREFS.putString(INIT_LEADERBOARD_KEY+8,"ziga8");
        PREFS.flush();
        PREFS.putString(INIT_LEADERBOARD_KEY+9,"ziga9");
        PREFS.flush();

        PREFS.putString(INIT_LEADERBOARD_INTEGER_KEY+0,"5001");
        PREFS.flush();
        PREFS.putString(INIT_LEADERBOARD_INTEGER_KEY+1,"5202");
        PREFS.flush();
        PREFS.putString(INIT_LEADERBOARD_INTEGER_KEY+2,"5600");
        PREFS.flush();
        PREFS.putString(INIT_LEADERBOARD_INTEGER_KEY+3,"1000");
        PREFS.flush();
        PREFS.putString(INIT_LEADERBOARD_INTEGER_KEY+4,"2000");
        PREFS.flush();
        PREFS.putString(INIT_LEADERBOARD_INTEGER_KEY+5,"3000");
        PREFS.flush();
        PREFS.putString(INIT_LEADERBOARD_INTEGER_KEY+6,"4000");
        PREFS.flush();
        PREFS.putString(INIT_LEADERBOARD_INTEGER_KEY+7,"5012");
        PREFS.flush();
        PREFS.putString(INIT_LEADERBOARD_INTEGER_KEY+8,"7000");
        PREFS.flush();
        PREFS.putString(INIT_LEADERBOARD_INTEGER_KEY+9,"5000");
        PREFS.flush();
*/
    }
    public CellState getInitMove() {
        String moveName = PREFS.getString(INIT_MOVE_KEY, CellState.WHITE.name());
        initMove = CellState.valueOf(moveName);
        return initMove;
    }
    public CellState InitMove() {
        return initMove;
    }
    public Time InitTime() {
        return initTime;
    }
    public SoundClass InitSound(){ return initSound;}

    public void setSound(SoundClass sd){
        initSound = sd;
        PREFS.putString(INIT_SOUND_KEY,sd.name());
        PREFS.flush();
    }
    public void setTimeState(Time time){
        initTime = time;
        PREFS.putString(INIT_TIME_KEY,time.name());
        PREFS.flush();
    }
    public void setInitMove(CellState move) {
        initMove = move;

        PREFS.putString(INIT_MOVE_KEY, move.name());
        PREFS.flush();
    }
}
