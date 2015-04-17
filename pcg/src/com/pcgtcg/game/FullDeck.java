package com.pcgtcg.game;

import java.util.ArrayList;

import com.pcgtcg.card.*;

public class FullDeck
{
    ArrayList<Card> cards;
    
    public FullDeck()
    {
        cards = new ArrayList<Card>(52);
        
        cards.add(new C2());
        cards.add(new C2());
        cards.add(new C2());
        cards.add(new C2());
        
        cards.add(new C3());
        cards.add(new C3());
        cards.add(new C3());
        cards.add(new C3());
        
        cards.add(new C4());
        cards.add(new C4());
        cards.add(new C4());
        cards.add(new C4());
        
        cards.add(new C5());
        cards.add(new C5());
        cards.add(new C5());
        cards.add(new C5());
        
        cards.add(new C6());
        cards.add(new C6());
        cards.add(new C6());
        cards.add(new C6());
        
        cards.add(new C7());
        cards.add(new C7());
        cards.add(new C7());
        cards.add(new C7());
        
        cards.add(new C8());
        cards.add(new C8());
        cards.add(new C8());
        cards.add(new C8());
        
        cards.add(new C9());
        cards.add(new C9());
        cards.add(new C9());
        cards.add(new C9());
        
        cards.add(new CT());
        cards.add(new CT());
        cards.add(new CT());
        cards.add(new CT());
        
        cards.add(new CJ());
        cards.add(new CJ());
        cards.add(new CJ());
        cards.add(new CJ());
        
        cards.add(new CQ());
        cards.add(new CQ());
        cards.add(new CQ());
        cards.add(new CQ());
        
        cards.add(new CK());
        cards.add(new CK());
        cards.add(new CK());
        cards.add(new CK());
        
        cards.add(new CA());
        cards.add(new CA());
        cards.add(new CA());
        cards.add(new CA());
    }

    public void shuffle()
    {
        ArrayList<Card> oldCards = cards;
        cards = new ArrayList<Card>(52);
        
        while (oldCards.size() > 0)
        {
            int index = (int) (Math.random()*oldCards.size());
            cards.add(oldCards.remove(index));
        }
    }
    
    public Card draw()
    {
        if (cards.size() > 0)
            return cards.remove(0);
        else
            return null;
    }
}
