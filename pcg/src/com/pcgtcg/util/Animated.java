package com.pcgtcg.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface Animated {
    
    public void setBox(int x,
                       int y,
                       int width,
                       int height);
    public Rectangle getBox();
    public void render(SpriteBatch batch);

}
