package com.pcgtcg.util;

import com.pcg.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Button {
	
	private final int DEFAULT_X = 100, DEFAULT_Y = 100, DEFAULT_WIDTH = 100, DEFAULT_HEIGHT = 100;
	private final String DEFAULT_TEXTURE = "data/defaultButtonTex.png";
	private final String DEFAULT_FONT = "data/eras.fnt";
	private Rectangle box;
	private Texture tex;
	private String text;
	private BitmapFont font;
	private boolean active;
	private boolean touched;
	
	public Button()
	{
		active = false;
		touched = false;
		box = new Rectangle();
		text = "";
		tex = pcgtcg.manager.get(DEFAULT_TEXTURE,Texture.class);
		font = pcgtcg.manager.get(DEFAULT_FONT,BitmapFont.class);
		font.setColor(0f, 0f, 0f, 1f);
		box.x = DEFAULT_X;
		box.y = DEFAULT_Y;
		box.width = DEFAULT_WIDTH;
		box.height = DEFAULT_HEIGHT;
		
	}
	
	public Button(int x, int y, int width, int height)
	{
		this();
		box.x = x;
		box.y = y;
		box.width = width;
		box.height = height;
		
	}
	
	public Button(int x, int y, int width, int height, String tex, String text)
	{
		this(x,y,width,height);
		this.tex = pcgtcg.manager.get(tex);
		this.text = text;
	}
	
	public void set()
	{
		active = true;
	}
	
	public void clear()
	{
		active = false;
	}
	
	public void toggle()
	{
		active = !active;
	}
	
	public boolean contains(int x, int y)
	{
		if(box.contains(x, y))
		{
			return true;
		}
		else
			return false;
	}
	
	public boolean fire(int x, int y)
	{
		if(box.contains(x,y))
		{
			set();
			return true;
		}
		else
			return false;
	}
	
	public void render(SpriteBatch batch)
	{
		font.setScale(1f);
		font.setColor(0f, 0f, 0f, 1f);
		batch.draw(tex, box.x, box.y, box.width, box.height);
		font.draw(batch, text, box.x + 20, box.y + 35);
	}
	
	public void update()
	{
		if(Gdx.input.isTouched())
		{
			if(!touched)
			{
				touched = true;
				Vector3 touchPos = new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
				pcgtcg.camera.unproject(touchPos);
				fire((int) touchPos.x,(int) touchPos.y);
			}
		}
		else
			touched = false;
	}
	
	
	public void setText(String text)
	{
		this.text = text;
	}
	
	public void setTexture(String tex)
	{
		this.tex = pcgtcg.manager.get(tex,Texture.class);
	}
}
