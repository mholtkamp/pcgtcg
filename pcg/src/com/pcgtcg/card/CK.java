package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.pcg.pcgtcg;
import com.pcgtcg.game.Game;
import com.pcgtcg.util.TargetSelector;

public class CK extends Card {
	
	public CK()
	{
		super();
		tex = pcgtcg.manager.get("data/cardKTex.png", Texture.class);
		value = 'K';
		power = 17;
		tributeValue = 1;
		tributeCost = 2;
		hasActive = true;
		hasActivateTarget = true;
		
		activeDescriptor = "If your life is less than the enemy's life, \nsummon a Q or lower from your hand at 0 tribute cost.";
		passiveDescriptor = "";
	}

	public void activate()
	{
	    if (pcgtcg.game.player.life < pcgtcg.game.eplayer.life &&
	        !pcgtcg.game.hasSummoned)
	    {
    	    pcgtcg.game.inGameState = pcgtcg.game.SELECT_TARGET_STATE;
            pcgtcg.game.targetSel = new TargetSelector(this,
    	                                               TargetSelector.Domain.HAND_DOMAIN,
    	                                               true,
    	                                               false);
	    }
	}
	
	public void summon()
	{
		
	}
	
    public void activateTarget(int numTargets, Card[] targets)
    {
        if (numTargets == 1)
        {
            if (targets[0].getValue() != 'K' &&
                targets[0].getValue() != 'A')
            {
                pcgtcg.game.summon(targets[0]);
            }
        }
    }
    
}
