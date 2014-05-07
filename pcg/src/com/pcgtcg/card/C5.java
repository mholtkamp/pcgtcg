package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.pcg.pcgtcg;

public class C5 extends Card {
	
	public C5()
	{
		super();
		tex = pcgtcg.manager.get("data/card5Tex.png", Texture.class);
		value = '5';
		power = 5;
		tributeValue = 1;
	}

	public void activate()
	{
		
	}
	
	public void summon()
	{
		
	}
	
	
}
