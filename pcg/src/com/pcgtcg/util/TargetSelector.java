package com.pcgtcg.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.pcg.pcgtcg;
import com.pcgtcg.card.Card;
import com.pcgtcg.game.Field;
import com.pcgtcg.game.Hand;

//**********************************************************
// The target selector is a general selector class that 
// selects a target for a Card's activated ability.
// 2, 4, 7, 10 and others might use this selector.
// This selector will have the ability to be configured to
// select a field card, grave card, deck card, or hand card.
//
// Friendly/Enemy flags can be configured for the selector 
// so that a player can be restricted to only target a 
// friendly or enemy card.
//
// Field and hand selection can be performed without
// rendering cards in different locations, but deck and
// grave selections will be spread out on the screen based
// on the type of selector.
//
// Number of selected targets can be configured and a 
// flag can be set to indicate if this is an exact
// number (i.e. select 3 cards vs. select up to 3 cards.
//**********************************************************
public class TargetSelector extends OptionSelector {
    
    public enum Domain 
    {
        FIELD_DOMAIN,
        HAND_DOMAIN,
        GRAVE_DOMAIN,
        DECK_DOMAIN
    }
    
    private Card card;
    private Card target;
    
    private Domain domain;
    private boolean flagFriendly;
    private boolean flagEnemy;
    
    private TextOption targetOption;
    private TextOption cancelOption;
    private Texture blueTex;
    
    public TargetSelector(Card    card,
                          Domain  domain,
                          boolean flagFriendly,
                          boolean flagEnemy)
    {
        super();
        
        this.card         = card;
        this.target       = null;
        this.domain       = domain;
        this.flagEnemy    = flagEnemy;
        this.flagFriendly = flagFriendly;
        
        targetOption = new TextOption("Target", 12, 250);
        cancelOption = new TextOption("Cancel", 12, 200);
        
        options.add(targetOption);
        options.add(cancelOption);
        
        targetOption.setValid(false);
        blueTex = pcgtcg.manager.get("data/blueTex.png",Texture.class);
    }
    
    public void update()
    {
        if(Gdx.input.justTouched())
        {
            Vector3 touchPos = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            pcgtcg.camera.unproject(touchPos);
            float tx = touchPos.x;
            float ty = touchPos.y;
            
            if (targetOption.isTouched(tx, ty))
            {
                // Remove card from hand first.
                // Removing the card first is important for 2's active
                // or it could possibly cause a position glitch if 
                // a card is returned to your own hand.
                Hand hand = pcgtcg.game.hand;
                for(int i = 0; i < hand.getSize(); i++)
                {
                    if(hand.getCard(i) == card)
                    {
                        pcgtcg.game.sdiscard(i);
                    }
                }
                
                // Call the activateTaget handler.
                // This is currently hardcoded to 1.
                // TODO: Unhardcode to support multiple targets
                Card[] targets = new Card[1];
                targets[0] = target;
                card.activateTarget(1, targets);
                
                pcgtcg.game.inGameState = pcgtcg.game.PLAY_STATE;
            }
            else if(cancelOption.isTouched(tx, ty))
            {
                pcgtcg.game.inGameState = pcgtcg.game.PLAY_STATE;
            }
            
            // Scan the proper domain for a selected target
            switch(domain)
            {
            case FIELD_DOMAIN:
                Field field = pcgtcg.game.field;
                Field efield = pcgtcg.game.efield;
                if (flagFriendly)
                {
                    for (int i = 0; i < field.getSize(); i++)
                    {
                        if(field.getCard(i).isTouched(tx, ty))
                        {
                            target = field.getCard(i);
                            targetOption.setValid(true);
                        }
                    }
                }
                if (flagEnemy)
                {
                    for (int i = 0; i < efield.getSize(); i++)
                    {
                        if(efield.getCard(i).isTouched(tx, ty))
                        {
                            target = efield.getCard(i);
                            targetOption.setValid(true);
                        }
                    }
                }
                break;
                
            case HAND_DOMAIN:
                Hand hand = pcgtcg.game.hand;
                Hand ehand = pcgtcg.game.ehand;
                
                if (flagFriendly)
                {
                    for (int i = 0; i < hand.getSize(); i++)
                    {
                        if(hand.getCard(i).isTouched(tx, ty))
                        {
                            target = hand.getCard(i);
                            targetOption.setValid(true);
                        }
                    }
                }
                if (flagEnemy)
                {
                    for (int i = 0; i < ehand.getSize(); i++)
                    {
                        if(ehand.getCard(i).isTouched(tx, ty))
                        {
                            target = ehand.getCard(i);
                            targetOption.setValid(true);
                        }
                    }
                }
                break;
                
            case GRAVE_DOMAIN:
                // TODO
                break;
            case DECK_DOMAIN:
                // TODO
                break;
                
            }
        }
    }
    
    public void render(SpriteBatch batch)
    {
        super.render(batch);
        
        switch(domain)
        {
        case FIELD_DOMAIN:
            Field field = pcgtcg.game.field;
            Field efield = pcgtcg.game.efield;
            if (flagFriendly)
            {
                for (int i = 0; i < field.getSize(); i++)
                {
                    field.getCard(i).render(batch);
                    if(field.getCard(i) == target)
                    {
                        Rectangle cbox = field.getCard(i).getBox();
                        batch.draw(blueTex,cbox.x,cbox.y,cbox.width,cbox.height);
                    }
                }
            }
            if (flagEnemy)
            {
                for (int i = 0; i < efield.getSize(); i++)
                {
                    efield.getCard(i).render(batch);
                    if(efield.getCard(i) == target)
                    {
                        Rectangle cbox = efield.getCard(i).getBox();
                        batch.draw(blueTex,cbox.x,cbox.y,cbox.width,cbox.height);
                    }
                }
            }
            break;
            
        case HAND_DOMAIN:
            Hand hand = pcgtcg.game.hand;
            Hand ehand = pcgtcg.game.ehand;
            
            if (flagFriendly)
            {
                for (int i = 0; i < hand.getSize(); i++)
                {
                    hand.getCard(i).render(batch);
                    if(hand.getCard(i) == target)
                    {
                        Rectangle cbox = hand.getCard(i).getBox();
                        batch.draw(blueTex,cbox.x,cbox.y,cbox.width,cbox.height);
                    }
                }
            }
            if (flagEnemy)
            {
                for (int i = 0; i < ehand.getSize(); i++)
                {
                    ehand.getCard(i).render(batch);
                    if(ehand.getCard(i) == target)
                    {
                        Rectangle cbox = ehand.getCard(i).getBox();
                        batch.draw(blueTex,cbox.x,cbox.y,cbox.width,cbox.height);
                    }
                }
            }
            break;
            
        case GRAVE_DOMAIN:
            // TODO
            break;
        case DECK_DOMAIN:
            // TODO
            break;
            
        }
    }
}
