package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.pcg.pcgtcg;

public class CJ extends Card {
	
	public CJ()
	{
		super();
		tex = pcgtcg.manager.get("data/cardJTex.png", Texture.class);
		value = 'J';
		power = 13;
		tributeValue = 1;
		tributeCost = 2;
		
		activeDescriptor = "Retrieve a Q, 10, or 5 from your deck.";
		passiveDescriptor = "Immune to enemy Q's active.";
	}

	public void activate()
	{
		
	}
	
	public void summon()
	{
		
	}
	
	
}
