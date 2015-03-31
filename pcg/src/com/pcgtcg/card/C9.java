package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.pcg.pcgtcg;

public class C9 extends Card {
	
	public C9()
	{
		super();
		tex = pcgtcg.manager.get("data/card9Tex.png", Texture.class);
		value = '9';
		power = 9;
		tributeValue = 1;
		tributeCost = 1;
		
		activeDescriptor = "While face-up, prevents all other friendly summoned cards from being attacked (except other 9s).";
		passiveDescriptor = "";
	}

	public void activate()
	{
		
	}
	
	public void summon()
	{
		
	}
	
	
}
