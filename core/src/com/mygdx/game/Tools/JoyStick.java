package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class JoyStick extends Touchpad {

    private static int size = Gdx.app.getGraphics().getWidth()/10;

    public JoyStick() {

        super(20, getTouchpadStyle());

    }


    private static Touchpad.TouchpadStyle getTouchpadStyle() {

        Skin touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture("backgrounjoy.png"));

        touchpadSkin.add("touchKnob", new Texture("joystick.png"));

        Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();

        Drawable touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchBackground.setMinHeight(size*2);
        touchBackground.setMinWidth(size*2);
        Drawable touchStick = touchpadSkin.getDrawable("touchKnob");
        touchStick.setMinWidth((float)size);
        touchStick.setMinHeight((float)size);

        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchStick;

        return touchpadStyle;
    }
}
