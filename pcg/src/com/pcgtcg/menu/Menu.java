package com.pcgtcg.menu;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.pcg.pcgtcg;
import com.pcgtcg.game.Game;
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
	
	private List<String> hostAdds;
	
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
			for(int i = 0; i < hostAdds.size(); i++)
			{
				font.draw(batch,hostAdds.get(i),100,230 - i*30);
			}
		}
		else if(menuState == CONNECT_STATE)
		{
			ipField.render(batch);
			connectOption.render(batch);
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
				hostAdds = pcgtcg.game.netman.getIP();
				hostButton.clear();
			}
			else if(connectButton.isActive())
			{
				menuState = CONNECT_STATE;
				ipField = new TextField("IP",200,400);
				Gdx.input.setOnscreenKeyboardVisible(true);
				Gdx.input.setInputProcessor(ipField);
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
			if(Gdx.input.justTouched() && (pcgtcg.game == null))
			{
				Vector3 touchPos = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
				pcgtcg.camera.unproject(touchPos);
				float tx = touchPos.x;
				float ty = touchPos.y;
				
				if(connectOption.isTouched(tx, ty))
				{
					pcgtcg.connectIP = ipField.getData();
					pcgtcg.game = new Game(false);
				}
			}
			if(Gdx.input.isKeyPressed(Input.Keys.ENTER) && (pcgtcg.game == null))
			{
				
				System.out.println("Client Game created.");
				pcgtcg.connectIP = ipField.getData();
				pcgtcg.game = new Game(false);
			}
			if((pcgtcg.game != null) && (pcgtcg.game.netman != null) && pcgtcg.game.netman.isConnected())
			{
				menuState = MAIN_STATE;
				System.out.println("Connected to Server!");
				Gdx.input.setOnscreenKeyboardVisible(false);
				pcgtcg.gameState = pcgtcg.GAME_STATE;
			}
		}
	}
	
	public void reset()
	{
	    
	}
}
