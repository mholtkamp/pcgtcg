package com.pcgtcg.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pcg.pcgtcg;
import com.pcgtcg.card.*;

public class Deck {

	private final int DEFAULT_DECK_SIZE = 26;
	private final int EPOS_X = 30, EPOS_Y = pcgtcg.SCREEN_HEIGHT - 30 - 105, EPOS_WIDTH = 70, EPOS_HEIGHT = 105;
	private final int POS_X = pcgtcg.SCREEN_WIDTH - EPOS_X - EPOS_WIDTH, POS_Y = 30, POS_WIDTH = EPOS_WIDTH, POS_HEIGHT = EPOS_HEIGHT;
	
	private String curStr;
	private String allStr;
	private Texture backTex;
	
	public LinkedList<Card> curCards;
	public LinkedList<Card> allCards;
	
	public Deck()
	{
		allCards = new LinkedList<Card>();
		curCards = new LinkedList<Card>();
		backTex = pcgtcg.manager.get("data/cardBackTex.png",Texture.class);

	}

	public int cardsRemaining()
	{
		return curCards.size();
	}
	
	public void randomizeDeck()
	{
		Random rand = new Random();
		for (int i = 0; i < DEFAULT_DECK_SIZE; i++)
		{
			int roll = rand.nextInt(13);
			roll += 2;
			
			switch (roll)
			{
				case 2: 
					allCards.add(new C2());
					curCards.add(new C2());
					break;
				case 3: 
					allCards.add(new C3());
					curCards.add(new C3());
					break;
				case 4: 
					allCards.add(new C4());
					curCards.add(new C4());
					break;
				case 5: 
					allCards.add(new C5());
					curCards.add(new C5());
					break;
				case 6: 
					allCards.add(new C6());
					curCards.add(new C6());
					break;
				case 7: 
					allCards.add(new C7());
					curCards.add(new C7());
					break;
				case 8: 
					allCards.add(new C8());
					curCards.add(new C8());
					break;
				case 9: 
					allCards.add(new C9());
					curCards.add(new C9());
					break;
				case 10: 
					allCards.add(new CT());
					curCards.add(new CT());
					break;
				case 11: 
					allCards.add(new CJ());
					curCards.add(new CJ());
					break;
				case 12: 
					allCards.add(new CQ());
					curCards.add(new CQ());
					break;
				case 13: 
					allCards.add(new CK());
					curCards.add(new CK());
					break;
				case 14: 
					allCards.add(new CA());
					curCards.add(new CA());
					break;
			}
		}
		System.out.println("Deck of size " + allCards.size() + " created!");
		
	}
	
	public void add(char c)
	{
		switch(c)
		{
			case '2': 
				allCards.add(new C2());
				curCards.add(new C2());
				break;
			case '3': 
				allCards.add(new C3());
				curCards.add(new C3());
				break;
			case '4': 
				allCards.add(new C4());
				curCards.add(new C4());
				break;
			case '5': 
				allCards.add(new C5());
				curCards.add(new C5());
				break;
			case '6': 
				allCards.add(new C6());
				curCards.add(new C6());
				break;
			case '7': 
				allCards.add(new C7());
				curCards.add(new C7());
				break;
			case '8': 
				allCards.add(new C8());
				curCards.add(new C8());
				break;
			case '9': 
				allCards.add(new C9());
				curCards.add(new C9());
				break;
			case 'T': 
				allCards.add(new CT());
				curCards.add(new CT());
				break;
			case 'J': 
				allCards.add(new CJ());
				curCards.add(new CJ());
				break;
			case 'Q': 
				allCards.add(new CQ());
				curCards.add(new CQ());
				break;
			case 'K': 
				allCards.add(new CK());
				curCards.add(new CK());
				break;
			case 'A': 
				allCards.add(new CA());
				curCards.add(new CA());
				break;
		}
	}
	
	public void dealDeck(FullDeck fdeck)
	{
	    for (int i = 0; i < 26; i++)
	    {
	        curCards.add(fdeck.draw());
	        allCards.add(curCards.get(i));
	    }
	}
	
	public int getSize()
	{
		return allCards.size();
	}
	
	public int getCardsRemaining()
	{
		return curCards.size();
	}
	
	public char getCardValue(int index)
	{
		return allCards.get(index).getValue();
	}
	
	public void setCardBoxes(boolean isOwn)
	{
	    if(isOwn)
	    {
    	    for(int i = 0; i < curCards.size(); i++)
    	    {
    	        curCards.get(i).setBox(POS_X,POS_Y,POS_WIDTH,POS_HEIGHT);
    	    }
	    }
	    else
	    {
           for(int i = 0; i < curCards.size(); i++)
            {
                curCards.get(i).setBox(EPOS_X,EPOS_Y,EPOS_WIDTH,EPOS_HEIGHT);
            }
	    }
	}
	
	public void render(SpriteBatch batch, boolean isOwn)
	{
		if(curCards.size() > 0)
		{
			if(isOwn)
				batch.draw(backTex,POS_X,POS_Y,POS_WIDTH,POS_HEIGHT);
			else
				batch.draw(backTex,EPOS_X,EPOS_Y,EPOS_WIDTH,EPOS_HEIGHT);
		}
	}
	
	public void setOwn(boolean isOwn)
	{
	    for (int  i = 0; i < curCards.size(); i++)
	    {
	        curCards.get(i).setIsOwn(isOwn);
	    }
	}
}
