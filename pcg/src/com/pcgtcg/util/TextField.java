package com.pcgtcg.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.pcg.pcgtcg;

public class TextField implements InputProcessor{
	
	private String label;
	private BitmapFont font;
	private Texture bgTex;
	private Rectangle box;
	
	private final float DEFAULT_WIDTH = 400;
	private final float DEFAULT_HEIGHT = 40;
	private final float X_BUFFER = 12;
	private final float Y_BUFFER = 28;
	
	private String data;
	private boolean active;
	
	public TextField(String label, float x, float y)
	{
		this.label = label;
		font = pcgtcg.manager.get("data/eras.fnt",BitmapFont.class);
		bgTex = pcgtcg.manager.get("data/grayTex.png",Texture.class);
		
		box = new Rectangle();
		box.x = x;
		box.y = y;
		box.width = DEFAULT_WIDTH;
		box.height = DEFAULT_HEIGHT;
		
		active = true;
		data = "";
		
		
	}
	

	
	public void render(SpriteBatch batch)
	{
		batch.draw(bgTex, box.x, box.y,box.width, box.height);
		font.setScale(1f);
		font.setColor(0f,0f,0f,1f);
		font.draw(batch, data, box.x + X_BUFFER , box.y + Y_BUFFER);
	}
	
	public boolean getActive()
	{
		return active;
	}
	
	public void setActive(boolean b)
	{
		this.active = b;
	}

	   @Override
	   public boolean keyDown (int keycode) {
	      return false;
	   }

	   @Override
	   public boolean keyUp (int keycode) {
	      return false;
	   }

	   @Override
	   public boolean keyTyped (char character) {
	      if((character == 8) && !data.isEmpty())
	      {
	    	  data = data.substring(0, data.length() - 1);
	      }
	      else if(character != 8)
	      {
	    	  data += character;
	      }
	      return true;
	   }

	   @Override
	   public boolean touchDown (int x, int y, int pointer, int button) {
	      return false;
	   }

	   @Override
	   public boolean touchUp (int x, int y, int pointer, int button) {
	      return false;
	   }

	   @Override
	   public boolean touchDragged (int x, int y, int pointer) {
	      return false;
	   }

	   public boolean touchMoved (int x, int y) {
	      return false;
	   }

	   @Override
	   public boolean scrolled (int amount) {
	      return false;
	   }

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public String getData()
	{
		return data.trim();
	}
}
