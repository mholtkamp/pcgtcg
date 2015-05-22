package com.pcgtcg.game;

import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.pcg.pcgtcg;
import com.pcgtcg.card.Card;
import com.pcgtcg.menu.Menu;
import com.pcgtcg.network.Client;
import com.pcgtcg.network.NetworkManager;
import com.pcgtcg.network.Server;
import com.pcgtcg.util.AnimationQueue;
import com.pcgtcg.util.AttackSelector;
import com.pcgtcg.util.FieldSelector;
import com.pcgtcg.util.HandSelector;
import com.pcgtcg.util.HistoryQueue;
import com.pcgtcg.util.StarFactory;
import com.pcgtcg.util.TextOption;
import com.pcgtcg.util.HistoryToast;
import com.pcgtcg.util.TargetSelector;
import com.pcgtcg.util.TributeSelector;
import com.pcgtcg.network.LANServer;

public class Game {

	public final int NULL_STATE = -1, INIT_STATE = 0, DRAW_STATE = 1, PLAY_STATE = 2, ACCEPT_STATE = 3; 
	public final int HAND_OPT_STATE = 4, FIELD_OPT_STATE = 5, TRIB_OPT_STATE = 6, ATT_TARGET_STATE = 7;
	public final int GAME_OVER_STATE = 8;
	public final int SELECT_TARGET_STATE = 9;
	public final int ONE_STATE = 1, TWO_STATE = 2;
	
	public int turnState;
	public int inGameState;
	private int testStatus;
	public boolean hasWon;
	public boolean gameOver;
	public boolean isFirstTurn;
	public boolean hasPlayerAttacked;
	
	public Texture blackTex;
	public BitmapFont font;
	public Texture staticBgTex;
	public Texture scrollingBgTex;
	private float scrollX = 0.0f;
	
	//Player + Locations
	public Player player;
	public Player eplayer;
	public Hand hand;
	public Hand ehand;
	public Field field;
	public Field efield;
	public Grave grave;
	public Grave egrave;
	
	//Options
	private HandSelector handSel;
	public TextOption endOpt;
	public TextOption quitOpt;
	public TextOption rematchOpt;
	public TextOption menuOpt;
	public TributeSelector tribSel;
	public FieldSelector fieldSel;
	public AttackSelector attackSel;
	public TargetSelector targetSel;
	
	public boolean hasSummoned;
	public int playerNum;
	public int firstTurn;
	public AnimationQueue animationQueue;
	public HistoryQueue history;
	
	public boolean rematchIntent;
	public boolean rematchRequest;
	
	public StarFactory starFactory;
	
	public Game()
	{
		player  = new Player();
		eplayer = new Player();
		hand    = new Hand(true);
		ehand   = new Hand(false);
		field   = new Field(true);
		efield  = new Field(false);
		grave   = new Grave(true);
		egrave  = new Grave(false);
		
		hasSummoned = false;
		hasWon = false;
		gameOver = false;
		hasPlayerAttacked = false;
		isFirstTurn = false;
		
		// Options
		endOpt = new TextOption("End Turn",650,220);
		endOpt.setValid(false);
		quitOpt = new TextOption("X",760,440);
		quitOpt.setWidth(40);
		rematchOpt = new TextOption("Rematch", 250, 150);
		rematchOpt.setValid(true);
		menuOpt = new TextOption("Menu", 450, 150);
		menuOpt.setValid(true);
		
		blackTex       = pcgtcg.manager.get("data/blackTex.png",Texture.class);
		font           = pcgtcg.manager.get("data/eras.fnt",BitmapFont.class);
		staticBgTex    = pcgtcg.manager.get("data/staticbg.png", Texture.class);
		scrollingBgTex = pcgtcg.manager.get("data/scrollingbg.png",Texture.class);
		animationQueue = new AnimationQueue();
		history        = new HistoryQueue();
		
		rematchIntent  = false;
		rematchRequest = false;
		
		starFactory = new StarFactory();
	}
	
	public Game(boolean isHost)
	{
		this();
		if(isHost)
		{
			playerNum = 1;
			FullDeck fdeck = new FullDeck();
			fdeck.shuffle();
			player.deck.dealDeck(fdeck);
			player.deck.setOwn(true);
			eplayer.deck.dealDeck(fdeck);
			eplayer.deck.setOwn(false);
			player.deck.setCardBoxes(true);
			eplayer.deck.setCardBoxes(false);
			if(Math.random() > 0.5)
			{
				firstTurn = 1;
				isFirstTurn = true;
			}
			else
				firstTurn = 2;
			inGameState = INIT_STATE;
		}
		else
		{
			playerNum = 2;
			inGameState = ACCEPT_STATE;
		}
	}
	
	public void update()
	{
	    // Update scrolling background
//	    scrollX += Gdx.graphics.getDeltaTime() * 20.0f;
//	    if (scrollX >= pcgtcg.SCREEN_WIDTH*2)
//	        scrollX -= pcgtcg.SCREEN_WIDTH*2;
	    
	    starFactory.update();
	    
	    // Update all animations
	    animationQueue.update();
	    
		if(inGameState == INIT_STATE)
		{
			String d1 = "DECKONE";
			for(int i = 0; i < player.deck.getSize(); i++)
			{
				d1 += ("." + player.deck.getCardValue(i));				
			}
			
			String d2 = "DECKTWO";
			for(int i = 0; i < eplayer.deck.getSize(); i++)
			{
				d2 += ("." + eplayer.deck.getCardValue(i));
			}
			
			String ft = "FIRSTTURN." + firstTurn;
			
			pcgtcg.netman.send(d1);
			pcgtcg.netman.send(d2);
			pcgtcg.netman.send(ft);
			
			if(firstTurn == 1)
			{
				draw();
				draw();
				draw();
				fdraw();
				fdraw();
				fdraw();
				fdraw();
				inGameState = DRAW_STATE;
			}
			else
			{
				draw();
				draw();
				draw();
				draw();
				fdraw();
				fdraw();
				fdraw();
				end();
			}
			
			
		}
		else if(inGameState == ACCEPT_STATE)
		{
			pcgtcg.netman.poll();
			if(Gdx.input.justTouched())
			{
			Vector3 touchPos = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
			pcgtcg.camera.unproject(touchPos);
			float tx = touchPos.x;
			float ty = touchPos.y;
			
			//if(quitOpt.isTouched(tx, ty))
			//	quit();
			}
		}
		else if(inGameState == DRAW_STATE)
		{
			draw();
			endOpt.setValid(true);
			hasSummoned = false;
			inGameState = PLAY_STATE;
			
			
			System.out.println("isFirstTurn: " + isFirstTurn);
			System.out.println("firstTurn: " + firstTurn);
		}
		else if(inGameState == PLAY_STATE)
		{
			if(gameOver)
				inGameState = GAME_OVER_STATE;
			
			if(Gdx.input.justTouched())
			{
				Vector3 touchPos = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
				pcgtcg.camera.unproject(touchPos);
				float tx = touchPos.x;
				float ty = touchPos.y;
				
				//Check Hand
				for(int i = 0; i < hand.getSize(); i++)
				{
					if(hand.getCard(i).isTouched(tx, ty))
					{
						inGameState = HAND_OPT_STATE;
						handSel = new HandSelector(hand.getCard(i));
						System.out.println("Card touched!");
						break;
					}
				}
				
				//Check Field
				for(int i =0; i < field.getSize(); i++)
				{
					if(field.getCard(i).isTouched(tx,ty))
					{
						inGameState = FIELD_OPT_STATE;
						fieldSel = new FieldSelector(field.getCard(i));
						break;
					}
				}
				
				//Check End Button
				if(endOpt.isTouched(tx, ty))
				{
					end();
				}
			}

		}
		else if(inGameState == HAND_OPT_STATE)
		{
			handSel.update();
		}
		else if(inGameState == TRIB_OPT_STATE)
		{
			tribSel.update();
		}
		else if(inGameState == FIELD_OPT_STATE)
		{
			fieldSel.update();
		}
		else if(inGameState == ATT_TARGET_STATE)
		{
			attackSel.update();
		}
		else if (inGameState == SELECT_TARGET_STATE)
		{
		    targetSel.update();
		}
		else if (inGameState == GAME_OVER_STATE)
		{
            if(Gdx.input.justTouched())
            {
                Vector3 touchPos = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
                pcgtcg.camera.unproject(touchPos);
                float tx = touchPos.x;
                float ty = touchPos.y;
                
                if(rematchOpt.isTouched(tx, ty))
                {
                    rematchIntent = true;
                    pcgtcg.netman.send("REMATCH");
                }
                
                if(menuOpt.isTouched(tx, ty))
                {
                    pcgtcg.netman.close();
                    pcgtcg.gameState = pcgtcg.MENU_STATE;
                    pcgtcg.game = null;
                    pcgtcg.menu = new Menu();
                }
            }
            
            pcgtcg.netman.poll();
            
            if (rematchIntent &&
                rematchRequest)
            {
                reset();
            }
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.Q))
		{
			pcgtcg.netman.close();
			Gdx.app.exit();
		}
		
		history.update();
	}
	
	public void render(SpriteBatch batch)
	{
	    // Render Backgrounds
	    //batch.draw(scrollingBgTex, scrollX, 0);
	    //batch.draw(scrollingBgTex, scrollX - pcgtcg.SCREEN_WIDTH*2, 0);
	    starFactory.render(batch);
	    batch.draw(staticBgTex, 0, 0);
	    
		//Render Locations
		hand.render(batch);
		ehand.render(batch);
		field.render(batch);
		efield.render(batch);
		grave.render(batch);
		egrave.render(batch);
		player.deck.render(batch,true);
		eplayer.deck.render(batch,false);
		
		//Render Options
		endOpt.render(batch);
		//quitOpt.render(batch);
		
		//Render Text
		font.setScale(1.5f);
		font.setColor(1f,0f,0f,1f);
		font.draw(batch, ""+player.life, 60, 220);
		font.setColor(0f, 0f, 1f, 1f);
		font.draw(batch, ""+eplayer.life, 60, 295);
		
		// Render history last to occlude other stuff besides
		// the options selectors below.
		history.render(batch);
		
		if(inGameState == HAND_OPT_STATE)
		{
			handSel.render(batch);
		}
		else if(inGameState == TRIB_OPT_STATE)
		{
			tribSel.render(batch);
		}
		else if(inGameState == FIELD_OPT_STATE)
		{
			fieldSel.render(batch);
		}
		else if(inGameState == ATT_TARGET_STATE)
		{
			attackSel.render(batch);
		}
		else if (inGameState == SELECT_TARGET_STATE)
		{
		    targetSel.render(batch);
		}
		else if(inGameState == GAME_OVER_STATE)
		{
			batch.draw(blackTex, 0, 0, pcgtcg.SCREEN_WIDTH, pcgtcg.SCREEN_HEIGHT);
			font.setScale(2f);
			font.setColor(1f, 1f, 1f, 1f);
			if(hasWon)
				font.draw(batch, "You Win!", 300, 300);
			else
				font.draw(batch,"You Lose!",300,300);
			
			// Draw text to indicate that the rematch request was sent.
			if(rematchIntent &&
			   !rematchRequest)
			{
			    font.setColor(0.5f, 0.5f, 1.0f, 1.0f);
			    font.setScale(1.0f);
			    font.draw(batch, "Waiting for opponent to accept rematch", 160, 400);
			    font.setColor(1f, 1f, 1f, 1f);
			}
			
			// If opponent has sent rematch request, let user know
			if (rematchRequest &&
			    !rematchIntent)
			{
                font.setColor(0.5f, 0.5f, 1.0f, 1.0f);
                font.setScale(1.0f);
                font.draw(batch, "Your opponent wants a rematch", 220, 400);
                font.setColor(1f, 1f, 1f, 1f);
			}
			
			rematchOpt.render(batch);
			menuOpt.render(batch);
		}
	}
	
	public void quit()
	{
		pcgtcg.netman.close();
		pcgtcg.game = null;
		pcgtcg.gameState = pcgtcg.MENU_STATE;
	}
	
	public void reset()
	{
	    player  = new Player();
        eplayer = new Player();
        hand    = new Hand(true);
        ehand   = new Hand(false);
        field   = new Field(true);
        efield  = new Field(false);
        grave   = new Grave(true);
        egrave  = new Grave(false);
        
        hasSummoned = false;
        hasWon = false;
        gameOver = false;
        hasPlayerAttacked = false;
        scrollX = 0.0f;
        
        rematchIntent = false;
        rematchRequest = false;
        
        if ((pcgtcg.netman instanceof Server) ||
            (pcgtcg.netman instanceof LANServer))
        {
            player.deck.randomizeDeck();
            player.deck.setOwn(true);
            eplayer.deck.randomizeDeck();
            eplayer.deck.setOwn(false);
            player.deck.setCardBoxes(true);
            eplayer.deck.setCardBoxes(false);
            Random rand = new Random();
            if(rand.nextFloat() > 0.5)
            {
                firstTurn = 1;
                isFirstTurn = true;
            }
            else
                firstTurn = 2;
            inGameState = INIT_STATE;
        }
        else
        {
            inGameState = ACCEPT_STATE;
        }
	        
	}
	
	public void addToast(String text)
	{
	    HistoryToast toast = new HistoryToast(true);
        toast.text = text;
        history.add(toast);
        
        pcgtcg.netman.send("NOTIFY." + text);
	}
	
	
	//*************************************************
	//*************     GAME ACTIONS    ***************
	//*************************************************
	
	public void draw()
	{
	    // Check if player has decked out
		if(player.deck.curCards.isEmpty())
		{
			player.life = 0;
			hasWon = false;
			gameOver = true;
		}
		else
		{
		    // Check if hand is full
		    if(hand.getSize() == hand.getMaxSize())
		    {
		        // Discard first card in hand to make room.
		        sdiscard(0);
		    }
			hand.add(player.deck.curCards.removeFirst());
		}
		pcgtcg.netman.send("DRAW");
		upkeep();
	}
	
    public void fdraw()
    {
        if(eplayer.deck.curCards.isEmpty())
        {
            eplayer.life = 0;
            hasWon = true;
            gameOver = true;
        }
        else
        {
            // Check if enemy hand is full
            if(ehand.getSize() == ehand.getMaxSize())
            {
                // Discard their first card to make room for
                // the forced draw.
                discard(0);
            }
            ehand.add(eplayer.deck.curCards.removeFirst());
        }
        pcgtcg.netman.send("FDRAW");
    }
    
	public void upkeep()
	{
		hasPlayerAttacked = false;
	}
	
	public void end()
	{
		inGameState = ACCEPT_STATE;
		endOpt.setValid(false);
		isFirstTurn = false;
		for(int i = 0; i < field.getSize(); i++)
			field.getCard(i).setHasAttacked(false);
		pcgtcg.netman.send("ENDTURN");
	}
	
	public void summon(Card c)
	{
		for(int i = 0; i < hand.getSize(); i++)
		{
			if(hand.getCard(i).getValue() == c.getValue())
			{
				hand.getCard(i).setAttackPosition(true);
				hand.getCard(i).setVisible(true);
				hand.getCard(i).setHasAttacked(pcgtcg.game.hasPlayerAttacked);
				field.add(hand.remove(i));
				break;
			}
		}
		
		// Invoke the "OnSummon" callback method
		c.summon();
		
		hasSummoned = true;
		pcgtcg.netman.send("SUMMON." + c.getValue());
		addToast("Summon " + c.getValue());
	}
	
	public void set(Card c)
	{		
		boolean ats = false;
		for(int i=0; i < field.getSize(); i++)
			ats = ats || field.getCard(i).hasAttacked();
	
		for(int i = 0; i < hand.getSize(); i++)
		{
			if(hand.getCard(i).getValue() == c.getValue())
			{
				hand.getCard(i).setAttackPosition(false);
				hand.getCard(i).setVisible(false);
				hand.getCard(i).setHasAttacked(pcgtcg.game.hasPlayerAttacked);
				field.add(hand.remove(i));
				break;
			}
		}
		hasSummoned = true;
		pcgtcg.netman.send("SET." + c.getValue());
	}
	
	public void activate(Card c)
	{
        for(int i = 0; i < hand.getSize(); i++)
        {
            // Find a matching card value to activate
            if(hand.getCard(i).getValue() == c.getValue())
            {
                if(hand.getCard(i).hasActive())
                {   
                    // Discard if not a special summon active
                    if (c.getValue() != '3' &&
                        c.getValue() != 'A' &&
                        (!c.hasActivateTarget()))
                    {
                        sdiscard(i);
                    }
                    
                    addToast("Activate " + c.getValue());
                    
                    c.activate();
                    
                    break;
                }
                else
                {
                    // Somehow activate was called on a card with no active
                    System.out.println("Invalid card activation.");
                    return;
                }
            }
        }
	}
	
	public void discard(int pos)
	{
	    egrave.add(ehand.remove(pos));
	    pcgtcg.netman.send("DISCARD." + pos);
	}
	
	public void sdiscard(int pos)
	{
	    grave.add(hand.remove(pos));
	    pcgtcg.netman.send("SDISCARD." + pos);
	}
	
	public void kill(int pos)
	{
		egrave.add(efield.remove(pos));
		pcgtcg.netman.send("KILL." + pos);
	}
	
	public void skill(int pos)
	{
	    grave.add(field.remove(pos));
		pcgtcg.netman.send("SKILL." + pos);
	}
	
	public void retract(int pos)
	{
	    ehand.add(efield.remove(pos));
	    pcgtcg.netman.send("RETRACT." + pos);
	}
	
	public void sretract(int pos)
	{
	    hand.add(field.remove(pos));
	    pcgtcg.netman.send("SRETRACT." + pos);
	}
	
	public void regenerate(int pos)
	{
		ehand.add(egrave.remove(pos));
		pcgtcg.netman.send("REGENERATE." + pos);
	}
	
	public void sregenerate(int pos)
	{
		hand.add(grave.remove(pos));
		pcgtcg.netman.send("SREGENERATE." + pos);
	}
	
	public void retrieve(int pos)
	{
		ehand.add(eplayer.deck.curCards.get(pos));
		pcgtcg.netman.send("RETRIEVE." + pos);
	}
	
	public void sretrieve(int pos)
	{
		hand.add(player.deck.curCards.remove(pos));
		pcgtcg.netman.send("SRETRIEVE." + pos);
	}
	
	public void toggle(int pos)
	{
		field.getCard(pos).setVisible(true);
		field.getCard(pos).toggleAttackPosition();
		pcgtcg.netman.send("TOGGLE."+pos);
		field.updatePosition();
	}
	
	public void damage(int amount)
	{
		eplayer.life -= amount;
		if(eplayer.life <= 0)
		{
			hasWon = true;
			gameOver = true;
		}
		pcgtcg.netman.send("DAMAGE."+amount);
	}
	
	public void sdamage(int amount)
	{
		player.life -= amount;
		if(player.life <= 0)
		{
			hasWon = false;
			gameOver = true;
		}
		pcgtcg.netman.send("SDAMAGE."+amount);
	}
	//*************************************************
	//*************     NET ACTIONS     ***************
	//*************************************************
	
	public void exeDECKONE(String params)
	{
		for(int i = 0; i < params.length(); i++)
			eplayer.deck.add(params.charAt(i));
		eplayer.deck.setCardBoxes(false);
		eplayer.deck.setOwn(false);
	}
	
	public void exeDECKTWO(String params)
	{
		for(int i = 0; i < params.length(); i++)
			player.deck.add(params.charAt(i));
		player.deck.setCardBoxes(true);
		player.deck.setOwn(true);
	}
	
	public void exeDRAW()
	{
       if(eplayer.deck.curCards.isEmpty())
        {
            eplayer.life = 0;
            hasWon = true;
            gameOver = true;
            inGameState = GAME_OVER_STATE;
        }
       else
       {
           ehand.add(eplayer.deck.curCards.removeFirst());
       }	
	}
	
	public void exeFDRAW()
	{      
	    if(player.deck.curCards.isEmpty())
        {
            player.life = 0;
            hasWon = false;
            gameOver = true;
            inGameState = GAME_OVER_STATE;
        }
	    else
	    {
	        hand.add(player.deck.curCards.removeFirst());
	    }
	}
	
	public void exeENDTURN()
	{
		inGameState = DRAW_STATE;
	}
	
	//The FIRSTTURN signal might actually be completely useless.
	public void exeFIRSTTURN(String param)
	{
		firstTurn = Integer.parseInt(param);
		System.out.println("Parsed firstTurn: " + firstTurn);
		if(firstTurn == 1)
		{
			turnState = ONE_STATE;
			isFirstTurn = false;
		}
		else
		{
			turnState = TWO_STATE;
			isFirstTurn = true;
		}
	}
	
	public void exeSUMMON(String param)
	{
		char cardVal = param.charAt(0);
		for(int i = 0; i < ehand.getSize(); i++)
		{
			if(ehand.getCard(i).getValue() == cardVal)
			{
				efield.add(ehand.remove(i));
				break;
			}
		}
	}
	
	public void exeSET(String param)
	{
		char cardVal = param.charAt(0);
		for(int i = 0; i < ehand.getSize(); i++)
		{
			if(ehand.getCard(i).getValue() == cardVal)
			{
				ehand.getCard(i).setAttackPosition(false);
				ehand.getCard(i).setVisible(false);
				efield.add(ehand.remove(i));
				break;
			}
		}
	}
	
	public void exeKILL(String param)
	{
		int pos = Integer.parseInt(param);
		grave.add(field.remove(pos));
	}
	
	public void exeSKILL(String param)
	{
		int pos = Integer.parseInt(param);
		egrave.add(efield.remove(pos));
	}
	
	public void exeTOGGLE(String param)
	{
		int pos = Integer.parseInt(param);
		efield.getCard(pos).toggleAttackPosition();
		efield.getCard(pos).setVisible(true);
		efield.updatePosition();
	}
	
	public void exeDAMAGE(String param)
	{
		int amount = Integer.parseInt(param);
		player.life -= amount;
		if(player.life <= 0)
		{
			hasWon = false;
			gameOver = true;
			inGameState = GAME_OVER_STATE;
		}
	}
	
	public void exeSDAMAGE(String param)
	{
		int amount = Integer.parseInt(param);
		eplayer.life -= amount;
		if(eplayer.life <= 0)
		{
			hasWon = true;
			gameOver = true;
			inGameState = GAME_OVER_STATE;
		}
	}
	
    public void exeDISCARD(String param)
    {
        int pos = Integer.parseInt(param);
        grave.add(hand.remove(pos));
    }
    
	public void exeSDISCARD(String param)
	{
	    int pos = Integer.parseInt(param);
	    egrave.add(ehand.remove(pos));
	}
	
	public void exeRETRACT(String param)
	{
	    int pos = Integer.parseInt(param);
	    hand.add(field.remove(pos));
	}
	
	public void exeSRETRACT(String param)
	{
	    int pos = Integer.parseInt(param);
	    ehand.add(efield.remove(pos));
	}
	
	public void exeREGENERATE(String param)
	{
		int pos = Integer.parseInt(param);
		hand.add(grave.remove(pos));
	}
	
	public void exeSREGENERATE(String param)
	{
		int pos = Integer.parseInt(param);
		ehand.add(egrave.remove(pos));
	}
	
	public void exeRETRIEVE(String param)
	{
		int pos = Integer.parseInt(param);
		hand.add(player.deck.curCards.remove(pos));
	}
	
	public void exeSRETRIEVE(String param)
	{
		int pos = Integer.parseInt(param);
		ehand.add(eplayer.deck.curCards.remove(pos));
	}
	
	public void exeNOTIFY(String param)
	{
	    // This network action is only used
	    // to provide a history toast.
	    HistoryToast toast = new HistoryToast(false);
        toast.text = param;
        history.add(toast);
	}
	
	public void exeMODPOWER(String param)
	{
        int pos = Integer.parseInt(param.split("[.]",2)[0]);
        int powerMod = Integer.parseInt(param.split("[.]",2)[1]);
        
        field.getCard(pos).modifyPower(powerMod);
	}
	
	public void exeSMODPOWER(String param)
	{
	    int pos = Integer.parseInt(param.split("[.]",2)[0]);
	    int powerMod = Integer.parseInt(param.split("[.]",2)[1]);
	    
	    efield.getCard(pos).modifyPower(powerMod);
	}
	
	public void exeREMATCH()
	{
	    rematchRequest = true;
	}
}
