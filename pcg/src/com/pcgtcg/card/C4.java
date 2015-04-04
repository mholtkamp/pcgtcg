package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pcg.pcgtcg;
import com.pcgtcg.game.Field;
import com.pcgtcg.game.Game;

public class C4 extends Card {
	
	public C4()
	{
		super();
		tex = pcgtcg.manager.get("data/card4Tex.png", Texture.class);
		value = '4';
		power = 4;
		tributeValue = 1;
		activeDescriptor = "";
		passiveDescriptor = "Gains 4 power when in defense position.";
	}

	public void activate()
	{
		
	}
	
	public void summon()
	{
		
	}
	
	public int getPower()
	{   
	    return attackPosition ? (power + powerModifier) : (power + powerModifier + 4);
	}
}
