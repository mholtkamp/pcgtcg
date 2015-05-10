package com.pcgtcg.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.pcg.pcgtcg;

public abstract class Option implements Touchable
{
    protected boolean isValid;
    protected Rectangle box;
    protected BitmapFont font;
    protected Texture texture;
    protected Color validColor;
    protected Color invalidColor;
    protected static final int DEFAULT_WIDTH = 150;
    protected static final int DEFAULT_HEIGHT = 40;
   
    public Option(float x,
                  float y)
    {
        isValid = true;
        
        texture = pcgtcg.manager.get("data/whiteTex.png", Texture.class);
        font    = pcgtcg.manager.get("data/eras.fnt",BitmapFont.class);
        
        box = new Rectangle();
        box.x = x;
        box.y = y;
        box.width = DEFAULT_WIDTH;
        box.height = DEFAULT_HEIGHT;
        
        validColor   = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        invalidColor = new Color(0.4f, 0.4f, 0.4f, 1.0f);
    }
    
    public void render(SpriteBatch batch)
    {
        if(isValid)
            batch.setColor(validColor);
        else
            batch.setColor(invalidColor);

        batch.draw(texture,box.x,box.y,box.width,box.height);
        batch.setColor(1f, 1f, 1f, 1f);
    }
    
    public boolean isTouched(float x, float y)
    {
        if(isValid && box.contains(x, y))
            return true;
        else
            return false;
    }
    
    public void setValid(boolean v)
    {
        isValid = v;
    }
    
    public void setX(float x)
    {
        box.x = x;
    }
    public void setY(float y)
    {
        box.y = y;
    }
    public void setWidth(float w)
    {
        box.width = w;
    }
    
    public void setHeight(float h)
    {
        box.height = h;
    }
    
    public void setValidColor(float r,
                              float g,
                              float b,
                              float a)
    {
        validColor = new Color(r,g,b,a);
    }
}
