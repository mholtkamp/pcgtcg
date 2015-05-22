package com.pcgtcg.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.pcg.pcgtcg;

public class Star
{
    private static float angle = (float) Math.PI/4;
    private Texture texture;
    private float speed;
    private float size;
    private float spin;
    private Color color;
    private Rectangle box;
    private float rotation;
    private boolean active;
    
    public Star()
    {
        texture = pcgtcg.manager.get("data/star.png", Texture.class);
        speed   = 100.0f;
        size    = 32.0f;
        spin    = 40.0f;
        color   = new Color(1.0f, 1.0f, 1.0f, 1.0f);
        box     = new Rectangle();
        rotation = 0.0f;
    }
    
    public void update()
    {
        box.x += Math.cos(angle)*Gdx.graphics.getDeltaTime()*speed;
        box.y -= Math.sin(angle)*Gdx.graphics.getDeltaTime()*speed;
        if (box.y + box.height < 0)
            active = false;
        rotation += spin*Gdx.graphics.getDeltaTime();
    }
    
    public void render(SpriteBatch batch)
    {
        batch.setColor(color);
        batch.draw(texture,
                   box.x,
                   box.y,
                   box.width/2,
                   box.height/2,
                   box.width,
                   box.height,
                   1.0f,
                   1.0f,
                   rotation,
                   0,
                   0,
                   texture.getWidth(),
                   texture.getHeight(),
                   false,
                   false);
    }
    
    public boolean isActive()
    {
        return active;
    }
    
    public void setProperties(float x,
                              float y,
                              float size,
                              float speed,
                              float spin,
                              Color color)
    {
        box.x      = x;
        box.y      = y;
        box.width  = size;
        box.height = size;
        this.speed = speed;
        this.spin  = spin;
        this.color.set(color);
        this.active = true;
    }
    
    public static void setAngle(float newAngle)
    {
        angle = newAngle;
    }
}
