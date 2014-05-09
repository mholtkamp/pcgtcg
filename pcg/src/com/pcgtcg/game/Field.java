package com.pcgtcg.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Field  extends Location {
	
	private final int POS_X = 200, POS_Y = 120, POS_WIDTH = 70, POS_HEIGHT = 105, POS_SPACING = POS_WIDTH + 35;
	private final int EPOS_X = POS_X, EPOS_Y = 300, EPOS_WIDTH = 70, EPOS_HEIGHT = 105, EPOS_SPACING = EPOS_WIDTH + 35;
	
	public Field()
	{
		super();
		maxSize = 3;
		
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
				if(cards.get(i).inAttackPosition())
					cards.get(i).setBox(POS_X + POS_SPACING*i, POS_Y, POS_WIDTH, POS_HEIGHT);
				else
				{
					cards.get(i).setBox(POS_X + POS_SPACING*i + (POS_WIDTH + POS_SPACING)/2, POS_Y + 16, POS_WIDTH, POS_HEIGHT);
					cards.get(i).setFBox(POS_X + POS_SPACING*i + (POS_WIDTH + POS_SPACING)/2 - POS_HEIGHT, POS_Y + 16, POS_HEIGHT, POS_WIDTH);
				}
			}
		}
		else
		{
			for(int i = 0; i < cards.size(); i++)
			{
				if(cards.get(i).inAttackPosition())
					cards.get(i).setBox(EPOS_X + EPOS_SPACING*i, EPOS_Y, EPOS_WIDTH, EPOS_HEIGHT);
				else
				{
					cards.get(i).setBox(EPOS_X + EPOS_SPACING*i + (EPOS_WIDTH + EPOS_SPACING)/2, EPOS_Y + 16, EPOS_WIDTH, EPOS_HEIGHT);
					cards.get(i).setFBox(EPOS_X + EPOS_SPACING*i + (EPOS_WIDTH + EPOS_SPACING)/2 - EPOS_HEIGHT, EPOS_Y + 16, EPOS_HEIGHT, EPOS_WIDTH);
				}
					
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
