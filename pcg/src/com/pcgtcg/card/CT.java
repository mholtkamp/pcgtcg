package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.pcg.pcgtcg;
import com.pcgtcg.game.Game;

public class CT extends Card {
	
	public CT()
	{
		super();
		tex = pcgtcg.manager.get("data/cardTTex.png", Texture.class);
		value = 'T';
		power = 10;
		tributeValue = 1;
		tributeCost = 1;
		hasActive = true;
		
		activeDescriptor = "Return a selected card from your grave to your hand.";
		passiveDescriptor = "";
	}

	public void activate()
	{
		Game game = pcgtcg.game;
		int pos = -1;
		
		if (game.grave.getSize() > 0)
		{
			pos = (int) Math.random()*game.grave.getSize();
			game.sregenerate(pos);
		}
	}
	
	public void summon()
	{
		
	}
}
