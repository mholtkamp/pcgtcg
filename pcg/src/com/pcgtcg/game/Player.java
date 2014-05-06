package com.pcgtcg.game;

public class Player {

	public String name;
	public Deck deck;
	public int life;
	
	public Player()
	{
		life = 40;
		deck = new Deck();
	}
	
	public Player(String name)
	{
		this();
		this.name = name;
	}
}
