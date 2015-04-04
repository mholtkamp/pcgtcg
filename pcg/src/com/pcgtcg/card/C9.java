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
		hasActive = true;
		
		activeDescriptor = "Draw a card.";
		passiveDescriptor = "Cannot be destroyed by battle.";
	}

	public void activate()
	{
		pcgtcg.game.draw();
	}
	
	public void summon()
	{
		
	}
	
	
}
