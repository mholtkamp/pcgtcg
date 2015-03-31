package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.pcg.pcgtcg;
import com.pcgtcg.util.TargetSelector;

public class C5 extends Card {
    
    public static final int MOD_POWER = 5;
	
	public C5()
	{
		super();
		tex = pcgtcg.manager.get("data/card5Tex.png", Texture.class);
		value = '5';
		power = 5;
		tributeValue = 1;
		hasActive = true;
		hasActivateTarget = true;
	}

	public void activate()
	{
		pcgtcg.game.inGameState = pcgtcg.game.SELECT_TARGET_STATE;
		pcgtcg.game.targetSel = new TargetSelector(this,
		                                           TargetSelector.Domain.FIELD_DOMAIN,
		                                           true,
		                                           false);
	}
	
	public void summon()
	{
		
	}
	
	public void activateTarget(int numTargets, Card[] targets)
	{
	    if (numTargets == 1)
	    {
	        int pos = -1;
	        targets[0].modifyPower(MOD_POWER);
	        
	        for (int i = 0; i < pcgtcg.game.field.getSize(); i++)
	        {
	            if (targets[0] == pcgtcg.game.field.getCard(i))
	            {
	                pos = i;
	                break;
	            }
	        }
	        pcgtcg.game.netman.send("SMODPOWER." + pos + "." + MOD_POWER);
	        pcgtcg.game.addToast("" + targets[0].getValue() + " power +" + MOD_POWER);
	    }
	}
}
