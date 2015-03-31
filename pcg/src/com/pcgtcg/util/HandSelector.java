package com.pcgtcg.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.pcg.pcgtcg;
import com.pcgtcg.card.Card;
import com.pcgtcg.util.*;

public class HandSelector extends OptionSelector {
	
	private Option summonOption;
	private Option activateOption;
	private Option setOption;
	private Option cancelOption;
	private Card card;
	
	public HandSelector(Card card)
	{
		super();
		this.card = card;
		
		summonOption = new Option("Summon",12,300);
		setOption = new Option("Set", 12, 250);
		activateOption = new Option("Activate", 12, 200);
		cancelOption = new Option("Cancel", 12, 150);
		
		options.add(summonOption);
		options.add(setOption);
		options.add(activateOption);
		options.add(cancelOption);
		
		if(!card.hasActive())
			activateOption.setValid(false);
		if(pcgtcg.game.hasSummoned || ((pcgtcg.game.field.getSize() >= pcgtcg.game.field.getMaxSize()) && (card.getTributeCost() == 0)))
		{
			summonOption.setValid(false);
			setOption.setValid(false);
		}	
	}
	
	public void update()
	{
		if(Gdx.input.justTouched())
		{
			Vector3 touchPos = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
			pcgtcg.camera.unproject(touchPos);
			float tx = touchPos.x;
			float ty = touchPos.y;
			
			if(summonOption.isTouched(tx, ty))
			{
				System.out.println("SummonOption is touched");
				if(card.getTributeCost() == 0)
				{
					pcgtcg.game.summon(card);
					pcgtcg.game.inGameState = pcgtcg.game.PLAY_STATE;
				}
				else
				{
					pcgtcg.game.tribSel = new TributeSelector(card,true);
					pcgtcg.game.inGameState = pcgtcg.game.TRIB_OPT_STATE;
				}
			}
			else if(cancelOption.isTouched(tx, ty))
			{
				pcgtcg.game.inGameState = pcgtcg.game.PLAY_STATE;
			}
			else if(setOption.isTouched(tx, ty))
			{
				if(card.getTributeCost() == 0)
				{
					pcgtcg.game.set(card);
					pcgtcg.game.inGameState = pcgtcg.game.PLAY_STATE;
				}
				else
				{
					pcgtcg.game.tribSel = new TributeSelector(card,false);
					pcgtcg.game.inGameState = pcgtcg.game.TRIB_OPT_STATE;
				}
			}
			else if (activateOption.isTouched(tx, ty))
			{
			    pcgtcg.game.activate(card);
			    if (!card.hasActivateTarget())
			        pcgtcg.game.inGameState = pcgtcg.game.PLAY_STATE;
			}
		}
	}
	
	
	

}
