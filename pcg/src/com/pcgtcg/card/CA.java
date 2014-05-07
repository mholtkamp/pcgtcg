package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.pcg.pcgtcg;

public class CA extends Card {
	
	public CA()
	{
		super();
		tex = pcgtcg.manager.get("data/cardATex.png", Texture.class);
		value = 'A';
		power = 20;
		tributeValue = 1;
	}

	public void activate()
	{
		
	}
	
	public void summon()
	{
		
	}
	
	
}
