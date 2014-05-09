package com.pcgtcg.game;

import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.pcg.pcgtcg;
import com.pcgtcg.card.Card;
import com.pcgtcg.network.Client;
import com.pcgtcg.network.NetworkManager;
import com.pcgtcg.network.Server;
import com.pcgtcg.util.FieldSelector;
import com.pcgtcg.util.HandSelector;
import com.pcgtcg.util.Option;
import com.pcgtcg.util.TributeSelector;

public class Game {

	public final int NULL_STATE = -1, INIT_STATE = 0, DRAW_STATE = 1, PLAY_STATE = 2, ACCEPT_STATE = 3; 
	public final int HAND_OPT_STATE = 4, FIELD_OPT_STATE = 5, TRIB_OPT_STATE = 6, ATT_TARGET_STATE = 7;
	public final int ONE_STATE = 1, TWO_STATE = 2;
	public int turnState;
	public int inGameState;
	private int testStatus;
	
	
	//Player + Locations
	public Player player;
	public Player eplayer;
	public Hand hand;
	public Hand ehand;
	public Field field;
	public Field efield;
	public LinkedList<Card> grave;
	public LinkedList<Card> egrave;
	
	//Options
	private HandSelector handSel;
	public Option endOpt;
	public TributeSelector tribSel;
	public FieldSelector fieldSel;
	
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
		grave = new LinkedList<Card>();
		egrave = new LinkedList<Card>();
		hasSummoned = false;
		endOpt = new Option("End Turn",650,220);
		endOpt.setValid(false);
		
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
				firstTurn = 1;
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
		}
		else if(inGameState == DRAW_STATE)
		{
			draw();
			endOpt.setValid(true);
			hasSummoned = false;
			inGameState = PLAY_STATE;
		}
		else if(inGameState == PLAY_STATE)
		{
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
		
		
		//Render Options
		endOpt.render(batch);
		
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
	}
	
	
	//*************************************************
	//*************     GAME ACTIONS    ***************
	//*************************************************
	
	public void draw()
	{
		hand.add(player.deck.curCards.removeFirst());
		netman.send("DRAW");
	}
	
	public void fdraw()
	{
		ehand.add(eplayer.deck.curCards.removeFirst());
		netman.send("FDRAW");
	}
	
	public void end()
	{
		inGameState = ACCEPT_STATE;
		endOpt.setValid(false);
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
				field.add(hand.remove(i));
				break;
			}
		}
		hasSummoned = true;
		c.setHasAttacked(true);
		netman.send("SUMMON." + c.getValue());
	}
	
	public void set(Card c)
	{
		for(int i = 0; i < hand.getSize(); i++)
		{
			if(hand.getCard(i).getValue() == c.getValue())
			{
				hand.getCard(i).setAttackPosition(false);
				hand.getCard(i).setVisible(false);
				field.add(hand.remove(i));
				break;
			}
		}
		hasSummoned = true;
		c.setHasAttacked(true);
		netman.send("SET." + c.getValue());
	}
	
	public void skill(int pos)
	{
			field.remove(pos);
			netman.send("SKILL." + pos);
	}
	
	public void toggle(int pos)
	{
		field.getCard(pos).setVisible(true);
		field.getCard(pos).toggleAttackPosition();
		netman.send("TOGGLE."+pos);
		field.updatePosition();
		
		//Weird shit is happening on toggle. please fix this sober martin.
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
		ehand.add(eplayer.deck.curCards.removeFirst());
	}
	
	public void exeFDRAW()
	{
		hand.add(player.deck.curCards.removeFirst());
	}
	
	public void exeENDTURN()
	{
		inGameState = DRAW_STATE;
	}
	
	//The FIRSTTURN signal might actually be completely useless.
	public void exeFIRSTTURN(String param)
	{
		firstTurn = Integer.parseInt(param);
		if(firstTurn == 1)
			turnState = ONE_STATE;
		else
			turnState = TWO_STATE;
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
	
	public void exeSKILL(String param)
	{
		int pos = Integer.parseInt(param);
		efield.remove(pos);
	}
	
	public void exeTOGGLE(String param)
	{
		int pos = Integer.parseInt(param);
		efield.getCard(pos).toggleAttackPosition();
		efield.getCard(pos).setVisible(true);
		efield.updatePosition();
	}
}
