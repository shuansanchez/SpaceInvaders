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
	//BATCH
	private  SpriteBatch batch;

	//ARRAYS
	private ArrayList<Bullet>balas;
	private ArrayList<Enemy>enemigosNaves;
	private ArrayList<EnemyBullet>balasE;

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
	private BitmapFont fontVidas;

	//INTS
	private int contadorNave;
	private int vidasNave;
	private int ancho;
	private int contadorEne;
	private int disparoC;
	private int XEn;
	private int YEn;


	String fontV;




	private  int[] vidasE;
	private boolean perdido;

	//CLASSES
	private Player nave;
	private Bullet b;

	private boolean bol;
	private boolean acabado;


	@Override
	public void create () {
		Gdx.input.setInputProcessor(this);
		Gdx.input.setInputProcessor(this);
		//INICIALITZACIO
		perdido = false;
		acabado = false;
		vidasE = new int[7];
		contadorNave = 100;
		vidasNave = 3;

		fontV = "Lives: ";
		font = new BitmapFont();
		fontVidas = new BitmapFont();
		fontP = new BitmapFont();

		XEn = Gdx.graphics.getWidth()/2;
		YEn = Gdx.graphics.getHeight() -80;

		//ARRAYS
		balas = new ArrayList<>();
		balasE = new ArrayList<>();
		enemigosNaves = new ArrayList<>();

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

		disparoC = 0;
		contadorEne = 1;
		bol = false;

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		batch.begin();
		font.getData().setScale(2.5F);
		font.draw(batch, fontV + vidasNave, 10, 60);

		nave.getSprite().draw(batch);
		if(perdido)
		{
			fontP.getData().setScale(10.5F);
			fontP.getColor().set(Color.RED);
			fontP.draw(batch, "YOU LOST", Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/2);
		}
		if(acabado)
		{
			fontP.getData().setScale(10.5F);
			fontP.getColor().set(Color.GREEN);
			fontP.draw(batch, "YOU WON", Gdx.graphics.getWidth()/10, Gdx.graphics.getHeight()/2);
		}
		if(enemigosNaves.size() > 0) {
			for (int y = 0; y < enemigosNaves.size(); y++) {
				batch.draw(enemigosNaves.get(y).getTexture(), enemigosNaves.get(y).getX(), enemigosNaves.get(y).getY());


			}
			if(balasE.size() > 0) {
				for (int d = 0; d < balasE.size(); d++) {
					batch.draw(balasE.get(d).getTexture(), balasE.get(d).getX(), balasE.get(d).getY());
				}
			}
			if (balas.size() > 0) {
				for (int i = 0; i < balas.size(); i++) {
					Bullet b2 = balas.get(i);
					Thread r = new Thread(b2);
					r.start();
					batch.draw(texturaTret, b2.getX(), b2.getY());


					for (int x = 0; x < enemigosNaves.size(); x++) {
						for (int j = 0; j < balas.size(); j++) {
							if(balas.get(j).getY() > Gdx.graphics.getHeight())
							{
								balas.remove(balas.get(j));
							}
							if (enemigosNaves.get(x).getX()  < b2.getX() && b2.getX() < enemigosNaves.get(x).getX() + enemigosNaves.get(x).getTexture().getWidth()) {
								if (b2.getY() + b2.getHeight()< enemigosNaves.get(x).getY() ) {
									if (vidasE[x] != 0) {
										vidasE[x] -= 1;
									}
									else {
										enemigosNaves.get(x).setTexture(texturaEliminat);
									}
								}
							}
						}
					}
				}
			}

			if(balasE.size() != 0 && enemigosNaves.size() != 0)
			{
				for(int i = 0; i < balasE.size(); i++)
				{
					EnemyBullet b4 = balasE.get(i);

					if(balasE.get(i).getY() < -Gdx.graphics.getHeight() + (float)50)
					{
						balasE.remove(b4);
					}

					if (b4.getX() < nave.getSprite().getX() + nave.getSprite().getTexture().getWidth() && b4.getX() > nave.getSprite().getX()) {

						if (b4.getY() + b4.getHeight() >= 100) {
							if(nave.getSprite().getTexture() != texturaEliminat) {
								if (contadorNave <= 0) {
									vidasNave -= 1;
									contadorNave = 100;
									if (vidasNave == 0) {
										nave.getSprite().setTexture(texturaEliminat);
										perdido = true;
									}
								}
								contadorNave--;
							}
						}
					}
				}
			}
		}

		if(contadorEne <3) {
			contadorEne++;
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

		if(enemigosNaves.size() == 0) {
			int i = 0;
			do {

				i = enemigosNaves.size();

				Enemy e = new Enemy(texturaEnemic, XEn, YEn, texturaEnemic.getWidth(), texturaEnemic.getHeight());

				e.setX(ancho);

				enemigosNaves.add(e);
				vidasE[i] = 10;

				Thread trr = new Thread(enemigosNaves.get(i));
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
				for(int z = 0; z < enemigosNaves.size(); z++) {
					if (enemigosNaves.get(z).getTexture() == texturaEliminat)
					{
						c++;
					}
				}

			}while(c != 7);
			acabado = true;
		}
		else
		{

			boolean salir = false;
			do {
				if(enemigosNaves.size()!= 0) {

					for (int y = 0; y < enemigosNaves.size(); y++) {
						EnemyBullet b3 = new EnemyBullet(texturaTret, 0, 0, (int) enemigosNaves.get(y).getWidth(), (int) enemigosNaves.get(y).getHeight());
						int ancho = (int) enemigosNaves.get(y).getX() + ((int) enemigosNaves.get(y).getWidth() / 2);
						int alto = (int) enemigosNaves.get(y).getY();
						b3.setX(ancho);
						b3.setY(alto);

						Thread tx = new Thread(b3);
						if(enemigosNaves.get(y).getTexture() != texturaEliminat) {
							tx.start();
							balasE.add(b3);

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
					disparoC = 1;
					b = new Bullet(texturaTret, (int) nave.getSprite().getX(), (int) nave.getSprite().getY(), texturaTret.getWidth(), texturaTret.getHeight());
					float alt = 100;

					b.setY(alt);
					b.setX(nave.getSprite().getX() + (nave.getSprite().getWidth() / 2));
					balas.add(b);
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
			balas.add(b);
			disparoC = 1;

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
