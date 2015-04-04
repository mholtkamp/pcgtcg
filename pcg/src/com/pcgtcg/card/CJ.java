package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.pcg.pcgtcg;

public class CJ extends Card {
	
	public CJ()
	{
		super();
		tex = pcgtcg.manager.get("data/cardJTex.png", Texture.class);
		value = 'J';
		power = 13;
		tributeValue = 1;
		tributeCost = 2;
		hasActive = true;
		
		activeDescriptor = "Retrieve a Q, 10, or 5 from your deck.";
		passiveDescriptor = "Immune to enemy Q's active.";
	}

	public void activate()
	{	
		// Go through the deck and return the first 
		// 5/T/Q that is found.
		for (int i = 0; i < pcgtcg.game.player.deck.curCards.size(); i++)
		{
			Card card = pcgtcg.game.player.deck.curCards.get(i);
			char val = card.getValue();
			
			if (val == '5' ||
				val == 'T' ||
				val == 'Q')
			{
				pcgtcg.game.sretrieve(i);
				break;
			}
		}
	}
	
	public void summon()
	{
		
	}
}
