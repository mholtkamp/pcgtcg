package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.pcg.pcgtcg;
import com.pcgtcg.game.Game;
import com.pcgtcg.util.TargetSelector;

public class C2 extends Card {
	
	public C2()
	{
		super();
		tex = pcgtcg.manager.get("data/card2Tex.png", Texture.class);
		value = '2';
		power = 2;
		tributeValue = 1;
		hasActive = true;
		hasActivateTarget = true;
		activeDescriptor = "Return a summoned card to its owner's hand.";
		passiveDescriptor = "";
	}

	public void activate()
	{
//	    Game game = pcgtcg.game;
//	    
//	    // Find a random enemy card to
//	    // return to their hand.
//	    if (game.efield.getSize() > 0)
//	    {
//    	    int retIndex = (int) (Math.random() * ((double) game.efield.getSize()));
//    		game.retract(retIndex);
//	    }
        pcgtcg.game.inGameState = pcgtcg.game.SELECT_TARGET_STATE;
        pcgtcg.game.targetSel = new TargetSelector(this,
                                                   TargetSelector.Domain.FIELD_DOMAIN,
                                                   true,
                                                   true);
	}
	
	public void activateTarget(int numTargets, Card[] targets)
	{
	    int pos = -1;
	    
	    // Check if card is in own field
	    for (int i = 0; i < pcgtcg.game.field.getSize(); i++)
	    {
	        if (targets[0] == pcgtcg.game.field.getCard(i))
	        {
	            pcgtcg.game.sretract(i);
	            return;
	        }
	    }
	    
	    // Check if card is on enemy field
        for (int i = 0; i < pcgtcg.game.efield.getSize(); i++)
        {
            if (targets[0] == pcgtcg.game.efield.getCard(i))
            {
                pcgtcg.game.retract(i);
                return;
            }
        }
	    
	    System.out.println("ERROR: No target for 2's active found.");
	}
	
	public void summon()
	{
		
	}
	
	
}
