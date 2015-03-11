package com.pcgtcg.card;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.pcg.pcgtcg;
import com.pcgtcg.util.Animated;
import com.pcgtcg.util.Touchable;

public abstract class Card implements Touchable , Animated {
	
	protected char value;
	protected int power;
	protected int tributeValue;
	protected boolean hasActive;
	protected int tributeCost;
	protected boolean attackPosition;
	protected boolean visible;
	protected boolean hasAttacked;
	protected String location;
	protected Texture tex;
	protected Texture backTex;
	protected Texture swordTex;
	protected Texture shieldTex;
	
	private Rectangle box;
	
	public Card()
	{
		box = new Rectangle();
		box.x = 50;
		box.y = 50;
		box.width = 100;
		box.height = 150;
		tributeCost = 0;
		hasActive = false;
		hasAttacked = false;
		visible = true;
		attackPosition = true;
		backTex   = pcgtcg.manager.get("data/cardBackTex.png", Texture.class);
		swordTex  = pcgtcg.manager.get("data/sword.png",Texture.class);
		shieldTex = pcgtcg.manager.get("data/shield.png",Texture.class);
	}
	
	public void render(SpriteBatch batch)
	{
		if(attackPosition && visible)
		{
			batch.draw(tex, box.x, box.y,box.width, box.height);
			//batch.draw(swordTex, box.x, box.y);
		}
		else if(!attackPosition && visible)
		{
			batch.draw(tex, box.x, box.y,box.width, box.height);
			batch.setColor(1.0f, 1.0f, 1.0f, 0.5f);
			batch.enableBlending();
			batch.draw(shieldTex, box.x, box.y + box.height/4.0f, 64.0f, 64.0f);
			batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		}
		else if(!attackPosition && !visible)
		{
			batch.draw(backTex, box.x, box.y,box.width, box.height);
			batch.setColor(1.0f, 1.0f, 1.0f, 0.5f);
            batch.enableBlending();
            batch.draw(shieldTex, box.x, box.y + box.height/4.0f, 64.0f, 64.0f);
            batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		}
	}

	public abstract void summon();
	public abstract void activate();
	
	public char getValue()
	{
		return value;
	}
	
	public void setAttackPosition(boolean p)
	{
		attackPosition = p;
	}
	
	public void toggleAttackPosition()
	{
		attackPosition = !attackPosition;
	}
	
	public boolean inAttackPosition()
	{
		return attackPosition;
	}
	
	public void setVisible(boolean v)
	{
		visible = v;
	}
	
	public boolean isVisible()
	{
		return visible;
	}
	
	public void setHasAttacked(boolean h)
	{
		hasAttacked = h;
	}
	
	public boolean hasAttacked()
	{
		return hasAttacked;
	}
	
	public int getPower()
	{
		return power;
	}
	
	public int getTributeValue()
	{
		return tributeValue;
	}
	
	public void setBox(int x, int y, int width, int height)
	{
		box.x = x;
		box.y = y;
		box.width = width;
		box.height = height;
	}
	
	public Rectangle getBox()
	{
		return box;
	}
	
	public boolean isTouched(float x, float y)
	{
		if(box.contains(x, y))
			return true;
		else
			return false;
	}
	
	public boolean hasActive()
	{
		return hasActive;
	}
	
	public int getTributeCost()
	{
		return tributeCost;
	}
	
	public Texture getTexture()
	{
		return tex;
	}
}
