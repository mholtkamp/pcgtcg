package com.pcgtcg.util;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pcg.pcgtcg;

public abstract class OptionSelector {
	
	protected LinkedList<Option> options;
	protected Texture whiteTex;
	protected Texture blackTex;
	
	public OptionSelector()
	{
		whiteTex = pcgtcg.manager.get("data/whiteTex.png",Texture.class);
		options = new LinkedList<Option>();
	}
	public void render(SpriteBatch batch)
	{
	    batch.setColor(0f, 0f, 0f, 0.4f);
		batch.draw(whiteTex,0,0,pcgtcg.SCREEN_WIDTH,pcgtcg.SCREEN_HEIGHT);
		batch.setColor(0f, 0f, 0f, 1f);
		batch.draw(whiteTex, 0, 0, 174, pcgtcg.SCREEN_HEIGHT);
		batch.setColor(1f, 1f, 1f, 1f);
		
		for(int i = 0; i < options.size(); i++)
		{
			options.get(i).render(batch);
		}
	}
	
	public abstract void update();

}
