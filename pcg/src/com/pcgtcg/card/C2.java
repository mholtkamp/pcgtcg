package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.pcg.pcgtcg;
import com.pcgtcg.game.Game;

public class C2 extends Card {
	
	public C2()
	{
		super();
		tex = pcgtcg.manager.get("data/card2Tex.png", Texture.class);
		value = '2';
		power = 2;
		tributeValue = 1;
		hasActive = true;
		activeDescriptor = "Return a summoned card to owner's hand.";
		passiveDescriptor = "";
	}

	public void activate()
	{
	    Game game = pcgtcg.game;
	    
	    // Find a random enemy card to
	    // return to their hand.
	    if (game.efield.getSize() > 0)
	    {
    	    int retIndex = (int) (Math.random() * ((double) game.efield.getSize()));
    		game.retract(retIndex);
	    }
	}
	
	public void summon()
	{
		
	}
	
	
}
