package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;


public class Constants {

    public static int WIDTH = Gdx.app.getGraphics().getWidth(); //screen width
    public static int HEIGHT = Gdx.app.getGraphics().getHeight(); //screen height

    public static int LEVEL = 1;
    public static int SCORE = 0;
    public static int ENEMIES = 0;

    public static int SIZE = WIDTH / 5; //basic size of objects (helps to scale map, sprites, etc)

    public static int scaleSizeOfFloor = 24;

    public static float scaleWidthOfEnemy = 1.2f;
    public static float scaleWidthOfBoss = 3.2f;

    public static int bossRoomWidth = 10;
    public static int bossRoomHeight = 5;

    public static int framePeriod = 1000 / 7;

    public static int joystickDeadZone = 20;

    public static float scrollPaneHeight = (float) HEIGHT / 7;
    public static float scrollPaneWidth = (float) WIDTH / 2;
    public static float scrollPaneScale = 1.2f;
    public static float scrollPaneX = scrollPaneWidth / 8;
    public static float scrollPaneY = (float) HEIGHT / 2 - scrollPaneHeight / 2;

    public static int sizeOfBackground = HEIGHT;
    public static float backgroundX = 0 - ((1 - (float) WIDTH / (float) HEIGHT) * sizeOfBackground) / 2;

    public static int logoWidth = 19 * (WIDTH / 20);
    public static int logoHeight = HEIGHT / 5;

    public static float logoX = (float) WIDTH / 2 - (float) logoWidth / 2;

    public static float scoreLogoY = 7 * (float) HEIGHT / 9;
    public static float menuLogoY = 4 * (float) HEIGHT / 9;

    public static float buttonWidth = (float) WIDTH / 2;
    public static float buttonHeight = (float) WIDTH / 10;
    public static float buttonX = (float) WIDTH / 2 - buttonWidth / 2;
    public static float sizeExitButton = (float) WIDTH / 10;
    public static float yesCoef = 1.1f;
    public static float noCoef = 0.1f;

    public static int maxNumberOfRooms = 8;

    public static float partOfDef = 0.1f;
    public static int bonusForNewLevel = 50;
    public static int scoreForEnemy = 10;
    public static int healthForEnemy = 100;
    public static int healthForBoss = 500;
    public static int maxPlayerHealth = 1000;

    public static int periodOfDrawingStats = 10;

    public static int minForHealth = 10;
    public static int maxForHealth = 30;
    public static int minForDef = 30;
    public static int maxForDef = 60;
    public static int minForAttack = 70;
    public static int maxForAttack = 90;

    public static float fontScale = (float) SIZE / 50;

    public static float partOfHealthX = (float) WIDTH / 2 + 5;
    public static float partOfHealthY = (float) HEIGHT / 2.5f;
    public static float partOfHealthX2 = (float) WIDTH / 600;
    public static float healthWidth = (float) WIDTH / 6;
    public static float healthHeight = (float) HEIGHT / 25;


}
