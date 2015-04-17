package com.pcgtcg.menu;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
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
	
	public static final int NULL_STATE    = -1;
	public static final int SPLASH_STATE  = 0;
	public static final int MAIN_STATE    = 1;
	public static final int HOST_STATE    = 2;
	public static final int CONNECT_STATE = 3;
	public static final int OPTIONS_STATE = 4;
	public static final int LOGIN_STATE   = 5;
	public static final int CREATE_STATE  = 6;
	public static final int LIST_STATE    = 7;
	
	private Button optionsButton;
	private Button hostButton;
	private Button connectButton;
	
	private Option connectOption;
	private Option cancelOption;
	private TextField ipField;
	private BitmapFont font;
	private Texture whiteTex;
	
	private boolean fadeFlag;
	private float fadeAlpha;
	private final static float FADE_RATE = 2f;
	
	private boolean hasSentLogin;
	private int queuedState;
	
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
		whiteTex = pcgtcg.manager.get("data/whiteTex.png", Texture.class);
		
		fadeFlag = true;
		fadeAlpha = 1f;
		
		hasSentLogin = false;
		queuedState = -1;
	}
	
	public void render(SpriteBatch batch)
	{
		if(menuState == MAIN_STATE)
		{
			optionsButton.render(batch);
			hostButton.render(batch);
			connectButton.render(batch);
				
			font.setScale(3f,3f);
			font.setColor(1f,0.1f,0.1f,1f);
			font.draw(batch, "PCG", 300, 430);
			font.setScale(2f,2f);
			font.setColor(0f,0f,0f,1f);
			font.draw(batch, "the", 340, 365);
			font.setScale(3f,3f);
			font.setColor(1f,0.1f,0.1f,1f);
			font.draw(batch, "TCG", 300, 310);
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
		else if (menuState == LOGIN_STATE)
		{
		    batch.setColor(0f, 0f, 0f, 1f);
		    batch.draw(whiteTex, 
		               0f,
		               0f,
		               pcgtcg.SCREEN_WIDTH,
		               pcgtcg.SCREEN_HEIGHT);
		    batch.setColor(1f, 1f, 1f, 1f);
		    
		    font.setScale(2f);
		    font.setColor(1f, 1f, 1f, 1f);
		    font.draw(batch, "Logging In...", 100, 400);
		}
		else if (menuState == CREATE_STATE)
		{
            batch.setColor(0f, 0f, 0f, 1f);
            batch.draw(whiteTex, 
                       0f,
                       0f,
                       pcgtcg.SCREEN_WIDTH,
                       pcgtcg.SCREEN_HEIGHT);
            batch.setColor(1f, 1f, 1f, 1f);
            
            font.setScale(2f);
            font.setColor(1f, 1f, 1f, 1f);
            font.draw(batch, "Creating Game...", 100, 400);
		}
		else if (menuState == LIST_STATE)
		{
            batch.setColor(0f, 0f, 0f, 1f);
            batch.draw(whiteTex, 
                       0f,
                       0f,
                       pcgtcg.SCREEN_WIDTH,
                       pcgtcg.SCREEN_HEIGHT);
            batch.setColor(1f, 1f, 1f, 1f);
            
            font.setScale(2f);
            font.setColor(1f, 1f, 1f, 1f);
            font.draw(batch, "Requesting Game List...", 100, 400);
		}
		
      if (fadeFlag)
        {
            fadeAlpha -= FADE_RATE*Gdx.graphics.getDeltaTime();
            if (fadeAlpha <= 0f)
                fadeFlag = false;
            else
            {
                batch.setColor(0f, 0f, 0f, fadeAlpha);
                batch.draw(whiteTex,0,0, pcgtcg.SCREEN_WIDTH, pcgtcg.SCREEN_HEIGHT);
                batch.setColor(1f, 1f, 1f, 1f);
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
				System.out.println("Server Game created.");
				
				if (pcgtcg.netman != null)
				    pcgtcg.netman.close();
				pcgtcg.netman = new Server();
				(new Thread(pcgtcg.netman)).start();
				
				hostButton.clear();
				queuedState = CREATE_STATE;
				menuState = LOGIN_STATE;
			}
			else if(connectButton.isActive())
			{
				menuState = CONNECT_STATE;
				
                if (pcgtcg.netman != null)
                    pcgtcg.netman.close();
				pcgtcg.netman = new Client();
				(new Thread(pcgtcg.netman)).start();

                connectButton.clear();
                queuedState = LIST_STATE;
                menuState = LOGIN_STATE;
                
				//Gdx.input.setOnscreenKeyboardVisible(true);
				//Gdx.input.setInputProcessor(ipField);
			}
		}
		else if(menuState == HOST_STATE)
		{
		    // Poll in hopes to receive a READY message
		    pcgtcg.netman.poll();
		    
			if(pcgtcg.netman.isConnected() &&
			   pcgtcg.netman.isReady())
			{
			    setFade();
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
				            setFade();
				            menuState = MAIN_STATE;
			                System.out.println("Connected to Server!");
			                pcgtcg.gameState = pcgtcg.GAME_STATE;
				        }
				        else
				        {
				            setFade();
				            menuState = MAIN_STATE;
				            System.out.println("Failed to join game");
				        }
				    }
				}
			}
		}
		else if (menuState == LOGIN_STATE)
		{
            // Send login
            if(pcgtcg.netman.isInitialized())
            {
                if (!hasSentLogin)
                {
                    pcgtcg.netman.sendServer("LOGIN." + pcgtcg.name);
                    hasSentLogin = true;
                }
                
                // Poll to check for R_LOGIN
                pcgtcg.netman.poll();
                
                if(pcgtcg.netman.isLoggedIn())
                {
                    menuState = queuedState;
                    queuedState = NULL_STATE;
                    
                    if (menuState == CREATE_STATE)
                    {
                        // Send game creation
                        pcgtcg.netman.sendServer("CREATE");
                    }
                    else if (menuState == LIST_STATE)
                    {
                        // Generate game list
                        pcgtcg.netman.sendServer("LIST");
                    }
                    hasSentLogin = false;
                }   
            }
		}
		else if (menuState == CREATE_STATE)
		{
		    pcgtcg.netman.poll();
            if (pcgtcg.netman.isInGame())
            {
                menuState = HOST_STATE;
                setFade();
            }
		}
		else if (menuState == LIST_STATE)
		{   
		    pcgtcg.netman.poll();
            if (pcgtcg.netman.hasGameList())
            {
                menuState = CONNECT_STATE;
                setFade();
            }
		}
	}
	
	public void setFade()
	{
	    fadeAlpha = 1f;
	    fadeFlag = true;
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
