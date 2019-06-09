package com.mygdx.game.Tools;


import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

public class Gesture implements GestureDetector.GestureListener {
    private JoyStick joyStick;


    public Gesture(JoyStick joyStick) {
        this.joyStick = joyStick;

    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {

        return false;
    }


    //set joystick position
    @Override
    public boolean tap(float x, float y, int count, int button) {
        joyStick.setX(x - joyStick.getWidth() / 2);
        joyStick.setY(joyStick.getHeight());
        return true;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}
