package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.pcg.pcgtcg;
import com.pcgtcg.game.Game;

public class C3 extends Card {
	
    private final int enemyMinPower = 8;
	public C3()
	{
		super();
		tex = pcgtcg.manager.get("data/card3Tex.png", Texture.class);
		value = '3';
		power = 3;
		tributeValue = 1;
		hasActive = true;
		activeDescriptor = "If there is an 8 or higher on the enemy field, special summon this card.";
		passiveDescriptor = "When summoned, lower the power of all cards on the enemy field by 3.";
	}

	public void activate()
	{
		Game game = pcgtcg.game;
		boolean canSpecialSummon = false;
		
		// Iterate through opponents field and look for
		// any cards that are 8 or higher
		for(int i = 0; i < game.efield.getSize(); i++)
		{
		    if(game.efield.getCard(i).power >= enemyMinPower)
		    {
		        canSpecialSummon = true;
		        break;
		    }
		}
		
		if (canSpecialSummon)
		{
		    // Special summon the 3 (do not count towards hasSummoned
		    boolean prevHasSummoned = game.hasSummoned;
		    game.summon(this);
		    game.hasSummoned = prevHasSummoned;
		    
		}
	}
	
	public void summon()
	{
		
	}
	
	
}
