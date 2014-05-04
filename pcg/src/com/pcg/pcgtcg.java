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
import com.pcgtcg.menu.Menu;

public class pcgtcg implements ApplicationListener {

	public static AssetManager manager;
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 480;
	
	public static OrthographicCamera camera;
	private SpriteBatch batch;
	private Menu menu;
	
	
	public void create()
	{	
		camera = new OrthographicCamera();
		camera.setToOrtho(false, SCREEN_WIDTH, SCREEN_HEIGHT);
		batch = new SpriteBatch();
		manager = new AssetManager();
		loadAssets();
		
		menu = new Menu();

		Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1f);
	}
	
	public void dispose() {
		batch.dispose();
	}

	public void loadAssets()
	{
		manager.load("data/eras.fnt", BitmapFont.class);
		manager.load("data/rockwell.fnt",BitmapFont.class);
		manager.load("data/defaultButtonTex.png", Texture.class);

		manager.update();
		manager.finishLoading();
		
	}
	
	private void update()
	{
		
	}
	
	public void render()
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		menu.render(batch);
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
