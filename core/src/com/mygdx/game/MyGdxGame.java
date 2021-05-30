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
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor, Runnable {
	//CONST
	private static int MAX_ENEMICS=7;
	private static int VIDES_JUGADOR=3;
	//BATCH
	private  SpriteBatch batch;

	//ARRAYS
	private ArrayList<Bullet>conjuntTretsJugador;
	private ArrayList<Enemy>conjuntEnemics;
	private ArrayList<EnemyBullet>conjuntTretsEnemics;

	//TEXTURES
	private Texture texturaNau;
	private Texture texturaEliminat;
	private Texture texturaTret;
	private Texture texturaEnemic;

	//SPRITES
	private Sprite jugador;
	private Sprite balaAmiga;

	//BITMAPS
	private BitmapFont fontP;
	private BitmapFont font;

	//INTS
	private int contadorNave;
	private int videsJugador;
	private int ancho;
	private int nEnemics;
	private int XEn;
	private int YEn;
	private  int[] vidasE;

	//BOOLEANS
	private boolean guanyat;
	private boolean perdut;

	String fontV;

	//CLASSES
	private Player nave;
	private Bullet b;

	@Override
	public void create () {
		Gdx.input.setInputProcessor(this);
		//INICIALITZACIO
		perdut = false;
		guanyat = false;
		vidasE = new int[MAX_ENEMICS];
		contadorNave = 100;
		videsJugador = VIDES_JUGADOR;

		fontV = "Lives: ";
		font = new BitmapFont();
		fontP = new BitmapFont();

		XEn = Gdx.graphics.getWidth()/2;
		YEn = Gdx.graphics.getHeight() -80;

		//ARRAYS
		conjuntTretsJugador = new ArrayList<>();
		conjuntTretsEnemics = new ArrayList<>();
		conjuntEnemics = new ArrayList<>();

		//TEXTURES
		texturaNau = new Texture("nau1.png");
		texturaEliminat = new Texture("nau2e.png");
		texturaTret = new Texture("tret.png");
		texturaEnemic = new Texture("nau2.png");


		//JOC
		batch = new SpriteBatch();

		balaAmiga = new Sprite(texturaTret, 0,0,texturaTret.getWidth(),texturaTret.getHeight());
		jugador = new Sprite(texturaNau, 0, 0, texturaNau.getWidth(), texturaNau.getHeight());

		balaAmiga.setY(100);
		ancho = Gdx.graphics.getWidth();

		jugador.setX((ancho-jugador.getWidth())/2);
		jugador.setY(100);


		nave = new Player(jugador);

		nEnemics = 1;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		batch.begin();
		font.getData().setScale(2.5F);
		font.draw(batch, fontV + videsJugador, 10, 60);

		nave.getSprite().draw(batch);
		if(perdut)
		{
			fontP.getData().setScale(10.5F);
			fontP.getColor().set(Color.RED);
			fontP.draw(batch, "YOU LOST", Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/2);
		}
		else if(guanyat)
		{
			fontP.getData().setScale(10.5F);
			fontP.getColor().set(Color.GREEN);
			fontP.draw(batch, "YOU WON", Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/2);
		}
		else if(conjuntEnemics.size() > 0) {
			for (int y = 0; y < conjuntEnemics.size(); y++) {
				batch.draw(conjuntEnemics.get(y).getTexture(), conjuntEnemics.get(y).getX(), conjuntEnemics.get(y).getY());
			}

			if(conjuntTretsEnemics.size() > 0) {
				for (int d = 0; d < conjuntTretsEnemics.size(); d++) {
					batch.draw(conjuntTretsEnemics.get(d).getTexture(), conjuntTretsEnemics.get(d).getX(), conjuntTretsEnemics.get(d).getY());
				}
			}

			if (conjuntTretsJugador.size() > 0) {
				for (int i = 0; i < conjuntTretsJugador.size(); i++) {
					Bullet b2 = conjuntTretsJugador.get(i);
					Thread r = new Thread(b2);
					r.start();
					batch.draw(texturaTret, b2.getX(), b2.getY());

					for (int x = 0; x < conjuntEnemics.size(); x++) {
						for (int j = 0; j < conjuntTretsJugador.size(); j++) {
							if(conjuntTretsJugador.get(j).getY() > Gdx.graphics.getHeight())
							{
								conjuntTretsJugador.remove(conjuntTretsJugador.get(j));
							}
							if (conjuntEnemics.get(x).getX()  < b2.getX() && b2.getX() < conjuntEnemics.get(x).getX() + conjuntEnemics.get(x).getTexture().getWidth()) {
								if (b2.getY() + b2.getHeight()< conjuntEnemics.get(x).getY() ) {
									if (vidasE[x] != 0) {
										vidasE[x] -= 1;
									}
									else {
										conjuntEnemics.get(x).setTexture(texturaEliminat);
									}
								}
							}
						}
					}
				}
			}

			if(conjuntTretsEnemics.size() != 0 && conjuntEnemics.size() != 0)
			{
				for(int i = 0; i < conjuntTretsEnemics.size(); i++)
				{
					EnemyBullet b4 = conjuntTretsEnemics.get(i);

					if(conjuntTretsEnemics.get(i).getY() < -Gdx.graphics.getHeight() + (float)50)
					{
						conjuntTretsEnemics.remove(b4);
					}

					if (b4.getX() < nave.getSprite().getX() + nave.getSprite().getTexture().getWidth() && b4.getX() > nave.getSprite().getX()) {

						if (b4.getY() + b4.getHeight() >= 100) {
							if(nave.getSprite().getTexture() != texturaEliminat) {
								if (contadorNave <= 0) {
									videsJugador -= 1;
									contadorNave = 100;
									if (videsJugador == 0) {
										nave.getSprite().setTexture(texturaEliminat);
										perdut = true;
									}
								}
								contadorNave--;
							}
						}
					}
				}
			}
		}

		if(nEnemics < VIDES_JUGADOR) {
			nEnemics++;
			Thread t = new Thread(this);
			t.start();
		}

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		texturaNau.dispose();
		texturaEnemic.dispose();
	}

	@Override
	public void run() {

		if(conjuntEnemics.size() == 0) {
			int i = 0;
			do {
				i = conjuntEnemics.size();

				Enemy e = new Enemy(texturaEnemic, XEn, YEn, texturaEnemic.getWidth(), texturaEnemic.getHeight());

				e.setX(ancho);

				conjuntEnemics.add(e);
				vidasE[i] = 10;

				Thread trr = new Thread(conjuntEnemics.get(i));
				trr.start();


				try {
					Thread.sleep(5000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}while(i != 6);
			int c;
			do {
				c = 0;
				for(int z = 0; z < conjuntEnemics.size(); z++) {
					if (conjuntEnemics.get(z).getTexture() == texturaEliminat)
					{
						c++;
					}
				}

			}while(c != MAX_ENEMICS);
			guanyat = true;
		}
		else
		{

			boolean salir = false;
			do {
				if(conjuntEnemics.size()!= 0) {

					for (int y = 0; y < conjuntEnemics.size(); y++) {
						EnemyBullet b3 = new EnemyBullet(texturaTret, 0, 0, (int) conjuntEnemics.get(y).getWidth(), (int) conjuntEnemics.get(y).getHeight());
						int ancho = (int) conjuntEnemics.get(y).getX() + ((int) conjuntEnemics.get(y).getWidth() / 2);
						int alto = (int) conjuntEnemics.get(y).getY();
						b3.setX(ancho);
						b3.setY(alto);

						Thread tx = new Thread(b3);
						if(conjuntEnemics.get(y).getTexture() != texturaEliminat) {
							tx.start();
							conjuntTretsEnemics.add(b3);

							try {
								Thread.sleep(150);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}

			}while(!salir);
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		switch(keycode)
		{
			case Input.Keys.DPAD_LEFT:
				if(nave.getSprite().getTexture() != texturaEliminat) {
					float novaPos = nave.getSprite().getX() - 40;
					if (novaPos >= 0) {
						nave.getSprite().setX(novaPos);
					}
				}
				break;
			case Input.Keys.DPAD_RIGHT:
				if(nave.getSprite().getTexture() != texturaEliminat) {
					float novaPos2 = nave.getSprite().getX() + 40;
					if (novaPos2 <= ancho - jugador.getWidth()) {
						nave.getSprite().setX(novaPos2);
					}
				}
				break;
			case Input.Keys.DPAD_UP:
				if(nave.getSprite().getTexture() != texturaEliminat) {
					b = new Bullet(texturaTret, (int) nave.getSprite().getX(), (int) nave.getSprite().getY(), texturaTret.getWidth(), texturaTret.getHeight());
					float alt = 100;

					b.setY(alt);
					b.setX(nave.getSprite().getX() + (nave.getSprite().getWidth() / 2));
					conjuntTretsJugador.add(b);
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

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(nave.getSprite().getTexture() != texturaEliminat) {

			nave.getSprite().setX(screenX);

			b = new Bullet(texturaTret, (int) nave.getSprite().getX(), (int) nave.getSprite().getY(), texturaTret.getWidth(), texturaTret.getHeight());
			float alt = 100;

			b.setY(alt);
			b.setX(nave.getSprite().getX() + (nave.getSprite().getWidth() / 2));
			conjuntTretsJugador.add(b);

		}
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
