package com.pcgtcg.menu;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pcg.pcgtcg;
import com.pcgtcg.game.Game;
import com.pcgtcg.util.Button;

public class Menu {

	public int menuState;
	
	private final int NULL_STATE = -1;
	private final int SPLASH_STATE = 0;
	private final int MAIN_STATE = 1;
	private final int HOST_STATE = 2;
	private final int CONNECT_STATE = 3;
	private final int OPTIONS_STATE = 4;
	
	private Button optionsButton;
	private Button hostButton;
	private Button connectButton;
	private BitmapFont font;
	
	public Menu()
	{
		menuState = MAIN_STATE;
		
		optionsButton = new Button(80,120,180,50);
		hostButton = new Button(300,120,180,50);
		connectButton = new Button( 520, 120, 180, 50);
		optionsButton.setText("Options");
		hostButton.setText("Host");
		connectButton.setText("Connect");
		
		font = pcgtcg.manager.get("data/eras.fnt",BitmapFont.class);
	}
	
	
	public void render(SpriteBatch batch)
	{
		
		if(menuState == MAIN_STATE)
		{
			optionsButton.render(batch);
			hostButton.render(batch);
			connectButton.render(batch);
				
			font.setScale(4f,3f);
			font.setColor(1f,0.1f,0.1f,1f);
			font.draw(batch, "PCG", 80, 430);
			font.setColor(0f,0f,0f,1f);
			font.draw(batch, "the", 180, 370);
			font.setColor(1f,0.1f,0.1f,1f);
			font.draw(batch, "TCG", 280, 310);
		}
		else if(menuState == HOST_STATE)
		{
			font.setScale(2f);
			font.setColor(0f,0f,0f,1f);
			font.draw(batch, "Waiting for connection...", 100, 400);
		}
		
		
	}
	
	public void update()
	{
		if(menuState == MAIN_STATE)
		{
			optionsButton.update();
			hostButton.update();
			connectButton.update();
			
			if(optionsButton.isActive())
			{
				optionsButton.clear();
			}
			else if(hostButton.isActive())
			{
				menuState = HOST_STATE;
				System.out.println("Server Game created.");
				pcgtcg.game = new Game(true);
				hostButton.clear();
			}
			else if(connectButton.isActive())
			{
				menuState = CONNECT_STATE;
				System.out.println("Client Game created.");
				pcgtcg.game = new Game(false);
				connectButton.clear();
			}
		}
		else if(menuState == HOST_STATE)
		{
			if(pcgtcg.game.netman.isConnected())
			{
				menuState = MAIN_STATE;
				System.out.println("Client has connected!");
				pcgtcg.gameState = pcgtcg.GAME_STATE;
			}
		}
		else if(menuState == CONNECT_STATE)
		{
			if(pcgtcg.game.netman.isConnected())
			{
				menuState = MAIN_STATE;
				System.out.println("Connected to Server!");
				pcgtcg.gameState = pcgtcg.GAME_STATE;
			}
		}
	}
	
}
