package com.pcgtcg.game;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pcg.pcgtcg;
import com.pcgtcg.card.Card;

public abstract class Location {

	protected int maxSize;
	protected Texture backTex;
	protected LinkedList<Card> cards;
	protected boolean isOwn;
	
	
	public Location()
	{
		cards = new LinkedList<Card>();
		backTex = pcgtcg.manager.get("data/cardBackTex.png", Texture.class);
		maxSize = 3;
		
	}
	
	public Location(boolean isOwn)
	{
		this();
		this.isOwn = isOwn;
	}

	public boolean add(Card c)
	{
		if(cards.size() < maxSize)
		{
			cards.add(c);
			updatePosition();
			return true;
		}
		else
			return false;
	}
	
	public Card remove(int i)
	{
		Card ret = cards.remove(i);
		ret.ClearStatus();
		updatePosition();
		return ret;
	}
	
	protected abstract void updatePosition();
	public abstract void render(SpriteBatch batch);
	
	public int getSize()
	{
		return cards.size();
	}
	
	public int getMaxSize()
	{
		return maxSize;
	}
	
	public Card getCard(int i)
	{
		return cards.get(i);
	}
}
