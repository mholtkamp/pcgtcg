package com.pcgtcg.game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import com.pcgtcg.card.*;

public class Deck {

	private final int DEFAULT_DECK_SIZE = 26;
	private String curStr;
	private String allStr;
	
	private LinkedList<Card> curCards;
	private LinkedList<Card> allCards;
	
	public Deck()
	{
		allCards = new LinkedList<Card>();
		curCards = new LinkedList<Card>();

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
					allCards.add(new C10());
					curCards.add(new C10());
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
}
