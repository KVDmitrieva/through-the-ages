package com.mygdx.game.Tools;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import static com.mygdx.game.Tools.Constants.SIZE;
import static com.mygdx.game.Tools.Constants.joystickDeadZone;

public class JoyStick extends Touchpad {


    public JoyStick() {

        super(joystickDeadZone, getTouchpadStyle());

    }


    private static Touchpad.TouchpadStyle getTouchpadStyle() {

        Skin touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture("backgrounjoy.png"));

        touchpadSkin.add("touchKnob", new Texture("joystick.png"));

        Touchpad.TouchpadStyle touchpadStyle = new Touchpad.TouchpadStyle();

        Drawable touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchBackground.setMinHeight(SIZE);
        touchBackground.setMinWidth(SIZE);
        Drawable touchStick = touchpadSkin.getDrawable("touchKnob");
        touchStick.setMinWidth((float) SIZE / 2);
        touchStick.setMinHeight((float) SIZE / 2);

        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchStick;

        return touchpadStyle;
    }
}
