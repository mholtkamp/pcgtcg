package com.pcgtcg.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.pcg.pcgtcg;

public class TextOption extends Option {
	
	private String text;

	
	public TextOption(String text, float x, float y)
	{
	    super(x,y);
		this.text = text;
		validColor   = new Color(0.7f, 0.68f, 1f, 1f);
		invalidColor = new Color(0.4f, 0.4f, 0.4f, 1f);
	}
	
	public void render(SpriteBatch batch)
	{
	    super.render(batch);
		
		font.setScale(1f);
		font.setColor(0f,0f,0f,1f);
		font.draw(batch, text, box.x + 12, box.y + box.height - 7);
	}
}
