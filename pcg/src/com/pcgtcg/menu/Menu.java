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
import com.pcgtcg.card.*;
import com.pcgtcg.game.Game;
import com.pcgtcg.network.Client;
import com.pcgtcg.network.LANClient;
import com.pcgtcg.network.LANServer;
import com.pcgtcg.network.Server;
import com.pcgtcg.util.Button;
import com.pcgtcg.util.TextOption;
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
	public static final int JOIN_STATE    = 8;
	public static final int LAN_CONNECT_STATE = 9;
	public static final int LAN_HOST_STATE    = 10;
	public static final int HOW_TO_PLAY_STATE = 11;
	
	private Button quitButton;
	private Button hostButton;
	private Button connectButton;
	private Button connectLANButton;
	private Button hostLANButton;
	private Button howToPlayButton;
	
	private TextOption connectOption;
	private TextOption cancelOption;
	private TextOption hostCancelOption;
	private TextField ipField;
	private BitmapFont font;
	private Texture whiteTex;
	
	private boolean fadeFlag;
	private float fadeAlpha;
	private final static float FADE_RATE = 2f;
	
	private boolean hasSentLogin;
	private int queuedState;
	private GameSelect gameSelect;
	
	float displayPos;
	
	private Card[] displayCards1;
	private Card[] displayCards2;
	
	private ArrayList<GameInfo> gameList;
	private List<String> hostAdds;
	
	public String connectIP;
	
	private HowToPlay howToPlay;
	
	public Menu()
	{
		menuState = MAIN_STATE;
		
		connectButton    = new Button(160,100,170,50);
		hostButton       = new Button(160,40,170,50);
		quitButton       = new Button(345, 70, 105, 50);
		connectLANButton = new Button(465,100,170,50);
		hostLANButton    = new Button(465,40,170,50);
		howToPlayButton  = new Button(530, 310, 120, 50);
		
		
		quitButton.setText("Quit");
		hostButton.setText("Host");
		connectButton.setText("Connect");
        connectLANButton.setText("Connect");
        hostLANButton.setText("Host");
        howToPlayButton.setText("Rules");
		
		font = pcgtcg.manager.get("data/eras.fnt",BitmapFont.class);
		connectOption = new TextOption("Connect",200,320);
		cancelOption = new TextOption("Cancel", 400, 320);
		hostCancelOption = new TextOption("Cancel", 600, 100);
		gameList = new ArrayList<GameInfo>();
		gameSelect = new GameSelect();
		whiteTex = pcgtcg.manager.get("data/whiteTex.png", Texture.class);
		
		displayCards1 = new Card[8];
		displayCards2 = new Card[8];

	    displayCards1[0] = new CA();
	    displayCards1[1] = new C4();
	    displayCards1[2] = new C9();
	    displayCards1[3] = new C2();
	    displayCards1[4] = new CK();
	    displayCards1[5] = new CT();
	    displayCards1[6] = new C5();
	    displayCards1[7] = new C7();
	    
	    displayCards2[0] = new CQ();
	    displayCards2[1] = new C8();
	    displayCards2[2] = new C6();
	    displayCards2[3] = new C3();
	    displayCards2[4] = new CA();
	    displayCards2[5] = new CK();
	    displayCards2[6] = new CJ();
	    displayCards2[7] = new C4();
	    
	    displayPos = 0.0f;

	    updateDisplayCards();
	    
		fadeFlag = true;
		fadeAlpha = 1f;
		
		hasSentLogin = false;
		queuedState = -1;
		connectIP = "";
		
		howToPlay = new HowToPlay();
	}
	
	public void render(SpriteBatch batch)
	{
		if(menuState == MAIN_STATE)
		{
			quitButton.render(batch);
			hostButton.render(batch);
			connectButton.render(batch);
			connectLANButton.render(batch);
			hostLANButton.render(batch);
			howToPlayButton.render(batch);
				
			font.setScale(3f,3f);
			font.setColor(1f,0.1f,0.1f,1f);
			font.draw(batch, "PCG", 300, 430);
			font.setScale(2f,2f);
			font.setColor(0f,0f,0f,1f);
			font.draw(batch, "the", 340, 365);
			font.setScale(3f,3f);
			font.setColor(1f,0.1f,0.1f,1f);
			font.draw(batch, "TCG", 300, 310);
			
			font.setColor(0.2f, 0.2f, 0.8f, 1f);
			font.setScale(1f);
			font.draw(batch, "Online", 190, 180);
			
	        font.setColor(0.2f, 0.2f, 0.8f, 1f);
            font.setScale(1f);
            font.draw(batch, "LAN", 515, 180);
			
			for (int i = 0; i < 8; i++)
			{
			    displayCards1[i].render(batch);
			    displayCards2[i].render(batch);
			}
		}
		else if(menuState == HOST_STATE)
		{
			font.setScale(2f);
			font.setColor(0f,0f,0f,1f);
			font.draw(batch, "Waiting For Connection...", 100, 400);
			font.setScale(1f);
			font.setColor(1f,0f,0f,1f);
			hostCancelOption.render(batch);
		}
		else if(menuState == CONNECT_STATE)
		{
		    gameSelect.render(batch);
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
		else if (menuState == JOIN_STATE)
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
            font.draw(batch, "Joining Game...", 100, 400);
		}
		else if (menuState == LAN_HOST_STATE)
		{
            font.setScale(2f);
            font.setColor(0f,0f,0f,1f);
            font.draw(batch, "Waiting for connection...", 100, 400);
            font.setScale(1f);
            font.setColor(1f,0f,0f,1f);
            for(int i = 0; i < hostAdds.size(); i++)
            {
                font.draw(batch,hostAdds.get(i),100,230 - i*30);
            }
            hostCancelOption.render(batch);
		}
        else if(menuState == LAN_CONNECT_STATE)
        {
            ipField.render(batch);
            connectOption.render(batch);
            cancelOption.render(batch);
        }
        else if(menuState == HOW_TO_PLAY_STATE)
        {
            howToPlay.render(batch);
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
	    displayPos += Gdx.graphics.getDeltaTime()*50;
	    if (displayPos > 8*150)
	        displayPos = 0;
	    updateDisplayCards();
	    
		if(menuState == MAIN_STATE)
		{
			quitButton.update();
			hostButton.update();
			connectButton.update();
			connectLANButton.update();
			hostLANButton.update();
			howToPlayButton.update();
			
			if(quitButton.isActive())
			{
				quitButton.clear();
				Gdx.app.exit();
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
			}
            else if(hostLANButton.isActive())
            {
                menuState = LAN_HOST_STATE;
                System.out.println("Server Game created.");
                pcgtcg.netman = new LANServer();
                (new Thread(pcgtcg.netman)).start();
                pcgtcg.game = new Game(true);
                hostAdds = pcgtcg.netman.addresses;
                hostLANButton.clear();
                setFade();
            }
            else if(connectLANButton.isActive())
            {
                menuState = LAN_CONNECT_STATE;
                ipField = new TextField("IP",200,400);
                Gdx.input.setOnscreenKeyboardVisible(true);
                Gdx.input.setInputProcessor(ipField);
                connectLANButton.clear();
                setFade();
            }
            else if(howToPlayButton.isActive())
            {
                menuState = HOW_TO_PLAY_STATE;
                howToPlayButton.clear();
                setFade();
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
			
            if(Gdx.input.justTouched())
            {
                Vector3 touchPos = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
                pcgtcg.camera.unproject(touchPos);
                float tx = touchPos.x;
                float ty = touchPos.y;
                
                if(hostCancelOption.isTouched(tx, ty))
                {
                    pcgtcg.netman.close();
                    pcgtcg.netman = null;
                    pcgtcg.game = null;
                    menuState = MAIN_STATE;
                    setFade();
                }
            }
		}
		else if(menuState == CONNECT_STATE)
		{
			gameSelect.update();
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
                gameSelect.setList(gameList);
                menuState = CONNECT_STATE;
                setFade();
            }
		}
		else if (menuState == JOIN_STATE)
		{    
		     pcgtcg.netman.poll();
             if (pcgtcg.netman.isInGame() &&
                 pcgtcg.netman.isReady())
             {
                 pcgtcg.game = new Game(false);
                 setFade();
                 menuState = MAIN_STATE;
                 System.out.println("Connected to Server!");
                 pcgtcg.gameState = pcgtcg.GAME_STATE;
             }
		}
        else if(menuState == LAN_HOST_STATE)
        {
            if(pcgtcg.netman.isConnected())
            {
                menuState = MAIN_STATE;
                System.out.println("Client has connected!");
                pcgtcg.gameState = pcgtcg.GAME_STATE;
            }
            
            if(Gdx.input.justTouched())
            {
                Vector3 touchPos = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
                pcgtcg.camera.unproject(touchPos);
                float tx = touchPos.x;
                float ty = touchPos.y;
                
                if(hostCancelOption.isTouched(tx, ty))
                {
                    pcgtcg.netman.close();
                    pcgtcg.netman = null;
                    pcgtcg.game = null;
                    menuState = MAIN_STATE;
                    setFade();
                }
            }
        }
        else if(menuState == LAN_CONNECT_STATE)
        {
            if(Gdx.input.justTouched() && (pcgtcg.game == null))
            {
                Vector3 touchPos = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
                pcgtcg.camera.unproject(touchPos);
                float tx = touchPos.x;
                float ty = touchPos.y;
                
                if(connectOption.isTouched(tx, ty))
                {
                    connectIP = ipField.getData();
                    pcgtcg.netman = new LANClient();
                    (new Thread(pcgtcg.netman)).start();
                    pcgtcg.game = new Game(false);
                }
                if(cancelOption.isTouched(tx, ty))
                {
                    menuState = MAIN_STATE;
                    setFade();
                    Gdx.input.setOnscreenKeyboardVisible(false);
                }
            }
            if(Gdx.input.isKeyPressed(Input.Keys.ENTER) && (pcgtcg.game == null))
            {
                
                System.out.println("Client Game created.");
                connectIP = ipField.getData();
                pcgtcg.netman = new LANClient();
                (new Thread(pcgtcg.netman)).start();
                pcgtcg.game = new Game(false);
            }
            if((pcgtcg.game != null) && (pcgtcg.netman != null) && pcgtcg.netman.isConnected())
            {
                menuState = MAIN_STATE;
                System.out.println("Connected to Server!");
                Gdx.input.setOnscreenKeyboardVisible(false);
                pcgtcg.gameState = pcgtcg.GAME_STATE;
            }
        }
        else if (menuState == HOW_TO_PLAY_STATE)
        {
            howToPlay.update();
        }
	}
	
	public void setFade()
	{
	    fadeAlpha = 1f;
	    fadeFlag = true;
	}
	
	public void clearFade()
	{
	    fadeAlpha = 0f;
	    fadeFlag = false;
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
	
	void updateDisplayCards()
	{
	    for (int i = 0; i < 8; i++)
	    {
	        if (i*150 + displayPos <= 480)
	        {
	            displayCards1[i].setBox(0,   (int) displayPos + i*150, 120, 150);
	            displayCards2[i].setBox(680, (int) (pcgtcg.SCREEN_HEIGHT - (i+1)*150 - displayPos), 120, 150);
	        }
	        else
	        {
	            displayCards1[i].setBox(0,   (int) displayPos + i*150 - 8*150, 120, 150);
	            displayCards2[i].setBox(680, (int) (pcgtcg.SCREEN_HEIGHT - (i+1)*150 - displayPos + 8*150), 120, 150);
	        }   
	    }
	}
}
