package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.pcg.pcgtcg;

public class C2 extends Card {
	
	public C2()
	{
		super();
		tex = pcgtcg.manager.get("data/card2Tex.png", Texture.class);
		value = '2';
		power = 2;
		tributeValue = 1;
	}

	public void activate()
	{
		
	}
	
	public void summon()
	{
		
	}
	
	
}
