package com.pcgtcg.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.pcg.pcgtcg;

public class HistoryToast {
    
    public static final float DEFAULT_TOAST_WIDTH = 150.0f;
    public static final float DEFAULT_TOAST_HEIGHT = 30.0f;
    public BitmapFont font;
    public Rectangle box;
    public Texture background;
    public String text;
    public float curTime = 0.0f;
    public static final float maxTime = 5.0f;
    public static final float fadeTime = 3.5f;
    
    public HistoryToast(boolean isOwn)
    {
        box = new Rectangle();
        box.x = 0.0f;
        box.y = 0.0f;
        box.width = DEFAULT_TOAST_WIDTH;
        box.height = DEFAULT_TOAST_HEIGHT;
        
        if (isOwn)
            background = pcgtcg.manager.get("data/p1color.png", Texture.class);
        else
            background = pcgtcg.manager.get("data/p2color.png", Texture.class);
        
        text = "Action";
        font = pcgtcg.manager.get("data/eras.fnt", BitmapFont.class);
    }

    public void render(SpriteBatch batch)
    {
        float alpha = 1.0f;
        if (curTime > fadeTime)
        {
            alpha = (maxTime - curTime)/(maxTime - fadeTime);
        }
        batch.setColor(1.0f, 1.0f, 1.0f, alpha);
        batch.draw(background, box.x, box.y, box.width, box.height);
        
        font.setScale(0.8f);
        font.setColor(1f,1f,1f,alpha);
        font.draw(batch, text, box.x + 12, box.y + box.height - 7);
        font.setColor(1f, 1f, 1f, 1f);
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public void update()
    {
        curTime += Gdx.graphics.getDeltaTime();
    }
    
    public float getTime()
    {
        return curTime;
    }
    
    
}
