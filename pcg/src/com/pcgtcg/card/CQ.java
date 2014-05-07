package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.pcg.pcgtcg;

public class CQ extends Card {
	
	public CQ()
	{
		super();
		tex = pcgtcg.manager.get("data/cardQTex.png", Texture.class);
		value = 'Q';
		power = 15;
		tributeValue = 1;
	}

	public void activate()
	{
		
	}
	
	public void summon()
	{
		
	}
	
	
}
