package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class JoyStick extends Touchpad {

    private static Skin touchpadSkin;
    private static Touchpad.TouchpadStyle touchpadStyle;
    private static Drawable touchBackground;
    private static Drawable touchStick;
    private static int size = Gdx.app.getGraphics().getWidth()/10;

    public JoyStick(float x, float y) {

        super(20, getTouchpadStyle());

    }


    static Touchpad.TouchpadStyle getTouchpadStyle() {

        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture("backgrounjoy.png"));

        touchpadSkin.add("touchKnob", new Texture("joystick.png"));

        touchpadStyle = new Touchpad.TouchpadStyle();

        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchBackground.setMinHeight(size*2);
        touchBackground.setMinWidth(size*2);
        touchStick = touchpadSkin.getDrawable("touchKnob");
        touchStick.setMinWidth((float)size);
        touchStick.setMinHeight((float)size);

        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchStick;

        return touchpadStyle;
    }
}
