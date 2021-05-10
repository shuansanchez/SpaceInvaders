package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Tret extends Sprite{
    private float x;
    private float y;

    // Which way is it shooting
    public final int UP = 0;
    public final int DOWN = 1;

    // Going nowhere
    int heading = -1;
    float speed =  350;

    private int width = 1;
    private int height;

    private boolean isActive;

    public Tret(int screenY) {

        height = screenY / 20;
        isActive = false;

    }
}
