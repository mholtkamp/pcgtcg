package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.pcg.pcgtcg;

public class CX extends Card {
	
	public CX()
	{
		super();
		tex = pcgtcg.manager.get("data/cardXTex.png", Texture.class);
		value = 'X';
		power = 0;
		tributeValue = 0;
	}

	public void activate()
	{
		
	}
	
	public void summon()
	{
		
	}
	
	
}
