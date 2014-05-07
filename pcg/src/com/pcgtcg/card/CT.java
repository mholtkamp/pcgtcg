package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.pcg.pcgtcg;

public class CT extends Card {
	
	public CT()
	{
		super();
		tex = pcgtcg.manager.get("data/cardTTex.png", Texture.class);
		value = 'T';
		power = 10;
		tributeValue = 1;
	}

	public void activate()
	{
		
	}
	
	public void summon()
	{
		
	}
	
	
}
