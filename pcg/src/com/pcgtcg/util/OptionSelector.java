package com.pcgtcg.util;

import java.util.LinkedList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pcg.pcgtcg;

public abstract class OptionSelector {
	
	protected LinkedList<Option> options;
	protected Texture grayTex;
	protected Texture blackTex;
	
	public OptionSelector()
	{
		grayTex = pcgtcg.manager.get("data/grayTex.png",Texture.class);
		blackTex = pcgtcg.manager.get("data/blackTex.png",Texture.class);
		options = new LinkedList<Option>();
	}
	public void render(SpriteBatch batch)
	{
		batch.draw(grayTex,0,0,pcgtcg.SCREEN_WIDTH,pcgtcg.SCREEN_HEIGHT);
		batch.draw(blackTex, 0, 0, 174, pcgtcg.SCREEN_HEIGHT);
		for(int i = 0; i < options.size(); i++)
		{
			options.get(i).render(batch);
		}
	}
	
	public abstract void update();

}
