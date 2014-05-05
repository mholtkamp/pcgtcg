package com.pcgtcg.menu;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pcg.pcgtcg;
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
	
	public void update()
	{
		optionsButton.update();
		hostButton.update();
		connectButton.update();
		
		if(optionsButton.isActive())
		{
			System.out.println("Options");
		}
		else if(hostButton.isActive())
		{
			System.out.println("Host");
		}
		else if(connectButton.isActive())
		{
			System.out.println("Connect");
		}
	}
	
}
