package com.pcgtcg.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.pcg.pcgtcg;
import com.pcgtcg.card.Card;
import com.pcgtcg.game.Field;

public class FieldSelector extends OptionSelector {
	
	private Card card;
	private Option attackOption;
	private Option toggleOption;
	private Option cancelOption;
	
	public FieldSelector(Card card)
	{
		super();
		this.card = card;
		
		attackOption = new Option("Attack",12,300);
		toggleOption = new Option("Toggle", 12, 250);
		cancelOption = new Option("Cancel", 12, 150);
		
		options.add(attackOption);
		options.add(toggleOption);
		options.add(cancelOption);
		
		if (!card.inAttackPosition())
		    attackOption.setValid(false);
//		else if(pcgtcg.game.efield.getSize() == 0)
//            attackOption.setValid(true);
		
		if(card.hasAttacked() || pcgtcg.game.isFirstTurn)
		{
			attackOption.setValid(false);
			toggleOption.setValid(false);
		}
			
	}
	
	public void update()
	{
		if(Gdx.input.justTouched())
		{
			Field f = pcgtcg.game.field;
			Vector3 touchPos = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
			pcgtcg.camera.unproject(touchPos);
			float tx = touchPos.x;
			float ty = touchPos.y;
			
			if(attackOption.isTouched(tx, ty))
			{
				if(pcgtcg.game.efield.getSize() == 0)
				{
					card.setHasAttacked(true);
					pcgtcg.game.hasPlayerAttacked = true;
					pcgtcg.game.damage(card.getPower());
					pcgtcg.game.inGameState = pcgtcg.game.PLAY_STATE;
					
		            // Show toast for attack and notify opponent
	                pcgtcg.game.addToast("" + card.getValue() + " DIR ATK");
				}
				else
				{
					pcgtcg.game.inGameState = pcgtcg.game.ATT_TARGET_STATE;
					pcgtcg.game.attackSel = new AttackSelector(card);
				}
			}
			else if(toggleOption.isTouched(tx, ty))
			{
				for(int i = 0; i < f.getSize(); i++)
				{
					if(f.getCard(i) == card)
					{
						pcgtcg.game.toggle(i);
						pcgtcg.game.inGameState = pcgtcg.game.PLAY_STATE;
					}
				}
			}
			else if(cancelOption.isTouched(tx, ty))
			{
				pcgtcg.game.inGameState = pcgtcg.game.PLAY_STATE;
			}

		}
	}

}
