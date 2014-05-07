package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.pcg.pcgtcg;

public class C6 extends Card {
	
	public C6()
	{
		super();
		tex = pcgtcg.manager.get("data/card6Tex.png", Texture.class);
		value = '6';
		power = 6;
		tributeValue = 2;
	}

	public void activate()
	{
		
	}
	
	public void summon()
	{
		
	}
	
	
}
