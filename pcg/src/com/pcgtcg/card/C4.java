package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.pcg.pcgtcg;
import com.pcgtcg.game.Game;

public class C4 extends Card {
	
	public C4()
	{
		super();
		tex = pcgtcg.manager.get("data/card4Tex.png", Texture.class);
		value = '4';
		power = 4;
		tributeValue = 1;
		activeDescriptor = "Natural power doubles when the sum of the natural powers of the enemy field are a multiple of 4.";
		passiveDescriptor = "";
	}

	public void activate()
	{
		
	}
	
	public void summon()
	{
		
	}
	
	
}
