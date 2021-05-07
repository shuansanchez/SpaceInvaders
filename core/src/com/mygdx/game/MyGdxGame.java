package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter implements Runnable{
	SpriteBatch batch;
	SpriteBatch nau2Sprite;
	SpriteBatch tretSprite;

	private Texture nau;
	Texture nau2;
	Texture tret;
	private int x, y;
	static private int SCREEN_HEIGHT, SCREEN_WIDTH;

	//pantalla: 1920 × 1080 pixel
	@Override
	public void create () {
		//ATRIBUTS
		SCREEN_HEIGHT= Gdx.graphics.getHeight();
		SCREEN_WIDTH=Gdx.graphics.getWidth();

		batch = new SpriteBatch();

		nau = new Texture("nau1.png");
		nau2 = new Texture("nau2.png");
		tret = new Texture("tret.png");

		//PROCESSOS
		Thread thread=new Thread(this);
		thread.start();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		//DIBUIX
		batch.draw(nau, x, y);
		batch.draw(nau2, 100, 100);
		batch.draw(tret, 200, 200);


		//FINAL
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	@Override
	public void run() {
		for(x=0; x<500;x+=10){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
