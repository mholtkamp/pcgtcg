package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.pcg.pcgtcg;

public class C8 extends Card {
	
	public C8()
	{
		super();
		tex = pcgtcg.manager.get("data/card8Tex.png", Texture.class);
		value = '8';
		power = 8;
		tributeValue = 1;
		tributeCost = 1;
	}

	public void activate()
	{
		
	}
	
	public void summon()
	{
		
	}
	
	
}
