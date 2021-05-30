package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player {
    private Sprite sprite;

    public Player(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
