package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.pcg.pcgtcg;

public class C7 extends Card {
	
	public C7()
	{
		super();
		tex = pcgtcg.manager.get("data/card7Tex.png", Texture.class);
		value = '7';
		power = 7;
		tributeValue = 1;
		
		activeDescriptor = "";
		passiveDescriptor = "";
	}

	public void activate()
	{
		
	}
	
	public void summon()
	{
		
	}
	
	
}
