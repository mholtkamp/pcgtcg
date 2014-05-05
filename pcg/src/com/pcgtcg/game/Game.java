package com.pcgtcg.game;

import com.pcgtcg.network.Client;
import com.pcgtcg.network.NetworkManager;
import com.pcgtcg.network.Server;

public class Game {

	private final int ONE_STATE = 1, TWO_STATE = 2;
	private int turnState;
	
	private Player p1;
	private Player p2;
	
	private int playerNum;
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
			playerNum = 1;
			netman = new Server();
			(new Thread(netman)).start();
		}
		else
		{
			playerNum = 2;
			netman = new Client();
			(new Thread(netman)).start();
		}
	}
	
}
