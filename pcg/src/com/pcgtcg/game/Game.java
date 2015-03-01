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
import com.pcgtcg.network.Client;
import com.pcgtcg.network.NetworkManager;
import com.pcgtcg.network.Server;
import com.pcgtcg.util.AttackSelector;
import com.pcgtcg.util.FieldSelector;
import com.pcgtcg.util.HandSelector;
import com.pcgtcg.util.Option;
import com.pcgtcg.util.TributeSelector;

public class Game {

	public final int NULL_STATE = -1, INIT_STATE = 0, DRAW_STATE = 1, PLAY_STATE = 2, ACCEPT_STATE = 3; 
	public final int HAND_OPT_STATE = 4, FIELD_OPT_STATE = 5, TRIB_OPT_STATE = 6, ATT_TARGET_STATE = 7;
	public final int GAME_OVER_STATE = 8;
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
	public Option endOpt;
	public Option quitOpt;;
	public TributeSelector tribSel;
	public FieldSelector fieldSel;
	public AttackSelector attackSel;
	
	public boolean hasSummoned;
	public int playerNum;
	public int firstTurn;
	public NetworkManager netman;
	
	public Game()
	{
		player = new Player();
		eplayer = new Player();
		hand = new Hand(true);
		ehand = new Hand(false);
		field = new Field(true);
		efield = new Field(false);
		grave = new Grave(true);
		egrave = new Grave(false);
		hasSummoned = false;
		hasWon = false;
		gameOver = false;
		hasPlayerAttacked = false;
		endOpt = new Option("End Turn",650,220);
		quitOpt = new Option("X",760,440);
		quitOpt.setWidth(40);
		endOpt.setValid(false);
		blackTex = pcgtcg.manager.get("data/blackTex.png",Texture.class);
		font = pcgtcg.manager.get("data/eras.fnt",BitmapFont.class);
		
	}
	
	public Game(boolean isHost)
	{
		this();
		if(isHost)
		{

			playerNum = 1;
			netman = new Server();
			(new Thread(netman)).start();
			player.deck.randomizeDeck();
			eplayer.deck.randomizeDeck();
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
			playerNum = 2;
			netman = new Client();
			(new Thread(netman)).start();
			inGameState = ACCEPT_STATE;

		}
	}
	
	
	public void update()
	{

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
			
			netman.send(d1);
			netman.send(d2);
			netman.send(ft);
			
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
			netman.poll();
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
		
		if(Gdx.input.isKeyPressed(Input.Keys.Q))
		{
			netman.close();
			Gdx.app.exit();
		}	
	}
	
	public void render(SpriteBatch batch)
	{
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
		font.setColor(0f, 0f, 1f, 1f);
		font.draw(batch, ""+player.life, 60, 220);
		font.setColor(1f,0f,0f,1f);
		font.draw(batch, ""+eplayer.life, 60, 295);
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
		else if(inGameState == GAME_OVER_STATE)
		{
			batch.draw(blackTex, 0, 0, pcgtcg.SCREEN_WIDTH, pcgtcg.SCREEN_HEIGHT);
			font.setScale(2f);
			font.setColor(1f, 1f, 1f, 1f);
			if(hasWon)
				font.draw(batch, "You Win!", 300, 300);
			else
				font.draw(batch,"You Lose!",300,300);
		}
	}
	
	public void quit()
	{
		netman.close();
		pcgtcg.game = null;
		pcgtcg.gameState = pcgtcg.MENU_STATE;
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
		netman.send("DRAW");
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
        netman.send("FDRAW");
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
		netman.send("ENDTURN");
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
		hasSummoned = true;
		netman.send("SUMMON." + c.getValue());
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
		netman.send("SET." + c.getValue());
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
                        c.getValue() != 'A')
                    {
                        sdiscard(i);
                    }
                    
                    // Activate Effect
                    //hand.getCard(i).activate();
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
	    netman.send("DISCARD." + pos);
	}
	
	public void sdiscard(int pos)
	{
	    grave.add(hand.remove(pos));
	    netman.send("SDISCARD." + pos);
	}
	
	public void kill(int pos)
	{
		egrave.add(efield.remove(pos));
		netman.send("KILL." + pos);
	}
	
	public void skill(int pos)
	{
	    grave.add(field.remove(pos));
		netman.send("SKILL." + pos);
	}
	
	public void retract(int pos)
	{
	    ehand.add(efield.remove(pos));
	    netman.send("RETRACT." + pos);
	}
	
	public void sretract(int pos)
	{
	    hand.add(field.remove(pos));
	    netman.send("SRETRACT." + pos);
	}
	
	public void toggle(int pos)
	{
		field.getCard(pos).setVisible(true);
		field.getCard(pos).toggleAttackPosition();
		netman.send("TOGGLE."+pos);
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
		netman.send("DAMAGE."+amount);
	}
	
	public void sdamage(int amount)
	{
		player.life -= amount;
		if(player.life <= 0)
		{
			hasWon = false;
			gameOver = true;
		}
		netman.send("SDAMAGE."+amount);
	}
	//*************************************************
	//*************     NET ACTIONS     ***************
	//*************************************************
	
	public void exeDECKONE(String params)
	{
		for(int i = 0; i < params.length(); i++)
			eplayer.deck.add(params.charAt(i));
	}
	
	public void exeDECKTWO(String params)
	{
		for(int i = 0; i < params.length(); i++)
			player.deck.add(params.charAt(i));
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
		ehand.add(eplayer.deck.curCards.removeFirst());
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
}
