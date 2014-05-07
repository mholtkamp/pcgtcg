package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.pcg.pcgtcg;

public class CK extends Card {
	
	public CK()
	{
		super();
		tex = pcgtcg.manager.get("data/cardKTex.png", Texture.class);
		value = 'K';
		power = 17;
		tributeValue = 1;
	}

	public void activate()
	{
		
	}
	
	public void summon()
	{
		
	}
	
	
}
