package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Random;

public class Enemy extends Sprite implements Runnable {

    static private int SCREEN_HEIGHT, SCREEN_WIDTH;

    public Enemy(Texture region, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(region, srcX, srcY, srcWidth, srcHeight);
    }

    @Override
    public void run() {
        SCREEN_HEIGHT= Gdx.graphics.getHeight()-100;
        SCREEN_WIDTH=Gdx.graphics.getWidth()-10;


        float x= Gdx.graphics.getWidth()-130;
        float y = Gdx.graphics.getHeight()-130;
        int SCREEN_X = Gdx.graphics.getWidth()-120;
        int SCREEN_Y = Gdx.graphics.getHeight()-120;

        double xforR = Gdx.graphics.getWidth();
        double yforR = Gdx.graphics.getHeight();
        Random r = new Random();
        float valorX = r.nextFloat() * 4 -2 ;
        Random r2 = new Random();
        float valorY = r2.nextFloat() * 4 -2 ;

        boolean salir = false;
        do
        {

            x += valorX;
            y += valorY;

            if (x < 0 || x > SCREEN_X)
            {
                valorX -= valorX;
                valorX = r.nextFloat() * 4 -2;

            }
            if (y < 0 ||y > SCREEN_Y || y < 200)
            {
                valorY -= valorY;
                valorY = r2.nextFloat() * 4 -2 ;

            }
            setX(x);
            setY(y);

            if(getTexture().toString() == "nau2e.png")
            {
                salir = true;
            }
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {

            }
        }while(salir == false);
    }
}
