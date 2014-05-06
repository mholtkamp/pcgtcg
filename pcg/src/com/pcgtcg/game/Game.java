package com.pcgtcg.game;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pcgtcg.network.Client;
import com.pcgtcg.network.NetworkManager;
import com.pcgtcg.network.Server;

public class Game {

	private final int ONE_STATE = 1, TWO_STATE = 2;
	private int turnState;
	private int testStatus;
	
	private Player p1;
	private Player p2;
	
	private int playerNum;
	public int firstTurn;
	public NetworkManager netman;
	
	public Game()
	{
		p1 = new Player();
		p2 = new Player();
	}
	
	public Game(boolean isHost)
	{
		this();
		if(isHost)
		{
			System.out.println("P1 created");
			playerNum = 1;
			netman = new Server();
			(new Thread(netman)).start();
			p1.deck.randomizeDeck();
			p2.deck.randomizeDeck();
			Random rand = new Random();
			if(rand.nextFloat() > 0.5)
				firstTurn = 1;
			else
				firstTurn = 2;
			
			testStatus = 1;

			
				
		}
		else
		{

			playerNum = 2;
			netman = new Client();
			(new Thread(netman)).start();
			testStatus = 0;
		}
	}
	
	public void update()
	{

		if(playerNum == 2)
		{
			netman.poll();
		}
		
		if(testStatus == 1)
		{
			if(netman.isConnected())
			{
				netman.send("DECKONE.2.3");
				netman.send("DECKTWO.J.5");
				testStatus = 0;
				System.out.println("Host sending deck data");
			}
		}
		
	}
	
	public void render(SpriteBatch batch)
	{
		
	}
}
