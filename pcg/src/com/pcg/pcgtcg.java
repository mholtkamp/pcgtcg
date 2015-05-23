package com.pcg;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pcgtcg.game.Game;
import com.pcgtcg.menu.Menu;
import com.pcgtcg.network.NetworkManager;

public class pcgtcg implements ApplicationListener {

	//Game States
	public static final int NULL_STATE = -1, INIT_STATE = 0, MENU_STATE = 1, GAME_STATE = 2;
	
	//Public Access
	public static AssetManager manager;
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 480;
	public static OrthographicCamera camera;
	
	//Private Objects
	private SpriteBatch batch;
	public static Menu menu;
	public static Game game;
	public static NetworkManager netman;
	
	//State
	public static int gameState;
	
	// Configured options
	public static String name = "Player";
	
	public void create()
	{	
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		batch = new SpriteBatch();
		manager = new AssetManager();
		loadAssets();
		
		menu = new Menu();
		gameState = MENU_STATE;
		
		//Gdx.gl.glClearColor(0.04f, 0.04f, 0.12f, 1f);
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
	}
	
	public void dispose() {
		batch.dispose();
		if(netman != null)
			netman.close();
		manager.dispose();
	}

	public void loadAssets()
	{
		manager.load("data/eras.fnt", BitmapFont.class);
		manager.load("data/rockwell.fnt",BitmapFont.class);
		manager.load("data/defaultButtonTex.png", Texture.class);
		
		//Cards
		manager.load("data/card2Tex.png",Texture.class);
		manager.load("data/card3Tex.png",Texture.class);
		manager.load("data/card4Tex.png",Texture.class);
		manager.load("data/card5Tex.png",Texture.class);
		manager.load("data/card6Tex.png",Texture.class);
		manager.load("data/card7Tex.png",Texture.class);
		manager.load("data/card8Tex.png",Texture.class);
		manager.load("data/card9Tex.png",Texture.class);
		manager.load("data/cardTTex.png",Texture.class);
		manager.load("data/cardJTex.png",Texture.class);
		manager.load("data/cardQTex.png",Texture.class);
		manager.load("data/cardKTex.png",Texture.class);
		manager.load("data/cardATex.png",Texture.class);
		manager.load("data/cardXTex.png",Texture.class);
		manager.load("data/cardBackTex.png",Texture.class);
		
		// Other 
		manager.load("data/grayTex.png",Texture.class);
		manager.load("data/blackTex.png", Texture.class);
		manager.load("data/blueTex.png",Texture.class);
		manager.load("data/whiteTex.png", Texture.class);
		manager.load("data/validOptionTex.png",Texture.class);
		manager.load("data/invalidOptionTex.png",Texture.class);
		manager.load("data/shield.png",Texture.class);
		manager.load("data/sword.png",Texture.class);
		manager.load("data/staticbg.png", Texture.class);
		manager.load("data/scrollingbg.png", Texture.class);
		manager.load("data/downarrow.png", Texture.class);
		manager.load("data/uparrow.png", Texture.class);
		manager.load("data/p1color.png", Texture.class);
		manager.load("data/p2color.png", Texture.class);
		manager.load("data/star.png", Texture.class);
		manager.update();
		manager.finishLoading();
		
	}
	
	private void update()
	{
		if(gameState == MENU_STATE)
			menu.update();
		else if(gameState == GAME_STATE)
			game.update();
	}
	
	public void render()
	{ 
		update();
		batch.setProjectionMatrix(camera.combined);
		
		if (gameState == MENU_STATE)
		    Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		else
		    Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		
		if(gameState == MENU_STATE)
			menu.render(batch);
		else if(gameState == GAME_STATE)
			game.render(batch);
		
		batch.end();
	}
	
	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
	
}
