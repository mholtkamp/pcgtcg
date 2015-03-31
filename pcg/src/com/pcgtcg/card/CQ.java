package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.pcg.pcgtcg;
import com.pcgtcg.game.Game;

public class CQ extends Card {
	
	public CQ()
	{
		super();
		tex = pcgtcg.manager.get("data/cardQTex.png", Texture.class);
		value = 'Q';
		power = 15;
		tributeValue = 1;
		tributeCost = 2;
		hasActive = true;
		
		activeDescriptor = "Send all cards on both players' fields to their respective graves.";
		passiveDescriptor = "";
	}

	public void activate()
	{
		Game game = pcgtcg.game;
		
		// Clear all cards on the enemy field.
		while(game.efield.getSize() > 0)
		{
			game.kill(0);
		}
		
		// Clear all cards on own field.
		while(game.field.getSize() > 0)
		{
			game.skill(0);
		}
	}
	
	public void summon()
	{
		
	}
	
	
}
