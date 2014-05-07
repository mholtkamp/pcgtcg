package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.pcg.pcgtcg;

public class C3 extends Card {
	
	public C3()
	{
		super();
		tex = pcgtcg.manager.get("data/card3Tex.png", Texture.class);
		value = '3';
		power = 3;
		tributeValue = 1;
	}

	public void activate()
	{
		
	}
	
	public void summon()
	{
		
	}
	
	
}
