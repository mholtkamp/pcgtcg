package com.pcgtcg.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.pcg.pcgtcg;
import com.pcgtcg.card.Card;

public class AttackSelector extends OptionSelector {
	
	private Card card;
	private Card target;
	private int targetPos;
	private TextOption attackOption;
	private TextOption cancelOption;
	private Texture blueTex;

	
	public AttackSelector(Card card)
	{
		super();
		this.card = card;
		
		attackOption = new TextOption("Attack", 12, 250);
		cancelOption = new TextOption("Cancel", 12, 200);
		
		options.add(attackOption);
		options.add(cancelOption);
		
		attackOption.setValid(false);
		blueTex = pcgtcg.manager.get("data/blueTex.png",Texture.class);
		targetPos = 0;
		
	}
	
	public void update()
	{

		if(Gdx.input.justTouched())
		{
			Vector3 touchPos = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
			pcgtcg.camera.unproject(touchPos);
			float tx = touchPos.x;
			float ty = touchPos.y;
			
			if(attackOption.isTouched(tx, ty))
			{

				//*********************************************
				//************ BATTLE CALCULATIONS ************
				//*********************************************
				
				card.setHasAttacked(true);
				pcgtcg.game.hasPlayerAttacked = true;
				
				if(card.getPower() > target.getPower())
				{
					// Deal damage if the target is in attack position
					// or if the attacking card is an 8.
					if(target.inAttackPosition() ||
					   card.getValue() == '8')
						pcgtcg.game.damage(card.getPower() - target.getPower());
					
					// Moved this kill statement after damage calculation
					// so that changing location doesn't reset the card status
					if (target.getValue() != '9')
					    pcgtcg.game.kill(targetPos);
				}
				else if(card.getPower() == target.getPower())
				{
					if(target.inAttackPosition())
					{
						for(int i = 0; i < pcgtcg.game.field.getSize(); i++)
						{
							if(pcgtcg.game.field.getCard(i) == card)
							{
							    if (card.getValue() != '9')
							        pcgtcg.game.skill(i);
							}
						}
	                    if (target.getValue() != '9')
	                        pcgtcg.game.kill(targetPos);
					}
				}
				else if(card.getPower() < target.getPower())
				{
					if(target.inAttackPosition())
					{
						for(int i = 0; i < pcgtcg.game.field.getSize(); i++)
						{
							if(pcgtcg.game.field.getCard(i) == card)
							{
							    if (card.getValue() != '9')
							        pcgtcg.game.skill(i);
							}
						}
					}
					pcgtcg.game.sdamage(target.getPower() - card.getPower());
				}
				card.setHasAttacked(true);
				pcgtcg.game.inGameState = pcgtcg.game.PLAY_STATE;
				
				// Show toast for attack and notify opponent
				pcgtcg.game.addToast("" + card.getValue() + " ATK " + target.getValue());
			}
			else if(cancelOption.isTouched(tx, ty))
			{
				pcgtcg.game.inGameState = pcgtcg.game.PLAY_STATE;
			}
			for(int i = 0; i < pcgtcg.game.efield.getSize(); i++)
			{
				if(pcgtcg.game.efield.getCard(i).isTouched(tx, ty))
				{
					target = pcgtcg.game.efield.getCard(i);
					targetPos = i;
					attackOption.setValid(true);
				}
			}
		}
	}
	
	public void render(SpriteBatch batch)
	{
		super.render(batch);
		
		for(int i = 0; i < pcgtcg.game.efield.getSize(); i++)
		{
			pcgtcg.game.efield.getCard(i).render(batch);
			if(pcgtcg.game.efield.getCard(i) == target)
			{
				Rectangle cbox = pcgtcg.game.efield.getCard(i).getBox();
				batch.draw(blueTex,cbox.x,cbox.y,cbox.width,cbox.height);
				
			}
		}
	}

}
