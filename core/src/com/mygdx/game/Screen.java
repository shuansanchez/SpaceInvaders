package com.mygdx.game;

public abstract class Screen {
    public abstract void create();
    public abstract void render();
    public abstract void resize();
    public abstract void dispose();
    public abstract void pause();
    public abstract void resume();
}
