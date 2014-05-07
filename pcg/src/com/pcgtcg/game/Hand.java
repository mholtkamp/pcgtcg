package com.pcgtcg.game;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pcg.pcgtcg;
import com.pcgtcg.card.Card;

public class Hand {
	
	private final int MAX_SIZE = 7;
	private final int POS_X = 200, POS_Y = 0, POS_WIDTH = 50, POS_HEIGHT = 75, POS_SPACING = POS_WIDTH;
	private final int EPOS_X = POS_X, EPOS_Y = 440, EPOS_WIDTH = 50, EPOS_HEIGHT = 75, EPOS_SPACING = EPOS_WIDTH;
	private Texture backTex;
	private LinkedList<Card> cards;
	private boolean isOwn;
	
	
	public Hand()
	{
		cards = new LinkedList<Card>();
		backTex = pcgtcg.manager.get("data/cardBackTex.png", Texture.class);
		
	}
	
	public Hand(boolean isOwn)
	{
		this();
		this.isOwn = isOwn;
	}

	public boolean add(Card c)
	{
		if(cards.size() < MAX_SIZE)
		{
			cards.add(c);
			updatePosition();
			return true;
		}
		else
			return false;
	}
	
	private void updatePosition()
	{
		for(int i = 0; i < cards.size(); i++)
		{
			cards.get(i).setBox(POS_X + POS_SPACING*i, POS_Y, POS_WIDTH, POS_HEIGHT);
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
