package com.pcgtcg.game;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pcg.pcgtcg;
import com.pcgtcg.card.Card;
import com.pcgtcg.util.AnimationEvent;

public class Hand extends Location {
	
	private final int POS_X = 200, POS_Y = 0, POS_WIDTH = 50, POS_HEIGHT = 75, POS_SPACING = POS_WIDTH;
	private final int EPOS_X = POS_X, EPOS_Y = 445, EPOS_WIDTH = 50, EPOS_HEIGHT = 75, EPOS_SPACING = EPOS_WIDTH;
	
	public Hand()
	{
		super();
		maxSize = 7;
		
	}
	
	public Hand(boolean isOwn)
	{
		super(isOwn);
		maxSize = 7;
	}

	protected void updatePosition()
	{
		if(isOwn)
		{
			for(int i = 0; i < cards.size(); i++)
			{
	            AnimationEvent event = new AnimationEvent();
                event.setTarget(cards.get(i));
                
                event.setDestination(POS_X + POS_SPACING*i,
                                     POS_Y,
                                     POS_WIDTH,
                                     POS_HEIGHT,
                                     AnimationEvent.DEFAULT_TIME);
                pcgtcg.game.animationQueue.add(event);
			}
		}
		else
		{
			for(int i = 0; i < cards.size(); i++)
			{
                AnimationEvent event = new AnimationEvent();
                event.setTarget(cards.get(i));
                
                event.setDestination(EPOS_X + EPOS_SPACING*i,
                                     EPOS_Y,
                                     EPOS_WIDTH,
                                     EPOS_HEIGHT,
                                     AnimationEvent.DEFAULT_TIME);
                pcgtcg.game.animationQueue.add(event);
			}
		}
	}
	   
	public void render(SpriteBatch batch)
	{
		if(isOwn)
		{
			for(int i = 0; i < cards.size(); i++)
				cards.get(i).render(batch);
		}
		else
		{
			for(int i = 0; i < cards.size(); i++)
				batch.draw(backTex, EPOS_X + EPOS_SPACING*i, EPOS_Y, EPOS_WIDTH, EPOS_HEIGHT);
		}
	}
}

