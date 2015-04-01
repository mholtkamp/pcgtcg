package com.pcgtcg.card;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pcg.pcgtcg;
import com.pcgtcg.game.Field;
import com.pcgtcg.game.Game;

public class C4 extends Card {
	
	public C4()
	{
		super();
		tex = pcgtcg.manager.get("data/card4Tex.png", Texture.class);
		value = '4';
		power = 4;
		tributeValue = 1;
		activeDescriptor = "";
		passiveDescriptor = "Natural power doubles when the sum of the \nnatural powers of the enemy field are a multiple of 4.";
	}

	public void activate()
	{
		
	}
	
	public void summon()
	{
		
	}
	
	public int getPower()
	{
	    int total = 0;
	    Field efield;
	    if (isOwn)
	        efield = pcgtcg.game.efield;
	    else
	        efield = pcgtcg.game.field;
	    
	    for (int i = 0; i < efield.getSize(); i++)
	    {
	        total += efield.getCard(i).getNaturalPower();
	    }
	    
	    return (total%4 == 0) ? 8 : 4;
	}
	
	public void render(SpriteBatch batch)
	{
	    super.render(batch);
	    
	    if (getPower() == 8)
	    {
    	    batch.setColor(0.7f, 0.5f, 1.0f, 0.5f);
    	    batch.draw(whiteTex, box.x, box.y, box.width, box.height);
    	    batch.setColor(1f, 1f, 1f, 1f);
	    }
	}
}
