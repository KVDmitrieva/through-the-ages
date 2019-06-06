package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

public class Gesture implements GestureDetector.GestureListener {
    JoyStick joyStick;
    Camera camera;

    public Gesture(JoyStick joyStick, Camera camera){
        this.joyStick = joyStick;
        this.camera = camera;
    }
    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        joyStick.setX(x-joyStick.getWidth()/2);
        if(camera.position.y-y/2<0) joyStick.setY(0); else
            if(camera.position.y-y/2>Gdx.app.getGraphics().getHeight())
                joyStick.setY(Gdx.app.getGraphics().getHeight()-joyStick.getHeight());else
        joyStick.setY(camera.position.y-y/2+joyStick.getHeight()/2);
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
