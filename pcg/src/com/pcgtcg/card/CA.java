package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.pcg.pcgtcg;
import com.pcgtcg.game.Game;

public class CA extends Card {
	
	public CA()
	{
		super();
		tex = pcgtcg.manager.get("data/cardATex.png", Texture.class);
		value = 'A';
		power = 20;
		tributeValue = 1;
		tributeCost = 3;
		hasActive = true;
		
		activeDescriptor = "Remove from game J/Q/K from your grave to \nspecial summon this card.";
		passiveDescriptor = "";
	}

	public void activate()
	{
        Game game = pcgtcg.game;
        boolean hasKing = false;
        boolean hasQueen = false;
        boolean hasJack = false;
        
        // Iterate through own graveyard and look for K, Q, J
        for(int i = 0; i < game.grave.getSize(); i++)
        {
            if (game.grave.getCard(i).getValue() == 'K')
            {
                hasKing = true;
            }
            if (game.grave.getCard(i).getValue() == 'Q')
            {
                hasQueen = true;
            }
            if (game.grave.getCard(i).getValue() == 'J')
            {
                hasJack = true;
            }
        }
        if (hasKing  &&
            hasQueen &&
            hasJack)
        {
            // Iterate once more, removing K/Q/J from game
            // Reusing hasKing/hasQueen/hasJack booleans
            // to indicate if a K/Q/J has been removed already.
            // Only want to remove 1 K, not all the Ks in grave.
            for (int i = 0; i < game.grave.getSize(); i++)
            {
                if (game.grave.getCard(i).getValue() == 'K' &&
                    hasKing)
                {
                    game.grave.remove(i);
                    hasKing = false;
                    i--;
                }
                if (game.grave.getCard(i).getValue() == 'Q' &&
                    hasQueen)
                {
                    game.grave.remove(i);
                    hasQueen = false;
                    i--;
                }
                if (game.grave.getCard(i).getValue() == 'J' &&
                    hasJack)
                {
                    game.grave.remove(i);
                    hasJack = false;
                    i--;
                }
            }
            
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
