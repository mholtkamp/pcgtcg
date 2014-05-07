package com.pcgtcg.game;

import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pcgtcg.card.Card;
import com.pcgtcg.network.Client;
import com.pcgtcg.network.NetworkManager;
import com.pcgtcg.network.Server;

public class Game {

	private final int NULL_STATE = -1, INIT_STATE = 0, DRAW_STATE = 1, PLAY_STATE = 2, ACCEPT_STATE = 3; 
	private final int ONE_STATE = 1, TWO_STATE = 2;
	private int turnState;
	private int inGameState;
	private int testStatus;
	
	private Player player;
	private Player eplayer;
	private LinkedList<Card> hand;
	private LinkedList<Card> ehand;
	private LinkedList<Card> field;
	private LinkedList<Card> efield;
	private LinkedList<Card> grave;
	private LinkedList<Card> egrave;
	
	private int playerNum;
	public int firstTurn;
	public NetworkManager netman;
	
	public Game()
	{
		player = new Player();
		eplayer = new Player();
		hand = new LinkedList<Card>();
		ehand = new LinkedList<Card>();
		field = new LinkedList<Card>();
		efield = new LinkedList<Card>();
		grave = new LinkedList<Card>();
		egrave = new LinkedList<Card>();
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
				inGameState = DRAW_STATE;
			}
			else
			{
				draw();
				draw();
				draw();
				draw();
				end();
			}
			
			
		}
		else if(inGameState == ACCEPT_STATE)
		{
			netman.poll();
		}
		else if(inGameState == DRAW_STATE)
		{
			
		}
		if(Gdx.input.isKeyPressed(Input.Keys.Q))
		{
			netman.close();
			Gdx.app.exit();
		}

		
	}
	
	public void render(SpriteBatch batch)
	{
		
	}
	
	
	//*************************************************
	//*************     GAME ACTIONS    ***************
	//*************************************************
	
	public void draw()
	{
		hand.add(player.deck.curCards.removeFirst());
		netman.send("DRAW");
		
	}
	
	public void end()
	{
		inGameState = ACCEPT_STATE;
		netman.send("ENDTURN");
	}
}
