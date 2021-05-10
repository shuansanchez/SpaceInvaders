package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor, Runnable {
	SpriteBatch batch;
	SpriteBatch batchEnemiga;

	private Texture nau;
	private Texture nauEnemiga;

	private int textx, texty;
	static private int SCREEN_HEIGHT, SCREEN_WIDTH;

	private BitmapFont font;
	private String text;

	private Sprite spriteNau;
	private Sprite spriteNauEnemiga;
	private ArrayList<Sprite> spriteNaus2;

	private int XEnemiga;

	private Vector2 lastTouch = new Vector2();

	private ShapeRenderer sr;

	@Override
	public void create () {
		Gdx.input.setInputProcessor(this);
		//ATRIBUTS
		SCREEN_HEIGHT= Gdx.graphics.getHeight()-100;
		SCREEN_WIDTH=Gdx.graphics.getWidth()-10;

		batch = new SpriteBatch();
		batchEnemiga=new SpriteBatch();
		spriteNaus2=new ArrayList<Sprite>();
		sr=new ShapeRenderer();

		nau = new Texture("nau1.png");
		nauEnemiga = new Texture("nau2.png");

		font=new BitmapFont();

		text="SPACE INVADERS";
		textx=25;
		texty=30;



		spriteNau=new Sprite(nau, 0,0,nau.getWidth(), nau.getHeight());
		spriteNau.setX((SCREEN_WIDTH-nau.getWidth())/2);
		spriteNau.setY((SCREEN_HEIGHT-nau.getHeight())/4);


		spriteNauEnemiga=new Sprite(nauEnemiga, 300,1000,nauEnemiga.getWidth(), nauEnemiga.getHeight());
		/*spriteNauEnemiga.setX((SCREEN_WIDTH-nau.getWidth())/2);*/
		spriteNauEnemiga.setY((SCREEN_HEIGHT-nau.getHeight())-200);
		Thread thread=new Thread(this);
		thread.start();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		//DIBUIX
		//----NAUS
		batch.draw(nau, spriteNau.getX(), spriteNau.getY());
		batch.draw(nauEnemiga, XEnemiga, spriteNauEnemiga.getY());

		//----FIGURES-TEXT

		font.getData().setScale(2.5F);
		font.setColor(Color.GREEN);
		font.draw(batch, text, textx, texty);


		//linia inferior
/*
		sr.begin(ShapeRenderer.ShapeType.Line);
		sr.setColor(Color.YELLOW);
		sr.rect(0,0,SCREEN_WIDTH+100, 100);
		sr.end();
*/

		//FINAL
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	@Override
	public void run() {
		//moure enemics

		for(XEnemiga=0; XEnemiga<SCREEN_WIDTH-20;XEnemiga+=5){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode){
			case Input.Keys.DPAD_LEFT:
				if(spriteNau.getX()>10){
					spriteNau.setX(spriteNau.getX()-20);
				}
				break;
			case Input.Keys.DPAD_RIGHT:
				if((spriteNau.getX()+nau.getWidth())<SCREEN_WIDTH){
					spriteNau.setX(spriteNau.getX()+20);
				}
				break;
				/*
			case Input.Keys.DPAD_DOWN:
				if(spriteNau.getY()>120){
					spriteNau.setY(spriteNau.getY()-20);
				}
				break;
			case Input.Keys.DPAD_UP:
				if((spriteNau.getY()-nau.getHeight())<SCREEN_HEIGHT){
					spriteNau.setY(spriteNau.getY()+20);
				}
				break;*/
			case Input.Keys.DPAD_UP:
				if((spriteNau.getY()-nau.getHeight())<SCREEN_HEIGHT){
					spriteNau.setY(spriteNau.getY()+20);
				}
				break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		lastTouch.set(screenX, screenY);
		spriteNau.setX(screenX-20);
		text="SHOOT";
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		text="NEUTRAL";

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
			spriteNau.setX(screenX-20);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}

}
