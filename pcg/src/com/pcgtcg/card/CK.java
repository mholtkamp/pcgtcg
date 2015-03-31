package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.pcg.pcgtcg;
import com.pcgtcg.game.Game;

public class CK extends Card {
	
	public CK()
	{
		super();
		tex = pcgtcg.manager.get("data/cardKTex.png", Texture.class);
		value = 'K';
		power = 17;
		tributeValue = 1;
		tributeCost = 2;
		
		activeDescriptor = "If your life is less than the enemy's life, \nsummoning cards cost one less tribute this turn (stacks).";
		passiveDescriptor = "";
	}

	public void activate()
	{
		
	}
	
	public void summon()
	{
		
	}
	
	
}
