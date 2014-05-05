package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Card {
	
	protected char value;
	protected int power;
	protected int tributeValue;
	
	protected String location;
	protected Texture tex;
	
	private Rectangle box;
	
	public Card()
	{
		box = new Rectangle();
		box.x = 50;
		box.y = 50;
		box.width = 100;
		box.height = 150;
	}
	
	public void render(SpriteBatch batch)
	{
		batch.draw(tex, box.x, box.y,box.width, box.height);
	}

	public abstract void summon();
	public abstract void activate();
	
	

}
