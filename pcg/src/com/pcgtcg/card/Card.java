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
	protected int powerModifier;
	protected int tributeValue;
	protected boolean hasActive;
	protected boolean hasActivateTarget;
	protected int tributeCost;
	protected boolean attackPosition;
	protected boolean visible;
	protected boolean hasAttacked;
	protected String location;
	protected Texture tex;
	protected Texture backTex;
	protected Texture swordTex;
	protected Texture shieldTex;
	protected Texture whiteTex;
	
	private Rectangle box;
	
	// Power Modification variables for pulsating effect
	private static final float POWER_MOD_PULSE_TIME = 0.5f;
	private float powerModTime;
	
	public Card()
	{
		box = new Rectangle();
		box.x = 50;
		box.y = 50;
		box.width = 100;
		box.height = 150;
		tributeCost = 0;
		hasActive = false;
		hasActivateTarget = false;
		hasAttacked = false;
		visible = true;
		attackPosition = true;
		powerModifier = 0;
		powerModTime = 0.0f;
		backTex   = pcgtcg.manager.get("data/cardBackTex.png", Texture.class);
		swordTex  = pcgtcg.manager.get("data/sword.png",Texture.class);
		shieldTex = pcgtcg.manager.get("data/shield.png",Texture.class);
		whiteTex  = pcgtcg.manager.get("data/whiteTex.png", Texture.class);
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
		
		// Render Power Modification pulse effect
		if (powerModifier > 0)
		{
		    powerModTime -= Gdx.graphics.getDeltaTime();
		    if(powerModTime < 0.0f)
		        powerModTime = POWER_MOD_PULSE_TIME;
		    batch.setColor(0.7f, 1f, 0.7f, powerModTime/POWER_MOD_PULSE_TIME);
		    batch.draw(whiteTex, box.x, box.y, box.width, box.height);
		    batch.setColor(1f,1f,1f,1f);
		}
		else if (powerModifier < 0)
		{
            powerModTime -= Gdx.graphics.getDeltaTime();
            if(powerModTime < 0.0f)
                powerModTime = POWER_MOD_PULSE_TIME;
            batch.setColor(1.0f, 0.7f, 0.7f, powerModTime/POWER_MOD_PULSE_TIME);
            batch.draw(whiteTex, box.x, box.y, box.width, box.height);
            batch.setColor(1f,1f,1f,1f);
		}
	}

	public abstract void summon();
	public abstract void activate();
	
	public void activateTarget(int numTargets, Card[] targets)
	{
	    System.out.println("activateTarget invoked on card without an implementation.\n");
	}
	
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
		return power + powerModifier;
	}
	
	public void modifyPower(int modPower)
	{
	    powerModifier += modPower;
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
	
	public boolean hasActivateTarget()
	{
	    return hasActivateTarget;
	}
	
	public int getTributeCost()
	{
		return tributeCost;
	}
	
	public Texture getTexture()
	{
		return tex;
	}
	
    public void ClearStatus()
    {
        attackPosition = true;
        visible = true;
        powerModifier = 0;
    }
}
