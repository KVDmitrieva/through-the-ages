package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.sql.Database;
import com.badlogic.gdx.sql.DatabaseCursor;
import com.badlogic.gdx.sql.DatabaseFactory;
import com.badlogic.gdx.sql.SQLiteGdxException;

import java.util.ArrayList;


import static com.mygdx.game.Tools.Constants.LEVEL;
import static com.mygdx.game.Tools.Constants.SCORE;
import static com.mygdx.game.Tools.Constants.ENEMIES;


public class MyDatabase {

    private Database dbHandler;


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "statisticDB";
    private static final String TABLE_STATISTIC = "statistic";

    private static final String KEY_ID = "_id";
    private static final String KEY_SCORE = "score";
    private static final String KEY_LEVEL = "level";
    private static final String KEY_ENEMIES = "enemy";

    private static final int NUM_COLUMN_SCORE = 1;
    private static final int NUM_COLUMN_LEVEL = 2;
    private static final int NUM_COLUMN_ENEMIES = 3;

    private static final String DATABASE_CREATE = "create table if not exists "
            + TABLE_STATISTIC + "(" + KEY_ID
            + " integer primary key autoincrement, " + KEY_SCORE + " INT, " +
            KEY_LEVEL + " INT," +
            KEY_ENEMIES + " INT);";


    public MyDatabase() {
        Gdx.app.log("DatabaseTest", "creation started");
        dbHandler = DatabaseFactory.getNewDatabase(DATABASE_NAME,
                DATABASE_VERSION, DATABASE_CREATE, null);

        dbHandler.setupDatabase();

    }


    public ArrayList<String> selectAll() {
        try {
            dbHandler.openOrCreateDatabase();
            dbHandler.execSQL(DATABASE_CREATE);
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        DatabaseCursor mCursor = null;
        ArrayList<String> arr = new ArrayList<String>();
        try {
            mCursor = dbHandler.rawQuery("SELECT * FROM statistic");
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }
        if (mCursor != null) {
            while (mCursor.next()) {
                int finalScore = mCursor.getInt(NUM_COLUMN_SCORE);
                int level = mCursor.getInt(NUM_COLUMN_LEVEL);
                int enemy = mCursor.getInt(NUM_COLUMN_ENEMIES);
                String totalScore = "Score " + finalScore + "   Level " + level + "   Enemies " + enemy;
                arr.add(totalScore);
            }
        }

        try {
            dbHandler.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }

        return arr;
    }

    public void pushData() {

        try {
            dbHandler.openOrCreateDatabase();
            dbHandler.execSQL(DATABASE_CREATE);
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }

        try {
            dbHandler.execSQL("INSERT INTO statistic ('score', 'level', 'enemy') VALUES ('" + SCORE + "','" + LEVEL + "','" + ENEMIES + "')");

        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }

        try {
            dbHandler.closeDatabase();
        } catch (SQLiteGdxException e) {
            e.printStackTrace();
        }

    }
}
