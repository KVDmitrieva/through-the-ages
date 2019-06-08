package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class MyButton extends Button {

    public MyButton(String styleName) {

        super(createSkin("buttons.atlas", "buttons.json"), styleName);

    }

    private static  Skin createSkin(String atlasString, String skinString){
        Skin skin = new Skin();
        TextureAtlas atlas = new TextureAtlas(atlasString);
        skin.addRegions(atlas);
        skin.load(Gdx.files.internal(skinString));
        return skin;
    }
}
