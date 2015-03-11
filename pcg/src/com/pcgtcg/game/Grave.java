package com.pcgtcg.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.pcg.pcgtcg;
import com.pcgtcg.card.Card;
import com.pcgtcg.util.AnimationEvent;

public class Grave extends Location {
	
	private final int POS_X = 30, POS_Y = 30, POS_WIDTH = 70, POS_HEIGHT = 105;
	private final int EPOS_X = pcgtcg.SCREEN_WIDTH - POS_X - POS_WIDTH, EPOS_Y = pcgtcg.SCREEN_HEIGHT - POS_Y - POS_HEIGHT, EPOS_WIDTH = POS_WIDTH, EPOS_HEIGHT = POS_HEIGHT;
	private Rectangle box;
	
	public Grave()
	{
		super();
		maxSize = 3;
	}
	
	public Grave(boolean isOwn)
	{
		super(isOwn);
		maxSize = 0xff;
		if(isOwn)
			box = new Rectangle(POS_X,POS_Y,POS_WIDTH,POS_HEIGHT);
		else
			box = new Rectangle(EPOS_X,EPOS_Y,EPOS_WIDTH, EPOS_HEIGHT);
	}
	
	public void updatePosition()
	{   
	    if (cards.size() == 0)
	        return;
	    
	    AnimationEvent event = new AnimationEvent();
	    event.setTarget(cards.getLast());
        if(isOwn)
        {
            event.setDestination(POS_X,
                                 POS_Y,
                                 POS_WIDTH,
                                 POS_HEIGHT,
                                 AnimationEvent.DEFAULT_TIME);
        }
        else
        {
            event.setDestination(EPOS_X,
                                 EPOS_Y,
                                 EPOS_WIDTH,
                                 EPOS_HEIGHT,
                                 AnimationEvent.DEFAULT_TIME);
        }
        pcgtcg.game.animationQueue.add(event);
	}
	
	public void render(SpriteBatch batch)
	{
		if(cards.size() > 0)
		{
		    // Draw the last two cards
	        Card lastCard = cards.getLast();
	        Rectangle lastBox = lastCard.getBox();
	        
	        if (cards.size() > 1)
	        {
	            batch.draw(cards.get(cards.size() - 2).getTexture(),box.x,box.y,box.width,box.height);
	        }
			batch.draw(lastCard.getTexture(),lastBox.x,lastBox.y,lastBox.width,lastBox.height);
		}
	}
}
