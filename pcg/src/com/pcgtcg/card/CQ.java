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
		
		activeDescriptor = "Send all cards on both players' fields to their \nrespective graves.";
		passiveDescriptor = "";
	}

	public void activate()
	{
		Game game = pcgtcg.game;
		
		// Clear all cards on the enemy field.
		for( int i = 0; i < game.efield.getSize(); i++)
		{
		    if (game.efield.getCard(i).getValue() != '5' &&
		        game.efield.getCard(i).getValue() != 'J')
		    {
		        game.kill(i);
		        i--;
		    }
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
