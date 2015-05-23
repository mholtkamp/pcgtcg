package com.pcgtcg.util;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.pcg.pcgtcg;

public class StarFactory
{
    private Star[] starList;
    
    // Factory Limits
    private float minSpeed;
    private float maxSpeed;
    
    private float minSize;
    private float maxSize;
    
    private float minSpin;
    private float maxSpin;
    
    private Color minColor;
    private Color maxColor;
    
    private Rectangle spawnBox;
    
    // Factory Settings
    private int count;
    private static final int maxCount = 100;
    
    public StarFactory()
    {
        minSpeed = 100.0f;
        maxSpeed = 500.0f;
        
        minSize = 16.0f;
        maxSize = 32.0f;
        
        minSpin = 20.0f;
        maxSpin = 200.0f;
        
        minColor = new Color(1.0f, 1.0f, 0.5f, 1.0f);
        maxColor = new Color(1.0f, 1.0f, 1.0f, 1.0f); 
        
        spawnBox = new Rectangle();
        setAngle((float)Math.PI/4);
        
        count = maxCount;
        starList = new Star[count];
        for (int i = 0; i < count; i++)
        {
            starList[i] = new Star();
            setStar(starList[i]);
        }
        
        
    }

    public void update()
    {
        for(int i = 0; i < count; i++)
        {
            starList[i].update();
            if (!starList[i].isActive())
                setStar(starList[i]);
        }
    }
    
    public void render(SpriteBatch batch)
    {
        for(int i = 0; i < count; i++)
        {
            starList[i].render(batch);
        }
        batch.setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    public void setStar(Star star)
    {
        float sSpeed = ((float)Math.random())*(maxSpeed - minSpeed) + minSpeed;
        float sSize  = ((float)Math.random())*(maxSize - minSize) + minSize;
        float sSpin  = ((float)Math.random())*(maxSpin - minSpin) + minSpin;
        
        Color sColor = new Color();
        sColor.r = ((float)Math.random())*(maxColor.r - minColor.r) + minColor.r;
        sColor.g = ((float)Math.random())*(maxColor.g - minColor.g) + minColor.g;
        sColor.b = ((float)Math.random())*(maxColor.b - minColor.b) + minColor.b;
        sColor.a = ((float)Math.random())*(maxColor.a - minColor.a) + minColor.a;
        
        float sX = (float) (spawnBox.x + spawnBox.width*Math.random());
        float sY = (float) (spawnBox.y + spawnBox.height*Math.random());
        star.setProperties(sX, sY, sSize, sSpeed, sSpin, sColor);
    }
    
    public void setAngle(float newAngle)
    {
        Star.setAngle(newAngle);
        
        spawnBox.x = (float) (0.0 - pcgtcg.SCREEN_HEIGHT*Math.tan(newAngle));
        spawnBox.width = (float) pcgtcg.SCREEN_WIDTH + -1.0f*spawnBox.x;
        spawnBox.y = (float) pcgtcg.SCREEN_HEIGHT;
        spawnBox.height = 64.0f;
    }
    
    public void setCount(int newCount)
    {
        // Do not exceed maximum
        if (newCount > maxCount)
            return;
        
        if (newCount > count)
        {
            for (int i = count; i < newCount; i++)
            {
                setStar(starList[i]);
            }
        }

        count = newCount;
    }
    
    public void setColors(Color minColor, 
                          Color maxColor)
    {
        this.minColor.set(minColor);
        this.maxColor.set(maxColor);
    }
}
