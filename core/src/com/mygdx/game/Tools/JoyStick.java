package com.mygdx.game.Tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class JoyStick extends Touchpad {

    private static Skin touchpadSkin;
    private static Touchpad.TouchpadStyle touchpadStyle;
    private static Drawable touchBackground;
    private static Drawable touchStick;

    public JoyStick(float x, float y) {

        super(20, getTouchpadStyle());
    }


    static Touchpad.TouchpadStyle getTouchpadStyle() {

        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture("backgrounjoy.png"));

        touchpadSkin.add("touchKnob", new Texture("joystick.png"));

        touchpadStyle = new Touchpad.TouchpadStyle();

        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchBackground.setMinHeight(70);
        touchBackground.setMinWidth(70);
        touchStick = touchpadSkin.getDrawable("touchKnob");
        touchStick.setMinWidth(40);
        touchStick.setMinHeight(40);

        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchStick;

        return touchpadStyle;
    }
}
