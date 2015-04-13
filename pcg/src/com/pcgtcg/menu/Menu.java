package com.pcgtcg.menu;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.pcg.pcgtcg;
import com.pcgtcg.game.Game;
import com.pcgtcg.network.Client;
import com.pcgtcg.network.Server;
import com.pcgtcg.util.Button;
import com.pcgtcg.util.Option;
import com.pcgtcg.util.TextField;

public class Menu {

	public int menuState;
	
	public static final int NULL_STATE = -1;
	public static final int SPLASH_STATE = 0;
	public static final int MAIN_STATE = 1;
	public static final int HOST_STATE = 2;
	public static final int CONNECT_STATE = 3;
	public static final int OPTIONS_STATE = 4;
	
	private Button optionsButton;
	private Button hostButton;
	private Button connectButton;
	
	private Option connectOption;
	private Option cancelOption;
	private TextField ipField;
	private BitmapFont font;
	
	private ArrayList<GameInfo> gameList;
	
	public Menu()
	{
		menuState = MAIN_STATE;
		
		optionsButton = new Button(80,120,180,50);
		hostButton    = new Button(300,120,180,50);
		connectButton = new Button( 520, 120, 180, 50);
		optionsButton.setText("Options");
		hostButton.setText("Host");
		connectButton.setText("Connect");
		
		font = pcgtcg.manager.get("data/eras.fnt",BitmapFont.class);
		connectOption = new Option("Connect",200,320);
		gameList = new ArrayList<GameInfo>();
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
			font.setScale(1f);
			font.setColor(1f,0f,0f,1f);
		}
		else if(menuState == CONNECT_STATE)
		{
		    if (gameList.size() == 0)
		    {
		        font.setColor(0f, 0f, 0f, 1f);
		        font.setScale(1f);
		        font.draw(batch, "No Open Games", 200, 250);
		    }
		    for (int i = 0; i < gameList.size(); i++)
		    {
		        gameList.get(i).render(batch);
		    }
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
				
				if (pcgtcg.netman != null)
				    pcgtcg.netman.close();
				pcgtcg.netman = new Server();
				(new Thread(pcgtcg.netman)).start();
				
				// Send login
				while(!pcgtcg.netman.isInitialized())
				{
				    
				}
				pcgtcg.netman.sendServer("LOGIN." + pcgtcg.name);
			    
				while (!pcgtcg.netman.isLoggedIn())
				{
				    pcgtcg.netman.poll();
				}
				
				
				// Send game creation
				pcgtcg.netman.sendServer("CREATE");
                while (!pcgtcg.netman.isInGame())
                {
                    pcgtcg.netman.poll();
                }
				hostButton.clear();
			}
			else if(connectButton.isActive())
			{
				menuState = CONNECT_STATE;
				
                if (pcgtcg.netman != null)
                    pcgtcg.netman.close();
				pcgtcg.netman = new Client();
				(new Thread(pcgtcg.netman)).start();

                // Send login
                while(!pcgtcg.netman.isInitialized())
                {
                    
                }
                pcgtcg.netman.sendServer("LOGIN." + pcgtcg.name);
                
                while (!pcgtcg.netman.isLoggedIn())
                {
                    pcgtcg.netman.poll();
                }
                
                // Generate game list
                pcgtcg.netman.sendServer("LIST");
                
                while (!pcgtcg.netman.hasGameList())
                {
                    pcgtcg.netman.poll();
                }
                
				//Gdx.input.setOnscreenKeyboardVisible(true);
				//Gdx.input.setInputProcessor(ipField);
				connectButton.clear();
			}
		}
		else if(menuState == HOST_STATE)
		{
		    // Poll in hopes to receive a READY message
		    pcgtcg.netman.poll();
		    
			if(pcgtcg.netman.isConnected() &&
			   pcgtcg.netman.isReady())
			{
				menuState = MAIN_STATE;
				System.out.println("Client has connected!");
				pcgtcg.game      = new Game(true);
				pcgtcg.gameState = pcgtcg.GAME_STATE;
			}
		}
		else if(menuState == CONNECT_STATE)
		{
			if(Gdx.input.justTouched() && (pcgtcg.game == null))
			{
				Vector3 touchPos = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
				pcgtcg.camera.unproject(touchPos);
				float tx = touchPos.x;
				float ty = touchPos.y;
				
				for (int i = 0; i < gameList.size(); i++)
				{
				    if (gameList.get(i).isTouched(tx, ty) &&
				        gameList.get(i).status == GameInfo.WAITING)
				    {
				        pcgtcg.netman.sendServer("JOIN."+gameList.get(i).id);
				        while (!pcgtcg.netman.isInGame() ||
				               !pcgtcg.netman.isReady())
				        {
				            pcgtcg.netman.poll();
				        }
				        
				        if (pcgtcg.netman.isInGame())
				        {
				            pcgtcg.game = new Game(false);
			                menuState = MAIN_STATE;
			                System.out.println("Connected to Server!");
			                pcgtcg.gameState = pcgtcg.GAME_STATE;
				        }
				        else
				        {
				            menuState = MAIN_STATE;
				            System.out.println("Failed to join game");
				        }
				    }
				}
			}
		}
	}
	
	public void clearGameList()
	{
	    gameList.clear();
	}
	
	public void addGameInfo(int    id,
	                        int    status,
	                        String hostName)
	{
	    int pos = gameList.size();
	    GameInfo newGameInfo = new GameInfo();
	    newGameInfo.id       = id;
	    newGameInfo.status   = status;
	    newGameInfo.hostName = hostName;
	    newGameInfo.box.set(25, 
	                        pcgtcg.SCREEN_HEIGHT - pos*130 - 130,
	                        400,
	                        105);
	    gameList.add(newGameInfo);
	}
	
	public void reset()
	{
	    
	}
}
