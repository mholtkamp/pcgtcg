package com.pcgtcg.menu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.pcg.pcgtcg;
import com.pcgtcg.util.Touchable;

public class GameInfo implements Touchable
{
    public int    id;
    public int    status;
    public String hostName;
    
    public Rectangle box;
    private BitmapFont font;
    private Texture whiteTex;
    
    public static final int WAITING = 0;
    public static final int INPROGRESS = 1;
    
    public GameInfo()
    {
        id       = -1;
        status   = -1;
        hostName = "";
        
        box = new Rectangle();
        box.x      = 0;
        box.y      = 0;
        box.width  = 240;
        box.height = 100;
        
        font = pcgtcg.manager.get("data/eras.fnt", BitmapFont.class);
        whiteTex  = pcgtcg.manager.get("data/whiteTex.png", Texture.class);
    }
    
    public boolean isTouched(float tx,
                             float ty)
    {
        if (box.contains(tx, ty))
            return true;
        else
            return false;
    }
    
    public void render(SpriteBatch batch)
    {
        // Draw background
        if (status == WAITING)
        {
            batch.setColor(0.9f, 0.4f, 0.4f, 1.0f);
            font.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        }
        else
        {
            batch.setColor(0.1f, 0.1f, 0.1f, 1.0f);
            font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }
        batch.draw(whiteTex,
                   box.x,
                   box.y,
                   box.width,
                   box.height);
        
        batch.setColor(1f, 1f, 1f, 1f);
        
        font.setScale(0.8f);
        font.draw(batch,
                  "Game ID: " + id, 
                  box.x + 10,
                  box.y + 90);
        font.draw(batch,
                  "Host: " + hostName,
                  box.x + 10,
                  box.y + 60);
        font.draw(batch,
                  "Mode: Dealt",
                  box.x + 10,
                  box.y + 30);
        
        font.setColor(1f,1f,1f,1f);
    }
    
    public void setPosition(int x, int y)
    {
        this.box.x = x;
        this.box.y = y;
    }
}
