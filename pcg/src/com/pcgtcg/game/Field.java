package com.pcgtcg.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pcg.pcgtcg;
import com.pcgtcg.util.AnimationEvent;

public class Field  extends Location {
	
	private final int POS_X = 200, POS_Y = 120, POS_WIDTH = 70, POS_HEIGHT = 105, POS_SPACING = POS_WIDTH + 35;
	private final int EPOS_X = POS_X, EPOS_Y = 300, EPOS_WIDTH = 70, EPOS_HEIGHT = 105, EPOS_SPACING = EPOS_WIDTH + 35;
	
	public Field()
	{
		super();
		maxSize = 4;
		
	}
	
	public Field(boolean isOwn)
	{
		super(isOwn);
		maxSize = 4;
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
		for(int i = 0; i < cards.size(); i++)
		{
				cards.get(i).render(batch);
		}
	}


}
