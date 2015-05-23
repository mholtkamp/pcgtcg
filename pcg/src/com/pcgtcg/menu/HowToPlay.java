package com.pcgtcg.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.pcg.pcgtcg;
import com.pcgtcg.card.*;

public class HowToPlay 
{
    private float offset;
    private float tex;
    
    private BitmapFont font;
    
    private static float h1x = 20;
    private static float h2x = 30;
    private static float h3x = 150;
    
    private static float h1scale = 1.4f;
    private static float h2scale = 0.8f;
    
    private static Color h1color;
    private static Color h2color;
    
    private static float minOffset = 480.0f;
    private static float maxOffset = 3000.0f;
    
    private float downY;
    private boolean hasBeenTouched;
    
    private Texture whiteTex;
    
    private static Texture tC2;
    private static Texture tC3;
    private static Texture tC4;
    private static Texture tC5;
    private static Texture tC6;
    private static Texture tC7;
    private static Texture tC8;
    private static Texture tC9;
    private static Texture tCT;
    private static Texture tCJ;
    private static Texture tCQ;
    private static Texture tCK;
    private static Texture tCA;
    
    public HowToPlay()
    {
        offset = pcgtcg.SCREEN_HEIGHT;
        font = pcgtcg.manager.get("data/eras.fnt", BitmapFont.class);
        h1color = new Color();
        h2color = new Color();
        h1color.set(1.0f, 0.35f, 0.35f, 1.0f);
        h2color.set(1.0f, 1.0f, 1.0f, 1.0f);
        
        downY = 0.0f;
        hasBeenTouched = false;
        
        whiteTex = pcgtcg.manager.get("data/whiteTex.png", Texture.class);
        
        tC2 = pcgtcg.manager.get("data/card2Tex.png", Texture.class);
        tC3 = pcgtcg.manager.get("data/card3Tex.png", Texture.class);
        tC4 = pcgtcg.manager.get("data/card4Tex.png", Texture.class);
        tC5 = pcgtcg.manager.get("data/card5Tex.png", Texture.class);
        tC6 = pcgtcg.manager.get("data/card6Tex.png", Texture.class);
        tC7 = pcgtcg.manager.get("data/card7Tex.png", Texture.class);
        tC8 = pcgtcg.manager.get("data/card8Tex.png", Texture.class);
        tC9 = pcgtcg.manager.get("data/card9Tex.png", Texture.class);
        tCT = pcgtcg.manager.get("data/cardTTex.png", Texture.class);
        tCJ = pcgtcg.manager.get("data/cardJTex.png", Texture.class);
        tCQ = pcgtcg.manager.get("data/cardQTex.png", Texture.class);
        tCK = pcgtcg.manager.get("data/cardKTex.png", Texture.class);
        tCA = pcgtcg.manager.get("data/cardATex.png", Texture.class);
    }
    
    public void render(SpriteBatch batch)
    {
        batch.setColor(0.2f, 0.2f, 0.2f, 1.0f);
        batch.draw(whiteTex, 0.0f, 0.0f, pcgtcg.SCREEN_WIDTH, pcgtcg.SCREEN_HEIGHT);
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        
        //*************
        // How To Play
        //*************
        font.setColor(h1color);
        font.setScale(h1scale);
        font.draw(batch, "How To Play", h1x, offset - 50);
        
        font.setColor(h1color);
        font.setScale(h2scale);
        font.draw(batch, "(Touch to Scroll)", 600, offset - 50);
        
        font.setColor(h2color);
        font.setScale(h2scale);
        font.draw(batch, 
                  "PCG the TCG is a card game that plays like a trading card game.",
                  h2x, offset - 100);
        font.draw(batch,
                  "Players take turns summoning cards to attack their opponent.",
                  h2x, offset - 130);
        font.draw(batch,
                  "The objective of the game is to reduce the opponent's life to 0.",
                  h2x, offset - 160);
        
        //*****************
        // The Battlefield
        //*****************
        font.setColor(h1color);
        font.setScale(h1scale);
        font.draw(batch,
                  "The Battlefield",
                  h1x, offset - 210);
        
        font.setColor(h2color);
        font.setScale(h2scale);
        font.draw(batch,
                  "The Deck - Each player draws their cards from their own deck. In",
                  h2x, offset - 260); 
        font.draw(batch,
                  "standard play, each player has a randomly dealt 26 card deck.", 
                  h2x, offset - 290);
        font.draw(batch,
                  "The Hand - When a player draws cards from their deck, they will be",
                  h2x, offset - 350);
        font.draw(batch,
                  "placed into their hand. At the beginning of the game, the player",
                  h2x, offset - 380);
        font.draw(batch,
                  "who plays their turn first starts with 4 cards. The player who plays",
                  h2x, offset - 410);
        font.draw(batch,
                  "second starts with 5 cards. The game randomly determines which",
                  h2x, offset - 440);
        font.draw(batch,
                  "player will go first. Only 7 cards can be held in the hand.",
                  h2x, offset - 470);
        font.draw(batch,
                  "The Field - a player can summon one card per turn from their hand",
                  h2x, offset - 530);
        font.draw(batch,
                  "to the field. Only cards that have been summoned to the field can",
                  h2x, offset - 560);
        font.draw(batch,
                  "attack the opponent. There can only be 4 cards on the field at any time.",
                  h2x, offset - 590);
        font.draw(batch,
                  "The Grave - cards that are destroyed on the field or discarded from",
                  h2x, offset - 650);
        font.draw(batch,
                  "the hand will be sent to the grave. Cards in the grave cannot be used.",
                  h2x, offset - 680);
        
        //***************
        // Game Mechanics
        //***************
        font.setColor(h1color);
        font.setScale(h1scale);
        font.draw(batch,
                  "Game Mechanics",
                  h1x, offset - 730);
        
        font.setColor(h2color);
        font.setScale(h2scale);
        
        // Drawing
        font.draw(batch,
                  "At the start of each turn, the player draws a card from their deck.",
                  h2x, offset - 780);
        font.draw(batch,
                  "If the hand is full, the leftmost card is discarded from the hand.",
                  h2x, offset - 810);
        font.draw(batch,
                  "If there are no remaining cards in the deck, you will lose the game.",
                  h2x, offset - 840);
        font.draw(batch,
                  "While it is your turn, you can summon or activate cards. All cards",
                  h2x, offset - 900);
        font.draw(batch,
                  "can be summoned, but not all cards can be activated.",
                  h2x, offset - 930);
        
        // Summoning
        font.draw(batch,
                  "Summoning - a card can be summoned from the hand by touching",
                  h2x, offset - 990);
        font.draw(batch,
                  "the card, and then selecting Summon from the option list. 8's and",
                  h2x, offset - 1020);
        font.draw(batch,
                  "higher require tributes in order to be summoned to the field. The",
                  h2x, offset - 1050);
        font.draw(batch,
                  "number of tributes required to summon a card is listed in the card",
                  h2x, offset - 1080);
        font.draw(batch,
                  "info pane (after touching a card in your hand). A tribute is a",
                  h2x, offset - 1110);
        font.draw(batch,
                  "card that is already on your field that will be discarded in order to",
                  h2x, offset - 1140);
        font.draw(batch,
                  "summon the selected card from your hand.",
                  h2x, offset - 1170);
        
        // Attacking
        font.draw(batch,
                  "Attacking - Any card on your field can attack the opponent once",
                  h2x, offset - 1230);
        font.draw(batch,
                  "per turn. If there are no cards on the enemy field, you will attack",
                  h2x, offset - 1260);
        font.draw(batch,
                  "the opponent directly, subtracting the attacking card's power from",
                  h2x, offset - 1290);
        font.draw(batch,
                  "the enemy's life. If the enemy has summoned cards on their field,",
                  h2x, offset - 1320);
        font.draw(batch,
                  "then the attacking card must attack one of them. Whichever card has",
                  h2x, offset - 1350);
        font.draw(batch,
                  "lower power will be destroyed and sent to the respective players grave.",
                  h2x, offset - 1380);
        font.draw(batch,
                  "The difference in power between cards will be dealt to the player whose",
                  h2x, offset - 1410);
        font.draw(batch,
                  "card was destroyed (assuming both cards are in attack position).",
                  h2x, offset - 1440);
        
        // Battle Position
        font.draw(batch,
                  "Battle Positions - a summoned card can be in either attack or defense",
                  h2x, offset - 1500);
        font.draw(batch,
                  "position. A card can only attack if it is in attack position (default).",
                  h2x, offset - 1530);
        font.draw(batch,
                  "If a card is toggled to defense position, then the player will not take",
                  h2x, offset - 1560);
        font.draw(batch,
                  "damage to their life as a result of it being destroyed by battle. To toggle",
                  h2x, offset - 1590);
        font.draw(batch,
                  "the battle position of a card, touch a card on your field and select the",
                  h2x, offset - 1620);
        font.draw(batch,
                  "Toggle option. Only cards that have not attacked on the current",
                  h2x, offset - 1650);
        font.draw(batch,
                  "turn can be toggled.",
                  h2x, offset - 1680);
        
        // Setting
        font.draw(batch,
                  "Setting - In addition to summoning, a card can be set instead.",
                  h2x, offset - 1740);
        font.draw(batch,
                  "When a card is set, it is placed face-down on the field in defense",
                  h2x, offset - 1770);
        font.draw(batch,
                  "position. The card identity will only be revealed if it is toggled.",
                  h2x, offset - 1800);
        font.draw(batch,
                  "To set a card, touch the card in your hand and select the Set option.",
                  h2x, offset - 1830);
        
        // Activate
        font.draw(batch,
                  "Active Abilities - certain cards can be played directly from the",
                  h2x, offset - 1890);
        font.draw(batch,
                  "hand. When a card is activated, its active effect will be executed.",
                  h2x, offset - 1920);
        font.draw(batch,
                  "When a card is activated it will be discarded (except for A and 3).",
                  h2x, offset - 1950);
        font.draw(batch,
                  "There is no limit to the number of cards that can be activated per turn.",
                  h2x, offset - 1980);
                  
         // Passive Abilites
        font.draw(batch,
                  "Passive Abilities - certain cards have passive abilties. These",
                  h2x, offset - 2040);
        font.draw(batch,
                  "abilties are effective while the card is on the field.",
                  h2x, offset - 2070);
        
        // Card List
        font.setColor(h1color);
        font.setScale(h1scale);
        font.draw(batch,
                  "Card List",
                  h1x, offset - 2120);
        
        font.setScale(h2scale);
        font.setColor(h2color);
        
        batch.draw(tC2, h2x, offset - 2330);
        font.draw(batch,
                  "Active: Return a summoned card to its owner's hand.",
                  h3x, offset - 2200);
        
        batch.draw(tC3, h2x, offset - 2530);
        font.draw(batch,
                  "Active: If there is an 8 or higher on the enemy",
                  h3x, offset - 2400);
        font.draw(batch,
                  "field, special summon this card.",
                  h3x + 80, offset - 2430);
        font.draw(batch,
                  "Passive: When summoned, lower the power of all",
                  h3x, offset - 2460);
        font.draw(batch,
                  "cards on the enemy field by 3.",
                  h3x + 80, offset - 2490);
                  
        
                  
                  
        
    }
    
    public void update()
    {
        if(Gdx.input.justTouched())
        {
            Vector3 touchPos = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            pcgtcg.camera.unproject(touchPos);
            downY = touchPos.y;
            hasBeenTouched = true;
        }
        else if (Gdx.input.isTouched() && hasBeenTouched)
        {
            Vector3 touchPos = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            pcgtcg.camera.unproject(touchPos);
            offset += touchPos.y - downY;
            downY = touchPos.y;
            
            if (offset > maxOffset)
                offset = maxOffset;
            else if (offset < minOffset)
                offset = minOffset;
        }
    }
    
    public void reset()
    {
        offset = 0.0f;
        hasBeenTouched = false;
    }
}
