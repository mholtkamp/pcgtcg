package com.pcgtcg.util;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.pcg.pcgtcg;
import com.pcgtcg.card.Card;

public class TributeSelector extends OptionSelector{
	
	private Option tributeOption;
	private Option cancelOption;
	private int tributeCount;
	private Card card;
	private LinkedList<Integer> markedPositions;
	private Texture blueTex;

	
	public TributeSelector(Card card)
	{
		super();
		this.card = card;
		markedPositions = new LinkedList<Integer>();
		tributeOption = new Option("Tribute",12,250);
		cancelOption = new Option("Cancel", 12, 200);
		
		options.add(tributeOption);
		options.add(cancelOption);
		
		tributeOption.setValid(false);
		tributeCount = 0;
		
		blueTex = pcgtcg.manager.get("data/blueTex.png",Texture.class);
	}
	
	public void update()
	{
		if(tributeCount >= card.getTributeCost())
			tributeOption.setValid(true);
		if(Gdx.input.justTouched())
		{
			Vector3 touchPos = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
			pcgtcg.camera.unproject(touchPos);
			float tx = touchPos.x;
			float ty = touchPos.y;
			
			if(tributeOption.isTouched(tx, ty))
			{
				for(int i = 0; i < markedPositions.size(); i++)
				{
					pcgtcg.game.skill(markedPositions.get(i));
				}
				
				pcgtcg.game.summon(card);
				pcgtcg.game.inGameState = pcgtcg.game.PLAY_STATE;
			}
			else if(cancelOption.isTouched(tx, ty))
			{
				pcgtcg.game.inGameState = pcgtcg.game.PLAY_STATE;
			}
			
			for(int i = 0; i < pcgtcg.game.field.getSize(); i++)
			{
				if(pcgtcg.game.field.getCard(i).isTouched(tx, ty))
				{
					if(!markedPositions.contains(i))
					{
						markedPositions.add(i);
						tributeCount += pcgtcg.game.field.getCard(i).getTributeValue();
					}
					else
					{
						markedPositions.remove(new Integer(i));
						tributeCount -= pcgtcg.game.field.getCard(i).getTributeValue();
					}
				}
			}
		}
	}
	
	public void render(SpriteBatch batch)
	{
		super.render(batch);
		
		for(int i = 0; i < pcgtcg.game.field.getSize(); i++)
		{
			pcgtcg.game.field.getCard(i).render(batch);
			if(markedPositions.contains(i))
			{
				Rectangle cbox = pcgtcg.game.field.getCard(i).getBox();
				batch.draw(blueTex,cbox.x,cbox.y,cbox.width,cbox.height);
				
			}
		}
	}
}
