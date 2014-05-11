package com.pcgtcg.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pcg.pcgtcg;

public class Option implements Touchable {
	
	private String text;
	private boolean isValid;
	private Rectangle box;
	private BitmapFont font;
	private Texture validTex;
	private Texture invalidTex;
	private final int DEFAULT_WIDTH = 150;
	private final int DEFAULT_HEIGHT = 40;
	
	public Option(String text, float x, float y)
	{
		this.text = text;
		isValid = true;
		validTex = pcgtcg.manager.get("data/validOptionTex.png", Texture.class);
		invalidTex = pcgtcg.manager.get("data/invalidOptionTex.png",Texture.class);
		font = pcgtcg.manager.get("data/eras.fnt",BitmapFont.class);
		box = new Rectangle();
		box.x = x;
		box.y = y;
		box.width = DEFAULT_WIDTH;
		box.height = DEFAULT_HEIGHT;
	}
	
	
	public boolean isTouched(float x, float y)
	{
		if(isValid && box.contains(x, y))
			return true;
		else
			return false;
	}
	
	public void render(SpriteBatch batch)
	{
		if(isValid)
		{
			batch.draw(validTex,box.x,box.y,box.width,box.height);
		}
		else
		{
			batch.draw(invalidTex,box.x,box.y,box.width,box.height);
		}
		
		font.setScale(1f);
		font.setColor(0f,0f,0f,1f);
		font.draw(batch, text, box.x + 12, box.y + box.height - 7);
	}
	
	public void setValid(boolean v)
	{
		isValid = v;
	}
	
	public void setX(float x)
	{
		box.x = x;
	}
	public void setY(float y)
	{
		box.y = y;
	}
	public void setWidth(float w)
	{
		box.width = w;
	}
	
	public void setHeight(float h)
	{
		box.height = h;
	}

}
